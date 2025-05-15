package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.UserProgressInModuleDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.UserProgressInModuleMapper;
import suai.vladislav.onboardingapi.model.Module;
import suai.vladislav.onboardingapi.model.User;
import suai.vladislav.onboardingapi.model.UserProgressInModule;
import suai.vladislav.onboardingapi.repository.ModuleRepository;
import suai.vladislav.onboardingapi.repository.UserProgressInModuleRepository;
import suai.vladislav.onboardingapi.repository.UserRepository;
import suai.vladislav.onboardingapi.service.interfaces.UserProgressInModuleService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProgressInModuleServiceImpl implements UserProgressInModuleService {

    private final UserProgressInModuleRepository userProgressInModuleRepository;

    private final UserProgressInModuleMapper userProgressInModuleMapper;

    private final UserRepository userRepository;

    private final ModuleRepository moduleRepository;

    @Override
    public List<UserProgressInModuleDto> getUserProgressInModule() {
        log.info("вызван getUserProgressInModule");

        return userProgressInModuleRepository.findAll().stream()
            .map(userProgressInModuleMapper::toDto)
            .toList();
    }

    @Override
    public UserProgressInModuleDto getUserProgressInModuleById(Long id) {
        log.info("вызван getUserProgressInModuleById id = {}", id);

        return userProgressInModuleMapper.toDto(userProgressInModuleRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.USER_PROGRESS_IN_MODULE_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public UserProgressInModuleDto addUserProgressInModule(UserProgressInModuleDto userProgressInModuleDto) {
        log.info("вызван addUserProgressInModule id = {}", userProgressInModuleDto.id());

        User user = userRepository.findById(userProgressInModuleDto.userId())
            .orElseThrow(
                () -> new CommonOnboardingApiException(ErrorType.USER_NOT_FOUND, userProgressInModuleDto.userId()
                )
            );

        Module module = moduleRepository.findById(userProgressInModuleDto.moduleId())
            .orElseThrow(
                () -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, userProgressInModuleDto.moduleId()
                )
            );

        UserProgressInModule userProgressInModule = userProgressInModuleMapper.toModel(userProgressInModuleDto);

        userProgressInModule.setUser(user);
        userProgressInModule.setModule(module);

        return userProgressInModuleMapper.toDto(
            userProgressInModuleRepository.save(userProgressInModule)
        );
    }

    @Override
    @Transactional
    public UserProgressInModuleDto updateUserProgressInModule(UserProgressInModuleDto userProgressInModuleDto) {
        log.info("вызван updateUserProgressInModule id = {}", userProgressInModuleDto.id());

        if (userProgressInModuleDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        UserProgressInModule userProgressInModule = userProgressInModuleRepository.findById(userProgressInModuleDto.id())
            .orElseThrow(
                () -> new CommonOnboardingApiException(
                    ErrorType.USER_PROGRESS_IN_MODULE_NOT_FOUND, userProgressInModuleDto.id()
                )
            );

        User user = userRepository.findById(userProgressInModuleDto.userId())
            .orElseThrow(() -> new CommonOnboardingApiException(
                ErrorType.USER_NOT_FOUND, userProgressInModuleDto.userId())
            );

        Module module = moduleRepository.findById(userProgressInModuleDto.moduleId())
            .orElseThrow(
                () -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, userProgressInModuleDto.moduleId())
            );

        userProgressInModule.setUser(user);
        userProgressInModule.setModule(module);
        userProgressInModule.setName(userProgressInModuleDto.name());
        userProgressInModule.setLastCompletedPageNumber(userProgressInModuleDto.lastCompletedPageNumber());

        return userProgressInModuleMapper.toDto(
            userProgressInModuleRepository.save(userProgressInModule)
        );
    }

    @Override
    @Transactional
    public void deleteUserProgressInModule(Long id) {
        log.info("вызван deleteUserProgressInModule, id = {}", id);

        UserProgressInModule userProgressInModule = userProgressInModuleRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.USER_PROGRESS_IN_MODULE_NOT_FOUND, id));

        userProgressInModuleRepository.delete(userProgressInModule);
    }
}
