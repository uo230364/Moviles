package gui;

import java.util.ArrayList;
import java.util.List;

import logica.IA.IAFacil;
import logica.modelo.Barco;
import logica.modelo.Casilla;
import logica.modelo.ColocadorDeBarcos;
import logica.modelo.Partida;
import util.Traductor;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class JuegoFacil extends Activity {
	
	private enum Estado{COLOCACION,JUEGO};
	
	private Estado estado=Estado.COLOCACION;
	private Partida partida;
	private ColocadorDeBarcos colocador;
	private List<Casilla> casillasBarco=new ArrayList<Casilla>();
	private int barco=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coloca_barcos);
		crearPartida();
		colocador = new ColocadorDeBarcos(partida.getTableroDelJugador());
	}
	
	public void jugadaRealizada(View view){
		
		String identificador=getResources().getResourceEntryName(view.getId());
		int[] array=Traductor.traducir(identificador);
		Casilla [][] casillas=partida.getTableroDelJugador().getCasillas();
		if (this.estado==Estado.COLOCACION){
			if (casillasBarco.size()<1){
				casillasBarco.add(casillas[array[0]][array[1]]);
			}else{
				casillasBarco.add(casillas[array[0]][array[1]]);
				colocador.colocarBarco(barco, casillasBarco.get(0), casillasBarco.get(1));
				casillasBarco.clear();
			}
		};
		
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
