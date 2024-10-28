package br.com.chamadosprefrec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.chamadosprefrec.handler.BusinessException;
import br.com.chamadosprefrec.model.User;
import br.com.chamadosprefrec.service.UserService;
import jakarta.el.ELException;

import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/api")  
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar cadastrar usuário.");
        }
    }

    
    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable("id") Long idUser){
        Optional<User> user = userService.findUserById(idUser);
        if (user.isPresent()){
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users")
    public ResponseEntity<?> listUsers(){
        try{
            List<User> user = userService.listUsers();
            if (user.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(user);
        } catch (ELException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar listar usuários.");
        }
    }
    // Ajustar para deletar dados de Roles e TB Roles referentes a Usuário
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long idUser) {
        try {
            userService.deleteUser(idUser);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content após dele  ção bem-sucedida
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar deletar usuário.");
        }
    }


}
