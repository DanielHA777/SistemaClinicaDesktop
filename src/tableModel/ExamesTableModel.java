package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Exame;
import sistemaVila.modelo.ItemExame;
import sistemaVila.modelo.Produto;

public class ExamesTableModel extends AbstractTableModel{
private List<ItemExame> produtos;
    private final String[] COLUNAS  =  {"nome", "qtd", "preco"};
    public ExamesTableModel(List<ItemExame> lista) {
    	this.produtos= lista;
	}   
	
	@Override
	public int getRowCount() {     
		return produtos.size();
	}

	@Override
	public int getColumnCount() { 
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) { 
		ItemExame p = produtos.get(rowIndex);
		switch (columnIndex) {  
		case 0: 
			return p.getExame().getNome();
		case 1: 
			return p.getQtd();
		case 2: 
			return String.format("R$ %6.2f",p.getExame().getPreco());
	}return null;}
	@Override
	public String getColumnName(int column) {
		return COLUNAS[column]; 
	}
}