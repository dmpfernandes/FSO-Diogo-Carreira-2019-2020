package TrabalhoPratico1.canalComunicacao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CanalComunicacoes{

	private File file;
	private FileChannel filechannel;
	private MappedByteBuffer map;
	final static int MAX_BUFFER = 256;

	private int disponiveis = 0;
	private static int posPut = 0;

	public CanalComunicacoes(String nomeDoFicheiro) {
		try {
			this.file = new File(nomeDoFicheiro);
			this.file.createNewFile();
			filechannel = new RandomAccessFile(this.file, "rw").getChannel();
			setMap(filechannel.map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean put(Mensagem msg) {

		if (disponiveis < MAX_BUFFER) {
			if (posPut >= MAX_BUFFER) {
				posPut = 0;
			}
			getMap().position(posPut);
			ByteBuffer bb = ByteBuffer.allocate(8).putInt(msg.getNumero()).putInt(msg.getOrdem());
			getMap().put(bb.duplicate().array());
			posPut += 8;
			disponiveis += 8;
			try {
//				filechannel.write((ByteBuffer) bb.flip());
				try (FileOutputStream fos = new FileOutputStream("teste.txt")) {
					   fos.write(bb.array());
					   //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;

	}

	public Mensagem get() {
		if (disponiveis <= 0) {
			return null;
		}
		int pos = posPut - disponiveis;
		if (pos < 0) {
			pos += MAX_BUFFER;
		}

		map.position(pos);
		IntBuffer mapBuffer = map.asIntBuffer();
		int numero = mapBuffer.get();
		int ordem = mapBuffer.get();
		System.out.println(numero + " " +  ordem);
		disponiveis -= 8;
		return new Mensagem(numero, ordem);
	}


	public MappedByteBuffer getMap() {
		return map;
	}

	public void setMap(MappedByteBuffer map) {
		this.map = map;
	}


}
