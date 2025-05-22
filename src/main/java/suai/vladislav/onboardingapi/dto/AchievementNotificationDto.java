package suai.vladislav.onboardingapi.dto;

import lombok.Builder;

@Builder
public record AchievementNotificationDto(
    Long userId,
    Long achievementId,
    String achievementName,
    String achievementDescription,
    String achievementPicture,
    Integer pointsAwarded
) {
}
