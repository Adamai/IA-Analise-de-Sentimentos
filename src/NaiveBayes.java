import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NaiveBayes {
	// Guarda as posições das palavras que lê na primeira linha do arquivo,
	// talvez nãp precise
	private Map<Integer, String> palavras = new HashMap<>();

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

	public NaiveBayes(Map<Integer, String> palavras) {
		this.palavras = palavras;

		// inicializa as médias e os desvios como 0
		for (int i = 0; i < this.palavras.size(); i++) {
			positivosMedia.put(i, 0.0);
			positivosDev.put(i, 0.0);

			negativosMedia.put(i, 0.0);
			negativosDev.put(i, 0.0);

			listaValoresPositivos.add(new ArrayList<Double>());
			listaValoresNegativos.add(new ArrayList<Double>());
		}
	}

	public double Media(double novoValor, double mediaAtual, int total) {
		double media = (novoValor + mediaAtual) / total;
		return media;
	}

	public double Desvio(List<Double> valores, double media, int total) {
		double temp = 0;
		for (Double valor : valores) {
			temp += (valor - media) * (valor - media);
		}
		double variancia = temp / (total-1);
		double desvio = Math.sqrt(variancia);
		return desvio;
	}

	public void treinaPositivo(String[] linha) {
		totalPositivo++;
		for (int i = 0; i < linha.length; i++) {
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
		for (int i = 0; i < linha.length; i++) {
			double valor = Double.parseDouble(linha[i]);
			double media = negativosMedia.get(i);
			double novaMedia = Media(valor, media, totalNegativo);
			negativosMedia.put(i, novaMedia);

			listaValoresNegativos.get(i).add(valor);
			double desvio = Desvio(listaValoresNegativos.get(i), novaMedia, totalNegativo);
			negativosDev.put(i, desvio);
		}
	}

	public String classifica(String[] texto) {
		
		return "";
	}

}
