package suai.vladislav.onboardingapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    USER_NOT_FOUND("Пользователь не найден, id=%s", HttpStatus.NOT_FOUND),
    SURVEY_NOT_FOUND("Опрос не найден, id=%s", HttpStatus.NOT_FOUND),
    KNOWLEDGE_BASE_NOT_FOUND("База знаний не найдена, id=%s", HttpStatus.NOT_FOUND),
    SCOREBOARD_NOT_FOUND("Таблица счета не найдена, id=%s", HttpStatus.NOT_FOUND),
    TRACK_NOT_FOUND("Трек не найден, id=%s", HttpStatus.NOT_FOUND),
    MODULE_NOT_FOUND("Модуль не найден, id=%s", HttpStatus.NOT_FOUND),
    PAGE_NOT_FOUND("Страница не найдена, id=%s", HttpStatus.NOT_FOUND),
    USER_PROGRESS_IN_MODULE_NOT_FOUND("Прогресс пользователя в модуле не найден, id=%s", HttpStatus.NOT_FOUND),
    ACHIEVEMENT_NOT_FOUND("Достижение с id = {} не найдено", HttpStatus.NOT_FOUND),

    INVALID_ACHIEVEMENT_CONDITION("Неверный формат условия для Достижения", HttpStatus.BAD_REQUEST),

    USER_ALREADY_EXISTS("Пользователь уже существует, email=%s", HttpStatus.CONFLICT),
    SCOREBOARD_ALREADY_EXISTS("Таблица счцета с таким пользователем уже существует, userId=%s",
        HttpStatus.CONFLICT),

    WRONG_CREDENTIALS("Неверный логин или пароль", HttpStatus.UNAUTHORIZED),

    ID_IS_MISSING("Не передан id сущности", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
