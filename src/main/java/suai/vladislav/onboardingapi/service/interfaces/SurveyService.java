package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.SurveyDto;

import java.util.List;

public interface SurveyService {

    List<SurveyDto> getSurveys();

    SurveyDto getSurveyById(Long id);

    SurveyDto addSurvey(SurveyDto surveyDto);

    SurveyDto updateSurvey(SurveyDto surveyDto);

    void deleteSurvey(Long id);
}
