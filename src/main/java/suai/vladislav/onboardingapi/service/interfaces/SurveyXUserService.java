package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.SecureUserDto;
import suai.vladislav.onboardingapi.dto.SurveyXUserDto;

import java.util.List;


/**
 * Интерфейс сервиса для связи между опросами и пользователями.
 */
public interface SurveyXUserService {

    /**
     * Получает список пользователей для определенного опроса.
     *
     * @param surveyId идентификатор опроса
     * @return список пользователей опроса
     */
    List<SecureUserDto> getUsersForSurvey(Long surveyId);

    /**
     * Добавляет пользователя в опрос.
     *
     * @param surveyId идентификатор опроса
     * @param userId   идентификатор пользователя
     * @return объект SurveyXUserDto, представляющий связь между опросом и пользователем
     */
    SurveyXUserDto addUserToSurvey(Long surveyId, Long userId);

    /**
     * Удаляет пользователя из опроса.
     *
     * @param surveyId идентификатор опроса
     * @param userId   идентификатор пользователя
     */
    void deleteUserFromSurvey(Long surveyId, Long userId);
}
