package aplicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;



import pacote_banco_dados.ClasseConexao;

public class Alterar {

	public static void main(String[] args)  {
		
		Connection conexao = null;
		PreparedStatement comando = null;
                
                	String sql="update clientes set nome=?,endereco=?,telefone=?,numeracao_cliente=? where cod_cliente=?";
		
		try {
			conexao = ClasseConexao.Conectar();
			comando = conexao.prepareStatement(sql);
                        
                        
                        
			if(comando.executeUpdate()>0) {
			 JOptionPane.showMessageDialog(null,"Dados do usuário alterados com sucesso");
                         
                        
                         
                        }
			
		} catch (SQLException e) {
		  JOptionPane.showMessageDialog(null, e);
                  }

       }
}
