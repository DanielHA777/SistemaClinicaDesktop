package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistemaVila.dao.DAOFisio;
import sistemaVila.modelo.Fisio;
import tableModel.FisiosTableModel;

public class RelatorioFisio extends JFrame {
	

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTextArea taInfo;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JMenuItem itSalvar;
	private List<Fisio> fisios;
	private DAOFisio dao;
	private Fisio pedSelect;
	private JMenuItem itExcluir;
	private JTable tbFisios;

	private void criarTabela() {
		// cria tbModel
		FisiosTableModel modelPedido = new FisiosTableModel(fisios);
		//
		tbFisios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbFisios.setModel(modelPedido);
		tbFisios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbFisios.getColumnModel().getColumn(0).setPreferredWidth(40);
		tbFisios.getColumnModel().getColumn(1).setPreferredWidth(150);
		tbFisios.getColumnModel().getColumn(2).setPreferredWidth(290);
		tbFisios.getColumnModel().getColumn(3).setPreferredWidth(80);
		// listener para pedido selecionado na tabela
		tbFisios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (tbFisios.getSelectedRow() >= 0) {
					itSalvar.setEnabled(true);
					itExcluir.setEnabled(true);
					pedSelect = fisios.get(tbFisios.getSelectedRow());
					popular();
				}
			}
		});
	}

	private void popular() {
		String info = "ID: " + pedSelect.getId() + "\n\n";
		//info += "colaborador_id: "+ pedSelect.getColaborador().getId() + "\n\n";
		info += "Colaborador: " + pedSelect.getColaborador().getNome() + "\n\n";
		info += "residente:: " + pedSelect.getResidente().getNome()+ "\n\n";
		info += "sessão: " + pedSelect.getSessao() + "\n\n";
	//	info += "residente_id: " + pedSelect.getResidente().getId() + "\n\n";
		info += "data: " + pedSelect.getData() + "\n\n";
		
		/*info += "ITENS:\n\n";
		info += String.format("%-5s|%-30s|%-30s|%-10s%n", "Qtd", "Produto", "observação", "Total");
		for (ItemPedido i : pedSelect.getItens()) {
			info += String.format("%-5d|%-30s|%-30s|R$ %5.2f%n", i.getQtd(), i.getProduto().getNome(),
					i.getObservacao(), i.getTotal());
		}
  info += String.format("%nTaxa de entrega: R$ %5.2f%n", pedSelect.getTxentrega()  );
		info += String.format("Troco para: R$ %5.2f%n", pedSelect.getTroco());
		info += String.format("Total: R$ %5.2f%n", pedSelect.getvTotal());
		info += String.format("\n Funcionario: " + pedSelect.getFuncionario().getNome());
//info += String.format("\n Funcionario: " + pedSelect.getFuncionario().getCargo().values());*/
		//info += String.format("\n Cargo: : " + pedSelect.getFuncionario().getCargo().ATENDENTE);
		taInfo.setText(info);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//dao = new DAOPedido();
				try {
				RelatorioFisio frame = new RelatorioFisio();
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
	public RelatorioFisio() {
	getContentPane().setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(333, 0, 351, 527);
		getContentPane().add(scrollPane_2);
		
		tbFisios = new JTable();
		scrollPane_2.setViewportView(tbFisios);
		setVisible(true);
		//setState(Frame.ICONIFIED);
		setResizable(false);
		setTitle("Pedidos");
		setBounds(100, 100, 943, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 471, 476);
		contentPane.add(scrollPane);

		taInfo = new JTextArea();
		taInfo.setEditable(false);
		scrollPane.setViewportView(taInfo);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(taInfo, popupMenu);
		
		itSalvar = new JMenuItem("Salvar Sessão");
		itSalvar.setEnabled(false);
		itSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//verifica se existe um pedido selecionado
				if(pedSelect != null) {
					//criar jfilechooser
					JFileChooser fc = new JFileChooser();
					//ajudtar titulo
					fc.setDialogTitle("Escolha um lugar para salvar");
					//criar um file com localização e nome do arquivo
					File arquivo = new File(System.getProperty("user.home")+ 
							"/Desktop/pedido"+pedSelect.getId()+ ".txt");
					//definir o arquivo como arquivo selecionado no filechooser
					fc.setSelectedFile(arquivo);
					//abre o dialog e pega o retorno
					int retorno = fc.showSaveDialog(null);
					//verifivca se foi clicado salvar no dialog
					if(retorno == JFileChooser.APPROVE_OPTION) {
						//criar um "escritor
						try {
							FileWriter writer = new FileWriter(fc.getSelectedFile());
						// escreve no arquivo 
							writer.write(taInfo.getText());
							//fechar o escritor
							writer.close();
						} catch (IOException e1) {
	
							e1.printStackTrace();
						} 
					}
				}
			}
		});
		popupMenu.add(itSalvar);
		
		itExcluir = new JMenuItem("Excluir Sessão");
		itExcluir.setEnabled(false);
		itExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pedSelect != null) {
					// exibe o confirm dialog e guarda a resposta
					int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir a sessão de fisioterapia "
					+ pedSelect.getId() + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(resposta == 0) {
					try {
						dao.excluir(pedSelect);
						fisios = dao.listar();
						criarTabela();
						limpar();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
				}
			}
		});
		popupMenu.add(itExcluir);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(491, 42, 436, 445);
		contentPane.add(scrollPane_1);

		tbFisios = new JTable();
		scrollPane_1.setViewportView(tbFisios);

		txtBuscar = new JTextField();
		txtBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//carregar a liosta atraves do metodo procurar
				try {
					fisios = dao.Procurar(txtBuscar.getText());
					criarTabela();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		txtBuscar.setBounds(491, 11, 436, 28);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		dao = new DAOFisio();
		try {
			fisios = dao.listar();
			criarTabela();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void limpar() {
		// era variavek select
		pedSelect = null;
		taInfo.setText(null);
		tbFisios.clearSelection();
		//inabilita os dois itens do poup menu
		itSalvar.setEnabled(false);
		itExcluir.setEnabled(false);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
