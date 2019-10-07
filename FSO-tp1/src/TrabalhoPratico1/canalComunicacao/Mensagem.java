package TrabalhoPratico1.canalComunicacao;

public class Mensagem {
	
	int numero;
	int ordem;
	
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

	
}
