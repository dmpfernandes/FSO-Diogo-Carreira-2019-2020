package TrabalhoPratico2.canalComunicacao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

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
	private Map<String, Map<String, Object>> pedidos;
	private List<String> order;

	private Semaphore elementosLivres, elementosOcupados, acessoCanal, acessoResource;

	public CanalComunicacoes(String nomeDoFicheiro) {
		file = new File(nomeDoFicheiro);
		pedidos = new HashMap<String, Map<String, Object>>();
		order = new LinkedList<String>();
		try {
			memoryMappedFile = new RandomAccessFile(file, "rw");
			map = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		elementosLivres = new Semaphore(MAX_BUFFER / 8);
		elementosOcupados = new Semaphore(0);
		acessoCanal = new Semaphore(0);
		acessoResource = new Semaphore(1);
	}

	public void escreverMsg(Mensagem msg) {
		try {
			acessoResource.acquire();
		} catch (InterruptedException e1) {
	
		}
		id = Thread.currentThread().getName();
		order.add(id);
		acessoCanal.release();
		accao = "escrever";
		System.out.println("id : "+id+", CANAL : Numero: " + msg.getNumero() + ", Ordem: " + msg.getOrdem());
		try {
			((Semaphore) pedidos.get(Thread.currentThread().getName()).get("semaphore")).acquire();
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
		releaseDancarinos();
		acessoResource.release();
	}

	private void releaseDancarinos() {
		for (Map.Entry e : pedidos.entrySet()) {
			HashMap<String, Object> hashMap = (HashMap<String, Object>)e.getValue();
			((Semaphore)hashMap.get("semaphoreCanRead")).release();
		}
	}

	public Mensagem lerMsg() {

		id = Thread.currentThread().getName();
		order.add(id);
		acessoCanal.release();
		accao = "ler";
		try {
			((Semaphore) pedidos.get(Thread.currentThread().getName()).get("semaphore")).acquire();
			
			((Semaphore) pedidos.get(Thread.currentThread().getName()).get("semaphoreCanRead")).acquire();
		} catch (InterruptedException e) {
		}
		System.out.println("id : " + Thread.currentThread().getName()+", map position : " + pedidos.get(Thread.currentThread().getName()).get("position"));
		if ((int) pedidos.get(Thread.currentThread().getName()).get("position") >= MAX_BUFFER) {
			pedidos.get(Thread.currentThread().getName()).put("position", 0);
		}

		map.position((int) pedidos.get(Thread.currentThread().getName()).get("position"));

		int numero = map.getInt();
		int ordem = map.getInt();
		
		int pos = map.position();
		pedidos.get(Thread.currentThread().getName()).put("position", pos);
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
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("type", Thread.currentThread().getName());

		args.put("position", posGet);
		args.put("semaphore", new Semaphore(0));
		args.put("semaphoreCanRead", new Semaphore(0));

		pedidos.put(Thread.currentThread().getName(), args);
		System.out.println("type : " + Thread.currentThread().getName());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String idx = "";
		while (true) {
			switch (estado) {
			case "dormir":
				try {
					
				
						acessoCanal.acquire();
						idx = order.get(0);
						System.out.println(idx);
						order.remove(0);
						estado = "validar";
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				break;
			case "ler":
				((Semaphore) pedidos.get(idx).get("semaphore")).release();

				estado = "dormir";
				break;
			case "escrever":
				((Semaphore) pedidos.get(idx).get("semaphore")).release();

				estado = "dormir";
				break;
			case "validar":
				if (pedidos.get(idx) != null && !pedidos.get(idx).isEmpty()) {
					estado = accao;
				} else {
					estado = "dormir";
				}
				break;
			}
		}
	}
}
