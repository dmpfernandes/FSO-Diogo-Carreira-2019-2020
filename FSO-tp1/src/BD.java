

public class BD {

	private String nomeRobot, consola;
	private boolean onOff, debug;
	private int raio, angulo, distancia;
	private boolean parar;
	
	
	public BD() {
		this.nomeRobot = new String("");
		this.consola = new String("");
		this.onOff = false;
		this.debug = true;
		this.raio = 0;
		this.angulo = 90;
		this.distancia = 20;
	}

	public String getNomeRobot() {
		return nomeRobot;
	}

	public void setNomeRobot(String nomeRobot) {
		this.nomeRobot = nomeRobot;
	}

	public String getConsola() {
		return consola;
	}

	public void setConsola(String consola) {
		this.consola = consola;
	}

	public boolean isOnOff() {
		return onOff;
	}

	public void setOnOff(boolean onOff) {
		this.onOff = onOff;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
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
