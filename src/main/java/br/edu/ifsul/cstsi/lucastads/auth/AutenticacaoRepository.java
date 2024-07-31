package br.edu.ifsul.cstsi.lucastads.auth;

import br.edu.ifsul.cstsi.lucastads.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    Esta interce visa, única e exclusivamente, realizar a busca pelo usuário com UserDetails.
    Note que ela implementa Repository, ao invés de JpaRepository. Assim, a AutenticacaoRepository
    vem vazia, sem métodos CRUD, como a JpaRepository. Logo, essa interface só terá o(s) método(s)
    implementado (s)nela.
    A responsabilidade por CRUD de usuários fica a cargo da UsuarioRepository.
 */
public interface AutenticacaoRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
