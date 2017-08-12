
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

			// Guarda as linhas que serão classificadas
			List<String[]> execucao = new ArrayList<>();

			int qtdPos = 0;
			int qtdNeg = 0;

			for (String[] linha : linhas) {
				if (linha[0].contains("document")) {
					// primeira linha, não faz nada
				} else if (linha[0].contains("positivo") && qtdPos < 800) {
					NB.treinaPositivo(linha);
					qtdPos++;
				} else if (linha[0].contains("negativo") && qtdNeg < 800) {
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
			System.out.println("Precisão = " + NB.precisao());
			System.out.println("Relevancia = " + NB.relevancia());
			System.out.println("F-Measure = " + NB.Fmeasure());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
