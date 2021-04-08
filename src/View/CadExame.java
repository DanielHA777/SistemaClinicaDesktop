package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOExame;
import sistemaVila.modelo.Exame;
import sistemaVila.modelo.Mascara;
import sistemaVila.modelo.Modelo;
import tableModel.ExameTableModel;
import tableModel.MascaraTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadExame extends JFrame {

	private JPanel contentPane;
	private JLabel lblId;
	private JTextField txtNome;
	private JTextField txtPreco;
	private JTextField txtDesc;
	private boolean insertUnico = false;
	private JTable table;
	private JButton btnSalvar;
	private List<Exame> exames;
	private Exame exame;
	DAOExame daoExame;
	private JButton Excluir;
	private JButton btnLimpar;
	private JButton btnOrcamento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadExame frame = new CadExame();
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
	public CadExame() {
		daoExame = new DAOExame();
		setTitle("Cadastro de exames");
		setBounds(100, 100, 659, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(52, 75, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Pre\u00E7o:");
		lblNewLabel_1.setBounds(416, 75, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Descri\u00E7\u00E3o:");
		lblNewLabel_2.setBounds(25, 112, 73, 14);
		contentPane.add(lblNewLabel_2);
		
		txtNome = new JTextField();
		txtNome.setBounds(91, 72, 276, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtPreco = new JTextField();
		txtPreco.setBounds(466, 72, 86, 20);
		contentPane.add(txtPreco);
		txtPreco.setColumns(10);
		
		txtDesc = new JTextField();
		txtDesc.setBounds(91, 109, 276, 20);
		contentPane.add(txtDesc);
		txtDesc.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(91, 154, 461, 141);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(txtNome.getText().isEmpty()) {  
						JOptionPane.showMessageDialog(null, "informe a data", "Aviso", JOptionPane.ERROR_MESSAGE);
						txtNome.requestFocus();
					}else {
						if(exame == null) {
							exame = new Exame();				 
						}
						exame.setNome(txtNome.getText());
						exame.setPreco(Double.parseDouble(txtPreco.getText()));
						exame.setObservacao(txtDesc.getText());
							try {
								 if(exame.getId() == 0) {
								daoExame.inserir(exame);
									}else {
								 daoExame.atualizar(exame); 
							 }				
							limpar();
							exames = daoExame.listar();
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
		btnSalvar.setBounds(97, 319, 89, 33);
		contentPane.add(btnSalvar);
		
		Excluir = new JButton("Excluir");
		Excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(exame == null) {
					JOptionPane.showMessageDialog(null, "Selecione um registro para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					java.awt.Toolkit.getDefaultToolkit().beep();   
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir o registro "+ exame.getId() + " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(botao == 0) {
						try {
							daoExame.excluir(exame);
							exames = daoExame.listar();
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
		Excluir.setBounds(212, 319, 89, 33);
		contentPane.add(Excluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(333, 319, 89, 33);
		contentPane.add(btnLimpar);
		
		btnOrcamento = new JButton("Or\u00E7amento");
		btnOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadOrcaExame frame = new CadOrcaExame();
				frame.setVisible(true);
			}
		});
		btnOrcamento.setBounds(447, 319, 105, 33);
		contentPane.add(btnOrcamento);
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
			table.setModel(model);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getColumnModel().getColumn(0).setPreferredWidth(280);
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(table.getSelectedRow() >= 0) {
						exame = exames.get(table.getSelectedRow());
						popular();
					}
				}
			} );
	}
	private void limpar() {
		txtNome.setText(null);
		txtPreco.setText(null);
		txtDesc.setText(null);
		lblId.setText("ID:");
		
	}
	private void popular() {
		//txtCola.setText(mascara.getColaborador().getNome());
		txtNome.setText(exame.getNome());
		txtPreco.setText(exame.getPreco()+"");
		txtDesc.setText(exame.getObservacao());
		lblId.setText("ID: "+exame.getId());
	}
}
