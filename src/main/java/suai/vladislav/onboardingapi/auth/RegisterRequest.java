package suai.vladislav.onboardingapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 1, max = 200)
    private String firstname;

    @NotBlank
    @Size(min = 1, max = 200)
    private String lastname;

    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 200)
    private String password;
}