package br.com.fiap;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML TextField textFieldSenha;
    @FXML TextField textFieldLogin;
    @FXML TextField textFieldLocal;
    @FXML TableView<Senha> tableView;
    @FXML TableColumn<Senha,String> colunaLogin;
    @FXML TableColumn<Senha,String> colunaSenha;
    @FXML TableColumn<Senha,String> colunaLocal;

    String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    String user = "user";
    String password = "password";
    String senha;
    String login;
    String local;
    
    private String descodificar(String codigo) {
        StringBuffer senhaCripto = new StringBuffer();
        for(int i=0;0<codigo.length();i++){
            char letra = codigo.charAt(i);
            int ascii = letra;
            ascii -=1;
            char[] letraCripto = Character.toChars(ascii);
            senhaCripto.insert(i, letraCripto);
        }
        String retorno = senhaCripto.toString();
        return retorno;
    }
    public void inserir() {
        senha = textFieldSenha.getText();
        login = textFieldLogin.getText();
        local = textFieldLocal.getText();
        StringBuffer senhaCripto = new StringBuffer();
        for (int i =0; i<senha.length();i++){
            char letra = senha.charAt(i);
            int ascii = letra;
            ascii +=1;
            char[] letraCripto = Character.toChars(ascii);
            senhaCripto.insert(i, letraCripto);
        }
        senha = senhaCripto.toString();
        System.out.println(senha);
        acessar();
    }
    public void verificar() {
        try {
            tableView.getItems().clear();
            Connection con = DriverManager.getConnection(url, user, password);
            String sql = "select * from senhas";
            Statement stt = con.createStatement();
            ResultSet rs = stt.executeQuery(sql);
            System.out.println(rs);

            while(rs.next()){
                String voltaLogin = rs.getString("LOGIN");
                System.out.println(voltaLogin);
                String voltaLocal = rs.getString("LOCAL");
                String voltaSenha= descodificar(rs.getString("SENHA"));
                Senha senha = new Senha(voltaSenha,voltaLogin,voltaLocal);
                tableView.getItems().add(senha);
            }
            con.close();
        } catch (Exception e) {
            e.getMessage();
            // TODO: handle exception
        }
        
        
    }
    private void acessar() {
        try{
        Connection con = DriverManager.getConnection(url, user, password);
        String sql = "INSERT INTO senhas (senha, login, local) "
                        + "VALUES (?, ?, ?)";
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setString(1, senha);
        stm.setString(2, login);
        stm.setString(3, local);

        stm.execute();
        con.close();
        }catch(SQLException e){
            e.getMessage();
        }
    
    }
}
