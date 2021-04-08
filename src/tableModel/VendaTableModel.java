package tableModel;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import sistemaVila.modelo.Venda;

public class VendaTableModel extends AbstractTableModel{
	  private List<Venda> vendas;
	    private final String[] COLUNAS  =  {"N", "data", "cliente", "valor"};
	    
	    public VendaTableModel(List<Venda> lista) {
	    	this.vendas= lista;
		}   
		@Override
		public int getRowCount() {
			return vendas.size();
		}
		@Override
		public int getColumnCount() {
			return COLUNAS.length;
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Venda p = vendas.get(rowIndex);
			switch (columnIndex) {   
			case 0: 
				return p.getNumero();
			case 1: 
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm" );
				return formatador.format(p.getData().getTime()); 
			case 2: 
				return p.getCliente().getNome();
			case 3: 
				return String.format("R$ %6.2f",p.getvTotal());
		}
			return null;
		}
		public String getColumnName(int column) {
			return COLUNAS[column]; 
		}
}
