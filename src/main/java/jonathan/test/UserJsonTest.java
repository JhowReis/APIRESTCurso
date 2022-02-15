package jonathan.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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
			.body("name", is("Jo�o da Silva")).body("name", containsString("Silva")).body("age", is(30))
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
			.body("error", is("Usu�rio inexistente"));
			
		;		
	}

}
