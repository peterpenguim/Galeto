package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTO.CadastroDTO;

public class CadastroDAO {

    private String comando;
    Connection conexao;
    PreparedStatement pstm;

    public void cadastrarFuncionarioDAO(CadastroDTO cadastrodto) {
        this.comando = "INSERT INTO tab_funcionarios (CPF_FUNCIONARIO, NOM_FUNCIONARIO, DSC_SETOR) VALUES (?, ?, ?)";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setString(1, cadastrodto.getCpfFuncionario());
            pstm.setString(2, cadastrodto.getNomeFuncionario());
            pstm.setString(3, cadastrodto.getSetorFuncionario());

            pstm.execute();
            pstm.close();   

        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void cadastrarLoginDAO(CadastroDTO cadastrodto) {
        this.comando = "INSERT INTO tab_login (ID_CADASTRO, CPF_FUNCIONARIO, DSC_USUARIO, DSC_SENHA) VALUES (?, ?, ?, ?)";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, cadastrodto.getIdFuncionario());
            pstm.setString(2, cadastrodto.getCpfFuncionario());
            pstm.setString(3, cadastrodto.getNomeFuncionario());
            pstm.setString(4, cadastrodto.getSenhaFuncionario());

            pstm.execute();
            pstm.close();   

        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void removerFuncionarioDAO(CadastroDTO cadastrodto) {
        this.comando = "DELETE FROM tab_funcionarios WHERE CPF_FUNCIONARIO = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setString(1, cadastrodto.getCpfFuncionario());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void removerLoginDAO(CadastroDTO cadastrodto) {
        this.comando = "DELETE FROM tab_login WHERE CPF_FUNCIONARIO = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setString(1, cadastrodto.getCpfFuncionario());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public ResultSet autenticacaoUsuarioDAO(CadastroDTO cadastrodto) {
        this.comando = "SELECT * FROM tab_login WHERE DSC_USUARIO = ? AND DSC_SENHA = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {

            pstm = conexao.prepareStatement(this.comando);
            pstm.setString(1, cadastrodto.getUsuarioLogin());
            pstm.setString(2, cadastrodto.getSenhaLogin());

            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            System.out.println(erro);
            return null;
        }
    }

    public ResultSet visualizarFuncionariosDAO(CadastroDTO cadastrodto) {
        this.comando = "SELECT * FROM tab_funcionarios WHERE CPF_FUNCIONARIO != 99999999999";
        conexao = new ConexaoDAO().conectaBD();

        try {

            pstm = conexao.prepareStatement(this.comando);

            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            System.out.println(erro);
            return null;
        }
    }

    public ResultSet verificaCpfDAO(CadastroDTO cadastrodto) {
        this.comando = "SELECT * FROM tab_funcionarios WHERE CPF_FUNCIONARIO = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {

            pstm = conexao.prepareStatement(this.comando);
            pstm.setString(1, cadastrodto.getCpfFuncionario());

            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            System.out.println(erro);
            return null;
        }
    }

}