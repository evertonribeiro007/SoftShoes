package aplicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pacote_banco_dados.ClasseConexao;

public class Deletar {

	public static void main(String[] args) {
		
		Connection conexao = null;
		PreparedStatement comando = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM clientes WHERE cod_cliente=?";
			comando = conexao.prepareStatement(sql);
			comando.setInt(1,3);
			
			
			if(comando.executeUpdate()>0) {
					System.out.println("Dados excluidos");
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
