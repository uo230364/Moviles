package logica.IA;

import logica.modelo.Casilla;
import logica.modelo.Tablero;

public interface IA {
	
	public Casilla proximaCasillaADisparar();
	public void setCasillasSinDispararDelJugador(Tablero tablero);
	public void colocarBarcos(Tablero tablero);

}