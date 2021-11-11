/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pacote_banco_dados.ClasseConexao;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author cliente
 */
public class GerenciarCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement comando = null;
    ResultSet resultado = null;

    /**
     * Creates new form GerenciarCliente
     */
    public GerenciarCliente() {
        initComponents();
        conexao = ClasseConexao.Conectar();
    }

    private void Inserir() {
        
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "INSERT INTO clientes(nome,telefone,numeracao_cliente,endereco) VALUES (?,?,?,?)";
			comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			comando.setString(1,txtNomeCli.getText());
			comando.setString(2,txtTelefone.getText());
                        comando.setString(3,txtNum.getText());
			comando.setString(4,txtEndereco.getText());
                        
                        if ((txtNomeCli. getText () . isEmpty ()) || (txtTelefone . getText () . isEmpty ())) {
                JOptionPane . showMessageDialog ( null , " Preencha todos os campos obrigatórios. " );
                        } else
                        
                        if(comando.executeUpdate()>0) {
				ResultSet resultado = comando.getGeneratedKeys();
				if(resultado.next()) {
					JOptionPane.showMessageDialog(null,"Dados do cliente gravado com sucesso.");
                                        limpar();
				}
			}
			
		} catch (SQLException e) {
                    //JOptionPane.showMessageDialog(null, "TELEFONE OU NUMERAÇÃO INVÁLIDA, DIGITE NOVAMENTE.", "ATENÇÃO!", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e);
		  } 
                catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2);
                }
    }

       

        

    private void pesquisarCliente() {
        String sql = "select cod_cliente as código,nome,telefone,numeracao_cliente as numeração,endereco as endereço from clientes where nome like ?";
        try {
            comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, txtcampoCli.getText() + "%");
            resultado = comando.executeQuery();

            tabCli.setModel(DbUtils.resultSetToTableModel(resultado));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setarCampos() {
       
        try {
        int setar = tabCli.getSelectedRow();
        txtCodCliente.setText(tabCli.getModel().getValueAt(setar, 0).toString());
        txtNomeCli.setText(tabCli.getModel().getValueAt(setar, 1).toString());
        txtTelefone.setText(tabCli.getModel().getValueAt(setar, 2).toString());
        txtNum.setText(tabCli.getModel().getValueAt(setar, 3).toString());
        txtEndereco.setText(tabCli.getModel().getValueAt(setar, 4).toString());
         //a linha abaixo desabilita o bt add
         btAddCli.setEnabled(false);
         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONE UM CLIENTE.","ATENÇÃO!",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void Alterar() {
        
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE clientes SET nome=?,telefone=?,numeracao_cliente=?,endereco=? WHERE cod_cliente=?";
			comando = conexao.prepareStatement(sql);
			comando.setString(1,txtNomeCli.getText());
			comando.setString(2,txtTelefone.getText());
                        comando.setString(3,txtNum.getText());
                        comando.setString(4,txtEndereco.getText());
                        comando.setString(5,txtCodCliente.getText());
                        
                        if ((txtNomeCli. getText () . isEmpty ()) || (txtTelefone . getText () . isEmpty ())) {
                JOptionPane . showMessageDialog ( null , " Preencha todos os campos obrigatórios. " );
                        } else
		
			
			if(comando.executeUpdate()>0) {
					JOptionPane.showMessageDialog(null,"Dados do cliente alterados com sucesso.");
                                        limpar();
                                        btAddCli.setEnabled(true);
				}
			
		} catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "TELEFONE OU NUMERAÇÃO INVÁLIDA, DIGITE NOVAMENTE.", "ATENÇÃO!", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2);
                }
    }
    
    private void Excluir() {
        int confirma = JOptionPane.showConfirmDialog(null,"TEM CERTEZA QUE DESEJA REMOVER ESTE CLIENTE?", "ATENÇÃO",JOptionPane.YES_NO_OPTION);
               if(confirma == JOptionPane.YES_OPTION) {
                   String sql = "DELETE FROM clientes WHERE cod_cliente=?";
		   try {
			conexao = ClasseConexao.Conectar();
			comando = conexao.prepareStatement(sql);
                        comando.setString(1, txtCodCliente.getText());
                        
			
			if(comando.executeUpdate()>0) {
					JOptionPane.showMessageDialog(null,"Dados excluidos com sucesso.");
                                        limpar();
                                        btAddCli.setEnabled(true);
                        }
			
		   } catch (SQLException e) {
                       JOptionPane.showMessageDialog(null, e);
		     } 

               }
    }
    
    private void limpar() {
        txtNomeCli.setText(null);
        txtTelefone.setText(null);
        txtNum.setText(null);
        txtEndereco.setText(null);
        txtCodCliente.setText(null);
        txtcampoCli.setText(null);
        ((DefaultTableModel) tabCli.getModel()).setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtcampoCli = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabCli = new javax.swing.JTable();
        txtTelefone = new javax.swing.JTextField();
        txtNomeCli = new javax.swing.JTextField();
        txtNum = new javax.swing.JTextField();
        txtEndereco = new javax.swing.JTextField();
        btEditCli = new javax.swing.JButton();
        btAddCli = new javax.swing.JButton();
        btDeletCli = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCodCliente = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setVisible(true);

        jLabel1.setText("*CAMPOS OBRIGATÓRIOS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("*TELEFONE");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*NOME");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("NUMERAÇÃO");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("ENDEREÇO");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisa.png"))); // NOI18N

        txtcampoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcampoCliActionPerformed(evt);
            }
        });
        txtcampoCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcampoCliKeyReleased(evt);
            }
        });

        tabCli = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "código", "nome", "telefone", "numeração", "endereço"
            }
        ));
        tabCli.setFocusable(false);
        tabCli.getTableHeader().setResizingAllowed(false);
        tabCli.getTableHeader().setReorderingAllowed(false);
        tabCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabCliMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabCli);

        txtTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneActionPerformed(evt);
            }
        });

        txtNomeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeCliActionPerformed(evt);
            }
        });

        txtNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumActionPerformed(evt);
            }
        });

        txtEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnderecoActionPerformed(evt);
            }
        });

        btEditCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        btEditCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditCliActionPerformed(evt);
            }
        });

        btAddCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        btAddCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddCliActionPerformed(evt);
            }
        });

        btDeletCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        btDeletCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletCliActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("COD CLIENTE");

        txtCodCliente.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(jLabel2))
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEndereco)
                                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(107, 107, 107)
                        .addComponent(btAddCli, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btEditCli, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btDeletCli, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtcampoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(106, 106, 106))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcampoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btEditCli, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btAddCli, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btDeletCli, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        setBounds(0, 0, 757, 389);
    }// </editor-fold>//GEN-END:initComponents

    private void txtcampoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcampoCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcampoCliActionPerformed

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumActionPerformed

    private void txtEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnderecoActionPerformed

    private void txtTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneActionPerformed

    private void btAddCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddCliActionPerformed
        // TODO add your handling code here:
        Inserir();
    }//GEN-LAST:event_btAddCliActionPerformed

    private void btEditCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditCliActionPerformed
        // chamar o metodo alterar clientes
        Alterar();
    }//GEN-LAST:event_btEditCliActionPerformed

    private void btDeletCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletCliActionPerformed
            // TODO add your handling code here:
            Excluir();
    }//GEN-LAST:event_btDeletCliActionPerformed

    private void txtcampoCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcampoCliKeyReleased
        // o evento abaixo é do tipo enquanto for digitando...
        pesquisarCliente();

    }//GEN-LAST:event_txtcampoCliKeyReleased

    private void tabCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabCliMouseClicked
        // o evento abaixo sera usado para setar os campos da tabela clicando com o mouse
       setarCampos();
    }//GEN-LAST:event_tabCliMouseClicked

    private void txtNomeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddCli;
    private javax.swing.JButton btDeletCli;
    private javax.swing.JButton btEditCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tabCli;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNomeCli;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtTelefone;
    private javax.swing.JTextField txtcampoCli;
    // End of variables declaration//GEN-END:variables
}
