package TrabalhoPratico1.canalComunicacao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class CanalComunicacoes {

	private File file;
	private FileChannel filechannel;
	private static MappedByteBuffer map;
	final static int MAX_BUFFER = 256;
	private static int posGet = 0;
	
	private int disponiveis = 0;
	private static int posPut = 0;

	public CanalComunicacoes(String nomeDoFicheiro) {
		try {
			this.file = new File(nomeDoFicheiro);
			this.file.createNewFile();
			filechannel = new RandomAccessFile(this.file, "rw").getChannel();
			map = filechannel.map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String get() {
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
		
		return "numero: "+numero+"  ordem: "+ordem;
		

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

}