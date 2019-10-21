package TrabalhoPratico1.canalComunicacao;

import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;

import TrabalhoPratico1.BD;
import TrabalhoPratico1.GUIDancarino;
import TrabalhoPratico1.MyRobotLego;

public class Vigilante {

	private CanalComunicacoes cc;
	private GUIDancarino gui;
	private int disponiveis = 0;
	private int posPut = 0;
	final static int MAX_BUFFER = 256;
	private MappedByteBuffer map;
	private MyRobotLego robot;
	private BD bd;

	public Vigilante(CanalComunicacoes cc, GUIDancarino gui) {
		this.cc = cc;
		this.gui = gui;
		this.map = cc.getMap();
		this.robot = gui.getRobot();
		this.bd = gui.getBd();
	}

	public void keepCheckingMensagem() {
		Mensagem msg = get();
		if(msg != null) {
			convertMsgToCommand(msg);
		} else keepCheckingMensagem();
	}
	
	public Mensagem get() {
		map = cc.getMap();
		if (disponiveis <= 0) {
			return null;
		}
		int pos = posPut - disponiveis;
		if (pos < 0) {
			pos += MAX_BUFFER;
		}

		map.position(pos);
		IntBuffer mapBuffer = map.asIntBuffer();
		int numero = mapBuffer.get();
		int ordem = mapBuffer.get();
		System.out.println(numero + " " +  ordem);
		disponiveis -= 8;
		cc.setMap(map);
		return new Mensagem(numero, ordem);
	}
	
	public void convertMsgToCommand(Mensagem msg){
		bd = gui.getBd();
		if(msg != null) {
			int ordem = msg.getOrdem();
			switch(ordem) {
			case 0:
				robot.parar();
				gui.myPrint("Parei!");
				break;
			case 1:
				robot.reta(bd.getDistancia());
				gui.myPrint("Reta!");

				break;
			case 2:
				robot.reta(-bd.getDistancia());
				gui.myPrint("Retaguarda!");
				break;
			case 3:
				robot.curvarEsquerda(bd.getRaio(), bd.getAngulo());
				gui.myPrint("Curvar Esquerda!");
				break;
			case 4:
				robot.curvarDireita(bd.getRaio(), bd.getAngulo());
				gui.myPrint("Curvar Direita!");
				break;
			}
		}
	}
}
