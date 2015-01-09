package gui;

import java.util.ArrayList;
import java.util.List;

import logica.IA.IAFacil;
import logica.modelo.Barco;
import logica.modelo.Casilla;
import logica.modelo.Partida;
import util.Traductor;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.battleship.R;

public class JuegoFacil extends Activity {

	private Button cambioLayout;

	private enum Estado {
		COLOCACION, JUEGO
	}

	private static final int TABLERO_RIVAL = R.layout.activity_coloca_barcos;
	private static final int TABLERO_JUGADOR = R.layout.activity_tablero_jugador;

	private Estado estado = Estado.COLOCACION;
	private Partida partida;

	private int barco = 0;
	private int barcosSinColocar = 5;
	private List<View> botonesEnemigo;
	private List<View> botonesJugador;
	private static int currentLayout;
	Casilla[][] casillasJugador;
	Casilla[][] casillasDelRival;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coloca_barcos);
		crearPartida();
		botonesEnemigo = ((RelativeLayout) findViewById(R.id.panelFacilEnemigo))
				.getTouchables();

		cambioLayout = (Button) findViewById(R.id.cambiarVista);
		cambioLayout.setVisibility(Button.INVISIBLE);
		casillasJugador = partida.getTableroDelJugador().getCasillas();
		casillasDelRival = partida.getTableroDelRival().getCasillas();
	}

	public void empezarAJugar(View view) {
		if (barcosSinColocar == 0) {
			for (View vista : botonesEnemigo) {
				if (vista.getTag() != null
						&& vista.getTag().toString() != "salir"
						&& vista.getTag().toString() != "jugar") {
					Button boton = (Button) vista;
					boton.setBackgroundResource(R.drawable.boton);
				}
			}
			findViewById(R.id.empezar).setBackgroundColor(Color.TRANSPARENT);
			cambioLayout.setVisibility(Button.VISIBLE);
		}
	}

	public void jugadaRealizada(View view) {

		String identificador = getResources()
				.getResourceEntryName(view.getId()).toUpperCase();
		int[] array = Traductor.traducir(identificador);
		Casilla[][] casillas = partida.getTableroDelJugador().getCasillas();
		List<Barco> barcos = partida.getTableroDelJugador().getBarcos();

		if (this.estado == Estado.COLOCACION && barcosSinColocar != 0) {
			if (array[0] + 1 <= 4) {
				if (casillas[array[0]][array[1]].getBarco() == null
						&& casillas[array[0] + 1][array[1]].getBarco() == null) {
					casillas[array[0]][array[1]].setBarco(barcos.get(barco));
					casillas[array[0] + 1][array[1]]
							.setBarco(barcos.get(barco));
					Button boton1 = (Button) findViewById(view.getId());
					Button boton2 = obtenerBotonAbajo(array[0] + 1, array[1]);
					boton1.setBackgroundResource(R.drawable.barcovertical1);
					boton2.setBackgroundResource(R.drawable.barcovertical2);
					barco++;
					barcosSinColocar--;

					if (barcosSinColocar == 0) {
						this.estado = Estado.JUEGO;
						partida.colocarBarcosDelRival();
					}
				}
			}
		} else {
			if (partida.efectuarDisparoDelJugador(array[0], array[1])) {
				if (casillasDelRival[array[0]][array[1]].getBarco() == null)
					view.setBackgroundColor(Color.TRANSPARENT);
				else
					view.setBackgroundResource(R.drawable.bomba);
				partida.efectuarDisparoDelRival();
			}
		}

		// Probandooooo
	};

	private Button obtenerBotonAbajo(int fila, int columna) {
		String id = Traductor.traducirALaInversa(fila, columna);
		Button botton = null;
		for (View button : botonesEnemigo) {
			if (button.getTag().toString().equals(id)) {
				botton = (Button) button;
				break;
			}
		}
		return botton;
	}

	private void crearPartida() {
		List<Barco> barcos = new ArrayList<Barco>();
		barcos = creaBarcos(5);
		this.partida = new Partida(5, 5, new IAFacil(), barcos);
	}

	private List<Barco> creaBarcos(int numeroDeBarcos) {
		List<Barco> barcos = new ArrayList<Barco>();
		for (int i = 0; i < numeroDeBarcos; i++) {
			barcos.add(new Barco(2, i));
		}
		return barcos;
	}

	public void cambioLayout(View view) {
		switch (currentLayout) {
		case TABLERO_RIVAL:
			setContentView(R.layout.activity_tablero_jugador);
			botonesJugador = ((RelativeLayout) findViewById(R.id.panelFacilJugador))
					.getTouchables();
			for (View boton : botonesJugador) {
				if (boton.getTag() != null
						&& !boton.getTag().equals("cambioLayout")) {
					boton.setBackgroundColor(Color.TRANSPARENT);
					boton.setEnabled(false);
				}
			}
			pintarCasillas(casillasJugador);
			break;
		case TABLERO_JUGADOR:
			setContentView(R.layout.activity_coloca_barcos);
			pintarCasillas(casillasDelRival);
			break;
		}
	}

	private void pintarCasillas(Casilla[][] casillas) {
		for (int i = 0; i < casillas.length; i++)
			for (int j = 0; j < casillas[0].length; j++) {
				Casilla casillaActual = casillas[i][j];
				String tagCasillaActual = Traductor.traducirALaInversa(i, j);
				Casilla casillaAbajo = null;
				Casilla casillaArriba = null;
				if (i < 4)
					casillaAbajo = casillas[i + 1][j];
				if (i != 0)
					casillaArriba = casillas[i - 1][j];
				pintarSegunLayout(casillaActual, tagCasillaActual,
						casillaAbajo, casillaArriba);
			}
	}

	private void pintarSegunLayout(Casilla casillaActual,
			String tagCasillaActual, Casilla casillaAbajo, Casilla casillaArriba) {

		if (currentLayout == TABLERO_JUGADOR)
			if (casillaActual.getBarco() != null) {
				for (View boton : botonesJugador) {
					if (boton.getTag() != null
							&& boton.getTag().equals(tagCasillaActual)) {
						if (casillaAbajo != null
								&& casillaAbajo.getBarco() != null
								&& casillaAbajo.getBarco().getId() == casillaActual
										.getBarco().getId())
							boton.setBackgroundResource(R.drawable.barcovertical1);
						if (casillaArriba != null
								&& casillaArriba.getBarco() != null
								&& casillaArriba.getBarco().getId() == casillaActual
										.getBarco().getId())
							boton.setBackgroundResource(R.drawable.barcovertical2);
					}
				}
			}
		if (currentLayout == TABLERO_RIVAL)
			if (casillaActual.estaTocada())
				for (View boton : botonesEnemigo)
					if (boton.getTag() != null
							&& boton.getTag().equals(tagCasillaActual))
						boton.setBackgroundColor(Color.TRANSPARENT);
	}

	@Override
	public void setContentView(int layoutResID) {
		currentLayout = layoutResID;
		super.setContentView(layoutResID);
	}

}
