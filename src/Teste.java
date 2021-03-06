
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teste {

	public static void main(String[] args) {

		try {
			LeitorArquivo leitor = new LeitorArquivo();
			leitor.abrirArquivo("entrada.csv");
			leitor.lerArquivo();
			List<String[]> linhas = leitor.getLinhas();
			Collections.shuffle(linhas);

			// Passa a quantidade de palavras como parametro
			NaiveBayes NB = new NaiveBayes(linhas.get(0).length);

			// Guarda as linhas que ser�o classificadas
			List<String[]> execucao = new ArrayList<>();

			int qtdPos = 0;
			int qtdNeg = 0;

			for (String[] linha : linhas) {
				if (linha[0].contains("document")) {
					// primeira linha, n�o faz nada
				} else if (linha[0].contains("positivo") && qtdPos < 667) {
					NB.treinaPositivo(linha);
					qtdPos++;
				} else if (linha[0].contains("negativo") && qtdNeg < 667) {
					NB.treinaNegativo(linha);
					qtdNeg++;
				} else {
					execucao.add(linha);
				}
			}

			NB.aprender();


			for (int i = 0; i < execucao.size(); i++) {
				String resultado = NB.classificar(execucao.get(i));
				String[] aux = execucao.get(i);
				NB.preencherMatriz(aux[0], resultado);
			}
			NB.imprimeMatriz();
			System.out.println();
			System.out.println("Acuracia = " + NB.acuracia(execucao.size()));
			System.out.println("Erro = " + NB.erro(execucao.size()));
			System.out.println("Precis�o = " + NB.precisao());
			System.out.println("Relevancia = " + NB.relevancia());
			System.out.println("F-Measure = " + NB.Fmeasure());

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		double valor = 1 ;
		double media = 2.5 ;
		double desvio = 0.70711 ;
		double densidade = 0.0;
		double dividendo = (Math.pow(Math.E,
				-(Math.pow(valor - media, 2)) / (2 * Math.pow(desvio, 2))));
		double divisor = (desvio * Math.sqrt(2 * Math.PI));
		if (divisor != 0)
			densidade = dividendo / divisor;
		System.out.println("densidade = "+densidade); */
		
	}
}
