package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.AchievementDto;
import suai.vladislav.onboardingapi.dto.AchievementNotificationDto;
import suai.vladislav.onboardingapi.dto.UserActionDto;

import java.util.List;

/**
 * Интерфейс сервиса достижений.
 */
public interface AchievementService {

    /**
     * Получить список всех достижений.
     *
     * @return список всех достижений
     */
    List<AchievementDto> getAllAchievements();

    /**
     * Получить достижение по его идентификатору.
     *
     * @param id идентификатор достижения
     * @return достижение
     */
    AchievementDto getAchievementById(Long id);

    /**
     * Создать новое достижение.
     *
     * @param achievementDto DTO достижения
     * @return созданное достижение
     */
    AchievementDto createAchievement(AchievementDto achievementDto);

    /**
     * Обновить существующее достижение.
     *
     * @param achievementDto DTO достижения
     * @return обновленное достижение
     */
    AchievementDto updateAchievement(AchievementDto achievementDto);

    /**
     * Удалить достижение по его идентификатору.
     *
     * @param id идентификатор достижения
     */
    void deleteAchievement(Long id);

    /**
     * Получить список достижений пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список достижений пользователя
     */
    List<AchievementDto> getUserAchievements(Long userId);

    /**
     * Обработать действие пользователя и вернуть уведомления о достижениях.
     *
     * @param userActionDto DTO действия пользователя
     * @return список уведомлений о достижениях
     */
    List<AchievementNotificationDto> processUserAction(UserActionDto userActionDto);
}