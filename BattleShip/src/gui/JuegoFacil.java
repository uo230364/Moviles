package gui;

import java.util.ArrayList;
import java.util.List;

import logica.IA.IAFacil;
import logica.modelo.Barco;
import logica.modelo.Casilla;
import logica.modelo.Partida;
import util.SoundManager;
import util.Traductor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

@SuppressLint("DefaultLocale")
public class JuegoFacil extends Activity implements OnInitListener {

	private Button cambioLayout;

	private enum Estado {
		COLOCACION, JUEGO
	}

	private static final int TABLERO_RIVAL = R.layout.activity_juego_facil;
	private static final int TABLERO_JUGADOR = R.layout.activity_tablero_jugador_facil;

	private Estado estado = Estado.COLOCACION;
	private Partida partida;

	private int barco = 0;
	private int barcosSinColocar = 5;
	private static int currentLayout;

	private TextToSpeech tts;
	private Intent starterIntent;

	private int water, bomb;
	private SoundManager sound;
	private boolean sonido = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego_facil);
		starterIntent = getIntent();
		sound = new SoundManager(getApplicationContext());
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		water = sound.load(R.raw.splash);
		bomb = sound.load(R.raw.bomb);
		crearPartida();

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
		Casilla[] casillasQueOcupa = new Casilla[2];

		if (this.estado == Estado.COLOCACION) {
			if (barcosSinColocar != 0) {
				if (array[0] + 1 <= 4) {
					if (casillas[array[0]][array[1]].getBarco() == null
							&& casillas[array[0] + 1][array[1]].getBarco() == null) {
						casillas[array[0]][array[1]]
								.setBarco(barcos.get(barco));
						casillas[array[0] + 1][array[1]].setBarco(barcos
								.get(barco));
						Button boton1 = (Button) findViewById(view.getId());
						Button boton2 = obtenerBotonAbajo(array[0] + 1,
								array[1]);
						casillasQueOcupa[0] = casillas[array[0]][array[1]];
						casillasQueOcupa[1] = casillas[array[0] + 1][array[1]];
						barcos.get(barco).setCasillasQueOcupa(casillasQueOcupa);
						boton1.setBackgroundResource(R.drawable.barcovertical1);
						boton2.setBackgroundResource(R.drawable.barcovertical2);
						barco++;
						barcosSinColocar--;
					}
				}
			} else {
				Toast.makeText(
						this,
						"Ya estan puestos todos los barcos, dale al boton Jugar para comenzar",
						Toast.LENGTH_LONG).show();
			}
		} else if (this.estado == Estado.JUEGO && !partida.partidaTerminada()) {
			if (partida.efectuarDisparoDelJugador(array[0], array[1])) {
				if (getCasillasRival()[array[0]][array[1]].getBarco() == null) {
					if (sonido)
						sound.play(water);
					view.setBackgroundColor(Color.TRANSPARENT);
				} else {
					if (sonido)
						sound.play(bomb);
					view.setBackgroundResource(R.drawable.bomba);
					if (partida.haGanadoElJugador()){
						tts.speak("Enhorabuena, has ganado",
								TextToSpeech.QUEUE_ADD, null);
						mostrarVentanaReinicioPartida("�Quieres jugar otra partida?");
					}
				}
				partida.efectuarDisparoDelRival();
				if (partida.haGanadoElRival()){
					Toast.makeText(
							this,
							"Has perdido, prueba suerte otra vez!",
							Toast.LENGTH_LONG).show();
					tts.speak("Has perdido, prueba otra vez",
							TextToSpeech.QUEUE_ADD, null);
					mostrarVentanaReinicioPartida("�Quieres jugar otra partida?");
				}
			}
		}

	};

	private Button obtenerBotonAbajo(int fila, int columna) {
		String id = Traductor.traducirALaInversa(fila, columna);
		Button botton = null;
		for (View button : getBotonesRival()) {
			if (button.getTag().toString().equals(id)) {
				botton = (Button) button;
				break;
			}
		}
		return botton;
	}

	private void crearPartida() {
		estado = Estado.COLOCACION;
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
			setContentView(R.layout.activity_tablero_jugador_facil);
			pintarCasillas(getCasillasJugador());
			break;
		case TABLERO_JUGADOR:
			setContentView(R.layout.activity_juego_facil);
			pintarCasillas(getCasillasRival());
			deshabilitarBotonJugar();
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
					}
				} else {
					if (casillaActual.estaTocada())
						boton.setBackgroundResource(R.drawable.bomba);
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

	public void paraEfectos(View view) {
		if (sonido) {
			sonido = false;
			view.setBackgroundResource(R.drawable.botonsonidoquitado);
		} else {
			sonido = true;
			view.setBackgroundResource(R.drawable.botonsonido);
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
		return ((RelativeLayout) findViewById(R.id.panelFacilEnemigo))
				.getTouchables();
	}

	public void paraReproduceMusica(View view) {
		Inicial.paraReproduceMusica(view);
	}

	@Override
	public void onInit(int status) {

	}

	/**
	 * M�todo para mostrar un dialogo que preguntar� al usuario si quiere
	 * comenzar una partida nueva una vez haya terminado la partida actual.
	 * El par�metro ser� el mensaje que se mostrar� en el di�logo (ej. "Has perdido, �quieres intentarlo otra vez?")
	 * 
	 * @param mensajeAMostrar
	 */
	private void mostrarVentanaReinicioPartida(String mensajeAMostrar) {
		new AlertDialog.Builder(this)
				.setTitle("Reiniciar")
				.setMessage(mensajeAMostrar)
				.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						startActivity(starterIntent);
						finish();
					}
				})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								finish();
							}
						}).show();
	}
	

}
