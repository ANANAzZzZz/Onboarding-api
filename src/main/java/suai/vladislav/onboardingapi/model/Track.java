package suai.vladislav.onboardingapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Track")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedEntityGraph(
    name = "track-with-modules-and-pages",
    attributeNodes = @NamedAttributeNode(value = "modules", subgraph = "modulesWithPages"),
    subgraphs = @NamedSubgraph(
        name = "modulesWithPages",
        attributeNodes = @NamedAttributeNode("pages")
    )
)
public class Track extends BaseModel {
    @Column(nullable = false)
    private String name;

    @JsonManagedReference("module-track")
    @OneToMany(mappedBy = "track", fetch = FetchType.LAZY)
    private Set<Module> modules;
}
