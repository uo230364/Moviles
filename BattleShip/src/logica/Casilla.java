package logica;

public class Casilla {
	
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
