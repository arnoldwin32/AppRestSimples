package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Arquivo {
	StringBuilder memoria = new StringBuilder();
	int contador = 0;
	public void limparMemoria(StringBuilder memoria) {
		memoria.setLength(0);
	}

	/*metodo de leitura de arquivo ,busca as informações em um TXT no HD
	 * e coloca seus dados na memória,quando o metodo "lerArquivo"é acessado o que esta
		 na memória é limpo antes de colocar novos dado na memória.
	 */
	public JSONObject lerArquivo(String nomeArquivo) throws JSONException {
		String linha;
		limparMemoria(memoria);
		
		this.contador = 0;
		try {
			BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream(nomeArquivo), "utf-8"));
			while ((linha = leitor.readLine()) != null) {
				memoria.append(linha + "\n");
				this.contador++;
			}
			leitor.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		JSONObject json = new JSONObject(memoria.toString());
		return json;
	}
	public boolean salvarArquivo(String linha, String nomeArquivo, boolean append) {
		try {
			OutputStream os = new FileOutputStream(nomeArquivo, append);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter saida = new BufferedWriter(osw);
			//BufferedWriter saida = new BufferedWriter(new FileWriter(nomeArquivo,append));
			saida.write(linha);
			saida.flush();
			saida.close();
			System.out.println("Arquivo gravado com sucesso!");
			return true;
		} catch (Exception e) {
			System.out.println("erro de gravacao: "+e.toString());
			return false;
		}
	}
}
