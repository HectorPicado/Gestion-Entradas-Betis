package betis;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;

public class CrearTicket {
	
	 String nombreRival, competicion, zona, tipoEntrada;
	 boolean esSocio;
	 int precio;


	public CrearTicket(String nombreRival, String competicion, String zona, String tipoEntrada, boolean esSocio,
			int precio) {
		this.nombreRival = nombreRival;
		this.competicion = competicion;
		this.zona = zona;
		this.tipoEntrada = tipoEntrada;
		this.esSocio = esSocio;
		this.precio = precio;
	}


	public void generarTicket(int idEntrada) {

	    String rutaCarpeta = "./Tickets_entradas/";
	    new File(rutaCarpeta).mkdirs(); // crea la carpeta si no existe

	    String nombreTicket = rutaCarpeta + "Ticket_" + idEntrada + ".txt";

	    try (FileWriter writer = new FileWriter(nombreTicket)) {

	        writer.write("Ticket de compra nº " + idEntrada + ":\n");
	        writer.write("Partido: Real Betis vs " + nombreRival + "\n");
	        writer.write("Competición: " + competicion + "\n");
	        writer.write("Zona del campo: " + zona + "\n");
	        writer.write("Socio del club: " + (esSocio ? "Sí" : "No") + "\n");
	        writer.write("Tipo de entrada: " + tipoEntrada + "\n");
	        writer.write("Precio : " + precio + " €\n");
	        writer.write("Muchas gracias por la compra.");

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "No se ha podido realizar el ticket, intentelo de nuevo");
	    }
	}
	

}
