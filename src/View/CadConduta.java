package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import sistemaVila.dao.DAOBuscaAtiva;
import sistemaVila.dao.DAOConduta;
import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.BuscaAtiva;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Colaboradores;
import sistemaVila.modelo.Conduta;
import sistemaVila.modelo.Especialidade;
import tableModel.ColaboradorTableModel;
import tableModel.CondutaTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class CadConduta extends JFrame {

	private JPanel contentPane;
	private JTextField txtSintoma;
	private JTable tbConduta;
	private JButton btnSalvar;
	private Colaborador colaborador;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JTable tbColaboradores;
	private JTextArea taConduta;
	private boolean insertUnico = false;
	private DAOConduta dao;
	private List<Conduta> condutas;
	private JTextArea taDesc;
	private Conduta conduta;
	private DAOcolaborador daoo;
	private JScrollPane scrollPane;
	private JTextField txtData;
	private JTextField txtBuscar;
	private JButton btnBuscar;
	private JLabel lblId;
	private JScrollPane scrollPane_2;
	private JTextField txtCola;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadConduta frame = new CadConduta();
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
	public CadConduta(String... nome) {
		setResizable(false);
		dao = new DAOConduta();
		daoo = new DAOcolaborador();
		setTitle("Condutas");
		setBounds(100, 100, 599, 382);
		contentPane = new JPanel();
		contentPane.setForeground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Colaborador: ");
		lblNewLabel.setBounds(10, 40, 82, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblSintomas = new JLabel("Sintoma(s)");
		lblSintomas.setBounds(193, 40, 62, 14);
		contentPane.add(lblSintomas);
		
		txtSintoma = new JTextField();
		txtSintoma.setColumns(10);
		txtSintoma.setBounds(264, 37, 97, 20);
		contentPane.add(txtSintoma);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(4, 82, 88, 14);
		contentPane.add(lblDescrio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 76, 460, 24);
		contentPane.add(scrollPane);
		
		taDesc = new JTextArea();
		scrollPane.setViewportView(taDesc);
		
		JLabel lblConduta = new JLabel("Conduta:");
		lblConduta.setBounds(10, 111, 50, 14);
		contentPane.add(lblConduta);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(64, 111, 460, 24);
		contentPane.add(scrollPane_1);
		
		taConduta = new JTextArea();
		scrollPane_1.setViewportView(taConduta);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(64, 173, 460, 107);
		contentPane.add(scrollPane_2);
		
		tbConduta = new JTable();
		scrollPane_2.setViewportView(tbConduta);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//validação de campos
				 if(txtData.getText().isEmpty()) {   // trim tira os espaços em brqncos // isEmpty se retorna vazio
					JOptionPane.showMessageDialog(null, "informe a data", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtData.requestFocus();
				}
				else if(txtSintoma.getText().trim().isEmpty()) {   // trim tira os espaços em brqncos // isEmpty se retorna vazio
					JOptionPane.showMessageDialog(null, "informe o sintoma", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtSintoma.requestFocus();
				}else if(taConduta.getText().trim().isEmpty()) {   // trim tira os espaços em brqncos // isEmpty se retorna vazio
					JOptionPane.showMessageDialog(null, "informe a conduta", "Aviso", JOptionPane.ERROR_MESSAGE);
					taConduta.requestFocus();
				}else {
					//verifica se o obj e nulo
					if(conduta == null) {
						conduta = new Conduta();
						//conduta.setCola((Colaboradores) cbCola.getSelectedItem()); 
					}
					// banco de dados // instancia obj cliente
					conduta.setColaborador(colaborador);
					// popula o obj cliente
					conduta.setSintomas(txtSintoma.getText());
                     conduta.setData(txtData.getText());;
                     conduta.setDescricaoString(taDesc.getText());
					conduta.setConduta(taConduta.getText());
						try {
							 if(conduta.getId() == 0) {
							dao.inserir(conduta);
								}else {
							 dao.atualizar(conduta); // mesclou 2 dao em um botão só
						 }				
						//limpa o formulario após inserçã
						limpar();
						// carrega a lista de clientes atraves da dao
						condutas = dao.listar();
						// monta a tabela
						criarTabela();
						if(insertUnico == true) {
							dispose(); 
						}}catch (Exception erro) {	
							erro.printStackTrace();
							JOptionPane.showMessageDialog(null, erro.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
						}// envia  o cliente para a dao inserir no bd
				}
			}
		});
		btnSalvar.setBounds(98, 287, 89, 31);
		contentPane.add(btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//verifica se existe um cliente selecionado
				if(conduta == null) {
					//exibe mensagem pedindo para selecionar cliente
					JOptionPane.showMessageDialog(null, "Selecione uma conduta para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					//caso hja cliente selecionado 
					//emite um beep
					java.awt.Toolkit.getDefaultToolkit().beep();   
					//retornando showconfirmdialog 0 para sim e 1 para n
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir a condutar "+ conduta.getColaborador().getNome()+ " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					//verifica se a variavel é igual a 0 o useja botao yes pressionado
					if(botao == 0) {
						try {
							//chama método excluir da dao
							dao.excluir(conduta);
							//recarrega a lista de clientes
							condutas = dao.listar();
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
		btnExcluir.setBounds(250, 287, 89, 31);
		contentPane.add(btnExcluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(401, 287, 89, 31);
		contentPane.add(btnLimpar);
		
		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setBounds(371, 40, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtData = new JTextField();
		txtData.setBounds(401, 37, 82, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//carrega lista de clientes com o listar
					condutas = dao.listar(txtBuscar.getText().trim());
					//cria tabela
					criarTabela();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(64, 142, 71, 20);
		contentPane.add(btnBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(145, 142, 379, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		txtCola = new JTextField();
		txtCola.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					colaborador = daoo.buscaNome(txtCola.getText());
					if (colaborador != null) {
						txtCola.setText(colaborador.getNome());
					//	txtTurno.setText(colaborador.getTurno());
					} else {
						int opcao = JOptionPane.showConfirmDialog(null, "Colaborador não encontrado! Deseja cadastrá-lo?",
								"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (opcao == 0) {
							// abre uma janela de Cadastro de Cliente
							CadColaborador frame = new CadColaborador();
							frame.setVisible(true);
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
			}
		});
		txtCola.setBounds(76, 37, 107, 20);
		contentPane.add(txtCola);
		txtCola.setColumns(10);
		try {
			condutas = dao.listar();
			//chama o criar tabela para para montar a table
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar as condutas", "erro", JOptionPane.ERROR_MESSAGE);
		}
// inicializa janela no centro da tela
		setLocationRelativeTo(null);
		
		if(nome.length > 0) {
			txtCola.setText(nome[0]);
			txtCola.requestFocus();
			//troca a variavel de controle para verdadeira
			insertUnico = true;
		}
	}
	private void limpar() {
		//limpa campos para inserir novo cliente
		txtData.setText(null);
		txtSintoma.setText(null);
		txtCola.setText(null);
		taConduta.setText(null);
		taDesc.setText(null);
		
		conduta = null;
		txtCola.requestFocus();
		tbConduta.clearSelection();
		//lbId.setText("ID:");
		
	}
	private void criarTabela() {
		// cria um table model com a lista de clientes 
		CondutaTableModel model = new CondutaTableModel(condutas);
		//aplica o table model a tbclientes
		tbConduta.setModel(model);
		//define q apenas uma linha é selecionavel
		 tbConduta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 //define a largura da coluna
		 tbConduta.getColumnModel().getColumn(0).setPreferredWidth(280);
		 //define o comportamento da tabela ao selecionar objeto
		 tbConduta.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				// verifica se existe uma linha selecionada
				if(tbConduta.getSelectedRow() >= 0) {
					//pego o cliente na lista e guardo na variavel cliente
					conduta = condutas.get(tbConduta.getSelectedRow());
					//popula o formulario com o obj cliente
					popular();
				}
			}
		} );
}
	private void popular() {
		txtCola.setText(conduta.getColaborador().getNome());
		txtData.setText(conduta.getData());
		txtSintoma.setText(conduta.getSintomas());
		taConduta.setText(conduta.getConduta());
		taDesc.setText(conduta.getDescricaoString());
		lblId.setText("ID: "+conduta.getId());
	}
}
