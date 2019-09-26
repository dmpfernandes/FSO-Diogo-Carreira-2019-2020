
public class MyRobotLego {
	private RobotLegoEV3 robot;
	
	public MyRobotLego() {
		robot = new RobotLegoEV3();
	}
	
	public void startRobot(String nomeRobot) {
		robot.OpenEV3(nomeRobot);
	}
	
	public void stopRobot() {
		robot.CloseEV3();
	}
	
	public void reta(int distancia) {
		robot.Reta(distancia);
		robot.Parar(false);
	}
	
}
