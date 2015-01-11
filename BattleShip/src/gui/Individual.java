
package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logica.IA.IAFacil;
import logica.modelo.Barco;
import logica.modelo.Casilla;
import logica.modelo.Partida;
import util.SoundManager;
import util.Traductor;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.battleship.R;

public class Individual extends Activity implements OnInitListener{
	
	private Partida partida;
	private int barcosSinColocar=15;
	private int jugadasDisponibles=25;
	private int barcosSinHundir=15;
	private Vibrator vibrator;
	private TextView restantes;
	private int water,bomb;
	
	private TextToSpeech tts;
	private SoundManager sound;
	private boolean sonido=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual);
		sound=new SoundManager(getApplicationContext());
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		water=sound.load(R.raw.splash);
		bomb=sound.load(R.raw.bomb);
		crearPartida();
		colocaBarcos();
		vibrator=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
		this.restantes=(TextView)findViewById(R.id.remainingShoots);
		restantes.setText(String.valueOf(jugadasDisponibles));
		tts=new TextToSpeech(this, this);
	}
	
	public void pararMusica(View view){
		Inicial.paraReproduceMusica(view);
	}
	
	public void buscando(View view){
		Casilla [][] casillas=partida.getTableroDelJugador().getCasillas();
		String identificador=view.getTag().toString();
		int[] array=Traductor.traducir(identificador);
		if (jugadasDisponibles!=0 && !partidaGanada()){	
			if (partida.efectuarDisparoDelJugador(array[0], array[1])){
				if(casillas[array[0]][array[1]].getBarco()==null){
					view.setBackgroundColor(Color.TRANSPARENT);
					if (sonido)sound.play(water);
				}
				else{
					view.setBackgroundResource(R.drawable.bomba);
					barcosSinHundir--;
					vibrator.vibrate(500);
					if (sonido)sound.play(bomb);
					if (partidaGanada()){
						tts.speak("Has descubierto todos los barcos, enhorabuena!",TextToSpeech.QUEUE_ADD,null);
						finish();
					}
					else{
						Toast.makeText(
								this,
								"Te quedan "+barcosSinHundir+" barcos por hundir",
								Toast.LENGTH_LONG).show();
					}
				}
			}
			jugadasDisponibles--;
			restantes.setText(String.valueOf(jugadasDisponibles));
			if (jugadasDisponibles==0 && !partidaGanada())
				tts.speak("Has perdido,  prueba suerte otra vez", TextToSpeech.QUEUE_ADD,null);
		}	
		
	}
	
	private void colocaBarcos(){
		Random random=new Random();
		List<Barco>barcos=partida.getTableroDelJugador().getBarcos();
		int barco=0;
		while (barcosSinColocar!=0){
			int fila=random.nextInt(9);
			int columna=random.nextInt(7);
			if (asignaBarco(fila, columna,barcos.get(barco))){
				barco++;
				barcosSinColocar--;
			}
		}
	}
	
	private boolean asignaBarco(int fila, int columna, Barco barco) {
		Casilla[][] casillas=partida.getTableroDelJugador().getCasillas();
		if (casillas[fila][columna].getBarco()==null){
			casillas[fila][columna].setBarco(barco);
			Casilla[]casillasOcu={casillas[fila][columna]};
			barco.setCasillasQueOcupa(casillasOcu);
			return true;
		}
		return false;
	}

	private void crearPartida (){
		List<Barco>barcos=new ArrayList<Barco>();
		barcos=creaBarcos(15);
		this.partida=new Partida(9,7,new IAFacil(),barcos);
	}
	
	private List<Barco> creaBarcos(int numeroDeBarcos){
		List<Barco>barcos=new ArrayList<Barco>();
		for (int i=0; i<numeroDeBarcos; i++){
			barcos.add(new Barco(1,i));
		}
		return barcos;
	}
	
	private boolean partidaGanada(){
		return barcosSinHundir==0;
	}
	
	public void pausaEfectos(View view){
		if (sonido)
			sonido=false;
		else sonido=true;
	}
	
	@Override
	public void onInit(int arg0) {
		
	}
}