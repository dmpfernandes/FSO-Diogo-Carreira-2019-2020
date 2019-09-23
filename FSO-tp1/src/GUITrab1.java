import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Color;

public class GUITrab1 extends JFrame {

	private JPanel contentPane;
	private JTextField tfRoboNome;
	private JTextField tfRaio;
	private JTextField tfAngulo;
	private JTextField tfDistancia;
	private JTextArea taConsole;

	private BD bd;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITrab1 frame = new GUITrab1();
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
	public GUITrab1() {
		bd = new BD();
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
				myPrint("nome robot: "+ bd.getNomeRobot());
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
				myPrint("On/Off: "+ bd.isOnOff());
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
					// TODO Auto-generated catch block
					tfRaio.setText(String.valueOf(bd.getRaio()));
				}
				myPrint("raio: "+ bd.getRaio());
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
					// TODO Auto-generated catch block
					tfAngulo.setText(String.valueOf(bd.getAngulo()));
				}
				myPrint("angulo: "+ bd.getAngulo());
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
					// TODO Auto-generated catch block
					tfDistancia.setText(String.valueOf(bd.getDistancia()));
				}
				myPrint("distancia: "+ bd.getDistancia());
			}
		});
		tfDistancia.setText(String.valueOf(bd.getDistancia()));
		tfDistancia.setBounds(341, 39, 60, 19);
		contentPane.add(tfDistancia);
		tfDistancia.setColumns(10);
		
		JLabel lblDist = new JLabel("Distancia:");
		lblDist.setBounds(247, 41, 88, 15);
		contentPane.add(lblDist);
		
		JButton btnFrente = new JButton("Frente");
		btnFrente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPrint("reta: "+ bd.getDistancia());
			}
		});
		btnFrente.setBounds(163, 72, 117, 55);
		contentPane.add(btnFrente);
		
		JButton btnParar = new JButton("Parar");
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPrint("Parar Assincrono: true");
			}
		});
		btnParar.setBackground(Color.RED);
		btnParar.setForeground(Color.BLACK);
		btnParar.setBounds(163, 126, 117, 55);
		contentPane.add(btnParar);
		
		JButton btnRetaguarda = new JButton("Retaguarda");
		btnRetaguarda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPrint("reta: "+ -bd.getDistancia());
			}
		});
		btnRetaguarda.setBounds(163, 179, 117, 55);
		contentPane.add(btnRetaguarda);
		
		JButton btnDireita = new JButton("Direita");
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPrint("Curva Direita \n raio: "+ bd.getRaio() +" angulo: "+bd.getAngulo());
			}
		});
		btnDireita.setBounds(283, 126, 117, 55);
		contentPane.add(btnDireita);
		
		JButton btnEsquerda = new JButton("Esquerda");
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myPrint("Curva Esquerda \n raio: "+ bd.getRaio() +" angulo: "+bd.getAngulo());
			}
		});
		btnEsquerda.setBounds(46, 126, 117, 55);
		contentPane.add(btnEsquerda);
		
		JCheckBox chckbxDebug = new JCheckBox("Debug");
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
	}

	private void myPrint(String msg) {
		if(bd.isDebug()) {
			taConsole.setText(taConsole.getText()+"\n"+msg);
		}
		
	}
}
