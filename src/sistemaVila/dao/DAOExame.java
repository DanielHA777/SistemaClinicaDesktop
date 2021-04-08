package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Exame;
import sistemaVila.modelo.Produto;

public class DAOExame implements InterfaceCrud<Exame> {
	private Connection conexao;
	public DAOExame() {
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Exame objeto) throws SQLException, Exception {
		String sql = "insert into Exame(nome, observacao, preco) values(?,?,?)";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getObservacao());
		comando.setDouble(3, objeto.getPreco());
		comando.execute();
		comando.close();
		
	}
	@Override
	public void atualizar(Exame objeto) throws SQLException {
		String sql = "update Exame set nome = ?, observacao = ?, preco = ? where id = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getObservacao());
		comando.setDouble(3, objeto.getPreco());
		comando.setInt(4, objeto.getId());
		comando.execute();
		comando.close();
		
	}
	@Override
	public void excluir(Exame objeto) throws SQLException {
		String sql = "delete from  exame where id = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());
		comando.execute();
		comando.close();
	}
	@Override
	public List<Exame> listar() throws SQLException {
		List<Exame> lista = new ArrayList<>();
		String sql = "select * from Exame order by nome asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery();
		while(rs.next()) {
			Exame p = new Exame();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setObservacao(rs.getString("observacao"));
			p.setPreco(rs.getDouble("preco"));
			
			lista.add(p);
		}
		rs.close();
		comando.close();
		return lista;
	}
	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
	
		return null;
	}
}
