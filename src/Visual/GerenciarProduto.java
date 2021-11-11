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
public class GerenciarProduto extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement comando = null;
    ResultSet resultado = null;

    /**
     * Creates new form GerenciarProduto
     */
    public GerenciarProduto() {
        initComponents();
        conexao = ClasseConexao.Conectar();
    }

    private void Inserir() {

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "INSERT INTO produtos(descricao_prod,numeracao_prod,QTD,valor) VALUES (?,?,?,?)";
            comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, txtDescProd.getText());
            comando.setString(2, txtNumProd.getText());
            comando.setString(3, txtQtd.getText());
            comando.setString(4, txtValorProd.getText().replace(",", "."));

            if ((txtDescProd.getText().isEmpty()) || (txtQtd.getText().isEmpty()) || (txtValorProd.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, " Preencha todos os campos obrigatórios. ");
            } else if (comando.executeUpdate() > 0) {
                ResultSet resultado = comando.getGeneratedKeys();
                if (resultado.next()) {
                    JOptionPane.showMessageDialog(null, "Produto adicionado ao estoque.");
                    limpar();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "VALOR OU NUMERAÇÃO INVÁLIDA, DIGITE NOVAMENTE.", "ATENÇÃO!", JOptionPane.ERROR_MESSAGE);
          }
          catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2);
          }
    }

    private void pesquisarProduto() {
        String sql = "select cod_prod as código,descricao_prod as descrição,numeracao_prod as numeração, QTD,valor from produtos where descricao_prod like ?";
        try {
            comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, txtcampoProd.getText() + "%");
            resultado = comando.executeQuery();

            tabProd.setModel(DbUtils.resultSetToTableModel(resultado));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setarCamposProd() {

        try {
            int setar = tabProd.getSelectedRow();
        txtCodProd.setText(tabProd.getModel().getValueAt(setar, 0).toString());
        txtDescProd.setText(tabProd.getModel().getValueAt(setar, 1).toString());
        txtNumProd.setText(tabProd.getModel().getValueAt(setar, 2).toString());
        txtQtd.setText(tabProd.getModel().getValueAt(setar, 3).toString());
        txtValorProd.setText(tabProd.getModel().getValueAt(setar, 4).toString());
        //a linha abaixo desabilita o bt add
        btAddProd.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONE UM PRODUTO.","ATENÇÃO!",JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public void Alterar() {

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "UPDATE produtos SET descricao_prod=?,numeracao_prod=?,QTD=?,valor=? WHERE cod_prod=?";
            comando = conexao.prepareStatement(sql);
            comando.setString(1, txtDescProd.getText());
            comando.setString(2, txtNumProd.getText());
            comando.setString(3, txtQtd.getText());
            comando.setString(4, txtValorProd.getText().replace(",", "."));
            comando.setString(5, txtCodProd.getText());

            if ((txtDescProd.getText().isEmpty()) || (txtQtd.getText().isEmpty()) || (txtValorProd.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, " Preencha todos os campos obrigatórios. ");
            } else if (comando.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Dados do produto alterados com sucesso.");
                limpar();
                btAddProd.setEnabled(true);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "VALOR OU NUMERAÇÃO INVÁLIDA, DIGITE NOVAMENTE.", "ATENÇÃO!", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void Excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "TEM CERTEZA QUE DESEJA REMOVER ESTE PRODUTO?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM produtos WHERE cod_prod=?";
            try {
                conexao = ClasseConexao.Conectar();
                comando = conexao.prepareStatement(sql);
                comando.setString(1, txtCodProd.getText());

                if (comando.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Dados excluidos com sucesso.");
                    limpar();
                    btAddProd.setEnabled(true);
                    
                }

            } catch (SQLException e) {
                e.printStackTrace();
             } 

        }
    }

    //metodo para limpar os campos
    private void limpar() {
        txtDescProd.setText(null);
        txtNumProd.setText(null);
        txtQtd.setText(null);
        txtValorProd.setText(null);
        txtCodProd.setText(null);
        txtcampoProd.setText(null);
        ((DefaultTableModel) tabProd.getModel()).setRowCount(0);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNumProd = new javax.swing.JTextField();
        txtDescProd = new javax.swing.JTextField();
        txtQtd = new javax.swing.JTextField();
        txtValorProd = new javax.swing.JTextField();
        btEditProd = new javax.swing.JButton();
        btAddProd = new javax.swing.JButton();
        btDeletProd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtcampoProd = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabProd = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtCodProd = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Produtos");

        txtNumProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumProdActionPerformed(evt);
            }
        });

        txtDescProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescProdActionPerformed(evt);
            }
        });

        txtQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdActionPerformed(evt);
            }
        });

        txtValorProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorProdActionPerformed(evt);
            }
        });

        btEditProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        btEditProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditProdActionPerformed(evt);
            }
        });

        btAddProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        btAddProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddProdActionPerformed(evt);
            }
        });

        btDeletProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        btDeletProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletProdActionPerformed(evt);
            }
        });

        jLabel1.setText("*CAMPOS OBRIGATÓRIOS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("NUMER. PROD");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*DESCRIÇÃO");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("*QTD");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("*VALOR");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisa.png"))); // NOI18N

        txtcampoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcampoProdActionPerformed(evt);
            }
        });
        txtcampoProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcampoProdKeyReleased(evt);
            }
        });

        tabProd = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "codigo", "descrição", "numeração", "QTD", "valor"
            }
        ));
        tabProd.setFocusable(false);
        tabProd.getTableHeader().setResizingAllowed(false);
        tabProd.getTableHeader().setReorderingAllowed(false);
        tabProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabProdMouseClicked(evt);
            }
        });
        tabProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabProdKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabProd);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("COD PROD");

        txtCodProd.setEnabled(false);
        txtCodProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtcampoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtValorProd, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNumProd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescProd, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btAddProd, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btEditProd, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(btDeletProd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(55, 55, 55))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcampoProd)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btAddProd)
                    .addComponent(btEditProd, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btDeletProd, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        setBounds(0, 0, 702, 395);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumProdActionPerformed

    private void txtDescProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescProdActionPerformed

    private void txtQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtdActionPerformed

    private void txtValorProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorProdActionPerformed

    private void btEditProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditProdActionPerformed
        // chamar o metodo alterar clientes
        Alterar();

    }//GEN-LAST:event_btEditProdActionPerformed

    private void btAddProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddProdActionPerformed
        // TODO add your handling code here:
        Inserir();
    }//GEN-LAST:event_btAddProdActionPerformed

    private void btDeletProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletProdActionPerformed
        // TODO add your handling code here:
        Excluir();
    }//GEN-LAST:event_btDeletProdActionPerformed

    private void txtcampoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcampoProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcampoProdActionPerformed

    private void txtcampoProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcampoProdKeyReleased
        // o evento abaixo é do tipo enquanto for digitando...
        pesquisarProduto();

    }//GEN-LAST:event_txtcampoProdKeyReleased

    private void tabProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProdMouseClicked
        // o evento abaixo sera usado para setar os campos da tabela clicando com o mouse
        setarCamposProd();
    }//GEN-LAST:event_tabProdMouseClicked

    private void txtCodProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProdActionPerformed

    private void tabProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabProdKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tabProdKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddProd;
    private javax.swing.JButton btDeletProd;
    private javax.swing.JButton btEditProd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabProd;
    private javax.swing.JTextField txtCodProd;
    private javax.swing.JTextField txtDescProd;
    private javax.swing.JTextField txtNumProd;
    private javax.swing.JTextField txtQtd;
    private javax.swing.JTextField txtValorProd;
    private javax.swing.JTextField txtcampoProd;
    // End of variables declaration//GEN-END:variables
}
