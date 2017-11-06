
package com;
 
/**
 * @author Arno Junio de Almeida Morais
 * 
 */
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.Arquivo;

 
@Path("/")
public class RestService {
	Arquivo a = new Arquivo();
	final String BASEPATH = "C:\\Users\\arnold\\Documents\\Rest\\mensagens.json";
	@POST
	@Path("/restService")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream entrada) throws JSONException {
		StringBuilder leitor = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(entrada));
			String line = null;
			while ((line = in.readLine()) != null) {
				leitor.append(line);
			}
		} catch (Exception e) {
			System.out.println("Erro ao converter: - ");
		}
		JSONObject json = new JSONObject(leitor.toString());
		JSONObject memoria = new JSONObject(a.lerArquivo(BASEPATH).toString());
		JSONArray array = memoria.getJSONArray("mensagens");
		System.out.println(array.toString());
		array.put(json);
		memoria.put("mensagens", array);
		System.out.println("Dados recebidos: " + array.toString());
		a.salvarArquivo(memoria.toString()+"\n", BASEPATH, false);
		return Response.status(200).entity(leitor.toString()).build();
	}
 
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_HTML)
	public Response verifyRESTService(InputStream entrada) {
		String result = "Servico Iniciado com Sucesso";
 
		return Response.status(200).entity(result).build();
	}
	@GET
	@Path("/getNewMessages/{id}")
	@Produces("application/json")
	public String jsonMessagem(@PathParam("id") String id) throws JSONException {
		Arquivo a = new Arquivo();
		String mensagem = "";
		JSONArray json = a.lerArquivo(BASEPATH).getJSONArray("mensagens");
		if(id.equals("c2")){
			for(int i = 0;i < json.length();i++){
				JSONObject jo = (JSONObject) json.get(i);
				if(jo.getString("target").equals("c2")){
					mensagem += jo.getString("user")+" em "+jo.getString("data")+" escreveu para "+jo.getString("target")+": "+jo.getString("msg")+"\n";
				}
			}
			return mensagem;
		}else if(id.equals("c1"))
		{
			for(int i = 0;i < json.length();i++){
				JSONObject jo = (JSONObject) json.get(i);
				if(jo.getString("target").equals("c1")){
					mensagem += jo.getString("user")+" em "+jo.getString("data")+" escreveu para "+jo.getString("target")+": "+jo.getString("msg")+"\n";
				}
			}
			return mensagem;
		}else{
			return "nao existem mensagens para esse usuario";
		}
	}
}