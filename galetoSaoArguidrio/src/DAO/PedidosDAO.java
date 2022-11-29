package DAO;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTO.PedidosDTO;

public class PedidosDAO {
    
    private String comando;
    Connection conexao;
    PreparedStatement pstm;

    public void cadastrarItensPedidoDAO(PedidosDTO pedidosdto) {
        this.comando = "INSERT INTO tab_itens_pedido (NUM_PEDIDO, COD_PRODUTO, DSC_ITEM, DSC_QUANTIDADE, VAL_ITENS) VALUES (?, ?, ?, ?, ?)";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, pedidosdto.getNumeroPedido());
            //pstm.setString(2, cadastrodto.getNomeFuncionario());
            pstm.setString(3, pedidosdto.getProdutoEscolhido());
            pstm.setInt(4, pedidosdto.getQuantidadeEscolhida());
            pstm.setDouble(5, pedidosdto.getValorItens());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "PedidosDAO" + erro);
        }
    }

    public void cadastrarPedidoDAO(PedidosDTO pedidosdto) {
        this.comando = "INSERT INTO tab_pedidos (NUM_PEDIDO, DSC_STATUS, VAL_TOTAL) VALUES (?, ?, ?)";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, pedidosdto.getNumeroPedido());
            pstm.setString(2, "PREPARANDO");
            pstm.setDouble(3, pedidosdto.getValorPedido());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "PedidosDAO" + erro);
        }
    }

    public void confirmarPedidoDAO(PedidosDTO pedidosdto) {
        this.comando = "UPDATE tab_pedidos SET DSC_STATUS = 'CONFIRMADO' WHERE NUM_PEDIDO = ?;";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, pedidosdto.getNumeroPedido());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "PedidosDAO" + erro);
        }
    }

    public void cancelarPedidoDAO(PedidosDTO pedidosdto) {
        this.comando = "DELETE FROM tab_pedidos WHERE NUM_PEDIDO = ?;";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, pedidosdto.getNumeroPedido());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "PedidosDAO" + erro);
        }
    }

    public void cancelarItensPedidoDAO(PedidosDTO pedidosdto) {
        this.comando = "DELETE FROM tab_itens_pedidos WHERE NUM_PEDIDO = ?;";
        conexao = new ConexaoDAO().conectaBD();

        try {
            
            pstm = conexao.prepareStatement(this.comando);
            pstm.setInt(1, pedidosdto.getNumeroPedido());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "PedidosDAO" + erro);
        }
    }

    public ResultSet pedidosPendentesDAO(PedidosDTO pedidosdto) {
        this.comando = "SELECT * FROM tab_pedidos WHERE DSC_STATUS = ?";
        conexao = new ConexaoDAO().conectaBD();

        try {

            pstm = conexao.prepareStatement(this.comando);
            pstm.setString(1, "PREPARANDO");

            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CadastroDAO: " + erro);
            return null;
        }
    }
}