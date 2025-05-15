package suai.vladislav.onboardingapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import suai.vladislav.onboardingapi.config.MapperConfig;
import suai.vladislav.onboardingapi.dto.UserProgressInModuleDto;
import suai.vladislav.onboardingapi.model.UserProgressInModule;

@Mapper(config = MapperConfig.class)
public interface UserProgressInModuleMapper {
    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "userId", source = "user.id")
    UserProgressInModuleDto toDto(UserProgressInModule userProgressInModule);

    UserProgressInModule toModel(UserProgressInModuleDto userProgressInModuleDto);
}
