package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.SurveyDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с опросами.
 */
public interface SurveyService {

    /**
     * Возвращает список всех опросов.
     *
     * @return список опросов
     */
    List<SurveyDto> getSurveys();

    /**
     * Возвращает опрос по его идентификатору.
     *
     * @param id идентификатор опроса
     * @return опрос
     */
    SurveyDto getSurveyById(Long id);

    /**
     * Добавляет новый опрос.
     *
     * @param surveyDto DTO опроса
     * @return добавленный опрос
     */
    SurveyDto addSurvey(SurveyDto surveyDto);

    /**
     * Обновляет существующий опрос.
     *
     * @param surveyDto DTO опроса
     * @return обновленный опрос
     */
    SurveyDto updateSurvey(SurveyDto surveyDto);

    /**
     * Удаляет опрос по его идентификатору.
     *
     * @param id идентификатор опроса
     */
    void deleteSurvey(Long id);
}
