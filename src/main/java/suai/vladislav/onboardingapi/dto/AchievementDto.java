package suai.vladislav.onboardingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import suai.vladislav.onboardingapi.enums.ActionType;

public record AchievementDto(
    Long id,

    @NotBlank
    String name,

    @NotBlank
    String description,

    @NotBlank
    String picture,

    @NotNull
    ActionType actionType,

    @NotBlank
    String condition,

    @Positive
    Integer pointsReward
) {
}
