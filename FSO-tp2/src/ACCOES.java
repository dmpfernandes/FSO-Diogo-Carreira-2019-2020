
public enum ACCOES {
	PARAR_FALSE(0), RETA(1), CDIR(2), CESQ(3), BACK(4), PARAR_TRUE(5), CLOSE(6), DO_NOTHING(-1);
	private int value;

	private ACCOES(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static ACCOES getAcaoWithValue(int value) {
		for(ACCOES acao : ACCOES.values()) {
			if(acao.value == value) {
				return acao;
			}
		}
		return null;
	}
	
}
