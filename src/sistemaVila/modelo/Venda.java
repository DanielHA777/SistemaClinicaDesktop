package sistemaVila.modelo;

import java.util.Calendar;
import java.util.List;

public class Venda {
	private int numero;
	private Cliente cliente;
	private String endEntrega;
	private Calendar data;
	private boolean retirada;
	private List<ItemVenda> itens;
	public List<ItemVenda> getItens() {
		return itens;
	}
	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	private double txentrega;
	//private double troco;
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getEndEntrega() {
		return endEntrega;
	}
	public void setEndEntrega(String endEntrega) {
		this.endEntrega = endEntrega;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public boolean isRetirada() {
		return retirada;
	}
	public void setRetirada(boolean retirada) {
		this.retirada = retirada;
	}
	public double getTxentrega() {
		return txentrega;
	}
	public void setTxentrega(double txentrega) {
		this.txentrega = txentrega;
	}
	public double getvTotal() {
		return vTotal;
	}
	public void setvTotal(double vTotal) {
		this.vTotal = vTotal;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public FormaPgto getFormaPgto() {
		return formaPgto;
	}
	public void setFormaPgto(FormaPgto formaPgto) {
		this.formaPgto = formaPgto;
	}
	private double vTotal;
	private String observacao;
	private FormaPgto formaPgto;
	//private List<ItemPedido> itens;
	//private Cola funcionario;
}
