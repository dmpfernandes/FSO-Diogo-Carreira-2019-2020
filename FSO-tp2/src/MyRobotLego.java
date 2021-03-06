import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRobotLego {
private String nomeRobot;

	private RobotLegoEV3 robot;
	
	public MyRobotLego() {
		robot = new RobotLegoEV3();
		
	}
	
	public void startRobot() {
		robot.OpenEV3(nomeRobot);
		System.out.println("Nome do Robot: " + nomeRobot);
	}
	
	public void closeRobot() {
		robot.CloseEV3();
		System.out.println("Ligacao Terminada");
	}
	
	public void reta(int distancia) {
		robot.Reta(distancia);
		robot.Parar(false);
		System.out.println("Reta com distancia: " + distancia );
	}
	
	public void curvarEsquerda(int raio, int angulo) {
		robot.CurvarEsquerda(raio, angulo);
		robot.Parar(false);
		System.out.println("Curva a Esquerda com raio de " + raio + " cm e angulo de " + angulo + "º.");
	}
	
	public void curvarDireita(int raio, int angulo) {
		robot.CurvarDireita(raio, angulo);
		robot.Parar(false);
		System.out.println("Curva a Direita com raio de " + raio + " cm e angulo de " + angulo + "º.");
	}
	
	public void parar(boolean parar) {
		robot.Parar(parar);
		System.out.println("Robot parado.");
	}

	public void setNomeRobot(String text) {
		nomeRobot = text;
	}
	
}
