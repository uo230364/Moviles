
package gui;

import java.util.ArrayList;
import java.util.List;

import logica.IA.IAMedio;
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

public class JuegoMedio extends Activity {
	
	private enum Estado{COLOCACION,JUEGO};
	
	private Estado estado=Estado.COLOCACION;
	private Partida partida;
	private int barco=0;
	private int barcosSinColocar=6;
	private List<View> allButtons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego_medio);
		crearPartida();
		allButtons=((RelativeLayout)findViewById(R.id.panelMedio)).getTouchables();
	}
	
	public void empezarAJugar(View view){
		for(View vista:allButtons){
			if(vista.getTag().toString()!="salir" && vista.getTag().toString()!="jugar"){
				Button boton=(Button)vista;
				boton.setBackgroundResource(R.drawable.boton);
			}
		}
		findViewById(R.id.empezar).setBackgroundColor(Color.TRANSPARENT);
	}
	
	public void jugadaRealizada(View view){
		
		String identificador=view.getTag().toString();
		int[] array=Traductor.traducir(identificador);
		Casilla [][] casillas=partida.getTableroDelJugador().getCasillas();
		List<Barco> barcos=partida.getTableroDelJugador().getBarcos();
		
		if (this.estado==Estado.COLOCACION && barcosSinColocar!=0){
			if (array[0]+1<=6){
				if(casillas[array[0]][array[1]].getBarco()==null &&
						casillas[array[0]+1][array[1]].getBarco()==null){
					casillas[array[0]][array[1]].setBarco(barcos.get(barco));
					casillas[array[0]+1][array[1]].setBarco(barcos.get(barco));
					Button boton1=(Button)findViewById(view.getId());
					Button boton2=obtenerBotonAbajo(array[0]+1,array[1]);
					boton1.setBackgroundResource(R.drawable.barcovertical1);
				    boton2.setBackgroundResource(R.drawable.barcovertical2);
					barco++;
					barcosSinColocar--;
					
					if (barcosSinColocar==0){
						this.estado=Estado.JUEGO;
						partida.colocarBarcosDelRival();
					}
				}
			}				
		}	
		else{
			Casilla [][] casillasDelRival=partida.getTableroDelRival().getCasillas();
			if (partida.efectuarDisparoDelJugador(array[0], array[1])){
				if(casillasDelRival[array[0]][array[1]].getBarco()==null)
					view.setBackgroundColor(Color.TRANSPARENT);
				else
					view.setBackgroundResource(R.drawable.bomba);
				partida.efectuarDisparoDelRival();
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
	
	private void crearPartida (){
		List<Barco>barcos=new ArrayList<Barco>();
		barcos=creaBarcos(6);
		this.partida=new Partida(7,7,new IAMedio(),barcos);
	}
	
	private List<Barco> creaBarcos(int numeroDeBarcos){
		List<Barco>barcos=new ArrayList<Barco>();
		for (int i=0; i<numeroDeBarcos; i++){
			barcos.add(new Barco(2,i));
		}
		return barcos;
	}
}


