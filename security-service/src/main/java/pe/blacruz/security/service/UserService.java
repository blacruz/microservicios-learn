package pe.blacruz.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.blacruz.security.domain.entity.User;
import pe.blacruz.security.repository.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    var savedUser = userRepository.save(user);
    return savedUser;
  }

}
