package pe.blacruz.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.blacruz.security.jwt.JwtProvider;
import pe.blacruz.security.repository.UserRepository;

@Service
public class AuthenticatorService {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Autowired
  private JwtProvider jwtProvider;

  public String authenticate(String username, String password) throws AuthenticationException {
    var user = userRepository.findByUsername(username);
    
    if (user == null) { 
     throw new AuthenticationException("Usuario no existe");
    }
    
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new AuthenticationException("Contraseña incorrecta");
    }

    var token = jwtProvider.createToken("0", username, user.getId().toString(), user.getFullName());
    return token;
  }

}
