package suai.vladislav.onboardingapi.auth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import suai.vladislav.onboardingapi.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}
