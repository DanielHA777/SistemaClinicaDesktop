package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Colaboradores;
import sistemaVila.modelo.Especialidade;
import sistemaVila.modelo.Fisio;
import sistemaVila.modelo.Residente;
import sistemaVila.modelo.Sessao;

public class DAOFisio implements InterfaceCrud<Fisio> {
	private Connection conexao;
	public DAOFisio() {
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Fisio objeto) throws SQLException, Exception {
		//conexao.setAutoCommit(false);
		String sql = "insert into fisio( nomeCola, NomeResi, sessao, data) values( ?, ?,?,?)"; 		
		PreparedStatement comando = conexao.prepareStatement(sql/*Statement.RETURN_GENERATED_KEYS*/); 
		
		//comando.setInt(1, objeto.getColaborador().getId());
		comando.setString(1, objeto.getNomeCola());
		comando.setString(2, objeto.getNomeResi());
		comando.setInt(3, objeto.getSessao().ordinal());  
		//comando.setInt(4, objeto.getResidente().getId());
		comando.setString(4, objeto.getData());
		comando.execute();
		comando.close();
	/*	try {
		    	comando.execute();
		    	ResultSet chaves = comando.getGeneratedKeys();
		    	if(chaves.next()) {
		    		objeto.setId(chaves.getInt(1));
		    	}else {
		    		throw new Exception("Erro ao inserir a sessão");
		    	}
		    	String sql2 = "insert into residentes(id, nome, idade, grau) values(?,?,?,?)";
		    	for(Residente i : objeto.getResis()) {
		    		PreparedStatement comando2 = conexao.prepareStatement(sql2);
		    		comando2.setInt(1, i.getId());
		    		comando2.setString(2, i.getNome());
		    		comando2.setString(3, i.getIdade());
		    		comando2.setString(4, i.getGrau());
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
			}*/
	}

	@Override
	public void atualizar(Fisio objeto) throws SQLException {
		String sql = "update fisio set colaborador_id = ?, nomeCola =?, NomeResi = ?, sessao = ?, residente_id = ?, data = ? where id = ?";  
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
	public void excluir(Fisio objeto) throws SQLException {
		String sql = "delete from fisio where id = ?"; 
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());  	
		comando.execute();
		comando.close();	
	}

	@Override
	public List<Fisio> listar() throws SQLException {
		List<Fisio> lista = new ArrayList<>(); 
		String sql = "select * from fisio order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
		     Fisio b = new Fisio();
			b.setId(rs.getInt("id"));
			//b.setCola(Colaboradores.values()[rs.getInt("colaborador")]);
			//b.setNome(rs.getString("nome"));
			//b.setColaborador(rs.getString("nome"));
			b.setSessao(Sessao.values()[rs.getInt("sessao")]);
			b.setData(rs.getString("data"));
			Residente c = new Residente();
			c.setId(rs.getInt("residente_id"));
			c.setNome(rs.getString("nomeResi"));
			b.setResidente(c);;
			Colaborador cc = new Colaborador();
			cc.setId(rs.getInt("colaborador_id"));
			cc.setNome(rs.getString("nomeCola"));
			//cc.setTurno(rs.getString("turno"));
			b.setColaborador(cc);
			lista.add(b);
		}
		rs.close();
		comando.close();
		return lista;						
	}
	public List<Fisio> listarSoma(int i) throws SQLException {
		List<Fisio> lista = new ArrayList<>(); 
		 String sql= "SELECT SUM(sessao) FROM fisio";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +i+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
		     Fisio b = new Fisio();
			b.setSessao(Sessao.values()[rs.getInt("sessao")]);
			lista.add(b);
		}
		rs.close();
		comando.close();
		return lista ;						
	}
	public List<Fisio> Procurar(String parametro) throws SQLException { 
		List<Fisio> lista = new ArrayList<>(); 
		String sql = "select * from fisio where colaborador like ?  order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {		
		Fisio c = new Fisio();		
			c.setId(rs.getInt("id"));
			//c.setCola(Colaboradores.values()[rs.getInt("colaborador")]);
		//	c.setNome(rs.getString("nome"));
			c.setSessao(Sessao.values()[rs.getInt("sessao")]);
			c.setData(rs.getString("data"));
			Residente r = new Residente();
			
			r.setId(rs.getInt("residente_id"));
			r.setNome(rs.getString("nomeResi"));
			c.setResidente(r);;
			Colaborador cc = new Colaborador();
			c.setId(rs.getInt("colaborador_id"));
			cc.setNome(rs.getString("nomeCola"));
			//cc.setTurno(rs.getString("turno"));
			c.setColaborador(cc);
			lista.add(c);
		}	
		rs.close();		
		comando.close();		
		return lista;
	}
	
	public List<Fisio> listar(String parametro) throws SQLException { 
		List<Fisio> lista = new ArrayList<>(); 
		String sql = "select * from fisio where nomeCola like ?  order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
			Fisio c = new Fisio();
			c.setId(rs.getInt("id"));
			c.setSessao(Sessao.values()[rs.getInt("sessao")]);
			c.setData(rs.getString("data"));
			Residente r = new Residente();
			r.setNome(rs.getString("nomeResi"));
			r.setId(rs.getInt("residente_id"));
			c.setResidente(r);
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			cc.setId(rs.getInt("colaborador_id"));
			//cc.setTurno(rs.getString("turno"));
			c.setColaborador(cc);
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;
	}
	public List<Fisio> listarResi(String parametro) throws SQLException { 
		List<Fisio> lista = new ArrayList<>(); 
		String sql = "select * from fisio where nomeResi like ?  order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
			Fisio c = new Fisio();
			c.setId(rs.getInt("id"));
			c.setSessao(Sessao.values()[rs.getInt("sessao")]);
			c.setData(rs.getString("data"));
			Residente r = new Residente();
			r.setNome(rs.getString("nomeResi"));
			r.setId(rs.getInt("residente_id"));
			c.setResidente(r);
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			cc.setId(rs.getInt("colaborador_id"));
			//cc.setTurno(rs.getString("turno"));
			c.setColaborador(cc);
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;
	}
	public List<Fisio> listarSessao(int i) throws SQLException { 
		List<Fisio> lista = new ArrayList<>(); 
		String sql = "select * from fisio where sessao like ?  order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, i);
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
			Fisio c = new Fisio();
			c.setId(rs.getInt("id"));
			c.setSessao(Sessao.values()[rs.getInt("sessao")]);
			c.setData(rs.getString("data"));
			Residente r = new Residente();
			r.setNome(rs.getString("nomeResi"));
			r.setId(rs.getInt("residente_id"));
			c.setResidente(r);
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			cc.setId(rs.getInt("colaborador_id"));
			//cc.setTurno(rs.getString("turno"));
			c.setColaborador(cc);
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;
	}
	public List<Fisio> listarSessaoF(int i, String nome) throws SQLException { 
		List<Fisio> lista = new ArrayList<>(); 
		String sql = "select * from fisio where sessao like ? and nomeCola like?  order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, i);
		comando.setString(2, nome);
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
			Fisio c = new Fisio();
			c.setId(rs.getInt("id"));
			c.setSessao(Sessao.values()[rs.getInt("sessao")]);
			c.setData(rs.getString("data"));
			Residente r = new Residente();
			r.setNome(rs.getString("nomeResi"));
			r.setId(rs.getInt("residente_id"));
			c.setResidente(r);
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			cc.setId(rs.getInt("colaborador_id"));
			//cc.setTurno(rs.getString("turno"));
			c.setColaborador(cc);
			lista.add(c);
		}
		rs.close();
		comando.close();
		return lista;
	}
	public List<Fisio> listarData(String parametro) throws SQLException { 
		List<Fisio> lista = new ArrayList<>(); 
		String sql = "select * from fisio where data like ?  order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
			Fisio c = new Fisio();
			c.setId(rs.getInt("id"));
			c.setSessao(Sessao.values()[rs.getInt("sessao")]);
			c.setData(rs.getString("data"));
			Residente r = new Residente();
			r.setNome(rs.getString("nomeResi"));
			r.setId(rs.getInt("residente_id"));
			c.setResidente(r);
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			cc.setId(rs.getInt("colaborador_id"));
			//cc.setTurno(rs.getString("turno"));
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
