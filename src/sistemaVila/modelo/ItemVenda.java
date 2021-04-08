package sistemaVila.modelo;

public class ItemVenda {
	private int id;
	private Produto produto;
	private int qtd;
	private double total;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {	
		this.qtd = qtd;
		if (this.id == 0) {
				this.total = this.getProduto().getValor() * qtd;
			}
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	private String observacao;
}
