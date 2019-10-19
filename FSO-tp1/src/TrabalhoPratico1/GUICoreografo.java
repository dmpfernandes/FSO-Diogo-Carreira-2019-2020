package TrabalhoPratico1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;



import TrabalhoPratico1.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico1.canalComunicacao.Mensagem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;

public class GUICoreografo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtFieldComandos;
	private JCheckBox chckbxAtivar;
	private JButton btnGerar16Comandos, btnGerar1Comando, btnGerar32Comandos, btnGerarComandosIlimitados,
			btnPararComandos;
	private CanalComunicacoes cc;
	private boolean parar;
	private int numero;
	private List<String> ultimosComandos;

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
		ultimosComandos = new ArrayList<String>();
	}

	/**
	 * Create the frame.
	 */
	public GUICoreografo() {

		cc = new CanalComunicacoes("teste.txt");
		System.out.println(cc.get());
		
		
		
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

		txtFieldComandos = new JTextArea();
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
		btnGerar16Comandos.setEnabled(false);
		btnGerar16Comandos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCommands(16);
			}
		});
		btnGerar16Comandos.setBounds(208, 92, 213, 29);
		contentPane.add(btnGerar16Comandos);

		btnGerar1Comando = new JButton("Gerar 1 comando");
		btnGerar1Comando.setEnabled(false);
		btnGerar1Comando.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCommands(1);
			}
		});
		btnGerar1Comando.setBounds(208, 51, 213, 29);
		contentPane.add(btnGerar1Comando);

		btnGerar32Comandos = new JButton("Gerar 32 comandos");
		btnGerar32Comandos.setEnabled(false);
		btnGerar32Comandos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCommands(32);
			}
		});
		btnGerar32Comandos.setBounds(208, 128, 213, 29);
		contentPane.add(btnGerar32Comandos);

		btnGerarComandosIlimitados = new JButton("Gerar comandos ilimitados");
		btnGerarComandosIlimitados.setEnabled(false);
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
		numero++;
		parar = true;
		cc.put(new Mensagem(numero, 0));
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
		
				ultimosComandos.add("Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
				
				
				
			}
			String x="";
			for (int j = ultimosComandos.size()-1; j >= ultimosComandos.size()-10 ; j--) {
				x+="\n"+ultimosComandos.get(j);
			}
			txtFieldComandos.setText(x);
			
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
		btnGerar1Comando.setEnabled(!btnGerar1Comando.isEnabled());
		btnGerar32Comandos.setEnabled(!btnGerar32Comandos.isEnabled());
		btnGerarComandosIlimitados.setEnabled(!btnGerarComandosIlimitados.isEnabled());
	}
}
