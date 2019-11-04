import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico2.canalComunicacao.Mensagem;

public class Dancarino implements Runnable{
	
	private CanalComunicacoes canal;
	private BD bd;
	private MyRobotLego robot;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void convertMsgToCommand(Mensagem msg) {

		int ordem = msg.getOrdem();
		switch (ordem) {
		case 0:
			break;
		case 1:
			robot.reta(bd.getDistancia());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			keepCheckingMensagem();
			break;
		case 2:
			robot.reta(-bd.getDistancia());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			keepCheckingMensagem();
			break;
		case 3:
			robot.curvarEsquerda(bd.getRaio(), bd.getAngulo());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			keepCheckingMensagem();
			break;
		case 4:
			robot.curvarDireita(bd.getRaio(), bd.getAngulo());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			keepCheckingMensagem();
			break;
		case 5:
			robot.parar();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			keepCheckingMensagem();
			break;
		}

	}

	public void keepCheckingMensagem() {
		Mensagem msg = canal.get();
		System.out.println(msg.toString());
		convertMsgToCommand(msg);
	}

	
}
