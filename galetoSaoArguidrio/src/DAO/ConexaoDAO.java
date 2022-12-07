package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConexaoDAO {
    
    public Connection conectaBD() {
        Connection conexao = null;

        try {
            String url = "jdbc:mysql://127.0.0.1:3306/galetosaoarguidrio?user=teste&password=";
            conexao = DriverManager.getConnection(url);
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "conexaoDAO" + erro.getMessage());
        }

        return conexao;
    }
}
