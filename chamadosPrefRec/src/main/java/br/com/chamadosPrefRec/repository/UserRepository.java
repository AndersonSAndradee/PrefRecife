package br.com.chamadosprefrec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import br.com.chamadosprefrec.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
 
}
