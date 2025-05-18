package suai.vladislav.onboardingapi.audit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import suai.vladislav.onboardingapi.model.AuditLog;
import suai.vladislav.onboardingapi.repository.AuditLogRepository;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Pointcut("execution(* suai.vladislav.onboardingapi.service.implementations.*.add*(..)) || " +
            "execution(* suai.vladislav.onboardingapi.service.implementations.*.update*(..)) || " +
            "execution(* suai.vladislav.onboardingapi.service.implementations.*.delete*(..))")
    public void auditableServiceMethods() {}

    @AfterReturning(pointcut = "auditableServiceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        Object arg = joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null;

        String username = "anonymous";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            username = auth.getName();
        }

        String action = joinPoint.getSignature().getName();
        String entityName = (arg != null) ? arg.getClass().getSimpleName() : "Unknown";
        String entityId = null;

        if (arg != null) {
            try {
                Field idField = Arrays.stream(arg.getClass().getDeclaredFields())
                        .filter(f -> f.getName().equalsIgnoreCase("id"))
                        .findFirst().orElse(null);
                if (idField != null) {
                    idField.setAccessible(true);
                    Object idValue = idField.get(arg);
                    if (idValue != null) entityId = idValue.toString();
                }
            } catch (Exception ignored) {}
        }

        StringBuilder details = new StringBuilder();
        details.append("Args: ").append(arg);
        if (result != null) {
            details.append("; Result: ").append(result);
        }

        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details.toString());

        auditLogRepository.save(log);
    }

    // Для delete*(Long id)
    @After("execution(* suai.vladislav.onboardingapi.service.implementations.*.delete*(Long)) && args(id)")
    public void logDeleteById(JoinPoint joinPoint, Long id) {
        String username = "anonymous";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            username = auth.getName();
        }

        String action = joinPoint.getSignature().getName();
        String entityName = "Unknown";
        String entityId = id != null ? id.toString() : null;

        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setTimestamp(LocalDateTime.now());
        log.setDetails("delete by id");

        auditLogRepository.save(log);
    }
}