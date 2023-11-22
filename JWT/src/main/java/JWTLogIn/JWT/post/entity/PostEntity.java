package JWTLogIn.JWT.post.entity;

import JWTLogIn.JWT.post.dto.PostDto;
import JWTLogIn.JWT.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @JoinColumn(name = "author", referencedColumnName = "name")
//    private UserEntity user;
    private String author;
    @UpdateTimestamp
    @Column(name = "written_date")
    private LocalDateTime writtenDate;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private String picture;
    @Column(name = "recommand_num")
    private int recommandNum;
    @Column(name = "is_admin")
    private Boolean isAdmin;

    // 연관매핑: 다대일
    // 참조하는(외래키를 가진) 엔티티에서 사용
    // FetchType (EAGER: 객체가 사용되지 않아도 조회 & 조회 시 연관 엔티티 한 번에 조회
    //            LAZY: 엔티티 사용 시점에 조회)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="student_id", referencedColumnName = "student_id")
    private UserEntity user;

    public static PostDto toDto(PostEntity postEntity) {
        return PostDto.builder()
                .author(postEntity.author)
                .title(postEntity.title)
                .description(postEntity.description)
                .picture(postEntity.picture).build();
    }

    public void updateContent(PostDto postDto) {
        this.author = this.user.getName();
        this.title = postDto.getTitle();
        this.description = postDto.getDescription();
        this.picture = postDto.getPicture();
    }
}
