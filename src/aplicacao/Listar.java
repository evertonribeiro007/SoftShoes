package aplicacao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pacote_banco_dados.ClasseConexao;

public class Listar {

	public static void main(String[] args) {
		//Precisamos criar algumas variaveis para conectar, dar comandos e 
		// tambem para armazenar a pesquisa com o SELECT:
		
		Connection conexao = null;
		Statement comando = null;
		ResultSet resultado = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			comando = conexao.createStatement();
			String meu_sql = "SELECT * FROM clientes";
			resultado = comando.executeQuery(meu_sql);
				while(resultado.next()) {
					System.out.println(resultado.getInt("cod_cliente") + "  " + resultado.getString("nome"));
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		  } finally {
			  	ClasseConexao.FecharConexao(conexao);
			  	try {
			  		comando.close();
			  		resultado.close();
			  	} catch (SQLException e) {
			  		e.printStackTrace();
			  	  }
			  
		  	}

	}

}
