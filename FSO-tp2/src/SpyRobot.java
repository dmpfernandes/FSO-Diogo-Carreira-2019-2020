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
import javax.swing.JRadioButton;
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
	private JRadioButton btnGravarTrajetoria;
	private JRadioButton btnReproduzir;
	private JTextField txtFldNomeFicheiro;
	private boolean btnGravar, btnEscrever, recording = false;
	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	private boolean onoff = true;
	private String estado = "dormir";
	private Semaphore canRead;
	private Dancarino dancarino;
	
	private File file;
	private RandomAccessFile memoryMappedFile;
	private MappedByteBuffer map;
	protected String nomeFicheiro = "";
	private JTextArea textArea;
	private List<String> ultimosComandos;


	public SpyRobot(Dancarino dancarino) {
		canRead = new Semaphore(0);
		ultimosComandos = new ArrayList<String>();
		this.dancarino = dancarino;
		
		//cria o ficheiro e subsequente buffer para o qual vai escrever
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGravarTrajetoria = new JRadioButton("Gravar trajetoria");
		btnGravarTrajetoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nomeFicheiro != "") {
					btnGravar = !btnGravar;
					if(btnGravar) {
						recording = true;
						estado = "ler";
					}
					else {
						recording = false;
						estado = "dormir";
					}
				}
			}
		});
		btnGravarTrajetoria.setBounds(24, 111, 172, 48);
		contentPane.add(btnGravarTrajetoria);
		
		btnReproduzir = new JRadioButton("Reproduzir trajetoria");
		btnReproduzir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nomeFicheiro != "") {
					btnEscrever = !btnEscrever;
					if(btnEscrever) {
						dancarino.setEstado("atuarSobreSpy");
						estado = "escrever";
					}else {	
						estado = "dormir";
					}
				}
			}
		});
		btnReproduzir.setBounds(24, 182, 172, 48);
		contentPane.add(btnReproduzir);
		
		txtFldNomeFicheiro = new JTextField();
		txtFldNomeFicheiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeFicheiro = txtFldNomeFicheiro.getText()!=""?txtFldNomeFicheiro.getText():"";
				myPrint(nomeFicheiro);
				estado = "validar";
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
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				break;
			case "ler":
				gravarMensagem();
				break;
			case "escrever":
				dancarino.startCommands();
				reproduzirTrajetoria();
				break;
			case "kill":
				
				break;
			case "validar":
				if(nomeFicheiro != "") {
					try {
						file = new File(nomeFicheiro);
						this.file.createNewFile();
						memoryMappedFile = new RandomAccessFile(file, "rw");
						map = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
					} catch (Exception exc) {
						exc.printStackTrace();
					}
					estado = "dormir";
				}
				break;
			}
		}
		
	}

	public void reproduzirTrajetoria() {
		while(map.hasRemaining()){
			System.out.println("entrou no reproduzir");

			String lastCommand = "";
			char c;
			map.position(0);
			while ((c = map.getChar()) != '\0') {
				lastCommand += c;
			}
			System.out.println("Command: " + lastCommand);
			dancarino.playCommandFromSpy(lastCommand);
		}
		dancarino.setEstado("dormir");
	}

	

	public void gravarMensagem() {
		try {
			System.out.println("entrou no gravar");
			if(recording) {
				canRead.acquire();
			} else {
				
				estado = "dormir";
				return;
			}
			
			
			myPrint("gravar : "+dancarino.getLastCommand());
			String lastCommand = dancarino.getLastCommand()+'\0';
			
			for (char c :  lastCommand.toCharArray()) {
				map.putChar(c);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		
		
	}
	
	private void myPrint(String msg) {
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
