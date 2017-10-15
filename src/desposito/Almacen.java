package desposito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Almacen {

	private ArrayList<Deposito> depositos = new ArrayList<Deposito>();
	private int volumenPetroleo = 0;
	private int cantDepositos = 0;
	
	private int cantDepositosAltos;

	private int cantDepositosUsados = 0;
	private int profundidadOcupada = 0;
	private int volumenTotalDepositos = 0;
	private boolean rebalsa = false;

	public Almacen(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		int superficie = 0;
		int profundidad = 0;

		this.cantDepositos = scan.nextInt();
		for (int i = 0; i < this.cantDepositos; i++) {
			superficie = scan.nextInt();
			profundidad = scan.nextInt();
			this.depositos.add(new Deposito(superficie, profundidad));

			this.volumenTotalDepositos += this.depositos.get(i).getVolumen();
		}
		this.volumenPetroleo = scan.nextInt();
		if (this.volumenPetroleo > this.volumenTotalDepositos) {
			rebalsa = true;
		}

		scan.close();
	}

	public void resolver() throws IOException {
		if (!rebalsa) {
			llenarAlmacen();
		}
		escribirSolucion();
	}

	private void llenarAlmacen() {
		int profHastaSigDeposito = 0;
		int volumenHastaAhora = 0;
		int volumenIndividual = 0;
		
		for (int i = 0; i < this.cantDepositos; i++) {
			this.cantDepositosUsados++; // Voy a usar, por lo menos, un depósito.
			
			if ((i+1) < this.cantDepositos)
				profHastaSigDeposito = this.depositos.get(i).getProfundidad() - this.depositos.get(i+1).getProfundidad(); // Profundidad (o altura) hasta la cual voy a llenar los depósitos.
			else
				profHastaSigDeposito = this.depositos.get(i).getProfundidad(); // Si el depósito es el último, la profundidad es la de este mismo.
			
			volumenIndividual += this.depositos.get(i).getSuperficie(); // Voy acumulando el volumen que ocupa un nivel de profundidad, entre todos los depositos que estoy llenando.
			
			for (int p = 1 ; p <= profHastaSigDeposito ; p ++) { // Voy llenando el deposito "de a una profundidad" por vez.
				volumenHastaAhora += volumenIndividual;
				if (volumenHastaAhora >= this.volumenPetroleo) {
					this.profundidadOcupada = this.depositos.get(i).getProfundidad() - p;
					return;
				}
			}
		}
	}

	private void escribirSolucion() throws IOException {
		FileWriter file = new FileWriter("depositos.out");
		BufferedWriter buffer = new BufferedWriter(file);
		
		if(this.rebalsa) {
			buffer.write("Rebalsa: " + (this.volumenPetroleo - this.volumenTotalDepositos));
		} else {
			buffer.write(String.valueOf(this.cantDepositosUsados));
			buffer.newLine();
			buffer.write(String.valueOf(this.profundidadOcupada));
		}
		
		buffer.close();
	}
}
