package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import sistemaVila.modelo.Cliente;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.FormaPgto;
import sistemaVila.modelo.ItemVenda;
import sistemaVila.modelo.Produto;
import sistemaVila.modelo.Venda;

public class DAOVendas implements InterfaceCrud<Venda> {
	private Connection conexao;
	public DAOVendas() {
		// obtém conexao da fabrica de conexao
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Venda p) throws SQLException, Exception {
		conexao.setAutoCommit(false);
		String sql = "insert into venda(cliente_id, end_entrega, retirada, tx_entrega, vTotal, observacao, forma_pgto) values(?,?,?,?,?,?,?)";
		PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    comando.setInt(1, p.getCliente().getId());
    comando.setString(2, p.getEndEntrega());
    comando.setBoolean(3, p.isRetirada());
    comando.setDouble(4, p.getTxentrega());
    comando.setDouble(5, p.getvTotal());
    comando.setString(6, p.getObservacao());
    comando.setInt(7, p.getFormaPgto().ordinal());
    try {
    	comando.execute();
    	ResultSet chaves = comando.getGeneratedKeys();
    	if(chaves.next()) {
    		p.setNumero(chaves.getInt(1));
    	}else {
    		throw new Exception("Erro ao inserir a venda");
    	}
    	String sql2 = "insert into itemvenda(produto_id, qtd, total, observacao, venda_numero) values(?,?,?,?,?)";
    	for(ItemVenda i : p.getItens()) {
    		PreparedStatement comando2 = conexao.prepareStatement(sql2);
    		comando2.setInt(1, i.getProduto().getId());
    		comando2.setInt(2, i.getQtd());
    		comando2.setDouble(3, i.getTotal());
    		comando2.setString(4, i.getObservacao());
    		comando2.setInt(5, p.getNumero());
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
	public void atualizar(Venda p) throws SQLException {
		String sql = "update venda set cliente_id = ?, end_entrega = ?, retirada = ?, tx_entrega = ?,  vTotal = ?, observacao = ?, forma_pgto=? where id = ?";   
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		 comando.setInt(1, p.getCliente().getId());
		    comando.setString(2, p.getEndEntrega());
		    comando.setBoolean(3, p.isRetirada());
		    comando.setDouble(4, p.getTxentrega());
		    comando.setDouble(5, p.getvTotal());
		    comando.setString(6, p.getObservacao());
		    comando.setInt(7, p.getFormaPgto().ordinal());
		    comando.setInt(8, p.getNumero());
		comando.execute();
		comando.close();
	}

	@Override
	public void excluir(Venda objeto) throws SQLException {
		String sql = "delete from venda where numero = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1,  objeto.getNumero());
		comando.execute();
		comando.close();
	}

	@Override
	public List<Venda> listar() throws SQLException {
		String sql = "select * from  data order by numero desc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		List<Venda> pedidos = new ArrayList<>();
		ResultSet rs = comando.executeQuery();
		while(rs.next()) {
			Venda p = new Venda();
			p.setNumero(rs.getInt("numero"));
			Calendar dataPed =  Calendar.getInstance();
			dataPed.setTimeInMillis(rs.getTimestamp("data").getTime()); 
			p.setData(dataPed);
			p.setEndEntrega(rs.getString("end_entrega"));
			p.setRetirada(rs.getBoolean("retirada"));
			p.setTxentrega(rs.getDouble("tx_entrega"));
			p.setvTotal(rs.getDouble("vtotal"));
			p.setObservacao(rs.getString("observacao"));
			p.setFormaPgto(FormaPgto.values()[rs.getInt("forma_pgto")]);
			Cliente c = new Cliente();
			c.setNome(rs.getString("nome_cliente"));
			c.setTelefone(rs.getString("telefone"));
			p.setCliente(c);
			List<ItemVenda> itens = new ArrayList<>();
			do {
				if(p.getNumero() != rs.getInt("numero")) {
					rs.previous();
					break;
				}
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setQtd(rs.getInt("qtd"));
				item.setTotal(rs.getDouble("total"));
				item.setObservacao(rs.getString("obs_item"));
				Produto pr = new Produto();
				pr.setNome(rs.getString("produto"));
				item.setProduto(pr);
				itens.add(item);
			}while(rs.next());
			p.setItens(itens);
			pedidos.add(p);
			}
		rs.close();
		comando.close();
		return pedidos;
	}
	public List<Venda> procurar(String parametro) throws SQLException {
		String sql = "select * from  view_pedidos where nome_cliente like ? or numero like ? order by numero desc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, "%"+parametro+"%");
		comando.setString(2, "%"+parametro+"%");
		List<Venda> vendas = new ArrayList<>();
		ResultSet rs = comando.executeQuery();
		while(rs.next()) {
			Venda p = new Venda();
			p.setNumero(rs.getInt("numero"));
			Calendar dataPed =  Calendar.getInstance();
			dataPed.setTimeInMillis(rs.getTimestamp("data").getTime());
			p.setData(dataPed);
			p.setEndEntrega(rs.getString("end_entrega"));
			p.setRetirada(rs.getBoolean("retirada"));
			p.setTxentrega(rs.getDouble("tx_entrega"));
			p.setvTotal(rs.getDouble("vtotal"));
			p.setObservacao(rs.getString("observacao"));
			p.setFormaPgto(FormaPgto.values()[rs.getInt("forma_pgto")]);
			Cliente c = new Cliente();
			c.setNome(rs.getString("nome_cliente"));
			c.setTelefone(rs.getString("telefone"));
			p.setCliente(c);
			List<ItemVenda> itens = new ArrayList<>();
			do {
				if(p.getNumero() != rs.getInt("numero")) {	
					rs.previous();
					break;
				}
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setQtd(rs.getInt("qtd"));
				item.setTotal(rs.getDouble("total"));
				item.setObservacao(rs.getString("obs_item"));
				Produto pr = new Produto();
				pr.setNome(rs.getString("produto"));
				item.setProduto(pr);
				itens.add(item);
			}while(rs.next());
			p.setItens(itens);
		
			vendas.add(p);
			}
		rs.close();
		comando.close();
		return vendas;
	}
	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
