package JWTLogIn.JWT.user.dto;

import JWTLogIn.JWT.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {

    private Long id;
    private String studentId; // 아이디
    private String password; // 비밀번호
    private String name; // 이름
    private String status; // 재학/휴학 상태
    private String semester; // 학년, 학기(3글자로 설정)
    private String phoneNumber; // 전화번호(13글자로 설정)

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();

        userDTO.setStudentId(userEntity.getStudentId());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setName(userEntity.getName());
        userDTO.setStatus(userEntity.getStatus());
        userDTO.setSemester(userEntity.getSemester());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());

        return userDTO;
    }
}
