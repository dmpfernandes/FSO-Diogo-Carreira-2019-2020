import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRobotLego {
private String nomeRobot;
private SpyRobot spy;

//	private RobotLegoEV3 robot;
	
	public MyRobotLego(SpyRobot spy) {
//		robot = new RobotLegoEV3();
		this.spy = spy;
		
	}
	
	public void startRobot() {
//		robot.OpenEV3(getNomeRobot());
		System.out.println("Nome do Robot: " + nomeRobot);
	}
	
	public void closeRobot() {
//		robot.CloseEV3();
		System.out.println("Ligacao Terminada");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("acao", ACCOES.CLOSE);
		sendToSpy(args);
	}
	
	public void reta(int distancia) {
//		robot.Reta(distancia);
//		robot.Parar(false);
		System.out.println("Reta com distancia: " + distancia );
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("acao", ACCOES.RETA);
		args.put("distancia", distancia);
		sendToSpy(args);
	}
	
	public void curvarEsquerda(int raio, int angulo) {
//		robot.CurvarEsquerda(raio, angulo);
//		robot.Parar(false);
		System.out.println("Curva a Esquerda com raio de " + raio + " cm e angulo de " + angulo + "º.");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("acao", ACCOES.CESQ);
		args.put("raio", raio);
		args.put("angulo", angulo);
		sendToSpy(args);
	}
	
	public void curvarDireita(int raio, int angulo) {
//		robot.CurvarDireita(raio, angulo);
//		robot.Parar(false);
		System.out.println("Curva a Direita com raio de " + raio + " cm e angulo de " + angulo + "º.");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("acao", ACCOES.CDIR);
		args.put("raio", raio);
		args.put("angulo", angulo);
		sendToSpy(args);
	}
	
	public void parar(boolean parar) {
//		robot.Parar(parar);
		System.out.println("Robot parado.");
		Map<String, Object> args = new HashMap<String, Object>();
		
		if(parar) {
			args.put("acao", ACCOES.PARAR_TRUE);
		} else {
			args.put("acao", ACCOES.PARAR_FALSE);
		}
		sendToSpy(args);
	}

	public void setNomeRobot(String text) {
		nomeRobot = text;
	}
	
	private void sendToSpy(Map<String, Object> args) {
		if(spy.isOnoff() && spy.isRecording()) {
			spy.gravarMensagem(args);
		}
	}
}
