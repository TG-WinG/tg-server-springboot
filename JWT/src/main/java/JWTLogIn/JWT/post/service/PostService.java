package JWTLogIn.JWT.post.service;

import JWTLogIn.JWT.post.dto.PostDto;
import JWTLogIn.JWT.post.entity.PostEntity;
import JWTLogIn.JWT.post.repository.PostRepository;
import JWTLogIn.JWT.user.entity.UserEntity;
import JWTLogIn.JWT.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static JWTLogIn.JWT.post.dto.PostDto.toEntity;
import static JWTLogIn.JWT.post.entity.PostEntity.toDto;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto getPost(Long id) // 특정 게시글 가져오기
    {
        // 입력된 id에 해당하는 글 찾기
        Optional<PostEntity> postEntityInOp = postRepository.findById(id);
        PostEntity postEntity = postEntityInOp.get();
        // 관리자 여부 확인
        System.out.println(postEntity.getIsAdmin());
        if(postEntity.getIsAdmin())
            return toDto(postEntity);
        else return null;
    }

    @Transactional
    public List<PostDto> getPostsByUserId(Long userId) // 유저 1명에 대한 모든 게시글 가져오기
    {
        List<PostDto> postDtos = new ArrayList<>();
        
        // 입력된 id에 해당하는 유저 찾기
        Optional<UserEntity> userEntityInOp = userRepository.findById(userId);
        UserEntity userEntity = userEntityInOp.get();
        
        // 해당 유저가 작성한 글 모두 가져오기
        List<PostEntity> postEntities = userEntity.getPosts();
        
        // Loop - 관리자 여부 확인해서 내보낼 글만 담기
        for (PostEntity postEntity : postEntities) {
            if (postEntity.getIsAdmin())
                postDtos.add(toDto(postEntity));
            else
                continue;
        }
        // Loop 끝나면 내보내기
        return postDtos;
    }
    public List<PostDto> searchPosts(String search)
    {
        List<PostDto> postDtos = new ArrayList<>();

        List<PostEntity> SearchedEntity = postRepository.findByDescriptionContains(search);
        for (PostEntity postEntity : SearchedEntity) {
            System.out.println("postEntity = " + postEntity);
            postDtos.add(toDto(postEntity));
        }
        return postDtos;
    }

    public PostEntity createPost(PostDto dto, Long userId) // 게시글 생성하기
    {
        // user 조회
        Optional<UserEntity> byId = userRepository.findById(userId);

//        if(byId.isEmpty()) { }
//        else { // user 조회되면, user name을 author로 설정
//            UserEntity userEntity = byId.get();
//            dto.setAuthor(userEntity.getName());
//        }
        UserEntity userEntity = byId.get();
        dto.setAuthor(userEntity.getName());

        System.out.println("dto = " + dto);

        // 연관매핑 미설정된 엔티티
        PostEntity preEntity = toEntity(dto);
        // user-post 연관매핑 재설정
        PostEntity postEntity = PostEntity.builder()
                .author(preEntity.getAuthor())
                .title(preEntity.getTitle())
                .description(preEntity.getDescription())
                .isAdmin(preEntity.getIsAdmin())
                .picture(preEntity.getPicture())
                .user(userEntity)
                .build();

        System.out.println("postEntity = " + postEntity);

        PostEntity savedEntity = postRepository.save(postEntity);
        return savedEntity;
    }
    public PostEntity updatePost(PostDto postDto, Long id) // 게시글 수정하기
    {
        Optional<PostEntity> byId = postRepository.findById(id);
        PostEntity postEntity = byId.get();

        postEntity.updateContent(postDto);

        PostEntity savedEntity = postRepository.save(postEntity);

        return savedEntity;
    }
    public void deletePost(Long postId) // 게시글 삭제하기
    {
        postRepository.deleteById(postId);
    }

    public Page<PostDto> findPostsInPage(int page, int size) {
        // Post DB에서 Page 단위로 가져오기
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PostEntity> postPage = postRepository.findAllByOrderByIdDesc(pageRequest);

        // Post 반환 + DTO로 변환
        List<PostEntity> posts = postPage.getContent();
        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity postEntity : posts) {
            PostDto dto = toDto(postEntity);
            postDtos.add(dto);
            postDtos.toString();
        }

        Page<PostDto> postDtoPage = postPage.map(PostEntity::toDto);

        return postDtoPage;
    }

}
