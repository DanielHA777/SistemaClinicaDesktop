package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Conduta;
import sistemaVila.modelo.Fisio;
import sistemaVila.modelo.Mascara;
import sistemaVila.modelo.Modelo;
import sistemaVila.modelo.Residente;
import sistemaVila.modelo.Sessao;

public class DAOMascara implements InterfaceCrud<Mascara> {
	private Connection conexao;
	public DAOMascara() {
		// obtém conexao da fabrica de conexao
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Mascara objeto) throws SQLException, Exception {
		String sql = "insert into mascara(nomeCola, qtd, modelo, data) values(?, ?,?,?)"; 		
		PreparedStatement comando = conexao.prepareStatement(sql/*Statement.RETURN_GENERATED_KEYS*/); 
		
		//comando.setInt(1, objeto.getColaborador().getId());
		comando.setString(1, objeto.getNomeCola());
		comando.setInt(2, objeto.getQtd());
		//comando.setInt(4, objeto.getEstoque());
		comando.setInt(3, objeto.getModelo().ordinal());
		comando.setString(4, objeto.getData());
		comando.execute();
		comando.close();
	}

	@Override
	public void atualizar(Mascara objeto) throws SQLException {
		String sql = "update mascara  set nomeCola =?, qtd = ?,  modelo = ?, data = ? where id = ?";  
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
	//	comando.setInt(1, objeto.getColaborador().getId());
		comando.setString(2, objeto.getNomeCola());
		comando.setInt(3, objeto.getQtd());
		//comando.setInt(4, objeto.getEstoque());
		comando.setInt(4, objeto.getModelo().ordinal());
		comando.setString(5, objeto.getData());
		comando.setInt(6, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public void excluir(Mascara objeto) throws SQLException {
		String sql = "delete from mascara where id = ?"; 
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());  	
		comando.execute();
		comando.close();	
	}

	@Override
	public List<Mascara> listar() throws SQLException {
		List<Mascara> lista = new ArrayList<>(); 
		String sql = "select * from mascara order by id asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery(); 
		while(rs.next()) {
		     Mascara b = new Mascara();
			b.setId(rs.getInt("id"));
			b.setQtd(rs.getInt("qtd"));
			b.setModelo(Modelo.values()[rs.getInt("modelo")]);
		//	b.setEstoque(rs.getInt("estoque"));
			b.setData(rs.getString("data"));
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
	public List<Mascara> listar(String parametro) throws SQLException { 
		// cria lista de clientes para retornar 
		List<Mascara> lista = new ArrayList<>(); 
		
		String sql = "select * from mascara where data like ?  order by data asc";
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1,"%" +parametro+"%");
				
		ResultSet rs = comando.executeQuery(); 
	
		while(rs.next()) {
			Mascara c = new Mascara();
			c.setId(rs.getInt("id"));
			c.setData(rs.getString("Data"));
			c.setQtd(rs.getInt("qtd"));
			//b.setColaborador(rs.getString("colaborador"));
			c.setModelo(Modelo.values()[rs.getInt("modelo")]);
			//c.setEstoque(rs.getInt("estoque"));
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nomeCola"));
			cc.setId(rs.getInt("colaborador_id"));
			c.setColaborador(cc);
			lista.add(c);
		}
		return lista;
	}
	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
		return null;
	}

}
