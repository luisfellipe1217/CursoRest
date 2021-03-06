package br.com.luisfellipe.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

public class FileTest {

	@Test	
	public void devoObrigarEnviarArquivo() {		
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) //deveria ser 400.
			.body("error", is("Arquivo não enviado"))
			;
		}
	
	@Test
	public void devoEnviarArquivo() {		
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200) 			
			.body("name", is("users.pdf"))
			;
		}
	
	@Test
	public void nãoDevoEnviarArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf")) //arquivo maior que 1mb
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(5000L)) //limitador de tempo de execução
			.statusCode(413) 						
			;
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void devoBaixarUmArquivo() throws IOException {
		byte[] image = given()
			.log().all()			
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()			
			.statusCode(200) 		
			.extract().asByteArray();
		
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		Assert.assertThat(imagem.length(), lessThan(100000L));
	}
	
}


