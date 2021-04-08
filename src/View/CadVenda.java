package View;


import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sistemaVila.dao.DAOCliente;
import sistemaVila.dao.DAOProduto;
import sistemaVila.dao.DAOVendas;
import sistemaVila.modelo.Cliente;
import sistemaVila.modelo.FormaPgto;
import sistemaVila.modelo.ItemVenda;
import sistemaVila.modelo.Produto;
import sistemaVila.modelo.Venda;
import tableModel.ItemVendaTableModel;
import tableModel.ProdutoTableModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Cursor;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CadVenda extends JFrame {

	private JPanel contentPane;
	private JButton btnFinalizar;
	private List<Produto> produtos;
	private List<Cliente> clientes;
	private ItemVendaTableModel modelItem;
	private JTable tbProdutos;
	private List<ItemVenda> itens = new ArrayList<>();
	private JButton btnPassar;
	private JTextField txtCliente;
	private JTextField txtEndereco;
	private JTextField txtTelefone;
	private JTable tbItens;
	private JButton btnBuscar;
	private Venda venda;
	private Cliente cliente;
	private Produto prodSelect;
	private Produto produto;
	private DAOVendas daoV;
	private DAOProduto daoP;
	private DAOCliente daoC;
	private JTextField txtBuscar;
	private JLabel lblTotal;
	private ItemVenda itemSelect;
	private JCheckBox chRetirada;
	private JTextField txtTaxa;
	private JComboBox cbPag;
	private JButton btnNewButton;
	private JButton btnVoltar;
	private JScrollPane scrollPane;
	private JTextArea taObs;
	private JSpinner spinner;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadVenda frame = new CadVenda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadVenda() {
		daoV = new DAOVendas();
		daoP = new DAOProduto();
		daoC = new DAOCliente();
		setResizable(false);
		setTitle("Vendas");
		setBounds(100, 100, 753, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cliente == null) {
					JOptionPane.showMessageDialog(null, "Informe o cliente para realizar um pedido",
							"cliente não informado", JOptionPane.ERROR_MESSAGE);
				} else if (txtEndereco.getText().trim().isEmpty() && !chRetirada.isSelected()) {
					JOptionPane.showMessageDialog(null, "Informe o endereço de entrega",
							"endereço não informado não informado", JOptionPane.ERROR_MESSAGE);
				} else if (itens.size() == 0) {
					JOptionPane.showMessageDialog(null, "A vendado deve ter pelo menos um item", "Venda não informado",
							JOptionPane.ERROR_MESSAGE);
				} else {
					venda = new Venda();
					venda.setCliente(cliente);
					venda.setEndEntrega(txtEndereco.getText());
					venda.setTxentrega(Double.parseDouble(txtTaxa.getText()));
					venda.setFormaPgto((FormaPgto) cbPag.getSelectedItem());
					venda.setObservacao(taObs.getText());
					venda.setRetirada(chRetirada.isSelected());
					venda.setItens(itens);
					venda.setvTotal(atualizaTotal());
					try {
						daoV.inserir(venda);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao inserir pedido", "Erro", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Salvo Com Sucesso");
				}
			}
		});
		btnFinalizar.setBounds(610, 321, 89, 37);
		contentPane.add(btnFinalizar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 138, 270, 159);
		contentPane.add(scrollPane);
		
		tbProdutos = new JTable();
		try {
			produtos = daoP.listar();
			// criando tabela de produtos no cadpedido
			criarTabelaProdutos();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		scrollPane.setViewportView(tbProdutos);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(418, 138, 281, 159);
		contentPane.add(scrollPane_1);
		
		tbItens = new JTable();
		scrollPane_1.setViewportView(tbItens);
		
		btnPassar = new JButton(">>");
		btnPassar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (prodSelect == null) {
					JOptionPane.showMessageDialog(null, "Selecione um produto para adicionar ao pedido", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} else if ((int) spinner.getValue() <= 0) {
					JOptionPane.showMessageDialog(null, "Informe a quantidade a ser adicionada", "Erro",
							JOptionPane.ERROR_MESSAGE);			
				}		
				else {
					ItemVenda item = new ItemVenda();
					item.setProduto(prodSelect);
					item.setQtd((int) spinner.getValue());
					String observacao = JOptionPane.showInputDialog(null, "Observação do item " + prodSelect.getNome(),
							"Observação", JOptionPane.QUESTION_MESSAGE);
					item.setObservacao(observacao);
					itens.add(item);
				//	modelItem.fireTableDataChanged();
					spinner.setValue(0);
					prodSelect = null;
					tbProdutos.clearSelection();
					atualizaTotal();
				}
			}
		});
		btnPassar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPassar.setBounds(324, 187, 84, 23);
		contentPane.add(btnPassar);
		
		btnVoltar = new JButton("<<");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (itemSelect == null) {
					JOptionPane.showMessageDialog(null, "Selecione um item do pedido para remover", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} else {
					itens.remove(itemSelect);
					modelItem.fireTableDataChanged();
					itemSelect = null;
					tbItens.clearSelection();
					atualizaTotal();
				}
			}
		});
		btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVoltar.setBounds(324, 228, 84, 23);
		contentPane.add(btnVoltar);
		
		spinner = new JSpinner();
		spinner.setBounds(335, 136, 62, 29);
		contentPane.add(spinner);
		
		btnNewButton = new JButton("1/2");
		btnNewButton.setBounds(324, 262, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Cliente:");
		lblNewLabel.setBounds(44, 28, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Endere\u00E7o:");
		lblNewLabel_1.setBounds(252, 28, 62, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Telefone:");
		lblNewLabel_2.setBounds(514, 28, 73, 14);
		contentPane.add(lblNewLabel_2);
		
		txtCliente = new JTextField();
		txtCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cliente = daoC.buscaNome(txtCliente.getText());
					if (cliente != null) {
						txtCliente.setText(cliente.getNome());
						txtEndereco.setText(cliente.getEndereco());
						txtTelefone.setText(cliente.getTelefone());
					} else {
						int opcao = JOptionPane.showConfirmDialog(null, "Cliente não encontrado! Deseja cadastrá-lo?",
								"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (opcao == 0) {
							CadCliente frame = new CadCliente();
							frame.setVisible(true);
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		txtCliente.setBounds(86, 25, 128, 20);
		contentPane.add(txtCliente);
		txtCliente.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(311, 25, 122, 20);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);
		
		txtTelefone = new JTextField();
		txtTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
				if (txtTelefone.getText().length() == 11) {
					e.consume();
				}
			}
		});
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(576, 25, 108, 20);
		contentPane.add(txtTelefone);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						produtos = daoP.listar(txtBuscar.getText());
						criarTabelaProdutos();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		});
		btnBuscar.setBounds(44, 112, 89, 23);
		contentPane.add(btnBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(143, 113, 171, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Produtos:");
		lblNewLabel_3.setBounds(44, 94, 78, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Itens da venda:");
		lblNewLabel_4.setBounds(418, 113, 96, 14);
		contentPane.add(lblNewLabel_4);
		
		lblTotal = new JLabel("New label");
		lblTotal.setBounds(500, 332, 78, 14);
		contentPane.add(lblTotal);
		
		JLabel lblNewLabel_5 = new JLabel("Observa\u00E7\u00F5es:");
		lblNewLabel_5.setBounds(44, 308, 112, 14);
		contentPane.add(lblNewLabel_5);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(118, 308, 196, 38);
		contentPane.add(scrollPane_2);
		
		taObs = new JTextArea();
		scrollPane_2.setViewportView(taObs);
		
		JLabel lblNewLabel_6 = new JLabel("Retirada:");
		lblNewLabel_6.setBounds(44, 53, 78, 14);
		contentPane.add(lblNewLabel_6);
		
		chRetirada = new JCheckBox("");
		chRetirada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verifica se está selecionada
				if (chRetirada.isSelected()) {
					txtTaxa.setText("0.00");
					txtTaxa.setEnabled(false);
				} else {
					txtTaxa.setText("" + txtTaxa.getText());
					txtTaxa.setEnabled(true);
				}
				atualizaTotal();
			}
		});
		chRetirada.setBounds(96, 49, 35, 23);
		contentPane.add(chRetirada);
		
		txtTaxa = new JTextField();
		txtTaxa.setText("");
		txtTaxa.setBounds(311, 50, 122, 20);
		contentPane.add(txtTaxa);
		txtTaxa.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Taxa entrega:");
		lblNewLabel_7.setBounds(229, 53, 96, 14);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Forma de pag.");
		lblNewLabel_8.setBounds(476, 53, 84, 14);
		contentPane.add(lblNewLabel_8);
		
		cbPag = new JComboBox();
		cbPag.setBounds(576, 53, 108, 22);
		cbPag.setModel(new DefaultComboBoxModel(FormaPgto.values()));
		contentPane.add(cbPag);
		try {
			produtos = daoP.listar();
			criarTabelaProdutos();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		criarTabelaItens();
	}

	private void criarTabelaProdutos() {
		ProdutoTableModel model = new ProdutoTableModel(produtos);
		tbProdutos.setModel(model);
		tbProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbProdutos.getSelectedRow() >= 0) {
					prodSelect = produtos.get(tbProdutos.getSelectedRow());
				}
			}
		});
	}

	private void criarTabelaItens() {
		modelItem = new ItemVendaTableModel(itens);
		// aplica o model na tabela de itens
		tbItens.setModel(modelItem);
		// ajusta a largura das colunas
		tbItens.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbItens.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbItens.getColumnModel().getColumn(1).setPreferredWidth(200);
		tbItens.getColumnModel().getColumn(2).setPreferredWidth(200);
		tbItens.getColumnModel().getColumn(3).setPreferredWidth(60);
		tbItens.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbItens.getSelectedRow() >= 0) {
					itemSelect = itens.get(tbItens.getSelectedRow());
				}
			}
		});
	}

	private double atualizaTotal() {
				double total = 0;
				for(ItemVenda i : itens) {
					total += i.getTotal(); 
				}
				double txEntrega = Double.parseDouble(txtTaxa.getText()+"");
				total += txEntrega;
				lblTotal.setText(String.format("R$ %7.2f", total));
				return total;
	}
	private void limpar() {
		try {
			cliente = null;
			txtTelefone.setText(null);
			prodSelect = null;
			produtos = daoP.listar();
			criarTabelaProdutos();
			itens = new ArrayList<>();
			criarTabelaItens();
			txtCliente.setText(null);
			txtEndereco.setText(null);
			taObs.setText(null);
			chRetirada.setSelected(false);
			cbPag.setSelectedIndex(1);
			txtTaxa.setText("7.00");
			atualizaTotal();
			txtTelefone.requestFocus();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
