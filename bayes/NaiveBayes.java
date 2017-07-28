import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NaiveBayes {

	private Map<String, Integer> positivos = new HashMap<>();
	private Map<String, Integer> negativos = new HashMap<>();
	private double totalPositivo = 0;
	private double totalNegativo = 0;

	public NaiveBayes() {
	}

	public void treina(String[] texto, String classe) {
		Set<String> adicionado = new HashSet<>();
		for (int i = 0; i < texto.length; i++) {
			if (classe.equals("positivo")) {
				if (positivos.containsKey(texto[i]) && !adicionado.contains(texto[i])) {
					int aux = positivos.get(texto[i]);
					positivos.put(texto[i], aux + 1);
				} else
					positivos.put(texto[i], 1);

				adicionado.add(texto[i]);
			} else {
				if (negativos.containsKey(texto[i]) && !adicionado.contains(texto[i])) {
					int aux = negativos.get(texto[i]);
					positivos.put(texto[i], aux + 1);
				} else
					negativos.put(texto[i], 1);

				adicionado.add(texto[i]);
			}
		}
		if (classe.equals("positivo"))
			totalPositivo++;
		else
			totalNegativo++;
	}

	public void treina2(String[] texto, String categoria) {
		Map<String, Integer> auxiliar;
		Set<String> adicionado = new HashSet<>();
		if (categoria.equals("positivo"))
			auxiliar = positivos;
		else
			auxiliar = negativos;

		for (int i = 0; i < texto.length; i++) {
			if (auxiliar.containsKey(texto[i]) && !adicionado.contains(texto[i])) {
				int aux = auxiliar.get(texto[i]);
				auxiliar.put(texto[i], aux + 1);
			} else
				auxiliar.put(texto[i], i);

			adicionado.add(texto[i]);
			if (categoria.equals("positivo"))
				totalPositivo++;
			else
				totalNegativo++;
		}

	}

	public String classifica(String[] texto) {
		double scorePositivo = 1;
		double scoreNegativo = 1;
		for (int i = 0; i < texto.length; i++) {
			if (positivos.containsKey(texto[i]))
				scorePositivo += positivos.get(texto[i])/totalPositivo;
			if (negativos.containsKey(texto[i]))
				scoreNegativo += negativos.get(texto[i])/totalNegativo;
		}

		System.out.println("scoreP = " + scorePositivo + "|" + " scoreN = " + scoreNegativo);
		if (scorePositivo >= scoreNegativo)
			return "Positivo";
		else
			return "Negativo";

	}

}
