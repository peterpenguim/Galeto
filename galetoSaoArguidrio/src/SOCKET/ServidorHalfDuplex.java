package SOCKET;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorHalfDuplex {
    public static void main(String args[]) {

        try {
            // abre o servidor e aguarda conexão
            ServerSocket servidor = new ServerSocket(12349);
            servidor.setReuseAddress(true);
            System.out.println("Servidor aberto");
            System.out.println("Aguardando conexão");
            
            // se conecta com o cliente
            Socket cliente = servidor.accept();
            System.out.println("Conexão estabelecida com o cliente");
    
            // para enviar dados para o cliente
            PrintStream ps = new PrintStream(cliente.getOutputStream());
    
            // buffer para ler as mensagens que vem do cliente
            BufferedReader bfrecebe = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
    
            // buffer ler dados do teclado
            BufferedReader bfentrada = new BufferedReader(new InputStreamReader(System.in));
    
            // loop pra o servidor continuar rodando
            while (true) {
    
                String str, str1;
    
                // ler as mensagens do cliente e imprimir na tela
                while ((str = bfrecebe.readLine()) != null) {
                    System.out.println("Cliente: " + str);
                    str1 = bfentrada.readLine();
    
                    // mensagem pra enviar pra o cliente
                    ps.println(str1);
                }

                // fim do servidor
    
                System.out.println("Servidor fechado.");
                ps.close();
                bfrecebe.close();
                bfentrada.close();
                servidor.close();
                cliente.close();
    
                System.exit(0);
  
            }
        } 
        catch (Exception e) {
            System.out.println("Aconteceu um problema ao iniciar o servidor");
        }
    }
}
