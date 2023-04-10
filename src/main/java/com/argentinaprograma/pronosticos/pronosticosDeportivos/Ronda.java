package com.argentinaprograma.pronosticos.pronosticosDeportivos;

import java.util.List;

public class Ronda {
	private String nro;
	private List<Partido> partidos;
	
	public Ronda(String nro, List<Partido> partidos) {
		this.nro = nro;
		this.partidos = partidos;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partidos = partidos;
	}
	
	//public int getTotalPuntosEquipo(Equipo equipo) {
	//	int totalPuntos = 0;
	//	
	//	for (Partido partido : partidos) {
	//		if (partido.getEquipo1().equals(equipo)) {
	//			totalPuntos += partido.puntos();
	//		} else if (partido.getEquipo2().equals(equipo)) {
	//			totalPuntos += partido.puntos();
	//		}
	//	}
	//	return totalPuntos;
	//}
	
}
