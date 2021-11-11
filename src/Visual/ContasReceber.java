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
public class ContasReceber extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement comando = null;
    ResultSet resultado = null;
    
    //a linha abaixo cria uma variavel para armazenar um texto de acordo com o radio button selecionado
    private String forma_pagamento;

    /**
     * Creates new form ContasReceber
     */
    public ContasReceber() {
        initComponents();
        conexao = ClasseConexao.Conectar();
    }
    
    private void pesquisarCliente() {
       String sql = "select cod_cliente,nome as Nome,telefone as Telefone from clientes where nome like ?";
        try {
            comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, txtcampoVenda.getText() + "%");
            resultado = comando.executeQuery();

            tabClientes.setModel(DbUtils.resultSetToTableModel(resultado));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    
    public void setarCamposCli() {
       
        try {
            int setar = tabClientes.getSelectedRow();
            txtcodCliente.setText(tabClientes.getModel().getValueAt(setar, 0).toString());
            //a linha abaixo desabilita o bt add
             //btAddVenda.setEnabled(false);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "POR FAVOR, SELECIONE UMA CLIENTE.","ATENÇÃO!",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    //emitir venda
    private void emitir_venda() {
        String sql = "insert into pagamentos(forma_pagamento,valor_pagamento,parcelas,situacao,cod_cliente,cod_prod) values(?,?,?,?,?,?)";
        try {
            comando = conexao.prepareStatement(sql);
            comando.setString(1,forma_pagamento);
            comando.setString(2,txtValorPag.getText().replace(",", "."));
            comando.setString(3,cbxParcelas.getSelectedItem().toString());
            comando.setString(4,cbxSituacao.getSelectedItem().toString());
            comando.setString(5,txtcodCliente.getText());
            comando.setString(6,cbxCodProd.getSelectedItem().toString());
            
            if ((txtValorPag.getText().isEmpty()) || (txtcodCliente.getText().isEmpty()))   {
               JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios.");
            }else {
                int adicionado = comando.executeUpdate();
                if (adicionado > 0) {
                   JOptionPane.showMessageDialog(null,"Compra adicionada com sucesso");
                   limpar();
                   
                }
                    
            }
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null,"VALOR OU CÓDIGO INVÁLIDO, DIGITE NOVAMENTE.","ATENÇÃO!",JOptionPane.ERROR_MESSAGE);
            //System.out.println(e);
          }
          catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2);
          }
    }
    
    private void limpar() {
        txtValorPag.setText(null);
        txtcodCliente.setText(null);
        
        ((DefaultTableModel) tabClientes.getModel()).setRowCount(0);
    }
    
    //METODO PARA PESQUISAR VENDA
    private void pesquisar_compra() {
        //a linha abaixo cria uma caixa de entrada do tipo JOptionPane
        String cod_compra=JOptionPane.showInputDialog("CÓDIGO DA COMPRA:");
        String sql = "select * from pagamentos where cod_compra= " + cod_compra;
        try {
            comando = conexao.prepareStatement(sql);
            resultado = comando.executeQuery();
            if(resultado.next()) {
                txtCodcompra.setText(resultado.getString(1));
                txtData.setText(resultado.getString(2));
                //setando os radio buttons
                String rbformaPagamento=resultado.getString(5);
                if (rbformaPagamento.equals("PARCELADO")) {
                    rbParc.setSelected(true);
                    forma_pagamento="PARCELADO";
                } else {
                    rbVista.setSelected(true);
                    forma_pagamento="A VISTA";
                }
                txtValorPag.setText(resultado.getString(6));
                cbxParcelas.setSelectedItem(resultado.getString(7));
                cbxSituacao.setSelectedItem(resultado.getString(8));
                txtcodCliente.setText(resultado.getString(3));
                cbxCodProd.setSelectedItem(resultado.getString(4));
                btAddVenda.setEnabled(false);
                txtcampoVenda.setEnabled(false);
                tabClientes.setVisible(false);
                btDeletVenda.setEnabled(true);
                
            }else {
                JOptionPane.showMessageDialog(null, "COMPRA NÃO CADASTRADA.");
            }
            
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null,"CÓDIGO INVÁLIDO.");
            //System.out.println(e);
          }
          catch (SQLException e){
           
            JOptionPane.showMessageDialog(null, e);
          }
    }
    
    private void Alterar() {
        String sql = "UPDATE pagamentos SET forma_pagamento=?,valor_pagamento=?,parcelas=?,situacao=?,cod_prod=? WHERE cod_compra=?";
        
        try {
            comando = conexao.prepareStatement(sql);
            comando.setString(1,forma_pagamento);
            comando.setString(2,txtValorPag.getText().replace(",", "."));
            comando.setString(3,cbxParcelas.getSelectedItem().toString());
            comando.setString(4,cbxSituacao.getSelectedItem().toString());
            comando.setString(5,cbxCodProd.getSelectedItem().toString());
            comando.setString(6,txtCodcompra.getText());
            
            if ((txtValorPag.getText().isEmpty())) {
               JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios.");
            }else {
                int adicionado = comando.executeUpdate();
                if (adicionado > 0) {
                   JOptionPane.showMessageDialog(null,"Compra alterada com sucesso");
                   limpar();
                   txtCodcompra.setText(null);
                   txtData.setText(null);
                   btAddVenda.setEnabled(true);
                   txtcampoVenda.setEnabled(true);
                   tabClientes.setVisible(true);
                   
                }
                  
            }
        } catch (java.sql.SQLException e) {
           JOptionPane.showMessageDialog(null,"VALOR OU CÓDIGO INVÁLIDO, DIGITE NOVAMENTE.","ATENÇÃO!",JOptionPane.ERROR_MESSAGE);
            //System.out.println(e);
          }
          catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, e2);
          }
    }
    
    private void Excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "TEM CERTEZA QUE DESEJA REMOVER ESTA COMPRA?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM pagamentos WHERE cod_compra=?";
            try {
                conexao = ClasseConexao.Conectar();
                comando = conexao.prepareStatement(sql);
                comando.setString(1, txtCodcompra.getText());

                if (comando.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Compra excluida com sucesso.");
                    limpar();
                    txtCodcompra.setText(null);
                    txtData.setText(null);
                   btAddVenda.setEnabled(true);
                   txtcampoVenda.setEnabled(true);
                   tabClientes.setVisible(true);
                   btDeletVenda.setEnabled(false);
                    
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
             } 

        }
    }
    
    private void Carregarcbx() {
        
       
		Connection conexao = null;
		Statement  comando = null;
		ResultSet  resultado = null;
		
		try {
			
			conexao = ClasseConexao.Conectar();
			comando = conexao.createStatement();
			String sql = "SELECT cod_prod FROM produtos"; 
			resultado = comando.executeQuery(sql); // Executa o SQL
			
			while(resultado.next()) {
				cbxCodProd.addItem(resultado.getString("cod_prod"));	
                                	
			}   
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
    
                  }             
    }
    
    private void CarregarcbxDesc() {
       
        Connection conexao = null;
		Statement  comando = null;
		ResultSet  resultado = null;
		
		try {
			
			conexao = ClasseConexao.Conectar();
			comando = conexao.createStatement();
			String sql = "SELECT descricao_prod FROM produtos"; 
			resultado = comando.executeQuery(sql); // Executa o SQL
			
			while(resultado.next()) {
				cbxProd.addItem(resultado.getString("descricao_prod"));	
                                	
			}   
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
    
                  }
    }
       
            
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup3 = new javax.swing.ButtonGroup();
        btAddVenda = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btDeletVenda = new javax.swing.JButton();
        txtcodCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtcampoVenda = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabClientes = new javax.swing.JTable();
        btEditVenda = new javax.swing.JButton();
        cbxParcelas = new javax.swing.JComboBox<>();
        txtValorPag = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbxProd = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbxSituacao = new javax.swing.JComboBox<>();
        rbParc = new javax.swing.JRadioButton();
        rbVista = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCodcompra = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cbxCodProd = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Contas a Receber");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        btAddVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/create.png"))); // NOI18N
        btAddVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddVendaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("*COD CLIENTE");

        btDeletVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png"))); // NOI18N
        btDeletVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletVendaActionPerformed(evt);
            }
        });

        txtcodCliente.setEnabled(false);
        txtcodCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodClienteActionPerformed(evt);
            }
        });

        jLabel1.setText("*CAMPOS OBRIGATÓRIOS");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*COD PROD");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("VALOR DA COMPRA");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("*PARCELAS");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisa.png"))); // NOI18N

        txtcampoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcampoVendaActionPerformed(evt);
            }
        });
        txtcampoVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcampoVendaKeyReleased(evt);
            }
        });

        tabClientes = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "cod", "nome", "telefone"
            }
        ));
        tabClientes.setFocusable(false);
        tabClientes.getTableHeader().setResizingAllowed(false);
        tabClientes.getTableHeader().setReorderingAllowed(false);
        tabClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabClientesMouseClicked(evt);
            }
        });
        tabClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabClientesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabClientes);

        btEditVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/update.png"))); // NOI18N
        btEditVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditVendaActionPerformed(evt);
            }
        });

        cbxParcelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1X", "2X", "3X" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("*DESCRIÇÃO PROD");

        CarregarcbxDesc();
        cbxProd.setMaximumRowCount(100);
        cbxProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProdActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("*SITUAÇÃO");

        cbxSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PAGO", "PENDENTE" }));

        buttonGroup3.add(rbParc);
        rbParc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbParc.setText("PARCELADO");
        rbParc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbParcActionPerformed(evt);
            }
        });

        buttonGroup3.add(rbVista);
        rbVista.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbVista.setText("A VISTA");
        rbVista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbVistaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("COD.  COMPRA");

        jLabel10.setText("DATA");

        txtCodcompra.setEditable(false);
        txtCodcompra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCodcompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodcompraActionPerformed(evt);
            }
        });

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtData)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodcompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/read (1).png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Carregarcbx();
        cbxCodProd.setMaximumRowCount(100);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel4))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cbxParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtValorPag, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rbParc)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(rbVista))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbxProd, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbxCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(btAddVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btEditVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btDeletVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtcampoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(40, 40, 40))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcampoVenda)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btAddVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btEditVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btDeletVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbVista)
                            .addComponent(rbParc))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );

        setBounds(0, 0, 825, 427);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddVendaActionPerformed
        // TODO add your handling code here:
       emitir_venda();
    }//GEN-LAST:event_btAddVendaActionPerformed

    private void btDeletVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletVendaActionPerformed
        // TODO add your handling code here:
      Excluir();
    }//GEN-LAST:event_btDeletVendaActionPerformed

    private void txtcodClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodClienteActionPerformed

    private void txtcampoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcampoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcampoVendaActionPerformed

    private void txtcampoVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcampoVendaKeyReleased
        // o evento abaixo é do tipo enquanto for digitando...
        pesquisarCliente();

    }//GEN-LAST:event_txtcampoVendaKeyReleased

    private void tabClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabClientesMouseClicked
        // o evento abaixo sera usado para setar os campos da tabela clicando com o mouse
        setarCamposCli();
    }//GEN-LAST:event_tabClientesMouseClicked

    private void tabClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabClientesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tabClientesKeyReleased

    private void btEditVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditVendaActionPerformed
        // chamar o metodo alterar clientes
        Alterar();
    }//GEN-LAST:event_btEditVendaActionPerformed

    private void rbParcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbParcActionPerformed
        // atribuindo um texto a variavel formapagamento se selecionado
        forma_pagamento = "PARCELADO";
    }//GEN-LAST:event_rbParcActionPerformed

    private void rbVistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVistaActionPerformed
        // A LINHA ABAIXO ATRIBUI UM TEXTO NO RADIO BUTTON
        forma_pagamento = "A VISTA";
    }//GEN-LAST:event_rbVistaActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // AO ABRIR O FORM MARCAR O RADIO BUTTON FORMA DE PAGAMENTO
        rbParc.setSelected(true);
        forma_pagamento = "PARCELADO";
        //DESABILITAR O BOTAO DE EXCLUIR
        btDeletVenda.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtCodcompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodcompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodcompraActionPerformed

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        pesquisar_compra();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbxProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddVenda;
    private javax.swing.JButton btDeletVenda;
    private javax.swing.JButton btEditVenda;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbxCodProd;
    private javax.swing.JComboBox<String> cbxParcelas;
    private javax.swing.JComboBox<Object> cbxProd;
    private javax.swing.JComboBox<String> cbxSituacao;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbParc;
    private javax.swing.JRadioButton rbVista;
    private javax.swing.JTable tabClientes;
    private javax.swing.JTextField txtCodcompra;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtValorPag;
    private javax.swing.JTextField txtcampoVenda;
    private javax.swing.JTextField txtcodCliente;
    // End of variables declaration//GEN-END:variables
}
