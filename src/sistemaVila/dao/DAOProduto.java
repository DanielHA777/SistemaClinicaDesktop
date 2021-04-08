package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Produto;

public class DAOProduto implements InterfaceCrud<Produto>{
	private Connection conexao;
	public DAOProduto() {
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Produto objeto) throws SQLException, Exception {
		String sql = "insert into Produto(nome, descricao, valor, imagem) values(?,?,?,?)";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getDescricao());
		comando.setDouble(3, objeto.getValor());
		comando.setBytes(4, objeto.getImgProduto());
		comando.execute();
		comando.close();
	}

	@Override
	public void atualizar(Produto objeto) throws SQLException {
		String sql = "update Produto set nome = ?, descricao = ?, valor = ?, imagem = ? where id = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getDescricao());
		comando.setDouble(3, objeto.getValor());
		comando.setBytes(4, objeto.getImgProduto());
		comando.setInt(5, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public void excluir(Produto objeto) throws SQLException {
		String sql = "delete from  produto where id = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public List<Produto> listar() throws SQLException {
		List<Produto> lista = new ArrayList<>();
		String sql = "select * from Produto order by nome asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery();
		while(rs.next()) {
			Produto p = new Produto();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getString("descricao"));
			p.setValor(rs.getDouble("valor"));
			p.setImgProduto(rs.getBytes("imagem"));
			lista.add(p);
		}
		rs.close();
		comando.close();
		return lista;
	}
	public List<Produto> listar(String parametro) throws SQLException { 	
		List<Produto> lista = new ArrayList<>(); 
		String sql = "select * from produto where nome = ? order by nome asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
			Produto p = new Produto();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getNString("descricao"));
			p.setValor(rs.getDouble("valor"));
			p.setImgProduto(rs.getBytes("imagem"));
			lista.add(p);
		}
		rs.close();
		comando.close();
		return lista;
	}
	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
