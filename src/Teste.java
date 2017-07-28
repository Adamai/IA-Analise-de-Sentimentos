
import java.util.ArrayList;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		Gerador gerador = new Gerador();

		try {
			gerador.abrirArquivo();
			List<Integer> indices = new ArrayList<Integer>();
			List<String> palavras = new ArrayList<String>();
			String[] linha = gerador.lerLinha().split(";");
			for (int i = 0; i < linha.length; i++) {
				palavras.add(linha[i]);
			}
			
//			 //love, wonderful, best, great, superb, still
//			beautiful, dazzling, brilliant, phenomenal, excellent, fantastic

			System.out.println("tamanho : " + palavras.size());
			
			System.out.println("GOOD");
			System.out.println(palavras.indexOf("good"));
			indices.add(palavras.indexOf("good"));
			System.out.println(palavras.indexOf("love"));
			indices.add(palavras.indexOf("love"));
			System.out.println(palavras.indexOf("incred"));
			indices.add(palavras.indexOf("incred"));
			System.out.println(palavras.indexOf("great"));
			indices.add(palavras.indexOf("great"));
			System.out.println(palavras.indexOf("superb"));
			indices.add(palavras.indexOf("superb"));
			System.out.println(palavras.indexOf("beauti"));
			indices.add(palavras.indexOf("beauti"));
			System.out.println(palavras.indexOf("brilliant"));
			indices.add(palavras.indexOf("brilliant"));
			System.out.println(palavras.indexOf("fantast"));
			indices.add(palavras.indexOf("fantast"));
			
			System.out.println();
			
			System.out.println("BAD");
			System.out.println(palavras.indexOf("bad"));
			indices.add(palavras.indexOf("bad"));
			System.out.println(palavras.indexOf("worst"));
			indices.add(palavras.indexOf("worst"));
			System.out.println(palavras.indexOf("stupid"));
			indices.add(palavras.indexOf("stupid"));
			System.out.println(palavras.indexOf("wast"));
			indices.add(palavras.indexOf("wast"));
			System.out.println(palavras.indexOf("bore"));
			indices.add(palavras.indexOf("bore"));
			System.out.println(palavras.indexOf("suck"));
			indices.add(palavras.indexOf("suck"));
			System.out.println(palavras.indexOf("terribl"));
			indices.add(palavras.indexOf("terribl"));
			System.out.println(palavras.indexOf("badli"));
			indices.add(palavras.indexOf("badli"));
			
			System.out.println(indices);
			
			gerador.lerArquivo(indices);
			
			List<String[]>saida = gerador.getLinhas();
			for(int i = 0; i < saida.size(); i++ ){
				String[] aux = saida.get(i);
				for(int j = 0; j< aux.length ; j++){
					System.out.print(aux[j]+", ");
				}
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
