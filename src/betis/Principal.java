package betis;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.DayOfWeek;


public class Principal {

	public static void main(String[] args) {
		
		Conexion.crearTablas();
		
		JOptionPane.showMessageDialog(
			    null, 
			    "¡Bienvenido a la venta online de entradas del mejor equipo del mundo!", 
			    "Real Betis", 
			    JOptionPane.INFORMATION_MESSAGE, 
			    new ImageIcon("imagenes/Real-Betis.png")
			);
		
		ArrayList<Partido> partidos = new ArrayList<>();
		partidos = inicializarPartidos(partidos);
		ArrayList<Socio> socios = new ArrayList<>();
		socios = inicializarSocios();

		inicio(partidos, socios);

	}
	
	public static void inicio(ArrayList<Partido> partidos, ArrayList<Socio> socios) {
		
		
		Partido partidoSeleccionado;
		
		int competicion = seleccionarCompeticion();		
		partidoSeleccionado = mostrarPartidos(competicion, partidos, socios);
		partidoSeleccionado.inicializarZonas();
		
		Conexion.inicializarDisponibilidadPartido(partidoSeleccionado.getIdPartido());
		
		int socioClub = JOptionPane.showConfirmDialog(null, "¿Eres socio del club?", "Soci@", JOptionPane.YES_NO_OPTION);
		
		if(socioClub == JOptionPane.YES_OPTION) {
			
			partidoSeleccionado.setEsCompradorSocio(comprobarSocio(socios));
			
		} else {
			
			 int hacerseSocio = JOptionPane.showConfirmDialog(null, "¿Quieres hacerte socio por solo 200€?", "Alta socio", JOptionPane.YES_NO_OPTION);

		        if (hacerseSocio == JOptionPane.YES_OPTION) {
		        	
		        	crearSocio(socios);
		        	partidoSeleccionado.setEsCompradorSocio(comprobarSocio(socios));
		        	
		        } else {
		        	JOptionPane.showMessageDialog(null, "Continuará con la compra de las entradas con el precio base sin descuento de socio.");
		        }
		}

		
		if(partidoSeleccionado.isEsCompradorSocio()) {
			partidoSeleccionado.setPrecioEntradaBase( (int) (partidoSeleccionado.precioEntradaBase * Partido.DESCUENTO_SOCIO));
		}
		
		
		JOptionPane.showMessageDialog(null, "Has seleccionado el partido Real Betis vs: " + partidoSeleccionado.nombreEquipoRival + "\nPrecio base: " +
				partidoSeleccionado.precioEntradaBase + "€");
		
		partidoSeleccionado.inicializarZonas();
		partidoSeleccionado.setZonaSeleccionada(partidoSeleccionado.elegirZona());
		
		int numEntradas = partidoSeleccionado.comprarEntrada();
		
		partidoSeleccionado.entradas = partidoSeleccionado.generarEntradas(numEntradas, partidoSeleccionado.getZonaSeleccionada());
		
		partidoSeleccionado.verPrecioFinal(partidoSeleccionado.entradas);
		
	}
	
	public static ArrayList<Socio> inicializarSocios() {
	    // Primero intento cargar de la base de datos
	    ArrayList<Socio> socios = Conexion.cargarSocios();

	    // Si no hay ninguno en la base de datos, los genero 
	    if (socios.isEmpty()) {
	        socios = generarSocios(socios);
	    }

	    return socios;
	}
	
	
	
	public static ArrayList<Socio>  generarSocios(ArrayList<Socio> socios) { //creo una serie de socios por defecro para hacerlo más realista
	    String[] nombres = {
	        "Juan Perez", "Ana Lopez", "Carlos Sanchez", "Maria Torres",
	        "Luis Ramirez", "Carmen Fernandez", "Jose Martinez", "Laura Castillo",
	        "Pedro Gomez", "Lucia Morales", "Andres Herrera", "Sofia Dominguez",
	        "Miguel Vargas", "Paula Ortega", "Javier Navarro", "Elena Serrano",
	        "Antonio Delgado", "Raquel Cabrera", "Diego Ruiz", "Cristina Rios",
	        "Fernando Molina", "Patricia Blanco", "Adrian Castro", "Beatriz Vega",
	        "Oscar Romero", "Silvia Campos", "Ruben Alvarez", "Natalia Suarez",
	        "Sergio Gil", "Alba Herrera", "Hector Lozano", "Marta Aguirre",
	        "Victor Cordero", "Irene Marquez", "David Iglesias", "Angela Santos",
	        "Alejandro Nieto", "Julia Fuentes", "Pablo Crespo", "Clara Pastor",
	        "Manuel Cabrera", "Rocio Jimenez", "Daniel Cortes", "Eva Gallardo",
	        "Francisco Peña", "Noelia Serrano", "Guillermo Bravo", "Sara Leon",
	        "Jorge Medina", "Monica Paredes" };

	    for (int i = 0; i < nombres.length; i++) {
	        socios.add(new Socio(i + 1, nombres[i]));
	    }
	    
	    for(Socio s : socios) {
	    	Conexion.insertarSocio(s.getNumSocio(), s.getNombreCompleto(),s.getPassword());
	    }
	    
	    return socios;
	}
	
	
	
	public static boolean comprobarSocio(ArrayList<Socio> socios) {
		
		int intentos = 3;
		while (intentos > 0) {
		    try { // uso el try para asegurarme que introduce numeros en el número de socio
		        int numeroSocio = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número de socio:"));
		        String passwordSocio = JOptionPane.showInputDialog("Introduce la contraseña:");

			    for (Socio s : socios) {
			       	if (numeroSocio == s.getNumSocio() && passwordSocio.equals(s.getPassword())) {
			               JOptionPane.showMessageDialog(null, "¡Gracias por ser socio del mejor club del mundo!");
			               return true;
			           }
			    }
			    
			    intentos--;
			    JOptionPane.showMessageDialog(null, "Datos incorrectos. Te quedan " + intentos + " intento/s.");

			} catch (NumberFormatException e) {
			        intentos--;
			        JOptionPane.showMessageDialog(null, "Número de socio no válido. Te quedan " + intentos + " intento/s.");
			    }
		}
			
		return false;
		
	}
	
	
	public static void crearSocio(ArrayList<Socio> socios) {
		
		int numSocio = socios.size() + 1;
        String nombre = JOptionPane.showInputDialog("Introduce tu nombre y tu primer apellido:");
        
        JOptionPane.showMessageDialog(null, "Tu número de socio es: " + numSocio);
        JOptionPane.showMessageDialog(null, "Recuerda, la contraseña será las iniciales en mayúscula de tu nombre y tu apellido, y seguidamente el número de socio.");

        Socio nuevoSocio = new Socio(numSocio, nombre);
        socios.add(nuevoSocio);
        
        JOptionPane.showMessageDialog(null, "¡Felicidades! Ahora eres socio del Real Betis Balompié.");
        
        Conexion.insertarSocio(nuevoSocio.getNumSocio(), nuevoSocio.getNombreCompleto(), nuevoSocio.getPassword());
		
	}
	
	
	public static int seleccionarCompeticion() {
		
		String [] competiciones = { "Liga", "Champions League" , "Copa del Rey" };
		
		int competicion = JOptionPane.showOptionDialog(null, "A continuación elije la competición y el partido del que deseas comprar entradas.", "Competiciones",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), competiciones, null);
		
		if (competicion == JOptionPane.CLOSED_OPTION) {
			JOptionPane.showMessageDialog(null, "Gracias por la visita, le esperamos pronto.");
			System.exit(0);
		}
		
		return competicion;
		
	}

	public static ArrayList<Partido> generarPartidos(ArrayList<Partido> partidos) {
		
		int idPartido = 1;
		 		
		String[] equiposLiga = {"Athletic Club", "Real Sociedad", "Girona", "Real Madrid", "Espanyol",
                "Rayo Vallecano", "Cádiz", "Alavés", "Villarreal", "Barcelona",
                "Valencia", "Burgos CF", "Osasuna", "Mallorca", "Granada",
                "Las Palmas", "Celta de Vigo", "Levante", "Getafe", "Atlético de Madrid"};
 
		int [] niveles = {4, 4, 3, 5, 2,
           3, 1, 1, 3, 5,
           3, 2, 2, 2, 2,
           2, 3, 1, 3, 4};
		 
		 	// Fecha inicial (primer partido de liga)
		    LocalDate fecha = LocalDate.of(2025, 8, 16);

		    // Asegurar que sea sábado
		    if (fecha.getDayOfWeek() != DayOfWeek.SATURDAY) {
		        fecha = fecha.with(DayOfWeek.SATURDAY);
		    }

		    // Generar un partido de liga
		    for (int i = 0; i < equiposLiga.length; i++) {
		        partidos.add(new Partido(idPartido, equiposLiga[i], Partido.LIGA, niveles[i], fecha.toString()));
		        fecha = fecha.plusWeeks(2); // Salta al siguiente sábado que juega en casa
		        idPartido++;
		    }
		
		    //Partidos de Champions
		    partidos.add(new Partido(idPartido, "Manchester City", Partido.CHAMPIONS, 5, "2025-09-16"));
		    idPartido++;
		    partidos.add(new Partido(idPartido, "Borussia Dortmund", Partido.CHAMPIONS, 4, "2025-10-07"));
		    idPartido++;
		    partidos.add(new Partido(idPartido, "Ajax", Partido.CHAMPIONS, 3, "2025-10-28"));
		    idPartido++;
		    
		    //Partido de copa
		    partidos.add(new Partido(idPartido, "CD IBIZA", Partido.COPA_DEL_REY, 1, "2025-11-18"));
		    
		    for(Partido p : partidos) {
		    	Conexion.insertarPartido(p.getIdPartido(),p.getNombreEquipoRival(), p.competicion, p.nivel, p.getPrecioEntradaBase(), p.fecha);
		    }
		return partidos;
	}
	
	
	public static ArrayList<Partido> inicializarPartidos(ArrayList<Partido> partidos){
		
		partidos = Conexion.cargarPartidos(partidos);
		
		 if (partidos.isEmpty()) {
		        partidos = generarPartidos(partidos);
		    }

		    return partidos;
	}
	
	
	
	public static Partido mostrarPartidos(int competicion, ArrayList<Partido> partidos, ArrayList<Socio> socios) {
		
		
		String [] botones = {"Anterior", "Seleccionar", "Siguiente", "Seleccionar competición"};
		
		if (competicion == 0) {

		    // filtrar solo los de liga
		    ArrayList<Partido> equiposLiga = new ArrayList<>();
		    for (Partido p : partidos) {
		        if (p.competicion == Partido.LIGA) {
		            equiposLiga.add(p);
		        }
		    }

		    // índice para recorrer los partidos
		    int i = 0;

		    // mientras el índice esté dentro de la lista
		    while (i >= 0 && i < equiposLiga.size()) {
		        Partido partidoActual = equiposLiga.get(i);

		        // mostrar opción
		        int opcion = JOptionPane.showOptionDialog(null, "Selecciona el partido que desea ver:\n" + partidoActual.getNombreEquipoRival() + " - " + partidoActual.getFecha(),
		                "Selecciona partido", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), botones, null);

		        if (opcion == 0) { //partido anterior
		            if (i > 0) {
		                i--;
		            }
		        } else if (opcion == 1) { // seleccionar partido
		            return partidoActual;
		        } else if (opcion == 2) { //partido siguiente
		            if (i < equiposLiga.size() - 1) {
		                i++;
		            }
		        } else if (opcion == 3) {
		        	inicio(partidos,socios);	
		        } else if (opcion == JOptionPane.CLOSED_OPTION) {
		        	JOptionPane.showMessageDialog(null, "Gracias por la visita, le esperamos pronto.");
		        	System.exit(0);
		        }
		    }
			
		} else if (competicion == 1) {
			
			ArrayList<Partido> equiposChampions = new ArrayList<Partido>();
			
			 for (Partido p : partidos) {
			        if (p.competicion == Partido.CHAMPIONS) {
			            equiposChampions.add(p);
			        }
			    }

			    // índice para recorrer los partidos
			    int i = 0;

			    // mientras el índice esté dentro de la lista
			    while (i >= 0 && i < equiposChampions.size()) {
			        Partido partidoActual = equiposChampions.get(i);

			        // mostrar opción
			        int opcion = JOptionPane.showOptionDialog(null, "Selecciona el partido que desea ver:\n" + partidoActual.getNombreEquipoRival() + " - " + partidoActual.getFecha(),
			                "Selecciona partido", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), botones, null);

			        if (opcion == 0) { //partido anterior
			            if (i > 0) {
			                i--;
			            }
			        } else if (opcion == 1) { // seleccionar partido
			            return partidoActual;
			        } else if (opcion == 2) { //partido siguiente
			            if (i < equiposChampions.size() - 1) {
			                i++;
			            }
			        } else if (opcion == 3) {
			        	inicio(partidos, socios);	
			        } else if (opcion == JOptionPane.CLOSED_OPTION) {
			        	JOptionPane.showMessageDialog(null, "Gracias por la visita, le esperamos pronto.");
			        	System.exit(0);
			        }
			    }
		
		} else {
			
			ArrayList<Partido> equiposCopa = new ArrayList<Partido>();
			
			for (Partido p : partidos) {
		        if (p.competicion == Partido.COPA_DEL_REY) {
		            equiposCopa.add(p);
		        }
		    }

		    // índice para recorrer los partidos
		    int i = 0;

		    // mientras el índice esté dentro de la lista
		    while (i >= 0 && i < equiposCopa.size()) {
		        Partido partidoActual = equiposCopa.get(i);

		        // mostrar opción
		        int opcion = JOptionPane.showOptionDialog(null, "Selecciona el partido que desea ver:\n" + partidoActual.getNombreEquipoRival() + " - " + partidoActual.getFecha(),
		                "Selecciona partido", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("imagenes/icon.png"), botones, null);

		        if (opcion == 0) { //partido anterior
		            if (i > 0) {
		                i--;
		            }
		        } else if (opcion == 1) { // seleccionar partido
		            return partidoActual;
		        } else if (opcion == 2) { //partido siguiente
		            if (i < equiposCopa.size() - 1) {
		                i++;
		            }
		        } else if (opcion == 3) {
		        	inicio(partidos, socios);	
		        } else if (opcion == JOptionPane.CLOSED_OPTION) {
		        	JOptionPane.showMessageDialog(null, "Gracias por la visita, le esperamos pronto.");
		        	System.exit(0);
		        }
		    }			
		}
		
		return null;
		
	}	
	
}
