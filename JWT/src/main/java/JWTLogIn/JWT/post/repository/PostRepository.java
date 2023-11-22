package JWTLogIn.JWT.post.repository;

import JWTLogIn.JWT.post.dto.PostDto;
import JWTLogIn.JWT.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findById(Long id);

    @Override
    <S extends PostEntity> S save(S entity);

//    List<PostEntity> findByTitleContains(String search);
    List<PostEntity> findByDescriptionContains(String search);
    Page<PostEntity> findAllByOrderByIdDesc(Pageable pageable);
}
