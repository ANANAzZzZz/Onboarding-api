package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.ModuleDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.ModuleMapper;
import suai.vladislav.onboardingapi.model.Module;
import suai.vladislav.onboardingapi.model.Track;
import suai.vladislav.onboardingapi.repository.ModuleRepository;
import suai.vladislav.onboardingapi.repository.TrackRepository;
import suai.vladislav.onboardingapi.service.interfaces.ModuleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ModuleServiceImpl implements ModuleService {

    private final ModuleMapper moduleMapper;

    private final ModuleRepository moduleRepository;

    private final TrackRepository trackRepository;

    @Override
    public List<ModuleDto> getModules() {
        log.info("вызван getModules");

        return moduleRepository.findAll().stream()
            .map(moduleMapper::toDto)
            .toList();
    }

    @Override
    public ModuleDto getModuleById(Long id) {
        log.info("вызван getModuleById, id = {}", id);

        return moduleMapper.toDto(moduleRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public ModuleDto addModule(ModuleDto moduleDto) {
        log.info("вызван addModule");

        Track track = trackRepository.findById(moduleDto.trackId())
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.TRACK_NOT_FOUND, moduleDto.trackId()));

        Module module = moduleMapper.toModel(moduleDto);
        module.setTrack(track);

        return moduleMapper.toDto(
            moduleRepository.save(module)
        );
    }

    @Override
    @Transactional
    public ModuleDto updateModule(ModuleDto moduleDto) {
        log.info("вызван updateModule");

        if (moduleDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        Module module = moduleRepository.findById(moduleDto.id())
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, moduleDto.id()));

        Track track = trackRepository.findById(moduleDto.trackId())
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.TRACK_NOT_FOUND, moduleDto.trackId()));

        module.setName(moduleDto.name());
        module.setStartContent(moduleDto.startContent());
        module.setEndContent(moduleDto.endContent());
        module.setOrderInTrack(moduleDto.orderInTrack());
        module.setTrack(track);

        return moduleMapper.toDto(
            moduleRepository.save(module)
        );
    }

    @Override
    @Transactional
    public void deleteModule(Long id) {
        log.info("вызван deleteModule, id = {}", id);

        Module module = moduleRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, id));

        moduleRepository.delete(module);
    }
}
