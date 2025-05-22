package suai.vladislav.onboardingapi.dto;

import jakarta.validation.constraints.NotNull;
import suai.vladislav.onboardingapi.enums.ActionType;

import java.util.Map;

public record UserActionDto(
    @NotNull
    Long userId,

    @NotNull
    ActionType actionType,

    Map<String, Object> metadata
) {
}
