package ar.utn.ap.pronosticos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MainTP {

	public static void main(String[] args) {
		Collection<Ronda> rondas = new ArrayList<Ronda>();
		Collection<Partido> partidos = new ArrayList<Partido>();
		
		Path pathResultados = Paths.get("src/test/resources/resultados_test2.csv");
		List<String> lineasResultados = null;
		
		try {
			lineasResultados = Files.readAllLines(pathResultados);
		} catch (IOException e) {
			System.out.println("No se pudo leer la linea de resultados...");
			System.out.println(e.getMessage());
			System.exit(1);
		}

		boolean primera = true;
		String numeroRonda = null;
		
		for (String lineaResultado : lineasResultados) {
			if (primera) {
				primera = false;
			} else {
				// Argentina,1,2,Arabia Saudita
				String[] campos = lineaResultado.split(",");
				String nuevaRonda = campos[0];
				if (!nuevaRonda.equals(numeroRonda)) {
					if (numeroRonda != null) {
						Ronda ronda = new Ronda(numeroRonda, partidos);
						rondas.add(ronda);
						partidos = new ArrayList<Partido>();
					}
					numeroRonda = nuevaRonda;
				}
				numeroRonda = campos[0];
				Equipo equipo1 = new Equipo(campos[1]);
				Equipo equipo2 = new Equipo(campos[4]);
				Partido partido = new Partido(equipo1, equipo2);
				partido.setGolesEq1(Integer.parseInt(campos[2]));
				partido.setGolesEq2(Integer.parseInt(campos[3]));
				partidos.add(partido);
			}
		}
		
		Ronda ronda = new Ronda(numeroRonda, partidos);
		rondas.add(ronda);

		// Leer pronostico
		
		Path pathPronostico = Paths.get("src/test/resources/pronostico_test2.csv");
		List<String> lineasPronostico = null;
		try {
			lineasPronostico = Files.readAllLines(pathPronostico);
		} catch (IOException e) {
			System.out.println("No se pudo leer la linea de pronosticos...");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		Map<String, List<Integer>> puntajesUsuarios = new HashMap<>();
		primera = true;
		String nombreUsuario = null;
		
		for (String lineaPronostico : lineasPronostico) {
			if (primera) {
				primera = false;
			} else {
				String[] campos = lineaPronostico.split(",");
				nombreUsuario = campos[0];
				Equipo equipo1 = new Equipo(campos[1]);
				Equipo equipo2 = new Equipo(campos[5]);
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
				EnumResultado resultado = null;
				if("X".equals(campos[2])) {
					equipo = equipo1;
					resultado = EnumResultado.GANADOR;
				}
				if("X".equals(campos[3])) {
					equipo = equipo1;
					resultado = EnumResultado.EMPATE;
				}
				if("X".equals(campos[4])) {
					equipo = equipo1;
					resultado = EnumResultado.PERDEDOR;
				}
				Pronostico pronostico = new Pronostico(partido, equipo, resultado);
				int puntos = pronostico.puntos();
				
				List<Integer> puntajes = puntajesUsuarios.get(nombreUsuario);
				if(puntajes == null) {
					puntajes = new ArrayList<>();
					puntajesUsuarios.put(nombreUsuario, puntajes);
				}
				puntajes.add(puntos);
			}
		}
		
		for (Map.Entry<String, List<Integer>> puntajeUsuario : puntajesUsuarios.entrySet()) {
			nombreUsuario = puntajeUsuario.getKey();
			List<Integer> puntajes = puntajeUsuario.getValue();
			int puntosTotales = 0;
			for (int puntos : puntajes) {
				puntosTotales += puntos;
			}
			System.out.println(nombreUsuario + ": " + puntosTotales + " puntos");
		}
	}
}
