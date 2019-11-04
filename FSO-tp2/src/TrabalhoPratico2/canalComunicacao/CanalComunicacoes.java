package TrabalhoPratico2.canalComunicacao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Semaphore;

public class CanalComunicacoes {

	private RandomAccessFile memoryMappedFile;
	private static MappedByteBuffer map;
	private static File file;
	final static int MAX_BUFFER = 256;

	private static int posPut = 0;
	private int posGet = 0;
	
	private Semaphore semaphore;

	public CanalComunicacoes(String nomeDoFicheiro) {
		file = new File(nomeDoFicheiro);
		semaphore = new Semaphore(1,true);
		try {
			this.memoryMappedFile = new RandomAccessFile(file, "rw");
			CanalComunicacoes.map = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		file.deleteOnExit();
	}

	public void put(Mensagem msg) {
		try {
			semaphore.acquire();
			if (posPut >= MAX_BUFFER) {
				posPut = 0;
			}
			map.position(posPut);
			map.putInt(msg.getNumero());
			map.putInt(msg.getOrdem());
			posPut += 8;
			semaphore.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Recurso em uso!");
		}
	
	}

	public Mensagem get() {
		map.position(posGet);

		int numero = map.getInt();
		int ordem = map.getInt();
		if (numero != 0) {

			System.out.println(numero + " " + ordem);

			posGet += 8;

			if (posGet >= MAX_BUFFER) {
				posGet = 0;
			}

			return new Mensagem(numero, ordem);
		}
		return new Mensagem(0, 0);

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
