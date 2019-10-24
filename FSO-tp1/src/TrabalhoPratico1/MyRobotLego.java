package TrabalhoPratico1;


public class MyRobotLego {
//	private RobotLegoEV3 robot;
	private String nomeRobot;
	
	public MyRobotLego(String nomeRobot) {
//		robot = new RobotLegoEV3();
		this.setNomeRobot(nomeRobot);
	}
	
	public void startRobot() {
//		robot.OpenEV3(nomeRobot);
//		System.out.println("Nome do Robot: " + nomeRobot);
	}
	
	public void closeRobot() {
//		robot.CloseEV3();
		System.out.println("Ligacao Terminada");
	}
	
	public void reta(int distancia) {
//		robot.Reta(distancia);
//		robot.Parar(false);
		System.out.println("Reta com distancia: " + distancia );
	}
	
	public void curvarEsquerda(int raio, int angulo) {
//		robot.CurvarEsquerda(raio, angulo);
//		robot.Parar(false);
		System.out.println("Curva a Esquerda com raio de " + raio + " cm e angulo de " + angulo + "º.");
	}
	
	public void curvarDireita(int raio, int angulo) {
//		robot.CurvarDireita(raio, angulo);
//		robot.Parar(false);
		System.out.println("Curva a Direita com raio de " + raio + " cm e angulo de " + angulo + "º.");
	}
	
	public void parar() {
//		robot.Parar(true);
		System.out.println("Robot parado.");
	}

	public String getNomeRobot() {
		return nomeRobot;
	}

	public void setNomeRobot(String nomeRobot) {
		this.nomeRobot = nomeRobot;
	}
}
