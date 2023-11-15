package JWTLogIn.JWT.user.repository;

import JWTLogIn.JWT.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByStudentId(String studentId);
    @Override
    Optional<UserEntity> findById(Long userId);

    @Override
    List<UserEntity> findAll();

    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity U WHERE U.id = :id")
    void deleteUser(@Param("id") Long id);  
}
