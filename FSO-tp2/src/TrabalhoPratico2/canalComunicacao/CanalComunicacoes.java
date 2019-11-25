package TrabalhoPratico2.canalComunicacao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class CanalComunicacoes implements Runnable {

	private RandomAccessFile memoryMappedFile;
	private MappedByteBuffer map;
	private File file;
	final static int MAX_BUFFER = 256;

	private String accao = "";
	private String estado = "dormir";
	private String id;
	private int posPut = 0;
	private int posGet = 0;
	private Map<String,Map<String,Object>> pedidos;

	private Semaphore elementosLivres, elementosOcupados, acessoCanal;

	public CanalComunicacoes(String nomeDoFicheiro) {
		file = new File(nomeDoFicheiro);
		pedidos = new HashMap<String, Map<String,Object>>();
		try {
			memoryMappedFile = new RandomAccessFile(file, "rw");
			map = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		elementosLivres = new Semaphore(MAX_BUFFER / 8);
		elementosOcupados = new Semaphore(0);
		acessoCanal = new Semaphore(0);
	}

	public void escreverMsg(Mensagem msg) {
		acessoCanal.release();
		estado = "escrever";
		try {
			elementosLivres.acquire();
			if (posPut >= MAX_BUFFER) {
				posPut = 0;
			}
			map.position(posPut);
			map.putInt(msg.getNumero());
			map.putInt(msg.getOrdem());
			posPut += 8;
		} catch (InterruptedException e) {
		}
		elementosOcupados.release();
	}

	public Mensagem lerMsg() {
		id = String.valueOf(Thread.currentThread().getId());
		acessoCanal.release();
		estado = "validar";
		accao = "ler";
		
		try {
			((Semaphore) pedidos.get(id).get("semaphore")).acquire();
			elementosOcupados.acquire();
		} catch (InterruptedException e) {
		}
		
		map.position(Integer.valueOf((String) pedidos.get(id).get("position")));
		
		int numero = map.getInt();
		int ordem = map.getInt();

		System.out.println(numero + " " + ordem);
		pedidos.get(id).put("position", map.position());
		elementosLivres.release();
		return new Mensagem(numero, ordem);

	}

	public void fecharCanal() {

		try {
			this.memoryMappedFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void open() {
		String id = String.valueOf(Thread.currentThread().getId());
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("type", Thread.currentThread().getName());
		args.put("position", posGet);
		args.put("semaphore", new Semaphore(0));
		pedidos.put(id, args);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch (estado) {
		case "dormir":
			try {
				acessoCanal.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "ler":
			((Semaphore) pedidos.get(id).get("semaphore")).release();
			
			estado = "dormir";
			break;
		case "escrever":
			((Semaphore) pedidos.get(id).get("semaphore")).release();
			
			estado = "dormir";
			break;
		case "validar":
			if(pedidos.get(id) != null && !pedidos.get(id).isEmpty()) {
				estado = accao;
			}else {
				estado = "dormir";
			}
			break;
		}
	}
}
