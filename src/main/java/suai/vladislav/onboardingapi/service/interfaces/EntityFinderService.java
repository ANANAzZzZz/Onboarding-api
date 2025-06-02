package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.model.KnowledgeBase;
import suai.vladislav.onboardingapi.model.Module;
import suai.vladislav.onboardingapi.model.Page;
import suai.vladislav.onboardingapi.model.Scoreboard;
import suai.vladislav.onboardingapi.model.Survey;
import suai.vladislav.onboardingapi.model.Track;
import suai.vladislav.onboardingapi.model.User;
import suai.vladislav.onboardingapi.model.UserProgressInModule;

/**
 * Интерфейс сервиса для поиска сущностей.
 */
public interface EntityFinderService {

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователь
     * @throws CommonOnboardingApiException если пользователь не найден
     */
    User getUserOrThrow(Long id);

    /**
     * Возвращает модуль по его идентификатору.
     *
     * @param id идентификатор модуля
     * @return модуль
     * @throws CommonOnboardingApiException если модуль не найден
     */
    Module getModuleOrThrow(Long id);

    /**
     * Возвращает страницу по ее идентификатору.
     *
     * @param id идентификатор страницы
     * @return страница
     * @throws CommonOnboardingApiException если страница не найдена
     */
    Page getPageOrThrow(Long id);

    /**
     * Возвращает опрос по его идентификатору.
     *
     * @param id идентификатор опроса
     * @return опрос
     * @throws CommonOnboardingApiException если опрос не найден
     */
    Survey getSurveyOrThrow(Long id);

    /**
     * Возвращает трек по его идентификатору.
     *
     * @param id идентификатор трека
     * @return трек
     * @throws CommonOnboardingApiException если трек не найден
     */
    Track getTrackOrThrow(Long id);

    /**
     * Возвращает таблицу счета по ее идентификатору.
     *
     * @param id идентификатор табло
     * @return табло
     * @throws CommonOnboardingApiException если табло не найдено
     */
    Scoreboard getScoreboardOrThrow(Long id);

    /**
     * Возвращает прогресс пользователя в модуле по его идентификатору.
     *
     * @param id идентификатор прогресса пользователя в модуле
     * @return прогресс пользователя в модуле
     * @throws CommonOnboardingApiException если прогресс пользователя в модуле не найден
     */
    UserProgressInModule getUserProgressInModuleOrThrow(Long id);

    /**
     * Возвращает базу знаний по ее идентификатору.
     *
     * @param id идентификатор базы знаний
     * @return база знаний
     * @throws CommonOnboardingApiException если база знаний не найдена
     */
    KnowledgeBase getKnowledgeBaseOrThrow(Long id);
}
