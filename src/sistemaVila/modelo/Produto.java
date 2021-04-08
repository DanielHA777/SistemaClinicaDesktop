package sistemaVila.modelo;

public class Produto {
private int id;
private String nome;
private String descricao;
private double valor;
private byte[] imgProduto;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getDescricao() {
	return descricao;
}
public void setDescricao(String descricao) {
	this.descricao = descricao;
}
public double getValor() {
	return valor;
}
public void setValor(double valor) {
	this.valor = valor;
}
public byte[] getImgProduto() {
	return imgProduto;
}
public void setImgProduto(byte[] imgProduto) {
	this.imgProduto = imgProduto;
}
}
