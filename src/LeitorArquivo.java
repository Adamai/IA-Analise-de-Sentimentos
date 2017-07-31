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
	private List<String[]> linhasSeparadas;

	public LeitorArquivo() {
		linhasSeparadas = new ArrayList<>();
	}

	public boolean abrirArquivo(String nomearquivo) throws FileNotFoundException {
		File f = new File(nomearquivo);
		br = new BufferedReader(new FileReader(f));
		return f.exists();
	}

	public String lerLinha() throws IOException {  //se da close então ele só serve pra ler a primeira linha?
		String ret;
		if ((ret = br.readLine()) == null) {
			br.close();
			acabou = true;
		}
		return ret;
	}
	
	private ArrayList<String []> linhas = new ArrayList();
	
	public void gerarLinhas(){		//preencher o arraylist linhas com as linhas do arquivo
		String linha;
		int k=0;
		try {
			while(k==0){
			linha = br.readLine();
			if(linha!=null){
				linhas.add(separadorLinha(linha));
				} else
					k=1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String []> getLinhas(){		//para usar o arraylist linhas em outras classes
		return linhas;
	}
	
	private String[] separadorLinha(String linha){		//separar uma linha pelos ';'
		String [] linhasep = linha.split(";");
		return linhasep;
	}
	

	/*public String[] separadorDeLinha(String entrada) {
		if (entrada != null) {
			String[] pedacos = entrada.split(";");
			String[] linha = this.lerLinha().split(";");
			String[] result = new String[13];
			for (int i = 0; i < 13; i++) {
				String[] aux = pedacos[i].split("\"");
				result[i] = aux[1];
			}
			return result;
		} else
			return null;
	}*/
	
	/*public boolean lerArquivo() throws IOException {
		while (!acabou) {
			String novaLinha = lerLinha(); 
			String[] linhaSeparada = separadorDeLinha(novaLinha);
			if (linhaSeparada != null)
				linhasSeparadas.add(linhaSeparada);
		}
		return true;
	}*/
	
	/*public List<String[]> getLinhas() {
		return linhasSeparadas;
	}*/

}
