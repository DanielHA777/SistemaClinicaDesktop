package View;




import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sistemaVila.modelo.Colaborador;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.Component;
import java.awt.SystemColor;

public class TelaMenu extends JFrame {

	private PainelPizza contentPane;
	private JButton btnBusca;
	private JButton btnCondutas;
	private JButton btnColaboradores;
	private JButton btnResidentes;
	public static Colaborador colaLogado;
	private JButton btnFisioterapia;
	private JButton btnFonoaudiologia;
	private JMenuItem itemBusca;
	private JMenuItem itemConduta;
	private JMenuItem itemCola;
	private JMenuItem itemResidente;
	private JMenuItem itemFisio;
	private JMenuItem itemFono;
	private JButton btnClientes;
	private JMenuItem itemCliente;
	private JButton btnProdutos;
	private JButton btnVendas;
	private JLabel lblImg;
	private JButton btnExames;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaMenu frame = new TelaMenu();
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
	public TelaMenu() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Menu");
		
		setBounds(100, 100, 780, 710);
		contentPane = new PainelPizza();
		contentPane.setBackground(SystemColor.activeCaption);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 181, 22);
		contentPane.add(menuBar);
		
		JMenu menuitens = new JMenu("Cadastros");
		menuBar.add(menuitens);
		
		itemBusca = new JMenuItem("Busca Ativa");
		itemBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBusca.doClick();
			}
		});
		menuitens.add(itemBusca);
		
		itemConduta = new JMenuItem("Condutas");
		itemConduta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCondutas.doClick();
			}
		});
		menuitens.add(itemConduta);
		
		itemCola = new JMenuItem("Colaboradores");
		itemCola.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnColaboradores.doClick();
			}
		});
		menuitens.add(itemCola);
		
		itemResidente = new JMenuItem("Residentes");
		itemResidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnResidentes.doClick();
			}
		});
		menuitens.add(itemResidente);
		
		itemFisio = new JMenuItem("Fisioterapia");
		itemFisio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFisioterapia.doClick();
			}
		});
		menuitens.add(itemFisio);
		
		itemFono = new JMenuItem("Fonoaudiologia");
		itemFono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFonoaudiologia.doClick();
			}
		});
		menuitens.add(itemFono);
		
		itemCliente = new JMenuItem("Clientes");
		itemCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClientes.doClick();
			}
		});
		menuitens.add(itemCliente);
		
		btnBusca = new JButton("Busca Ativa");
		btnBusca.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadBuscaAtiva frame = new CadBuscaAtiva();
				frame.setVisible(true);
			}
		});
		btnBusca.setBounds(49, 64, 155, 78);
		contentPane.add(btnBusca);
		
		btnCondutas = new JButton("Condutas");
		btnCondutas.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnCondutas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadConduta frame = new CadConduta();
				frame.setVisible(true);
			}
		});
		btnCondutas.setBounds(49, 153, 155, 78);
		contentPane.add(btnCondutas);
		
		btnColaboradores = new JButton("Colaboradores");
		btnColaboradores.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnColaboradores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadColaborador frame = new CadColaborador();
				frame.setVisible(true);
			}
		});
		btnColaboradores.setBounds(49, 242, 155, 76);
		contentPane.add(btnColaboradores);
		
		btnResidentes = new JButton("Residentes");
		btnResidentes.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnResidentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadResidente frame = new CadResidente();
				frame.setVisible(true);
			}
		});
		btnResidentes.setBounds(49, 329, 155, 82);
		contentPane.add(btnResidentes);
		
		btnFisioterapia = new JButton("Fisioterapia");
		btnFisioterapia.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnFisioterapia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadFisio frame = new CadFisio();
				frame.setVisible(true);
			}
		});
		btnFisioterapia.setBounds(49, 422, 155, 90);
		contentPane.add(btnFisioterapia);
		
		btnFonoaudiologia = new JButton("Fonoaudiologia");
		btnFonoaudiologia.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnFonoaudiologia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadFono frame = new CadFono();
				frame.setVisible(true);
			}
		});
		btnFonoaudiologia.setBounds(324, 64, 155, 78);
		contentPane.add(btnFonoaudiologia);
		
		btnClientes = new JButton("Clientes");
		btnClientes.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadCliente frame = new CadCliente();
				frame.setVisible(true);
			}
		});
		btnClientes.setBounds(324, 153, 155, 78);
		contentPane.add(btnClientes);
		
		btnProdutos = new JButton("Produtos");
		btnProdutos.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadProduto frame = new CadProduto();
				frame.setVisible(true);
			}
		});
		btnProdutos.setBounds(324, 242, 155, 76);
		contentPane.add(btnProdutos);
		
		btnVendas = new JButton("Vendas");
		btnVendas.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadVenda frame = new CadVenda();
				frame.setVisible(true);
			}
		});
		btnVendas.setBounds(324, 329, 155, 82);
		contentPane.add(btnVendas);
		
		JButton btnMscarasEstoque = new JButton("M\u00E1scaras ");
		btnMscarasEstoque.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnMscarasEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadMascara frame = new CadMascara();
				frame.setVisible(true);
			}
		});
		btnMscarasEstoque.setBounds(324, 422, 155, 90);
		contentPane.add(btnMscarasEstoque);
		
		btnExames = new JButton("Cad. de Exames");
		btnExames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadExame frame = new CadExame();
				frame.setVisible(true);
			}
		});
		btnExames.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnExames.setBounds(509, 64, 155, 78);
		contentPane.add(btnExames);
		
		lblImg = new JLabel("");
		lblImg.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblImg.setRequestFocusEnabled(false);
		lblImg.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		//lblImg.setIcon(new ImageIcon("C:\\Users\\recepcao\\Pictures\\logo.png"));
		lblImg.setBounds(670, -175, 797, 998);
		
		contentPane.add(lblImg);
		
		
	}
	class PainelPizza extends JDesktopPane {
		@Override
		protected void paintComponent(Graphics g) {
			//extrair do graphics g uma referencia para graphics 2d
			Graphics2D g2d = (Graphics2D) g;
			//carregar a imagem do background 
			try {
				BufferedImage imagem = ImageIO.read(getClass().getResource("/imagens/logo.png"));
				g2d.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}}
}
