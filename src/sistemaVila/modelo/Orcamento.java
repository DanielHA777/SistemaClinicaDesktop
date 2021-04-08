package sistemaVila.modelo;

import java.util.List;

public class Orcamento {
private int id;
private Cliente cliente;
private FormaPgto pgto;
private boolean taxaColeta;
private boolean convenio;
private String nomeCliente;
private String data;
private double vTotal;
private String obs;
private List<ItemExame> itens;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Cliente getCliente() {
	return cliente;
}
public void setCliente(Cliente cliente) {
	this.cliente = cliente;
}
public FormaPgto getPgto() {
	return pgto;
}
public void setPgto(FormaPgto pgto) {
	this.pgto = pgto;
}
public boolean isTaxaColeta() {
	return taxaColeta;
}
public void setTaxaColeta(boolean taxaColeta) {
	this.taxaColeta = taxaColeta;
}
public boolean isConvenio() {
	return convenio;
}
public void setConvenio(boolean convenio) {
	this.convenio = convenio;
}
public String getObs() {
	return obs;
}
public void setObs(String obs) {
	this.obs = obs;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public List<ItemExame> getItens() {
	return itens;
}
public void setItens(List<ItemExame> itens) {
	this.itens = itens;
}
public double getvTotal() {
	return vTotal;
}
public void setvTotal(double vTotal) {
	this.vTotal = vTotal;
}
public String getNomeCliente() {
	return nomeCliente;
}
public void setNomeCliente(String nomeCliente) {
	this.nomeCliente = nomeCliente;
}
}
