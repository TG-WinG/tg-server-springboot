package JWTLogIn.JWT.post.entity;

import JWTLogIn.JWT.post.dto.PostDto;
import JWTLogIn.JWT.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String author;
    @Column(name = "written_date")
    private String writtenDate;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private String picture;
    @Column(name = "recommand_num")
    private int recommandNum;

    // 연관매핑: 다대일
    // 참조하는(외래키를 가진) 엔티티에서 사용
    // FetchType (EAGER: 객체가 사용되지 않아도 조회 & 조회 시 연관 엔티티 한 번에 조회
    //            LAZY: 엔티티 사용 시점에 조회)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", referencedColumnName = "studentId")
    private UserEntity user;

    public static PostDto toDto(PostEntity postEntity) {
        return PostDto.builder()
                .author(postEntity.author)
                .title(postEntity.title)
                .description(postEntity.description)
                .picture(postEntity.picture).build();
    }
}
