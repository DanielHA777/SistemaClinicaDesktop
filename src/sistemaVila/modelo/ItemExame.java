package sistemaVila.modelo;

public class ItemExame {
	private int id;
	private Exame exame;
	private int qtd;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Exame getExame() {
		return exame;
	}
	public void setExame(Exame exame) {
		this.exame = exame;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
		if (this.id == 0) {
				this.total = this.getExame().getPreco() * qtd;
			}
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	private double total;
}
