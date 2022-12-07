package DTO;

public class PedidosDTO {
    
    private String produtoEscolhido;
    private int quantidadeEscolhida, numeroPedido, codProduto;
    private double valorItens, valorPedido;
    public Double[] valoresUnitarios = {24.00, 30.00, 15.00, 20.00, 15.00, 6.00, 7.50};
    public String[] produtos = {"GALETO          ","CHURRASCO BOI   ","COMERCIAL GALETO","COMERCIAL BOI   ","FRITAS          ","PÃO DE ALHO     ","GUARANÁ JESUS   "};

    public String getProdutoEscolhido() {
        return produtoEscolhido;
    }
    public void setProdutoEscolhido(String produtoEscolhido) {
        this.produtoEscolhido = produtoEscolhido;
    }
    public int getQuantidadeEscolhida() {
        return quantidadeEscolhida;
    }
    public void setQuantidadeEscolhida(int quantidadeEscolhida) {
        this.quantidadeEscolhida = quantidadeEscolhida;
    }
    public int getNumeroPedido() {
        return numeroPedido;
    }
    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    public double getValorItens() {
        return valorItens;
    }
    public void setValorItens(double valorItens) {
        this.valorItens = valorItens;
    }
    public double getValorPedido() {
        return valorPedido;
    }
    public void setValorPedido(double valorPedido) {
        this.valorPedido = valorPedido;
    }
    public int getCodProduto() {
        return codProduto;
    }
    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }
}
