package JWTLogIn.JWT.user.entity;

import JWTLogIn.JWT.post.entity.PostEntity;
import JWTLogIn.JWT.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 점직적 증가
    private Long id; // 기본 키.

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String status; // 재학/휴학 상태

    @Column(nullable = false, length = 3)
    private String semester; // 학년, 학기(3글자로 설정)

    @Column(name = "phone_number", nullable = false, unique = true, length = 13)
    private String phoneNumber; // 전화번호(13글자로 설정)

    // 연관매핑: 일대다
    // 참조 당하는 엔티티에서 사용
    // mappedBy - 양방향 매핑 시 어떤 변수로 참조되었는지 알려주는
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JoinColumn(name="student_id")
    private List<PostEntity> posts = new ArrayList<>(); // 연관매핑 아직 공부해야함.

    public static UserEntity toUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .studentId(userDTO.getStudentId())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .status(userDTO.getStatus())
                .semester(userDTO.getSemester())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
    }
}
