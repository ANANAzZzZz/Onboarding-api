package suai.vladislav.onboardingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserProgressInModuleDto (

    @Positive
    Long id,

    @NotBlank
    String name,

    @Positive
    Integer lastCompletedPageNumber,

    @Positive
    @NotNull
    Long moduleId,

    @Positive
    @NotNull
    Long userId
) {
}
