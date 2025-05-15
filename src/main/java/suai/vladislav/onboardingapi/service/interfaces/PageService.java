package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.PageDto;

import java.util.List;

public interface PageService {

    List<PageDto> getPages();

    PageDto getPageById(Long id);

    PageDto addPage(PageDto pageDto);

    PageDto updatePage(PageDto pageDto);

    void deletePage(Long id);
}
