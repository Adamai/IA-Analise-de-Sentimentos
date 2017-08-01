import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.math.*;

public class NaiveBayes {

	// Guarda a média da palvra que aparece naquela posição
	private Map<Integer, Double> positivosMedia = new HashMap<>();

	// Guarda o desvio da palavra que aparece naquela posição
	private Map<Integer, Double> positivosDev = new HashMap<>();

	// Lista com os valores para o calculo do desvio
	private List<ArrayList<Double>> listaValoresPositivos = new ArrayList<>();

	// Guarda a média da palavra que aparece naquela posição
	private Map<Integer, Double> negativosMedia = new HashMap<>();

	// Guarda o desvio da palavra que aparece naquela posição
	private Map<Integer, Double> negativosDev = new HashMap<>();

	// Lista com os valores para o calculo do desvio
	private List<ArrayList<Double>> listaValoresNegativos = new ArrayList<>();

	private int totalPositivo = 0;// total de exemplos positivos
	private int totalNegativo = 0;// total de exemplos negativos

	public NaiveBayes(int tamanho) {
		// inicializa as médias e os desvios como 0
		for (int i = 0; i < tamanho; i++) {
			positivosMedia.put(i, 0.0);
			positivosDev.put(i, 0.0);

			negativosMedia.put(i, 0.0);
			negativosDev.put(i, 0.0);

			listaValoresPositivos.add(new ArrayList<Double>());
			listaValoresNegativos.add(new ArrayList<Double>());
		}
	}

	private double Media(double novoValor, double mediaAtual, int total) {
		double media = (novoValor + mediaAtual) / total;
		return media;
	}

	private double Desvio(List<Double> valores, double media, int total) {
		double temp = 0;
		for (Double valor : valores) {
			temp += (valor - media) * (valor - media);
		}
		double variancia = temp / (total);
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
			double media = positivosMedia.get(i);
			double novaMedia = Media(valor, media, totalPositivo);
			positivosMedia.put(i, novaMedia);

			listaValoresPositivos.get(i).add(valor);
			double desvio = Desvio(listaValoresPositivos.get(i), novaMedia, totalPositivo);
			positivosDev.put(i, desvio);
		}
	}

	public void treinaNegativo(String[] linha) {
		totalNegativo++;
		for (int i = 1; i < linha.length; i++) { // i = 1 pq o primeiro atributo
													// é o nome do doc
			double valor = Double.parseDouble(linha[i]);
			double media = negativosMedia.get(i);
			double novaMedia = Media(valor, media, totalNegativo);
			negativosMedia.put(i, novaMedia);

			listaValoresNegativos.get(i).add(valor);
			double desvio = Desvio(listaValoresNegativos.get(i), novaMedia, totalNegativo);
			negativosDev.put(i, desvio);
		}
	}

	public String classificar(String[] texto) {
		double scorePos = 0.0;
		double scoreNeg = 0.0;

		for (int i = 1; i < texto.length; i++) {
			scorePos += Densidade(i, Double.parseDouble(texto[i]), "positivo");
			scoreNeg += Densidade(i, Double.parseDouble(texto[i]), "negativo");
		}
		System.out.println("score pos: " + scorePos);
		System.out.println("score neg: " + scoreNeg);

		if (scorePos >= scoreNeg)
			return "Positivo";
		else
			return "Negativo";
	}

}
