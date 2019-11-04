

public class BD {

	private String nomeRobot;
	private int raio, angulo, distancia;
	private boolean parar;
	
	
	public BD() {
		this.nomeRobot = new String("");
		this.raio = 0;
		this.angulo = 45;
		this.distancia = 10;
	}

	public String getNomeRobot() {
		return nomeRobot;
	}

	public void setNomeRobot(String nomeRobot) {
		this.nomeRobot = nomeRobot;
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public boolean getParar() {
		return this.parar;
	}
	

}
