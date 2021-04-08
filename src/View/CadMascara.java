package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOMascara;
import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Conduta;
import sistemaVila.modelo.Mascara;
import sistemaVila.modelo.Modelo;
import sistemaVila.modelo.Sessao;
import tableModel.CondutaTableModel;
import tableModel.MascaraTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadMascara extends JFrame {

	private JPanel contentPane;
	private JTable tbMasc;
	private JButton btnSalvar;
	private List<Mascara> mascaras;
	private DAOcolaborador daocola;
	private JButton btnExcluir;
	private boolean insertUnico = false;
private Colaborador colaborador;
	private JButton btnLimpar;
	private JComboBox cbModelo;
	private DAOMascara dao;
	private Mascara mascara;
	private JTextField txtData;
	private JTextField txtQtd;
	private JLabel lblEstoq;
	private JLabel lblId;
	private JTextField txtBuscar;
	private JButton btnBuscar;
	private JComboBox cbCola;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadMascara frame = new CadMascara();
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
	public CadMascara(String... nome) {
		dao = new DAOMascara();
		daocola = new DAOcolaborador();
		setResizable(false);
		setTitle("M\u00E1scaras");
		setBounds(100, 100, 659, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 153, 548, 114);
		contentPane.add(scrollPane);
		
		tbMasc = new JTable();
		scrollPane.setViewportView(tbMasc);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(txtData.getText().isEmpty()) {  
						JOptionPane.showMessageDialog(null, "informe a data", "Aviso", JOptionPane.ERROR_MESSAGE);
						txtData.requestFocus();
					}else {
						if(mascara == null) {
							mascara = new Mascara();				 
						}
						mascara.setColaborador(colaborador);
						mascara.setData(txtData.getText());
						mascara.setQtd(Integer.parseInt(txtQtd.getText()));
						mascara.setModelo((Modelo) cbModelo.getSelectedItem());
						mascara.setNomeCola(cbCola.getSelectedItem().toString());
					//	mascara.setEstoque(lblEstoq.getText() - 1);
							try {
								 if(mascara.getId() == 0) {
								dao.inserir(mascara);
									}else {
								 dao.atualizar(mascara); 
							 }				
							limpar();
							mascaras = dao.listar();
							criarTabela();
							if(insertUnico == true) {
								dispose(); 
							}}catch (Exception erro) {	
								erro.printStackTrace();
								JOptionPane.showMessageDialog(null, erro.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
							}
					}
			}
		});
		btnSalvar.setBounds(128, 278, 89, 32);
		contentPane.add(btnSalvar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(265, 278, 89, 32);
		contentPane.add(btnLimpar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mascara == null) {
					JOptionPane.showMessageDialog(null, "Selecione um registro para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					java.awt.Toolkit.getDefaultToolkit().beep();   
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir o registro "+ mascara.getId() + " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(botao == 0) {
						try {
							dao.excluir(mascara);
							mascaras = dao.listar();
							criarTabela();
							limpar();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		btnExcluir.setBounds(418, 278, 89, 32);
		contentPane.add(btnExcluir);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel_1 = new JLabel("Colaborador:");
		lblNewLabel_1.setBounds(39, 36, 69, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Modelo:");
		lblNewLabel_2.setBounds(39, 63, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setBounds(263, 36, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		lblEstoq = new JLabel("Estoque:");
		lblEstoq.setBounds(501, 11, 86, 14);
		contentPane.add(lblEstoq);
		
		JLabel lblNewLabel_5 = new JLabel("Quantidade:");
		lblNewLabel_5.setBounds(263, 63, 91, 14);
		contentPane.add(lblNewLabel_5);
		
		cbModelo = new JComboBox();
		cbModelo.setModel(new DefaultComboBoxModel(Modelo.values()));
		cbModelo.setBounds(112, 59, 116, 22);
		contentPane.add(cbModelo);
		
		txtData = new JTextField();
		txtData.setBounds(335, 33, 86, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		txtQtd =new JTextField();
		txtQtd.setColumns(10);
		txtQtd.setBounds(335, 60, 86, 20);
		contentPane.add(txtQtd);
		
		btnBuscar = new JButton("Buscar:");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mascaras = dao.listar(txtBuscar.getText().trim());
					criarTabela();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(39, 128, 89, 23);
		contentPane.add(btnBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(131, 129, 234, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		cbCola = new JComboBox();
		cbCola.setBounds(112, 32, 116, 22);
		contentPane.add(cbCola);
		try {
			mascaras = dao.listar();
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar as maáscaras", "erro", JOptionPane.ERROR_MESSAGE);
		}
		setLocationRelativeTo(null);
		

		/*if(nome.length > 0) {
			txtCola.setText(nome[0]);
			txtCola.requestFocus();
			//troca a variavel de controle para verdadeira
			insertUnico = true;
		}*/
		
try {
			
			List<Colaborador> lista = daocola.buscaCola();
			for (Colaborador funcionario: lista) {
				cbCola.setSelectedIndex(-1);
				cbCola.addItem(funcionario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void limpar() {
		cbCola.setSelectedIndex(-1);
		txtData.setText(null);
		txtQtd.setText(null);
		mascara = null;
		tbMasc.clearSelection();
		lblId.setText("ID:");
		
	}
	private void criarTabela() {
	MascaraTableModel model = new MascaraTableModel(mascaras);
		tbMasc.setModel(model);
		 tbMasc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 tbMasc.getColumnModel().getColumn(0).setPreferredWidth(280);
		 tbMasc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tbMasc.getSelectedRow() >= 0) {
					mascara = mascaras.get(tbMasc.getSelectedRow());
					popular();
				}
			}
		} );
}
	private void popular() {
		//txtCola.setText(mascara.getColaborador().getNome());
		cbCola.setToolTipText(mascara.getColaborador().getNome());
		cbModelo.setToolTipText(mascara.getModelo()+"");
		txtData.setText(mascara.getData());
		txtQtd.setText(mascara.getData());
		lblId.setText("ID: "+mascara.getId());
	}
}
