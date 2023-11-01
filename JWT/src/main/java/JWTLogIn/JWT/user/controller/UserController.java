package JWTLogIn.JWT.user.controller;

import JWTLogIn.JWT.user.dto.LogInDTO;
import JWTLogIn.JWT.user.dto.UserDTO;
import JWTLogIn.JWT.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tgwing.kr")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        System.out.println("User Register");
        System.out.println("userDTO = " + userDTO);
        userService.userSave(userDTO); // 회원 저장

        return ResponseEntity.ok().build();
    } // 회원 저장

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LogInDTO logInDTO) {
        UserDTO login = userService.login(logInDTO);
        if(login == null) { // null값일 경우 회원 정보를 못찾은 것임.
            System.out.println("로그인 실패. 회원정보 불일치");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        System.out.println("로그인 성공.");
        return ResponseEntity.ok(login);
    } // 회원 로그인

    @DeleteMapping("/profile/delete/{id}")
    public ResponseEntity<Void> withdrwal(@PathVariable Long id) {
        if(userService.withdrawalUser(id)) {
            System.out.println("회원 삭제 완료");
            return ResponseEntity.ok().build();
        }
        System.out.println("회원을 찾을 수 없음.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
