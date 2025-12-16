package betis;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Partido {
	
	public static final int LIGA = 0;
	public static final int CHAMPIONS = 1;
	public static final int COPA_DEL_REY = 2;
	public static final double DESCUENTO_SOCIO = 0.9;
	public static final int LIMITE_ENTRADAS = 10;
	
	int idPartido;
	String nombreEquipoRival;
	int competicion;
	int nivel; //5-Muy bueno 4-Bueno 3-Equilibrado 2-Bajo 1-Muy bajo
	String fecha; //aún no me apaño muy bien con las fechas :(
	boolean esCompradorSocio = false;
	int precioEntradaBase;
	ArrayList<Zona> zonas = new ArrayList<Zona>();
	ArrayList<Entrada> entradas = new ArrayList<Entrada>();
	Zona zonaSeleccionada;
	
	public Partido(int idPartido, String nombreEquipoRival,int competicion, int nivel, String fecha) {

		this.idPartido = idPartido;
		this.nombreEquipoRival = nombreEquipoRival;
		this.competicion = competicion;
		this.nivel = nivel;
		this.fecha = fecha;
		
		switch (nivel) {
			case 1: 
				precioEntradaBase = 40;
			break;
			
			case 2: 
				precioEntradaBase = 50;
			break;
			
			case 3: 
				precioEntradaBase = 60;
			break;
			
			case 4: 
				precioEntradaBase = 70;
			break;
			
			case 5: 
				precioEntradaBase = 80;
			break;
		}		
		
	}

	public int getIdPartido() {
		return idPartido;
	}
	
	
    public void setIdPartido(int idPartido) { 
    	this.idPartido = idPartido;
    } 
	

	public String getNombreEquipoRival() {
		return nombreEquipoRival;
	}


	public String getFecha() {
		return fecha;
	}

	
	public boolean isEsCompradorSocio() {
		return esCompradorSocio;
	}


	public void setEsCompradorSocio(boolean esCompradorSocio) {
		this.esCompradorSocio = esCompradorSocio;
	}

	
	
	public int getPrecioEntradaBase() {
		return precioEntradaBase;
	}



	public void setPrecioEntradaBase(int precioEntradaBase) {
		this.precioEntradaBase = precioEntradaBase;
	}




	public Zona getZonaSeleccionada() {
		return zonaSeleccionada;
	}


	public void setZonaSeleccionada(Zona zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}
	

	public String getCompeticion() { //esto ha sido un pequeño apaño.. aunque se que está mal organizado
		
		String nombreCompeticion = null;
		
		switch (competicion) {
		
		case 0: nombreCompeticion = "LIGA";
			return nombreCompeticion;
		case 1: nombreCompeticion = "CHAMPIONS LEAGUE";
			return nombreCompeticion;
		case 2: nombreCompeticion = "COPA DEL REY";
			return nombreCompeticion;
		}
		return nombreCompeticion;
	}


	public void setCompeticion(int competicion) {
		this.competicion = competicion;
	}
	
	public void inicializarZonas() {
	    this.zonas = Conexion.cargarZonas();
	    if (this.zonas == null || this.zonas.isEmpty()) {
	        crearZonas();
	    }
	}


	public void crearZonas() {
		
		for(int i = 0; i <= Zona.VIP; i++) {
			
			Zona z = new Zona(i);
			
			this.zonas.add(z);
		}
		
		for(Zona z : zonas) {

			Conexion.insertarZona(z.getLugar(), z.getNombre(), z.getCapacidad(), z.getSuplementoZona(), z.getEntradasDisponibles());
		}
		
	}
	
	public Zona elegirZona() {
		
		JOptionPane.showMessageDialog(null, "A continuación vas a seleccionar la zona desde la cual vas a disfrutar el partido.\nRecuerde que dependiendo de la zona el precio puede variar.");
		
		int i = 0; //indice para recorrer la la zona
		String [] botones = {"Anterior", "Seleccionar", "Siguiente"};//botones para elegir zona
		
		while(i >= 0 && i < zonas.size()) {
			
			Zona zonaActual = zonas.get(i);
			int disponibles = Conexion.getDisponibles(idPartido, zonaActual.getLugar());
			
			int zonaElegida = JOptionPane.showOptionDialog(null, "Zona: " + zonaActual.getNombre() + "\nPrecio: " + (int) (zonaActual.suplementoZona * precioEntradaBase)
					+ "\nEntradas disponibles: " + disponibles, "Selecciona la zona donde quiere disfrutar del partido en el Benito Villamarin",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), botones, null);
			
			  if (zonaElegida == 0) { //zona anterior
		            if (i > 0) {
		                i--;
		            }
		        } else if (zonaElegida == 1) { // seleccionar zona
		        	
		        	if (disponibles > 0) {
		                precioEntradaBase = (int) (zonaActual.getSuplementoZona() * precioEntradaBase);
		                return zonaActual;
		            } else {
		                JOptionPane.showMessageDialog(null, "Lo sentimos, no quedan entradas en esta zona. Selecciona otra.");
		            }
		        	
		        } else if (zonaElegida == 2) { //zona siguiente
		            if (i < zonas.size() - 1) {
		                i++;
		            }
		        } else if (zonaElegida == JOptionPane.CLOSED_OPTION) {
		        	JOptionPane.showMessageDialog(null, "Gracias por la visita, le esperamos pronto.");
		        	System.exit(0);
		        }
		}
		
		return null;
		
	}

	
	public int comprarEntrada(){
		
		int disponibles = Conexion.getDisponibles(idPartido, zonaSeleccionada.getLugar());
		
		if (disponibles >= LIMITE_ENTRADAS) {
			
			String[] cantidad = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
			int numEntradas = JOptionPane.showOptionDialog(null, "Seleccione el número de entradas que desea, el máximo son 10 por usuario", "Real Betis Balompie",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), cantidad, null);
			
			numEntradas++;
			
			return numEntradas;
			
		} else {
			
			String [] cantidad = new String[disponibles];
			for(int i = 0; i < disponibles; i++) {
				cantidad[i] = String.valueOf(i + 1); //lo paso a String para que aparezca en opciones
			}
			
			int numEntradas = JOptionPane.showOptionDialog(null, "Seleccione el número de entradas que desea, el máximo son 10 por usuario", "Real Betis Balompie",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), cantidad, null);
			
			numEntradas++;
			
			return numEntradas;
			
		}		
	}
	
	public ArrayList<Entrada> generarEntradas(int numEntradas, Zona zonaSeleccionada) {
		
		int contadorEntradas = 1;
		ArrayList<Entrada> entradas = new ArrayList<Entrada>();
		
		String [] tipos = {"Normal - " + precioEntradaBase + "€", "Entrada infantil - " + (int)(precioEntradaBase * Entrada.DESCUENTO_INFANTIL) + "€",
				"Familia numerosa - " + (int)(precioEntradaBase * Entrada.DESCUENTO_FAMILIA_NUMEROSA) + "€", "Movilidad reducida - " + (int)(precioEntradaBase * Entrada.DESCUENTO_MOVILIDAD_REDUCIDA) + "€"};
		
		for(int i = 0; i < numEntradas; i++) {
			int tipoEntrada = JOptionPane.showOptionDialog(null, "Seleccione el tipo de entrada que desea comprar.\nEntrada nº" + (i + 1), "Real Betis Balompie",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), tipos, null);
			
			Entrada e = new Entrada(contadorEntradas, this,zonaSeleccionada);
			e.setZona(zonaSeleccionada);
			if(esCompradorSocio) {
				setEsCompradorSocio(true);
			}
			
			if (tipoEntrada == Entrada.ENTRADA_NORMAL) {
				
				
				e.precioFinalEntrada();
				entradas.add(e);
				
				
			} else if (tipoEntrada == Entrada.ENTRADA_INFANTIL) {
				
				e.setEntradaInfantil(true);
				e.precioFinalEntrada();
				entradas.add(e);
				
			} else if (tipoEntrada == Entrada.ENTRADA_FAMILIA_NUMEROSA) {
				
				e.setFamiliaNumerosa(true);
				e.precioFinalEntrada();
				entradas.add(e);
				
			} else if (tipoEntrada == Entrada.ENTRADA_MOVILIDAD_REDUCIDA) {
				
				e.setMovilidadReducida(true);
				e.precioFinalEntrada();
				entradas.add(e);
				
			} else if (tipoEntrada == JOptionPane.CLOSED_OPTION) {
				JOptionPane.showMessageDialog(null, "Gracias por la visita, le esperamos pronto.");
				System.exit(0);
	        }
			
			contadorEntradas++;
		}
		
		return entradas;
	}
	
	

	
	public void verPrecioFinal(ArrayList<Entrada> entradas) {
		
		int precioTotal = 0;
		
		String [] opciones = {"Confirmar pago", "Salir"};
		
		for (Entrada e : entradas) {
		        precioTotal += e.getPrecioFinal();
		}
		
		int opcion = JOptionPane.showOptionDialog(null, "El precio final es de " + precioTotal + "€", "Confirmación compra entradas", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.PLAIN_MESSAGE, null, opciones, null);
		
		if (opcion == 0) {
			
			JOptionPane.showMessageDialog(null, "¡Enhorabuena, disfrute del partido!");
			
			Conexion.restarDisponibles(this.getIdPartido(), zonaSeleccionada.getLugar(), entradas.size());
			int nextId = Conexion.getMaxEntradaId() + 1;
			
			for (Entrada e : entradas) {
				int id = nextId;
				e.setIdEntrada(id);
				Conexion.insertarEntrada(e.getIdEntrada(), this.getIdPartido(), this.getCompeticion(), this.zonaSeleccionada.getLugar(), precioTotal, e.getTipoEntrada());
				nextId++;
			}
			
			tickets(entradas);
		} else {
			JOptionPane.showMessageDialog(null, "¡Te esperamos pronto!");
			System.exit(0);
		}
	}
	
	
	public void tickets(ArrayList<Entrada> entradas) {
	    
	    for (Entrada e : entradas) {
	        CrearTicket ticket = new CrearTicket(e.partido.getNombreEquipoRival(), e.partido.getCompeticion(), e.getZona().nombre, e.getTipoEntrada(), isEsCompradorSocio(), e.getPrecioFinal());
	        ticket.generarTicket(e.getIdEntrada());
	    }
	}
	
	
}
