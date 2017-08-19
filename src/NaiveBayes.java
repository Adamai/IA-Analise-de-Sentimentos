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
	private int[][] matriz;

	public NaiveBayes(int tamanho) {
		for (int i = 0; i < tamanho; i++) {
			listaValoresPositivos.add(new ArrayList<Double>());
			listaValoresNegativos.add(new ArrayList<Double>());
		}

		matriz = new int[2][2];
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
		if (desvio == 0)
			desvio = 0.0001;
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

		double densidade = 0.5;

		double dividendo = (Math.pow(Math.E,
				-(Math.pow(valor - auxMedia.get(palavra), 2)) / (2 * Math.pow(auxDev.get(palavra), 2))));

		double divisor = (auxDev.get(palavra) * Math.sqrt(2 * Math.PI));

		if (divisor != 0)
			densidade = dividendo / divisor;

		return Math.log(densidade);
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
		// System.out.println("score pos: " + scorePos);
		// System.out.println("score neg: " + scoreNeg);

		if (scorePos >= scoreNeg)
			return "positivo";
		else
			return "negativo";
	}

	public void preencherMatriz(String real, String previsto) {
		if (real.contains("positivo") && previsto.equals("positivo")) {
			matriz[0][0]++;
		} else if (real.contains("positivo") && previsto.equals("negativo")) {
			matriz[0][1]++;
		} else if (real.contains("negativo") && previsto.equals("positivo")) {
			matriz[1][0]++;
		} else
			matriz[1][1]++;
	}

	public void imprimeMatriz() {

		System.out.println("Matriz de confusão");
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}

	public double acuracia(double total) {
		double acuracia;

		acuracia = (matriz[0][0] + matriz[1][1]) / total;
		// System.out.println("Acurácia = " + acuracia);
		return acuracia;
	}

	public double erro(double total) {
		double erro;

		erro = (matriz[1][0] + matriz[0][1]) / total;
		// System.out.println("Erro = " + erro);
		return erro;
	}

	public double precisao() {
		double precisao;
		double divisor = matriz[0][0] + matriz[1][0];

		precisao = matriz[0][0] / divisor;
		// System.out.println("Precisao =" + precisao);
		return precisao;
	}

	public double relevancia() {
		double relevancia;
		double divisor = matriz[0][0] + matriz[0][1];

		relevancia = matriz[0][0] / divisor;
		return relevancia;
	}

	public double Fmeasure() {
		double Fmeasure;
		double aux = (precisao() * relevancia()) / (precisao() + relevancia());

		Fmeasure = 2 * aux;
		return Fmeasure;
	}

}
