package com.example.backend.security;

import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    // GENERATE TOKEN

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, refreshTokenExpiration);
    }

    private String buildToken(UserDetails userDetails, long expiration) {
        return Jwts.builder().subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities()).issuedAt(new Date()) // thời gian khởi
                                                                                   // tạo
                .expiration(new Date(System.currentTimeMillis() + expiration))// thời gian hết hạn
                .signWith(getSignKey()) // kí bằng chữ kí bí mật
                .compact();// tạo thành chuoif jwt
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        // so sánh ngày hết hạn vs hiện tại
        return extractExpiration(token).before(new Date());
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // lấy 1 thông tin(claim) bên trong token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignKey()) // mở khóa = chữ kí
                .build().parseSignedClaims(token)// parse token
                .getPayload(); // lấy dữ liệu
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isRefreshToken(String token) {
        try {
            long expiration = extractExpiration(token).getTime();
            long now = System.currentTimeMillis();
            // Nếu thời gian sống > 1 ngày → coi là refresh token
            return (expiration - now) > 86400000; // 24h
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
