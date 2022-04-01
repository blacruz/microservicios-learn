package pe.blacruz.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase para general el bean de BCryptPasswordEncoder que usará la clase de autenticacion y autorizacion
 * @author blacruzc
 *
 */
@Configuration
public class PasswordEnconderConfig {
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
