package logica.IA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logica.modelo.Casilla;

public class IAFacil implements IA {

	/* guarda una lista de las casillas del tablero del jugador humano, y va
	 * eliminando a medida que las devuelve para que disparen en ellas
	 */private List<Casilla> casillasSinDispararDelJugador;

	 public IAFacil(Casilla[][] tablero)
	 {
		 casillasSinDispararDelJugador = new ArrayList<Casilla>();
		 for (Casilla[] casillas : tablero) {
			for (Casilla casilla : casillas) {
				casillasSinDispararDelJugador.add(casilla);
			}
		}
	 }
	 
	@Override
	public Casilla proximaCasillaADisparar() {
		if(casillasSinDispararDelJugador.size() == 0)
			throw new IllegalStateException("No hay mas casillas para disparar en el tablero del jugador.");
		Random rand = new Random();
		return casillasSinDispararDelJugador.remove(rand
				.nextInt(casillasSinDispararDelJugador.size()));
	}

}
