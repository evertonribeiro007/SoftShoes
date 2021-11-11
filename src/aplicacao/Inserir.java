package aplicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pacote_banco_dados.ClasseConexao;

public class Inserir {

	public static void main(String[] args) {
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "INSERT INTO clientes(nome,numeracao) VALUES (?,?)";
			comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			comando.setString(1,"Joao");
			comando.setInt(2,43);
			
			
			
			if(comando.executeUpdate()>0) {
				ResultSet resultado = comando.getGeneratedKeys();
				if(resultado.next()) {
					///System.out.println("Dados gravados no c�digo: " + resultado.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		  } finally {
			  	ClasseConexao.FecharConexao(conexao);
			  	try {
			  		comando.close();
			  	} catch (SQLException e) {
			  		e.printStackTrace();
			  	  }
			  
		  	}

	}

}
