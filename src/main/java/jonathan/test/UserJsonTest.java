package jonathan.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJsonTest {

	@Test
	public void deveVerificarPrimeiroNivel() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("João da Silva")).body("name", containsString("Silva")).body("age", is(30))
			.body("age", greaterThan(18)).body("salary", is(1234.5678F))

		;

	}

	@Test
	public void outrasFormasVerficarPrimeiroNivel() {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");

		// PATH
		Assert.assertEquals(1, response.path("id"));
		Assert.assertEquals(1, response.path("%s", "id"));

		// JSONPATH
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));

		// FROM
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
	}

	@Test
	public void deveVerificarSegundoNivel() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/2")
		.then()	
			.statusCode(200)
			.body("name", containsString("Joaquina")).body("endereco.rua", is("Rua dos bobos"))
			.body("endereco.numero", is(0))

		;
	}

	@Test
	public void deveVerificarListas() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/3")
		.then()
			.statusCode(200)
			.body("name", containsString("Ana")).body("filhos", hasSize(2)).body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho")).body("filhos.name", hasItem("Luizinho"))
			.body("filhos.name", hasItems("Luizinho", "Zezinho"))

		;
	}

	@Test
	public void deveRetornarErroInexistente() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/4")
		.then()
			.statusCode(404)
			.body("error", is("Usuário inexistente"))
			
		;		
	}
	
	@Test
	public void deveVerificarListaRaiz() {
		given()
		.when()
		.get("https://restapi.wcaquino.me/users")
		.then()
		.statusCode(200)
		.body("$", hasSize(3))
		.body("name[1]", is("Maria Joaquina"))
		.body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho")))
		.body("salary", contains(1234.5678f,2500, null))
		;		
	}
	
	@Test
	public void deveFazerVerificacoesAvancadas() {
		given()
		.when()
		.get("https://restapi.wcaquino.me/users")
		.then()
		.statusCode(200)
		.body("$", hasSize(3))
		.body("age.findAll{it <= 25}.size()", is(2))
		.body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
		.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
		.body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))
		.body("find{it.age <= 25}.name", is("Maria Joaquina"))
		.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina","Ana Júlia"))
		.body("findAll{it.name.length() > 10}.name", hasItems("João da Silva","Maria Joaquina"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"),arrayWithSize(1)))
		.body("age.collect{it * 2}", hasItems(60, 40, 50))
		.body("id.max()", is(3))
		.body("salary.min()", is(1234.5678f))
		.body("salary.findAll{it != null}.sum()", is(closeTo(3734.5678f, 0.001)))
		.body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)))
		
		;		
	}
	

}



























