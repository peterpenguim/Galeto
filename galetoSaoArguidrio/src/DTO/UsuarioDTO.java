package DTO;

import java.util.Scanner;
public class UsuarioDTO {

    Scanner entrada = new Scanner(System.in);
    private int opcao;

    public int getOpcao() {
        return opcao;
    }
    public int setOpcao(String texto) {
        System.out.print(texto);
        this.opcao = entrada.nextInt();
        return this.opcao;
    }
}