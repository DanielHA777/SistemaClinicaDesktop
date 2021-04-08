package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Conduta;
import sistemaVila.modelo.Exame;


public class ExameTableModel extends AbstractTableModel {
	 private List<Exame> Exames;
	 private final String[] COLUNAS  =  {"id","Nome", "Preço", "Descrição"};
	 
	 public ExameTableModel(List<Exame> lista) {
	    	this.Exames = lista;
		}   
	@Override
	public int getRowCount() {
		
		return Exames.size();
	}

	@Override
	public int getColumnCount() {
		
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Exame b = Exames.get(rowIndex);
		//verifica qual coluna
		switch (columnIndex) {   // pra cada coluna no programa faz um case aqui
		//se for coluna 0, retorna o nome
		case 0: 
			return b.getId();
					
			// se for coluna 1 retorna telefone
		case 1: 
			return b.getNome();
		
		case 2: 
			return b.getPreco();
		
		case 3: 
			return b.getObservacao();
		}
		return null;
	}
	public String getColumnName(int column) {
		// retorna o valor da posição do vetor COLUNAS
		return COLUNAS[column]; // CABEÇALHO DA TABELA
	}

}
