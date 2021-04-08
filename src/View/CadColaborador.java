package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Especialidade;
import tableModel.ColaboradorTableModel;

public class CadColaborador extends JFrame {

	private JPanel contentPane;
	private JTable tbColaboradores;
	private JTextField txtNome;
	private JTextField txtSenha;
	private JTextField txtTurno;
	private JTextField txtBuscar;
	private JComboBox cbEspe;
	private Colaborador colaborador;
	private boolean insertUnico = false;
	private JButton btnSalvar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnBuscar;
	private DAOcolaborador dao;
	private List<Colaborador> colaboradores;
	private JLabel lbId;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadColaborador frame = new CadColaborador();
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
	public CadColaborador(String... nome) {
		setTitle("Cadastro de Colaboradores");
		setResizable(false);
		dao = new DAOcolaborador();
		setBounds(100, 100, 662, 381);
		contentPane = new JPanel();
		contentPane.setForeground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//validação de campos
				if(txtNome.getText().isEmpty()) { // se o texto for vazio
					JOptionPane.showMessageDialog(null, "informe o nome", "Aviso", JOptionPane.ERROR_MESSAGE);//componente pai null, mensagem da caixa de mensagem, titulo, icone de erro
					//retorna o focu para a caixa de texto
			txtNome.requestFocus();
				}else if(txtSenha.getText().trim().isEmpty()) {   // trim tira os espaços em brqncos // isEmpty se retorna vazio
					JOptionPane.showMessageDialog(null, "informe a senha", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtSenha.requestFocus();
				}
				else if(txtTurno.getText().trim().isEmpty()) {   // trim tira os espaços em brqncos // isEmpty se retorna vazio
					JOptionPane.showMessageDialog(null, "informe o turno", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtTurno.requestFocus();
				}else {
					//verifica se o obj e nulo
					if(colaborador == null) {
						colaborador = new Colaborador();
						colaborador.setNome(txtNome.getText().trim());
					}
					// banco de dados // instancia obj cliente
					
					// popula o obj cliente
					colaborador.setNome(txtNome.getText().trim());
					colaborador.setSenha(txtSenha.getText().trim());
            colaborador.setEspe((Especialidade) cbEspe.getSelectedItem()); 
					colaborador.setTurno(txtTurno.getText().trim());
									
				 try {
					 if(colaborador.getId() == 0) {
						dao.inserir(colaborador);// envia  o cliente para a dao inserir no bd
					 }else {
						 dao.atualizar(colaborador); // mesclou 2 dao em um botão só
					 }				
					//limpa o formulario após inserçã
					limpar();
					// carrega a lista de clientes atraves da dao
					colaboradores = dao.listar();
					// monta a tabela
					criarTabela();
					if(insertUnico == true) {
						dispose();
					}
				} catch (SQLException erro) {
					erro.printStackTrace();
					JOptionPane.showMessageDialog(null, erro.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
				}
				}
		
			}
		});
		btnSalvar.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnSalvar.setBounds(114, 277, 89, 36);
		
		contentPane.add(btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//verifica se existe um cliente selecionado
					if(colaborador == null) {
						//exibe mensagem pedindo para selecionar cliente
						JOptionPane.showMessageDialog(null, "Selecione um colaborador para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
					}else {
						//caso hja cliente selecionado 
						//emite um beep
						java.awt.Toolkit.getDefaultToolkit().beep();   
						//retornando showconfirmdialog 0 para sim e 1 para n
						int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir o colaborador "+ colaborador.getNome()+ " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						//verifica se a variavel é igual a 0 o useja botao yes pressionado
						if(botao == 0) {
							try {
								//chama método excluir da dao
								dao.excluir(colaborador);
								//recarrega a lista de clientes
								colaboradores = dao.listar();
								//cria tabela novamente
								criarTabela();
								//limpa formulário
								limpar();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
			}
		});
		btnExcluir.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnExcluir.setBounds(281, 277, 89, 36);
		contentPane.add(btnExcluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnLimpar.setBounds(457, 277, 89, 36);
		contentPane.add(btnLimpar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 120, 594, 146);
		contentPane.add(scrollPane);
		
		tbColaboradores = new JTable();
		scrollPane.setViewportView(tbColaboradores);
		
		txtNome = new JTextField();
		txtNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// guarda na variável cliente o retorno da busca pelo telefone
				try {
					colaborador = dao.buscaNome(txtNome.getText().trim());
					//verifica se o cliente é nulo
					if(colaborador == null) {
						JOptionPane.showMessageDialog(null, "colaborador não encontrado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
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
		txtNome.setBounds(69, 31, 86, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome: ");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblNewLabel.setBounds(24, 33, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblSenha.setBounds(165, 33, 46, 14);
		contentPane.add(lblSenha);
		
		txtSenha = new JTextField();
		txtSenha.setColumns(10);
		txtSenha.setBounds(209, 31, 86, 20);
		contentPane.add(txtSenha);
		
		cbEspe = new JComboBox();
		cbEspe.setBounds(393, 30, 97, 22);
		cbEspe.setModel(new DefaultComboBoxModel(Especialidade.values()));
		contentPane.add(cbEspe);
		
		JLabel lblEspecialidade = new JLabel("Especialidade:");
		lblEspecialidade.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblEspecialidade.setBounds(305, 33, 89, 14);
		contentPane.add(lblEspecialidade);
		
		JLabel Turno = new JLabel("turno:");
		Turno.setFont(new Font("Arial Black", Font.PLAIN, 11));
		Turno.setBounds(500, 33, 46, 14);
		contentPane.add(Turno);
		
		txtTurno = new JTextField();
		txtTurno.setColumns(10);
		txtTurno.setBounds(542, 31, 76, 20);
		contentPane.add(txtTurno);
		
		lbId = new JLabel("ID:");
		lbId.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lbId.setBounds(24, 11, 46, 14);
		contentPane.add(lbId);
		
		txtBuscar = new JTextField();
		txtBuscar.setColumns(10);
		txtBuscar.setBounds(103, 93, 174, 20);
		contentPane.add(txtBuscar);
		
		btnBuscar = new JButton("Buscar");
		//ImageIcon IconeLupa = new ImageIcon(getClass().getResource("/lupa.png"));
		//btnBuscar.setIcon(ImageUtil.redimensiona(IconeLupa, btnBuscar.getWidth(), btnBuscar.getHeight())); // continuação
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//carrega lista de clientes com o listar
					colaboradores = dao.listar(txtBuscar.getText().trim());
					//cria tabela
					criarTabela();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		btnBuscar.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnBuscar.setBounds(24, 93, 76, 20);
		contentPane.add(btnBuscar);
		tbColaboradores = new JTable();
		scrollPane.setViewportView(tbColaboradores);
		
		try {
			colaboradores = dao.listar();
			//chama o criar tabela para para montar a table
			criarTabela();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar os colaboradores", "erro", JOptionPane.ERROR_MESSAGE);
		}
// inicializa janela no centro da tela
		setLocationRelativeTo(null);
		
		if(nome.length > 0) {
			txtNome.setText(nome[0]);
			txtNome.requestFocus();
			//troca a variavel de controle para verdadeira
			insertUnico = true;
		}
		
		
	}
	private void limpar() {
		//limpa campos para inserir novo cliente
		txtNome.setText(null);
		txtSenha.setText(null);
		txtTurno.setText(null);
		cbEspe.setSelectedIndex(0);
		
		colaborador = null;
		txtNome.requestFocus();
		tbColaboradores.clearSelection();
		lbId.setText("ID:");
		
	}
	private void criarTabela() {
		// cria um table model com a lista de clientes 
		ColaboradorTableModel model = new ColaboradorTableModel(colaboradores);
		//aplica o table model a tbclientes
		tbColaboradores.setModel(model);
		//define q apenas uma linha é selecionavel
		 tbColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 //define a largura da coluna
		 tbColaboradores.getColumnModel().getColumn(0).setPreferredWidth(280);
		 //define o comportamento da tabela ao selecionar objeto
		 tbColaboradores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				// verifica se existe uma linha selecionada
				if(tbColaboradores.getSelectedRow() >= 0) {
					//pego o cliente na lista e guardo na variavel cliente
					colaborador = colaboradores.get(tbColaboradores.getSelectedRow());
					//popula o formulario com o obj cliente
					popular();
				}
			}
		} );
}
	private void popular() {
		txtNome.setText(colaborador.getNome());
		txtSenha.setText(colaborador.getSenha());
		cbEspe.setToolTipText(colaborador.getEspe() + "");
		txtTurno.setText(colaborador.getTurno());
		lbId.setText("ID: "+colaborador.getId());
}
	
}
