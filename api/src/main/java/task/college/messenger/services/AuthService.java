package task.college.messenger.services;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.college.messenger.dto.request.LoginDTO;
import task.college.messenger.dto.request.RegistrationDTO;
import task.college.messenger.entities.User;
import task.college.messenger.exceptions.UserAlreadyExistsException;
import task.college.messenger.exceptions.UserNotFoundException;
import task.college.messenger.repos.UserRepository;
import task.college.messenger.services.security.jwt.JwtTokenService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public void register(RegistrationDTO dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new UserAlreadyExistsException("User already exists");
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
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return jwtTokenService.generateToken(user);
    }
}