package suai.vladislav.onboardingapi.mapper;

import org.mapstruct.Mapper;
import suai.vladislav.onboardingapi.config.MapperConfig;
import suai.vladislav.onboardingapi.dto.AchievementDto;
import suai.vladislav.onboardingapi.model.Achievement;

@Mapper(config = MapperConfig.class)
public interface AchievementMapper {
    AchievementDto toDto(Achievement achievement);

    Achievement toModel(AchievementDto achievementDto);
}
