package pe.blacruz.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import pe.blacruz.security.auth.AuthenticationException;
import pe.blacruz.security.auth.AuthenticatorService;
import pe.blacruz.security.domain.entity.User;
import pe.blacruz.security.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
public class AuthenticatorTests {
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private AuthenticatorService authenticatorService;

  @Test
  public void authenticateOk() throws AuthenticationException {
    var user = new User();
    user.setUsername("blacruz");
    user.setPassword("Sup3erC0ntrasen4");
    user.setFullName("Brayan La Cruz");
    var savedUser = userService.save(user);
    assertNotNull(savedUser);
    var token = authenticatorService.authenticate("blacruz", "Sup3erC0ntrasen4");
    assertTrue(!token.isBlank());
  }
  
  
  @Test
  public void authenticateFail() {
    var user = new User();
    user.setUsername("blacruz2");
    user.setPassword("Sup3erC0ntrasen4");
    user.setFullName("Brayan La Cruz");
    var savedUser = userService.save(user);
    assertNotNull(savedUser);
    
    assertThrows(AuthenticationException.class, () -> {
      authenticatorService.authenticate("blacruz2", "super_Contraseña");
    }, "Contraseña incorrecta");
    
    assertThrows(AuthenticationException.class, () -> {
      authenticatorService.authenticate("usuarioDesconocido", "Sup3er_C0ntrasen4");
    }, "Usuario no existe");
    
  }

}
