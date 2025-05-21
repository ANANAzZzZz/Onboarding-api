package suai.vladislav.onboardingapi.service.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.AchievementDto;
import suai.vladislav.onboardingapi.dto.AchievementNotificationDto;
import suai.vladislav.onboardingapi.dto.UserActionDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.AchievementMapper;
import suai.vladislav.onboardingapi.model.Achievement;
import suai.vladislav.onboardingapi.model.Scoreboard;
import suai.vladislav.onboardingapi.model.User;
import suai.vladislav.onboardingapi.repository.AchievementRepository;
import suai.vladislav.onboardingapi.repository.ScoreboardRepository;
import suai.vladislav.onboardingapi.repository.UserRepository;
import suai.vladislav.onboardingapi.service.interfaces.AchievementService;
import suai.vladislav.onboardingapi.service.interfaces.EntityFinderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final EntityFinderService entityFinderService;
    private final ScoreboardRepository scoreboardRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public List<AchievementDto> getAllAchievements() {
        log.info("Вызван getAllAchievements");
        return achievementRepository.findAll().stream()
            .map(achievementMapper::toDto)
            .toList();
    }

    @Override
    public AchievementDto getAchievementById(Long id) {
        log.info("Вызван getAchievementById, id = {}", id);
        return achievementMapper.toDto(getAchievementOrThrow(id));
    }

    @Override
    @Transactional
    public AchievementDto createAchievement(AchievementDto achievementDto) {
        log.info("Вызван createAchievement");
        validateCondition(achievementDto.condition());

        Achievement achievement = achievementMapper.toModel(achievementDto);
        return achievementMapper.toDto(achievementRepository.save(achievement));
    }

    @Override
    @Transactional
    public AchievementDto updateAchievement(AchievementDto achievementDto) {
        log.info("Вызван updateAchievement");

        if (achievementDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        validateCondition(achievementDto.condition());

        Achievement achievement = getAchievementOrThrow(achievementDto.id());

        achievement.setName(achievementDto.name());
        achievement.setDescription(achievementDto.description());
        achievement.setPicture(achievementDto.picture());
        achievement.setActionType(achievementDto.actionType());
        achievement.setCondition(achievementDto.condition());
        achievement.setPointsReward(achievementDto.pointsReward());

        return achievementMapper.toDto(achievementRepository.save(achievement));
    }

    @Override
    @Transactional
    public void deleteAchievement(Long id) {
        log.info("Вызван deleteAchievement, id = {}", id);
        Achievement achievement = getAchievementOrThrow(id);
        achievementRepository.delete(achievement);
    }

    @Override
    public List<AchievementDto> getUserAchievements(Long userId) {
        log.info("Вызван getUserAchievements, userId = {}", userId);
        return achievementRepository.findByUsersId(userId).stream()
            .map(achievementMapper::toDto)
            .toList();
    }

    @Override
    @Transactional
    public List<AchievementNotificationDto> processUserAction(UserActionDto userActionDto) {
        log.info("Вызван processUserAction, userId = {}, actionType = {}",
            userActionDto.userId(), userActionDto.actionType());

        User user = entityFinderService.getUserOrThrow(userActionDto.userId());

        List<Achievement> potentialAchievements = achievementRepository.findByActionType(userActionDto.actionType());

        List<AchievementNotificationDto> newAchievements = new ArrayList<>();

        Set<Achievement> userAchievements = user.getAchievements();

        for (Achievement achievement : potentialAchievements) {
            if (userAchievements != null && userAchievements.contains(achievement)) {
                continue;
            }

            if (checkAchievementCondition(achievement, userActionDto)) {
                user.addAchievement(achievement);

                userRepository.save(user);

                updateUserScore(user, achievement.getPointsReward());

                newAchievements.add(AchievementNotificationDto.builder()
                    .userId(user.getId())
                    .achievementId(achievement.getId())
                    .achievementName(achievement.getName())
                    .achievementDescription(achievement.getDescription())
                    .achievementPicture(achievement.getPicture())
                    .pointsAwarded(achievement.getPointsReward())
                    .build());
            }
        }
        return newAchievements;
    }

    private Achievement getAchievementOrThrow(Long id) {
        return achievementRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.ACHIEVEMENT_NOT_FOUND, id));
    }

    private void validateCondition(String condition) {
        try {
            objectMapper.readTree(condition);
        } catch (JsonProcessingException e) {
            throw new CommonOnboardingApiException(ErrorType.INVALID_ACHIEVEMENT_CONDITION, e);
        }
    }

    private boolean checkAchievementCondition(Achievement achievement, UserActionDto userActionDto) {
        try {
            JsonNode conditionNode = objectMapper.readTree(achievement.getCondition());
            Map<String, Object> metadata = userActionDto.metadata();

            return switch (achievement.getActionType()) {
                case COMPLETE_MODULE -> checkModuleCompletion(conditionNode, metadata);
                case COMPLETE_TRACK -> checkTrackCompletion(conditionNode, metadata);
                case COMPLETE_SURVEY -> checkSurveyCompletion(conditionNode, metadata);
                case DAYS_STREAK -> checkDaysStreak(conditionNode, metadata);
                case SCORE_THRESHOLD -> checkScoreThreshold(conditionNode, userActionDto.userId());
                case KNOWLEDGE_BASE_READ -> checkKnowledgeBaseRead(conditionNode, metadata);
                default -> false;
            };
        } catch (Exception e) {
            log.error("Ошибка при проверке условия ачивки", e);
            return false;
        }
    }

    private boolean checkModuleCompletion(JsonNode condition, Map<String, Object> metadata) {
        if (!metadata.containsKey("moduleId")) {
            return false;
        }

        Long moduleId = Long.valueOf(metadata.get("moduleId").toString());

        if (condition.has("moduleId")) {
            return moduleId.equals(condition.get("moduleId").asLong());
        }

        return true; // Если просто нужно завершить любой модуль
    }

    private boolean checkTrackCompletion(JsonNode condition, Map<String, Object> metadata) {
        if (!metadata.containsKey("trackId")) {
            return false;
        }

        Long trackId = Long.valueOf(metadata.get("trackId").toString());

        if (condition.has("trackId")) {
            return trackId.equals(condition.get("trackId").asLong());
        }

        return true; // Если просто нужно завершить любой трек
    }

    private boolean checkSurveyCompletion(JsonNode condition, Map<String, Object> metadata) {
        if (!metadata.containsKey("surveyId")) {
            return false;
        }

        Long surveyId = Long.valueOf(metadata.get("surveyId").toString());

        if (condition.has("surveyId")) {
            return surveyId.equals(condition.get("surveyId").asLong());
        }

        return true; // Если просто нужно завершить любой опрос
    }

    private boolean checkDaysStreak(JsonNode condition, Map<String, Object> metadata) {
        if (!metadata.containsKey("daysStreak")) {
            return false;
        }

        Integer daysStreak = Integer.valueOf(metadata.get("daysStreak").toString());

        if (condition.has("minDays")) {
            return daysStreak >= condition.get("minDays").asInt();
        }

        return false;
    }

    private boolean checkScoreThreshold(JsonNode condition, Long userId) {
        if (!condition.has("minScore")) {
            return false;
        }

        Integer minScore = condition.get("minScore").asInt();

        // Находим очки пользователя
        List<Scoreboard> userScoreboards = scoreboardRepository.findListByUserId(userId);
        int totalScore = userScoreboards.stream()
            .mapToInt(Scoreboard::getScore)
            .sum();

        return totalScore >= minScore;
    }

    private boolean checkKnowledgeBaseRead(JsonNode condition, Map<String, Object> metadata) {
        if (!metadata.containsKey("knowledgeBaseId")) {
            return false;
        }

        Long knowledgeBaseId = Long.valueOf(metadata.get("knowledgeBaseId").toString());

        if (condition.has("knowledgeBaseId")) {
            return knowledgeBaseId.equals(condition.get("knowledgeBaseId").asLong());
        }

        return true; // Если просто нужно прочитать любую базу знаний
    }

    @Transactional
    public void updateUserScore(User user, Integer points) {
        // Находим последнюю запись в scoreboard или создаем новую
        Scoreboard scoreboard = scoreboardRepository.findByUserId(user.getId())
            .orElseGet(() -> {
                Scoreboard newScoreboard = new Scoreboard();
                newScoreboard.setUser(user);
                newScoreboard.setScore(0);
                return newScoreboard;
            });

        // Обновляем очки
        scoreboard.setScore(scoreboard.getScore() + points);

        // Сохраняем
        scoreboardRepository.save(scoreboard);
    }
}