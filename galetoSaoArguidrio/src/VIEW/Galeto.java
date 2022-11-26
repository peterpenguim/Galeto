package VIEW;


import DTO.UsuarioDTO;
import DTO.PedidosDTO;
import DTO.CadastroDTO;
import DAO.CadastroDAO;

import java.util.Scanner;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Galeto {

    UsuarioDTO usuariodto = new UsuarioDTO();
    CadastroDTO cadastrodto = new CadastroDTO();
    CadastroDAO cadastrodao = new CadastroDAO();
    PedidosDTO pedidosdto = new PedidosDTO();
    Random gerador = new Random();
    Scanner entrada = new Scanner(System.in);


    public void menuInicial(String usuariologin) {
        System.out.println("\n|......................[ MENU INICIAL ].....................|");
        System.out.println("\n[ "+usuariologin+" ]");
        System.out.println("\n                 .............................\n"+
                           "                 :  1. ADMINISTRAÇÃO         :\n"+
                           "                 :  2. PEDIDOS               :\n"+
                           "                 :  3. LOGOFF                :\n"+
                           "                 :  4. SAIR                  :\n"+
                           "                 :...........................:\n");
        opcaoMenuInicial();
    }

    public void opcaoMenuInicial() {
        switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
            case 1: menuAdministracao(); break;
            case 2: menuPedidos(); break;
            case 3: login(); break;
            case 4: System.out.println("Sessão finalizada."); break;
            default: System.out.println("> ERRO: Opção inválida."); opcaoMenuInicial();
        }
    }

    public void login() {
        System.out.println("\n|..........................[ LOGIN ]..........................|\n");

        try {
            
            System.out.print("\n> USUÁRIO: ");
            String usuario = entrada.next();
            cadastrodto.setUsuarioLogin(usuario);
            System.out.print("\n> SENHA: ");
            String senha = entrada.next();
            cadastrodto.setSenhaLogin(senha);

            ResultSet rscadastrodao = cadastrodao.autenticacaoUsuarioDAO(cadastrodto);
            if (rscadastrodao.next()) {
                System.out.println("\n> Login efetuado.");
                menuInicial(cadastrodto.getUsuarioLogin());
            } else {
                System.out.println("\nERRO: Usuário e/ou senha inválidos.");
                login();
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Galeto" + erro);
        }
    }


    void menuAdministracao() {
        System.out.println("\n|......................[ ADMINISTRAÇÃO ]......................|");
        System.out.println("\n               ...............................\n"+
                           "               :  1. CADASTRAR FUNCIONÁRIO   :\n"+
                           "               :  2. REMOVER FUNCIONÁRIO     :\n"+
                           "               :  3. ABRIR CAIXA             :\n"+
                           "               :  4. FECHAR CAIXA            :\n"+
                           "               :  5. MENU INICIAL            :\n"+
                           "               :.............................:\n");
        opcaoMenuAdministracao();
    }

    void opcaoMenuAdministracao() {
        if(cadastrodto.getUsuarioLogin().equals("admin")) {
            switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
                case 1: cadastrarFuncionario(); break;
                case 2: removerFuncionario(); break;
                case 3:break;
                case 4:break;
                case 5:menuInicial(cadastrodto.getUsuarioLogin());break;
                default:System.out.println("> ERRO: Opção inválida.");opcaoMenuAdministracao();
            }

        } else {
            System.out.println("> ERRO: Não possui permissão de acesso.");
            menuInicial(cadastrodto.getUsuarioLogin());
        }
    }

    void cadastrarFuncionario() {
        System.out.println("\n|................[ CADASTRO DE FUNCIONÁRIO ]................|");
        System.out.print("\n> PRIMEIRO NOME: ");
        String nome = entrada.next();
        cadastrodto.setNomeFuncionario(nome);
        System.out.print("\n> CPF: ");
        String cpf = entrada.next();
        cadastrodto.setCpfFuncionario(cpf);
        criarSenha();
    }

    void criarSenha() {
        System.out.print("\n> SENHA DE ACESSO: ");
        String senha = entrada.next();
        System.out.print("\n> CONFIRME A SENHA: ");
        String confirmacao = entrada.next();
        if (senha.equals(confirmacao)) {
            cadastrodto.setSenhaFuncionario(senha);
            int id = gerador.nextInt(8999999) + 1000000;
            cadastrodto.setIdFuncionario(id);
            cadastrodao.cadastrarFuncionarioDAO(cadastrodto);
            System.out.println("\n> Cadastro efetuado.");
            menuAdministracao();
        } else {
            System.out.println("> ERRO: Senhas divergentes.");
            criarSenha();
        }
    }

    void removerFuncionario() {
        System.out.print("\nINFORME O FUNCIONÁRIO QUE DESEJA REMOVER: ");
        int opcao = entrada.nextInt();
        cadastrodto.setIdFuncionario(opcao);
        cadastrodao.removerFuncionarioDAO(cadastrodto);
        System.out.println("\n> Funcionário removido.");
        menuAdministracao();
    }

    
    void menuPedidos(){
        System.out.println("\n|.....................[ MENU DE PEDIDOS ].....................|");
        System.out.println("\n               ...............................\n"+
                           "               :  1. CADASTRAR PEDIDO        :\n"+
                           "               :  2. CANCELAR PEDIDO         :\n"+
                           "               :  3. CONFIRMAR PEDIDO        :\n"+
                           "               :  4. PEDIDOS PENDENTES       :\n"+
                           "               :  5. MENU INICIAL            :\n"+
                           "               :.............................:\n");
        opcaoMenuPedidos();
    }

    void opcaoMenuPedidos() {
        switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
            case 1: cadastrarPedido(); break;
            case 2: chamaMetodosPedidos(1, "CANCELAR", "cancelado"); break;
            case 3: chamaMetodosPedidos(1, "CONFIRMAR", "confirmado"); break;
            case 4: chamaMetodosPedidos(2, null, null); break;
            case 5: menuInicial(usuariodto.getModo()); break;
            default:System.out.println("> ERRO: Opção inválida."); opcaoMenuPedidos();
        }
    }

    void chamaMetodosPedidos(int opcao, String msg1, String msg2) {
        if (verificaPendentes()) {
            visualizarPendentes();
            switch(opcao) {
                case 1: confirmarOuCancelarPedido(msg1, msg2); break;
                default: menuPedidos(); break;
            }
        } else {
            menuPedidos();
        }
    }   

    boolean verificaPendentes() {
        if (usuariodto.getPendentes().size() == 0) {
            usuariodto.setVerificador(false);
            System.out.println("> ERRO: Não existem pedidos pendentes.");
        } else {
            usuariodto.setVerificador(true);
        }
        return usuariodto.getVerificador();
    }

    void visualizarPendentes() {
        System.out.println("\n.....................[ PEDIDOS PENDENTES ].....................\n");
        for(int i = 0; i < usuariodto.getPendentes().size(); i++) {
            System.out.println((i+1)+". "+usuariodto.getPendentes().get(i)+"\n");
        }
    }

    void confirmarOuCancelarPedido(String mensagem1, String mensagem2) {
        usuariodto.setOpcao("> INFORME O PEDIDO QUE DESEJA "+mensagem1+": ");
        if (usuariodto.getOpcao() > usuariodto.getPendentes().size()) {
            System.out.println("> ERRO: O pedido Nº "+usuariodto.getOpcao()+" não existe.");
            menuPedidos();
        } else {
            usuariodto.removePendentes(usuariodto.getOpcao()-1);
            System.out.println("Pedido Nº "+usuariodto.getOpcao()+" "+mensagem2);
            menuPedidos();
        }
    }

    void cadastrarPedido() {
        menuProdutos();
        usuariodto.addValoresPedido(usuariodto.getValorProduto());
        usuariodto.setValorTotalPedido(usuariodto.getValorTotalPedido()+usuariodto.getValorProduto());
        opcaoFinalizarPedido();
    }

    void gerarNumeroPedido() {
        usuariodto.setNumeroPedido(String.valueOf(gerador.nextInt(999)));
        if (usuariodto.getNumeroPedido().length() == 1) {
            usuariodto.setNumeroPedido("00"+usuariodto.getNumeroPedido());
        } else if(usuariodto.getNumeroPedido().length() == 2) {
            usuariodto.setNumeroPedido("0"+usuariodto.getNumeroPedido());
        }
        usuariodto.addNumerosPedidos(usuariodto.getNumeroPedido());
    }

    void menuProdutos() {
        System.out.println("\n|........................[ PRODUTOS ].........................|");
        System.out.println("\n              ................................\n"+
                           "              :  1. GALETO            24.00  :\n"+
                           "              :  2. CHURRASCO BOI     30.00  :\n"+
                           "              :  3. COMERCIAL GALETO  15.00  :\n"+
                           "              :  4. COMERCIAL BOI     20.00  :\n"+
                           "              :  5. FRITAS            15.00  :\n"+
                           "              :  6. PÃO DE ALHO        6.00  :\n"+
                           "              :  7. GUARANÁ JESUS 1L   7.50  :\n"+
                           "              :..............................:\n");
        opcaoProduto();
    }

    void opcaoProduto() {
        usuariodto.setOpcao("> SELECIONE UM PRODUTO: ");
        chamaMetodosProduto(usuariodto.getOpcao()-1);
    }

    void chamaMetodosProduto(int valor) {
        lerQuantidade(); 
        adicionarPedido(valor); 
        usuariodto.setValorProduto(valor);
    }

    void lerQuantidade() {
        System.out.print("\n> INFORME A QUANTIDADE: ");
        usuariodto.setQuantidade(entrada.nextInt());
    }

    void adicionarPedido(int indice) {
        usuariodto.addPedido(usuariodto.getProdutos(indice)+" x"+usuariodto.getQuantidade()+" ");
    }

    void opcaoFinalizarPedido() {
        System.out.println("\n1. CONTINUAR PEDIDO\n"+"2. FINALIZAR PEDIDO");
        switch(usuariodto.setOpcao("\n> ESCOLHA UMA OPÇÃO: ")) {
            case 1: cadastrarPedido(); break;
            case 2: gerarNumeroPedido(); notaFiscal(); usuariodto.addPendentes(pedidoFinal()); resetPedido(); menuPedidos(); break;
            default:System.out.println("\n> ERRO: Opção inválida.");opcaoFinalizarPedido(); 
        } 
    }

    void notaFiscal() {
        System.out.println("\nPedido cadastrado.");
        System.out.println("\n|.......................[ NOTA FISCAL ].......................|");
                System.out.println("\nPEDIDO Nº "+usuariodto.getNumeroPedido()+"\n");
                for (int i = 0; i <usuariodto.getPedido().size(); i++) {
                    System.out.println(usuariodto.getPedido().get(i)+usuariodto.getValoresPedido().get(i));
                }
                System.out.println("TOTAL: "+usuariodto.getValorTotalPedido());
    }

    void resetPedido() {
        usuariodto.setPedidoCompleto("");
        usuariodto.setValorTotalPedido(0);
        usuariodto.getValoresPedido().clear();
        usuariodto.getPedido().clear();
    }

    String pedidoFinal() {
        for (int i = 0; i < usuariodto.getPedido().size(); i++) {
            usuariodto.setPedidoCompleto(usuariodto.getPedidoCompleto()+(usuariodto.getPedido().get(i) + "/ "));
        }
        usuariodto.setPedidoCompleto(usuariodto.getPedidoCompleto()+usuariodto.getValorTotalPedido());
        return usuariodto.getPedidoCompleto();
    }

    public static void main(String[] args) {
        Galeto teste = new Galeto();
        teste.login();
       
    }
}