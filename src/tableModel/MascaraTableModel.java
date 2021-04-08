package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Conduta;
import sistemaVila.modelo.Mascara;

public class MascaraTableModel extends AbstractTableModel {
	 private List<Mascara> mascara;
	    private final String[] COLUNAS  =  {"id","colaborador", "qtd","data", "modelo"};
	    
	    public MascaraTableModel(List<Mascara> lista) {
	    	this.mascara = lista;
		}   

	@Override
	public int getRowCount() {
		
		return mascara.size();
	}

	@Override
	public int getColumnCount() {
		
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Mascara b = mascara.get(rowIndex);
		switch (columnIndex) {  
		case 0: 
			return b.getId();

		case 1: 
			return b.getColaborador().getNome();
		
		case 2: 
			return b.getQtd();
		
		case 3: 
			return b.getData();
		case 4: 
			return b.getModelo();
		}
		return null;
	}
	public String getColumnName(int column) {
		return COLUNAS[column]; 
	}

}
