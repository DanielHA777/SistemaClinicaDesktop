package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOResidente;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Especialidade;
import sistemaVila.modelo.Residente;
import tableModel.ColaboradorTableModel;
import tableModel.ResidenteTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadResidente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtIdade;
	private Residente residente;
	private List<Residente> residentes;
	private JTextField txtGrau;
	private JButton btnSalvar;
	private boolean insertUnico = false;
	private JScrollPane scrollPane;
	private JTable tbResidentes;
	private JLabel lblId;
	private DAOResidente dao;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JTextField txtBuscar;
	private JButton btnBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadResidente frame = new CadResidente();
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
	public CadResidente(String... nome) {
		dao = new DAOResidente();
		setResizable(false);
		setTitle("Residentes");
		setBounds(100, 100, 520, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblId = new JLabel("Id:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(30, 47, 55, 14);
		contentPane.add(lblNewLabel);

		JLabel lblIdade = new JLabel("Idade:");
		lblIdade.setBounds(222, 47, 46, 14);
		contentPane.add(lblIdade);

		JLabel lblGrauDeDependncia = new JLabel("Grau de depend\u00EAncia:");
		lblGrauDeDependncia.setBounds(30, 77, 134, 14);
		contentPane.add(lblGrauDeDependncia);

		txtNome = new JTextField();
		txtNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					residente = dao.buscaNome(txtNome.getText().trim());
					//verifica se o cliente é nulo
					if(residente == null) {
						JOptionPane.showMessageDialog(null, "Residente não encontrado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					}else {
						//popula o formulário com o o bj vcliente
						popular();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		txtNome.setBounds(79, 44, 133, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtIdade = new JTextField();
		
		txtIdade.setBounds(260, 44, 86, 20);
		contentPane.add(txtIdade);
		txtIdade.setColumns(10);

		txtGrau = new JTextField();
		txtGrau.setBounds(174, 74, 86, 20);
		contentPane.add(txtGrau);
		txtGrau.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 130, 441, 120);
		contentPane.add(scrollPane);

		tbResidentes = new JTable();
		scrollPane.setViewportView(tbResidentes);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtNome.getText().isEmpty()) { 
					JOptionPane.showMessageDialog(null, "informe o nome", "Aviso", JOptionPane.ERROR_MESSAGE);
			txtNome.requestFocus();
				}else if(txtIdade.getText().trim().isEmpty()) {   
					JOptionPane.showMessageDialog(null, "informe a idade", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtIdade.requestFocus();
				}
				else if(txtGrau.getText().trim().isEmpty()) {   
					JOptionPane.showMessageDialog(null, "informe o Grau de Dependência", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtGrau.requestFocus();
				}else {
					if(residente == null) {
						residente = new Residente();
						residente.setNome(txtNome.getText().trim());
					}
					residente.setNome(txtNome.getText().trim());
					residente.setIdade(txtIdade.getText().trim()); 
					residente.setGrau(txtGrau.getText().trim());				
						try {
							 if(residente.getId() == 0) {
							dao.inserir(residente);
						}else {
							 dao.atualizar(residente); 
						 }				
						limpar();
						residentes = dao.listar();
						criarTabela();
						if(insertUnico == true) {
							dispose();
						} }catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		btnSalvar.setBounds(75, 285, 89, 32);
		contentPane.add(btnSalvar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(residente == null) {
					JOptionPane.showMessageDialog(null, "Selecione um residente para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					java.awt.Toolkit.getDefaultToolkit().beep();   
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir o colaborador "+ residente.getNome()+ " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(botao == 0) {
						try {
							dao.excluir(residente);
							residentes = dao.listar();
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
		btnExcluir.setBounds(205, 285, 89, 32);
		contentPane.add(btnExcluir);

		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(329, 285, 89, 32);
		contentPane.add(btnLimpar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {//carrega lista de clientes com o listar
					residentes = dao.listar(txtBuscar.getText().trim());
					criarTabela();
				} catch (SQLException e1) {				
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(29, 102, 89, 23);
		contentPane.add(btnBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(128, 103, 343, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		try {
			residentes = dao.listar();
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar os residentes", "erro", JOptionPane.ERROR_MESSAGE);
		}
		setLocationRelativeTo(null);
		
		// verifica se foi passado um tel no construtor
		if(nome.length > 0) {
			txtNome.setText(nome[0]);
			txtNome.requestFocus();
			//troca a variavel de controle para verdadeira
			insertUnico = true;
		}
	}

	private void limpar() {
		txtNome.setText(null);
		txtIdade.setText(null);
		txtGrau.setText(null);
		residente = null;
		txtNome.requestFocus();
		tbResidentes.clearSelection();
		lblId.setText("ID:");
	}

	private void criarTabela() {
		ResidenteTableModel model = new ResidenteTableModel(residentes);
    	tbResidentes.setModel(model);
		tbResidentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbResidentes.getColumnModel().getColumn(0).setPreferredWidth(280);
		tbResidentes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbResidentes.getSelectedRow() >= 0) {
					residente = residentes.get(tbResidentes.getSelectedRow());
					popular();
				}
			}
		});
	}

	private void popular() {
		txtNome.setText(residente.getNome());
		txtIdade.setText(residente.getIdade()+"");
		txtGrau.setText(residente.getGrau()+"");
		lblId.setText("ID: " + residente.getId());
	}
}
