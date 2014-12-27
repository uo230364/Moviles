package util;

public class Traductor {
	
	public static final Character CARACTER_BASE = 'A';
	
	/**
	 * Método para traducir las letras de las filas a números para las matrices de casillas
	 * @param letra
	 * @return
	 */
	public static int traducir(char letra)
	{
		return Character.valueOf(letra).hashCode() - CARACTER_BASE.hashCode();
	}

}
