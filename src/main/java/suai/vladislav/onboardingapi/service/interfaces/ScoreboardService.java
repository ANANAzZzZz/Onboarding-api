package suai.vladislav.onboardingapi.service.interfaces;

import suai.vladislav.onboardingapi.dto.ScoreboardDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с таблицей счета.
 */
public interface ScoreboardService {

    /**
     * Получает список всех записей в таблице счета.
     *
     * @return список записей в таблице счета
     */
    List<ScoreboardDto> getScoreboard();

    /**
     * Получает запись в таблице счета по идентификатору.
     *
     * @param id идентификатор записи
     * @return запись в таблице счета
     */
    ScoreboardDto getScoreboardById(Long id);

    /**
     * Добавляет новую запись в таблицу счета.
     *
     * @param scoreboardDto данные новой записи
     * @return добавленная запись в таблице счета
     */
    ScoreboardDto addScoreboard(ScoreboardDto scoreboardDto);

    /**
     * Обновляет существующую запись в таблице счета.
     *
     * @param scoreboardDto данные обновленной записи
     * @return обновленная запись в таблице счета
     */
    ScoreboardDto updateScoreboard(ScoreboardDto scoreboardDto);

    /**
     * Удаляет запись из таблицы счета по идентификатору.
     *
     * @param id идентификатор записи
     */
    void deleteScoreboard(Long id);
}
