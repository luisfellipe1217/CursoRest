package br.com.luisfellipe.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.ContentType;

public class VerbosTest {
	
	private static String apiToken = "b056fbc4609acfa4d3241ce1a6fe725bfa267badb3996abf518851b8db92d0ab"; //Deve ser informado o Token do usuário trello
	private static String apiKey = "f560501b8207ba3a8632de34b00e8a60"; // Deve ser informado a key da api trello

	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"name\": \"Jose\", 	\"age\": 50 }")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
		;		
	}
	
	@Test
	public void deveSalvarUsuarioMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuario via MAP");
		params.put("age", 25);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via MAP"))
			.body("age", is(25))
		;		
	}
	
	@Test
	public void deveSalvarUsuarioObjeto() {
		
		User user = new User("Usuario via Objeto", 20, 1255.50);

		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via Objeto"))
			.body("age", is(20))
		;		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveDesSerealizarObjeto() {
		
		User user = new User("Usuario Descerealizado", 20, 1255.50);

		User usuarioInserido = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)	
			.extract().body().as(User.class);		
		
		System.out.println(usuarioInserido);
		Assert.assertThat(usuarioInserido.getId(), Matchers.is(notNullValue()));
		Assert.assertEquals(usuarioInserido.getName(), "Usuario Descerealizado");
		Assert.assertThat(usuarioInserido.getAge(), is(20));
		
		
		
	}
	@Test
	public void naoDeveSlavarUsuarioSemNome() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"age\": 50 }")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))		
			.body("error", is("Name é um atributo obrigatório"))
	;				
	}
	
	@Test
	public void devoSalvarUmUsuarioViaXML() {
		given()
		.log().all()
		.contentType(ContentType.XML)
		.body("<user><name> Jose </name><age>50</age></user>")
	.when()
		.post("http://restapi.wcaquino.me/usersXML")
	.then()
		.log().all()
		.statusCode(201)
		.body("user.@id", is(notNullValue()))		
		.body("user.name", is("Jose"))
		.body("user.age", is("50"))		
		;	
	}
	
	@Test
	public void deveAlterarUsuario() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{ \"name\": \"Nome Alterado\", 	\"age\": 80 }")
	.when()
		.put("http://restapi.wcaquino.me/users/1")
	.then()
		.log().all()		
		.statusCode(200)
		.body("id", is(1))
		.body("name", is("Nome Alterado"))
		.body("age", is("80"))	
		;		
	}
	
	@Test
	public void queEstouComAcessoAoTrello() {
				
		given()
			.log().method()
			.log().uri()
		.when()
			.get("/1/members/me/boards?key=?key={yourKey}&token={yourToken}", apiKey , apiToken)
		.then()
		.statusCode(200);  //valida statusCode se requisição está acontecendo com sucesso.
	}

}
