
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
				} else if (linha[0].contains("positivo") && qtdPos < 600) {
					NB.treinaPositivo(linha);
					qtdPos++;
				} else if (linha[0].contains("negativo") && qtdNeg < 600) {
					NB.treinaNegativo(linha);
					qtdNeg++;
				} else {
					execucao.add(linha);
				}
			}
			
			NB.aprender();
			
//			StringBuffer resposta = new StringBuffer();
//			for(int i = 1; i < 600; i++){
//				resposta.append(NB.positivosMedia.get(i));
//				resposta.append("\n");
//			}			
//			System.out.println(resposta.toString());

			int countPos = 0;
			int countNeg = 0;
			for (int i = 0; i < 800; i++) {
				String resultado = NB.classificar(execucao.get(i));
				if(resultado.equals("Positivo"))
					countPos++;
				else
					countNeg++;
//				String[] linha = execucao.get(i);
//				System.out.println("Era " + linha[0]);
//				System.out.println("Classificou como : " + resultado);
			}
			System.out.println("Positivos: " + countPos+", " + "Negativos: "+countNeg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
