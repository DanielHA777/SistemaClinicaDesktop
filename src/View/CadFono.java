package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOFono;
import sistemaVila.dao.DAOResidente;
import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Fisio;
import sistemaVila.modelo.Fono;
import sistemaVila.modelo.Residente;
import sistemaVila.modelo.Sessao;
import tableModel.FisioTableModel;
import tableModel.FonoTableModel;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CadFono extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private Colaborador colaborador;
	private Fono fono;
	private boolean insertUnico = false;
	private Residente residente;
	private List<Fono> fonos;
	private DAOFono daoFono;
	private DAOResidente daoResidente;
	private DAOcolaborador daoCola;
	private JTable tbFono;
	private JButton btnBuscar;
	private JComboBox cbSessao;
	private JScrollPane scrollPane;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JButton btnLimpar;
	private JLabel lblId;
	private JTextField txtData;
	private JComboBox cbCola;
	private JComboBox cbResi;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadFono frame = new CadFono();
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
	public CadFono() {
		setResizable(false);
		daoFono = new DAOFono();
		daoCola = new DAOcolaborador();
		daoResidente = new DAOResidente();
		setTitle("Fonoaudiologia ");
		setBounds(100, 100, 599, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cbSessao = new JComboBox();
		cbSessao.setModel(new DefaultComboBoxModel(Sessao.values()));
		cbSessao.setBounds(97, 62, 161, 22);
		contentPane.add(cbSessao);
		
		JLabel lblNewLabel = new JLabel("Colaborador:");
		lblNewLabel.setBounds(10, 34, 77, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Residente:");
		lblNewLabel_1.setBounds(211, 34, 74, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Sess\u00E3o:");
		lblNewLabel_3.setBounds(10, 66, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		lblId = new JLabel("Id:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fonos = daoFono.listar(txtBuscar.getText());
					criarTabela();
				} catch (SQLException e1) {				
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(43, 115, 89, 23);
		contentPane.add(btnBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(142, 116, 396, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 147, 495, 114);
		contentPane.add(scrollPane);
		
		tbFono = new JTable();
		scrollPane.setViewportView(tbFono);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbCola.getSelectedIndex()== -1) { 
					JOptionPane.showMessageDialog(null, "informe o profissional", "Aviso", JOptionPane.ERROR_MESSAGE);
			cbCola.requestFocus();
				}else if(cbSessao.getSelectedIndex() == -1) {   
					JOptionPane.showMessageDialog(null, "informe a Sessão", "Aviso", JOptionPane.ERROR_MESSAGE);
					cbSessao.requestFocus();
				}
				else if(txtData.getText().trim().isEmpty()) {   
					JOptionPane.showMessageDialog(null, "informe a data", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtData.requestFocus();
				}else {
					if(fono == null) {
						fono = new Fono();
						fono.setResidente(residente);
						fono.setColaborador(colaborador);
					}
					fono.setColaborador(colaborador);
					fono.setResidente(residente);
					fono.setSessao((Sessao) cbSessao.getSelectedItem());
					fono.setData(txtData.getText().trim());		
					fono.setNomeCola(cbCola.getSelectedItem().toString());
					fono.setNomeResi(cbResi.getSelectedItem().toString());
						try {
							 if(fono.getId() == 0) {
							daoFono.inserir(fono);
						}else {
							 daoFono.atualizar(fono); 
						 }				
						limpar();
						fonos = daoFono.listar();
						criarTabela();
						if(insertUnico == true) {
							dispose();
						} }catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		btnSalvar.setBounds(112, 272, 89, 39);
		contentPane.add(btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fono == null) {
					JOptionPane.showMessageDialog(null, "Selecione uma sessão para excluir", "selecione", JOptionPane.WARNING_MESSAGE); 
				}else {
					java.awt.Toolkit.getDefaultToolkit().beep();   
					int botao = JOptionPane.showConfirmDialog(null, "Deseja excluir a sessão "+ fono.getResidente().getNome()+ " ?" , "CONFIRMAR EXCLUSÃO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(botao == 0) {
						try {
							daoFono.excluir(fono);
							fonos = daoFono.listar();
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
		btnExcluir.setBounds(271, 272, 89, 39);
		contentPane.add(btnExcluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(423, 272, 89, 39);
		contentPane.add(btnLimpar);
		
		txtData = new JTextField();
		txtData.setBounds(338, 63, 104, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Data:");
		lblNewLabel_4.setBounds(282, 66, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		cbCola = new JComboBox();
		cbCola.setBounds(97, 30, 104, 22);
		contentPane.add(cbCola);
		
		cbResi = new JComboBox();
		cbResi.setBounds(282, 30, 104, 22);
		contentPane.add(cbResi);
		
		try {
			fonos = daoFono.listar();
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar as fonoaudiologias", "erro", JOptionPane.ERROR_MESSAGE);
		}
		setLocationRelativeTo(null);
		
try {
			
			List<Colaborador> lista = daoCola.buscaFono();
			for (Colaborador funcionario: lista) {
				cbCola.setSelectedIndex(-1);
				cbCola.addItem(funcionario);
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
		cbCola.setSelectedIndex(0);
		cbResi.setSelectedIndex(0);
		cbSessao.setSelectedIndex(0);
		txtData.setText(null);
		fono= null;
	cbCola.requestFocus();
		tbFono.clearSelection();
		lblId.setText("ID:");
	}

	private void criarTabela() {
		FonoTableModel model = new FonoTableModel(fonos);
    	tbFono.setModel(model);
		tbFono.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbFono.getColumnModel().getColumn(0).setPreferredWidth(280);
		tbFono.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbFono.getSelectedRow() >= 0) {
					fono = fonos.get(tbFono.getSelectedRow());
					popular();
				}
			}
		});
	}

	private void popular() {
	  
	    cbCola.setToolTipText(fono.getNomeCola());
		cbSessao.setToolTipText(fono.getSessao()+"");
		txtData.setText(fono.getData()+"");
		cbResi.setToolTipText(fono.getNomeResi());
		//txtIdade.setText(fono.getResidente().getIdade());
		lblId.setText("ID: " + fono.getId()+"");
	}
}
