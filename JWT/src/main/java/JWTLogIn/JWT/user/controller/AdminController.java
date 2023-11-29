package JWTLogIn.JWT.user.controller;

import JWTLogIn.JWT.user.dto.UserDTO;
import JWTLogIn.JWT.user.security.JwtUtil;
import JWTLogIn.JWT.user.service.AuthService;
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
    private final AuthService authService;

    @GetMapping("/info/user")
    public ResponseEntity<List<UserDTO>> userAll(Authentication authentication) {
        List<UserDTO> userAll = userService.findUserAll();
        if(userAll == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(userAll);
    }

    @GetMapping("/userlist")
    public ResponseEntity<List<UserDTO>> userPage(Pageable pageable, @RequestHeader("authorization") String token) {
        String jwt = token.split(" ")[1];
        String level = authService.extractLevel(jwt);

        if(!level.equals("Manager")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<UserDTO> userAllByPage = userService.findUserAllByPage(pageable);

        return ResponseEntity.ok(userAllByPage.getContent());
    }// 페이지마다의 회원 정보

    @PutMapping("/userlist/put/manager/{userId}")
    public ResponseEntity<Void> changeAdmin(@PathVariable Long userId, @RequestHeader("authorization") String token) {
        String jwt = token.split(" ")[1];
        String level = authService.extractLevel(jwt);

        if(!level.equals("Manager")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.changeLevel(userId, "Manager");

        return ResponseEntity.ok().build();
    }// ??->관리자로 변경

    @PutMapping("/userlist/put/member/{userId}")
    public ResponseEntity<Void> changeMember(@PathVariable Long userId, @RequestHeader("authorization") String token) {
        String jwt = token.split(" ")[1];
        String level = authService.extractLevel(jwt);

        if(!level.equals("Manager")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.changeLevel(userId, "Member");

        return ResponseEntity.ok().build();
    }// ??->동아리로 변경

    @PutMapping("/userlist/put/normal/{userId}")
    public ResponseEntity<Void> changeNormal(@PathVariable Long userId, @RequestHeader("authorization") String token) {
        String jwt = token.split(" ")[1];
        String level = authService.extractLevel(jwt);

        if(!level.equals("Manager")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.changeLevel(userId, "Normal");

        return ResponseEntity.ok().build();
    }// ??->일반으로 변경

    // 관리자, 동아리원, 일반 3가지 형태로 구분해야함.




}
