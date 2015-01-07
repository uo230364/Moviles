package logica.modelo;

public class Barco {

	private Casilla[] casillasQueOcupa;
	private int tamaño;
	private int id; // para guardar un set de barcos y poder identificarlos de
					// alguna manera desde el resto de clases

	public Barco(int tamaño, int id) {
		this.tamaño = tamaño;
		this.id = id;
	}

	public Casilla[] getCasillasQueOcupa() {
		return casillasQueOcupa;
	}

	public void setCasillasQueOcupa(Casilla[] casillasQueOcupa) {
		if (casillasQueOcupa.length != this.tamaño)
			throw new IllegalArgumentException(
					"El número de casillas que ocupa el barco coincide con su tamaño.");
		if (this.casillasQueOcupa != null) {
			for (Casilla casilla : this.casillasQueOcupa) {
				casilla.setBarco(null);
			}
		}
		this.casillasQueOcupa = casillasQueOcupa;
		for (Casilla casilla : this.casillasQueOcupa) {
			casilla.setBarco(this);
		}
	}

	public boolean estaTocado() {
		for (Casilla casilla : casillasQueOcupa) {
			if (casilla.estaTocada()) {
				return true;
			}
		}
		return false;
	}

	public boolean estaHundido() {
		for (Casilla casilla : casillasQueOcupa) {
			if (!casilla.estaTocada())
				return false;
		}
		return true;
	}

	public int getTamaño() {
		return tamaño;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Barco other = (Barco) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Barco clonar() {
		return new Barco(tamaño, id);
	}

}
