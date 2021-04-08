package View;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;

import sistemaVila.dao.DAOFisio;
import sistemaVila.dao.DAOResidente;
import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Colaboradores;
import sistemaVila.modelo.Especialidade;
import sistemaVila.modelo.Fisio;
import sistemaVila.modelo.Residente;
import sistemaVila.modelo.Sessao;
import tableModel.FisioTableModel;
import tableModel.ResidenteTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class CadFisio extends JFrame {

	private JPanel contentPane;
	private JTextField txtData;
	private Fisio fisio;
	private boolean insertUnico = false;
	private Residente residente;
	private JButton btnBuscar;
	private Colaborador colaborador;
	private JTextField txtBuscar;
	private DAOResidente daoResidente;
	private JTable tbFisio;
	private JScrollPane scrollPane;
	private Connection conexao;
	private List<Fisio> fisios;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JComboBox cbSessao;
	private DAOFisio  dao;
	private JLabel lblId;
	private DAOcolaborador daoCola;
	private JComboBox cbFisio;
	private JComboBox cbResi;
	private JButton btnRela;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadFisio frame = new CadFisio();
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
	public CadFisio() {
		dao = new DAOFisio();
		daoResidente = new DAOResidente();
		daoCola = new DAOcolaborador();
		setResizable(false);
		setTitle("Fisioterapia");
		setBounds(100, 100, 649, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblId = new JLabel("Id:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("Colaborador:");
		lblNewLabel.setBounds(32, 56, 86, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblSesso = new JLabel("Sess\u00E3o:");
		lblSesso.setBounds(231, 56, 65, 14);
		contentPane.add(lblSesso);
		
		JLabel lblData = new JLabel("Data: ");
		lblData.setBounds(437, 56, 46, 14);
		contentPane.add(lblData);
		
		JLabel lblResidente = new JLabel("Residente:");
		lblResidente.setBounds(32, 81, 65, 14);
		contentPane.add(lblResidente);
		
		txtData = new JTextField();
		txtData.setBounds(470, 53, 86, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 139, 524, 130);
		contentPane.add(scrollPane);
		
		tbFisio = new JTable();
		scrollPane.setViewportView(tbFisio);
		
		btnBuscar = new JButton("Buscar: ");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fisios = dao.listar(txtBuscar.getText().trim());
					criarTabela();
				} catch (SQLException e1) {				
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(32, 116, 86, 23);
		contentPane.add(btnBuscar);
		
		List<String> sessoes = new ArrayList<>();
		sessoes.add("Motora");
		sessoes.add("Massagem");
		
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(132, 117, 424, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbFisio.getSelectedIndex() == -1) { 
					JOptionPane.showMessageDialog(null, "informe o profissional", "Aviso", JOptionPane.ERROR_MESSAGE);
			cbFisio.requestFocus();
				}else if(cbSessao.getSelectedIndex() == -1) {   
					JOptionPane.showMessageDialog(null, "informe a Sessão", "Aviso", JOptionPane.ERROR_MESSAGE);
					cbSessao.requestFocus();
				}
				else if(txtData.getText().trim().isEmpty()) {   
					JOptionPane.showMessageDialog(null, "informe a data", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtData.requestFocus();
				}else {
					if(fisio == null) {
						fisio = new Fisio();
						//fisio.setResidente(residente);
						//fisio.setColaborador(colaborador);
					}
					fisio.setColaborador(colaborador);
					fisio.setResidente(residente);
					fisio.setSessao((Sessao) cbSessao.getSelectedItem());
					fisio.setData(txtData.getText().trim());		
					fisio.setNomeCola(cbFisio.getSelectedItem().toString());
				//	fisio.setColaborador(colaborador.getNome());
					fisio.setNomeResi(cbResi.getSelectedItem().toString());
						try {
							 if(fisio.getId() == 0) {
							dao.inserir(fisio);
						}else {
							 dao.atualizar(fisio); 
						 }				
						limpar();
						fisios = dao.listar();
						criarTabela();
						if(insertUnico == true) {
							dispose();
						} }catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		btnSalvar.setBounds(32, 295, 89, 39);
		contentPane.add(btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fisio == null) {
					JOptionPane.showMessageDialog(null, "Selecione uma sessão para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					java.awt.Toolkit.getDefaultToolkit().beep();   
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir a sessão "+ fisio.getResidente().getNome()+ " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(botao == 0) {
						try {
							dao.excluir(fisio);
							fisios = dao.listar();
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
		btnExcluir.setBounds(189, 295, 89, 39);
		contentPane.add(btnExcluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(326, 295, 89, 39);
		contentPane.add(btnLimpar);
		
		cbSessao = new JComboBox();
		cbSessao.setModel(new DefaultComboBoxModel(Sessao.values()));
		//cbSessao.setSelectedItem(sessoes.get(1));;
		cbSessao.setBounds(295, 52, 132, 22);
		contentPane.add(cbSessao);
		
		cbFisio = new JComboBox();
		cbFisio.setBounds(114, 52, 107, 22);
		contentPane.add(cbFisio);
		
		cbResi = new JComboBox();
		cbResi.setBounds(107, 77, 114, 22);
		contentPane.add(cbResi);
		
		btnRela = new JButton("Relat\u00F3rio");
		btnRela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewFisio frame = new ViewFisio();
				frame.setVisible(true);
			}
		});
		btnRela.setBounds(467, 295, 89, 39);
		contentPane.add(btnRela);
		try {
			fisios = dao.listar();
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar as fisioterapias", "erro", JOptionPane.ERROR_MESSAGE);
		}
		setLocationRelativeTo(null);
		
	try {
			
			List<Colaborador> lista = daoCola.buscaFisio();
			for (Colaborador funcionario: lista) {
				cbFisio.setSelectedIndex(-1);
				cbFisio.addItem(funcionario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	try {
		
		List<Residente> lista = daoResidente.buscaResi();
		for (Residente func: lista) {
			cbResi.setSelectedIndex(-1);
			cbResi.addItem(func);
			//cbResi.setSelectedItem(func.getNome());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	}
	private void limpar() {
		cbFisio.setSelectedIndex(-1);
		cbSessao.setSelectedIndex(-1);
		txtData.setText(null);
		cbResi.setSelectedIndex(-1);
		
		fisio= null;
	cbFisio.requestFocus();
		tbFisio.clearSelection();
		lblId.setText("ID:");
	}

	private void criarTabela() {
		FisioTableModel model = new FisioTableModel(fisios);
    	tbFisio.setModel(model);
		tbFisio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbFisio.getColumnModel().getColumn(0).setPreferredWidth(280);
		tbFisio.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbFisio.getSelectedRow() >= 0) {
					fisio = fisios.get(tbFisio.getSelectedRow());
					popular();
				}
			}
		});
	}

	private void popular() {
		cbFisio.setSelectedItem(fisio.getResidente().getNome());
		cbSessao.setToolTipText(fisio.getSessao()+"");
		txtData.setText(fisio.getData()+"");
		cbResi.setSelectedItem(fisio.getResidente().getNome());
		//txtRes.setText(fisio.getResidente().getNome());
		//txtIdade.setText(fisio.getResidente().getIdade());
		lblId.setText("ID: " + fisio.getId()+"");
	}
	/*public void GeraRela() {
		try {
			conexao = (Connection) connectionFactory.getConnection();
			Map parametros = new HashMap();
			JasperPrint jp = JasperFillManager.fillReport(getClass().getResource("/RelReclamacoes.jasper").getFile(), parametros, conexao);
			JasperViewer jw = new JasperViewer(jp);
			jw.setVisible(true);
			//JasperExportManager.exportReportToPdfFile(jp,"/RelReclamacoes.xml");
			FileWriter arq;
            try {
            	  arq = new FileWriter(System.getProperty("user.home") + "/Documents/PIZZARIA_RECLAMACOES.txt", true);
              //  arq = new FileWriter(new File(System.getProperty("user.home") + "/Documents/PIZZARIA_RECLAMACOES.txt"));
                arq.write(lblId.getText() + "," +txtData.getText()+";"+"\n");
                arq.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
