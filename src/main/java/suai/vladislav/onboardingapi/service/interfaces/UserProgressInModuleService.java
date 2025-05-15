package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.UserProgressInModuleDto;

import java.util.List;

public interface UserProgressInModuleService {

    List<UserProgressInModuleDto> getUserProgressInModule();

    UserProgressInModuleDto getUserProgressInModuleById(Long id);

    UserProgressInModuleDto addUserProgressInModule(UserProgressInModuleDto userProgressInModuleDto);

    UserProgressInModuleDto updateUserProgressInModule(UserProgressInModuleDto userProgressInModuleDto);

    void deleteUserProgressInModule(Long id);
}
