package logica.modelo;

import java.util.List;

import logica.IA.IA;

public class Partida {
	//para representar el estado de la partida: los dos tableros, turnos, etc.
	public static final int FILAS_POR_DEFECTO = 8;
	public static final int COLUMNAS_POR_DEFECTO = 10;
	
	private Tablero tableroDelJugador;
	private Tablero tableroDelRival;
	private IA rival;
	private boolean turnoDelJugador = true; //true si le toca jugar al jugador, false si le toca al rival
	
	
	/**
	 * Constructor por defecto de Partida, crea dos tableros con el tamaño por defecto, requiere como parámetro los barcos
	 * con los que se va a jugar la partida y una instancia de la IA del rival ( por ejemplo new IAFacil() )
	 * @param dificultadDelRival
	 * @param barcos
	 */
	public Partida(IA dificultadDelRival, List<Barco> barcos)
	{
		tableroDelJugador = new Tablero(FILAS_POR_DEFECTO, COLUMNAS_POR_DEFECTO, barcos);
		tableroDelRival = new Tablero(FILAS_POR_DEFECTO, COLUMNAS_POR_DEFECTO, barcos);
		this.rival = dificultadDelRival;
		this.rival.setTableroDelJugador(tableroDelJugador);
	}
	
	/**
	 * Crea una partida con el tamaño de tablero, barcos y dificultad introducidos como parámetros
	 * @param filasPorTablero
	 * @param columnasPorTablero
	 * @param dificultadDelRival
	 * @param barcos
	 */
	public Partida(int filasPorTablero, int columnasPorTablero, IA dificultadDelRival, List<Barco> barcos)
	{
		tableroDelJugador = new Tablero(filasPorTablero, columnasPorTablero, barcos);
		tableroDelRival = new Tablero(filasPorTablero, columnasPorTablero, barcos);
		this.rival = dificultadDelRival;
		this.rival.setTableroDelJugador(tableroDelJugador);
	}
	
	public void setDificultad(IA dificultadDelRival)
	{
		this.rival = dificultadDelRival;
	}
	
	public Tablero getTableroDelJugador() {
		return tableroDelJugador;
	}

	public Tablero getTableroDelRival() {
		return tableroDelRival;
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
		return haGanadoElJugador() || haGanadoElRival();
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
