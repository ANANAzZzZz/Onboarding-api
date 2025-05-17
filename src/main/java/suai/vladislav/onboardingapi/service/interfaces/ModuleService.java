package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.ModuleDto;

import java.util.List;

public interface ModuleService {

    List<ModuleDto> getModules();

    ModuleDto getModuleById(Long id);

    ModuleDto addModule(ModuleDto moduleDto);

    ModuleDto updateModule(ModuleDto moduleDto);

    void deleteModule(Long id);
}
