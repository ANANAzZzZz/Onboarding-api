package suai.vladislav.onboardingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import suai.vladislav.onboardingapi.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
