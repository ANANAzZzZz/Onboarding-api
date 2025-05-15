package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.KnowledgeBaseDto;
import suai.vladislav.onboardingapi.model.KnowledgeBase;

import java.util.List;

public interface KnowledgeBaseService {

    List<KnowledgeBaseDto> getKnowledgeBases();

    KnowledgeBaseDto getKnowledgeBaseById(Long id);

    KnowledgeBaseDto addKnowledgeBase(KnowledgeBaseDto knowledgeBaseDto);

    KnowledgeBaseDto updateKnowledgeBase(KnowledgeBaseDto knowledgeBaseDto);

    void deleteKnowledgeBase(Long id);
}
