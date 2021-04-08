package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Especialidade;
import sistemaVila.modelo.Residente;

public class DAOResidente implements InterfaceCrud<Residente> {
	private Connection conexao;

	public DAOResidente() {
		conexao = ConnectionFactory.getConnection();
	}

	@Override
	public void inserir(Residente objeto) throws SQLException, Exception {
		String sql = "insert into residentes(nome, idade, grau) values(?, ?, ?)";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getIdade());
		comando.setString(3, objeto.getGrau());
		comando.execute();
		comando.close();
	}

	@Override
	public void atualizar(Residente objeto) throws SQLException {
		String sql = "update residentes set nome = ?, idade = ?, grau = ? where id = ?";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getIdade());
		comando.setString(3, objeto.getGrau());
		comando.setInt(4, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public void excluir(Residente objeto) throws SQLException {
		String sql = "delete from residentes where id = ?";
		// comando para o banco de dados
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());
		comando.execute();
		comando.close();

	}

	@Override
	public List<Residente> listar() throws SQLException {
		List<Residente> lista = new ArrayList<>();
		String sql = "select * from residentes order by nome asc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery(); // query consulta
		while (rs.next()) {
			Residente c = new Residente();
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setIdade(rs.getString("idade"));
			c.setGrau(rs.getString("grau"));
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;
	}

	public List<Residente> listar(String parametro) throws SQLException {
		List<Residente> lista = new ArrayList<>();
		String sql = "select * from residentes where nome like ?  order by nome asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, "%" + parametro + "%");
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			Residente c = new Residente();
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setIdade(rs.getString("idade"));
			c.setGrau(rs.getString("grau"));
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;
	}

	public Residente buscaNome(String nome) throws SQLException {
		Residente c = null;
		String sql = "select * from residentes where nome = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, nome);
		ResultSet rs = comando.executeQuery();
		if (rs.next()) {
			c = new Residente();
			//c.getId();
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setIdade(rs.getString("idade"));
			c.setGrau(rs.getString("grau"));
		}
		rs.close();
		comando.close();
		return c;
	}
	public List<Residente> buscaResi(){
		  List<Residente> lista = new ArrayList<Residente>();
			try {
				  String sql = "select * from residentes";
				PreparedStatement comando = conexao.prepareStatement(sql);
			//	comando.setString(1, p.getNome());
				ResultSet rs = comando.executeQuery();
				while(rs.next()) {
					Residente p = new Residente();
					p.setId(rs.getInt("id"));
					p.setNome(rs.getString("nome"));
					p.setIdade(rs.getString("idade"));
					p.setGrau(rs.getString("grau"));
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
