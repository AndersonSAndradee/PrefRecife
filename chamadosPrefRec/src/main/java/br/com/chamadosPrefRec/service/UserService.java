package br.com.chamadosprefrec.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.chamadosprefrec.repository.UserRepository;
import jakarta.transaction.Transactional;
import br.com.chamadosprefrec.util.PasswordValidator;
import org.slf4j.Logger;


import java.util.Optional;
import java.util.List;

import br.com.chamadosprefrec.handler.BusinessException;
import br.com.chamadosprefrec.model.User;
 
@Service
public class UserService {

    //Usando framework de logging para exibir mensagens como Logs
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;

    @Autowired

    //Criar usuário
    @Transactional
    public User registerUser(User user){
        try{
            //Bloco de validação de criação de usuário
            if (userRepository.existByEmail(user.getEmail())) {
                throw new BusinessException("Email já está cadastrado. Por favor, utilize um endereço de email diferente.");
            }

            if (!PasswordValidator.isSenhaSegura(user.getPassword())) {
                throw new BusinessException("A senha não atende aos requisitos de segurança.");
            }
            //Bloco de validação Security password add



            return userRepository.save(user);

        } catch (BusinessException e) {
            throw e;
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
            if (!userRepository.existsById(id)) {
                throw new BusinessException("O usuário selecionado não existe!");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Erro ao tentar deletar usuário: {}", e.getMessage());
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


