package betis;

public class Socio {

	String nombreCompleto;
	int numSocio;
	String password;
	
	
	public Socio(int numSocio, String nombreCompleto) {
        this.numSocio = numSocio;
        this.nombreCompleto = nombreCompleto;
        this.password = generarPassword(nombreCompleto, numSocio);
    }

    private String generarPassword(String nombreCompleto, int numSocio) {
        // coger iniciales del nombre completo
        String[] partes = nombreCompleto.split(" "); //para que genere dos strings de nombre y apellido
        StringBuilder iniciales = new StringBuilder(); //para juntar Strings automaticamente
        for (String parte : partes) {
            if (!parte.isEmpty()) { //comprueba que no han dado dos espacios o un tabulador
                iniciales.append(Character.toUpperCase(parte.charAt(0))); //pone las iniciales en mayusculas
            }
        }
        return iniciales.toString() + numSocio;
    }

    public int getNumSocio() {
        return numSocio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getPassword() {
        return password;
    }
}
