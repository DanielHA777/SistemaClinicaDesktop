package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Fisio;
import sistemaVila.modelo.Mascara;

public class FisiosTableModel extends AbstractTableModel{
	 private List<Fisio> fisio;
	    private final String[] COLUNAS  =  {"id","colaborador_id", "nomeCola","nomeResi", "sessao", "residente_id", "data"};
	    
	    public FisiosTableModel(List<Fisio> lista) {
	    	this.fisio = lista;
		}   
	@Override
	public int getRowCount() {
		return fisio.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Fisio c = fisio.get(rowIndex);
		switch (columnIndex) {  
		case 0: 
			return c.getId();
		case 1: 
			return c.getColaborador().getId();
		case 2: 
			return c.getColaborador().getNome();
		case 3: 
			return c.getResidente().getNome();
		case 4: 
			return c.getSessao();
		case 5: 
			return c.getResidente().getId();
		case 6:
			return c.getData();
		}
		return null;
	}
	public String getColumnName(int column) {
		// retorna o valor da posição do vetor COLUNAS
		return COLUNAS[column]; // CABEÇALHO DA TABELA
	}

}
