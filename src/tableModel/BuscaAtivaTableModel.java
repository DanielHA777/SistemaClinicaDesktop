package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.BuscaAtiva;



public class BuscaAtivaTableModel extends AbstractTableModel{
	 private List<BuscaAtiva> buscaativa;
	    
	    //vetor com nomes das colunas
	    private final String[] COLUNAS  =  {"id","colaborador", "temperatura", "data"};
	    
	    // construtor q recebe uma lista de clientes
	    public BuscaAtivaTableModel(List<BuscaAtiva> lista) {
	    	this.buscaativa = lista;
		}   
		
		@Override
		public int getRowCount() {     // quantas linhas
			//numero de linhas é o numero de clientes da lista
			return buscaativa.size();
		}

		@Override
		public int getColumnCount() { // saber quantas colunas
			// numero de colunas é o comprimento do vetor
			return COLUNAS.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) { // saber quantas celulas //aceita qualquer retorno
			// retira da lista o obj na posição orwIndex
			BuscaAtiva b = buscaativa.get(rowIndex);
			//verifica qual coluna
			switch (columnIndex) {   // pra cada coluna no programa faz um case aqui
			//se for coluna 0, retorna o nome
			case 0: 
				return b.getId();
						
				// se for coluna 1 retorna telefone
			case 1: 
				return b.getNome();
			
			case 2: 
				return b.getTemp();
			
			case 3: 
				return b.getData();
			}
			return null;
		}
		@Override
		public String getColumnName(int column) {
			// retorna o valor da posição do vetor COLUNAS
			return COLUNAS[column]; // CABEÇALHO DA TABELA
		}
}
