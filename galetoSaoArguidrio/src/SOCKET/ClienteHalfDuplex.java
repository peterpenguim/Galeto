package SOCKET;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteHalfDuplex {
    public static void main(String args[]) {

        try {
            // se conecta com o servidor
            Socket cliente = new Socket("127.0.0.1", 12349);
            System.out.println("Cliente conectado ao servidor.");
    
            // pra enviar mensagens para o servidor
            System.out.println("Enviar mensagem: ");
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
    
            // pra ler as mensagens do servidor
            BufferedReader bfrecebe = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
    
            // pra ler os dados do teclado
            BufferedReader bfentrada = new BufferedReader(new InputStreamReader(System.in));
            String envia, recebe;
    
            // loop pra o cliente continuar saindo até ele se desconectar do servidor
            while (!(envia = bfentrada.readLine()).equals("exit")) {
    
                // Enviar mensagens para o servidor
                dos.writeBytes(envia + "\n");
    
                // receber mensagens do servidor
                recebe = bfrecebe.readLine();
    
                System.out.println("Servidor: " + recebe);
            }
    
            // fecha a conexão com o servidor
            dos.close();
            bfrecebe.close();
            bfentrada.close();
            cliente.close();
    
        } 
        catch (Exception e) {
            System.out.println("O cliente não conseguiu se conectar ao servidor");
        }
    }
}