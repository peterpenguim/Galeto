package VIEW;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Administracao extends MenuInicial{

    public Administracao(String usuario) {
        cadastrodto.setUsuarioLogin(usuario);
    }

    public Administracao () {

    }

    public void menu() {
        System.out.println("\n|......................[ ADMINISTRAÇÃO ]......................|");
        System.out.println("\n               ...............................\n"+
                           "               :  1. ABRIR CAIXA             :\n"+
                           "               :  2. ENCERRAR CAIXA          :\n"+
                           "               :  3. CADASTRAR FUNCIONÁRIO   :\n"+
                           "               :  4. REMOVER FUNCIONÁRIO     :\n"+
                           "               :  5. MENU INICIAL            :\n"+
                           "               :.............................:\n");
        escolherOpcao();

    }

    public void escolherOpcao() {
        if(cadastrodto.getUsuarioLogin().equals("admin")) {
            switch(usuariodto.setOpcao("> ESCOLHA UMA OPÇÃO: ")) {
                case 1: abrirCaixa(); break;
                case 2: encerrarCaixa(); break;
                case 3: cadastrarFuncionario(); break;
                case 4: removerFuncionario(); break;
                case 5: 
                    MenuInicial inicial = new MenuInicial(cadastrodto.getUsuarioLogin(), pedidosdto.getStatusCaixa());
                    inicial.menu();break;
                default:System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXX OPÇÃO INVÁLDA XXXXXXXXXXXXXXXXXXXXXXXX\n"); escolherOpcao();
            }

        } else {
            System.out.println(">> ERRO: Não possui permissão de acesso. <<");
            MenuInicial inicial = new MenuInicial(cadastrodto.getUsuarioLogin(), pedidosdto.getStatusCaixa());
            inicial.menu();
        }
    }

    public void verificacao() {

        try {
            
            ResultSet rscadastrodao = cadastrodao.visualizarFuncionariosDAO(cadastrodto);
            if (rscadastrodao.next()) {
                visualizarFuncionarios();
            } else {
                System.out.println("\n> Nenhum cadastro encontrado.");
                menu();
            }
        } catch (SQLException erro) {
            System.out.println(erro);
        }

    }

    public void abrirCaixa() {
        if (pedidosdto.getStatusCaixa() > 0) {
            System.out.println("\n>> ERRO: Caixa já aberto. <<");
            menu();
        } else {
            pedidosdto.setStatusCaixa(1);
            LocalDateTime data = LocalDateTime.now();
            String dataAbertura = data.format(formatoData);
            System.out.println("\n[ CAIXA ABERTO EM "+dataAbertura+" ]");
            MenuInicial inicial = new MenuInicial(cadastrodto.getUsuarioLogin(), pedidosdto.getStatusCaixa());
            inicial.menu();
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
            menu();
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
            menu();
        } else {
            System.out.println(">> ERRO: Senhas divergentes. <<");
            criarSenha();
        }
    }

    public void removerFuncionario() {

        try {

            verificacao();
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
                    menu();
                } else {
                    cadastrodao.removerFuncionarioDAO(cadastrodto);
                    System.out.println("\n> Funcionário removido.");
                    menu();
                }
            } else {
                System.out.println("\n>> ERRO: O CPF informado não consta na base de dados. <<");
                menu();
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
}