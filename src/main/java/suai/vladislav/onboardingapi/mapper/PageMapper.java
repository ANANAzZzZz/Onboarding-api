package suai.vladislav.onboardingapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import suai.vladislav.onboardingapi.dto.PageDto;
import suai.vladislav.onboardingapi.model.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(target = "moduleId", source = "module.id")
    PageDto toDto(Page page);

    Page toModel(PageDto pageDto);
}
