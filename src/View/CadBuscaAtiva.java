package View;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import sistemaVila.dao.DAOBuscaAtiva;
import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.BuscaAtiva;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Colaboradores;
import sistemaVila.modelo.Especialidade;
import tableModel.BuscaAtivaTableModel;
import tableModel.ColaboradorTableModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadBuscaAtiva extends JFrame {

	private JPanel contentPane;
	private JTable tbBuscaAtiva;
	private JTextField txtCansaco;
	private JTextField txtTemp;
	private JTextField txtFebre;
	private JTextField txtNausea;
	private JTextField txtDorCorpo;
	private JTextField txtTosse;
	private boolean insertUnico = false;
	private List<BuscaAtiva> buscas;
	private Colaborador colaborador;
	private JTextField txtDiarreia;
	private JTextField txtDorCab;
	private DAOBuscaAtiva dao;
	private DAOcolaborador daoColaborador;
	private JTextField txtPaladar;
	private JTextField txtData;
	private JTextField taDesc;
	private JTextField txtBuscar;
	private BuscaAtiva buscaAtiva;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JLabel lbId;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JTextField txtColaborador;
	private JTextField txtTurno;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadBuscaAtiva frame = new CadBuscaAtiva();
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
	public CadBuscaAtiva() {
		setResizable(false);
		dao = new DAOBuscaAtiva();
		daoColaborador = new DAOcolaborador();
		setTitle("Busca Ativa");
		setBounds(100, 100, 694, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(txtTemp.getText().trim().isEmpty()) {   // trim tira os espaços em brqncos // isEmpty se retorna vazio
					JOptionPane.showMessageDialog(null, "informe a temperatura", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtTemp.requestFocus();
				} else {
						//verifica se o obj e nulo
						if(buscaAtiva == null) {
							buscaAtiva = new BuscaAtiva();
							//buscaAtiva.setCola((Colaboradores) cbColaborador.getSelectedItem()); 
						}
						//buscaAtiva.setCola( (Colaboradores) cbColaborador.getSelectedItem());
						buscaAtiva.setData(txtData.getText());
						buscaAtiva.setTemp(Double.parseDouble(txtTemp.getText()));
						buscaAtiva.setCansaco(txtCansaco.getText().trim());
						buscaAtiva.setDiarreia(txtDiarreia.getText());
						buscaAtiva.setDorCabeca(txtDorCab.getText());
						buscaAtiva.setTosseCoriza(txtTosse.getText());
						//buscaAtiva.setData(txtData.getText());
						buscaAtiva.setDescricao(taDesc.getText());
						buscaAtiva.setDorCorpo(txtDorCorpo.getText());
						buscaAtiva.setFebre(txtFebre.getText());
						buscaAtiva.setNauseaVomito(txtNausea.getText());
						buscaAtiva.setPaladarOlfato(txtPaladar.getText());
						//buscaAtiva.setColaborador(colaborador.getTurno());
					 buscaAtiva.setColaborador(colaborador);
					 try {
						 if(buscaAtiva.getId() == 0) {
							dao.inserir(buscaAtiva);// envia  o cliente para a dao inserir no bd
						 }else {
							// dao.atualizar(buscaAtiva); // mesclou 2 dao em um botão só
						 }				
						//limpa o formulario após inserçã
						limpar();
						// carrega a lista de clientes atraves da dao
						buscas = dao.listar();
						// monta a tabela
						criarTabela();
						if(insertUnico == true) {
							dispose();
						}
						if(txtCansaco.getText() != "não" || txtDiarreia.getText() != "não" || txtDorCab.getText() != "não" || txtFebre.getText() != "não"
								|| txtNausea.getText() != "não" || txtDorCorpo.getText() != "não" || txtPaladar.getText() != "não" || txtTosse.getText() != "não") {
							// abre um confirmDialog e guarda na variável a opção escolhida
							int opcao = JOptionPane.showConfirmDialog(null, "Sintoma registrado, deseja cadastrar uma conduta?",
									"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							// se a opcao for de cadastrar
							if (opcao == 0) {
								// abre uma janela de Cadastro de Cliente
								CadConduta frame = new CadConduta();
								frame.setVisible(true);
							}
						}
						txtTemp.setText("0");
						double valor = Double.parseDouble(txtTemp.getText());
						 if(valor >= 37.3) {
							JOptionPane.showMessageDialog(null, "Temperatura ultrapassou o permitido, entrada proibida!", "Atenção!", JOptionPane.WARNING_MESSAGE);
						}
						if(txtCansaco.getText() == "Sim") {
							JOptionPane.showMessageDialog(null, "Sintoma registrado, deseja cadastrar uma conduta agora?");
							int op = 0;
							switch (op) {
							case 1: {
								//CadColaborador
							}
							default:
								throw new IllegalArgumentException("Unexpected value: " + op);
							}
						}
					} catch (Exception erro) {
						// TODO Auto-generated catch block
						erro.printStackTrace();
						JOptionPane.showMessageDialog(null, erro.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
						erro.printStackTrace();
					}
					}
			}
		});
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalvar.setBounds(103, 325, 89, 39);
		
		contentPane.add(btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//verifica se existe um cliente selecionado
				if(buscaAtiva == null) {
					//exibe mensagem pedindo para selecionar cliente
					JOptionPane.showMessageDialog(null, "Selecione uma busca ativa para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					//caso hja cliente selecionado 
					//emite um beep
					java.awt.Toolkit.getDefaultToolkit().beep();   
					//retornando showconfirmdialog 0 para sim e 1 para n
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir a busca ativa"+ buscaAtiva.getColaboradores()+ " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					//verifica se a variavel é igual a 0 o useja botao yes pressionado
					if(botao == 0) {
						try {
							//chama método excluir da dao
							dao.excluir(buscaAtiva);
							//recarrega a lista de clientes
							buscas = dao.listar();
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
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExcluir.setBounds(249, 325, 89, 39);
		contentPane.add(btnExcluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLimpar.setBounds(394, 325, 89, 39);
		contentPane.add(btnLimpar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 190, 572, 124);
		contentPane.add(scrollPane);
		
		tbBuscaAtiva = new JTable();
		scrollPane.setViewportView(tbBuscaAtiva);
		
		lbId = new JLabel("ID:");
		lbId.setBounds(10, 11, 46, 14);
		contentPane.add(lbId);
		
		JLabel lblNewLabel = new JLabel("Colaborador:");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblNewLabel.setBounds(20, 36, 89, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblCansao = new JLabel("Cansa\u00E7o:");
		lblCansao.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblCansao.setBounds(381, 36, 54, 14);
		contentPane.add(lblCansao);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblData.setBounds(504, 87, 55, 14);
		contentPane.add(lblData);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblDescrio.setBounds(20, 131, 70, 14);
		contentPane.add(lblDescrio);
		
		JLabel lblDiarria = new JLabel("Diarr\u00E9ia:");
		lblDiarria.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblDiarria.setBounds(381, 87, 64, 14);
		contentPane.add(lblDiarria);
		
		JLabel lblDorDeCabea = new JLabel("Dor de Cabe\u00E7a:");
		lblDorDeCabea.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblDorDeCabea.setBounds(22, 86, 97, 14);
		contentPane.add(lblDorDeCabea);
		
		JLabel lblDoresNoCorpo = new JLabel("Dores no corpo:");
		lblDoresNoCorpo.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblDoresNoCorpo.setBounds(277, 61, 94, 14);
		contentPane.add(lblDoresNoCorpo);
		
		JLabel lblNuseaOuVomito = new JLabel("N\u00E1usea ou Vomito:");
		lblNuseaOuVomito.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblNuseaOuVomito.setBounds(94, 61, 109, 14);
		contentPane.add(lblNuseaOuVomito);
		
		JLabel lblFebre = new JLabel("Febre:");
		lblFebre.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblFebre.setBounds(504, 36, 46, 14);
		contentPane.add(lblFebre);
		
		JLabel lblPerdaDePaladarolfato = new JLabel("Perda de paladar/olfato:");
		lblPerdaDePaladarolfato.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblPerdaDePaladarolfato.setBounds(176, 86, 145, 14);
		contentPane.add(lblPerdaDePaladarolfato);
		
		JLabel lblTosseSecaOu = new JLabel("Tosse seca ou Coriza:");
		lblTosseSecaOu.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblTosseSecaOu.setBounds(438, 61, 130, 14);
		contentPane.add(lblTosseSecaOu);
		
		txtCansaco = new JTextField();
		txtCansaco.setBounds(438, 33, 56, 20);
		contentPane.add(txtCansaco);
		txtCansaco.setColumns(10);
		
		txtTemp = new JTextField();
		txtTemp.setBounds(315, 33, 56, 20);
		contentPane.add(txtTemp);
		txtTemp.setColumns(10);
		
		JLabel lblYemperatura = new JLabel("Temperatura:");
		lblYemperatura.setFont(new Font("Arial Black", Font.PLAIN, 10));
		lblYemperatura.setBounds(238, 36, 85, 14);
		contentPane.add(lblYemperatura);
		
		txtFebre = new JTextField();
		txtFebre.setBounds(560, 33, 56, 20);
		contentPane.add(txtFebre);
		txtFebre.setColumns(10);
		
		txtNausea = new JTextField();
		txtNausea.setBounds(203, 58, 46, 20);
		contentPane.add(txtNausea);
		txtNausea.setColumns(10);
		
		txtDorCorpo = new JTextField();
		txtDorCorpo.setBounds(370, 61, 46, 20);
		contentPane.add(txtDorCorpo);
		txtDorCorpo.setColumns(10);
		
		txtTosse = new JTextField();
		txtTosse.setBounds(570, 58, 46, 20);
		contentPane.add(txtTosse);
		txtTosse.setColumns(10);
		
		txtDiarreia = new JTextField();
		txtDiarreia.setBounds(448, 84, 43, 20);
		contentPane.add(txtDiarreia);
		txtDiarreia.setColumns(10);
		
		txtDorCab = new JTextField();
		txtDorCab.setBounds(129, 83, 37, 20);
		contentPane.add(txtDorCab);
		txtDorCab.setColumns(10);
		
		txtPaladar = new JTextField();
		txtPaladar.setBounds(324, 83, 46, 20);
		contentPane.add(txtPaladar);
		txtPaladar.setColumns(10);
		
		txtData = new JTextField();
		txtData.setBounds(545, 84, 71, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(94, 131, 376, 23);
		contentPane.add(scrollPane_1);
		
		taDesc = new JTextField();
		scrollPane_1.setViewportView(taDesc);
		taDesc.setColumns(10);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(116, 161, 222, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//carrega lista de clientes com o listar
					buscas = dao.listar(txtBuscar.getText().trim());
					//cria tabela
					criarTabela();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		btnBuscar.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnBuscar.setBounds(20, 159, 89, 23);
		contentPane.add(btnBuscar);
		
		txtColaborador = new JTextField();
		txtColaborador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					colaborador = daoColaborador.buscaNome(txtColaborador.getText());
					if (colaborador != null) {
						txtColaborador.setText(colaborador.getNome());
						txtTurno.setText(colaborador.getTurno());
					//	txtIdade.setText(residente.getIdade());
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
		txtColaborador.setBounds(103, 33, 100, 20);
		contentPane.add(txtColaborador);
		txtColaborador.setColumns(10);
		
		txtTurno = new JTextField();
		txtTurno.setBounds(560, 109, 56, 20);
		contentPane.add(txtTurno);
		txtTurno.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Turno");
		lblNewLabel_1.setBounds(504, 112, 46, 14);
		contentPane.add(lblNewLabel_1);
		try {
			buscas = dao.listar();
			//chama o criar tabela para para montar a table
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar os colaboradores", "erro", JOptionPane.ERROR_MESSAGE);
		}
// inicializa janela no centro da tela
		setLocationRelativeTo(null);
	
	}
	private void limpar() {
		//limpa campos para inserir novo cliente
		txtColaborador.setText(null);
	    txtData.setText(null);
		txtTemp.setText(null);
		txtCansaco.setText(null);
		txtFebre.setText(null);
		txtNausea.setText(null);
		txtDorCorpo.setText(null);
		txtTosse.setText(null);
		txtDorCab.setText(null);
		txtPaladar.setText(null);
		txtData.setText(null);
		txtDiarreia.setText(null);
		
		buscaAtiva = null;
	
		tbBuscaAtiva.clearSelection();
		lbId.setText("ID:");
		
	}
	private void popular() {
		//cbColaborador.setSelectedItem(buscaAtiva.getCola());
		txtColaborador.setText(buscaAtiva.getColaborador().getNome());
		txtTemp.setText(buscaAtiva.getTemp()+"");
		txtCansaco.setText(buscaAtiva.getCansaco());
		txtFebre.setText(buscaAtiva.getFebre());
		txtNausea.setText(buscaAtiva.getNauseaVomito());
		txtDorCorpo.setText(buscaAtiva.getDorCorpo());
		txtTosse.setText(buscaAtiva.getTosseCoriza());
		txtDorCab.setText(buscaAtiva.getDorCabeca());
		txtPaladar.setText(buscaAtiva.getPaladarOlfato());
		txtData.setText(buscaAtiva.getData()+"");
		txtDiarreia.setText(buscaAtiva.getDiarreia());
		
		
		lbId.setText("ID: "+buscaAtiva.getId());
}
	private void criarTabela() {
		// cria um table model com a lista de clientes 
		BuscaAtivaTableModel model = new BuscaAtivaTableModel(buscas);
		//aplica o table model a tbclientes
		tbBuscaAtiva.setModel(model);
		//define q apenas uma linha é selecionavel
		tbBuscaAtiva.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 //define a largura da coluna
		tbBuscaAtiva.getColumnModel().getColumn(0).setPreferredWidth(280);
		 //define o comportamento da tabela ao selecionar objeto
		tbBuscaAtiva.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				// verifica se existe uma linha selecionada
				if(tbBuscaAtiva.getSelectedRow() >= 0) {
					//pego o cliente na lista e guardo na variavel cliente
					buscaAtiva = buscas.get(tbBuscaAtiva.getSelectedRow());
					//popula o formulario com o obj cliente
					popular();
				}
			}
		} );
}
}
