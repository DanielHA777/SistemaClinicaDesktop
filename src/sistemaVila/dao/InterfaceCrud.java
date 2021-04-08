package sistemaVila.dao;

import java.sql.SQLException;
import java.util.List;
import sistemaVila.modelo.Colaborador;

public interface InterfaceCrud<T> {
	// metodos para inserir, atualizar, excluir e listar obj no BD
public void inserir(T objeto) throws SQLException, Exception;
public void atualizar(T objeto)throws SQLException;
public void excluir(T objeto)throws SQLException;
public List<T> listar() throws SQLException;
Colaborador selecionar(String usuario, String senha) throws SQLException;
}
