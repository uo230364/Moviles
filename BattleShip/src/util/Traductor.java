package util;

public class Traductor {
	
	public static final Character CARACTER_BASE = 'A';
	
	/**
	 * M�todo que traduce el id de un bot�n a un array de dos elementos que contiene su fila y columna correspondientes
	 * en un tablero, en ese orden.
	 * @param cadena
	 * @return
	 */
	public static int[] traducir (String cadena)
	{
		int[] array = { traducir(cadena.charAt(0)), traducir(Character.getNumericValue(cadena.charAt(1))) };
		return array;
	}
	
	public static String traducirALaInversa(int fila, int columna)
	{
		String string = "" + Character.toString((char) (CARACTER_BASE.hashCode() + fila)) + (columna + 1);
		return string;
	}
	
	
	/**
	 * M�todo para traducir las letras de las filas a n�meros para las matrices de casillas
	 * @param letra
	 * @return
	 */
	public static int traducir(char letra)
	{
		return Character.valueOf(letra).hashCode() - CARACTER_BASE.hashCode();
	}
	
	/**
	 * M�todo para traducir los n�meros de las columnas para las matrices de casillas (restarle 1)
	 * @param letra
	 * @return
	 */
	public static int traducir(int numero)
	{
		return numero - 1;
	}

}
