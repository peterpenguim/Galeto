package VIEW;

import DTO.UsuarioDTO;
import DTO.PedidosDTO;
import DAO.PedidosDAO;
import DTO.CadastroDTO;
import DAO.CadastroDAO;

import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.time.format.DateTimeFormatter;

public class MenuInicial implements SistemaGaleto {

    UsuarioDTO usuariodto = new UsuarioDTO();
    CadastroDTO cadastrodto = new CadastroDTO();
    CadastroDAO cadastrodao = new CadastroDAO();
    PedidosDTO pedidosdto = new PedidosDTO();
    PedidosDAO pedidosdao = new PedidosDAO();
    Random gerador = new Random();
    Scanner entrada = new Scanner(System.in);
    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public MenuInicial(String usuario, int caixa) {
        cadastrodto.setUsuarioLogin(usuario);
        pedidosdto.setStatusCaixa(caixa);
    }

    public MenuInicial() {

    }

    public void menu() {

        System.out.println("\n|......................[ MENU INICIAL ].....................|");
        System.out.println("\n    [ "+cadastrodto.getUsuarioLogin()+" ]");
        System.out.println("\n                 .............................\n"+
                           "                 :  1. ADMINISTRAÇÃO         :\n"+
                           "                 :  2. PEDIDOS               :\n"+
                           "                 :  3. LOGOFF                :\n"+
                           "                 :  4. SAIR                  :\n"+
                           "                 :...........................:\n");
        escolherOpcao();
    }

    public void escolherOpcao() {
        switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
            case 1: 
                Administracao adm = new Administracao(cadastrodto.getUsuarioLogin());
                adm.menu(); break;
            case 2: 
                if (pedidosdto.getStatusCaixa() <= 0) {
                    System.out.println("\n>> ERRO: O caixa está fechado. <<");
                    menu(); break;
                } else {
                    Pedidos pedidos = new Pedidos(cadastrodto.getUsuarioLogin(), pedidosdto.getStatusCaixa());
                    pedidos.menu(); 
                    break;
                }
            case 3: login(); break;
            case 4: System.out.println("Sessão finalizada."); break;
            default: System.out.println("> ERRO: Opção inválida."); escolherOpcao();
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
                menu();
            } else {
                System.out.println("\nERRO: Usuário e/ou senha inválidos.");
                login();
            }
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void verificacao() {

    }

    public static void main(String[] args) {
        MenuInicial teste = new MenuInicial();
        teste.login();
       
    }
}