package JWTLogIn.JWT.user.security;

import JWTLogIn.JWT.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Authorization : {}", authorization);
        // slf4j에서 log를 찍어서 확인함.

        if(authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
            log.error("권한이 없음.");
            filterChain.doFilter(request, response);
            return;
        }
        // authentication이 없을 경우, token이 없을 경우, 혹은 "Bearer "로 시작하지 않으면 block함.
        // 이 때 header의 authorization의 부분에서 어떤 입력이라도 있으면 200이 됨.



        String token = authorization.split(" ")[1];
        // 토큰 꺼내기
        // authorization의 첫번째 부분이 토큰이다.
        // 띄워쓰기를 쪼개는 방식으로 가져갈 때, Bearer다음의 부분이 토큰이다.

        // 토큰 유효기간 확인
        if(JwtUtil.isExpired(token, secretKey)) {
            log.error("토큰이 만료됨.");
            filterChain.doFilter(request, response);
            return;
        }


        //Username Token에서 꺼내기
        //이를 통해 아래 UsernamePasswordAuthenticationToken에서 userName을 사용가능함.
        String name = JwtUtil.getUserName(token, secretKey);
        String level = JwtUtil.getLevel(token, secretKey);

        log.info("name : {}", name);
        log.info("level : {}", level);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(name, null,
                        List.of(new SimpleGrantedAuthority("name")));
        // Detail을 넣어줌
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        authenticationToken.setDetails(level);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }// 관문. 이곳을 통과해서 지나감.
}// 권한을 부여해줌. 본래 authenticate를 통과해야하는 상태를 통과하게 해줌.
