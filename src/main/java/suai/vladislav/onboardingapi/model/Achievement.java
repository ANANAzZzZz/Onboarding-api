package suai.vladislav.onboardingapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import suai.vladislav.onboardingapi.enums.ActionType;

import java.util.List;

@Entity
@Table(name = "Achievement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Achievement extends BaseModel {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column(nullable = false)
    private String condition;

    @Column(nullable = false)
    private Integer pointsReward;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "AchievementXUser",
        joinColumns = @JoinColumn(name = "AchievementId"),
        inverseJoinColumns = @JoinColumn(name = "UserId")
    )
    private List<User> users;
}
