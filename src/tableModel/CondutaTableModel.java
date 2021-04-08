package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Conduta;

public class CondutaTableModel  extends AbstractTableModel {
	 private List<Conduta> conduta;
	    
	    //vetor com nomes das colunas
	    private final String[] COLUNAS  =  {"id","colaborador", "sintoma", "data", "conduta"};
	    
	    // construtor q recebe uma lista de clientes
	    public CondutaTableModel(List<Conduta> lista) {
	    	this.conduta = lista;
		}   
		
		public int getRowCount() {     // quantas linhas
			//numero de linhas é o numero de clientes da lista
			return conduta.size();
		}

		public int getColumnCount() { // saber quantas colunas
			// numero de colunas é o comprimento do vetor
			return COLUNAS.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) { // saber quantas celulas //aceita qualquer retorno
			// retira da lista o obj na posição orwIndex
			Conduta b = conduta.get(rowIndex);
			//verifica qual coluna
			switch (columnIndex) {   // pra cada coluna no programa faz um case aqui
			//se for coluna 0, retorna o nome
			case 0: 
				return b.getId();
						
				// se for coluna 1 retorna telefone
			case 1: 
				return b.getColaborador().getNome();
			
			case 2: 
				return b.getSintomas();
			
			case 3: 
				return b.getData();
			case 4: 
				return b.getConduta();
			}
			return null;
		}
		public String getColumnName(int column) {
			// retorna o valor da posição do vetor COLUNAS
			return COLUNAS[column]; // CABEÇALHO DA TABELA
		}
}
