package TrabalhoPratico1.canalComunicacao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JTextArea;

import TrabalhoPratico1.BD;

public class CanalComunicacoes extends Thread{

	private File file;
	private FileChannel filechannel;
	private static MappedByteBuffer map;
	final static int MAX_BUFFER = 256;
	private static int posGet = 0;
	
	private int disponiveis = 0;
	private BD bd;
	private int numero;
	private static int posPut = 0;
	
	private List<String> ultimosComandos;
	private JTextArea txtFieldComandos;

	public CanalComunicacoes(String nomeDoFicheiro, BD bd, JTextArea txtFieldComandos) {
		try {
			this.file = new File(nomeDoFicheiro);
			this.file.createNewFile();
			this.bd = bd;
			this.txtFieldComandos = txtFieldComandos;
			filechannel = new RandomAccessFile(this.file, "rw").getChannel();
			map = filechannel.map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CanalComunicacoes(String nomeDoFicheiro, BD bd) {
		try {
			this.file = new File(nomeDoFicheiro);
			this.file.createNewFile();
			this.bd = bd;
			filechannel = new RandomAccessFile(this.file, "rw").getChannel();
			map = filechannel.map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Mensagem get() {
		if(disponiveis == 0) {
			return null;
		}
		int pos = posPut - disponiveis;
		if(pos < 0) {
			pos += MAX_BUFFER;
		}
		
		map.position(pos);
		IntBuffer mapBuffer = map.asIntBuffer();
		int numero = mapBuffer.get();
		int ordem = mapBuffer.get();
		
		disponiveis -= 8;
		
		return new Mensagem(numero,ordem);
		

	}
	
	public boolean put(Mensagem msg) {
		
		if(disponiveis < MAX_BUFFER){
			if(posPut >= MAX_BUFFER) {
				posPut=0;
			}
			map.position(posPut);
			ByteBuffer bb = ByteBuffer.allocate(8).putInt(msg.getNumero()).putInt(msg.getOrdem());
			map.put(bb.duplicate().array());
			posPut += 8;
			disponiveis += 8;
			try {
				
				filechannel.write((ByteBuffer)bb.flip());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			return true;
		}
		return false;
		
		
	}

	public void generateCommands(int i) {
		if (i == -1) {
			while (!bd.getParar()) {
				put(generateRandomCommand());
			}
		} else {
			for (int j = 0; j < i; j++) {
				Mensagem msg = generateRandomCommand();
				put(msg);
		
				ultimosComandos.add("Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
				
				
				
			}
			String textCommand= "";
			int numeroComandosAMostrar = i==1 ? 1 : 10;
			for (int j = ultimosComandos.size()-1; j >= ultimosComandos.size()-numeroComandosAMostrar ; j--) {
				textCommand = ultimosComandos.get(j) + "\n";
				
			}
			txtFieldComandos.setText(textCommand);

			
		}
	}
	
	private Mensagem generateRandomCommand() {
		numero++;
		Random r = new Random();
		int ordem = r.nextInt(4);
		return new Mensagem(numero, ordem);
	}
}
