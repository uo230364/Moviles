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
public class JuegoFacil extends Activity implements OnInitListener{

	private static final int TABLERO_RIVAL = R.layout.activity_coloca_barcos;
	private static final int TABLERO_JUGADOR = R.layout.activity_tablero_jugador;
	private enum Estado {
		COLOCACION, JUEGO
	}
	
	private Button cambioLayout;
	private boolean sonido=true;
	private Estado estado = Estado.COLOCACION;
	private Partida partida;

	private int barco = 0;
	private int barcosSinColocar = 5;
	private List<View> botonesJugador;
	private static int currentLayout;
	private TextToSpeech tts;
	
	private int water,bomb;
	private SoundManager sound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coloca_barcos);
		
		sound=new SoundManager(getApplicationContext());
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		water=sound.load(R.raw.splash);
		bomb=sound.load(R.raw.bomb);
		
		crearPartida();

		cambioLayout = (Button) findViewById(R.id.cambiarVista);
		cambioLayout.setVisibility(Button.INVISIBLE);
		tts=new TextToSpeech(this,this);
	}

	public void empezarAJugar(View view) {
		if (barcosSinColocar == 0) {
			partida.colocarBarcosDelRival();
			this.estado = Estado.JUEGO;
			for (View vista : getBotonesRival()) {
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
		Casilla[]casillasQueOcupa=new Casilla[2];

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
						casillasQueOcupa[0]=casillas[array[0]][array[1]];
						casillasQueOcupa[1]=casillas[array[0] + 1][array[1]];
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
				if (getCasillasRival()[array[0]][array[1]].getBarco() == null){
					if (sonido)sound.play(water);
					view.setBackgroundColor(Color.TRANSPARENT);
				}
				else{
					if (sonido)sound.play(bomb);
					view.setBackgroundResource(R.drawable.bomba);
					if (partida.haGanadoElJugador())
						tts.speak("Enhorabuena, has ganado", TextToSpeech.QUEUE_ADD,null);
				}
				partida.efectuarDisparoDelRival();
				if (partida.haGanadoElRival())
					tts.speak("Has perdido, prueba otra vez", TextToSpeech.QUEUE_ADD,null);
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
			pintarCasillas(getCasillasJugador());
			break;
		case TABLERO_JUGADOR:
			setContentView(R.layout.activity_coloca_barcos);
			pintarCasillas(getCasillasRival());
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
			for (View boton : botonesJugador) {
				if (boton.getTag() != null
						&& boton.getTag().equals(tagCasillaActual)) {
					if (casillaActual.getBarco() != null) {
						if (casillaActual.estaTocada())
							boton.setBackgroundResource(R.drawable.bomba);
						else {
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
					} else {
						if (casillaActual.estaTocada())
							boton.setBackgroundResource(R.drawable.bomba);
					}
				}
			}
		if (currentLayout == TABLERO_RIVAL)
			for (View boton : getBotonesRival()) {
				if (boton.getTag() != null) {
					if (boton.getTag().equals(tagCasillaActual)){
						if (casillaActual.estaTocada()){
							if(casillaActual.getBarco() != null)
								boton.setBackgroundResource(R.drawable.bomba);
							else
								boton.setBackgroundColor(Color.TRANSPARENT);
						}
					}
					
					if (boton.getTag().equals("jugar"))
						boton.setVisibility(Button.INVISIBLE);
				}
			}
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
	
	public void paraMusica(View view){
		Inicial.paraReproduceMusica(view);
	}
	
	public void paraEfectos(View view){
		if (sonido)sonido=false;
		else sonido=true;
	}

	@Override
	public void onInit(int status) {
		
	}

}
