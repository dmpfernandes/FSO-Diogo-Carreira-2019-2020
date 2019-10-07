package TrabalhoPratico1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import TrabalhoPratico1.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico1.canalComunicacao.Mensagem;

public class GUICoreografo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUICoreografo frame = new GUICoreografo();
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
	public GUICoreografo() {
		Mensagem msg = new Mensagem(2,3);
		Mensagem msg1 = new Mensagem(1,2);
		Mensagem msg2 = new Mensagem(2,3);
		Mensagem msg3 = new Mensagem(3,4);
		Mensagem msg4 = new Mensagem(4,5);
		
		CanalComunicacoes cc = new CanalComunicacoes("teste.txt");
		cc.put(msg);
		cc.put(msg1);
		cc.put(msg2);
		cc.put(msg3);
		cc.put(msg4);
		System.out.println(cc.get());
		System.out.println(cc.get());
		System.out.println(cc.get());
		System.out.println(cc.get());
		System.out.println(cc.get());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
