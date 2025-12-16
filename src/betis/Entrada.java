package betis;



public class Entrada {

	public static final double DESCUENTO_INFANTIL = 0.6;
	public static final double DESCUENTO_FAMILIA_NUMEROSA = 0.7;
	public static final double DESCUENTO_MOVILIDAD_REDUCIDA = 0.5;

	
	public static final int ENTRADA_NORMAL = 0;
	public static final int ENTRADA_INFANTIL = 1;
	public static final int ENTRADA_FAMILIA_NUMEROSA = 2;
	public static final int ENTRADA_MOVILIDAD_REDUCIDA = 3;

	int idEntrada;
	Partido partido;
	Zona zona;
	boolean esEntradaInfantil, esFamiliaNumerosa, esMovilidadReducida;
	int precioFinal;
	CrearTicket ticket;
	
	public Entrada(int idEntrada, Partido partido, Zona zona) {
		
		this.idEntrada = idEntrada;
		this.partido = partido;
		this.zona = zona;
		this.esEntradaInfantil = false;
		this.esFamiliaNumerosa = false;
		this.esMovilidadReducida = false;
	}
	
	public int precioFinalEntrada() {
	
		if (esEntradaInfantil) {
			precioFinal =  (int) (partido.precioEntradaBase * DESCUENTO_INFANTIL);
		} else if (esFamiliaNumerosa) {
			precioFinal = (int) (partido.precioEntradaBase * DESCUENTO_FAMILIA_NUMEROSA);
		} else if (esMovilidadReducida) {
			precioFinal = (int) (partido.precioEntradaBase * DESCUENTO_MOVILIDAD_REDUCIDA);
		} else {
			precioFinal = partido.precioEntradaBase;
		}
		
		return precioFinal;
	}
	

	public int getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(int idEntrada) {
		this.idEntrada = idEntrada;
	}

	public int getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(int precioFinal) {
		this.precioFinal = precioFinal;
	}


	public void setEntradaInfantil(boolean entradaInfantil) {
		this.esEntradaInfantil = entradaInfantil;
	}


	public void setFamiliaNumerosa(boolean familiaNumerosa) {
		this.esFamiliaNumerosa = familiaNumerosa;
	}


	public void setMovilidadReducida(boolean movilidadReducida) {
		this.esMovilidadReducida = movilidadReducida;
	}


	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}
	
	public void setTipoEntrada(String tipoEntrada) {
		
		if (tipoEntrada.equalsIgnoreCase("Infantil")) {
			this.esEntradaInfantil = true;
		} else if (tipoEntrada.equalsIgnoreCase("Familia numerosa")) {
			this.esFamiliaNumerosa = true;
		} else if (tipoEntrada.equalsIgnoreCase("Movilidad reducida")) {
			this.esMovilidadReducida = true;
		}
		
	}
	
	public String getTipoEntrada() {
		
		String tipo = "Normal";
		
		if (esEntradaInfantil) {
			tipo = "Infantil";
		} else if (esFamiliaNumerosa) {
			tipo = "Familia numerosa";
		} else if (esMovilidadReducida) {
			tipo = "Movilidad reducida";
		} 
		
		return tipo;
	}
	
}
