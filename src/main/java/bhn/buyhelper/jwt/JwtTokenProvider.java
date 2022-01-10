// package bhn.buyhelper.jwt;

// import java.time.LocalDate;
// import java.util.Date;

// import org.springframework.stereotype.Component;

// import bhn.buyhelper.service.impl.CustomUserDetails;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.UnsupportedJwtException;
// import lombok.extern.slf4j.Slf4j;
// import net.bytebuddy.asm.Advice.Local;

// @Component
// @Slf4j
// public class JwtTokenProvider {
// private final String JWT_SECRET = "huy";
// private final long JWT_EXPIRATION = 604800000L;

// public String generateToken(CustomUserDetails userDetails) {
// Date now = new Date();
// Date expDate = new Date(now.getTime() + JWT_EXPIRATION);
// // Tạo chuỗi json web token từ id của user.
// return Jwts.builder()
// .setSubject(userDetails.getAccount().getUsername())
// .setIssuedAt(now)
// .setExpiration(expDate)
// .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
// .compact();
// }

// public String getUserIdFromJWT(String token) {
// Claims claims = Jwts.parser()
// .setSigningKey(JWT_SECRET)
// .parseClaimsJws(token)
// .getBody();

// return claims.getSubject();
// }

// public boolean validateToken(String authToken) {
// try {
// Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
// return true;
// } catch (MalformedJwtException ex) {
// log.error("Invalid JWT token");
// } catch (ExpiredJwtException ex) {
// log.error("Expired JWT token");
// } catch (UnsupportedJwtException ex) {
// log.error("Unsupported JWT token");
// } catch (IllegalArgumentException ex) {
// log.error("JWT claims string is empty.");
// }
// return false;
// }
// }