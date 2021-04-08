package sistemaVila.modelo;

public class Mascara {
private int qtd;
private int id;
private Colaborador colaborador;
private Modelo modelo;
private String nomeCola;
private int estoque;
private String data;
public int getQtd() {
	return qtd;
}
public void setQtd(int qtd) {
	this.qtd = qtd;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Colaborador getColaborador() {
	return colaborador;
}
public void setColaborador(Colaborador colaborador) {
	this.colaborador = colaborador;
}

public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public int getEstoque() {
	return estoque;
}
public void setEstoque(int estoque) {
	this.estoque = estoque;
}
public Modelo getModelo() {
	return modelo;
}
public void setModelo(Modelo modelo) {
	this.modelo = modelo;
}
public String getNomeCola() {
	return nomeCola;
}
public void setNomeCola(String nomeCola) {
	this.nomeCola = nomeCola;
}


}
