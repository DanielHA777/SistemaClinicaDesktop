package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOCliente;
import sistemaVila.modelo.Cliente;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Especialidade;
import tableModel.ClienteTableModel;
import tableModel.ColaboradorTableModel;

import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadCliente extends JFrame {

	private JPanel contentPane;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JTable tbClientes;
	private JTextField txtBuscar;
	private Boolean insertUnico = false;
	private JLabel lblId;
	private List<Cliente> clientes;
	private Cliente cliente;
	private DAOCliente dao;
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextArea taEndereco;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JButton btnLimpar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadCliente frame = new CadCliente();
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
	public CadCliente(String... nome) {
		dao = new DAOCliente();
		setResizable(false);
		setTitle("Clientes");
		setBounds(100, 100, 684, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtNome.getText().isEmpty()) { 
					JOptionPane.showMessageDialog(null, "informe o nome", "Aviso", JOptionPane.ERROR_MESSAGE);
			txtNome.requestFocus();
				}else if(txtTelefone.getText().trim().isEmpty()) {   
					JOptionPane.showMessageDialog(null, "informe o telefone", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtTelefone.requestFocus();
				}else {
					if(cliente == null) {
						cliente = new Cliente();
						cliente.setNome(txtNome.getText().trim());
					}
					cliente.setNome(txtNome.getText().trim());
					cliente.setTelefone(txtTelefone.getText().trim());
					cliente.setEmail(txtEmail.getText());
					cliente.setEndereco(taEndereco.getText());									
						try {
							if(cliente.getId() == 0) {
							dao.inserir(cliente);
							}else {
							 dao.atualizar(cliente); 
						 }				
						limpar();
						clientes = dao.listar();
						criarTabela();
						if(insertUnico == true) {
							dispose();
						}} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
						}
				}
			}
		});
		btnSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalvar.setBounds(137, 331, 89, 29);
		contentPane.add(btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cliente == null) {
					JOptionPane.showMessageDialog(null, "Selecione um cliente para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					java.awt.Toolkit.getDefaultToolkit().beep();   
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir o colaborador "+ cliente.getNome()+ " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);					
					if(botao == 0) {
						try {
							dao.excluir(cliente);
							clientes = dao.listar();
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
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBounds(318, 331, 89, 29);
		contentPane.add(btnExcluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBounds(500, 334, 89, 29);
		contentPane.add(btnLimpar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 173, 619, 147);
		contentPane.add(scrollPane);
		
		tbClientes = new JTable();
		scrollPane.setViewportView(tbClientes);
		
		btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clientes= dao.listar(txtBuscar.getText());
					criarTabela();
				} catch (SQLException e1) {				
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(29, 145, 89, 23);
		contentPane.add(btnNewButton);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(126, 146, 522, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(29, 34, 63, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Telefone:");
		lblNewLabel_1.setBounds(29, 59, 63, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("E-mail:");
		lblNewLabel_2.setBounds(29, 84, 63, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Endere\u00E7o de entrega: ");
		lblNewLabel_3.setBounds(281, 34, 142, 14);
		contentPane.add(lblNewLabel_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(413, 34, 235, 64);
		contentPane.add(scrollPane_1);
		
		taEndereco = new JTextArea();
		scrollPane_1.setViewportView(taEndereco);
		
		txtNome = new JTextField();
		txtNome.setBounds(102, 31, 86, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(102, 56, 86, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(102, 81, 86, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		

		try {
			clientes = dao.listar();
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar os clientes", "erro", JOptionPane.ERROR_MESSAGE);
		}
		setLocationRelativeTo(null);
		
		if(nome.length > 0) {
			txtNome.setText(nome[0]);
			txtNome.requestFocus();
			insertUnico = true;
		}
	}
	private void limpar() {
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtEmail.setText(null);
		taEndereco.setText(null);
		cliente = null;
		txtNome.requestFocus();
		lblId.setText("ID:");	
	}
	private void criarTabela() {		
		ClienteTableModel model = new ClienteTableModel(clientes);
		tbClientes.setModel(model);
		 tbClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 tbClientes.getColumnModel().getColumn(0).setPreferredWidth(280);
		 tbClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tbClientes.getSelectedRow() >= 0) {
					cliente = clientes.get(tbClientes.getSelectedRow());
					popular();
				}
			}
		} );
}
	private void popular() {
		txtNome.setText(cliente.getNome());
		txtTelefone.setText(cliente.getTelefone());
		txtEmail.setText(cliente.getEmail());
		taEndereco.setText(cliente.getEndereco());
		lblId.setText("ID: "+cliente.getId());
}
}
