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
    import tn.esprit.projetsalledemarche.dto.AuthResponseDTO;
    import tn.esprit.projetsalledemarche.dto.LoginDto;
    import tn.esprit.projetsalledemarche.dto.RegisterDto;
    import tn.esprit.projetsalledemarche.security.JWTGenerator;

    import java.security.SignatureException;
    import java.util.Collections;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {
        private  final AuthenticationManager authenticationManager;
        private   final UserRepository userRepository;
        private final  RoleRepository roleRepository;
        private  final PasswordEncoder passwordEncoder;
        private final JWTGenerator jwtGenerator;

        @Autowired
        public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                              RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
            this.authenticationManager = authenticationManager;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtGenerator = jwtGenerator;
        }

        @PostMapping("login")
        public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
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


            // Assigner un rôle "USER"
            Role roles = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role USER not found!"));
            user.setRoles(Collections.singletonList(roles));

            // Sauvegarder l'utilisateur dans la base de données
            userRepository.save(user);

            // Retourner une réponse de succès
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        }}
