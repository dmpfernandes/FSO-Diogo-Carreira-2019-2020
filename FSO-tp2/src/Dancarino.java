import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico2.canalComunicacao.Mensagem;

public class Dancarino extends JFrame implements Runnable{
	
	private CanalComunicacoes canal;
	private BD bd;
	private MyRobotLego robot;
	private String nomeRobot;
	private static final long serialVersionUID = 1L;
	
	// ficamos com o EV8, passe de conexao 1234
	private JPanel contentPane;
	private JTextField tfRoboNome;
	private JTextField tfRaio;
	private JTextField tfAngulo;
	private JTextField tfDistancia;
	private JTextArea taConsole;

	private JButton btnEsquerda;
	private JButton btnDireita;
	private JButton btnRetaguarda;
	private JButton btnFrente;
	private JButton btnParar;

	private static Dancarino frame;


	public Dancarino(CanalComunicacoes canal) {
		this.canal = canal;
		initializeGUI();
		setVisible(true);
//		a = new Thread(this::convertMsgToCommand);
		
	}

	/**
	 * Create the frame.
	 */
	public void initializeGUI() {
		bd = new BD();
		
		this.robot = new MyRobotLego();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRobot = new JLabel("Robot: ");
		lblRobot.setBounds(93, 12, 70, 15);
		contentPane.add(lblRobot);

		tfRoboNome = new JTextField();
		tfRoboNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bd.setNomeRobot(tfRoboNome.getText());
				robot.setNomeRobot(tfRoboNome.getText());
				myPrint("nome robot: " + bd.getNomeRobot());
			}
		});
		tfRoboNome.setText(bd.getNomeRobot());
		tfRoboNome.setBounds(153, 10, 114, 19);
		contentPane.add(tfRoboNome);
		tfRoboNome.setColumns(10);

		tfRaio = new JTextField();
		tfRaio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					bd.setRaio(Integer.parseInt(tfRaio.getText()));
				} catch (NumberFormatException e) {
					tfRaio.setText(String.valueOf(bd.getRaio()));
				}
				myPrint("raio: " + bd.getRaio());
			}
		});
		tfRaio.setText(String.valueOf(bd.getRaio()));
		tfRaio.setBounds(56, 39, 60, 19);
		contentPane.add(tfRaio);
		tfRaio.setColumns(10);

		JLabel lblRaio = new JLabel("Raio:");
		lblRaio.setBounds(12, 41, 70, 15);
		contentPane.add(lblRaio);

		tfAngulo = new JTextField();
		tfAngulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					bd.setAngulo(Integer.parseInt(tfAngulo.getText()));
				} catch (NumberFormatException e) {
					tfAngulo.setText(String.valueOf(bd.getAngulo()));
				}
				myPrint("angulo: " + bd.getAngulo());
			}
		});
		tfAngulo.setText(String.valueOf(bd.getAngulo()));
		tfAngulo.setBounds(181, 39, 60, 19);
		contentPane.add(tfAngulo);
		tfAngulo.setColumns(10);

		JLabel lblAngulo = new JLabel("Angulo:");
		lblAngulo.setBounds(125, 41, 70, 15);
		contentPane.add(lblAngulo);

		tfDistancia = new JTextField();
		tfDistancia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					bd.setDistancia(Integer.parseInt(tfDistancia.getText()));
				} catch (NumberFormatException e) {
					tfDistancia.setText(String.valueOf(bd.getDistancia()));
				}
				myPrint("distancia: " + bd.getDistancia());
			}
		});
		tfDistancia.setText(String.valueOf(bd.getDistancia()));
		tfDistancia.setBounds(341, 39, 60, 19);
		contentPane.add(tfDistancia);
		tfDistancia.setColumns(10);

		JLabel lblDist = new JLabel("Distancia:");
		lblDist.setBounds(247, 41, 88, 15);
		contentPane.add(lblDist);

		btnFrente = new JButton("Frente");
		btnFrente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPrint("reta: " + bd.getDistancia());
				robot.reta(bd.getDistancia());
			}
		});
		btnFrente.setBounds(163, 72, 117, 55);
		contentPane.add(btnFrente);

		btnParar = new JButton("Parar");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.parar(true);
				myPrint("Parar Assincrono: true");
			}
		});
		btnParar.setBackground(Color.RED);
		btnParar.setForeground(Color.BLACK);
		btnParar.setBounds(163, 126, 117, 55);
		contentPane.add(btnParar);

		btnRetaguarda = new JButton("Retaguarda");
		btnRetaguarda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.reta(-bd.getDistancia());
				myPrint("reta: " + -bd.getDistancia());
			}
		});
		btnRetaguarda.setBounds(163, 179, 117, 55);
		contentPane.add(btnRetaguarda);

		btnDireita = new JButton("Direita");
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.curvarDireita(bd.getRaio(), bd.getAngulo());
				myPrint("Curva Direita \n raio: " + bd.getRaio() + " angulo: " + bd.getAngulo());
			}
		});
		btnDireita.setBounds(283, 126, 117, 55);
		contentPane.add(btnDireita);

		btnEsquerda = new JButton("Esquerda");
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.curvarEsquerda(bd.getRaio(), bd.getAngulo());
				myPrint("Curva Esquerda \n raio: " + bd.getRaio() + " angulo: " + bd.getAngulo());
			}
		});
		btnEsquerda.setBounds(46, 126, 117, 55);
		contentPane.add(btnEsquerda);

		taConsole = new JTextArea();
		taConsole.setEditable(false);
		taConsole.setBounds(12, 238, 420, 170);
		contentPane.add(taConsole);

	}
	
	@Override
	public void run() {
		while(true) {
			canal.open();
			Mensagem msg = canal.lerMsg();
			System.out.println("DANCARINO: Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
			if(msg.getNumero() == 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println(msg.toString());
				convertMsgToCommand(msg);
			}
		}
	}

	public void convertMsgToCommand(Mensagem msg) {
		
		int ordem = msg.getOrdem();
		switch (ordem) {
		case 0:
			robot.parar(false);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			robot.reta(bd.getDistancia());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			robot.curvarDireita(bd.getRaio(), bd.getAngulo());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			robot.curvarEsquerda(bd.getRaio(), bd.getAngulo());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			robot.reta(-bd.getDistancia());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			robot.parar(true);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

	}

	public void startRobot() {
		robot = new MyRobotLego();
		robot.setNomeRobot(nomeRobot);
		robot.startRobot();
	}
	
	

	public String getNomeRobot() {
		return nomeRobot;
	}

	public void setNomeRobot(String nomeRobot) {
		this.nomeRobot = nomeRobot;
	}

	public void myPrint(String msg) {
		taConsole.setText(taConsole.getText() + "\n" + msg);
	}
	
}
