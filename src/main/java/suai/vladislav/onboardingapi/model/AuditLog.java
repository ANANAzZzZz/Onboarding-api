package suai.vladislav.onboardingapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "AuditLog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String action;
    private String entityName;
    private String entityId;
    private LocalDateTime timestamp;

    @Lob
    private String details;
}
