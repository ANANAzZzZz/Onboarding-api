package suai.vladislav.onboardingapi.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suai.vladislav.onboardingapi.dto.KnowledgeBaseDto;
import suai.vladislav.onboardingapi.enums.ErrorType;
import suai.vladislav.onboardingapi.exception.CommonOnboardingApiException;
import suai.vladislav.onboardingapi.mapper.KnowledgeBaseMapper;
import suai.vladislav.onboardingapi.model.KnowledgeBase;
import suai.vladislav.onboardingapi.repository.KnowledgeBaseRepository;
import suai.vladislav.onboardingapi.service.interfaces.KnowledgeBaseService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeBaseRepository knowledgeBaseRepository;

    private final KnowledgeBaseMapper knowledgeBaseMapper;

    @Override
    public List<KnowledgeBaseDto> getKnowledgeBases() {
        log.info("вызван getKnowledgeBases");

        return knowledgeBaseRepository.findAll().stream()
            .map(knowledgeBaseMapper::toDto)
            .toList();
    }

    @Override
    public KnowledgeBaseDto getKnowledgeBaseById(Long id) {
        log.info("вызван getKnowledgeBaseById, id = {}", id);

        return knowledgeBaseMapper.toDto(knowledgeBaseRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.KNOWLEDGE_BASE_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public KnowledgeBaseDto addKnowledgeBase(KnowledgeBaseDto knowledgeBaseDto) {
        log.info("вызван addKnowledgeBase");

        return knowledgeBaseMapper.toDto(
            knowledgeBaseRepository.save(knowledgeBaseMapper.toModel(knowledgeBaseDto))
        );
    }

    @Override
    @Transactional
    public KnowledgeBaseDto updateKnowledgeBase(KnowledgeBaseDto knowledgeBaseDto) {
        log.info("вызван updateKnowledgeBase");

        KnowledgeBase knowledgeBase = knowledgeBaseRepository.findById(knowledgeBaseDto.id())
            .orElseThrow(
                () -> new CommonOnboardingApiException(ErrorType.KNOWLEDGE_BASE_NOT_FOUND, knowledgeBaseDto.id())
            );

        knowledgeBase.setName(knowledgeBaseDto.name());
        knowledgeBase.setContent(knowledgeBaseDto.content());

        knowledgeBaseRepository.save(knowledgeBase);

        return knowledgeBaseMapper.toDto(knowledgeBase);
    }

    @Override
    @Transactional
    public void deleteKnowledgeBase(Long id) {
        log.info("вызван deleteKnowledgeBase");

        KnowledgeBase knowledgeBase = knowledgeBaseRepository.findById(id)
            .orElseThrow(() -> new CommonOnboardingApiException(ErrorType.KNOWLEDGE_BASE_NOT_FOUND, id));

        knowledgeBaseRepository.delete(knowledgeBase);
    }
}
