package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConexaoDAO {
    
    public Connection conectaBD() {
        Connection conexao = null;

        try {
            String url = "jdbc:mysql://localhost:3306/galeto?user=teste&password=";
            conexao = DriverManager.getConnection(url);
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "conexaoDAO" + erro.getMessage());
        }

        return conexao;
    }
}
