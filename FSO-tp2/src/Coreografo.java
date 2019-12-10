import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico2.canalComunicacao.Mensagem;

public class Coreografo extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CanalComunicacoes canal;
	private boolean parar;
	private int numero;
	private BD bd;
	private List<String> ultimosComandos;
	
	private JPanel contentPane;
	private JTextArea txtFieldComandos;
	private JCheckBox chckbxAtivar;
	private JButton btnGerar16Comandos, btnGerar1Comando, btnGerar32Comandos, btnGerarComandosIlimitados,
			btnPararComandos;
	private CanalComunicacoes cc;
	private Semaphore atividade;
	private boolean onoff = true;
	private String estado = "atuar";

	public void initializeVariables() {
		parar = false;
		numero = -1;
		bd = new BD();
		ultimosComandos = new ArrayList<String>();
		bd.setNomeRobot("RobotLego");
		setVisible(true);
		atividade = new Semaphore(0);
	}

	public void run() {
		canal.open();
		while(onoff) {
			switch(estado) {
			case "dormir":
				try {
					atividade.acquire();
					estado = "atuar";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
				break;
			case "atuar":
				generateCommands();
				
				break;
			case "kill":
				break;
			}
		}
		
	}
	
	

	public Coreografo(CanalComunicacoes canal) {
		this.canal = canal;
		
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



		btnPararComandos = new JButton("Parar Comandos");
		btnPararComandos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopCommands();
			}
		});
		btnPararComandos.setBounds(208, 211, 213, 29);
		contentPane.add(btnPararComandos);
	}

	public void startCommands() {
		atividade.release();
		parar = false;
		
	}
	
	public void stopCommands() {
	
		parar = true;
		estado="dormir";
	}
	
	public void killApp() {
		estado = "kill";
		dispose();
		parar = true;
		onoff = false;
		
		
	}


	
	public void showComandosExecutados() {
		String textCommand = "";
		txtFieldComandos.setText("");
		for (int j = 1; j < 11; j++) {
			int counter = ultimosComandos.size()-j;
			if(counter >= 0 && ultimosComandos.get(counter)!=null && !ultimosComandos.get(counter).isEmpty()) {
				textCommand = ultimosComandos.get(counter) + "\n";
				txtFieldComandos.setText(textCommand+txtFieldComandos.getText() );
			}else {
				break;
			}
			
		}
	}

	public List<String> generateCommands() {
		if (!parar) {
			
			Mensagem msg = generateRandomCommand();
			canal.escreverMsg(msg);
//			System.out.println("COREOGRAFO : Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
			ultimosComandos.add("Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
			showComandosExecutados();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			generateCommands();
		}
		return ultimosComandos;
	}

	private Mensagem generateRandomCommand() {
		numero++;
		Random r = new Random();
		int ordem = r.nextInt(4);
		if (ordem == 0) {
			numero--;
			return generateRandomCommand();
		} else {
			return new Mensagem(numero, ordem);
		}

	}

	public boolean isParar() {
		return parar;
	}

}
