package TrabalhoPratico1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.glass.ui.Robot;

import TrabalhoPratico1.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico1.canalComunicacao.Mensagem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;

public class GUICoreografo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFieldComandos;
	private JCheckBox chckbxAtivar;
	private JButton btnGerar16Comandos, btnGerar1Comando, btnGerar32Comandos, btnGerarComandosIlimitados,
			btnPararComandos;
	private CanalComunicacoes cc;
	private boolean parar;
	private int numero;

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

	public void initializeVariables() {
		parar = false;
		numero = -1;
	}

	/**
	 * Create the frame.
	 */
	public GUICoreografo() {

		cc = new CanalComunicacoes("teste.txt");
		initializeVariables();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUltmosComandos = new JLabel("Ultímos 10 comandos:");
		lblUltmosComandos.setBounds(6, 23, 155, 16);
		contentPane.add(lblUltmosComandos);

		txtFieldComandos = new JTextField();
		txtFieldComandos.setBounds(6, 51, 190, 206);
		contentPane.add(txtFieldComandos);
		txtFieldComandos.setColumns(10);

		chckbxAtivar = new JCheckBox("Ativar");
		chckbxAtivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeButtons();
			}
		});
		chckbxAtivar.setBounds(212, 19, 128, 23);
		contentPane.add(chckbxAtivar);

		btnGerar16Comandos = new JButton("Gerar 16 comandos");
		btnGerar16Comandos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCommands(16);
			}
		});
		btnGerar16Comandos.setBounds(208, 92, 213, 29);
		contentPane.add(btnGerar16Comandos);

		btnGerar1Comando = new JButton("Gerar 1 comando");
		btnGerar1Comando.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCommands(1);
			}
		});
		btnGerar1Comando.setBounds(208, 51, 213, 29);
		contentPane.add(btnGerar1Comando);

		btnGerar32Comandos = new JButton("Gerar 32 comandos");
		btnGerar32Comandos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCommands(32);
			}
		});
		btnGerar32Comandos.setBounds(208, 128, 213, 29);
		contentPane.add(btnGerar32Comandos);

		btnGerarComandosIlimitados = new JButton("Gerar comandos ilimitados");
		btnGerarComandosIlimitados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCommands(-1);
			}
		});
		btnGerarComandosIlimitados.setBounds(208, 169, 213, 29);
		contentPane.add(btnGerarComandosIlimitados);

		btnPararComandos = new JButton("Parar Comandos");
		btnPararComandos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopCommands();
			}
		});
		btnPararComandos.setBounds(208, 211, 213, 29);
		contentPane.add(btnPararComandos);
	}

	private void stopCommands() {
		parar = true;
		cc.put(new Mensagem(numero++, 0));
	}

	private void generateCommands(int i) {
		if (i == -1) {
			while (!parar) {
				cc.put(generateRandomCommand());
			}
		} else {
			for (int j = 0; j < i; j++) {
				Mensagem msg = generateRandomCommand();
				cc.put(msg);
				txtFieldComandos.setText("Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
			}
		}
	}

	private Mensagem generateRandomCommand() {
		numero++;
		Random r = new Random();
		int ordem = r.nextInt(4);
		return new Mensagem(numero, ordem);
	}

	protected void changeButtons() {
		btnGerar16Comandos.setEnabled(!btnGerar16Comandos.isEnabled());
		btnGerar1Comando.setEnabled(!btnGerar16Comandos.isEnabled());
		btnGerar32Comandos.setEnabled(!btnGerar16Comandos.isEnabled());
		btnGerarComandosIlimitados.setEnabled(!btnGerar16Comandos.isEnabled());
	}
}
