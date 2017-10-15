package desposito;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			Almacen almacen = new Almacen("depositos.in");
			almacen.resolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
