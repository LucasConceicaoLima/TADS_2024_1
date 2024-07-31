package br.edu.ifsul.cstsi.lucastads.auth;

import br.edu.ifsul.cstsi.lucastads.infra.security.TokenJwtDTO;
import br.edu.ifsul.cstsi.lucastads.infra.security.TokenService;
import br.edu.ifsul.cstsi.lucastads.users.PerfilRepository;
import br.edu.ifsul.cstsi.lucastads.users.UserRepository;
import br.edu.ifsul.cstsi.lucastads.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private TokenService tokenService;
    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private AuthenticationManager manager;
    @Autowired
    private PerfilRepository perfilRepository;
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginRequestDTO body){
        var authenticationDTO = new UsernamePasswordAuthenticationToken(body.email(), body.password()); //converte o DTO em DTO do Spring Security

        var authentication = manager.authenticate(authenticationDTO); //autentica o usuário (esse objeto contém o usuário e a senha)
        var tokenJWT = tokenService.geraToken((User) authentication.getPrincipal()); //gera o token JWT para enviar na response
        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT)); //envia
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setPerfis(Arrays.asList(perfilRepository.findByNome("ROLE_USER")));
            this.repository.save(newUser);

            String token = this.tokenService.geraToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
