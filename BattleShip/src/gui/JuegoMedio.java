package gui;

import java.util.ArrayList;
import java.util.List;

import logica.IA.IAMedio;
import logica.modelo.Barco;
import logica.modelo.Casilla;
import logica.modelo.Partida;
import util.SoundManager;
import util.Traductor;
import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.battleship.R;

public class JuegoMedio extends Activity implements OnInitListener {

	private enum Estado {
		COLOCACION, JUEGO
	};

	private static final int ALTO_ANCHO_TABLERO = 7;

	private Button cambioLayout;
	private Estado estado = Estado.COLOCACION;
	private Partida partida;
	private int barco = 0;
	private int barcosSinColocar = 6;
	private List<View> allButtons;
	private static final int TABLERO_RIVAL = R.layout.activity_juego_medio;
	private static final int TABLERO_JUGADOR = R.layout.activity_tablero_jugador_medio;
	private static int currentLayout;
	private TextToSpeech tts;

	private int water, bomb;
	private SoundManager sound;
	private boolean sonido = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego_medio);
		crearPartida();
		allButtons = ((RelativeLayout) findViewById(R.id.panelDificilEnemigo))
				.getTouchables();
		sound = new SoundManager(getApplicationContext());
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		water = sound.load(R.raw.splash);
		bomb = sound.load(R.raw.bomb);
		cambioLayout = (Button) findViewById(R.id.cambiarVista);
		cambioLayout.setVisibility(Button.INVISIBLE);
		tts = new TextToSpeech(this, this);

		if (!Inicial.obtenerReproductor().isPlaying())
			findViewById(R.id.btMusicaMedio).setBackgroundResource(
					R.drawable.botonmusicaquitado);
	}

	public void empezarAJugar(View view) {
		if (barcosSinColocar == 0 && estado == Estado.COLOCACION) {
			partida.colocarBarcosDelRival();
			this.estado = Estado.JUEGO;
			for (View vista : getBotonesRival()) {
				if (vista.getTag() != null && !vista.getTag().equals("salir")
						&& !vista.getTag().equals("jugar")) {
					Button boton = (Button) vista;
					boton.setBackgroundResource(R.drawable.boton);
				}
				findViewById(R.id.btMusicaMedio).setBackgroundResource(
						R.drawable.botonmusica);
				findViewById(R.id.btSonidoMedio).setBackgroundResource(
						R.drawable.botonsonido);
			}
			findViewById(R.id.empezar).setBackgroundColor(Color.TRANSPARENT);
			cambioLayout.setVisibility(Button.VISIBLE);
			partida.getRival().setTableroDelJugador(
					partida.getTableroDelJugador());
		}
	}

	public void jugadaRealizada(View view) {

		String identificador = view.getTag().toString();
		int[] array = Traductor.traducir(identificador);
		Casilla[][] casillas = partida.getTableroDelJugador().getCasillas();
		List<Barco> barcos = partida.getTableroDelJugador().getBarcos();
		Casilla[] casillasQueOcupa = new Casilla[2];

		if (this.estado == Estado.COLOCACION) {
			if (barcosSinColocar != 0) {
				if (casillas[array[0]][array[1]].getBarco() == null) {
					if (barcosSinColocar <= 3 && array[1] + 1 <= 6) {
						if (casillas[array[0]][array[1] + 1].getBarco() == null) {
							casillas[array[0]][array[1]].setBarco(barcos
									.get(barco));
							casillas[array[0]][array[1] + 1].setBarco(barcos
									.get(barco));
							casillasQueOcupa[0] = casillas[array[0]][array[1]];
							casillasQueOcupa[1] = casillas[array[0]][array[1] + 1];
							barcos.get(barco).setCasillasQueOcupa(
									casillasQueOcupa);
							Button boton1 = (Button) findViewById(view.getId());
							Button boton2 = obtenerBotonDerecha(array[0],
									array[1] + 1);
							boton1.setBackgroundResource(R.drawable.barcohorizontal1);
							boton2.setBackgroundResource(R.drawable.barcohorizontal2);
							barco++;
							barcosSinColocar--;
						}
					}
					else 
					if (barcosSinColocar > 3) {
						if (array[0] + 1 <= 6) {
							if (casillas[array[0] + 1][array[1]].getBarco() == null) {
								casillas[array[0]][array[1]].setBarco(barcos
										.get(barco));
								casillas[array[0] + 1][array[1]]
										.setBarco(barcos.get(barco));
								casillasQueOcupa[0] = casillas[array[0]][array[1]];
								casillasQueOcupa[1] = casillas[array[0] + 1][array[1]];
								barcos.get(barco).setCasillasQueOcupa(
										casillasQueOcupa);
								Button boton1 = (Button) findViewById(view
										.getId());
								Button boton2 = obtenerBotonAbajo(array[0] + 1,
										array[1]);
								boton1.setBackgroundResource(R.drawable.barcovertical1);
								boton2.setBackgroundResource(R.drawable.barcovertical2);
								barco++;
								barcosSinColocar--;
							}
						}
					}

				}

			}
			else {
				Toast toast = Toast.makeText(this,
					"Ya colocaste todos tus barcos. Pulsa JUGAR para comenzar",
					Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		else if (this.estado == Estado.JUEGO && !partida.partidaTerminada()) {
			if (partida.efectuarDisparoDelJugador(array[0], array[1])) {
				if (getCasillasRival()[array[0]][array[1]].getBarco() == null) {
					if (sonido)
						sound.play(water);
					view.setBackgroundColor(Color.TRANSPARENT);
				}
				else {
					if (sonido)
						sound.play(bomb);
					view.setBackgroundResource(R.drawable.bomba);
					if (partida.haGanadoElJugador())
						tts.speak("Enhorabuena, has ganado",
								TextToSpeech.QUEUE_ADD, null);
				}
				partida.efectuarDisparoDelRival();
				if (partida.haGanadoElRival())
					tts.speak("Has perdido, prueba otra vez",
							TextToSpeech.QUEUE_ADD, null);
			}
		}

	}
	
	private Button obtenerBotonAbajo(int fila, int columna) {
		String id = Traductor.traducirALaInversa(fila, columna);
		Button botton = null;
		for (View button : allButtons) {
			if (button.getTag().toString().equals(id)) {
				botton = (Button) button;
				break;
			}
		}
		return botton;
	}

	private Button obtenerBotonDerecha(int fila, int columna) {
		String id = Traductor.traducirALaInversa(fila, columna);
		Button botton = null;
		for (View button : allButtons) {
			if (button.getTag().toString().equals(id)) {
				botton = (Button) button;
				break;
			}
		}
		return botton;
	}

	public void cambioLayout(View view) {
		switch (currentLayout) {
		case TABLERO_RIVAL:
			setContentView(R.layout.activity_tablero_jugador_medio);
			pintarCasillas(getCasillasJugador());
			break;
		case TABLERO_JUGADOR:
			setContentView(R.layout.activity_juego_medio);
			pintarCasillas(getCasillasRival());
			deshabilitarBotonJugar();
			break;
		}
	}

	private void deshabilitarBotonJugar() {
		getWindow().getDecorView().findViewWithTag("jugar")
				.setVisibility(Button.INVISIBLE);
	}

	@Override
	public void setContentView(int layoutResID) {
		currentLayout = layoutResID;
		super.setContentView(layoutResID);
	}

	private Casilla[][] getCasillasJugador() {
		return partida.getTableroDelJugador().getCasillas();
	}

	private Casilla[][] getCasillasRival() {
		return partida.getTableroDelRival().getCasillas();
	}

	private List<View> getBotonesRival() {
		return ((RelativeLayout) findViewById(R.id.panelDificilEnemigo))
				.getTouchables();
	}

	private void pintarCasillas(Casilla[][] casillas) {
		for (int i = 0; i < casillas.length; i++)
			for (int j = 0; j < casillas[0].length; j++) {
				Casilla casillaActual = casillas[i][j];
				String tagCasillaActual = Traductor.traducirALaInversa(i, j);
				Casilla casillaAbajo = null;
				Casilla casillaArriba = null;
				Casilla casillaIzquierda = null;
				Casilla casillaDerecha = null;
				if (i < ALTO_ANCHO_TABLERO - 1)
					casillaAbajo = casillas[i + 1][j];
				if (i != 0)
					casillaArriba = casillas[i - 1][j];
				if (j != 0)
					casillaIzquierda = casillas[i][j - 1];
				if (j < ALTO_ANCHO_TABLERO - 1)
					casillaDerecha = casillas[i][j + 1];
				pintarSegunLayout(casillaActual, tagCasillaActual,
						casillaAbajo, casillaArriba, casillaIzquierda,
						casillaDerecha);
			}
	}

	private void pintarSegunLayout(Casilla casillaActual,
			String tagCasillaActual, Casilla casillaAbajo,
			Casilla casillaArriba, Casilla casillaIzquierda,
			Casilla casillaDerecha) {
		Button boton = (Button) getWindow().getDecorView().findViewWithTag(
				tagCasillaActual);
		if (currentLayout == TABLERO_JUGADOR) {
			if (boton.getTag() != null) {
				if (casillaActual.getBarco() != null) {
					if (casillaActual.estaTocada())
						boton.setBackgroundResource(R.drawable.bomba);
					else {
						dibujarBarcoAbajo(casillaActual, casillaAbajo, boton);
						dibujarBarcoArriba(casillaActual, casillaArriba, boton);
						dibujarBarcoIzquierda(casillaActual, casillaIzquierda,
								boton);
						dibujarBarcoDerecha(casillaActual, casillaDerecha,
								boton);
					}
				} else {
					if (casillaActual.estaTocada())
						boton.setBackgroundResource(R.drawable.botonagua);
				}
			}
			boton.setEnabled(false);
		}
		if (currentLayout == TABLERO_RIVAL) {
			if (boton.getTag() != null) {
				if (casillaActual.estaTocada()) {
					if (casillaActual.getBarco() != null)
						boton.setBackgroundResource(R.drawable.bomba);
					else
						boton.setBackgroundColor(Color.TRANSPARENT);
				}
			}
		}
	}

	private void dibujarBarcoAbajo(Casilla casillaActual, Casilla casillaAbajo,
			Button boton) {
		if (casillaAbajo != null
				&& casillaAbajo.getBarco() != null
				&& casillaAbajo.getBarco().getId() == casillaActual.getBarco()
						.getId())
			boton.setBackgroundResource(R.drawable.barcovertical1);
	}

	private void dibujarBarcoArriba(Casilla casillaActual,
			Casilla casillaArriba, Button boton) {
		if (casillaArriba != null
				&& casillaArriba.getBarco() != null
				&& casillaArriba.getBarco().getId() == casillaActual.getBarco()
						.getId())
			boton.setBackgroundResource(R.drawable.barcovertical2);
	}

	private void dibujarBarcoIzquierda(Casilla casillaActual,
			Casilla casillaIzquierda, Button boton) {
		if (casillaIzquierda != null
				&& casillaIzquierda.getBarco() != null
				&& casillaIzquierda.getBarco().getId() == casillaActual
						.getBarco().getId())
			boton.setBackgroundResource(R.drawable.barcohorizontal2);
	}

	private void dibujarBarcoDerecha(Casilla casillaActual,
			Casilla casillaDerecha, Button boton) {
		if (casillaDerecha != null
				&& casillaDerecha.getBarco() != null
				&& casillaDerecha.getBarco().getId() == casillaActual
						.getBarco().getId())
			boton.setBackgroundResource(R.drawable.barcohorizontal1);
	}

	private void crearPartida() {
		List<Barco> barcos = new ArrayList<Barco>();
		barcos = creaBarcos(6);
		this.partida = new Partida(ALTO_ANCHO_TABLERO, ALTO_ANCHO_TABLERO,
				new IAMedio(), barcos);
	}

	private List<Barco> creaBarcos(int numeroDeBarcos) {
		List<Barco> barcos = new ArrayList<Barco>();
		for (int i = 0; i < numeroDeBarcos; i++) {
			barcos.add(new Barco(2, i));
		}
		return barcos;
	}

	public void paraMusica(View view) {
		Inicial.paraReproduceMusica(view);
	}

	public void paraEfecto(View view) {
		if (sonido) {
			sonido = false;
			view.setBackgroundResource(R.drawable.botonsonidoquitado);
		} else {
			sonido = true;
			view.setBackgroundResource(R.drawable.botonsonido);
		}
	}

	@Override
	public void onInit(int status) {

	}

}
