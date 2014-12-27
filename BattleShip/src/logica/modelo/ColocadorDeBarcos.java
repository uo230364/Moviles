package logica.modelo;

import java.util.ArrayList;
import java.util.List;

public class ColocadorDeBarcos {

	private Tablero tableroConBarcos;

	public ColocadorDeBarcos(Tablero tableroConBarcos) {
		this.tableroConBarcos = tableroConBarcos;
	}

	/**
	 * Este m�todo sirve para colocar los barcos, para facilitar el uso se introducir�n los par�metros de la siguiente manera:
	 * 1� id del barco, 2� la primera casilla (la menor de las dos, ya sea en n�mero de fila o columna), 3� la segunda casilla
	 * (la mayor de las dos)
	 * @param id
	 * @param primeraCasilla
	 * @param segundaCasilla
	 * @return
	 */
	public boolean colocarBarco(int id, Casilla primeraCasilla,
			Casilla segundaCasilla) {
		List<Casilla> casillasQueOcupariaElBarco = calcularCasillasIntermedias(primeraCasilla, segundaCasilla);
		for (Casilla casilla : casillasQueOcupariaElBarco) {
			if (casilla.estaTocada())
				return false;
		}
		try{
		tableroConBarcos
				.getBarcos()
				.get(id)
				.setCasillasQueOcupa(
						casillasQueOcupariaElBarco.toArray(
								new Casilla[casillasQueOcupariaElBarco.size()]));
		}catch(IllegalArgumentException e)
		{
			return false; //TODO lanzar mensaje?
		}
		return true;
	}

	private List<Casilla> calcularCasillasIntermedias(Casilla primeraCasilla,
			Casilla segundaCasilla) {
		List<Casilla> lista = new ArrayList<Casilla>();
		lista.add(primeraCasilla);
		lista.add(segundaCasilla);
		
		boolean continuar = true;
		while(continuar)
		{
			if(lista.get(lista.size()-2).getFila() + 1 < lista.get(lista.size()-1).getFila()
					&& lista.get(lista.size()-2).getColumna() == lista.get(lista.size()-1).getColumna()) //el barco esta en vertical
			{
				lista.add(lista.size()-2,
						tableroConBarcos.getCasillas()[lista.get(lista.size()-2).getFila() + 1][lista.get(lista.size()-2).getColumna()]);
			}//TODO comprobar que funciona bien
			else if(lista.get(lista.size()-2).getFila() == lista.get(lista.size()-1).getFila()
					&& lista.get(lista.size()-2).getColumna() + 1 < lista.get(lista.size()-1).getColumna()) //el barco esta en horizontal
			{
				lista.add(lista.size()-2,
						tableroConBarcos.getCasillas()[lista.get(lista.size()-2).getFila()][lista.get(lista.size()-2).getColumna()+1]);
			}
			else
				continuar = false;
		}
		
		return lista;
	}

}
