package VIEW;

import DTO.UsuarioDTO;
import DTO.PedidosDTO;
import DTO.CadastroDTO;
import DAO.CadastroDAO;
import DAO.PedidosDAO;

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
    PedidosDAO pedidosdao = new PedidosDAO();
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
            //case 2: menuPedidos(); break;
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
        System.out.print("\n> FUNÇÃO: ");
        String setor = entrada.next();
        cadastrodto.setSetorFuncionario(setor);
        cadastrodao.cadastrarFuncionarioDAO(cadastrodto);
        if (cadastrodto.getSetorFuncionario().equals("caixa")) {
            criarSenha();
        } else {
            System.out.println("\n> Cadastro efetuado.");
            menuAdministracao();
        }
        
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
            cadastrodao.cadastrarLoginDAO(cadastrodto);
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
            case 1: 
                pedidosdto.setNumeroPedido(gerador.nextInt(899) + 100); 
                cadastrarPedido(); break;
            //case 2: chamaMetodosPedidos(1, "CANCELAR", "cancelado"); break;
            //case 3: chamaMetodosPedidos(1, "CONFIRMAR", "confirmado"); break;
            //case 4: chamaMetodosPedidos(2, null, null); break;
            //case 5: menuInicial(usuariodto.getModo()); break;
            default:System.out.println("> ERRO: Opção inválida."); opcaoMenuPedidos();
        }
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
        
    }

    public void cadastrarPedido () {
        menuProdutos();
        System.out.print("\n> SELECIONE O PRODUTO DESEJADO: ");
        int produto = entrada.nextInt();
        pedidosdto.setProdutoEscolhido(pedidosdto.produtos[produto-1]);
        System.out.print("\n> INFORME A QUANTIDADE: ");
        int quantidade = entrada.nextInt();
        pedidosdto.setQuantidadeEscolhida(quantidade);
        pedidosdto.setValorItens(quantidade * pedidosdto.valoresUnitarios[produto-1]);
        pedidosdao.cadastrarItensPedidoDAO(pedidosdto);
        pedidosdto.setValorPedido(pedidosdto.getValorPedido() + pedidosdto.getValorItens());
        finalizarOuContinuarPedido();     
    }

    void finalizarOuContinuarPedido() {
        System.out.println("\n1. CONTINUAR PEDIDO\n"+"2. FINALIZAR PEDIDO");
        switch(usuariodto.setOpcao("\n> ESCOLHA UMA OPÇÃO: ")) {
            case 1: cadastrarPedido(); break;
            case 2: 
                pedidosdao.cadastrarPedidoDAO(pedidosdto);
                System.out.println("\n> Pedido cadastrado.");
                menuPedidos(); break;
            default: System.out.println("\n> ERRO: Opção inválida"); finalizarOuContinuarPedido(); break;
        }
    }

    void confirmarPedido() {
        pedidosPendentes();
        System.out.print("\nINFORME O Nº DO PEDIDO QUE DESEJA CONFIRMAR: ");
        int pedido = entrada.nextInt();
        pedidosdto.setNumeroPedido(pedido);
        pedidosdao.confirmarPedidoDAO(pedidosdto);
        System.out.println("\n> Pedido confirmado.");
    }

    void cancelarPedido() {
        pedidosPendentes();
        System.out.print("\nINFORME O Nº DO PEDIDO QUE DESEJA CANCELAR: ");
        int pedido = entrada.nextInt();
        pedidosdto.setNumeroPedido(pedido);
        pedidosdao.cancelarPedidoDAO(pedidosdto);
        pedidosdao.cancelarItensPedidoDAO(pedidosdto);
        System.out.println("\n Pedido cancelado.");
    }

    void pedidosPendentes() {

        try {
            ResultSet rscadastrodao = pedidosdao.pedidosPendentesDAO(pedidosdto);
            if(rscadastrodao.next()){
                System.out.println(rscadastrodao);
            } else {
                System.out.println("\n> ERRO: Não existem pedidos pendentes.");
                menuAdministracao();
            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Galeto" + erro);
        }
    }

    public static void main(String[] args) {
        Galeto teste = new Galeto();
        teste.login();
       
    }
}