import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivo {

	private BufferedReader br;
	private boolean acabou = false;
	private List<String[]> linhas;

	public LeitorArquivo() {
		linhas = new ArrayList<>();
	}

	public boolean abrirArquivo(String nomearquivo) throws FileNotFoundException {
		File f = new File(nomearquivo);
		br = new BufferedReader(new FileReader(f));
		return f.exists();
	}

	private String lerLinha() throws IOException {
		String ret;
		if ((ret = br.readLine()) == null) {
			br.close();
			acabou = true;
		}
		return ret;
	}

	public void lerArquivo() {
		try {
			String linha;
			while (!acabou) {
				linha = lerLinha();
				if (linha != null) {
					String[]linhaSeparada = linha.split(";");
					linhas.add(linhaSeparada);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String[]> getLinhas() {
		return linhas;
	}
}
