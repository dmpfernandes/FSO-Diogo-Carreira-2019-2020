import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;
import TrabalhoPratico2.canalComunicacao.Mensagem;

public class Dancarino implements Runnable{
	
	private CanalComunicacoes canal;
	private BD bd;
	private MyRobotLego robot;

	
	
	@Override
	public void run() {
		while(true) {
			Mensagem msg = canal.lerMsg();
			if(msg.getNumero() == 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println(msg.toString());
				convertMsgToCommand(msg);
			}
			
		}
			
		
	}

	public void convertMsgToCommand(Mensagem msg) {

		int ordem = msg.getOrdem();
		switch (ordem) {
		case 0:
			robot.parar(false);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			robot.reta(bd.getDistancia());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			robot.curvarDireita(bd.getRaio(), bd.getAngulo());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			robot.curvarEsquerda(bd.getRaio(), bd.getAngulo());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			robot.reta(-bd.getDistancia());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			robot.parar(true);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

	}


	
}
