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
	private int posGet = 0;

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
		int pos = map.position();
		map.position(posGet);
		IntBuffer mapBuffer = map.asIntBuffer();
		int numero = mapBuffer.get();
		int ordem = mapBuffer.get();
		posGet+=8;
		map.position(pos);
		return "numero: "+numero+"  ordem: "+ordem;
	}
	
	public void put(Mensagem msg) {
		
		ByteBuffer bb = ByteBuffer.allocate(8).putInt(msg.getNumero()).putInt(msg.getOrdem());
		map.put(bb.array());
		
	}
	
	

}
