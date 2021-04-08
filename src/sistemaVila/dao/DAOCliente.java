package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaVila.modelo.Cliente;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Especialidade;

public class DAOCliente implements InterfaceCrud<Cliente> {
	private Connection conexao;
	public DAOCliente() {
		conexao = ConnectionFactory.getConnection();
	}
	
	@Override
	public void inserir(Cliente objeto) throws SQLException, Exception {
		String sql = "insert into cliente(nome, telefone, email, endereco) values(?, ?, ?, ?)";  
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getTelefone());
		comando.setString(3, objeto.getEmail());
		comando.setString(4, objeto.getEndereco());
		comando.execute();
		comando.close();
	}

	@Override
	public void atualizar(Cliente objeto) throws SQLException {
		String sql = "update into colaborador(nome = ?, telefone = ?, email = ?, endereco = ? where id = ?)";  
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getTelefone());
		comando.setString(3, objeto.getEmail());
		comando.setString(4, objeto.getEndereco());
		comando.setInt(5, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public void excluir(Cliente objeto) throws SQLException {
		String sql = "delete from cliente where id = ?";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public List<Cliente> listar() throws SQLException {
		List<Cliente> lista = new ArrayList<>(); 
		String sql = "select * from cliente order by nome asc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery();
		while(rs.next()) {
		     Cliente c = new Cliente();
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setTelefone(rs.getString("telefone"));
			c.setEmail(rs.getString("email"));
			c.setEndereco(rs.getString("endereco"));
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;				
	}
	public List<Cliente> listar(String parametro) throws SQLException { 
		List<Cliente> lista = new ArrayList<>(); 
		String sql = "select * from cliente where nome like ?  order by nome asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
			Cliente c = new Cliente();
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setTelefone(rs.getString("telefone")); 
			c.setEmail(rs.getString("Email"));	
			c.setEndereco(rs.getString("endereco"));
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;
	}
	public Cliente buscaNome(String nome) throws SQLException {			
		Cliente c = null; 
		String sql = "select * from cliente where nome = ?";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,nome);
		ResultSet rs = comando.executeQuery(); 
		if(rs.next()) {
			 c = new Cliente();
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setTelefone(rs.getString("telefone")); 
			c.setEmail(rs.getString("Email"));	
			c.setEndereco(rs.getString("endereco"));
		}
		rs.close();
		comando.close();
		return c;
	}
	public List<Cliente> buscaCli(){
		  List<Cliente> lista = new ArrayList<Cliente>();
			try {
				  String sql = "select * from cliente";
				PreparedStatement comando = conexao.prepareStatement(sql);
			//	comando.setString(1, );
				ResultSet rs = comando.executeQuery();
				
				while(rs.next()) {
					Cliente p = new Cliente();
					//p.getId();
					p.setId(rs.getInt("id"));
					p.setNome(rs.getString("nome"));
					lista.add(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lista;  
	  }

	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
