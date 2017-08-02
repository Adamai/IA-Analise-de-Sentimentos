import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.math.*;

public class NaiveBayes {

	// Guarda a média da palvra que aparece naquela posição
	public Map<Integer, Double> positivosMedia = new HashMap<>();

	// Guarda o desvio da palavra que aparece naquela posição
	public Map<Integer, Double> positivosDev = new HashMap<>();

	// Lista com os valores para o calculo do desvio
	private List<ArrayList<Double>> listaValoresPositivos = new ArrayList<>();

	// Guarda a média da palavra que aparece naquela posição
	public Map<Integer, Double> negativosMedia = new HashMap<>();

	// Guarda o desvio da palavra que aparece naquela posição
	public Map<Integer, Double> negativosDev = new HashMap<>();

	// Lista com os valores para o calculo do desvio
	private List<ArrayList<Double>> listaValoresNegativos = new ArrayList<>();

	private int totalPositivo = 0;// total de exemplos positivos
	private int totalNegativo = 0;// total de exemplos negativos

	public NaiveBayes(int tamanho) {
		for (int i = 0; i < tamanho; i++) {
			listaValoresPositivos.add(new ArrayList<Double>());
			listaValoresNegativos.add(new ArrayList<Double>());
		}
	}

	private double Media(int total, List<Double> valores) {
		double soma = 0;
		for (Double valor : valores) {
			soma += valor;
		}
		return soma / total;
	}

	private double Desvio(List<Double> valores, double media, int total) {
		double temp = 0;
		for (Double valor : valores) {
			temp += ((valor - media) * (valor - media));
		}
		double variancia = temp / (total - 1);
		double desvio = Math.sqrt(variancia);
		return desvio;
	}

	private double Densidade(int palavra, double valor, String classific) {
		Map<Integer, Double> auxMedia;
		Map<Integer, Double> auxDev;
		if (classific.equals("positivo")) {
			auxMedia = positivosMedia;
			auxDev = positivosDev;
		} else {
			auxMedia = negativosMedia;
			auxDev = negativosDev;
		}

		double densidade = 0.0;

		double dividendo = (Math.pow(Math.E,
				-(Math.pow(valor - auxMedia.get(palavra), 2)) / (2 * Math.pow(auxDev.get(palavra), 2))));

		double divisor = (auxDev.get(palavra) * Math.sqrt(2 * Math.PI));

		if (divisor != 0)
			densidade = dividendo / divisor;

		return densidade;
	}

	public void treinaPositivo(String[] linha) {
		totalPositivo++;
		for (int i = 1; i < linha.length; i++) { // i = 1 pq o primeiro atributo
													// é o nome do doc
			double valor = Double.parseDouble(linha[i]);
			listaValoresPositivos.get(i).add(valor);
		}
	}

	public void treinaNegativo(String[] linha) {
		totalNegativo++;
		for (int i = 1; i < linha.length; i++) { // i = 1 pq o primeiro atributo
													// é o nome do doc
			double valor = Double.parseDouble(linha[i]);
			listaValoresNegativos.get(i).add(valor);
		}
	}

	public void aprender() {
		double mediaPos;
		double mediaNeg;
		double desvioPos;
		double desvioNeg;

		for (int i = 1; i < listaValoresNegativos.size(); i++) {
			mediaPos = Media(totalPositivo, listaValoresPositivos.get(i));
			positivosMedia.put(i, mediaPos);
			desvioPos = Desvio(listaValoresPositivos.get(i), mediaPos, totalPositivo);
			positivosDev.put(i, desvioPos);

			mediaNeg = Media(totalNegativo, listaValoresNegativos.get(i));
			negativosMedia.put(i, mediaNeg);
			desvioNeg = Desvio(listaValoresNegativos.get(i), mediaNeg, totalNegativo);
			negativosDev.put(i, desvioNeg);
		}
	}

	public String classificar(String[] texto) {
		double scorePos = 0.0;
		double scoreNeg = 0.0;

		for (int i = 1; i < texto.length; i++) {
			scorePos += Densidade(i, Double.parseDouble(texto[i]), "positivo");
			scoreNeg += Densidade(i, Double.parseDouble(texto[i]), "negativo");
		}

		scorePos = scorePos + totalPositivo / (totalPositivo + totalNegativo);
		scoreNeg = scoreNeg + totalNegativo / (totalPositivo + totalNegativo);
		System.out.println("score pos: " + scorePos);
		System.out.println("score neg: " + scoreNeg);

		if (scorePos >= scoreNeg)
			return "Positivo";
		else
			return "Negativo";
	}

}
