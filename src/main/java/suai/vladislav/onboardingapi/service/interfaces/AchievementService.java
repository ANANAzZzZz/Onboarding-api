package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.AchievementDto;
import suai.vladislav.onboardingapi.dto.AchievementNotificationDto;
import suai.vladislav.onboardingapi.dto.UserActionDto;

import java.util.List;

public interface AchievementService {

    List<AchievementDto> getAllAchievements();

    AchievementDto getAchievementById(Long id);

    AchievementDto createAchievement(AchievementDto achievementDto);

    AchievementDto updateAchievement(AchievementDto achievementDto);

    void deleteAchievement(Long id);

    List<AchievementDto> getUserAchievements(Long userId);

    List<AchievementNotificationDto> processUserAction(UserActionDto userActionDto);
}