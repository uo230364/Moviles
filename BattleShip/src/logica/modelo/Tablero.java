package logica.modelo;

import java.util.List;

public class Tablero {
	
	private Casilla[][] casillas;
	private List<Barco> barcos;
	
	public Tablero(int filas, int columnas) //dejar como privado/eliminar y eliminar setBarcos()?
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

	public Tablero(int filas, int columnas, List<Barco> barcos)
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

	public List<Barco> getBarcos() {
		return barcos;
	}
	
	public void setBarcos(List<Barco> barcos) {
		this.barcos = barcos;
	}

	public boolean estanTodosLosBarcosHundidos()
	{
		for (Barco barco : barcos) {
			if(!barco.estaHundido())
				return false;
		}
		return true;
	}

}
