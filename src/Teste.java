
import java.util.ArrayList;
import java.util.List;

public class Teste {

	public static void main(String[] args) {

		try {
			LeitorArquivo leitor = new LeitorArquivo();
			leitor.abrirArquivo("entrada.csv");
			leitor.lerArquivo();
			List<String[]> linhas = leitor.getLinhas();

			// Passa a quantidade de palavras como parametro
			NaiveBayes NB = new NaiveBayes(linhas.get(0).length);

			// Guarda as linhas que serão classificadas
			List<String[]> execucao = new ArrayList<>();

			int qtdPos = 0;
			int qtdNeg = 0;

			for (String[] linha : linhas) {
				if (linha[0].contains("document")) {
					// primeira linha, não faz nada
				} else if (linha[0].contains("positivo") && qtdPos < 750) {
					NB.treinaPositivo(linha);
					qtdPos++;
				} else if (linha[0].contains("negativo") && qtdNeg < 750) {
					NB.treinaNegativo(linha);
					qtdNeg++;
				} else {
					execucao.add(linha);
				}
			}

			//// for (String[] entrada : execucao) {
			//// String resultado = NB.classificar(entrada);
			//// System.out.println(resultado);
			//// }
			for (int i = 0; i < 100; i++) {
				String resultado = NB.classificar(execucao.get(i));
				String[] linha = execucao.get(i);
				System.out.println("Era " + linha[0]);
				System.out.println("Classificou como : " + resultado);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
