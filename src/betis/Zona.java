package betis;


public class Zona {

	public static final int FONDO_SUR = 0;
	public static final int FONDO_SUR_ANFITEATRO = 1;
	public static final int FONDO_NORTE = 2;
	public static final int FONDO_NORTE_ANFITEATRO = 3;
	public static final int LATERAL = 4;
	public static final int LATERAL_ANFITEATRO = 5;
	public static final int TRIBUNA = 6;
	public static final int TRIBUNA_ANFITEATRO = 7;
	public static final int CURVA = 8;
	public static final int CURVA_ANFITEATRO = 9;
	public static final int VIP = 10;
	
	
	int lugar;
	String nombre;
	int capacidad;
	double suplementoZona;
	int entradasDisponibles;
	
	public Zona(int lugar) {
		
		this.lugar = lugar;
		
		switch(lugar) {
			case FONDO_SUR:
				nombre = "FONDO_SUR";
				capacidad = 1000;
				suplementoZona = 1;
				entradasDisponibles = 1000;
			break;
			
			case FONDO_SUR_ANFITEATRO:
				nombre = "FONDO_SUR_ANFITEATRO";
				capacidad = 4000;
				suplementoZona = 1.2;
				entradasDisponibles = 4000;
			break;
			
			case FONDO_NORTE:
				nombre = "FONDO_NORTE";
				capacidad = 3000;
				suplementoZona = 1.3;
				entradasDisponibles = 3000;
			break;
			
			case FONDO_NORTE_ANFITEATRO:
				nombre = "FONDO_NORTE_ANFITEATRO";
				capacidad = 4000;
				suplementoZona = 1.2;
				entradasDisponibles = 4000;
			break;
			
			case LATERAL:
				nombre = "LATERAL";
				capacidad = 7000;
				suplementoZona = 1.4;
				entradasDisponibles = 7000;
			break;
			
			case LATERAL_ANFITEATRO:
				nombre = "LATERAL_ANFITEATRO";
				capacidad = 9000;
				suplementoZona = 1.3;
				entradasDisponibles = 9000;
			break;
			
			case TRIBUNA:
				nombre = "TRIBUNA";
				capacidad = 6500;
				suplementoZona = 1.6;
				entradasDisponibles = 6500;
			break;
			
			case TRIBUNA_ANFITEATRO:
				nombre = "TRIBUNA_ANFITEATRO";
				capacidad = 9000;
				suplementoZona = 1.55;
				entradasDisponibles = 9000;
			break;
			
			case CURVA:
				nombre = "CURVA";
				capacidad = 1000;
				suplementoZona = 1.2;
				entradasDisponibles = 1000;
			break;
			
			case CURVA_ANFITEATRO:
				nombre = "CURVA_ANFITEATRO";
				capacidad = 1000;
				suplementoZona = 1.25;
				entradasDisponibles = 1000;
			break;
			
			case VIP:
				nombre = "VIP";
				capacidad = 20;
				suplementoZona = 2;
				entradasDisponibles = 20;
			break;
		}
		
	}
	
	
	public Zona(int idZona, String nombre, int capacidad, int entradasDisponibles, double suplementoZona) {
	    this.lugar = idZona;
	    this.nombre = nombre;
	    this.capacidad = capacidad;
	    this.entradasDisponibles = entradasDisponibles;
	    this.suplementoZona = suplementoZona;
	}


	public int getLugar() {
		return lugar;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


	public void setLugar(int lugar) {
		this.lugar = lugar;
	}


	public double getSuplementoZona() {
		return suplementoZona;
	}


	public void setSuplementoZona(double suplementoZona) {
		this.suplementoZona = suplementoZona;
	}


	public String getNombre() {
		return nombre;
	}


	public int getEntradasDisponibles() {
		return entradasDisponibles;
	}


	public void setEntradasDisponibles(int entradasDisponibles) {
		this.entradasDisponibles = entradasDisponibles;
	}	
	
	
}
