package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.SecureUserDto;
import suai.vladislav.onboardingapi.dto.SurveyXUserDto;

import java.util.List;

public interface SurveyXUserService {

    List<SecureUserDto> getUsersForSurvey(Long surveyId);

    SurveyXUserDto addUserToSurvey(Long surveyId, Long userId);

    void deleteUserFromSurvey(Long surveyId, Long userId);
}
