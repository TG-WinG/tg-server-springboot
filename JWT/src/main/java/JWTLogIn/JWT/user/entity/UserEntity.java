package JWTLogIn.JWT.user.entity;

import JWTLogIn.JWT.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_table")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 점직적 증가
    private Long id; // 기본 키.

    @Column(nullable = false, unique = true)
    private String studentId; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String status; // 재학/휴학 상태

    @Column(nullable = false, length = 3)
    private String semester; // 학년, 학기(3글자로 설정)

    @Column(nullable = false, unique = true, length = 13)
    private String phoneNumber; // 전화번호(13글자로 설정)

//    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private PostEntity post; // 연관매핑 아직 공부해야함.

    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setStudentId(userDTO.getStudentId());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setName(userDTO.getName());
        userEntity.setStatus(userDTO.getStatus());
        userEntity.setSemester(userDTO.getSemester());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());

        return userEntity;
    }
}
