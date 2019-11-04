package TrabalhoPratico2.canalComunicacao;

public class Mensagem {

	private int numero;
	private int ordem;

	public Mensagem(int numero, int ordem) {
		this.numero = numero;
		this.ordem = ordem;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	
	@Override
	public String toString() {
		return "Mensagem [numero=" + numero + ", ordem=" + ordem + "]";
	}

	
}
