package View;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import sistemaVila.dao.DAOcolaborador;
import sistemaVila.modelo.Colaborador;
import sistemaVila.util.PasswordUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.SystemColor;

public class Login extends JFrame {

	private PainelPizza contentPane;
	private JTextField txtUsu;
	private Colaborador colaborador;
	private DAOcolaborador daoC;
	private JButton btnLogin;
	private JPasswordField pfSenha;
	private JLabel lblimg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		daoC = new DAOcolaborador();
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 322);
		contentPane = new PainelPizza();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usu\u00E1rio:");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNewLabel.setBounds(98, 120, 69, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(98, 158, 69, 14);
		contentPane.add(lblNewLabel_1);
		
		txtUsu = new JTextField();
		txtUsu.setBounds(177, 119, 187, 20);
		contentPane.add(txtUsu);
		txtUsu.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					String senha = new String(pfSenha.getPassword());
					//senha = PasswordUtil.criptografa256(senha);
					colaborador = daoC.selecionar(txtUsu.getText(),senha );
					if(colaborador != null) {
						JOptionPane.showMessageDialog(null,"Bem vindo"+ " " +colaborador.getNome());
						TelaMenu.colaLogado = colaborador;
						TelaMenu frame = new TelaMenu();
						frame.setVisible(true);				
					}else {
							JOptionPane.showMessageDialog(null, "Senha ou Usuário incorretos", "Erro", JOptionPane.ERROR_MESSAGE);						
						}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(203, 188, 89, 36);
		contentPane.add(btnLogin);
		
		pfSenha = new JPasswordField();
		pfSenha.setBounds(177, 157, 187, 20);
		contentPane.add(pfSenha);
		
		lblimg = new JLabel("");
		//lblimg.setIcon(new ImageIcon("C:\\Users\\recepcao\\Pictures\\logoo.png"));
		lblimg.setBounds(87, 0, 347, 109);
		contentPane.add(lblimg);
		
	}
	class PainelPizza extends JDesktopPane {
		@Override
		protected void paintComponent(Graphics g) {
			//extrair do graphics g uma referencia para graphics 2d
			Graphics2D g2d = (Graphics2D) g;
			//carregar a imagem do background 
			try {
				BufferedImage imagem = ImageIO.read(getClass().getResource("/imagens/logoo.png"));
				g2d.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}
}
