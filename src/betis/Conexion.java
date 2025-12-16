package betis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Conexion {

	
	public static Connection getConexion() {
		Connection conexion = null;
		String url = "jdbc:sqlite:Betis.db";
		
		try {
			
			conexion = DriverManager.getConnection(url);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al conectarnos a la base de datos");
		}
		
		return conexion;
	}
	
	
	public static void crearTablas() {
		
		String sqlSocio = """
		        CREATE TABLE IF NOT EXISTS Socio (
		            NumSocio INTEGER PRIMARY KEY AUTOINCREMENT,
		            Nombre   TEXT NOT NULL,
		            Password TEXT NOT NULL
		        );
		    """;

		    String sqlPartido = """
		        CREATE TABLE IF NOT EXISTS Partido (
		            Id         INTEGER PRIMARY KEY, 
		            Rival      TEXT NOT NULL,
		            Competicion INTEGER NOT NULL,
		            Nivel      INTEGER NOT NULL,
		            PrecioBase INTEGER NOT NULL,
		            Fecha      TEXT NOT NULL
		        );
		    """;

		    String sqlZona = """
		        CREATE TABLE IF NOT EXISTS Zona (
		            Lugar              INTEGER PRIMARY KEY,
		            Nombre             TEXT NOT NULL,
		            Capacidad          INTEGER NOT NULL,
		            Suplemento         REAL NOT NULL,
		            EntradasDisponibles INTEGER NOT NULL
		        );
		    """;

		    String sqlPartidoZona = """
		    		CREATE TABLE IF NOT EXISTS PartidoZona (
						  Partido     INTEGER NOT NULL,
						  Zona        INTEGER NOT NULL,
						  Disponibles INTEGER NOT NULL,
						  PRIMARY KEY (Partido, Zona),
						  FOREIGN KEY (Partido) REFERENCES Partido(Id),
						  FOREIGN KEY (Zona)     REFERENCES Zona(Lugar)
						);
		    		""";
		    
		    
		    String sqlEntrada = """
		        CREATE TABLE IF NOT EXISTS Entrada (
		            idEntrada    INTEGER PRIMARY KEY,
		            Partido      INTEGER NOT NULL,
		            Competicion  TEXT NOT NULL,
		            Zona         INTEGER NOT NULL,
		            PrecioFinal  INTEGER NOT NULL,
		            Tipo         TEXT NOT NULL,
		            FOREIGN KEY (Partido) REFERENCES Partido(Id),
		            FOREIGN KEY (Zona) REFERENCES Zona(Lugar)
		        );
		    """;

		    
		    try(Connection con = getConexion();
		    	Statement st = con.createStatement()){
		    	
		    	st.executeUpdate(sqlSocio);
		    	st.executeUpdate(sqlPartido);
		        st.executeUpdate(sqlZona);
		        st.executeUpdate(sqlPartidoZona);
		        st.executeUpdate(sqlEntrada);
		    	
		    } catch (Exception e) {
		    	JOptionPane.showMessageDialog(null, "Error al crear las tablas.");
		    }
		
	}
	
	public static void insertarSocio(int numSocio, String nombre, String password) {
		
		String sql = "Insert into Socio(NumSocio, Nombre, Password) values (?,?,?)";
		
		try (Connection con = getConexion(); //Informandome he visto que es mejor así para que se cierren las conexiones automaticamente
			 PreparedStatement ps = con.prepareStatement(sql)){
			
			ps.setInt(1, numSocio);
			ps.setString(2, nombre);
			ps.setString(3, password);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error al insertar socio");
			
		}
	}
	
	
	public static ArrayList<Socio> cargarSocios() {
	    ArrayList<Socio> socios = new ArrayList<>();
	    String sql = "SELECT NumSocio, Nombre, Password FROM Socio";
	    
	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            int numSocio = rs.getInt("NumSocio");
	            String nombre = rs.getString("Nombre");
	            socios.add(new Socio(numSocio, nombre));
	        }
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error al cargar los socios");
	    }
	    
	    return socios;
	}
	
	
	public static void insertarPartido(int idPartido, String rival, int competicion, int nivel, int precioBase, String fecha) {
	    String sql = "INSERT INTO Partido (Id, Rival, Competicion, Nivel, PrecioBase, Fecha) VALUES ( ?, ?, ?, ?, ?, ?)";
	    
	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	    	ps.setInt(1, idPartido);
	        ps.setString(2, rival);
	        ps.setInt(3, competicion);
	        ps.setInt(4, nivel);
	        ps.setInt(5, precioBase);
	        ps.setString(6, fecha); 
	        
	        ps.executeUpdate();
	        
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error al insertar un partido");
	    }
	}

	
	public static ArrayList<Partido> cargarPartidos(ArrayList<Partido> partidos) {
	    
	    String sql = "SELECT * FROM Partido";

	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	        	int idPartido = rs.getInt("Id");
	            String rival = rs.getString("Rival");
	            int competicion = rs.getInt("Competicion");
	            int nivel = rs.getInt("Nivel");
	            String fecha = rs.getString("Fecha");

	            partidos.add(new Partido(idPartido, rival, competicion, nivel, fecha));
	        }

	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error al cargar un partido");
	    }
	    return partidos;
	}
	
	
	
	public static void insertarZona(int lugar, String nombre, int capacidad, double suplemento, int entradasDisponibles) {
	    String sql = "INSERT INTO Zona (Lugar, Nombre, Capacidad, Suplemento, EntradasDisponibles) VALUES (?, ?, ?, ?, ?)";
	    
	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, lugar);
	        ps.setString(2, nombre);
	        ps.setInt(3, capacidad);
	        ps.setDouble(4, suplemento);
	        ps.setInt(5, entradasDisponibles);
	        
	        ps.executeUpdate();
	        
	        
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error al insertar una zona");
	    }
	}
	
	
	public static ArrayList<Zona> cargarZonas() {
	    ArrayList<Zona> zonas = new ArrayList<>();
	    String sql = "SELECT * FROM Zona";

	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            int lugar = rs.getInt("Lugar");
	            String nombre = rs.getString("Nombre");
	            int capacidad = rs.getInt("Capacidad");
	            int disponibles = rs.getInt("EntradasDisponibles");
	            double precio = rs.getDouble("Suplemento");

	            zonas.add(new Zona(lugar, nombre, capacidad, disponibles, precio));
	        }

	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error al cargar una zona");
	    }
	    return zonas;
	}
	
	
	
	public static void inicializarDisponibilidadPartido(int idPartido) {
	    String sql = """
	        INSERT OR IGNORE INTO PartidoZona (Partido, Zona, Disponibles)
	        SELECT ?, z.Lugar, z.Capacidad
	        FROM Zona z
	        """;
	    
	    try (Connection con = getConexion();
	        PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, idPartido);
	        ps.executeUpdate();
	        
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error al cargar la disponibilidad de las entradas");
	    }
	}
	
	
	
	public static int getDisponibles(int partidoId, int zonaLugar) {
	    String sql = "SELECT Disponibles FROM PartidoZona WHERE Partido = ? AND Zona = ?";
	    
	    try (Connection con = getConexion();
	            PreparedStatement ps = con.prepareStatement(sql)) {

	           ps.setInt(1, partidoId);
	           ps.setInt(2, zonaLugar);

	           try (ResultSet rs = ps.executeQuery()) {
	               if (rs.next()) {
	                   return rs.getInt(1);
	               }
	           }
	        
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar la disponibilidad de las entradas");;
	    }
	    return 0;
	}
	
	
	
	public static void insertarEntrada(int idEntrada, int idPartido,String competicion, int idZona, int precioFinal, String tipo) {
	    String sql = "INSERT INTO Entrada (idEntrada, Partido, Competicion, Zona, PrecioFinal, Tipo) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	    	ps.setInt(1, idEntrada);
	    	ps.setInt(2, idPartido);
	        ps.setString(3, competicion);
	        ps.setInt(4, idZona);
	        ps.setInt(5, precioFinal);
	        ps.setString(6, tipo);
	        
	        ps.executeUpdate();
	        
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "Error al insertar una entrada");
	    }
	}
	
	
	public static void restarDisponibles(int partidoId, int zonaLugar, int cantidad) {
	    String sql = """
	        UPDATE PartidoZona
	        SET Disponibles = Disponibles - ?
	        WHERE Partido = ? AND Zona = ?
	        """;

	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, cantidad);
	        ps.setInt(2, partidoId);
	        ps.setInt(3, zonaLugar);

	        ps.executeUpdate();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al realizar la compra");;
	    }
	}
	
	public static ArrayList<Entrada> cargarEntradas(ArrayList<Partido> partidos, ArrayList<Zona> zonas) {
		ArrayList<Entrada> entradas = new ArrayList<>();

	    String sql = "SELECT idEntrada, Partido, Zona, PrecioFinal, Tipo FROM Entrada";

	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            int idEntrada = rs.getInt("idEntrada");
	            int partidoId = rs.getInt("Partido");
	            int zonaLugar = rs.getInt("Zona");

	            Partido partidoEncontrado = null; //con esto encuentro el partido para poder pasarselo al constructor
	            for (Partido p : partidos) {
	                if (p.getIdPartido() == partidoId) {
	                    partidoEncontrado = p;
	                    break;
	                }
	            }

	            Zona zonaEncontrada = null; //con esto encuentro la zona para poder pasarsela al constructor
	            for (Zona z : zonas) {
	                if (z.getLugar() == zonaLugar) {
	                    zonaEncontrada = z;
	                    break;
	                }
	            }

	            if (partidoEncontrado != null && zonaEncontrada != null) {
	                Entrada e = new Entrada(idEntrada, partidoEncontrado, zonaEncontrada);

	                 e.setPrecioFinal(rs.getInt("PrecioFinal"));
	                 e.setTipoEntrada(rs.getString("Tipo"));

	                entradas.add(e);
	            }
	        }

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar las entradas");
	    }

	    return entradas;
	}
	
	
	public static int getMaxEntradaId() {
	    String sql = "SELECT MAX(idEntrada) AS maxId FROM Entrada";
	    try (Connection con = getConexion();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	    	
	        if (rs.next()) return rs.getInt("maxId");
	        
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al cargar las entradas");;
	    }
	    return 0;
	}
	
}
