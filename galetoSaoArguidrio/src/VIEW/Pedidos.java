package VIEW;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Pedidos extends MenuInicial{

    public Pedidos(String usuario, int caixa) {
        cadastrodto.setUsuarioLogin(usuario);
        pedidosdto.setStatusCaixa(caixa);

    }

    public Pedidos() {

    }

    public void menu() {
        System.out.println("\n|.....................[ MENU DE PEDIDOS ].....................|");
        System.out.println("\n               ...............................\n"+
                           "               :  1. CADASTRAR PEDIDO        :\n"+
                           "               :  2. CANCELAR PEDIDO         :\n"+
                           "               :  3. CONFIRMAR PEDIDO        :\n"+
                           "               :  4. PEDIDOS PENDENTES       :\n"+
                           "               :  5. MENU INICIAL            :\n"+
                           "               :.............................:\n");
        escolherOpcao();
    }

    public void escolherOpcao() {
        switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
            case 1: 
                pedidosdto.setNumeroPedido(gerador.nextInt(899) + 100);
                pedidosdao.cadastrarPedidoDAO(pedidosdto);
                cadastrarPedido(); break;
            case 2: cancelarPedido(); break;
            case 3: confirmarPedido(); break;
            case 4: verificacao(); menu(); break;
            case 5:
                MenuInicial inicial = new MenuInicial(cadastrodto.getUsuarioLogin(), pedidosdto.getStatusCaixa());
                inicial.menu();break;
            default:System.out.println("> ERRO: Opção inválida."); escolherOpcao();
        }

    }

    public void verificacao() {
        try {
            ResultSet rspedidosdao = pedidosdao.pedidosPendentesDAO(pedidosdto);
            if(rspedidosdao.next()){
                pedidosPendentes();
            } else {
                System.out.println("\n> ERRO: Não existem pedidos pendentes.");
                menu();
            }
            
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void Cardapio() {
        System.out.println("\n|........................[ PRODUTOS ].........................|");
        System.out.println("\n              ................................\n"+
                           "              :  1. GALETO            24.00  :\n"+
                           "              :  2. CHURRASCO BOI     30.00  :\n"+
                           "              :  3. COMERCIAL GALETO  15.00  :\n"+
                           "              :  4. COMERCIAL BOI     20.00  :\n"+
                           "              :  5. FRITAS            15.00  :\n"+
                           "              :  6. PÃO DE ALHO        6.00  :\n"+
                           "              :  7. GUARANÁ JESUS      4.50  :\n"+
                           "              :..............................:\n");
        
    }

    public void cadastrarPedido () {
        Cardapio();
        System.out.print("\n> SELECIONE O PRODUTO DESEJADO: ");
        int produto = entrada.nextInt();
        pedidosdto.setProdutoEscolhido(pedidosdto.produtos[produto-1]);
        pedidosdto.setCodProduto(produto);
        System.out.print("\n> INFORME A QUANTIDADE: ");
        int quantidade = entrada.nextInt();
        pedidosdto.setQuantidadeEscolhida(quantidade);
        pedidosdto.setValorItens(quantidade * pedidosdto.valoresUnitarios[produto-1]);
        pedidosdao.cadastrarItensDAO(pedidosdto);
        pedidosdto.setValorPedido(pedidosdto.getValorPedido() + pedidosdto.getValorItens());
        finalizarOuContinuarPedido();
    }

    public void finalizarOuContinuarPedido() {
        System.out.println("\n1. CONTINUAR PEDIDO\n"+"2. FINALIZAR PEDIDO");
        switch(usuariodto.setOpcao("\n> ESCOLHA UMA OPÇÃO: ")) {
            case 1: cadastrarPedido(); break;
            case 2:
                pedidosdao.inserirValorPedidoDAO(pedidosdto);
                System.out.println("\n> Pedido cadastrado.");
                notaFiscal();
                menu(); break;
            default: System.out.println("\n> ERRO: Opção inválida"); finalizarOuContinuarPedido(); break;
        }
    }

    public void notaFiscal() {
        try {
            System.out.println("\n........................[ NOTA FISCAL ]........................\n"+
                               ":                                                             :\n"+
                               ":                     GALETO SÃO ARGUIDRO                     :\n"+
                               ":                                                             :\n"+
                               ":                        "+"PEDIDO Nº "+pedidosdto.getNumeroPedido()+"                        :\n"+
                               ": PRODUTOS                                         QTD    VAL :\n"+
                               ":                                                             :");
            ResultSet rspedidosdao = pedidosdao.notaFiscalDAO(pedidosdto);
            while(rspedidosdao.next()) {
                String produto = rspedidosdao.getString("DSC_PRODUTO");
                int qtd = rspedidosdao.getInt("DSC_QUANTIDADE");
                double valor = rspedidosdao.getDouble("DSC_VALOR");
                System.out.println(": "+produto+"                                  "+qtd+"    "+valor+" :");
            }
            System.out.println(":                                                             :");
            ResultSet rsvalortotal = pedidosdao.verificaNumeroPedidoDAO(pedidosdto);
            while(rsvalortotal.next()) {
                double total = rsvalortotal.getDouble("VAL_TOTAL");
                System.out.println(": TOTAL                                                 "+total+" :");
            }
            System.out.println(":.............................................................:");

        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void confirmarPedido() {
        try {
            verificacao();
            System.out.print("\nINFORME O Nº DO PEDIDO QUE DESEJA CONFIRMAR: ");
            int pedido = entrada.nextInt();
            pedidosdto.setNumeroPedido(pedido);
            ResultSet rspedidosdao = pedidosdao.verificaNumeroPedidoDAO(pedidosdto);
            if (rspedidosdao.next()) {
                pedidosdao.confirmarPedidoDAO(pedidosdto);
                System.out.println("\n> Pedido confirmado.");
                menu();
            } else {
                System.out.println("\n> Esse pedido não existe.");
                confirmarPedido();
            }
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void cancelarPedido() {

        try {
            verificacao();
            System.out.print("\nINFORME O Nº DO PEDIDO QUE DESEJA CANCELAR: ");
            int pedido = entrada.nextInt();
            pedidosdto.setNumeroPedido(pedido);
            ResultSet rspedidosdao = pedidosdao.verificaNumeroPedidoDAO(pedidosdto);
            if (rspedidosdao.next()) {
                pedidosdao.cancelarItensDAO(pedidosdto);
                pedidosdao.cancelarPedidoDAO(pedidosdto);
                System.out.println("\n Pedido cancelado.");
                menu();
                
            } else {
                System.out.println("\n> Esse pedido não existe.");
                cancelarPedido();
            }
            
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void pedidosPendentes() {
        System.out.println("\n|........................[ PENDENTES ]........................|");

        try {
            ResultSet rspedidosdao = pedidosdao.pedidosPendentesDAO(pedidosdto);
            while(rspedidosdao.next()) {
                int numero = rspedidosdao.getInt("NUM_PEDIDO");
                System.out.println("\n>> Nº "+numero);
            }
            
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }
}