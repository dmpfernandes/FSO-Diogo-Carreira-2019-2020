import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico2.canalComunicacao.Mensagem;

public class Coreografo implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CanalComunicacoes cc;
	private boolean parar;
	private int numero;
	private BD bd;
	private List<String> ultimosComandos;

	public void initializeVariables() {
		parar = false;
		numero = -1;
		bd = new BD();
		ultimosComandos = new ArrayList<String>();
		cc = new CanalComunicacoes("teste.txt");
	}

	public void run() {
	}

	public Coreografo() {
		initializeVariables();
	}

	private void stopCommands() {
		parar = true;
	}

	public void showComandosExecutados(int nComandos) {
		String textCommand = "";

		for (int j = ultimosComandos.size() - 1; j > ultimosComandos.size() - 11; j--) {
			textCommand = ultimosComandos.get(j) + "\n";
		}
	}

	public List<String> generateCommands() {
		if (!parar) {
			Mensagem msg = generateRandomCommand();
			cc.escreverMsg(msg);
			System.out.println("Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
			ultimosComandos.add("Numero: " + msg.getNumero() + " Ordem: " + msg.getOrdem());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			generateCommands();
		}
		return ultimosComandos;
	}

	private Mensagem generateRandomCommand() {
		numero++;
		Random r = new Random();
		int ordem = r.nextInt(4);
		if (ordem == 0) {
			numero--;
			return generateRandomCommand();
		} else {
			return new Mensagem(numero, ordem);
		}

	}

	public boolean isParar() {
		return parar;
	}
}
