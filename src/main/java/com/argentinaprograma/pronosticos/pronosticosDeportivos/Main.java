package com.argentinaprograma.pronosticos.pronosticosDeportivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		Collection<Partido> partidos = new ArrayList<Partido>();
        
        Path pathResultados = Paths.get(args[0]);
        List<String> lineasResultados = null;
        
        try {
			lineasResultados = Files.readAllLines(pathResultados);
		} catch (IOException e) {
			System.out.println("No se pudo leer la linea de resultados...");
			System.out.println(e.getMessage());
			System.out.println(1);
		}
        
        boolean primera = true;
        
        for (String lineaResultado : lineasResultados) {
			if (primera) {
				primera = false;
			} else {
				String[] campos = lineaResultado.split(",");
				Equipo equipo1 = new Equipo(campos[0]);
				Equipo equipo2 = new Equipo(campos[3]);
				Partido partido = new Partido(equipo1, equipo2);
				partido.setGolesEquipo1(Integer.parseInt(campos[1]));
				partido.setGolesEquipo2(Integer.parseInt(campos[2]));
				partidos.add(partido);
			}
		}
        
		int puntos = 0;
		
		Path pathPronostico = Paths.get(args[1]);
		List<String> lineasPronostico = null;
		try {
			lineasPronostico = Files.readAllLines(pathPronostico);
		} catch (IOException e) {
			System.out.println("No se pudo leer la linea de pronosticos...");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		primera = true;
		
		for (String lineaPronostico : lineasPronostico) {
			if (primera) {
				primera = false;
			} else {
				String[] campos = lineaPronostico.split(",");
				Equipo equipo1 = new Equipo(campos[0]);
				Equipo equipo2 = new Equipo(campos[4]);
				Partido partido = null;

				for (Partido partidoCol : partidos) {
					if (partidoCol.getEquipo1().getNombre(
							).equals(equipo1.getNombre())
							&& partidoCol.getEquipo2().getNombre(
									).equals(equipo2.getNombre())) {
						
						partido = partidoCol;
						
					}
				}
				
				Equipo equipo = null;
				ResultadoEnum resultado = null;
				if("X".equals(campos[1])) {
					equipo = equipo1;
					resultado = ResultadoEnum.GANADOR;
				}
				if("X".equals(campos[2])) {
					equipo = equipo1;
					resultado = ResultadoEnum.EMPATE;
				}
				if("X".equals(campos[3])) {
					equipo = equipo1;
					resultado = ResultadoEnum.PERDEDOR;
				}
				
				Pronostico pronostico = new Pronostico(partido, equipo, resultado);
				puntos += pronostico.puntos();
			}
		}
        
        System.out.println("los puntos obtenidos por el usuario final fueron: ");
        System.out.println(puntos);

	}

}
