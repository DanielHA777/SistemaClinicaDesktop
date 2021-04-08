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

public class DAOcolaborador implements InterfaceCrud<Colaborador> {
	private Connection conexao;
	public DAOcolaborador() {
		// obtém conexao da fabrica de conexao
		conexao = ConnectionFactory.getConnection();
	}
	
	@Override
	public void inserir(Colaborador objeto) throws SQLException {
		String sql = "insert into colaborador(nome, especialidade, senha, turno) values(?, ?, ?, ?)";  // string com a instrução sql
		// comando para o banco de dados		
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
		comando.setString(1, objeto.getNome());  // 1 primeia ?
		comando.setInt(2, objeto.getEspe().ordinal());  // 2 segunda ?
		comando.setString(3, objeto.getSenha());  // ...
		comando.setString(4, objeto.getTurno());
		//executa o comando
		comando.execute();
		// encerra comando
		comando.close();
	}

	@Override
	public void atualizar(Colaborador objeto) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update colaborador set nome = ?, especialidade = ?, senha = ?, turno = ? where id = ?";  // string com a instrução sql
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
		// comando para o banco de dados	? por valor do obj	
		
		comando.setString(1, objeto.getNome());  // 1 primeia ?
		comando.setInt(2, objeto.getEspe().ordinal());;  // 2 segunda ?
		//comando.setString(3, objeto.getTelefone());  // ...
		comando.setString(3, objeto.getSenha());;
		comando.setString(4, objeto.getTurno());;
		comando.setInt(5, objeto.getId());
		//executa o comando
		comando.execute();
		// encerra comando
		comando.close();
	}

	@Override
	public void excluir(Colaborador objeto) throws SQLException {
		
				String sql = "delete from colaborador where id = ?";  // string com a instrução sql
				// comando para o banco de dados		
				java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
				comando.setInt(1, objeto.getId());  // 1 primeia ?	
				//executa o comando
				comando.execute();
				// encerra comando
				comando.close();		
	}

	@Override
	public List<Colaborador> listar() throws SQLException {
		// TODO Auto-generated method stub
				// cria lista de clientes para retornar 
				List<Colaborador> lista = new ArrayList<>(); // passa linha por linha no bd no cliente e retorna
				// Strin gcom a instrução sql
				String sql = "select * from colaborador order by nome asc";
				//cria comando
				PreparedStatement comando = conexao.prepareStatement(sql);
				//cria o resultSet para percorrer os registros do banco
				ResultSet rs = comando.executeQuery(); // query consulta
				//percorrer o result Set, ele começa na linha -1
				//enquanto houver linhas
				while(rs.next()) {
					//cria obj cliente
				     Colaborador c = new Colaborador();
					//popula o  obj cliente
					c.setId(rs.getInt("id"));
					c.setNome(rs.getString("nome"));
				//	c.setEspe(Especialidade);
					c.setEspe(Especialidade.values()[rs.getInt("especialidade")]);
					//c.setFormaPgto(FormaPgto.values()[rs.getInt("forma_pgto")]);
					c.setSenha(rs.getString("senha")); 
					c.setTurno(rs.getString("turno"));
					//adcionar o cliente a lista
					lista.add(c);
				}
				//fechar resultSet
				rs.close();
				//fechar o statement
				comando.close();
				//retorna liosta
				return lista;				
	}
	public List<Colaborador> listar(String parametro) throws SQLException { // metodo com mesmo nome mas assinatura difeente	
		// cria lista de clientes para retornar 
		List<Colaborador> lista = new ArrayList<>(); // passa linha por linha no bd no cliente e retorna
		// Strin gcom a instrução sql
		String sql = "select * from colaborador where nome like ?  order by nome asc";
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
			Colaborador c = new Colaborador();
			//popula o  obj cliente
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setSenha(rs.getString("senha")); 
			c.setTurno(rs.getString("turno"));			//adcionar o cliente a lista
			lista.add(c);
		}
		//fechar resultSet
		rs.close();
		//fechar o statement
		comando.close();
		//retorna liosta
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
	public Colaborador selecionar(String nome, String senha) throws SQLException {
		String sql = "select * from colaborador where nome = ? and senha = ?";
		Colaborador f = null;
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, nome);
		comando.setString(2, senha);
		ResultSet rs = comando.executeQuery();	
if (rs.next()) {	
			f = new Colaborador();
			f.setNome(rs.getString("nome"));
			f.setSenha(rs.getString("senha"));	
			f.setEspe(Especialidade.values()[rs.getInt("especialidade")]);
		}
		rs.close();
		comando.close();
		return f;
	}	
	public List<Colaborador> buscaCola(){
		  List<Colaborador> lista = new ArrayList<Colaborador>();
			try {
				  String sql = "select * from colaborador";
				PreparedStatement comando = conexao.prepareStatement(sql);
			//	comando.setString(1, p.getNome());
				ResultSet rs = comando.executeQuery();
				while(rs.next()) {
					Colaborador p = new Colaborador();
					p.setId(rs.getInt("id"));
					p.setNome(rs.getString("nome"));
					lista.add(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lista;  
	  }
	public List<Colaborador> buscaFisio(){
		  List<Colaborador> lista = new ArrayList<Colaborador>();
			try {
				  String sql = "select * from colaborador where especialidade = ?";
				PreparedStatement comando = conexao.prepareStatement(sql);
				comando.setInt(1, Especialidade.Fisioterapeuta.ordinal());
				ResultSet rs = comando.executeQuery();
				
				while(rs.next()) {
					Colaborador p = new Colaborador();
					//p.getId();
					p.setId(rs.getInt("id"));
					p.setNome(rs.getString("nome"));
					p.setSenha(rs.getString("senha"));
					p.setTurno(rs.getString("turno"));
					lista.add(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lista;  
	  }
	public List<Colaborador> buscaFono(){
		  List<Colaborador> lista = new ArrayList<Colaborador>();
			try {
				  String sql = "select * from colaborador where especialidade = ?";
				PreparedStatement comando = conexao.prepareStatement(sql);
				comando.setInt(1, Especialidade.Fonoaudiologa.ordinal());
				ResultSet rs = comando.executeQuery();
				
				while(rs.next()) {
					Colaborador p = new Colaborador();
					//p.getId();
					p.setId(rs.getInt("id"));
					p.setNome(rs.getString("nome"));
					p.setSenha(rs.getString("senha"));
					p.setTurno(rs.getString("turno"));
					lista.add(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lista;  
	  }
}
