package JWTLogIn.JWT.user.controller;

import JWTLogIn.JWT.user.dto.UserDTO;
import JWTLogIn.JWT.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tgwing.kr")
public class AdminController {
    private final UserService userService;
    @GetMapping("/info/user")
    public ResponseEntity<List<UserDTO>> userAll() {
        List<UserDTO> userAll = userService.findUserAll();
        if(userAll == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userAll);
    }

//    @GetMapping("/tgwing.kr/userlist")
//    public ResponseEntity<List<UserDTO>> userPage(@RequestParam(value = "page", defaultValue = "1") Integer page, Pageable pageable) {
//
//        return
//    }// 페이지마다의 회원 정보
//
//    @PutMapping("/tgwing.kr/userlist/put/manager/{userId}")
//    public ResponseEntity<UserDTO> changeAdmin(@PathVariable Long userId) {
//
//        return
//    }// ??->관리자로 변경
//
//    @PutMapping("/tgwing.kr/userlist/put/normal/{userId}")
//    public ResponseEntity<UserDTO> changeNormal(@PathVariable Long userId) {
//
//        return
//    }// ??->일반으로 변경
    // 관리자, 동아리원, 일반 3가지 형태로 구분해야함.




}
