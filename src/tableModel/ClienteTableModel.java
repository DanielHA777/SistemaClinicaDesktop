package tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sistemaVila.modelo.Cliente;
import sistemaVila.modelo.Colaborador;

public class ClienteTableModel extends AbstractTableModel{
	private List<Cliente> cliente;
    private final String[] COLUNAS  =  {"id","nome", "telefone", "email", "endereco"};
    public ClienteTableModel(List<Cliente> lista) {
    	this.cliente = lista;
	}   
	
	@Override
	public int getRowCount() {     
		return cliente.size();
	}

	@Override
	public int getColumnCount() { 
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) { 
		Cliente c = cliente.get(rowIndex);
		switch (columnIndex) {   
		case 0: 
			return c.getId();
		case 1:
			return c.getNome();
		case 2: 
			return c.getTelefone();
		case 3: 
			return c.getEmail();
		case 4: 
			return c.getEndereco();
		}
		return null;
	}
	@Override
	public String getColumnName(int column) {
		// retorna o valor da posição do vetor COLUNAS
		return COLUNAS[column]; // CABEÇALHO DA TABELA
	}
}
