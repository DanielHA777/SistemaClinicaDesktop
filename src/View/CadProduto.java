package View;
import java.awt.EventQueue;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import sistemaVila.dao.DAOProduto;
import sistemaVila.modelo.Produto;
import sistemaVila.util.ImageUtil;
import tableModel.ProdutoTableModel;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.awt.Color;

public class CadProduto extends JFrame {
	private JPanel contentPane;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JScrollPane scrollPane;
	private JTable tbProduto;
	private DAOProduto dao;
	private Produto produto;
	private List<Produto> produtos;
	private boolean insertUnico = false;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	private byte[] img;
	private JLabel lblId;
	private JLabel lblImg;
	private JTextField txtNome;
	private JTextField txtValor;
	private JTextArea taDesc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadProduto frame = new CadProduto();
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
	public CadProduto() {
		dao = new DAOProduto();
		setTitle("Produtos");
		setBounds(100, 100, 674, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "informe o nome", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtNome.requestFocus();
				} else if (txtValor.getText().trim().isEmpty()) { 
					JOptionPane.showMessageDialog(null, "informe o valor", "Aviso", JOptionPane.ERROR_MESSAGE);
					txtNome.requestFocus();
				} else {
					if (produto == null) {
						produto = new Produto();
					}
					produto.setValor(Double.parseDouble(txtValor.getText()));
					produto.setNome(txtNome.getText().trim());
					produto.setDescricao(taDesc.getText());
					produto.setImgProduto(img);
							try {
								if (produto.getId() == 0) {
								dao.inserir(produto);
								}else {
								dao.atualizar(produto); 
							}
							limpar();
							produtos = dao.listar();
							criarTabela();
							}	
								catch (Exception e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, e1.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
							}
				}
			}
		});
		btnSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalvar.setBounds(112, 367, 89, 36);
		contentPane.add(btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (produto == null) {
					JOptionPane.showMessageDialog(null, "Selecione um produto para excluir", "selecione",
							JOptionPane.WARNING_MESSAGE);
				} else {
					java.awt.Toolkit.getDefaultToolkit().beep();
					int botao = JOptionPane.showConfirmDialog(null,
							"Deseja excluir o produto " + produto.getNome() + " ?", "CONFIRMAR EXCLUSÃO",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (botao == 0) {
						try {
							dao.excluir(produto);
							produtos = dao.listar();
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
		btnExcluir.setBounds(283, 367, 89, 36);
		contentPane.add(btnExcluir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBounds(464, 367, 89, 36);
		contentPane.add(btnLimpar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 203, 545, 153);
		contentPane.add(scrollPane);
		
		tbProduto = new JTable();
		scrollPane.setViewportView(tbProduto);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					produtos = dao.listar(txtBuscar.getText()); 
					criarTabela();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBounds(46, 174, 89, 23);
		contentPane.add(btnBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(145, 175, 446, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(46, 52, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(256, 52, 46, 14);
		contentPane.add(lblValor);
		
		JLabel lblNewLabel_1 = new JLabel("Descri\u00E7\u00E3o:");
		lblNewLabel_1.setBounds(46, 89, 63, 14);
		contentPane.add(lblNewLabel_1);
		
		lblImg = new JLabel("");
		lblImg.setBackground(Color.BLACK);
		lblImg.setForeground(Color.BLACK);
		lblImg.setOpaque(true);
		lblImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { 
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File(System.getProperty("user.home")));
					javax.swing.filechooser.FileFilter filtroImg = new FileNameExtensionFilter("imagens",
							ImageIO.getReaderFileSuffixes());
					fc.setFileFilter(filtroImg);
					int retorno = fc.showOpenDialog(null);
					if (retorno == JFileChooser.APPROVE_OPTION) {
						File arqSelec = fc.getSelectedFile();
						try {
							BufferedImage foto = ImageIO.read(arqSelec); 
							ImageIcon imgFoto = new ImageIcon(foto);
							lblImg.setIcon(ImageUtil.redimensiona(imgFoto, lblImg.getWidth(), lblImg.getHeight()));
							ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 																		
							ImageIO.write(foto, "png", outStream); 
							img = outStream.toByteArray(); 
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(null, e2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		lblImg.setToolTipText("Clique 2x para selecionar ");
		lblImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblImg.setBounds(464, 33, 146, 115);
		//ImageIcon fotoP = new ImageIcon(getClass().getResource("/imagem.png"));
		//lblImg.setIcon(ImageUtil.redimensiona(fotoP, lblImg.getWidth(), lblImg.getHeight()));
		contentPane.add(lblImg);
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				lblImg.setEnabled(true);
			}
		});
		txtNome.setBounds(84, 49, 136, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent tecla) {
				if (!Character.isDigit(tecla.getKeyChar()) && tecla.getKeyChar() != '.') {
					tecla.consume(); 
				}
				if (tecla.getKeyChar() == '.' && txtValor.getText().contains(".")) {
					{
						tecla.consume(); 
					}
				}
				if (txtValor.getText().length() == 6) {
					tecla.consume();
				}
			}
		});
		txtValor.setBounds(286, 49, 112, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(112, 89, 286, 36);
		contentPane.add(scrollPane_1);
		
		taDesc = new JTextArea();
		scrollPane_1.setViewportView(taDesc);
		
		try {
			produtos = dao.listar();
			criarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "não foi possível carregar os produtos", "erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	private void limpar() {
		txtNome.setText(null);
		txtValor.setText(null);
		taDesc.setText(null);
		//ImageIcon fotoP = new ImageIcon(getClass().getResource("/salvaimg.jpg"));
	//	lblImg.setIcon(ImageUtil.redimensiona(fotoP, lblImg.getWidth(), lblImg.getHeight()));
		img = null;
		produto = null;
		txtNome.requestFocus();
	}

	private void criarTabela() {
		ProdutoTableModel model = new ProdutoTableModel(produtos);
		tbProduto.setModel(model);
		tbProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProduto.getColumnModel().getColumn(0).setPreferredWidth(280);
		tbProduto.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbProduto.getSelectedRow() >= 0) {
					produto = produtos.get(tbProduto.getSelectedRow());
					popular();
				}
			}
		});
	}

	private void popular() {
		img = produto.getImgProduto();
		if(img != null) {
			ImageIcon icon = new ImageIcon(img);
			lblImg.setIcon(ImageUtil.redimensiona(icon, lblImg.getWidth(), lblImg.getHeight()));
		}
		/*else {
			ImageIcon fotoP = new ImageIcon(getClass().getResource("/salvaimg.jpg"));
			lblImg.setIcon(ImageUtil.redimensiona(fotoP, lblImg.getWidth(), lblImg.getHeight()));
		}*/
		txtNome.setText(produto.getNome());
		lblId.setText("ID: " + produto.getId());
		txtValor.setText(produto.getValor()+"");
		taDesc.setText(produto.getDescricao());
	}
	
}
