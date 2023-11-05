package JWTLogIn.JWT.user.dto;

import JWTLogIn.JWT.user.entity.UserEntity;
import lombok.*;


@Data
@Builder
public class UserDTO {

    private Long id;
    private String studentId; // 아이디
    private String password; // 비밀번호
    private String name; // 이름
    private String status; // 재학/휴학 상태
    private String semester; // 학년, 학기(3글자로 설정)
    private String phoneNumber; // 전화번호(13글자로 설정)


    public static UserEntity toUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .studentId(userDTO.getStudentId())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .status(userDTO.getStatus())
                .semester(userDTO.getSemester())
                .phoneNumber(userDTO.getPhoneNumber()).build();
    }
}
