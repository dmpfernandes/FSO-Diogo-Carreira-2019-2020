package TrabalhoPratico2.canalComunicacao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CanalComunicacoes{

	private RandomAccessFile memoryMappedFile;
	private static MappedByteBuffer map;
	private static File file;
	final static int MAX_BUFFER = 256;

	private int posPut = 0;
	private int pos = 0;
	private int matchnumero = -1;

	public CanalComunicacoes(String nomeDoFicheiro) {
		file = new File(nomeDoFicheiro);
		try {
			this.memoryMappedFile = new RandomAccessFile(file, "rw");
			this.map = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		file.deleteOnExit();
	}


	public void put(Mensagem msg) {

		
			if (posPut >= MAX_BUFFER) {
				posPut = 0;
			}
			map.position(posPut);
			map.putInt(msg.getNumero());
			map.putInt(msg.getOrdem());
			posPut += 8;
	}

	public Mensagem get() {
		
		
		
//		if (pos < 0) {
//			pos += MAX_BUFFER;
//		}
		
		map.position(pos);
		
		
		IntBuffer mapBuffer = map.asIntBuffer();
		int numero = mapBuffer.get();
		int ordem = mapBuffer.get();
		if (matchnumero < numero) {
			matchnumero = numero;

			System.out.println(numero + " " +  ordem);
			if(ordem == 0) {
				
				return new Mensagem(0, 0);
			}else {
				pos += 8;
			}
			if(pos >= MAX_BUFFER) {
				map.clear();
				pos=0;
			}
			
			return new Mensagem(numero, ordem);
		}
		return new Mensagem(0,0);
		
	}
	
	
	public void fecharCanal() {
		
		try {
			this.memoryMappedFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
