package com;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * @author Arno Junio de Almeida Morais
 * 
 */

public class RestServiceClient {
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
		String opcao;
		do{
			opcao = menuPrincipal();
			switch (opcao) {
			case "1":
				enviarMensagem();
				break;
			case "2":
				verificarNovasMensagens();
				break;
			case "4":
				JOptionPane.showMessageDialog(null, "Obrigado!");
				break;
			}
		}while(!opcao.equals("4"));


	}
	static void enviarMensagem(){
		String mensagem = "";
		Calendar c = Calendar.getInstance();
		String user = "c1";
		String data = ""+c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.HOUR)+c.get(Calendar.MINUTE)+c.get(Calendar.SECOND);
		System.out.println("Digite uma mensagem: ");
		mensagem = input.nextLine();
		mensagem = "{data: "+data+", user: "+user+", msg: "+mensagem+", target: c2}";
		System.out.println(mensagem);
		try {
			JSONObject jsonObject = new JSONObject(mensagem);
			try {
				URL url = new URL("http://localhost:8081/AppRestSimples/api/restService");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				while (in.readLine() != null) {
					
				}
				System.out.println("\nMensagem enviada com sucesso ao servidor!");
				
				in.close();
			} catch (Exception e) {
				System.out.println("\nErro ao enviar a mensagem!");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void verificarNovasMensagens(){
		try {
			URL url = new URL("http://localhost:8081/AppRestSimples/api/getNewMessages/c1");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Erro ao se conectar! "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Resposta do servidor .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	static String menuPrincipal(){
		return JOptionPane.showInputDialog( "BEM VINDO C1!\n1 - Enviar uma mensagem para C2\n2 - Verificar novas mensagens\n3 - Sobre\n4 - Sair");
	}
}