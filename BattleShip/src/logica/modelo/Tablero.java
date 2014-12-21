package logica.modelo;

import java.util.Collections;
import java.util.Set;

public class Tablero {
	
	private Casilla[][] casillas;
	private Set<Barco> barcos;
	
	public Tablero(int filas, int columnas)
	{
		casillas = new Casilla[filas][columnas];
		for(int i = 0; i < filas; i++)
		{
			for(int j = 0; j < columnas; j++)
			{
				casillas[i][j] = new Casilla(i, j);
			}
		}
	}
	
	public Casilla[][] getCasillas() {
		return casillas;
	}

	public Tablero(int filas, int columnas, Set<Barco> barcos)
	{
		this(filas, columnas);
		this.barcos = barcos;
	}
	
	public boolean dispararCasilla(int fila, int columna)
	{
		if(casillas[fila][columna].estaTocada())
			return false;
		casillas[fila][columna].setTocada(true);
		return true;
	}

	public Set<Barco> getBarcos() {
		return barcos;
	}

}
