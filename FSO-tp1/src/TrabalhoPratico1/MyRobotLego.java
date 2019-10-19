package TrabalhoPratico1;
import RobotLegoEV3;

public class MyRobotLego {
	private RobotLegoEV3 robot;
	
	public MyRobotLego() {
		robot = new RobotLegoEV3();
	}
	
	public void startRobot(String nomeRobot) {
		robot.OpenEV3(nomeRobot);
	}
	
	public void closeRobot() {
		robot.CloseEV3();
	}
	
	public void reta(int distancia) {
		robot.Reta(distancia);
		robot.Parar(false);
	}
	
	public void curvarEsquerda(int raio, int angulo) {
		robot.CurvarEsquerda(raio, angulo);
		robot.Parar(false);
	}
	
	public void curvarDireita(int raio, int angulo) {
		robot.CurvarDireita(raio, angulo);
		robot.Parar(false);
	}
	
	public void parar() {
		robot.Parar(true);
	}
	
	
	
}
