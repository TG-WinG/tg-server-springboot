package JWTLogIn.JWT.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post_table")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관매핑: 다대일
    // 참조하는(외래키를 가진) 엔티티에서 사용
    // FetchType (EAGER: 객체가 사용되지 않아도 조회 & 조회 시 연관 엔티티 한 번에 조회
    //            LAZY: 엔티티 사용 시점에 조회)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="student_id", referencedColumnName = "studentId")
//    private UserEntity user;

}
