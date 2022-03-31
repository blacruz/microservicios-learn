package pe.blacruz.security.jwt;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expirationTime}")
  private Long expirationTime;

  @PostConstruct
  protected void init() {
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
  }

  /**
   * Genera un token JWT con el tiempo de expiracion indicado en las propiedades
   * jwt.expirationTime
   * 
   * @param jti      Id token
   * @param userName
   * @param userId
   * @param fullName
   * @return Token en formato JWT
   */
  public String createToken(String jti, String userName, String userId, String fullName) {
    return createToken(jti, userName, userId, fullName, expirationTime);
  }

  /**
   * Genera un token JWT indicando el tiempo de expiracion en milisegundos
   * 
   * @param jti               Id token
   * @param userName
   * @param userId
   * @param fullName
   * @param expirationTimeAdd tiempo de expiracion adicional en milisegundos
   * @return Token en formato JWT
   */
  public String createToken(String jti, String userName, String userId, String fullName, long expirationTimeAdd) {
    var now = new Date();
    var token = Jwts.builder().claim("uid", userId).claim("name", fullName).setId(jti).setIssuedAt(now)
        .setSubject(userName).setIssuer(fullName).setExpiration(new Date(now.getTime() + expirationTimeAdd))
        .signWith(SignatureAlgorithm.HS256, secret).compact();
    return token;
  }

  /**
   * Valida el token JWT, si puede ser parseado con la clave secreta o si ha
   * expirado
   * 
   * @param token
   * @return
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException ex) {
      logger.warn("Token expirado " + ex.getMessage());
      return false;
    } catch (SignatureException ex) {
      logger.warn("Firma inválida " + ex.getMessage());
      return false;
    } catch (Exception ex) {
      logger.warn("Otras causas de invalidez de token " + ex.getMessage());
      return false;
    }
  }

  /**
   * Obtiene el id de usuario desde un token proporcionado, previa validación
   * 
   * @param token
   * @return
   */
  public String getUserIdFrom(String token) {
    try {
      if (!validateToken(token)) {
        return "invalid token";
      }
      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("uid", String.class);
    } catch (Exception ex) {
      return "bad token";
    }
  }

}
