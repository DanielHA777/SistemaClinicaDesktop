package sistemaVila.dao;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class ConnectionFactory {
		private static Connection conexao;
	    private static String usuario = "user";
	    private static String senha = "root";
	   private static String strConexao = "jdbc:mysql://10.121.0.55:3307/sistvila?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";  //localhost 3606   ssl estudar  // tutorecconect = true, quando a conex�o cai ela volta automatico
	//   private static String strConexao = "jdbc:mysql://10.121.0.55:3307/sistvila";
	   public static Connection getConnection() {
			//verifica se a conex�o � nula
		   if(conexao == null) {
			   // abre a conex�o
			   try {
				conexao = DriverManager.getConnection(strConexao, usuario, senha);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); // linha do erro, tratado aqui dentro
				System.out.println("ERRO DE CONEX�O");
			}
		   }
		   //retorna o objeto conexao
		   return conexao;
		}
	  public static void closeConnection() {   // fecha conexao
		  if(conexao != null) {
			  try {
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERRO DE CONEX�O");
			}
		  }
	  }}