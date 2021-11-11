package pacote_banco_dados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClasseConexao {
	private static Connection conexao = null;
	//metodo para fazer conexao com o mysql	
	public static Connection Conectar() {
		try {
			if(conexao==null) {
				String url = "jdbc:mysql://localhost/projeto_a3";
				conexao = DriverManager.getConnection(url,"root","vertrigo");
                                
			}
			
		} 	catch (SQLException e) {
                   
		  	}
		return conexao;
		
	}
	public static void FecharConexao(Connection c) {
		try {
			if(c!=null) {
				c.close();
				conexao = null;
			}
		} catch (SQLException e) {
		  }
	}
}
