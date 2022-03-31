package pe.blacruz.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import pe.blacruz.security.jwt.JwtProvider;

@SpringBootTest
@ActiveProfiles("test")
class JwtTests {
  
  @Autowired
  private JwtProvider jwtProvider;
  
  /**
   * Este test crear un token de JWT
   */
	@Test
	public void createToken() {
	  var token = jwtProvider.createToken("1", "blacruz", "1312", "Brayan La Cruz");
	  assertTrue(!token.isBlank());
	}
	
	/**
	 * Crea tokens JWT y los valida
	 */
	@Test
	public void validateToken() {
	  var expiratedToken = jwtProvider.createToken("1", "blacruz", "1312", "Brayan La Cruz", 0);
	  assertFalse(jwtProvider.validateToken(expiratedToken));
	  
	  var manipulatedToken = jwtProvider.createToken("1", "blacruz", "1312", "Brayan La Cruz") + "a";
    assertFalse(jwtProvider.validateToken(manipulatedToken));
    
    var okToken = jwtProvider.createToken("1", "blacruz", "1312", "Brayan La Cruz");
    assertTrue(jwtProvider.validateToken(okToken));
	}
	
	/**
	 * Funcionalidad de obtener el id de usuario mediante el token
	 */
	@Test
	public void getUserIdFromToken() {
	  var token = jwtProvider.createToken("1", "blacruz", "1312", "Brayan La Cruz");
    assertEquals(jwtProvider.getUserIdFrom(token), "1312");
	}
	
}
