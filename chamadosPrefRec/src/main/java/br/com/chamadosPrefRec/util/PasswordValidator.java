package br.com.chamadosprefrec.util;

public class PasswordValidator {
    
    public static boolean isSenhaSegura(String senha) {
        // Regras de validação de senha
        if (senha == null || senha.length() < 8) {
            return false;
        }
        if (!senha.matches(".*[A-Z].*")) {
            return false;
        }
        if (!senha.matches(".*[a-z].*")) {
            return false;
        }
        if (!senha.matches(".*[0-9].*")) {
            return false;
        }
        if (!senha.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return false;
        }
        return true;
    }
}
