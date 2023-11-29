package JWTLogIn.JWT.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static Boolean isExpired(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }// 현재의 토큰의 만료시간이 현재보다 이전인지 아닌지 확인함.


    public static String getUserName(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
                .getBody().get("name", String.class);
    } // userName 꺼내오기.

    public static String getLevel(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
                .getBody().get("level", String.class);
    } // level 꺼내오기

    public static String createJwt(String name, String studentId, String level, String secretKey) {
        // name : token에 들어있는 것으로 사용함
        // secretKey : 서명

        Claims claims = Jwts.claims();
        // claim안에 내가 담고자 하는 정보 담아두기 가능함.

        claims.put("name", name);
        claims.put("studentId", studentId);
        claims.put("level", level);

        Long expiredMs = 1000 * 60 * 60l;

        return Jwts.builder()
                .setClaims(claims) // jwt 공간안에 claim에 담은 정보 넣기
                .setIssuedAt(new Date(System.currentTimeMillis())) // 보내는 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 유효시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }// 토큰을 생성함. 이를 가지고 회원인지 아닌지를 구분할 수 있음.
}
