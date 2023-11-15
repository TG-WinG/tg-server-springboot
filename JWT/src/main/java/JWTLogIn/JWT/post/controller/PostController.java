package JWTLogIn.JWT.post.controller;

import JWTLogIn.JWT.post.dto.PostDto;
import JWTLogIn.JWT.post.entity.PostEntity;
import JWTLogIn.JWT.post.repository.PostRepository;
import JWTLogIn.JWT.post.service.PostService;
import JWTLogIn.JWT.user.entity.UserEntity;
import JWTLogIn.JWT.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static JWTLogIn.JWT.post.entity.PostEntity.toDto;

@RestController
@RequestMapping("/tgwing.kr")
@RequiredArgsConstructor
public class PostController {

    /*
     * 공지글 가져오기 - GET, /tgwing.kr/info/notice
     * 특정 공지글 가져오기 - GET, /tgwing.kr/info/notice/{id}
     * 공지 내용 검색 - GET, /tgwing.kr/notice/search?text={search}
     * 공지 작성 - POST, /tgwing.kr/notice/post
     * 공지 수정 - PUT, /tgwing.kr/notice/put/{id}
     * 공지 삭제 - DELETE, /tgwing.kr/notice/delete/{id}
     * */
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @CrossOrigin
    @GetMapping("/info/notice") // 공지글 가져오기 - GET, /tgwing.kr/info/notice
    public ResponseEntity<List<PostDto>> getAllPosts() {
        System.out.println("-- Retrieve All of Posts --");

        // post.isAdmin() = 1이면 관리자가 올린 글, 아니면 일반인이 올린 글 ( != 공지)
        // DB에서 모든 유저 가져와서 각 유저마다 가지고 있는 post list를 가져옴
        // one user의 post list에서 isAdmin이 1인 post를 모두 보여주기

        List<UserEntity> allUser = userRepository.findAll();

        List<PostDto> allPostDto = new ArrayList<>();

        // 모든 유저에 대해서 : 모든 글에 대한 정보 모아서, list에 추가
        for (UserEntity user : allUser) {
            List<PostDto> userPostDtos = postService.getPostsByUserId(user.getId());
            allPostDto.addAll(userPostDtos);
        }

        // 모든 유저에 대해서 : 모든 글에 대해서 list에 추가
        return new ResponseEntity<>(allPostDto, HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/info/notice/{postId}") // 특정 공지글 가져오기 - GET, /tgwing.kr/info/notice/{id}
    public ResponseEntity<PostDto> getPost(@PathVariable("postId") Long postId) {
        System.out.println("-- Retreive Specific Post by Id --");
        // 특정 게시글 id에 대한 post 정보를 모아 반환 (id, title, description, writtenDate, ?조회수?

        PostDto post = postService.getPost(postId);
        return ResponseEntity.ok(post);

    }
    @CrossOrigin
    @GetMapping("/notice/search") // 공지 내용 검색 - GET, /tgwing.kr/notice/search?text={search}
    public ResponseEntity<List<PostDto>> searchPost(@RequestParam String text)
    {
        System.out.println("-- Retrieve Posts which description has search text --");

        // 입력 파라미터로 넘어온 텍스트를 내용으로 갖고 있는 post를 찾아서 반환
        // 검색 결과 복수개 가능 - List로 전달
        List<PostDto> postDtos = postService.searchPosts(text);
        return ResponseEntity.ok(postDtos);
    }
    @CrossOrigin
    @PostMapping("/notice/post/{userId}") // 공지 작성 - POST, /tgwing.kr/notice/post
    public ResponseEntity<PostDto> post(@RequestBody PostDto postDto,
                                        @PathVariable("userId") Long userId)
    {
        System.out.println("-- Post new post --");
        System.out.println("postDto = " + postDto);
        // 가져오는 값 : 제목, 내용, 사진url
        // dto 가져온 값을 엔티티로 바꾸고 DB에 save
        // 다시 꺼내와서 완성된 객체의 구성요소(id, title, description, writtenDate ...)

        PostEntity post = postService.createPost(postDto, userId);
        PostDto returnDto = toDto(post);
        return ResponseEntity.ok(returnDto);
    }
    @CrossOrigin
    @PutMapping("/notice/put/{id}") // 공지 수정 - PUT, /tgwing.kr/notice/put/{id}
    public ResponseEntity<PostDto> modify(@RequestBody PostDto postDto,
                                       @PathVariable("id") Long id)
    {
        System.out.println("-- Modify (title + description) of post --");
        // repository에 대해서 해당 id를 가진 엔티티를 가져오고,
        // 그 엔티티의 내용을 dto 내용으로 수정 및 다시 repository에 저장한다

        System.out.println("postDto = " + postDto);
        PostEntity updatedEntity = postService.updatePost(postDto, id);
        PostDto updatedDto = toDto(updatedEntity);

        return ResponseEntity.ok(updatedDto);
    }

    @CrossOrigin
    @DeleteMapping("/notice/delete/{id}") // 공지 삭제 - DELETE, /tgwing.kr/notice/delete/{id}
    public ResponseEntity<Void> delete(@PathVariable("id") Long id)
    {
        System.out.println("-- Delete Specific Post --");
        // 아이디에 해당하는 글 객체를 그냥 삭제 -> 응답
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }




}
