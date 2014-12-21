package logica.modelo;

import java.util.Set;

import logica.IA.IA;

public class Partida {
	//para representar el estado de la partida: los dos tableros, turnos, etc.
	public static final int FILAS_POR_DEFECTO = 8;
	public static final int COLUMNAS_POR_DEFECTO = 10;
	
	private Tablero tableroDelJugador;
	private Tablero tableroDelRival;
	private IA rival;
	private boolean turnoDelJugador = true; //true si le toca jugar al jugador, false si le toca al rival
	
	public Partida(IA dificultadDelRival, Set<Barco> barcos)
	{
		this.rival = dificultadDelRival;
		tableroDelJugador = new Tablero(FILAS_POR_DEFECTO, COLUMNAS_POR_DEFECTO, barcos);
		tableroDelRival = new Tablero(FILAS_POR_DEFECTO, COLUMNAS_POR_DEFECTO, barcos);
		this.rival.setCasillasSinDispararDelJugador(tableroDelJugador);
	}
	
	public Partida(int filasPorTablero, int columnasPorTablero, IA dificultadDelRival, Set<Barco> barcos)
	{
		this.rival = dificultadDelRival;
		tableroDelJugador = new Tablero(filasPorTablero, columnasPorTablero, barcos);
		tableroDelRival = new Tablero(filasPorTablero, columnasPorTablero, barcos);
		this.rival.setCasillasSinDispararDelJugador(tableroDelJugador);
	}
	
	/**
	 * Método que devuelve true si es el turno del jugador, false si es el turno del rival
	 * @return boolean
	 */
	public boolean esElTurnoDelJugador()
	{
		return turnoDelJugador;
	}
	
	public boolean haGanadoElJugador()
	{
		return tableroDelRival.estanTodosLosBarcosHundidos();
	}
	
	public boolean haGanadoElRival()
	{
		return tableroDelJugador.estanTodosLosBarcosHundidos();
	}
	
	public boolean partidaTerminada() //TODO métodos temporales, echarle un ojo a esto
	{
		return haGanadoElJugador() && haGanadoElRival();
	}
	
	public boolean efectuarDisparoDelJugador(int fila, int columna)
	{
		return tableroDelRival.dispararCasilla(fila, columna);
	}
	
	public boolean efectuarDisparoDelRival()
	{
		if(turnoDelJugador)
			return false;
		rival.proximaCasillaADisparar().setTocada(true);
		return true;
	}

}
