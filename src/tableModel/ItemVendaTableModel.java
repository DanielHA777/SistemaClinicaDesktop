package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import sistemaVila.modelo.ItemVenda;

public class ItemVendaTableModel extends AbstractTableModel{
	private List<ItemVenda> itens;
	private String[] colunas = {"qtd", "produto", "Observação", "total"};
	
	public ItemVendaTableModel(List<ItemVenda> lista) {
		this.itens = lista;
	}
	@Override
	public int getRowCount() {
		
		return itens.size();
	}

	@Override
	public int getColumnCount() {
		
		return colunas.length;
	}
	public String getColumnName(int column) {
		
		return colunas[column];
	}
	@Override
	public Object getValueAt(int linha, int coluna) {
		ItemVenda item = itens.get(linha);
		switch (coluna) {
		case 0:
			return item.getQtd();
		case 1:
			return item.getProduto().getNome();
		case 2:
			return item.getObservacao();
		case 3:
			return String.format("R$ %6.2f", item.getTotal());
		default:
		return null;
	}
	}
}
