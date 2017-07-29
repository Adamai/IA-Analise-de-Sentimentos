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
	
	private float [] medias;
	
	public void gerarMedias(){		//gerar as medias das palavras. A palavra na posição linhas.get(0)[x] vai ter sua média em medias[x] (medias[0] vai ficar vazio por causa do atirbuto nome)
		//acho que aqui tem outro for. Um para percorrer as palavras e outro para percorrer as linhas
		for(int i = 1; i<linhas.size(); i++){	//começa de 1 por causa do atirbuto nome que não importa
			//PAREI AQUI -> SOMAR TODOS OS VALORES DE CADA LINHA PARA E DIVIDIR PELO NUMERO DE LINHAS PARA CADA PALAVRA. GUARDAR ESSA MÉDIA EM medias[i]
		}
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
