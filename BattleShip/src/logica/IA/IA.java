package logica.IA;

import logica.modelo.Casilla;
import logica.modelo.Tablero;

public interface IA {
	
	public Casilla proximaCasillaADisparar();
	public void setTableroDelJugador(Tablero tablero);
	public void colocarBarcos(Tablero tablero);

}