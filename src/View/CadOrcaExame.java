package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOCliente;
import sistemaVila.dao.DAOExame;
import sistemaVila.dao.DAOOrcamento;
import sistemaVila.modelo.Cliente;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Exame;
import sistemaVila.modelo.FormaPgto;
import sistemaVila.modelo.ItemExame;
import sistemaVila.modelo.ItemVenda;
import sistemaVila.modelo.Orcamento;
import sistemaVila.modelo.Venda;
import tableModel.ExameTableModel;
import tableModel.ExamesTableModel;
import tableModel.ItemVendaTableModel;
import tableModel.ProdutoTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class CadOrcaExame extends JFrame {

	private JPanel contentPane;
	private JTable tbExame;
	private JButton btnPassar;
	private JButton btnVoltar;
	private List<Exame> exames;
	private JTable table;
	private Exame ExaSelect;
	private List<ItemExame> itens = new ArrayList<>();
	private DAOCliente daoCli;
	private JTable tborca;
	private JButton btnFinalizar;
	private JButton btnEmail;
	private JLabel lblTotal;
	private Orcamento orcamento;
	private ItemExame itemSelect;
	private DAOExame daoExame;
	private ExamesTableModel modelItem;
	private Cliente cliente;
	private JCheckBox chConvenio;
	private JComboBox cbCli;
	private JSpinner spinner;
	private DAOOrcamento daoOrca;
	private JComboBox cbPag;
	private JTextField txtData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadOrcaExame frame = new CadOrcaExame();
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
	public CadOrcaExame() {
		daoExame = new DAOExame();
		daoOrca = new DAOOrcamento();
		daoCli = new DAOCliente();
		setTitle("Or\u00E7amento de exame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Clente:");
		lblNewLabel_1.setBounds(38, 51, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		cbCli = new JComboBox();
		cbCli.setBounds(84, 47, 142, 22);
		contentPane.add(cbCli);
		
		JLabel lblNewLabel_2 = new JLabel("Exame:");
		lblNewLabel_2.setBounds(38, 95, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 120, 200, 194);
		contentPane.add(scrollPane);
		
		tbExame = new JTable();
		scrollPane.setViewportView(tbExame);
		
		btnPassar = new JButton(">>");
		btnPassar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ExaSelect == null) {
					JOptionPane.showMessageDialog(null, "Selecione um Exame para adicionar ao pedido", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} else if ((int) spinner.getValue() <= 0) {
					JOptionPane.showMessageDialog(null, "Informe a quantidade a ser adicionada", "Erro",
							JOptionPane.ERROR_MESSAGE);			
				}		
				else {
					ItemExame item = new ItemExame();
					item.setExame(ExaSelect);
					item.setQtd((int) spinner.getValue());
					String observacao = JOptionPane.showInputDialog(null, "Observação do item " + ExaSelect.getNome(),
							"Observação", JOptionPane.QUESTION_MESSAGE);
					//item.set(observacao);
					itens.add(item);
				//	modelItem.fireTableDataChanged();
					spinner.setValue(0);
					ExaSelect = null;
					tborca.clearSelection();
					atualizaTotal();
				}
			}
		});
		btnPassar.setBounds(248, 162, 89, 23);
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
					tborca.clearSelection();
					atualizaTotal();
				}
			}
		});
		btnVoltar.setBounds(248, 212, 89, 23);
		contentPane.add(btnVoltar);
		
		spinner = new JSpinner();
		spinner.setBounds(248, 118, 89, 20);
		contentPane.add(spinner);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(347, 120, 188, 194);
		contentPane.add(scrollPane_1);
		
		tborca = new JTable();
		scrollPane_1.setViewportView(tborca);
		
		table = new JTable();
		table.setBounds(347, 121, 186, 0);
		contentPane.add(table);
		
		btnFinalizar = new JButton("Salvar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*if (cliente == null) {
					JOptionPane.showMessageDialog(null, "Informe o cliente para realizar um pedido",
							"cliente não informado", JOptionPane.ERROR_MESSAGE);
				}*/  if (itens.size() == 0) {
					JOptionPane.showMessageDialog(null, "O orçamento deve ter pelo menos um item", "Exame não informado",
							JOptionPane.ERROR_MESSAGE);
				} else {
					orcamento = new Orcamento();
					//orcamento.setCliente(cliente);
					orcamento.setNomeCliente(cbCli.getSelectedItem().toString());
					orcamento.setConvenio(chConvenio.isSelected());
					orcamento.setData(txtData.getText());
                    orcamento.setPgto((FormaPgto) cbPag.getSelectedItem());
					orcamento.setItens(itens);
				   orcamento.setvTotal(atualizaTotal());
					try {
						if(orcamento.getId() == 0) {
					daoOrca.inserir(orcamento);
						}else {
							daoOrca.atualizar(orcamento);
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao inserir orçamento", "Erro", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				JOptionPane.showMessageDialog(null, "Salvo Com Sucesso");
				}
			}
		});
		btnFinalizar.setBounds(446, 389, 89, 44);
		contentPane.add(btnFinalizar);
		
		btnEmail = new JButton("Enviar E-mail");
		btnEmail.setBounds(309, 389, 113, 44);
		contentPane.add(btnEmail);
		
		lblTotal = new JLabel("Total");
		lblTotal.setBounds(282, 338, 241, 14);
		contentPane.add(lblTotal);
		
		chConvenio = new JCheckBox("Conv\u00EAnio");
		chConvenio.setBounds(232, 47, 97, 23);
		contentPane.add(chConvenio);
		
		cbPag = new JComboBox();
		cbPag.setModel(new DefaultComboBoxModel(FormaPgto.values()));
		cbPag.setBounds(473, 47, 113, 22);
		contentPane.add(cbPag);
		
		JLabel lblNewLabel_3 = new JLabel("Forma de pagamento:");
		lblNewLabel_3.setBounds(335, 51, 200, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Data:");
		lblNewLabel_4.setBounds(438, 11, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		txtData = new JTextField();
		txtData.setBounds(473, 8, 86, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		criarTabelaItens();
		
try {
			
			List<Cliente> lista = daoCli.buscaCli();
			for (Cliente cli: lista) {
				cbCli.setSelectedIndex(-1);
				cbCli.addItem(cli);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
try {
	exames = daoExame.listar();
	criarTabela();
} catch (SQLException e1) {
	e1.printStackTrace();
	JOptionPane.showMessageDialog(null, "não foi possível carregar os exames", "erro", JOptionPane.ERROR_MESSAGE);
}
setLocationRelativeTo(null);
	}
	private void criarTabela() {
		ExameTableModel model = new ExameTableModel(exames);
		tbExame.setModel(model);
		tbExame.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbExame.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbExame.getSelectedRow() >= 0) {
					ExaSelect = exames.get(tbExame.getSelectedRow());
				}
			}
		});
	}
	private double atualizaTotal() {
		double total = 0;
		for(ItemExame i : itens) {
			total += i.getTotal(); 
		}
		//double txEntrega = Double.parseDouble(txt.getText()+"");
		//total += txEntrega;
		if(chConvenio.isSelected()) {
		lblTotal.setText("taxa de Coleta: " +String.format("R$ %7.2f", total - total + 120));
		}else {
			lblTotal.setText("Total + taxa de Coleta: " +String.format("R$ %7.2f", total + 120));
		
		}
		return total;
}
	private void criarTabelaItens() {
		modelItem = new ExamesTableModel(itens);
		// aplica o model na tabela de itens
		tborca.setModel(modelItem);
		// ajusta a largura das colunas
		tborca.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
		tborca.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tborca.getSelectedRow() >= 0) {
					itemSelect = itens.get(tborca.getSelectedRow());
				}
			}
		});
	}
}
