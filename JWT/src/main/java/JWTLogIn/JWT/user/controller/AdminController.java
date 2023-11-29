package JWTLogIn.JWT.user.controller;

import JWTLogIn.JWT.user.dto.UserDTO;
import JWTLogIn.JWT.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tgwing.kr")
public class AdminController {
    private final UserService userService;
    @GetMapping("/info/user")
    public ResponseEntity<List<UserDTO>> userAll(Authentication authentication) {
        List<UserDTO> userAll = userService.findUserAll();
        if(userAll == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        System.out.println("누군가가 회원의 전체목록을 확인했습니다." + authentication.getDetails());

        return ResponseEntity.ok(userAll);
    }

    @GetMapping("/userlist")
    public ResponseEntity<Page<UserDTO>> userPage(Pageable pageable) {
        Page<UserDTO> userAllByPage = userService.findUserAllByPage(pageable);

        return ResponseEntity.ok(userAllByPage);
    }// 페이지마다의 회원 정보

    @PutMapping("/userlist/put/manager/{userId}")
    public ResponseEntity<Void> changeAdmin(@PathVariable Long userId) {
        userService.changeLevel(userId, "Manager");

        return ResponseEntity.ok().build();
    }// ??->관리자로 변경

    @PutMapping("/userlist/put/member/{userId}")
    public ResponseEntity<Void> changeMember(@PathVariable Long userId) {
        userService.changeLevel(userId, "Member");

        return ResponseEntity.ok().build();
    }// ??->동아리로 변경

    @PutMapping("/userlist/put/normal/{userId}")
    public ResponseEntity<Void> changeNormal(@PathVariable Long userId) {
        userService.changeLevel(userId, "Normal");

        return ResponseEntity.ok().build();
    }// ??->일반으로 변경

    // 관리자, 동아리원, 일반 3가지 형태로 구분해야함.




}
