package br.com.fiap;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML TextField textFieldSenha;
    @FXML TextField textFieldLogin;
    @FXML TextField textFieldLocal;
    @FXML TableView tableView;

    String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    String user = "rm95315";
    String password = "161203";
    String senha;
    String login;
    String local;
    
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
            PreparedStatement ptt = con.prepareStatement(sql);
            ResultSet rs = ptt.executeQuery();

            while(rs.next()){
                String voltaSenha; 
                for (int i =0;  i < rs.getString(senha).length();i++){
                        
                }
                String voltaLogin = rs.getString(login);
                String voltaLocal = rs.getString(login);
                Senha senha = new Senha(voltaSenha,voltaLogin,voltaLocal);
                tableView.getItems().add(senha);
            }
            con.close();
        } catch (Exception e) {
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
        System.out.println(senha);
        }catch(SQLException e){
            e.getMessage();
        }
    
    }
}
