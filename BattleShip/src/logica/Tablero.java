package logica;

public class Tablero {
	
	private Casilla[][] casillas;
	
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

}
