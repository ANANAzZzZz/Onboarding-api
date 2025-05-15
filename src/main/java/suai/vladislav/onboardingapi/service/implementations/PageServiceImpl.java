package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.PageDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.PageMapper;
import suai.vladislav.onboardingapi.model.Module;
import suai.vladislav.onboardingapi.model.Page;
import suai.vladislav.onboardingapi.repository.ModuleRepository;
import suai.vladislav.onboardingapi.repository.PageRepository;
import suai.vladislav.onboardingapi.service.interfaces.PageService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    private final ModuleRepository moduleRepository;

    private final PageMapper pageMapper;

    @Override
    public List<PageDto> getPages() {
        log.info("вызван getPages");

        return pageRepository.findAll().stream()
            .map(pageMapper::toDto)
            .toList();
    }

    @Override
    public PageDto getPageById(Long id) {
        log.info("вызван getPageById, id = {}", id);

        return pageMapper.toDto(pageRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.PAGE_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public PageDto addPage(PageDto pageDto) {
        log.info("вызван addPage");

        Module module = moduleRepository.findById(pageDto.moduleId())
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, pageDto.moduleId()));

        Page page = pageMapper.toModel(pageDto);
        page.setModule(module);

        return pageMapper.toDto(
            pageRepository.save(page)
        );

    }

    @Override
    @Transactional
    public PageDto updatePage(PageDto pageDto) {
        log.info("вызван updatePage");

        if (pageDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        Page page = pageRepository.findById(pageDto.id())
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.PAGE_NOT_FOUND, pageDto.id()));

        Module module = moduleRepository.findById(pageDto.moduleId())
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.MODULE_NOT_FOUND, pageDto.moduleId()));

        page.setName(pageDto.name());
        page.setContent(pageDto.content());
        page.setModule(module);
        page.setOrderInModule(pageDto.orderInModule());

        return pageMapper.toDto(
            pageRepository.save(page)
        );
    }

    @Override
    @Transactional
    public void deletePage(Long id) {
        Page page = pageRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.PAGE_NOT_FOUND, id));

        pageRepository.delete(page);
    }
}
