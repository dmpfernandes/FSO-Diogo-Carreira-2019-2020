package TrabalhoPratico1;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import TrabalhoPratico1.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico1.canalComunicacao.Mensagem;

public class GUIDancarino extends JFrame {

	private static final long serialVersionUID = 1L;
	// ficamos com o EV8, passe de conexao 1234
	private JPanel contentPane;
	private JTextField tfRoboNome;
	private JTextField tfRaio;
	private JTextField tfAngulo;
	private JTextField tfDistancia;
	private JTextArea taConsole;
	private JCheckBox chckbxAtivarCoreagrafo;

	private BD bd;
	private MyRobotLego robot;
	private GUICoreografo guiCoregrafo;
	
	private JCheckBox chckbxDebug;
	private JButton btnEsquerda;
	private JButton btnDireita;
	private JButton btnRetaguarda;
	private JButton btnFrente;
	private JButton btnParar;
	
	private CanalComunicacoes canal;
	private static GUIDancarino frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIDancarino();
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
	public GUIDancarino() {
		bd = new BD();
		canal = new CanalComunicacoes("teste.txt");
		this.robot = new MyRobotLego(bd.getNomeRobot());
		
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
				myPrint("nome robot: " + bd.getNomeRobot());
			}
		});
		tfRoboNome.setText(bd.getNomeRobot());
		tfRoboNome.setBounds(153, 10, 114, 19);
		contentPane.add(tfRoboNome);
		tfRoboNome.setColumns(10);

		JRadioButton rdbtnOnOff = new JRadioButton("On/Off");
		rdbtnOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bd.setOnOff(rdbtnOnOff.isSelected());
			
				myPrint("On/Off: " + bd.isOnOff());
				updateButtons(false);
			}
		});
		rdbtnOnOff.setBounds(283, 8, 149, 23);
		contentPane.add(rdbtnOnOff);

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
				robot.parar();
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

		chckbxDebug = new JCheckBox("Debug");
		chckbxDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bd.setDebug(chckbxDebug.isSelected());
			}
		});
		chckbxDebug.setSelected(bd.isDebug());
		chckbxDebug.setBounds(12, 211, 129, 23);
		contentPane.add(chckbxDebug);

		taConsole = new JTextArea();
		taConsole.setEditable(false);
		taConsole.setBounds(12, 238, 420, 170);
		contentPane.add(taConsole);
		
		 chckbxAtivarCoreagrafo = new JCheckBox("Ativar Coreografo");
		chckbxAtivarCoreagrafo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(chckbxAtivarCoreagrafo.isSelected()) {
					guiCoregrafo = new GUICoreografo(canal);
					guiCoregrafo.setVisible(true);
					updateButtons(true);
				}
			}
		});
		chckbxAtivarCoreagrafo.setBounds(293, 195, 149, 23);
		contentPane.add(chckbxAtivarCoreagrafo);
		
		updateButtons(true);
	}

	public void myPrint(String msg) {
		if (bd.isDebug()) {
			taConsole.setText(taConsole.getText() + "\n" + msg);
		}

	}

	public void updateButtons(boolean firstStart) {
		btnDireita.setEnabled(bd.isOnOff());
		btnEsquerda.setEnabled(bd.isOnOff());
		btnFrente.setEnabled(bd.isOnOff());
		btnParar.setEnabled(bd.isOnOff());
		btnRetaguarda.setEnabled(bd.isOnOff());
		if(!firstStart) {
			robotConnection();
		}
	}
	
	public void robotConnection() {
		if(bd.isOnOff()) {
			System.out.println("Nome do Robot: " + bd.getNomeRobot());
			robot.startRobot();
		} else {
			robot.closeRobot();
		}
	}
	
	public void convertMsgToCommand(Mensagem msg){
		if(msg != null) {
			int ordem = msg.getOrdem();
			switch(ordem) {
			case 0:
				robot.parar();
				myPrint("Parei!");
				break;
			case 1:
				robot.reta(bd.getDistancia());
				myPrint("Reta com distancia: " + bd.getDistancia());

				break;
			case 2:
				robot.reta(-bd.getDistancia());
				myPrint("Retaguarda com distancia: " + bd.getDistancia());
				break;
			case 3:
				robot.curvarEsquerda(bd.getRaio(), bd.getAngulo());
				myPrint("Curvar Esquerda com raio de " + bd.getRaio() + " e com angulo " + bd.getAngulo());
				break;
			case 4:
				robot.curvarDireita(bd.getRaio(), bd.getAngulo());
				myPrint("Curvar Direita com raio de " + bd.getRaio() + " e com angulo " + bd.getAngulo());
				break;
			}
		} else keepCheckingMensagem();
	}
	
	public void keepCheckingMensagem() {
		Mensagem msg = canal.get();
		if(msg != null) {
			convertMsgToCommand(msg);
			keepCheckingMensagem();
		}
	}

	public MyRobotLego getRobot() {
		return robot;
	}

	public BD getBd() {
		return bd;
	}

	public void setBd(BD bd) {
		this.bd = bd;
	}
	
}
