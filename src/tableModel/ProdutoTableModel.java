package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Produto;


public class ProdutoTableModel extends AbstractTableModel{
private List<Produto> produtos;
    private final String[] COLUNAS  =  {"nome", "obs", "preco"};
    public ProdutoTableModel(List<Produto> lista) {
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
		Produto p = produtos.get(rowIndex);
		switch (columnIndex) {  
		case 0: 
			return p.getNome();
		case 1: 
			return p.getDescricao();
		case 2: 
			return String.format("R$ %6.2f",p.getValor());
	}return null;}
	@Override
	public String getColumnName(int column) {
		return COLUNAS[column]; 
	}
}
