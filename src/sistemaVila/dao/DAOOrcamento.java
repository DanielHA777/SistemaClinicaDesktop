package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.Statement;

import sistemaVila.modelo.Cliente;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Exame;
import sistemaVila.modelo.FormaPgto;
import sistemaVila.modelo.ItemExame;
import sistemaVila.modelo.ItemVenda;
import sistemaVila.modelo.Orcamento;
import sistemaVila.modelo.Produto;
import sistemaVila.modelo.Venda;

public class DAOOrcamento implements InterfaceCrud<Orcamento>{
	private Connection conexao;
	public DAOOrcamento() {
		// obtém conexao da fabrica de conexao
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Orcamento objeto) throws SQLException, Exception {
		conexao.setAutoCommit(false);
		String sql = "insert into orcamento( clienteNome, forma_Pgto, convenio, data, vtotal) values( ?, ?,?,? ,?)"; 		
		PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
		
		comando.setString(1, objeto.getNomeCliente());
		comando.setInt(2, objeto.getPgto().ordinal());
		comando.setBoolean(3, objeto.isConvenio());
		comando.setString(4, objeto.getData());
		comando.setDouble(5, objeto.getvTotal());
		 try {
		    	comando.execute();
		    	ResultSet chaves = comando.getGeneratedKeys();
		    	if(chaves.next()) {
		    		objeto.setId(chaves.getInt(1));
		    	}else {
		    		throw new Exception("Erro ao inserir o orçamento");
		    	}
		    	String sql2 = "insert into itemPedido(exame_id, qtd, total, orcamento_id) values(?,?,?,?)";
		    	for(ItemExame i : objeto.getItens()) {
		    		PreparedStatement comando2 = conexao.prepareStatement(sql2);
		    		comando2.setInt(1, i.getExame().getId());
		    		comando2.setInt(2, i.getQtd());
		    		comando2.setDouble(3, i.getTotal());
		    		comando2.setInt(4, objeto.getId());
		    		comando2.execute();
		    		comando2.close();
		    		conexao.commit();
		    	}
		    } catch (Exception e) {
				conexao.rollback();
				e.printStackTrace();
				throw e; 
			}finally {
				conexao.setAutoCommit(true);
			}
		
	}


	@Override
	public List<Orcamento> listar() throws SQLException {
		String sql = "select * from orcamento where data order by id desc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		List<Orcamento> pedidos = new ArrayList<>();
		ResultSet rs = comando.executeQuery();
		while(rs.next()) {
			Orcamento p = new Orcamento();
			p.setId(rs.getInt("id"));
			//Calendar dataPed =  Calendar.getInstance();
			//dataPed.setTimeInMillis(rs.getTimestamp("data").getTime()); 
			//p.setData(dataPed);
			p.setConvenio(rs.getBoolean("convenio"));
			p.setObs(rs.getString("obs"));
			p.setTaxaColeta(rs.getBoolean("taxaColeta"));
			p.setPgto(FormaPgto.values()[rs.getInt("pgto")]);
			p.setData(rs.getString("data"));
			Cliente c = new Cliente();
			c.setNome(rs.getString("clienteNome"));
			c.setTelefone(rs.getString("telefone"));
			p.setCliente(c);
			List<ItemExame> itens = new ArrayList<>();
			do {
				if(p.getId() != rs.getInt("id")) {
					rs.previous();
					break;
				}
				ItemExame item = new ItemExame();
				item.setId(rs.getInt("id"));
				item.setQtd(rs.getInt("qtd"));
				item.setTotal(rs.getDouble("total"));
			//	item.set(rs.getString("obs_item"));
				Exame pr = new Exame();
				pr.setNome(rs.getString("produto"));
				item.setExame(pr);
				itens.add(item);
			}while(rs.next());
			p.setItens(itens);
			pedidos.add(p);
			}
		rs.close();
		comando.close();
		return pedidos;
		
	}

	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
		
		return null;
	}
	@Override
	public void atualizar(Orcamento objeto) throws SQLException {
		
		
	}
	@Override
	public void excluir(Orcamento objeto) throws SQLException {
		
		
	}

}
