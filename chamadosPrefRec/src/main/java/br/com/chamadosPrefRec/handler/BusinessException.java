package br.com.chamadosprefrec.handler;

//Exeção central que fara o reedirecionamento para RuntimeException
public class BusinessException extends RuntimeException {
    public BusinessException(String mensagem) {
        super(mensagem);
    }

    public BusinessException(String mensagem, Object... params) {
        super(String.format(mensagem, params));
    }
}