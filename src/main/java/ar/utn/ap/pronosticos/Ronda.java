package ar.utn.ap.pronosticos;

import java.util.Collection;
import java.util.List;

public class Ronda {
	
	private String nro;
	private Collection<Partido> partidos;

	public Ronda(String nro, Collection<Partido> partidos) {
		super();
		this.nro = nro;
		this.partidos = partidos;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	public Collection<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partidos = partidos;
	}


}
