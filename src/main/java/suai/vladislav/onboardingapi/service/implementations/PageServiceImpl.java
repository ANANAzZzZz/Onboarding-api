package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.PageDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.PageMapper;
import suai.vladislav.onboardingapi.model.Module;
import suai.vladislav.onboardingapi.model.Page;
import suai.vladislav.onboardingapi.repository.PageRepository;
import suai.vladislav.onboardingapi.service.interfaces.EntityFinderService;
import suai.vladislav.onboardingapi.service.interfaces.PageService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final PageMapper pageMapper;
    private final EntityFinderService entityFinderService;

    @Override
    @Cacheable(value = "allPages")
    public List<PageDto> getPages() {
        log.info("вызван getPages");

        return pageRepository.findAll().stream()
            .map(pageMapper::toDto)
            .toList();
    }

    @Override
    @Cacheable(value = "pageById", key = "#id")
    public PageDto getPageById(Long id) {
        log.info("вызван getPageById, id = {}", id);

        return pageMapper.toDto(entityFinderService.getPageOrThrow(id));
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "allPages", allEntries = true)
    })
    public PageDto addPage(PageDto pageDto) {
        log.info("вызван addPage");

        Module module = entityFinderService.getModuleOrThrow(pageDto.moduleId());
        Page page = pageMapper.toModel(pageDto);

        page.setModule(module);

        return pageMapper.toDto(
            pageRepository.save(page)
        );

    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "allPages", allEntries = true),
        @CacheEvict(value = "pageById", key = "#pageDto.id()"),
        @CacheEvict(value = "findEntityById", key = "'page_' + #pageDto.id()")
    })
    public PageDto updatePage(PageDto pageDto) {
        log.info("вызван updatePage");

        if (pageDto.id() == null) {
            throw new CommonOnboardingApiException(ErrorType.ID_IS_MISSING);
        }

        Page page = entityFinderService.getPageOrThrow(pageDto.id());
        Module module = entityFinderService.getModuleOrThrow(pageDto.moduleId());

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
    @Caching(evict = {
        @CacheEvict(value = "allPages", allEntries = true),
        @CacheEvict(value = "pageById", key = "#id"),
        @CacheEvict(value = "findEntityById", key = "'page_' + #id")
    })
    public void deletePage(Long id) {
        Page page = entityFinderService.getPageOrThrow(id);

        pageRepository.delete(page);
    }
}
