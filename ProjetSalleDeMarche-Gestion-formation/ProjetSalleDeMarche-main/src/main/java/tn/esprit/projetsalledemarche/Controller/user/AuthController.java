    package tn.esprit.projetsalledemarche.Controller.user;

    import io.jsonwebtoken.Claims;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import tn.esprit.projetsalledemarche.Entity.Linda.user.Role;
    import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
    import tn.esprit.projetsalledemarche.Repository.lindarepo.user.RoleRepository;
    import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserRepository;
    import tn.esprit.projetsalledemarche.Service.ser.user.EmailService;
    import tn.esprit.projetsalledemarche.dto.AuthResponseDTO;
    import tn.esprit.projetsalledemarche.dto.LoginDto;
    import tn.esprit.projetsalledemarche.dto.RegisterDto;
    import tn.esprit.projetsalledemarche.security.JWTGenerator;

    import java.security.SignatureException;
    import java.util.Collections;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {
        private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final JWTGenerator jwtGenerator;
        private final EmailService emailService;
        @Autowired
        public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                              RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,EmailService emailService) {
            this.authenticationManager = authenticationManager;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtGenerator = jwtGenerator;
            this.emailService = emailService;
        }

        @PostMapping("login")
        public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            // Récupérer l'utilisateur connecté et son rôle
            User user = userRepository.findByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found!"));
            String role = user.getRoles().get(0).getName(); // Suppose que l'utilisateur a un seul rôle

            // Retourner le token avec le rôle
            AuthResponseDTO response = new AuthResponseDTO(token);
            response.setRole(role);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


        @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
            // Vérifier si le nom d'utilisateur existe déjà
            if (userRepository.existsByUsername(registerDto.getUsername())) {
                return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
            }

            // Vérifier si l'email existe déjà
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }

            // Créer un nouvel utilisateur
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            // Vérifier et attribuer le rôle (par défaut : USER)
            String roleName = (registerDto.getRole() != null && registerDto.getRole().equalsIgnoreCase("ADMIN")) ? "ADMIN" : "USER";
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found!"));
            user.setRoles(Collections.singletonList(role));

            // Sauvegarder l'utilisateur dans la base de données
            userRepository.save(user);

            // Envoyer un e-mail de confirmation
            emailService.sendConfirmationEmail(registerDto.getEmail(), registerDto.getUsername());

            return new ResponseEntity<>("User registered successfully. Confirmation email sent!", HttpStatus.CREATED);
        }

    }