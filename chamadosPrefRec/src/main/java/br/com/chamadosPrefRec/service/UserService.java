package br.com.chamadosprefrec.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.chamadosprefrec.repository.RoleRepository;
import br.com.chamadosprefrec.repository.UserRepository;
import jakarta.transaction.Transactional;
import br.com.chamadosprefrec.util.PasswordValidator;
import org.slf4j.Logger;


import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.List;


import br.com.chamadosprefrec.handler.BusinessException;
import br.com.chamadosprefrec.model.User;
import br.com.chamadosprefrec.model.Role;

 
@Service
public class UserService {

    //Usando framework de logging para exibir mensagens como Logs
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Criar usuário
    @Transactional
    public User registerUser(User user){
        try{
            //Bloco de validação de criação de usuário
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new BusinessException("Email já está cadastrado. Por favor, utilize um endereço de email diferente.");
            }

            if (userRepository.existsByCpf(user.getCpf())) {
                throw new BusinessException("O CPF informado já está cadastrado no sistema. Verifique se você já possui uma conta ou utilize um CPF diferente.");
            }

            if (!PasswordValidator.isSenhaSegura(user.getPassword())) {
                throw new BusinessException("A senha não atende aos requisitos de segurança.");
            }
            //Security password add
            String encorder = passwordEncoder.encode(user.getPassword());
            user.setPassword(encorder);

            // Obter roles do banco
            Set<Role> managedRoles = new HashSet<>();
            for (Role role : user.getRoles()) {
                Role managedRole = roleRepository.findById(role.getRoleId())
                                                .orElseThrow(() -> new BusinessException("Role não encontrada"));
                managedRoles.add(managedRole);
            }
            user.setRoles(managedRoles);

            return userRepository.save(user);

        } catch (BusinessException be) {
            logger.error("Erro de negócio: {}", be.getMessage());
            throw be;
        } catch (Exception e ) {
            logger.error("Erro ao tentar cadastrar usuário: {}", e.getMessage());
            throw new RuntimeException("Erro ao tentar cadastrar usuário.", e);
        }

       
    }

    //Buscar usuário por id
    public Optional<User> findUserById(Long id){
        try {     
            return userRepository.findById(id);
        } catch (Exception e ) {
            logger.error("Erro ao tentar buscar usuário por id: {}", e.getMessage());
            throw e;
        }
    }

    //Buscar todos usuários
    public List<User> listUsers(){
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Erro ao tentar listar todos os usuários: {}", e.getMessage());
            return null;
        }
    }


    //Deletar usuários
    @Transactional
    public void deleteUser(Long id) {
        try {
            // Obtém o usuário a ser deletado, lançando uma exceção se não for encontrado
            User user = userRepository.findById(id)
                        .orElseThrow(() -> new BusinessException("O usuário selecionado não existe!"));

            // Limpa as referências de roles para remover as associações na tabela user_roles
            user.getRoles().clear();

            // Salva o usuário atualizado para refletir as mudanças
            userRepository.save(user);
            
            // Agora, deletar o usuário
            userRepository.deleteById(id);
        } catch (BusinessException be) {
            // Se for uma BusinessException, loga e relança
            logger.error("Erro de negócio: {}", be.getMessage());
            throw be; // Relança a exceção para o chamador
        } catch (Exception e) {
            logger.error("Erro ao tentar deletar usuário: {}", e.getMessage());
            throw new RuntimeException("Erro ao tentar deletar o usuário."); // Lança uma RuntimeException
        }
    }


    // Metodo para atualizar usuário (Adicionar o encoder após o password ser alterado)
    @Transactional
    public User atualizarDadosUser(Long id, User usuario) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<User> userWithSameEmail = userRepository.findByEmail(usuario.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getUserId().equals(id)) {
            throw new RuntimeException("Email já em uso por outro usuário");
        }

        existingUser.setUsername(usuario.getUsername());
        existingUser.setEmail(usuario.getEmail());   

        return userRepository.save(existingUser);
    }
}


