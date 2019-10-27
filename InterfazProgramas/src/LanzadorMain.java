import java.awt.EventQueue;
import control.Controlador;
import view.Ventana;

public class LanzadorMain {

	public static void main(String[] args) {		
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {				
				Ventana v = new Ventana();
				Controlador ctrl = new Controlador(v);				
				v.setControl(ctrl);
				v.hacerVisible();
				v.CargarLista();
			}
		});

	}

}
