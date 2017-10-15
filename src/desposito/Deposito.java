package desposito;

public class Deposito {
	
	private int superficie;
	private int profundidad;
	
	
	public Deposito(int superficie, int profundidad) {
		this.superficie = superficie;
		this.profundidad = profundidad;
	}

	public int getSuperficie() {
		return superficie;
	}
	
	public int getProfundidad() {
		return profundidad;
	}
	
	public int getVolumen() {
		return this.profundidad * this.superficie;
	}

}
