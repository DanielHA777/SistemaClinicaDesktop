package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Fisio;


public class FisioTableModel extends AbstractTableModel{
	 private List<Fisio> fisio;
	    private final String[] COLUNAS  =  {"id", "colaborador", "residente", "sessao", "data"};
	    public FisioTableModel(List<Fisio> lista) {
	    	this.fisio = lista;
		}   
		
		@Override
		public int getRowCount() {     
			return fisio.size();
		}

		@Override
		public int getColumnCount() { 
			return COLUNAS.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) { 
			Fisio c = fisio.get(rowIndex);
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
