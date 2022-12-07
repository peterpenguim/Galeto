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
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Galeto {

    UsuarioDTO usuariodto = new UsuarioDTO();
    CadastroDTO cadastrodto = new CadastroDTO();
    CadastroDAO cadastrodao = new CadastroDAO();
    PedidosDTO pedidosdto = new PedidosDTO();
    PedidosDAO pedidosdao = new PedidosDAO();
    Random gerador = new Random();
    Scanner entrada = new Scanner(System.in);
    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public void menuInicial(String usuariologin) {
        LocalDate data = LocalDate.now();
        System.out.println("\n|......................[ MENU INICIAL ].....................|");
        System.out.println("\n    [ "+usuariologin+" ]                             [ "+data+" ]");
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
            case 2: 
                if (usuariodto.getStatusCaixa() <= 0) {
                    System.out.println("\n>> ERRO: O caixa está fechado. <<");
                    menuInicial(cadastrodto.getUsuarioLogin()); break;
                } else {
                    menuPedidos(); 
                    break;
                }
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
            System.out.println(erro);
        }
    }


    public void menuAdministracao() {
        System.out.println("\n|......................[ ADMINISTRAÇÃO ]......................|");
        System.out.println("\n               ...............................\n"+
                           "               :  1. ABRIR CAIXA             :\n"+
                           "               :  2. ENCERRAR CAIXA          :\n"+
                           "               :  3. CADASTRAR FUNCIONÁRIO   :\n"+
                           "               :  4. REMOVER FUNCIONÁRIO     :\n"+
                           "               :  5. MENU INICIAL            :\n"+
                           "               :.............................:\n");
        opcaoMenuAdministracao();
    }

    public void opcaoMenuAdministracao() {
        if(cadastrodto.getUsuarioLogin().equals("admin")) {
            switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
                case 1: abrirCaixa(); break;
                case 2: encerrarCaixa(); break;
                case 3: cadastrarFuncionario(); break;
                case 4: removerFuncionario(); break;
                case 5: menuInicial(cadastrodto.getUsuarioLogin());break;
                default:System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXX OPÇÃO INVÁLDA XXXXXXXXXXXXXXXXXXXXXXXX\n");opcaoMenuAdministracao();
            }

        } else {
            System.out.println(">> ERRO: Não possui permissão de acesso. <<");
            menuInicial(cadastrodto.getUsuarioLogin());
        }
    }

    public void abrirCaixa() {
        if (usuariodto.getStatusCaixa() > 0) {
            System.out.println("\n>> ERRO: Caixa já aberto. <<");
            menuAdministracao();
        } else {
            usuariodto.setStatusCaixa(1);
            LocalDateTime data = LocalDateTime.now();
            String dataAbertura = data.format(formatoData);
            System.out.println("\n[ CAIXA ABERTO EM "+dataAbertura+" ]");
            menuInicial(cadastrodto.getUsuarioLogin());
        }
    }

    public void encerrarCaixa() {
        
        LocalDateTime data = LocalDateTime.now();
        String dataFechamento = data.format(formatoData);
        System.out.println("\n[ CAIXA FECHADO EM "+dataFechamento+" ]");

    }

    public void cadastrarFuncionario() {
        System.out.println("\n|................[ CADASTRO DE FUNCIONÁRIO ]................|");
        System.out.print("\n> PRIMEIRO NOME: ");
        String nome = entrada.next();
        cadastrodto.setNomeFuncionario(nome);
        System.out.print("\n> CPF: ");
        String cpf = entrada.next();
        cadastrodto.setCpfFuncionario(cpf);
        escolherFuncao();
        cadastrodao.cadastrarFuncionarioDAO(cadastrodto);
        if (cadastrodto.getSetorFuncionario().equals("CAIXA")) {
            criarSenha();
        } else {
            System.out.println("\n> Cadastro efetuado.");
            menuAdministracao();
        }
        
    }

    public void escolherFuncao() {
        System.out.println("\n1. CAIXA\n"+"2. COZINHA\n"+"3. DELIVERY");
        System.out.print("\n> INFORME O SETOR: ");
        int setor = entrada.nextInt();
        switch(setor) {
            case 1: cadastrodto.setSetorFuncionario("CAIXA"); break;
            case 2: cadastrodto.setSetorFuncionario("COZINHA"); break;
            case 3: cadastrodto.setSetorFuncionario("DELIVERY"); break;
            default: System.out.println("\n>> ERRO: Opção inválida. <<"); escolherFuncao(); break;
        }
    }

    public void criarSenha() {
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
            System.out.println(">> ERRO: Senhas divergentes. <<");
            criarSenha();
        }
    }

    public void removerFuncionario() {

        try {

            verificaFuncionarios();
            System.out.print("\nINFORME O CPF DO FUNCIONÁRIO QUE DESEJA REMOVER: ");
            String cpf = entrada.next();
            cadastrodto.setCpfFuncionario(cpf);
            ResultSet rscadastrodao = cadastrodao.verificaCpfDAO(cadastrodto);
            if(rscadastrodao.next()) {
                String setor = rscadastrodao.getString("DSC_SETOR");
                if (setor.equals("CAIXA")) {
                    cadastrodao.removerLoginDAO(cadastrodto);
                    cadastrodao.removerFuncionarioDAO(cadastrodto);
                    System.out.println("\n> Funcionário removido.");
                    menuAdministracao();
                } else {
                    cadastrodao.removerFuncionarioDAO(cadastrodto);
                    System.out.println("\n> Funcionário removido.");
                    menuAdministracao();
                }
            } else {
                System.out.println("\n>> ERRO: O CPF informado não consta na base de dados. <<");
                menuAdministracao();
            }

            
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void visualizarFuncionarios() {
        System.out.println("\n|......................[ FUNCIONÁRIOS ]......................|");
        try {
            ResultSet rscadastrodao = cadastrodao.visualizarFuncionariosDAO(cadastrodto);
            while(rscadastrodao.next()) {
                String cpf = rscadastrodao.getString("CPF_FUNCIONARIO");
                String nome = rscadastrodao.getString("NOM_FUNCIONARIO");
                String funcao = rscadastrodao.getString("DSC_SETOR");
                System.out.println("\n>> "+cpf+" | "+nome.toUpperCase()+" | "+funcao.toUpperCase());
            }
            
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void verificaFuncionarios() {
        try {
            
            ResultSet rscadastrodao = cadastrodao.visualizarFuncionariosDAO(cadastrodto);
            if (rscadastrodao.next()) {
                visualizarFuncionarios();
            } else {
                System.out.println("\n> Nenhum cadastro encontrado.");
                menuAdministracao();
            }
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    
    public void menuPedidos(){
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

    public void opcaoMenuPedidos() {
        switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
            case 1: 
                pedidosdto.setNumeroPedido(gerador.nextInt(899) + 100);
                pedidosdao.cadastrarPedidoDAO(pedidosdto);
                cadastrarPedido(); break;
            case 2: cancelarPedido(); break;
            case 3: confirmarPedido(); break;
            case 4: verificaPendentes(); menuPedidos(); break;
            case 5:menuInicial(cadastrodto.getUsuarioLogin());break;
            default:System.out.println("> ERRO: Opção inválida."); opcaoMenuPedidos();
        }
    }

    public void menuProdutos() {
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
        menuProdutos();
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
                menuPedidos(); break;
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
            verificaPendentes();
            System.out.print("\nINFORME O Nº DO PEDIDO QUE DESEJA CONFIRMAR: ");
            int pedido = entrada.nextInt();
            pedidosdto.setNumeroPedido(pedido);
            ResultSet rspedidosdao = pedidosdao.verificaNumeroPedidoDAO(pedidosdto);
            if (rspedidosdao.next()) {
                pedidosdao.confirmarPedidoDAO(pedidosdto);
                System.out.println("\n> Pedido confirmado.");
                menuPedidos();
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
            verificaPendentes();
            System.out.print("\nINFORME O Nº DO PEDIDO QUE DESEJA CANCELAR: ");
            int pedido = entrada.nextInt();
            pedidosdto.setNumeroPedido(pedido);
            ResultSet rspedidosdao = pedidosdao.verificaNumeroPedidoDAO(pedidosdto);
            if (rspedidosdao.next()) {
                pedidosdao.cancelarItensDAO(pedidosdto);
                pedidosdao.cancelarPedidoDAO(pedidosdto);
                System.out.println("\n Pedido cancelado.");
                menuPedidos();
                
            } else {
                System.out.println("\n> Esse pedido não existe.");
                cancelarPedido();
            }
            
        } catch (SQLException erro) {
            System.out.println(erro);
        }
    }

    public void verificaPendentes() {
        try {
            ResultSet rspedidosdao = pedidosdao.pedidosPendentesDAO(pedidosdto);
            if(rspedidosdao.next()){
                pedidosPendentes();
            } else {
                System.out.println("\n> ERRO: Não existem pedidos pendentes.");
                menuPedidos();
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

    public static void main(String[] args) {
        Galeto teste = new Galeto();
        teste.login();
       
    }
}