package JWTLogIn.JWT.user.repository;

import JWTLogIn.JWT.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByStudentId(String studentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity U WHERE U.id = :id")
    void deleteUser(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET " +
            "u.level = :level " +
            "WHERE u.id = :id")
    void changeLv (@Param("id") Long id, @Param("level") String level);


//    @Query("UPDATE BoardEntity b SET " +
//            "b.boardCurrentStudents = :boardCurrentStudents " +
//            "WHERE b.boardId = :boardId")

    Page<UserEntity> findAll(Pageable pageable);
}
