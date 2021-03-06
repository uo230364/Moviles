package logica.IA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logica.modelo.Barco;
import logica.modelo.Casilla;
import logica.modelo.ColocadorDeBarcos;
import logica.modelo.Tablero;

public class IAMedio implements IA {

	/*
	 * guarda una lista de las casillas del tablero del jugador humano, y va
	 * eliminando a medida que las devuelve para que disparen en ellas
	 */
	private List<Casilla> casillasSinDispararDelJugador = new ArrayList<Casilla>();
	private List<Casilla> casillasConBarco = new ArrayList<Casilla>();

	public IAMedio() {
	}

	public IAMedio(Tablero tablero) {
		setTableroDelJugador(tablero);
	}

	public void setTableroDelJugador(Tablero tablero) {
		Casilla[][] tablero2 = tablero.getCasillas();
		casillasSinDispararDelJugador.clear();
		for (Casilla[] casillas : tablero2) {
			for (Casilla casilla : casillas) {
				casillasSinDispararDelJugador.add(casilla);
				if(casilla.getBarco() != null)
					casillasConBarco.add(casilla);
			}
		}
	}

	@Override
	public Casilla proximaCasillaADisparar() {
		if (casillasSinDispararDelJugador.size() == 0)
			throw new IllegalStateException(
					"No hay mas casillas para disparar en el tablero del jugador.");
		Random rand = new Random();
		int randNum = rand.nextInt(2);
		Casilla casilla = null;
		if (randNum == 0) {
				casilla = casillasSinDispararDelJugador.get(rand.nextInt(casillasSinDispararDelJugador.size()));
				casillasSinDispararDelJugador.remove(casilla);
				if(casilla.getBarco() != null)
					casillasConBarco.remove(casilla);
		} else {
			casilla = casillasConBarco.remove(0);		
			casillasSinDispararDelJugador.remove(casilla);
		}
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
			if (colocarBarco(barcosSinColocar.get(0), tablero))
				barcosSinColocar.remove(0); 
		}
	}

	/**
	 * M�todo para colocar los barcos de forma semialeatoria, la IA en
	 * dificultad f�cil los colocar� todos en vertical
	 * 
	 * @param barco
	 * @param colocadorDeBarcos
	 */
	private boolean colocarBarco(Barco barco, Tablero tablero) {
		Random rand = new Random();
		ColocadorDeBarcos colocadorDeBarcos = new ColocadorDeBarcos(tablero);
		Casilla primeraCasilla = obtenerCasillaAleatoria(tablero, rand);

		while (primeraCasilla.getBarco() != null || primeraCasilla.getFila() + barco.getTama�o() - 1 > tablero
				.getCasillas().length - 1 || tablero
				.getCasillas()[primeraCasilla.getFila() + barco.getTama�o() - 1][primeraCasilla.getColumna()].getBarco() != null)
			//TODO  si el barco no entra en el tablero... A�ADIDO o si la casilla esta ocupada
			primeraCasilla = obtenerCasillaAleatoria(tablero, rand);

		return colocadorDeBarcos.colocarBarco(
				barco.getId(),
				primeraCasilla,
				tablero.getCasillas()[primeraCasilla.getFila()
						+ barco.getTama�o() - 1][primeraCasilla.getColumna()]);
	}

	public Casilla obtenerCasillaAleatoria(Tablero tablero, Random rand) {
		return tablero.getCasillas()[rand.nextInt(tablero.getCasillas().length)][rand
				.nextInt(tablero.getCasillas()[0].length)];
	}

}
