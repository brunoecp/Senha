package br.com.fiap;

public class Senha {

    String senha;
    String login;
    String local;
    
    public Senha(String senha, String login, String local) {
        this.senha = senha;
        this.login = login;
        this.local = local;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    
}