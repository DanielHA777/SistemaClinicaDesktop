package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Colaborador;



public class ColaboradorTableModel extends AbstractTableModel {
	 private List<Colaborador> colaborador;
	    
	    //vetor com nomes das colunas
	    private final String[] COLUNAS  =  {"nome", "turno"};
	    
	    // construtor q recebe uma lista de clientes
	    public ColaboradorTableModel(List<Colaborador> lista) {
	    	this.colaborador = lista;
		}   
		
		@Override
		public int getRowCount() {     // quantas linhas
			//numero de linhas é o numero de clientes da lista
			return colaborador.size();
		}

		@Override
		public int getColumnCount() { // saber quantas colunas
			// numero de colunas é o comprimento do vetor
			return COLUNAS.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) { // saber quantas celulas //aceita qualquer retorno
			// retira da lista o obj na posição orwIndex
			Colaborador c = colaborador.get(rowIndex);
			//verifica qual coluna
			switch (columnIndex) {   // pra cada coluna no programa faz um case aqui
			//se for coluna 0, retorna o nome
			case 0: 
				return c.getNome();
				// se for coluna 1 retorna telefone
			case 1: 
				return c.getTurno();
			}
			return null;
		}
		@Override
		public String getColumnName(int column) {
			// retorna o valor da posição do vetor COLUNAS
			return COLUNAS[column]; // CABEÇALHO DA TABELA
		}
}
