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
import br.com.pauloarcenio.entidades.Produto;
import br.com.pauloarcenio.entidades.Venda;
import br.com.pauloarcenio.enums.TipoLittle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LittletreeGUI extends javax.swing.JFrame {

    /**
     * Creates new form LittletreeGUI
     */
    public LittletreeGUI() {
        initComponents();
        jtValorTotal.setEditable(false);
        jtNomeClientePedido.setEditable(false);
        LittletreeBD.limparTabelaPedido();
        popularComboProdutoPedido();
        popularComboProdutos();
        atualiza();

    }

    private void atualiza() {
        // TABELA DE CLIENTES
        String[] cposClientes = {"ID", "Nome", "Contato.", "Vendas"};
        List<Cliente> clientes = ClienteDAO.selecionarTodos();
        String[][] dadosClientes;
        dadosClientes = new String[clientes.size()][4];
        int posicao = 0;
        for (Cliente cli : clientes) {
            String[] umCliente = new String[4];
            umCliente[0] = String.valueOf(cli.getId());
            umCliente[1] = cli.getNome();
            umCliente[2] = cli.getContato();
            umCliente[3] = String.valueOf(cli.getVendas());
            dadosClientes[posicao++] = umCliente;
        }
        DefaultTableModel modeloCli = new DefaultTableModel(
                dadosClientes, cposClientes);
        jtbClientes.setModel(modeloCli);

        // TABELA DE PRODUTOS
        String[] cposProdutos = {"ID", "Nome", "Tipo", "Valor", "Quantidade"};
        List<Littletree> littles = ProdutoDAO.selecionarTodos();
        String[][] dadosLittles;
        dadosLittles = new String[littles.size()][5];
        int posicao2 = 0;
        for (Littletree lit : littles) {
            String[] umLittle = new String[5];
            umLittle[0] = String.valueOf(lit.getId());
            umLittle[1] = lit.getNome();
            umLittle[2] = lit.getTipo().getNomeLittle();
            umLittle[3] = String.valueOf(lit.getValor());
            umLittle[4] = String.valueOf(lit.getQuantidade());
            dadosLittles[posicao2++] = umLittle;
        }
        DefaultTableModel modeloLittle = new DefaultTableModel(
                dadosLittles, cposProdutos);
        jtbProdutos.setModel(modeloLittle);

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
        jtbVendas.setModel(modeloVendas);
    }

    private void popularComboProdutos() {
        jcTipoProduto.removeAllItems();
        for (TipoLittle tipo : TipoLittle.values()) {
            jcTipoProduto.addItem(tipo.getNomeLittle());
        }
    }

    private void popularComboProdutoPedido() {
        jcProdutos.removeAllItems();
        List<Littletree> listaDeLittle = ProdutoDAO.selecionarTodos();
        for (Littletree lit : listaDeLittle) {
            jcProdutos.addItem(lit.getNome());
        }
    }

    private void retornaValorTotalPedido() {
        List<Pedido> listaPedidos = PedidoDAO.selecionarTodos();
        if (!listaPedidos.isEmpty()) {
            int valor = PedidoDAO.valorTotal();
            String valorTotal = String.valueOf(valor);
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

        jpPrincipal = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbPedidos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jlValorTotal = new javax.swing.JLabel();
        jtValorTotal = new javax.swing.JTextField();
        jlCliente = new javax.swing.JLabel();
        jcProdutos = new javax.swing.JComboBox<>();
        jlProduto = new javax.swing.JLabel();
        jbAdicionar = new javax.swing.JButton();
        jbRemover = new javax.swing.JButton();
        jbFinalizarPedido = new javax.swing.JButton();
        jtNomeClientePedido = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbClientes = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jtContatoCliente = new javax.swing.JTextField();
        jlContatoCliente = new javax.swing.JLabel();
        jbAdicionarCliente = new javax.swing.JButton();
        jbAlterarCliente = new javax.swing.JButton();
        jbRemoverCliente = new javax.swing.JButton();
        jtNomeCliente = new javax.swing.JTextField();
        jlNomeCliente = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtbProdutos = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtNomeProduto = new javax.swing.JTextField();
        jcTipoProduto = new javax.swing.JComboBox<>();
        jtValorProduto = new javax.swing.JTextField();
        jtQuantidadeProduto = new javax.swing.JTextField();
        jbAdicionarProduto = new javax.swing.JButton();
        jbAlterarProduto = new javax.swing.JButton();
        jbDeletarProduto = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbVendas = new javax.swing.JTable();
        jbDeletarVendas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jlValorTotal.setText("Valor Total:");

        jtValorTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtValorTotalFocusGained(evt);
            }
        });

        jlCliente.setText("Cliente");

        jcProdutos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jlProduto.setText("Produto");

        jbAdicionar.setText("Adicionar");
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jbRemover.setText("Remover");
        jbRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverActionPerformed(evt);
            }
        });

        jbFinalizarPedido.setText("FINALIZAR PEDIDO");
        jbFinalizarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFinalizarPedidoActionPerformed(evt);
            }
        });

        jtNomeClientePedido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtNomeClientePedidoFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtNomeClientePedido, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jcProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbAdicionar))
                            .addComponent(jbFinalizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemover))
                    .addComponent(jlProduto))
                .addGap(67, 67, 67))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jlValorTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlValorTotal)
                    .addComponent(jtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlCliente)
                            .addComponent(jlProduto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtNomeClientePedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbAdicionar)
                        .addComponent(jbRemover)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbFinalizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Pedido", jPanel1);

        jtbClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtbClientes);

        jtContatoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtContatoClienteFocusGained(evt);
            }
        });

        jlContatoCliente.setText("Contato:");

        jbAdicionarCliente.setText("Adicionar");
        jbAdicionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarClienteActionPerformed(evt);
            }
        });

        jbAlterarCliente.setText("Alterar");
        jbAlterarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarClienteActionPerformed(evt);
            }
        });

        jbRemoverCliente.setText("Remover");
        jbRemoverCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverClienteActionPerformed(evt);
            }
        });

        jtNomeCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtNomeClienteFocusGained(evt);
            }
        });

        jlNomeCliente.setText("Nome:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlNomeCliente))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlContatoCliente)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jtContatoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbAdicionarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAlterarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbRemoverCliente)))
                .addGap(53, 53, 53))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jlNomeCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jlContatoCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtContatoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbAdicionarCliente)
                            .addComponent(jbAlterarCliente)
                            .addComponent(jbRemoverCliente))))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Clientes", jPanel2);

        jtbProdutos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jtbProdutos);

        jLabel1.setText("Nome");

        jLabel2.setText("Tipo");

        jLabel3.setText("Valor");

        jLabel4.setText("Quantidade");

        jtNomeProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtNomeProdutoFocusGained(evt);
            }
        });

        jcTipoProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcTipoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jcTipoProdutoFocusGained(evt);
            }
        });

        jtValorProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtValorProdutoFocusGained(evt);
            }
        });

        jtQuantidadeProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtQuantidadeProdutoFocusGained(evt);
            }
        });

        jbAdicionarProduto.setText("Adicionar");
        jbAdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarProdutoActionPerformed(evt);
            }
        });

        jbAlterarProduto.setText("Alterar");
        jbAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarProdutoActionPerformed(evt);
            }
        });

        jbDeletarProduto.setText("X");
        jbDeletarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeletarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcTipoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jtValorProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtQuantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAdicionarProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAlterarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDeletarProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                        .addGap(5, 5, 5))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtValorProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtQuantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAdicionarProduto)
                    .addComponent(jbAlterarProduto)
                    .addComponent(jbDeletarProduto)
                    .addComponent(jcTipoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Produtos", jPanel6);

        jtbVendas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jtbVendas);

        jbDeletarVendas.setText("DELETAR");
        jbDeletarVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeletarVendasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(jbDeletarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbDeletarVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Vendas", jPanel3);

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbAdicionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarClienteActionPerformed
        String nome = jtNomeCliente.getText();
        String contato = jtContatoCliente.getText();
        boolean existe = ClienteDAO.existeCliente(nome, contato);
        if (existe) {
            JOptionPane.showMessageDialog(null, "Já existe esse cliente!");
        } else {
            Cliente cliente = new Cliente(nome, contato);
            ClienteDAO.inserir(cliente);
            atualiza();
        }

    }//GEN-LAST:event_jbAdicionarClienteActionPerformed

    private void jbRemoverClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverClienteActionPerformed
        int selecionado = jtbClientes.getSelectedRow();
        if (jtbClientes.isRowSelected(selecionado)) {
            String idString = (String) jtbClientes.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            ClienteDAO.apagar(ClienteDAO.getClientePorID(id));
            atualiza();
        } else {
            Base.mensagem("Selecione um cliente para apagar.");
        }

    }//GEN-LAST:event_jbRemoverClienteActionPerformed

    private void jbAlterarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarClienteActionPerformed
        int selecionado = jtbClientes.getSelectedRow();
        if (jtbClientes.isRowSelected(selecionado)) {
            String idString = (String) jtbClientes.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            Cliente cliente = (ClienteDAO.getClientePorID(id));

            String nome = jtNomeCliente.getText();
            String contato = jtContatoCliente.getText();
            cliente.setNome(nome);
            cliente.setContato(contato);
            ClienteDAO.alterar(cliente);
            atualiza();
        } else {
            Base.mensagem("Selecione um cliente para alterar.");
        }
    }//GEN-LAST:event_jbAlterarClienteActionPerformed

    private void jtNomeClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNomeClienteFocusGained
        int selecionado = jtbClientes.getSelectedRow();
        if (jtbClientes.isRowSelected(selecionado)) {
            String idString = (String) jtbClientes.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);

            Cliente cliente = ClienteDAO.getClientePorID(id);
            jtNomeCliente.setText(cliente.getNome());
            jtContatoCliente.setText(cliente.getContato());
        }


    }//GEN-LAST:event_jtNomeClienteFocusGained

    private void jtNomeClientePedidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNomeClientePedidoFocusGained
        int selecionado = jtbClientes.getSelectedRow();
        if (jtbClientes.isRowSelected(selecionado)) {
            String idString = (String) jtbClientes.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            Cliente cliente = ClienteDAO.getClientePorID(id);
            jtNomeClientePedido.setText(cliente.getNome());
        }

    }//GEN-LAST:event_jtNomeClientePedidoFocusGained

    private void jbAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarProdutoActionPerformed
        String nome = jtNomeProduto.getText();
        TipoLittle tipo = TipoLittle.getTipoPorNome(jcTipoProduto.getSelectedItem().toString());
        int valor = Integer.parseInt(jtValorProduto.getText());
        int quantidade = Integer.parseInt(jtQuantidadeProduto.getText());
        Littletree littleNew = new Littletree(nome, tipo, valor, quantidade);
        ProdutoDAO.inserir(littleNew);
        atualiza();
        popularComboProdutoPedido();

//        String nome = jtNomeCliente.getText();
//        String contato = jtContatoCliente.getText();
//        boolean existe = ClienteDAO.existeCliente(nome, contato);
//        if (existe) {
//            JOptionPane.showMessageDialog(null, "Já existe esse cliente!");
//        } else {
//            Cliente cliente = new Cliente(nome, contato);
//            ClienteDAO.inserir(cliente);
//            atualiza();
//        }
    }//GEN-LAST:event_jbAdicionarProdutoActionPerformed

    private void jbDeletarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeletarProdutoActionPerformed
        int selecionado = jtbProdutos.getSelectedRow();
        if (jtbProdutos.isRowSelected(selecionado)) {
            String idString = (String) jtbProdutos.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            ProdutoDAO.apagar(ProdutoDAO.getLittletreePorID(id));
            atualiza();
            popularComboProdutoPedido();
        } else {
            Base.mensagem("Selecione um produto para apagar.");
        }
    }//GEN-LAST:event_jbDeletarProdutoActionPerformed

    private void jbAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarProdutoActionPerformed
        int selecionado = jtbProdutos.getSelectedRow();
        if (jtbProdutos.isRowSelected(selecionado)) {
            String idString = (String) jtbProdutos.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            Littletree little = (ProdutoDAO.getLittletreePorID(id));

            String nome = jtNomeProduto.getText();
            String tipo = jcTipoProduto.getSelectedItem().toString();
            int valor = Integer.parseInt(jtValorProduto.getText());
            int quantidade = Integer.parseInt(jtQuantidadeProduto.getText());
            little.setNome(nome);
            little.setTipo(TipoLittle.getTipoPorNome(tipo));
            little.setValor(valor);
            little.setQuantidade(quantidade);
            ProdutoDAO.alterar(little);
            atualiza();
            popularComboProdutoPedido();
        } else {
            Base.mensagem("Selecione um produto para alterar.");
        }
    }//GEN-LAST:event_jbAlterarProdutoActionPerformed

    private void jtNomeProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNomeProdutoFocusGained
        int selecionado = jtbProdutos.getSelectedRow();
        if (jtbProdutos.isRowSelected(selecionado)) {
            String idString = (String) jtbProdutos.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);

            Littletree little = ProdutoDAO.getLittletreePorID(id);
            jtNomeProduto.setText(little.getNome());
        }


    }//GEN-LAST:event_jtNomeProdutoFocusGained

    private void jtValorProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtValorProdutoFocusGained
        int selecionado = jtbProdutos.getSelectedRow();
        if (jtbProdutos.isRowSelected(selecionado)) {
            String idString = (String) jtbProdutos.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);

            Littletree little = ProdutoDAO.getLittletreePorID(id);
            jtValorProduto.setText(String.valueOf(little.getValor()));
        }

    }//GEN-LAST:event_jtValorProdutoFocusGained

    private void jtQuantidadeProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtQuantidadeProdutoFocusGained
        int selecionado = jtbProdutos.getSelectedRow();
        if (jtbProdutos.isRowSelected(selecionado)) {
            String idString = (String) jtbProdutos.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);

            Littletree little = ProdutoDAO.getLittletreePorID(id);
            jtQuantidadeProduto.setText(String.valueOf(little.getQuantidade()));
        }

    }//GEN-LAST:event_jtQuantidadeProdutoFocusGained

    private void jcTipoProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcTipoProdutoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jcTipoProdutoFocusGained

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        String nomeProduto = jcProdutos.getSelectedItem().toString();
        int selecionado = jtbClientes.getSelectedRow();
        if (jtbClientes.isRowSelected(selecionado) || !jtNomeClientePedido.getText().isEmpty()) {

            Littletree littlePedido = ProdutoDAO.getLittletreePorNome(nomeProduto);
            Pedido pedido;

            boolean existe = PedidoDAO.existeProdutoPedido(littlePedido.getNome());
            if (existe) {
                pedido = PedidoDAO.getPedidoPorNomeProduto(littlePedido.getNome());
                pedido.setQuantidade();
                pedido.setValor(littlePedido.getValor());
                PedidoDAO.alterar(pedido);
                atualiza();
                retornaValorTotalPedido();
            } else {
                pedido = new Pedido();
                pedido.setNomeProduto(littlePedido.getNome());
                pedido.setQuantidade();
                pedido.setValor(littlePedido.getValor());
                PedidoDAO.inserir(pedido);
                atualiza();
                retornaValorTotalPedido();
            }
        } else
            Base.mensagem("Selecione um cliente!");
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jbRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverActionPerformed
        int selecionado = jtbPedidos.getSelectedRow();
        if (jtbPedidos.isRowSelected(selecionado)) {
            String idString = (String) jtbPedidos.getValueAt(selecionado, 0);
            byte id = Byte.parseByte(idString);
            PedidoDAO.apagar(PedidoDAO.getPedidoPorID(id));
            atualiza();
//            retornaValorTotalPedido();
        } else {
            Base.mensagem("Selecione um produto para apagar.");
        }
    }//GEN-LAST:event_jbRemoverActionPerformed

    private void jtValorTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtValorTotalFocusGained

    }//GEN-LAST:event_jtValorTotalFocusGained

    private void jbFinalizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFinalizarPedidoActionPerformed
        String nomeCliente = jtNomeClientePedido.getText();
        Cliente cliente = ClienteDAO.getClientePorNome(nomeCliente);
        int vendasClientes = cliente.getVendas() + 1;
        List<Pedido> listaPedido = PedidoDAO.selecionarTodos();
        String descricao = listaPedido.toString();
        String valor = String.valueOf(jtValorTotal.getText());
        int valorTotal = Integer.parseInt(valor);
        LocalDate agora = LocalDate.now();
        agora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Venda venda = new Venda(cliente, descricao, valorTotal, agora);
        cliente.setVendas(vendasClientes);
        ClienteDAO.alterar(cliente);
        VendasDAO.inserir(venda);
        //DAR BAIXA NO ESTOQUE
        ProdutoDAO.baixaEstoque(listaPedido);
        JOptionPane.showMessageDialog(null, " VENDA REALIZADA COM SUCESSO!");
        LittletreeBD.limparTabelaPedido();
        atualiza();

    }//GEN-LAST:event_jbFinalizarPedidoActionPerformed

    private void jtContatoClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtContatoClienteFocusGained

    }//GEN-LAST:event_jtContatoClienteFocusGained

    private void jbDeletarVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeletarVendasActionPerformed
        int selecionado = jtbVendas.getSelectedRow();
        String idString = (String) jtbVendas.getValueAt(selecionado, 0);
        byte id = Byte.parseByte(idString);
        Venda venda = VendasDAO.getVendaPorID(id);
        VendasDAO.apagar(venda);
        atualiza();
    }//GEN-LAST:event_jbDeletarVendasActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LittletreeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LittletreeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LittletreeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LittletreeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LittletreeGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbAdicionarCliente;
    private javax.swing.JButton jbAdicionarProduto;
    private javax.swing.JButton jbAlterarCliente;
    private javax.swing.JButton jbAlterarProduto;
    private javax.swing.JButton jbDeletarProduto;
    private javax.swing.JButton jbDeletarVendas;
    private javax.swing.JButton jbFinalizarPedido;
    private javax.swing.JButton jbRemover;
    private javax.swing.JButton jbRemoverCliente;
    private javax.swing.JComboBox<String> jcProdutos;
    private javax.swing.JComboBox<String> jcTipoProduto;
    private javax.swing.JLabel jlCliente;
    private javax.swing.JLabel jlContatoCliente;
    private javax.swing.JLabel jlNomeCliente;
    private javax.swing.JLabel jlProduto;
    private javax.swing.JLabel jlValorTotal;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JTextField jtContatoCliente;
    private javax.swing.JTextField jtNomeCliente;
    private javax.swing.JTextField jtNomeClientePedido;
    private javax.swing.JTextField jtNomeProduto;
    private javax.swing.JTextField jtQuantidadeProduto;
    private javax.swing.JTextField jtValorProduto;
    private javax.swing.JTextField jtValorTotal;
    private javax.swing.JTable jtbClientes;
    private javax.swing.JTable jtbPedidos;
    private javax.swing.JTable jtbProdutos;
    private javax.swing.JTable jtbVendas;
    // End of variables declaration//GEN-END:variables
}
