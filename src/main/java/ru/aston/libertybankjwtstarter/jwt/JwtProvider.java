package ru.aston.libertybankjwtstarter.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aston.libertybankjwtstarter.config.JwtProperties;


@Slf4j
@Component
public class JwtProvider {


  private final JwtProperties key;

  @Autowired
  public JwtProvider (JwtProperties key){
    this.key = key;
  }

//  @Value("${application.security.jwt.expiration-session}")
//  private long jwtSessionExpiration;
//  @Value("${application.security.jwt.expiration-access}")
//  private long jwtAccessExpiration;
//  @Value("${application.security.jwt.expiration-refresh}")
//  private long jwtRefreshExpiration;

//  /**
//   * Method to generate Session token.
//   *
//   * @param customerId User id (required),
//   * @return A string as a hashed token
//   */
//  public String generateSessionToken(UUID customerId) {
//    log.info("Creating new session token");
//    return buildToken(new HashMap<>(), customerId, jwtSessionExpiration);
//  }
//
//  /**
//   * Method to generate Access token.
//   *
//   * @param customerId User id (required),
//   * @return A string as a hashed token
//   */
//  public String generateAccessToken(UUID customerId) {
//    log.info("Creating new access token");
//    return buildToken(new HashMap<>(), customerId, jwtAccessExpiration);
//  }
//
//  /**
//   * Method to generate Refresh token.
//   *
//   * @param customerId User id (required),
//   * @return A string as a hashed token
//   */
//  public String generateRefreshToken(UUID customerId) {
//    log.info("Creating new refresh token");
//    return buildToken(new HashMap<>(), customerId, jwtRefreshExpiration);
//  }

  /**
   * Extract UUID User from token.
   *
   * @param token String as a hashed token (required)
   * @return UUID User from token as string
   */
  public UUID extractCustomerId(String token) {
    log.info("Extract customer id");
    return UUID.fromString(extractClaim(token, Claims::getSubject));
  }

  /**
   * Check token for expired.
   *
   * @param token String as a hashed token (required)
   */
  public void checkTokenExpired(String token) {
    log.info("Check tokens for expired");
    Date extractExpiration = extractExpiration(token);
    log.debug("Token expiration: {}", extractExpiration);
  }

  /**
   * Extract token data expiration.
   *
   * @param token String as a hashed token (required)
   * @return Date expiration
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

//  /**
//   * Build new token.
//   *
//   * @param customerId  User id (required),
//   * @param expiration  Token validity period (required),
//   * @param extraClaims the JWT claims to be set as the JWT body
//   * @return A string as a hashed token
//   */
//  private String buildToken(Map<String, Object> extraClaims, UUID customerId, long expiration) {
//    return Jwts.builder()
//        .setClaims(extraClaims)
//        .setSubject(customerId.toString())
//        .setIssuedAt(new Date())
//        .setExpiration(new Date(System.currentTimeMillis() + expiration))
//        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//        .compact();
//  }

  /**
   * Extract all claims.
   *
   * @param token String as a hashed token (required)
   * @return Returns the JWT body, either a Claims instance.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Extract claims as the function result.
   *
   * @param token          String as a hashed token (required)
   * @param <T>            The type of the result of the function
   * @param claimsResolver Function of claims resolver
   * @return The function result
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Get the signing key based on the secret key.
   *
   * @return sign key
   */
  private Key getSignInKey() {
    //log.info("{}",key);
    byte[] keyBytes = Decoders.BASE64.decode(key.getSecret());
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

