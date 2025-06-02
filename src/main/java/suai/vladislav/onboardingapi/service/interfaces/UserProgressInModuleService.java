package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.UserProgressInModuleDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с прогрессом пользователя в модуле.
 */
public interface UserProgressInModuleService {

    /**
     * Получает список прогресса пользователя в модуле.
     *
     * @return список прогресса пользователя в модуле
     */
    List<UserProgressInModuleDto> getUserProgressInModule();

    /**
     * Получает прогресс пользователя в модуле по идентификатору.
     *
     * @param id идентификатор прогресса пользователя в модуле
     * @return прогресс пользователя в модуле
     */
    UserProgressInModuleDto getUserProgressInModuleById(Long id);

    /**
     * Добавляет прогресс пользователя в модуле.
     *
     * @param userProgressInModuleDto объект прогресса пользователя в модуле
     * @return добавленный прогресс пользователя в модуле
     */
    UserProgressInModuleDto addUserProgressInModule(UserProgressInModuleDto userProgressInModuleDto);

    /**
     * Обновляет прогресс пользователя в модуле.
     *
     * @param userProgressInModuleDto объект прогресса пользователя в модуле
     * @return обновленный прогресс пользователя в модуле
     */
    UserProgressInModuleDto updateUserProgressInModule(UserProgressInModuleDto userProgressInModuleDto);

    /**
     * Удаляет прогресс пользователя в модуле по идентификатору.
     *
     * @param id идентификатор прогресса пользователя в модуле
     */
    void deleteUserProgressInModule(Long id);
}
