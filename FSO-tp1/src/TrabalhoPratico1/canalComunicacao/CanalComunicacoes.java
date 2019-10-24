package TrabalhoPratico1.canalComunicacao;

import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CanalComunicacoes{

	private RandomAccessFile memoryMappedFile;
	private MappedByteBuffer map;
	final static int MAX_BUFFER = 256;

	private int bytesUtilizados = 0;
	private static int posPut = 0;

	public CanalComunicacoes(String nomeDoFicheiro) {
		try {
			this.memoryMappedFile = new RandomAccessFile(nomeDoFicheiro, "rw");
			this.map = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean put(Mensagem msg) {

		if (bytesUtilizados < MAX_BUFFER) {
			if (posPut >= MAX_BUFFER) {
				posPut = 0;
			}
			map.position(posPut);
			map.putInt(msg.getNumero());
			map.putInt(msg.getOrdem());
			posPut += 8;
			bytesUtilizados += 8;
			return true;
		}
		return false;

	}

	public Mensagem get() {
		if (bytesUtilizados <= 0) {
			return new Mensagem(-1, -1);
		}
		int pos = posPut - bytesUtilizados;
		if (pos < 0) {
			pos += MAX_BUFFER;
		}
		
		map.position(pos);
		IntBuffer mapBuffer = map.asIntBuffer();
		int numero = mapBuffer.get();
		int ordem = mapBuffer.get();
		System.out.println(numero + " " +  ordem);
		bytesUtilizados -= 8;
		return new Mensagem(numero, ordem);
	}
}
