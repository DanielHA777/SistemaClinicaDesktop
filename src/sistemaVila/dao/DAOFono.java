package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Fisio;
import sistemaVila.modelo.Fono;
import sistemaVila.modelo.Residente;
import sistemaVila.modelo.Sessao;

public class DAOFono implements InterfaceCrud<Fono> {
	private Connection conexao;
	public DAOFono() {
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Fono objeto) throws SQLException, Exception {
		String sql = "insert into fono(nomeCola, NomeResi, sessao, data) values(?, ?,?,?)"; 		
		PreparedStatement comando = conexao.prepareStatement(sql/*Statement.RETURN_GENERATED_KEYS*/); 
		
	//	comando.setInt(1, objeto.getColaborador().getId());
		comando.setString(1, objeto.getNomeCola());
		comando.setString(2, objeto.getNomeResi());
		comando.setInt(3, objeto.getSessao().ordinal());  
	//	comando.setInt(5, objeto.getResidente().getId());
		comando.setString(4, objeto.getData());
		comando.execute();
		comando.close();
	}

	@Override
	public void atualizar(Fono objeto) throws SQLException {
		String sql = "update fono set colaborador_id = ?, nomeCola =?, NomeResi = ?, sessao = ?, residente_id = ?, data = ? where id = ?";  
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getColaborador().getId());
		comando.setString(2, objeto.getColaborador().getNome());
		comando.setString(3, objeto.getResidente().getNome());
		comando.setInt(4, objeto.getSessao().ordinal());  
		comando.setInt(5, objeto.getResidente().getId());
		comando.setString(6, objeto.getData());
		comando.setInt(7, objeto.getId());
		comando.execute();
		comando.close();
		
	}

	@Override
	public void excluir(Fono objeto) throws SQLException {
		String sql = "delete from fono where id = ?"; 
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());  	
		comando.execute();
		comando.close();	
		
	}

	@Override
	public List<Fono> listar() throws SQLException {
		List<Fono> lista = new ArrayList<>(); 
		String sql = "select * from fono order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
		     Fono b = new Fono();
			b.setId(rs.getInt("id"));
			b.setSessao(Sessao.values()[rs.getInt("sessao")]);
			b.setData(rs.getString("data"));
			Residente c = new Residente();
			c.setNome(rs.getString("nomeResi"));
			b.setResidente(c);;
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			cc.setId(rs.getInt("colaborador_id"));
			b.setColaborador(cc);
			lista.add(b);
		}
		rs.close();
		comando.close();
		return lista;
	}
	public List<Fono> listar(String parametro) throws SQLException { 
		List<Fono> lista = new ArrayList<>(); 
		String sql = "select * from fono where nomeCola like ?  order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {		
		Fono c = new Fono();		
			c.setId(rs.getInt("id"));
			c.setSessao(Sessao.values()[rs.getInt("sessao")]);
			c.setData(rs.getString("data"));
			Residente r = new Residente();
			r.setNome(rs.getString("nomeResi"));
			r.setId(rs.getInt("residente_id"));
			c.setResidente(r);;
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			c.setId(rs.getInt("colaborador_id"));
			c.setColaborador(cc);
			lista.add(c);
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
