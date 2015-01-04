package logica.IA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logica.modelo.Barco;
import logica.modelo.Casilla;
import logica.modelo.ColocadorDeBarcos;
import logica.modelo.Tablero;

public class IAFacil implements IA {

	/*
	 * guarda una lista de las casillas del tablero del jugador humano, y va
	 * eliminando a medida que las devuelve para que disparen en ellas
	 */private List<Casilla> casillasSinDispararDelJugador = new ArrayList<Casilla>();

	public IAFacil() {
	}

	public IAFacil(Tablero tablero) {
		setTableroDelJugador(tablero);
	}

	public void setTableroDelJugador(Tablero tablero) {
		Casilla[][] tablero2 = tablero.getCasillas();
		for (Casilla[] casillas : tablero2) {
			for (Casilla casilla : casillas) {
				casillasSinDispararDelJugador.add(casilla);
			}
		}
	}

	@Override
	public Casilla proximaCasillaADisparar() {
		if (casillasSinDispararDelJugador.size() == 0)
			throw new IllegalStateException(
					"No hay mas casillas para disparar en el tablero del jugador.");
		Random rand = new Random();
		Casilla casilla = casillasSinDispararDelJugador.remove(rand
				.nextInt(casillasSinDispararDelJugador.size()));
		if (casilla.estaTocada())
			throw new IllegalStateException(
					"Hay al menos una casilla tocada en el set de casillas libres, error de consistencia.");
		return casilla;
	}

	@Override
	public void colocarBarcos(Tablero tablero) {
		List<Barco> barcosSinColocar = new ArrayList<Barco>();

		for (Barco barco : tablero.getBarcos()) {
			barcosSinColocar.add(barco);
		}
		while (barcosSinColocar.size() != 0) {
			if(colocarBarco(barcosSinColocar.get(0), tablero))
				barcosSinColocar.remove(0); //TODO testear
		}
	}

	/**
	 * Método para colocar los barcos de forma semialeatoria, la IA en
	 * dificultad fácil los colocará todos en vertical
	 * 
	 * @param barco
	 * @param colocadorDeBarcos
	 */
	private boolean colocarBarco(Barco barco, Tablero tablero) {
		Random rand = new Random();
		ColocadorDeBarcos colocadorDeBarcos = new ColocadorDeBarcos(tablero);
		Casilla primeraCasilla = obtenerCasillaAleatoria(tablero, rand);

		while (primeraCasilla.getFila() + barco.getTamaño() - 1 > tablero
				.getCasillas().length)
			// si el barco no entra en el tablero...
			primeraCasilla = obtenerCasillaAleatoria(tablero, rand);

		return colocadorDeBarcos.colocarBarco(
				barco.getId(),
				primeraCasilla,
				tablero.getCasillas()[primeraCasilla.getFila()
						+ barco.getTamaño() - 1][primeraCasilla.getColumna()]);
	}

	public Casilla obtenerCasillaAleatoria(Tablero tablero, Random rand) {
		return tablero.getCasillas()[rand.nextInt(tablero.getCasillas().length)][rand
				.nextInt(tablero.getCasillas()[0].length)];
	}

}
