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
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.battleship.R;

public class JuegoFacil extends Activity {
	
	private enum Estado{COLOCACION,JUEGO};
	
	private Estado estado=Estado.COLOCACION;
	private Partida partida;
	private List<Casilla> casillasBarco=new ArrayList<Casilla>();
	private List<Button> botonesNecesitanBarco=new ArrayList<Button>();
	private int barco=0;
	private int barcosSinColocar=5;
	private List<View> allButtons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coloca_barcos);
		crearPartida();
		allButtons=((RelativeLayout)findViewById(R.id.panelFacil)).getTouchables();
	}
	
	public void jugadaRealizada(View view){
		
		String identificador=getResources().getResourceEntryName(view.getId()).toUpperCase();
		int[] array=Traductor.traducir(identificador);
		Casilla [][] casillas=partida.getTableroDelJugador().getCasillas();
		List<Barco> barcos=partida.getTableroDelJugador().getBarcos();
		
		if (this.estado==Estado.COLOCACION && barcosSinColocar!=0){
			if (array[0]+1<4){
				if(casillas[array[0]][array[1]].getBarco()==null &&
						casillas[array[0]+1][array[1]].getBarco()==null){
					casillas[array[0]][array[1]].setBarco(barcos.get(barco));
					casillas[array[0]+1][array[1]].setBarco(barcos.get(barco));
					Button boton1=(Button)findViewById(view.getId());
					Button boton2=obtenerBotonAbajo(array[0]+1,array[1]);
					boton1.setBackgroundResource(R.drawable.barcoparte1);
					boton2.setBackgroundResource(R.drawable.barcoparte1);
					barco++;
					barcosSinColocar--;
				}
			}				
		}
	};
	
	private Button obtenerBotonAbajo(int fila, int columna){
		String id=Traductor.traducirALaInversa(fila, columna);
		Button botton=null;
		for (View button: allButtons){
			if (button.getTag().toString().equals(id)){
				botton=(Button)button;
				break;
			}
		}
		return botton;
	}
		
	private void asignaBarco (List<Casilla>casillas, int barco){
		List<Barco>barcos=partida.getTableroDelJugador().getBarcos();
		Casilla[] casillasBarco={casillas.get(0),casillas.get(1)};
		for (Barco barquito: barcos){
			if (barquito.getId()==barco)
				barquito.setCasillasQueOcupa(casillasBarco);
		}
	}
	
	private void crearPartida (){
		List<Barco>barcos=new ArrayList<Barco>();
		barcos=creaBarcos(5);
		this.partida=new Partida(5,5,new IAFacil(),barcos);
	}
	
	private List<Barco> creaBarcos(int numeroDeBarcos){
		List<Barco>barcos=new ArrayList<Barco>();
		for (int i=0; i<numeroDeBarcos; i++){
			barcos.add(new Barco(2,i));
		}
		return barcos;
	}
}
