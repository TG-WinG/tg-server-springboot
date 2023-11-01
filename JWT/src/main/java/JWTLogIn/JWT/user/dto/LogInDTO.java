package JWTLogIn.JWT.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LogInDTO {
    private String studentId;
    private String password;
}
