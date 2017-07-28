import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TesteBayes {

	public static void main(String[] args) {
		

		NaiveBayes classificador = new NaiveBayes();
		
		String[] positivo1 = {"very","good","movie","enjoyed","scene"};
		String[] positivo2 = {"cool", "acting","movie", "amazing"};
		
		String[] negativo1 = {"bad", "movie", "suck", "awful"};
		String[] negativo2 = {"terrible", "acting", "waste", "time"};
		
		classificador.treina(positivo1, "positivo");
		classificador.treina(positivo2, "positivo");
		classificador.treina(negativo1, "negativo");
		classificador.treina(negativo2, "negativo");
		
		
		String[]teste1 = {"good","movie"};
		String[]teste2 = {"cool","enjoyed","acting","movie","good"};
		String[]teste3 = {"terrible", "movie", "bad","waste"};
		
		System.out.println("Usando teste1 :" + classificador.classifica(teste1));
		System.out.println("Usando teste2 :" + classificador.classifica(teste2));
		System.out.println("Usando teste3 :" + classificador.classifica(teste3));
	}
}
