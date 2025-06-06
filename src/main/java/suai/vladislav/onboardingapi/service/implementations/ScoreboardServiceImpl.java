package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.ScoreboardDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.ScoreboardMapper;
import suai.vladislav.onboardingapi.model.Scoreboard;
import suai.vladislav.onboardingapi.model.User;
import suai.vladislav.onboardingapi.repository.ScoreboardRepository;
import suai.vladislav.onboardingapi.service.interfaces.EntityFinderService;
import suai.vladislav.onboardingapi.service.interfaces.ScoreboardService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreboardServiceImpl implements ScoreboardService {

    private final ScoreboardMapper scoreboardMapper;
    private final ScoreboardRepository scoreboardRepository;
    private final EntityFinderService entityFinderService;

    @Override
    public List<ScoreboardDto> getScoreboard() {
        log.info("вызван getScoreboard");

        return scoreboardRepository.findAll().stream()
            .map(scoreboardMapper::toDto)
            .toList();
    }

    @Override
    public ScoreboardDto getScoreboardById(Long id) {
        log.info("вызван getScoreboardById id = {}", id);

        return scoreboardMapper.toDto(entityFinderService.getScoreboardOrThrow(id));
    }

    @Override
    @Transactional
    public ScoreboardDto addScoreboard(ScoreboardDto scoreboardDto) {
        log.info("вызван addScoreboard");

        User user = entityFinderService.getUserOrThrow(scoreboardDto.userId());

        if (scoreboardRepository.findByUserId(user.getId()).isPresent()) {
            throw new CommonOnboardingApiException(ErrorType.SCOREBOARD_ALREADY_EXISTS, user.getId());
        }

        Scoreboard scoreboard = scoreboardMapper.toModel(scoreboardDto);

        scoreboard.setScore(scoreboardDto.score());
        scoreboard.setUser(user);

        return scoreboardMapper.toDto(
            scoreboardRepository.save(scoreboard));
    }

    @Override
    @Transactional
    public ScoreboardDto updateScoreboard(ScoreboardDto scoreboardDto) {
        log.info("вызван updateScoreboard");

        if (scoreboardDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        Scoreboard scoreboard = entityFinderService.getScoreboardOrThrow(scoreboardDto.id());
        User user = entityFinderService.getUserOrThrow(scoreboardDto.userId());

        scoreboard.setScore(scoreboardDto.score());
        scoreboard.setUser(user);

        scoreboardRepository.save(scoreboard);

        return scoreboardMapper.toDto(scoreboard);
    }

    @Override
    @Transactional
    public void deleteScoreboard(Long id) {
        log.info("вызван deleteScoreboard");

        Scoreboard scoreboard = entityFinderService.getScoreboardOrThrow(id);

        scoreboardRepository.delete(scoreboard);
    }
}
