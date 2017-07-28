import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gerador {

	private BufferedReader br;
	private boolean acabou = false;
	private List<String[]> linhasSeparadas;

	public Gerador() {
		linhasSeparadas = new ArrayList<>();
	}

	public boolean abrirArquivo() throws FileNotFoundException {
		File f = new File("Re8.csv");
		br = new BufferedReader(new FileReader(f));
		return f.exists();
	}

	public String lerLinha() throws IOException {
		String ret;
		if ((ret = br.readLine()) == null) {
			br.close();
			acabou = true;
		}
		return ret;
	}

	public String[] separadorDeLinha(String entrada,List<Integer> indices) {
		if (entrada != null) {
			String[] pedacos = entrada.split(";");
			String[] result = new String[indices.size()];
			for(int i = 0; i < result.length; i++){
				result[i] = pedacos[indices.get(i)];
			}
			return result;
		} else
			return null;
	}
	
	public boolean lerArquivo(List<Integer> indices) throws IOException {
		while (!acabou) {
			String novaLinha = lerLinha(); 
			String[] linhaSeparada = separadorDeLinha(novaLinha, indices);
			if (linhaSeparada != null)
				linhasSeparadas.add(linhaSeparada);
		}
		return true;
	}
	
	public List<String[]> getLinhas() {
		return linhasSeparadas;
	}

}
