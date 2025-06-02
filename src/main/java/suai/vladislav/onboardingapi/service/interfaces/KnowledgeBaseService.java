package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.KnowledgeBaseDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с базой знаний.
 */
public interface KnowledgeBaseService {

    /**
     * Получает список всех баз знаний.
     *
     * @return список баз знаний
     */
    List<KnowledgeBaseDto> getKnowledgeBases();

    /**
     * Получает базу знаний по идентификатору.
     *
     * @param id идентификатор базы знаний
     * @return база знаний
     */
    KnowledgeBaseDto getKnowledgeBaseById(Long id);

    /**
     * Добавляет новую базу знаний.
     *
     * @param knowledgeBaseDto DTO базы знаний
     * @return добавленная база знаний
     */
    KnowledgeBaseDto addKnowledgeBase(KnowledgeBaseDto knowledgeBaseDto);

    /**
     * Обновляет существующую базу знаний.
     *
     * @param knowledgeBaseDto DTO базы знаний
     * @return обновленная база знаний
     */
    KnowledgeBaseDto updateKnowledgeBase(KnowledgeBaseDto knowledgeBaseDto);

    /**
     * Удаляет базу знаний по идентификатору.
     *
     * @param id идентификатор базы знаний
     */
    void deleteKnowledgeBase(Long id);
}
