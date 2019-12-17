import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico2.canalComunicacao.Mensagem;

import javax.swing.JTextField;

public class SpyRobot extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	private static final long MAX_BUFFER = 2000000;
	private JPanel contentPane;
	private JButton btnGravarTrajetoria;
	private JButton btnReproduzir;
	private JTextField txtFldNomeFicheiro;
	private boolean btnGravar, btnEscrever,onoff, recording = false;
	private String estado = "dormir";
	private Semaphore atividade, canRead;
	private Dancarino dancarino;
	
	private File file;
	private RandomAccessFile memoryMappedFile;
	private MappedByteBuffer map;
	protected String nomeFicheiro = "Default_Spy_Name";
	private JTextArea textArea;
	private List<String> ultimosComandos;


	public SpyRobot(Dancarino dancarino) {
		atividade = new Semaphore(0);
		canRead = new Semaphore(0);
		ultimosComandos = new ArrayList<String>();
		this.dancarino = dancarino;
		
		//cria o ficheiro e subsequente buffer para o qual vai escrever
		try {
			file = new File(nomeFicheiro);
			memoryMappedFile = new RandomAccessFile(file, "rw");
			map = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGravarTrajetoria = new JButton("Gravar trajetoria");
		btnGravarTrajetoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGravar = !btnGravar;
				if(btnGravar) {
					recording = true;
					estado = "ler";
					atividade.release();
				}
				else {
					recording = false;
					estado = "dormir";
				}
				
			}
		});
		btnGravarTrajetoria.setBounds(24, 111, 172, 48);
		contentPane.add(btnGravarTrajetoria);
		
		btnReproduzir = new JButton("Reproduzir trajetoria");
		btnReproduzir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEscrever = !btnEscrever;
				if(btnEscrever) {
					estado = "escrever";
					atividade.release();
				}else {
					estado = "dormir";
				}
				
			}
		});
		btnReproduzir.setBounds(24, 182, 172, 48);
		contentPane.add(btnReproduzir);
		
		txtFldNomeFicheiro = new JTextField();
		txtFldNomeFicheiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeFicheiro = txtFldNomeFicheiro.getText();
			}
		});
		txtFldNomeFicheiro.setBounds(75, 46, 121, 26);
		contentPane.add(txtFldNomeFicheiro);
		txtFldNomeFicheiro.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setBounds(26, 51, 61, 16);
		contentPane.add(lblNome);
		
		textArea = new JTextArea();
		textArea.setBounds(218, 51, 196, 179);
		contentPane.add(textArea);
		setVisible(true);
	}

	@Override
	public void run() {
		while(onoff) {
			switch(estado) {
			case "dormir":
				try {
					atividade.acquire();
				} catch (InterruptedException e) {}
				break;
			case "ler":
				gravarMensagem();
				break;
			case "escrever":
				reproduzirTrajetoria();
				break;
			case "kill":
				
				break;
			}
		}
		
	}

	private void reproduzirTrajetoria() {
		while(map.hasRemaining()){
			String lastCommand = "";
			while(map.getChar()!=',') {
				lastCommand += map.getChar();
			}
			processLastCommand(lastCommand);
		}
		
		
		
	}

	private void processLastCommand(String msg) {
		myPrint("escrever : "+msg);
		String[] args = msg.split("/");
		switch(args[0]) {
		case "PARAR_FALSE":
			dancarino.getRobot().parar(false);
			break;
		case "RETA":
			dancarino.getRobot().reta(Integer.valueOf(args[1].split("=")[1]));
			break;
		case "CDIR":
			dancarino.getRobot().curvarDireita(Integer.valueOf(args[1].split("=")[1]),Integer.valueOf(args[2].split("=")[1]));
			break;
		case "CESQ":
			dancarino.getRobot().curvarEsquerda(Integer.valueOf(args[1].split("=")[1]),Integer.valueOf(args[2].split("=")[1]));
			break;
		case "BACK":
			dancarino.getRobot().reta(-Integer.valueOf(args[1].split("=")[1]));
			break;
		case "PARAR_TRUE":
			dancarino.getRobot().parar(true);
			break;
		
		}
	}

	public void gravarMensagem() {
		try {
			canRead.acquire();
			myPrint("gravar : "+dancarino.getLastCommand());
			String lastCommand = dancarino.getLastCommand()+",";
			
			for (char c :  lastCommand.toCharArray()) {
				map.putChar(c);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		
		
	}
	
	public void myPrint(String msg) {
		ultimosComandos.add(msg);
		showComandosExecutados();
	}

	public void showComandosExecutados() {
		String textCommand = "";
		textArea.setText("");
		for (int j = 1; j < 11; j++) {
			int counter = ultimosComandos.size() - j;
			if (counter >= 0 && ultimosComandos.get(counter) != null && !ultimosComandos.get(counter).isEmpty()) {
				textCommand = ultimosComandos.get(counter) + "\n";
				textArea.setText(textCommand + textArea.getText());
			} else {
				break;
			}

		}
	}
	
	public void killApp() {
		estado = "kill";
		dispose();
		onoff = false;

	}


	public boolean isOnoff() {
		return onoff;
	}
	
	public boolean isRecording() {
		return recording;
	}

	public Semaphore getCanRead() {
		return canRead;
	}




}
