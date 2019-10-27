package view;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import control.Controlador;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ListSelectionModel;

public class Ventana extends JFrame {
	private JPanel panel;
	private JButton btnExcel;
	private JTextField txtWeb;
	private JButton btnPowerP;
	private JButton btnWord;
	private JList<String> listaWebs;
	private DefaultListModel<String> dlm;
	private JScrollPane scrollPaneList;
	private JButton btnNavegar;
	private JLabel lblError;

	public Ventana() throws HeadlessException {
		super("Gestión de programas");
		setBackground(UIManager.getColor("TextArea.inactiveBackground"));
		inicializar();
	}

	private void inicializar() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {			
			e.printStackTrace();
		}
		panel = new JPanel();
		panel.setBackground(new Color(240, 255, 255));
		setPreferredSize(new Dimension(594, 418));
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();		
		Dimension ventana = this.getPreferredSize();		
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);		
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setContentPane(panel);
		panel.setLayout(null);
		
		btnExcel = new JButton("EXCEL");
		btnExcel.setBackground(new Color(144, 238, 144));
		btnExcel.setFont(new Font("Arial", Font.BOLD, 12));
		btnExcel.setBounds(60, 24, 99, 23);
		panel.add(btnExcel);
		
		btnWord = new JButton("WORD");
		btnWord.setBackground(new Color(30, 144, 255));
		btnWord.setFont(new Font("Arial", Font.BOLD, 12));
		btnWord.setBounds(234, 24, 89, 23);
		panel.add(btnWord);
		
		btnPowerP = new JButton("POWER POINT");
		btnPowerP.setBackground(new Color(255, 153, 102));
		btnPowerP.setFont(new Font("Arial", Font.BOLD, 12));
		btnPowerP.setBounds(400, 24, 120, 23);
		panel.add(btnPowerP);
		
		txtWeb = new JTextField();
		txtWeb.setBackground(new Color(255, 255, 240));
		txtWeb.setText("http://www.");
		txtWeb.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtWeb.setBounds(60, 94, 296, 20);
		panel.add(txtWeb);
		txtWeb.setColumns(10);
		
		btnNavegar = new JButton("NAVEGAR");
		btnNavegar.setBackground(new Color(224, 255, 255));
		btnNavegar.setFont(new Font("Arial", Font.BOLD, 12));
		btnNavegar.setBounds(380, 92, 108, 23);
		panel.add(btnNavegar);
		
		scrollPaneList = new JScrollPane();
		scrollPaneList.setBounds(60, 146, 378, 169);
		panel.add(scrollPaneList);
		
		listaWebs = new JList<String>();
		listaWebs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaWebs.setBackground(new Color(255, 255, 240));
		listaWebs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dlm = new DefaultListModel<String>();
		listaWebs.setModel(dlm);
		scrollPaneList.setViewportView(listaWebs);			
		
		lblError = new JLabel("");
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setFont(new Font("Verdana", Font.BOLD, 14));
		lblError.setBounds(160, 336, 224, 23);
		panel.add(lblError);
	}
	
	public String obtenerUrl() {		
		String url; 
		if (!(url = txtWeb.getText()).equalsIgnoreCase("http://www.") & !url.equalsIgnoreCase("")) {			
			if (!dlm.contains(url)) {
				dlm.addElement(url);
				guardarenFichero(url); 
			}
			txtWeb.setText("http://www.");			
		} else {
			lblError.setText("¡Debe introducir una url!");
		}
		return url;
	}	
	
	public void guardarenFichero(String url) {
		FileWriter fl = null;
		try {
			fl = new FileWriter("Archivos\\webs.txt", true);
			fl.write(url+ "\n");
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			if (fl != null) {
				try {
					fl.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void CargarLista() {
		FileReader fr = null;
		String web;
		int letra;
		char [] letras = new char[100];
		int i = 0;
		char c;
		File archivo = new File("Archivos\\webs.txt");
		try {
			fr = new FileReader(archivo);
			if (archivo.exists() & archivo.length() != 0) {
				try {
					while ((letra = fr.read()) > -1) {
						c = (char) letra;
						letras[i] = c;
						i++;
						if (c == '\n') {
							web = String.valueOf(letras);  
							dlm.addElement(web.trim());
							letras = new char[100];
							i =0;
						}						 
					}
					web = new String(letras).trim();					
					dlm.addElement(web);					
				} catch (IOException e) {				
					e.printStackTrace();
				} finally {										
					try {
						if (fr != null) 
							fr.close();
					} catch (IOException e) {					
						e.printStackTrace();
					}					
				}	
			}				
		} catch (FileNotFoundException e) {				
		}
	}

	public JButton getBtnExcel() {
		return btnExcel;
	}

	public JButton getBtnPowerP() {
		return btnPowerP;
	}

	public JButton getBtnWord() {
		return btnWord;
	}

	public JButton getBtnNavegar() {
		return btnNavegar;
	}		

	public JTextField getTxtWeb() {
		return txtWeb;
	}	

	public JList<String> getListaWebs() {
		return listaWebs;
	}

	public void hacerVisible() {
		setVisible(true);
	}


	public void setControl(Controlador control) {
		btnExcel.addActionListener(control);
		btnWord.addActionListener(control);
		btnPowerP.addActionListener(control);
		btnNavegar.addActionListener(control);
		txtWeb.addMouseListener(control);
		listaWebs.addMouseListener(control);
	}

	public void limpiarLbl() {
		lblError.setText("");		
	}
}
