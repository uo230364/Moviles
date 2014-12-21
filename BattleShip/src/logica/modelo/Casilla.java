package logica.modelo;

public class Casilla {
	
	/**
	 * Ideas para actualizar la GUI con las casillas/barcos: poner un observer, cuando se use el metodo de disparar() (o el nombre 
	 * que se le de) llamará al método de actualización pasando la casilla, se actualiza sólo esa casilla dependiendo de lo que tenga
	 * dentro, por eso estan sobrescritos el equals y hashcode. También se puede hacer que verifique si el barco que esta casilla contiene
	 * esta hundido o no después del disparo, por eso se ha añadido también el id al barco, se podra identificar y contiene las casillas
	 * que ocupa, si está hundido se pueden actualizar sólo esas casillas en la GUI para mostrarlo (así no hará falta actualizar todas
	 * las casillas con cada tirada)
	 */
	
	private boolean tocada;
	private Barco barco;
	private int fila;
	private int columna;
	
	
	public Casilla(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}


	public boolean estaTocada() {
		return tocada;
	}


	public void setTocada(boolean tocada) {
		this.tocada = tocada;
	}


	public Barco getBarco() {
		return barco;
	}


	public void setBarco(Barco barco) {
		this.barco = barco;
	}


	public int getFila() {
		return fila;
	}


	public int getColumna() {
		return columna;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columna;
		result = prime * result + fila;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Casilla other = (Casilla) obj;
		if (columna != other.columna)
			return false;
		if (fila != other.fila)
			return false;
		return true;
	}
	
	
	

}
