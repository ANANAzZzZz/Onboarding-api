package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.PageDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы со страницами.
 */
public interface PageService {

    /**
     * Возвращает список всех страниц.
     *
     * @return список всех страниц
     */
    List<PageDto> getPages();

    /**
     * Возвращает страницу по ее идентификатору.
     *
     * @param id идентификатор страницы
     * @return страница с указанным идентификатором
     */
    PageDto getPageById(Long id);

    /**
     * Добавляет новую страницу.
     *
     * @param pageDto объект, содержащий данные новой страницы
     * @return добавленная страница
     */
    PageDto addPage(PageDto pageDto);

    /**
     * Обновляет существующую страницу.
     *
     * @param pageDto объект, содержащий обновленные данные страницы
     * @return обновленная страница
     */
    PageDto updatePage(PageDto pageDto);

    /**
     * Удаляет страницу по ее идентификатору.
     *
     * @param id идентификатор страницы
     */
    void deletePage(Long id);
}
