package sistemaVila.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaVila.modelo.BuscaAtiva;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Colaboradores;
import sistemaVila.modelo.Conduta;
import sistemaVila.modelo.Especialidade;

public class DAOConduta  implements InterfaceCrud<Conduta>{
	private Connection conexao;
	public DAOConduta() {
		// obtém conexao da fabrica de conexao
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(Conduta objeto) throws Exception {
		String sql = "insert into Conduta(colaborador_id,nome,  data, sintomas, descricao, conduta) values(?, ?, ?, ?, ?, ?)";  // string com a instrução sql
		// comando para o banco de dados		
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
		//comando.setInt(1, objeto.getCola().ordinal());  // 1 primeia ?
		comando.setInt(1, objeto.getColaborador().getId());
		comando.setString(2, objeto.getColaborador().getNome());
		comando.setString(3, objeto.getData());  // 2 segunda ?
		comando.setString(4, objeto.getSintomas());  // ...
		comando.setString(5, objeto.getDescricaoString());
		comando.setString(6, objeto.getConduta());
		//executa o comando
		comando.execute();
		// encerra comando
		comando.close();	
	}
	@Override
	public void atualizar(Conduta objeto) throws SQLException {
		String sql = "update Conduta set colaborador_id = ?,nome = ?, data = ?, sintomas = ?, descricao = ?, conduta = ? where id = ?";  
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getColaborador().getId());
		comando.setString(2, objeto.getColaborador().getNome());
		comando.setString(3, objeto.getData());
		comando.setString(4,  objeto.getSintomas());
		comando.setString(5, objeto.getDescricaoString());
		comando.setString(6, objeto.getConduta());
		comando.setInt(7, objeto.getId());
		comando.execute();
		comando.close();		
	}
	@Override
	public void excluir(Conduta objeto) throws SQLException {
		String sql = "delete from conduta where id = ?";  // string com a instrução sql
		// comando para o banco de dados		
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
		comando.setInt(1, objeto.getId());  // 1 primeia ?	
		//executa o comando
		comando.execute();
		// encerra comando
		comando.close();			
	}
	@Override
	public List<Conduta> listar() throws SQLException {
		List<Conduta> lista = new ArrayList<>(); // passa linha por linha no bd no cliente e retorna
		// Strin gcom a instrução sql
		String sql = "select * from conduta order by nome asc";
		//cria comando
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		//cria o resultSet para percorrer os registros do banco
		ResultSet rs = comando.executeQuery(); // query consulta
		//percorrer o result Set, ele começa na linha -1
		//enquanto houver linhas
		while(rs.next()) {
			//cria obj cliente
		     Conduta c = new Conduta();
			//popula o  obj cliente
			c.setId(rs.getInt("id"));
			//c.setCola(Colaboradores.values()[rs.getInt("colaboradores")]);
			c.setNome(rs.getString("nome"));
			c.setData(rs.getString("Data"));
			c.setSintomas(rs.getString("Sintomas"));
			c.setDescricaoString(rs.getString("descricao"));
			c.setConduta(rs.getString("conduta"));
			//adcionar o cliente a lista
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nome"));
			cc.setId(rs.getInt("colaborador_id"));
			//cc.setTurno(rs.getString("turno"));
			c.setColaborador(cc);
			lista.add(c);
		}
		//fechar resultSet
		rs.close();
		//fechar o statement
		comando.close();
		//retorna liosta
		return lista;				
	}
	public List<Conduta> listar(String parametro) throws SQLException { // metodo com mesmo nome mas assinatura difeente	
		// cria lista de clientes para retornar 
		List<Conduta> lista = new ArrayList<>(); // passa linha por linha no bd no cliente e retorna
		// Strin gcom a instrução sql
		String sql = "select * from conduta where nome like ?  order by nome asc";
		//cria comando
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		// substitui as 3 ?
		comando.setString(1,"%" +parametro+"%");
				
		//cria o resultSet para percorrer os registros do banco
		ResultSet rs = comando.executeQuery(); // query consulta
		//percorrer o result Set, ele começa na linha -1
		//enquanto houver linhas
		while(rs.next()) {
			//cria obj cliente
			Conduta c = new Conduta();
			//popula o  obj cliente
			c.setId(rs.getInt("id"));
			//c.setCola(Colaboradores.values()[rs.getInt("colaboradores")]);
			c.setData(rs.getString("Data"));
			c.setSintomas(rs.getString("Sintoma"));
			c.setDescricaoString(rs.getString("descricao"));
			c.setConduta(rs.getString("conduta"));
			Colaborador cc = new Colaborador();
			cc.setNome(rs.getString("nome"));
			cc.setId(rs.getInt("colaborador_id"));
			//cc.setTurno(rs.getString("turno"));
			c.setColaborador(cc);
			lista.add(c);
		}
		return lista;
}
	public Colaborador buscaNome(String nome) throws SQLException { // metodo com mesmo nome mas assinatura difeente	
		// cria obj  de clientes para retornar 
		Colaborador c = null; // passa linha por linha no bd no cliente e retorna
		// Strin gcom a instrução sql
		String sql = "select * from colaborador where nome = ?";
		//cria comando
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		// substitui  ? por telefone
		comando.setString(1,nome);
				
		//cria o resultSet para percorrer os registros do banco
		ResultSet rs = comando.executeQuery(); // query consulta
		//percorrer o result Set, ele começa na linha -1
		//enquanto houver linhas
		if(rs.next()) {
			//cria obj cliente
			 c = new Colaborador();
			//instancia o  obj cliente
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setSenha(rs.getString("senha")); 
			c.setTurno(rs.getString("turno"));	
		}
		//fechar resultSet
		rs.close();
		//fechar o statement
		comando.close();
		//retorna liosta
		return c;
	}
	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}	
}
