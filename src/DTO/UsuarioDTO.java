package DTO;

import java.util.Scanner;
import java.util.ArrayList;
public class UsuarioDTO {

    Scanner entrada = new Scanner(System.in);
    private ArrayList<String> pendentes = new ArrayList<String>();
    private ArrayList<String> pedido = new ArrayList<String>();
    private ArrayList<String> numerosPedidos = new ArrayList<String>();
    private ArrayList<Double> valoresPedido = new ArrayList<Double>();
    private Double[] valores = {24.00, 30.00, 15.00, 20.00, 15.00, 6.00, 7.50};
    private String[] produtos = {"GALETO","CHURRASCO BOI","COMERCIAL GALETO","COMERCIAL BOI","FRITAS","PÃO DE ALHO","GUARANÁ JESUS"};
    private double valorTotalPedido = 0, valorProduto;
    private int index, opcao, quantidade;
    private String password, user, modo, numeroPedido, pedidoCompleto = "";


    public double getValorTotalPedido() {
        return valorTotalPedido;
    }
    public void setValorTotalPedido(double valorTotalPedido) {
        this.valorTotalPedido = valorTotalPedido;
    }
    public double getValorProduto() {
        return valorProduto;
    }
    public void setValorProduto(int indice) {
        this.valorProduto = this.valores[indice] * getQuantidade();
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getOpcao() {
        return opcao;
    }
    public int setOpcao(String texto) {
        System.out.print(texto);
        this.opcao = entrada.nextInt();
        return this.opcao;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getModo() {
        return modo;
    }
    public void setModo(String modo) {
        this.modo = modo;
    }
    public String getNumeroPedido() {
        return numeroPedido;
    }
    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    public String getPedidoCompleto() {
        return pedidoCompleto;
    }
    public void setPedidoCompleto(String pedidoCompleto) {
        this.pedidoCompleto = pedidoCompleto;
    }
    public ArrayList<String> getPendentes() {
        return pendentes;
    }
    public void addPendentes(String item) {
        this.pendentes.add(item);
    }
    public void removePendentes(int indice) {
        this.pendentes.remove(indice);
    }
    public ArrayList<String> getPedido() {
        return pedido;
    }
    public void addPedido(String item) {
        this.pedido.add(item);
    }
    public ArrayList<String> getNumerosPedidos() {
        return numerosPedidos;
    }
    public void addNumerosPedidos(String item) {
        this.numerosPedidos.add(item);
    }
    public ArrayList<Double> getValoresPedido() {
        return valoresPedido;
    }
    public void addValoresPedido(double valor) {
        this.valoresPedido.add(valor);
    }
    public Double getValores(int indice) {
        return valores[indice];
    }
    public String getProdutos(int indice) {
        return produtos[indice];
    }
}
