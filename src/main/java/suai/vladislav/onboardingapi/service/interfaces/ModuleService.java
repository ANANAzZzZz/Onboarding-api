package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.ModuleDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с модулями.
 */
public interface ModuleService {

    /**
     * Возвращает список всех модулей.
     *
     * @return список модулей
     */
    List<ModuleDto> getModules();

    /**
     * Возвращает модуль по его идентификатору.
     *
     * @param id идентификатор модуля
     * @return модуль
     */
    ModuleDto getModuleById(Long id);

    /**
     * Добавляет новый модуль.
     *
     * @param moduleDto DTO модуля
     * @return добавленный модуль
     */
    ModuleDto addModule(ModuleDto moduleDto);

    /**
     * Обновляет существующий модуль.
     *
     * @param moduleDto DTO модуля
     * @return обновленный модуль
     */
    ModuleDto updateModule(ModuleDto moduleDto);

    /**
     * Удаляет модуль по его идентификатору.
     *
     * @param id идентификатор модуля
     */
    void deleteModule(Long id);
}
