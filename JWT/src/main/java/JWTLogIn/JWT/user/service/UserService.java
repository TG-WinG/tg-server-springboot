package JWTLogIn.JWT.user.service;

import JWTLogIn.JWT.user.dto.LogInDTO;
import JWTLogIn.JWT.user.dto.UserDTO;
import JWTLogIn.JWT.user.entity.UserEntity;
import JWTLogIn.JWT.user.repository.UserRepository;
import JWTLogIn.JWT.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserRepository userRepository;
    public void userSave(UserDTO userDTO) {
        UserEntity userEntity = UserDTO.toUserEntity(userDTO);
        userRepository.save(userEntity);
    } // 회원 정보 저장

    public String login(LogInDTO logInDTO) {
        Optional<UserEntity> userEntity = userRepository.findByStudentId(logInDTO.getStudentId());
        if(userEntity.isPresent()) { // 학번을 통해 찾은 user의 정보가 존재한다면
            if(userEntity.get().getPassword().equals(logInDTO.getPassword())) {
                return JwtUtil.createJwt(userEntity.get().getName(), userEntity.get().getStudentId(), secretKey);
            }
            else { // password 일치하지 않을 경우
                return null;
            }
        }
        else { // user의 학번이 Entity에 없는 경우
            return null;
        }
    }// login. null일 경우 회원정보 불일치함. 아닐 경우, 회원정보 일치. 회원 정보 return.

    public Boolean withdrawalUser(Long id) {
        Optional<UserEntity> find = userRepository.findById(id);
        if(find != null) { // 회원이 있으면 null이 아님. 이를 삭제하고 true보내서 삭제 완료를 보냄.
            userRepository.deleteUser(id);
            return true;
        }
        else { // 회원이 없으면 null임. id에 해당하는 회원이 없음을 false로 알림.
            return false;
        }
    }// 회원 삭제

    public List<UserDTO> findUserAll() {
        List<UserEntity> allUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for(UserEntity userEntity : allUser) {
            if(userEntity != null){
                UserDTO userDTO = UserEntity.toUserDTO(userEntity);
                userDTOList.add(userDTO);
            }
            else
                break;
        }
        return userDTOList;
    }// 모든 회원 찾기
}
