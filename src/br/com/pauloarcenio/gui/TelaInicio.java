package br.com.pauloarcenio.gui;

import br.com.pauloarcenio.base.Base;
import br.com.pauloarcenio.bd.LittletreeBD;
import br.com.pauloarcenio.dao.ClienteDAO;
import br.com.pauloarcenio.dao.PedidoDAO;
import br.com.pauloarcenio.dao.ProdutoDAO;
import br.com.pauloarcenio.dao.VendasDAO;
import br.com.pauloarcenio.entidades.Cliente;
import br.com.pauloarcenio.entidades.Littletree;
import br.com.pauloarcenio.entidades.Pedido;
import br.com.pauloarcenio.entidades.Usuario;
import br.com.pauloarcenio.entidades.Venda;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class TelaInicio extends javax.swing.JFrame {

    public static final String SENHAMESTRE = "littletreesalagoinhas";

    /**
     * Creates new form TelaInicio
     */
    public TelaInicio() {
        initComponents();
        telaLogin();
        LittletreeBD.limparTabelaPedido();
        atualiza();
        popularComboProdutoPedido();
        jtbClientes1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtbVendasRealizadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static boolean senhaADM() {
        TelaSenhaADM adm = new TelaSenhaADM(new javax.swing.JFrame(), true);
        adm.setVisible(true);
        return adm.confirmaSenha;
    }

    private void telaLogin() {
        TelaLogin login = new TelaLogin(new javax.swing.JFrame(), true);
        login.setVisible(true);
        Usuario.fecharPrograma(login.entrarLogin);
        JOptionPane.showMessageDialog(null, "Bem vindo " + login.nomeUsuario.toUpperCase() + " !","SUCESSO!",JOptionPane.PLAIN_MESSAGE);
    }

    private void telaClientes() {
        TelaClientes clientes = new TelaClientes(new javax.swing.JFrame(), true);
        clientes.setVisible(true);
    }

    private void telaProutos() {
        TelaProduto produtos = new TelaProduto(new javax.swing.JFrame(), true);
        produtos.setVisible(true);
    }

    private void telaUsuarios() {
        TelaUsuarios usuarios = new TelaUsuarios(new javax.swing.JFrame(), true);
        usuarios.setVisible(true);
    }

    private void telaVendas() {
        TelaVendas vendas = new TelaVendas(new javax.swing.JFrame(), true);
        vendas.setVisible(true);
    }

    public static boolean confereSenhaMestre(String senha) {
        boolean retorno = false;
        if (senha.trim().toLowerCase().equals(SENHAMESTRE.trim().toLowerCase())) {
            retorno = true;
        }
        return retorno;
    }

    private void atualiza() {
        // TABELA DE CLIENTES
        String[] cposClientes = {"ID", "Nome", "Vendas"};
        List<Cliente> clientes = ClienteDAO.selecionarTodos();
        String[][] dadosClientes;
        dadosClientes = new String[clientes.size()][3];
        int posicao = 0;
        for (Cliente cli : clientes) {
            String[] umCliente = new String[3];
            umCliente[0] = String.valueOf(cli.getId());
            umCliente[1] = cli.getNome();
            umCliente[2] = String.valueOf(cli.getVendas());
            dadosClientes[posicao++] = umCliente;
        }
        DefaultTableModel modeloCli = new DefaultTableModel(
                dadosClientes, cposClientes);
        jtbClientes1.setModel(modeloCli);

        // TABELA DE PEDIDOS
        String[] cposPedidos = {"ID", "Nome", "Quantidade", "Valor"};
        List<Pedido> pedidos = PedidoDAO.selecionarTodos();
        String[][] dadosPedidos;
        dadosPedidos = new String[pedidos.size()][4];
        int posicao3 = 0;
        for (Pedido ped : pedidos) {
            String[] umPedido = new String[4];
            umPedido[0] = String.valueOf(ped.getId());
            umPedido[1] = ped.getNomeProduto();
            umPedido[2] = String.valueOf(ped.getQuantidade());
            umPedido[3] = String.valueOf(ped.getValor());
            dadosPedidos[posicao3++] = umPedido;
        }
        DefaultTableModel modeloPedidos = new DefaultTableModel(
                dadosPedidos, cposPedidos);
        jtbPedidos.setModel(modeloPedidos);
        retornaValorTotalPedido();

        //TABELA VENDAS
        String[] cposVendas = {"ID", "NomeCliente", "Descricao", "ValorTotal", "Data"};
        List<Venda> vendas = VendasDAO.selecionarTodos();
        String[][] dadosVendas;
        dadosVendas = new String[vendas.size()][5];
        int posicao4 = 0;
        for (Venda vend : vendas) {
            String[] umaVenda = new String[5];
            umaVenda[0] = String.valueOf(vend.getId());
            umaVenda[1] = vend.getCliente().getNome();
            umaVenda[2] = vend.getDescricao();
            umaVenda[3] = String.valueOf(vend.getValorTotal());
            umaVenda[4] = String.valueOf(vend.getData().toString());
            dadosVendas[posicao4++] = umaVenda;
        }
        DefaultTableModel modeloVendas = new DefaultTableModel(
                dadosVendas, cposVendas);
        jtbVendasRealizadas.setModel(modeloVendas);
    }

    private void exibirTabelaFiltrada() {
        String nomeCliente = jtClienteBusca.getText().trim().toLowerCase();
        String[] cposClientes = {"ID", "Nome", "Vendas"};
        List<Cliente> clientes = ClienteDAO.filtrarCliente(nomeCliente);
        String[][] dadosClientes;
        dadosClientes = new String[clientes.size()][3];
        int posicao = 0;
        for (Cliente cli : clientes) {
            String[] umCliente = new String[3];
            umCliente[0] = String.valueOf(cli.getId());
            umCliente[1] = cli.getNome();
            umCliente[2] = String.valueOf(cli.getVendas());
            dadosClientes[posicao++] = umCliente;
        }
        DefaultTableModel modeloCli = new DefaultTableModel(
                dadosClientes, cposClientes);
        jtbClientes1.setModel(modeloCli);
    }

    private void popularComboProdutoPedido() {
        jcProdutosEstoque.removeAllItems();
        List<Littletree> listaDeLittle = ProdutoDAO.selecionarTodos();
        for (Littletree lit : listaDeLittle) {
            jcProdutosEstoque.addItem(lit.getNome());
        }
    }

    private void retornaValorTotalPedido() {
        List<Pedido> listaPedidos = PedidoDAO.selecionarTodos();
        if (!listaPedidos.isEmpty()) {
            double valor = PedidoDAO.valorTotal();
            String valorTotal = String.valueOf(valor).replace(".", ",");
            jtValorTotal.setText(valorTotal);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtbPedidos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbClientes1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbVendasRealizadas = new javax.swing.JTable();
        jtValorTotal = new javax.swing.JTextField();
        jtClienteSelecionado = new javax.swing.JTextField();
        jtClienteBusca = new javax.swing.JTextField();
        jbBuscarCliente = new javax.swing.JButton();
        jcProdutosEstoque = new javax.swing.JComboBox<>();
        jbAdicionarProduto = new javax.swing.JButton();
        jbRemoverProduto = new javax.swing.JButton();
        jbRealizarPedido = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jbLimparFiltroClientes = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Little Trees Alagoinhas");
        setBackground(new java.awt.Color(255, 255, 102));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setForeground(java.awt.Color.yellow);
        setIconImage(new ImageIcon(this.getClass().getResource("/br/com/pauloarcenio/images/padraologo.png")).getImage());
        setLocation(new java.awt.Point(250, 75));
        setMinimumSize(new java.awt.Dimension(903, 650));

        jtbPedidos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtbPedidos);

        jtbClientes1.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbClientes1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtbClientes1.setUpdateSelectionOnSort(false);
        jtbClientes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbClientes1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtbClientes1);
        jtbClientes1.getAccessibleContext().setAccessibleDescription("");
        jtbClientes1.getAccessibleContext().setAccessibleParent(jtbClientes1);

        jtbVendasRealizadas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jtbVendasRealizadas);

        jtValorTotal.setEditable(false);
        jtValorTotal.setToolTipText("");
        jtValorTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Valor Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jtClienteSelecionado.setEditable(false);
        jtClienteSelecionado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        jtClienteSelecionado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtClienteSelecionadoMouseClicked(evt);
            }
        });

        jtClienteBusca.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome do Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jbBuscarCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbBuscarCliente.setText("Buscar");
        jbBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarClienteActionPerformed(evt);
            }
        });

        jcProdutosEstoque.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcProdutosEstoque.setMaximumRowCount(30);
        jcProdutosEstoque.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcProdutosEstoque.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Produtos :", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 2, 12))); // NOI18N
        jcProdutosEstoque.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jbAdicionarProduto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbAdicionarProduto.setText("Adicionar");
        jbAdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarProdutoActionPerformed(evt);
            }
        });

        jbRemoverProduto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbRemoverProduto.setText("Remover");
        jbRemoverProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverProdutoActionPerformed(evt);
            }
        });

        jbRealizarPedido.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jbRealizarPedido.setText("FINALIZAR PEDIDO");
        jbRealizarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRealizarPedidoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel4.setText("ÚLTIMAS VENDAS:");

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel5.setText("REALIZAR PEDIDO");

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel6.setText("CLIENTES");

        jbLimparFiltroClientes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbLimparFiltroClientes.setText("Limpar");
        jbLimparFiltroClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltroClientesActionPerformed(evt);
            }
        });

        jMenu1.setText("Clientes");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pauloarcenio/images/clienteIcon.png"))); // NOI18N
        jMenuItem1.setText("Gerenciar Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Produtos");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pauloarcenio/images/produtoIcon.png"))); // NOI18N
        jMenuItem2.setText("Gerenciar Produtos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Usuários");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pauloarcenio/images/iconUser.png"))); // NOI18N
        jMenuItem3.setText("Gerenciar Usuários");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Vendas");

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pauloarcenio/images/removerIcon.png"))); // NOI18N
        jMenuItem4.setText("Remover");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jcProdutosEstoque, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jbAdicionarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbRemoverProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(302, 302, 302)
                                        .addComponent(jtClienteSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 46, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(153, 153, 153)
                                .addComponent(jbRealizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtClienteBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbBuscarCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbLimparFiltroClientes)))))
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(124, 124, 124))
            .addGroup(layout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(jLabel4)
                .addGap(132, 332, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtClienteSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcProdutosEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbAdicionarProduto)
                            .addComponent(jbRemoverProduto)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtClienteBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbBuscarCliente)
                            .addComponent(jbLimparFiltroClientes)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jbRealizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        telaUsuarios();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jbAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarProdutoActionPerformed
        String nomeProduto = jcProdutosEstoque.getSelectedItem().toString();
        int selecionado = jtbClientes1.getSelectedRow();
        if (jtbClientes1.isRowSelected(selecionado) || !jtClienteSelecionado.getText().isEmpty()) {
            Littletree littlePedido = ProdutoDAO.getLittletreePorNome(nomeProduto);
            Pedido pedido;
            boolean existe = PedidoDAO.existeProdutoPedido(littlePedido.getNome());
            boolean existeEstoque = ProdutoDAO.consultaEstoque(littlePedido);
            if (existe) {
                if (existeEstoque) {
                    pedido = PedidoDAO.getPedidoPorNomeProduto(littlePedido.getNome());
                    pedido.setQuantidade();
                    pedido.setValor(littlePedido.getValor());
                    PedidoDAO.alterar(pedido);
                    atualiza();
                    retornaValorTotalPedido();
                } else {
                    Base.mensagem("Não tem mais em estoque!");
                }

            } else {
                if (existeEstoque) {
                    pedido = new Pedido();
                    pedido.setNomeProduto(littlePedido.getNome());
                    pedido.setQuantidade();
                    pedido.setValor(littlePedido.getValor());
                    PedidoDAO.inserir(pedido);
                    atualiza();
                    retornaValorTotalPedido();
                } else {
                    Base.mensagem("Não tem mais em estoque!");
                }

            }
        } else
            Base.mensagem("Selecione um cliente!");
    }//GEN-LAST:event_jbAdicionarProdutoActionPerformed

    private void jbRemoverProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverProdutoActionPerformed
        int selecionado = jtbPedidos.getSelectedRow();
        if (jtbPedidos.isRowSelected(selecionado)) {
            String idString = (String) jtbPedidos.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            PedidoDAO.apagar(PedidoDAO.getPedidoPorID(id));
            atualiza();
        } else {
            Base.mensagem("Selecione um produto para apagar.");
        }
    }//GEN-LAST:event_jbRemoverProdutoActionPerformed

    private void jtClienteSelecionadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClienteSelecionadoMouseClicked

    }//GEN-LAST:event_jtClienteSelecionadoMouseClicked

    private void jtbClientes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbClientes1MouseClicked
        int selecionado = jtbClientes1.getSelectedRow();
        if (jtbClientes1.isRowSelected(selecionado)) {
            String idString = (String) jtbClientes1.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            Cliente cliente = ClienteDAO.getClientePorID(id);
            jtClienteSelecionado.setText(cliente.getNome());

        }
    }//GEN-LAST:event_jtbClientes1MouseClicked

    private void jbRealizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRealizarPedidoActionPerformed
        List<Pedido> listaPedido = PedidoDAO.selecionarTodos();
        if (!listaPedido.isEmpty()) {
            String nomeCliente = jtClienteSelecionado.getText();
            Cliente cliente = ClienteDAO.getClientePorNome(nomeCliente);
            int vendasClientes = cliente.getVendas() + 1;
            String descricao = listaPedido.toString();
            String valor = jtValorTotal.getText().replace(",", ".");
            double valorTotal = Double.parseDouble(valor);
            LocalDate agora = LocalDate.now();
            agora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Venda venda = new Venda(cliente, descricao, (int) valorTotal, agora);
            cliente.setVendas(vendasClientes);
            ClienteDAO.alterar(cliente);
            VendasDAO.inserir(venda);
            ProdutoDAO.baixaEstoque(listaPedido);
            JOptionPane.showMessageDialog(null, " VENDA REALIZADA COM SUCESSO!");
            LittletreeBD.limparTabelaPedido();
            atualiza();
            jtClienteSelecionado.setText("");
            jtValorTotal.setText("");
        } else
            Base.mensagem("Você não fez o pedido!");
    }//GEN-LAST:event_jbRealizarPedidoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        telaClientes();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        telaProutos();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        telaVendas();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jbBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarClienteActionPerformed
        if (!jtClienteBusca.getText().isEmpty()) {
            exibirTabelaFiltrada();
        } else {
            JOptionPane.showMessageDialog(null, "Informe o nome do cliente que deseja buscar.");
        }

    }//GEN-LAST:event_jbBuscarClienteActionPerformed

    private void jbLimparFiltroClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltroClientesActionPerformed
        atualiza();
        jtClienteBusca.setText("");
        jtClienteSelecionado.setText("");
    }//GEN-LAST:event_jbLimparFiltroClientesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaInicio().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbAdicionarProduto;
    private javax.swing.JButton jbBuscarCliente;
    private javax.swing.JButton jbLimparFiltroClientes;
    private javax.swing.JButton jbRealizarPedido;
    private javax.swing.JButton jbRemoverProduto;
    public static javax.swing.JComboBox<String> jcProdutosEstoque;
    private javax.swing.JTextField jtClienteBusca;
    private javax.swing.JTextField jtClienteSelecionado;
    private javax.swing.JTextField jtValorTotal;
    public static javax.swing.JTable jtbClientes1;
    public static javax.swing.JTable jtbPedidos;
    public static javax.swing.JTable jtbVendasRealizadas;
    // End of variables declaration//GEN-END:variables
}
