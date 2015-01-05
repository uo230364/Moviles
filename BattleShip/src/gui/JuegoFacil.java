package gui;

import java.util.ArrayList;
import java.util.List;

import logica.IA.IAFacil;
import logica.modelo.Barco;
import logica.modelo.ColocadorDeBarcos;
import logica.modelo.Partida;
import android.app.Activity;
import android.os.Bundle;

import com.example.battleship.R;

public class JuegoFacil extends Activity {
	
	private enum Estado{COLOCACION,JUEGO};
	
	private Estado estado=Estado.COLOCACION;
	private Partida partida;
	private ColocadorDeBarcos colocador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coloca_barcos);
		crearPartida();
		colocador = new ColocadorDeBarcos(partida.getTableroDelJugador());
	}
	
	private void crearPartida (){
		List<Barco>barcos=new ArrayList<Barco>();
		barcos=creaBarcos(3);
		this.partida=new Partida(5,5,new IAFacil(),barcos);
	}
	
	private List<Barco> creaBarcos(int numeroDeBarcos){
		List<Barco>barcos=new ArrayList<Barco>();
		for (int i=0; i<numeroDeBarcos; i++){
			barcos.add(new Barco(3,i));
		}
		return barcos;
	}
}
