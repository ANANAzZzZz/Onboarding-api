package suai.vladislav.onboardingapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import suai.vladislav.onboardingapi.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_User")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel implements UserDetails {

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference("user-scoreboard")
    private List<Scoreboard> scoreboards;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference("user-progress-module")
    private List<UserProgressInModule> userProgressInModules;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Survey> surveys = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "AchievementXUser",
        joinColumns = @JoinColumn(name = "UserId"),
        inverseJoinColumns = @JoinColumn(name = "AchievementId")
    )
    private Set<Achievement> achievements;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
    }

    public void removeAchievement(Achievement achievement) {
        this.achievements.remove(achievement);
    }
}
