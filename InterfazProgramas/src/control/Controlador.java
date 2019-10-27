package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;
import view.Ventana;

public class Controlador extends MouseAdapter implements ActionListener {
	private Ventana ventana;	
	

	public Controlador(Ventana ventana) {
		this.ventana = ventana;
	}

	@Override
	public void actionPerformed(ActionEvent ev) { //Ponemos 2 opciones de ruta para dar 2 opciones según la ubicación del programa
		if (ev.getSource() == ventana.getBtnExcel()) {
			lanzarPrograma("C:\\Program Files\\Microsoft Office\\Office16\\EXCEL", 
					"C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL");
		} else if (ev.getSource() == ventana.getBtnWord()) {
			lanzarPrograma("C:\\Program Files\\Microsoft Office\\Office16\\WINWORD", 
					"C:\\Program Files\\Microsoft Office\\root\\Office16\\WINWORD");
		} else if (ev.getSource() == ventana.getBtnPowerP()) {
			lanzarPrograma("C:\\Program Files\\Microsoft Office\\Office16\\POWERPNT", 
					"C:\\Program Files\\Microsoft Office\\root\\Office16\\POWERPNT");
		} else if (ev.getSource() == ventana.getBtnNavegar()) {
			String url = ventana.obtenerUrl();
			if (!url.equalsIgnoreCase("http://www.") & !url.equalsIgnoreCase("")) {
				abrirNavegador(url);
			} else {
				 generarError();
			}
		}		
	}	

	private void abrirNavegador(String url) {
		
		try {			
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e) {			
			e.printStackTrace();
			JOptionPane.showMessageDialog(ventana, "No se ha podido abrir el navegador", "Error", JOptionPane.WARNING_MESSAGE);
		} 		
	}	

	private void lanzarPrograma(String ruta, String rutaAlter) {
		Runtime r = Runtime.getRuntime();		
		try {			
			r.exec(ruta);
		} catch (IOException e) {				
			try {
				r.exec(rutaAlter);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(ventana, "No se ha podido abrir la aplicación", "Error", JOptionPane.WARNING_MESSAGE);					
			}
						
		}		
	}
	
	private void generarError() {
		try {
				Player apl = new Player(new FileInputStream("Archivos\\fallo.mp3"));
				apl.play();
				apl.close();			
			} catch (Exception e) {			
				e.printStackTrace();
			}
	}
	
	@Override
	public void mouseClicked(MouseEvent ev) {
	    if (ev.getSource() == ventana.getTxtWeb()) {
	    	ventana.limpiarLbl();
	    } else if (ev.getSource() == ventana.getListaWebs()) {
	    	String urlSeleccion = ventana.getListaWebs().getSelectedValue();
	    	ventana.getTxtWeb().setText(urlSeleccion);
	    	abrirNavegador(urlSeleccion);	    	
	    	ventana.getListaWebs().clearSelection();
	    }
	    
	  }

}
