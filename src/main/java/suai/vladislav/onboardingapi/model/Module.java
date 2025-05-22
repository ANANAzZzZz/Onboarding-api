package suai.vladislav.onboardingapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Module")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Module extends BaseModel {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String startContent;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String endContent;

    @Column(nullable = false)
    private Integer orderInTrack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "module-track")
    @JoinColumn(name = "trackId")
    private Track track;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    @JsonManagedReference("module-page")
    private Set<Page> pages;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    @JsonManagedReference("module-user-progress")
    private List<UserProgressInModule> userProgressInModules;
}
