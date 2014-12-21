package logica.IA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logica.modelo.Casilla;
import logica.modelo.Tablero;

public class IAFacil implements IA {

	/* guarda una lista de las casillas del tablero del jugador humano, y va
	 * eliminando a medida que las devuelve para que disparen en ellas
	 */private List<Casilla> casillasSinDispararDelJugador = new ArrayList<Casilla>();
	 
	 public IAFacil(){}

	 public IAFacil(Tablero tablero)
	 {
		 setCasillasSinDispararDelJugador(tablero);
	 }
	 

	public void setCasillasSinDispararDelJugador(Tablero tablero) {
		Casilla[][] tablero2 = tablero.getCasillas();
		 for (Casilla[] casillas : tablero2) {
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
		Casilla casilla = casillasSinDispararDelJugador.remove(rand
				.nextInt(casillasSinDispararDelJugador.size()));
		if(casilla.estaTocada())
			throw new IllegalStateException("Hay al menos una casilla tocada en el set de casillas libres, error de consistencia.");
		return casilla;
	}

}
