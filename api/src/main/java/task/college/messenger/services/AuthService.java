package task.college.messenger.services;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.college.messenger.dto.LoginDTO;
import task.college.messenger.dto.RegistrationDTO;
import task.college.messenger.entities.User;
import task.college.messenger.repos.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(RegistrationDTO dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
    }

    public String login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password()
                )
        );

        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return jwtService.generateToken(user);
    }
}