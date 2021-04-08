package sistemaVila.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import sistemaVila.modelo.BuscaAtiva;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Colaboradores;
import sistemaVila.modelo.Residente;

public class DAOBuscaAtiva implements InterfaceCrud<BuscaAtiva>{

	private Connection conexao;
	public DAOBuscaAtiva() {
		// obtém conexao da fabrica de conexao
		conexao = ConnectionFactory.getConnection();
	}
	@Override
	public void inserir(BuscaAtiva objeto) throws Exception {
				String sql = "insert into buscaAtiva(colaborador_id, nome, data, temperatura, febre, cansaco, diarreia, dorDeCabeca, dorcorpo, Vomito, tosse, paladar, descricao, turno) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,? )";  // string com a instrução sql
				// comando para o banco de dados		
				java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
				
				//comando.setObject(1, objeto.getColaboradores());  // 1 primeia ?
				//comando.setInt(1, objeto.getColaborador().getId());
			//	comando.setString(2, objeto.getColaborador().getNome());
			//	comando.setInt(1, objeto.getCola().ordinal());
				comando.setInt(1, objeto.getColaborador().getId());
				comando.setString(2, objeto.getColaborador().getNome());
				comando.setString(3, objeto.getData());
				comando.setDouble(4,  objeto.getTemp());
				comando.setString(5, objeto.getFebre());
				comando.setString(6, objeto.getCansaco());
				comando.setString(7, objeto.getDiarreia());
				comando.setString(8, objeto.getDorCabeca());
				comando.setString(9, objeto.getDorCorpo());
				comando.setString(10, objeto.getNauseaVomito());
				comando.setString(11, objeto.getTosseCoriza());
				comando.setString(12, objeto.getPaladarOlfato());
				comando.setString(13, objeto.getDescricao());
				comando.setString(14, objeto.getColaborador().getTurno());
				
				comando.execute();
				comando.close();
					}

	@Override
	public void atualizar(BuscaAtiva objeto) throws SQLException {
				String sql = "update BuscaAtiva set colaborador_id = ?,nome = ?, data = ?, temperatura = ?, febre = ?, cansaco = ?, diarreia = ?, dorDeCabeca = ?, dorCorpo = ?, Vomito = ?, tosse= ?, paladar = ?, descricao = ?, turno = ? where id = ?";  // string com a instrução sql
				java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
				// comando para o banco de dados	? por valor do obj	
				comando.setInt(1, objeto.getColaborador().getId());
				comando.setString(2, objeto.getColaborador().getNome());
				comando.setString(3, objeto.getData());
				comando.setDouble(4,  objeto.getTemp());
				comando.setString(5, objeto.getFebre());
				comando.setString(6, objeto.getCansaco());
				comando.setString(7, objeto.getDiarreia());
				comando.setString(8, objeto.getDorCabeca());
				comando.setString(9, objeto.getDorCorpo());
				comando.setString(10, objeto.getNauseaVomito());
				comando.setString(11, objeto.getTosseCoriza());
				comando.setString(12, objeto.getPaladarOlfato());
				comando.setString(13, objeto.getDescricao());
				comando.setString(14, objeto.getColaborador().getTurno());
				comando.setInt(15, objeto.getId());
				comando.execute();
				// encerra comando
				comando.close();
	}

	@Override
	public void excluir(BuscaAtiva objeto) throws SQLException {
		
		String sql = "delete from buscaAtiva where id = ?";  // string com a instrução sql
		// comando para o banco de dados		
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);// preparedStatement obriga q trate o erro
		comando.setInt(1, objeto.getId());  // 1 primeia ?	
		//executa o comando
		comando.execute();
		// encerra comando
		comando.close();		
	}

	@Override
	public List<BuscaAtiva> listar() throws SQLException {
		// cria lista de clientes para retornar 
		List<BuscaAtiva> lista = new ArrayList<>(); // passa linha por linha no bd no cliente e retorna
		// Strin gcom a instrução sql
		String sql = "select * from buscaAtiva order by nome asc";
		//cria comando
		java.sql.PreparedStatement comando = conexao.prepareStatement(sql);
		//cria o resultSet para percorrer os registros do banco
		ResultSet rs = comando.executeQuery(); // query consulta
		//percorrer o result Set, ele começa na linha -1
		//enquanto houver linhas
		while(rs.next()) {
			//cria obj cliente
		     BuscaAtiva b = new BuscaAtiva();
			//popula o  obj cliente
			b.setId(rs.getInt("id"));
			b.setNome(rs.getString("nome"));
			b.setData(rs.getString("data"));
		//	c.setEspe(Especialidade);
			//b.setData(rs.get);
			//Calendar dataPed =  Calendar.getInstance();
			//ajusta data no calendar
			//dataPed.setTimeInMillis(rs.getTimestamp("data").getTime()); // recebe um long  // fixme mes q vem
			//seta calendar no pedido
			//b.setData(dataPed);
			b.setFebre(rs.getString("febre"));
			b.setCansaco(rs.getString("Cansaco"));
			b.setDorCabeca(rs.getString("dorDeCabeca"));
			b.setDorCorpo(rs.getString("dorCorpo"));
			b.setDiarreia(rs.getString("Diarreia"));
			b.setNauseaVomito(rs.getString("vomito"));
		b.setTosseCoriza(rs.getString("Tosse"));
		b.setTemp(rs.getDouble("temperatura"));
		b.setPaladarOlfato(rs.getString("paladar"));
		b.setDescricao(rs.getString("descricao"));
		Colaborador c = new Colaborador();
		c.setNome(rs.getString("nome"));
		c.setTurno(rs.getString("turno"));
		b.setColaborador(c);
			//adcionar o cliente a lista
			lista.add(b);
		}
		//fechar resultSet
		rs.close();
		//fechar o statement
		comando.close();
		//retorna liosta
		return lista;						
	}
	public List<BuscaAtiva> listar(String parametro) throws SQLException { // metodo com mesmo nome mas assinatura difeente	
		// cria lista de clientes para retornar 
		List<BuscaAtiva> lista = new ArrayList<>(); // passa linha por linha no bd no cliente e retorna
		// Strin gcom a instrução sql
		String sql = "select * from buscaAtiva where nome like ?  order by nome asc";
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
			BuscaAtiva b = new BuscaAtiva();
			//popula o  obj cliente
			b.setId(rs.getInt("id"));
			b.setNome(rs.getString("nome"));
		//	b.setCola(Colaboradores.values()[rs.getInt("colaboradores")]);
			b.setData(rs.getString("data"));
		//	c.setEspe(Especialidade);
			//b.setData(rs.get);
			b.setFebre(rs.getString("febre"));
			b.setCansaco(rs.getString("cansaco"));
			b.setDorCabeca(rs.getString("dorDeCabeca"));
			b.setDorCorpo(rs.getString("dorCorpo"));
			b.setDiarreia(rs.getString("Diarreia"));
			b.setNauseaVomito(rs.getString("vomito"));
		b.setTosseCoriza(rs.getString("Tosse"));
		b.setTemp(rs.getDouble("temperatura"));
		b.setPaladarOlfato(rs.getString("paladar"));
		b.setDescricao(rs.getString("descricao"));
		Colaborador c = new Colaborador();
		c.setNome(rs.getString("nome"));
		c.setTurno(rs.getString("turno"));
		c.setId(rs.getInt("colaborador_id"));
		b.setColaborador(c);;
		lista.add(b);
						//adcionar o cliente a lis
		}
		//fechar resultSet
		rs.close();
		//fechar o statement
		comando.close();
		//retorna liosta
		return lista;
	}
	public List<BuscaAtiva> procurar(String parametro) throws SQLException {
		String sql = "select * from  buscaAtiva where nome like ?  order by nome desc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, "%"+parametro+"%");
	
		List<BuscaAtiva> buscas = new ArrayList<>();
		ResultSet rs = comando.executeQuery();
		while(rs.next()) {
			// cria pedido
			BuscaAtiva p = new BuscaAtiva();
			//p.setCola(Colaboradores.values()[rs.getInt("colaboradores")]);
			p.setData(rs.getString("data"));
			//criar calendar 
			//Calendar dataPed =  Calendar.getInstance();
			
			//ajusta data no calendar
			//dataPed.setTimeInMillis(rs.getTimestamp("data").getTime()); // recebe um long  // fixme mes q vem
			//seta calendar no pedido
			//p.setData(dataPed);
			p.setFebre(rs.getString("febre"));
			p.setCansaco(rs.getString("cansaço"));
			p.setDorCabeca(rs.getString("dor de cabeça"));
			p.setDorCorpo(rs.getString("dor no corpo"));
			p.setDiarreia(rs.getString("Diarréia"));
			p.setNauseaVomito(rs.getString("Nausea ou vomito"));
		p.setTosseCoriza(rs.getString("Tosse ou coriza"));
		p.setTemp(rs.getDouble("temperatura"));
		p.setPaladarOlfato(rs.getString("perda de paladar/olfato"));
		p.setDescricao(rs.getString("descrição"));
		
			
			//cria clinte
			Colaborador c = new Colaborador();
			c.setNome(rs.getString("nome"));
			c.setTurno(rs.getString("turno"));
			c.setId(rs.getInt("colaborador_id"));
			//c.setNome(rs.getString("nome"));
			
			//seta o cliente no pedido 
			p.setColaborador(c);
			
					rs.previous();
					//encerraa do while...
					break;
				}
				
		rs.close();
		comando.close();
		return buscas;
	}
	@Override
	public Colaborador selecionar(String usuario, String senha) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
