package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import sistemaVila.dao.ConnectionFactory;
import sistemaVila.dao.DAOFisio;
import sistemaVila.dao.DAOResidente;
import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.Colaborador;
import sistemaVila.modelo.Fisio;
import sistemaVila.modelo.Residente;
import sistemaVila.modelo.Sessao;
import tableModel.FisioTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Font;

public class ViewFisio extends JFrame {

	private JPanel contentPane;
	private JTable tbFisio;
	private List<Fisio> fisios;
	private List<Residente> resis;
	private DAOcolaborador daoCola;
	private Fisio fisio;
	private DAOFisio  dao;
	private DAOResidente daoResi;
	private JComboBox cbFisio;
	private JButton txtBuscar;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JComboBox cbResi;
	private JLabel lblNewLabel_2;
	private JComboBox cbSessao;
	private JLabel lblNewLabel_3;
	private Connection conexao;
	private JTextField txtData;
	private JButton btnNewButton;
	private JButton btnLimpar;
	private JTextField txtTotal;
	private JButton btnRela;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewFisio frame = new ViewFisio();
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
	public ViewFisio() {
		daoCola = new DAOcolaborador();
		daoResi = new DAOResidente();
		dao = new DAOFisio();
		setTitle("Relat\u00F3rio Fisioterapia");
		setResizable(false);
		setBounds(100, 100, 661, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 156, 578, 221);
		contentPane.add(scrollPane);
		
		tbFisio = new JTable();
		scrollPane.setViewportView(tbFisio);
		
		cbFisio = new JComboBox();
		cbFisio.setBounds(73, 51, 109, 22);
		contentPane.add(cbFisio);
		
		txtBuscar = new JButton("Buscar:");
		txtBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cbFisio.getSelectedIndex() == -1 && cbResi.getSelectedIndex() == -1 && cbSessao.getSelectedIndex() == -1 && txtData.getText() == null) {
						JOptionPane.showMessageDialog(null, "Escolha uma opção");
					}else if(cbFisio.getSelectedItem() != null){
						fisios = dao.listar(cbFisio.getSelectedItem().toString());
						criarTabela();
					}else if(cbResi.getSelectedItem() != null) {
						fisios = dao.listarResi(cbResi.getSelectedItem().toString());
						criarTabela();
					}else if(cbSessao.getSelectedItem() != null) {
						fisios = dao.listarSessao(cbSessao.getSelectedIndex());
						criarTabela();
					}else if(txtData.getText() != null) {
						fisios = dao.listarData(txtData.getText());
						criarTabela();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		txtBuscar.setBounds(32, 129, 89, 23);
		contentPane.add(txtBuscar);
		
		lblNewLabel = new JLabel("Fisioterapeuta:");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel.setBounds(71, 26, 562, 14);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Residente:");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(216, 26, 417, 14);
		contentPane.add(lblNewLabel_1);
		
		cbResi = new JComboBox();
		cbResi.setBounds(218, 51, 101, 22);
		contentPane.add(cbResi);
		
		lblNewLabel_2 = new JLabel("Sess\u00E3o");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(367, 26, 266, 14);
		contentPane.add(lblNewLabel_2);
		
		cbSessao = new JComboBox();
		cbSessao.setModel(new DefaultComboBoxModel(Sessao.values()));
		cbSessao.setSelectedIndex(-1);
		cbSessao.setBounds(369, 51, 123, 22);
		contentPane.add(cbSessao);
		
		lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(529, 26, 104, 14);
		contentPane.add(lblNewLabel_3);
		
		txtData = new JTextField();
		txtData.setBounds(524, 52, 86, 20);
		contentPane.add(txtData);
		txtData.setColumns(10);
		
		btnNewButton = new JButton("Exibir Total");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {if(cbSessao.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Escolha uma sessão");
				}else if (cbSessao.getSelectedIndex() != -1 && cbFisio.getSelectedItem() == null){
					 fisios = dao.listarSessao(cbSessao.getSelectedIndex());
					 txtTotal.setText(fisios.size()+"");
				}else if(cbSessao.getSelectedIndex() != -1 && cbFisio.getSelectedItem() != null) {
					 fisios = dao.listarSessaoF(cbSessao.getSelectedIndex(), cbFisio.getSelectedItem().toString());
					 txtTotal.setText(fisios.size()+"");
				}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(499, 129, 112, 23);
		contentPane.add(btnNewButton);
		
		btnLimpar = new JButton("Limpar ");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbFisio.setSelectedIndex(-1);
				cbResi.setSelectedIndex(-1);
				cbSessao.setSelectedIndex(-1);
				txtData.setText(null);
			}
		});
		btnLimpar.setBounds(521, 388, 89, 23);
		contentPane.add(btnLimpar);
		
		txtTotal = new JTextField();
		txtTotal.setBounds(403, 130, 86, 20);
		contentPane.add(txtTotal);
		txtTotal.setColumns(10);
		
		btnRela = new JButton("Gerar");
		btnRela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeraRela();
			}
		});
		btnRela.setBounds(432, 388, 89, 23);
		contentPane.add(btnRela);
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
	
	List<Residente> lista = daoResi.buscaResi();
	for (Residente func: lista) {
		cbResi.setSelectedIndex(-1);
		cbResi.addItem(func);
		//cbResi.setSelectedItem(func.getNome());
	}
} catch (Exception e) {
	e.printStackTrace();
}
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
					//popular();
				}
			}
		});
	}
	public void GeraRela() {
		try {
			conexao = (Connection) ConnectionFactory.getConnection();
			Map parametros = new HashMap();
			JasperPrint jp = JasperFillManager.fillReport(getClass().getResource("/VILAVIDA - ADMINISTRATIVO/Logins/Blank_A4.jasper").getFile(), parametros, conexao);
			JasperViewer jw = new JasperViewer(jp);
			jw.setVisible(true);
			//JasperExportManager.exportReportToPdfFile(jp,"/RelReclamacoes.xml");
			//FileWriter arq;
            /*try {
            	  arq = new FileWriter(System.getProperty("user.home") + "/Documents/PIZZARIA_RECLAMACOES.txt", true);
                arq.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
