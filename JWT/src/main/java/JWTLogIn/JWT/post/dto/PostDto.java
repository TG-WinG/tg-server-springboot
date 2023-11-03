package JWTLogIn.JWT.post.dto;

import JWTLogIn.JWT.post.entity.PostEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {
    private String author;
    private String title;
    private String description;
    private String picture;

    public static PostEntity toEntity(PostDto postDto) {
        return PostEntity.builder()
                .author(postDto.author)
                .title(postDto.title)
                .description(postDto.description)
                .picture(postDto.picture).build();
    }
}
