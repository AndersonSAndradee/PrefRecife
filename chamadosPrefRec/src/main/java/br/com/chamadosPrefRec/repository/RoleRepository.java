package br.com.chamadosprefrec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.chamadosprefrec.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
