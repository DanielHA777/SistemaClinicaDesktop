package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Fono;

public class FonoTableModel extends AbstractTableModel{
	 private List<Fono> fono;
	    private final String[] COLUNAS  =  {"id", "colaborador", "residente", "sessao", "data"};
	    public FonoTableModel(List<Fono> lista) {
	    	this.fono = lista;
		}   
		
		@Override
		public int getRowCount() {     
			return fono.size();
		}

		@Override
		public int getColumnCount() { 
			return COLUNAS.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) { 
			Fono c = fono.get(rowIndex);
			switch (columnIndex) {  
			case 0: 
				return c.getId();
			case 1: 
				return c.getColaborador().getNome();
			case 2: 
				return c.getResidente().getNome();
			case 3: 
				return c.getSessao();
			case 4: 
				return c.getData();
			}
			return null;
		}
		@Override
		public String getColumnName(int column) {
			return COLUNAS[column]; 
		}
}
