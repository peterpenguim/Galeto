package DAO;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTO.CadastroDTO;

public class CadastroDAO {

    private String comando;
    Connection conexao;
    PreparedStatement pstm;
    
    public void cadastrarFuncionarioDAO(CadastroDTO cadastrodto) {
        this.comando = "INSERT INTO login (ID_Funcionario, usuario, senha) VALUES (?, ?, ?)";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, cadastrodto.getIdFuncionario());
            pstm.setString(2, cadastrodto.getCpfFuncionario());
            pstm.setString(3, cadastrodto.getSenhaFuncionario());

            pstm.execute();
            pstm.close();   

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CadastroDAO" + erro);
        }
    }

    public void removerFuncionarioDAO(CadastroDTO cadastrodto) {
        this.comando = "DELETE FROM login WHERE ID_Funcionario = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, cadastrodto.getIdFuncionario());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CadastroDAO" + erro);
        }
    }

    public ResultSet autenticacaoUsuarioDAO(CadastroDTO cadastrodto) {
        this.comando = "SELECT * FROM login WHERE usuario = ? AND senha = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {

            pstm = conexao.prepareStatement(this.comando);
            pstm.setString(1, cadastrodto.getUsuarioLogin());
            pstm.setString(2, cadastrodto.getSenhaLogin());

            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CadastroDAO: " + erro);
            return null;
        }
    }

}