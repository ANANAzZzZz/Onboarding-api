package suai.vladislav.onboardingapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Entity
@Table(name = "Page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page extends BaseModel implements Serializable {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer orderInModule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moduleId")
    @JsonBackReference("module-page")
    private Module module;
}
