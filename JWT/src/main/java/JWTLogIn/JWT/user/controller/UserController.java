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

    @GetMapping
    public ResponseEntity<Void> mainPage() {

        return ResponseEntity.ok().build();
    } // main 페이지. 추후 구현함. JWT 필요.(로그인 이전, 이후때문에)

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
    public ResponseEntity<String> login(@RequestBody LogInDTO logInDTO) {
        String key = userService.login(logInDTO);
        if(key == null) { // null값일 경우 회원 정보를 못찾은 것임.
            System.out.println("로그인 실패. 회원정보 불일치");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        System.out.println("로그인 성공.");
        return ResponseEntity.ok().body(key);
    } // 회원 로그인

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        return ResponseEntity.ok().build();
    } // 회원 로그아웃. 추후 구현함. JWT 필요

    @DeleteMapping("/profile/delete/{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id) {
        if(userService.withdrawalUser(id)) {
            System.out.println("회원 삭제 완료");
            return ResponseEntity.ok().build();
        }
        System.out.println("회원을 찾을 수 없음.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }// 회원 탈퇴

}
