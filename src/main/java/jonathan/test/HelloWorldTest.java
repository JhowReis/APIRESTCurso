package jonathan.test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class HelloWorldTest {

	@Test
	public void testHelloWorld() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}

	@Test
	public void outrasFormarRestAssured() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		get("https://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given()//pré condições
		.when()//Ações
			.get("https://restapi.wcaquino.me/ola")
		.then()//Assertivas
			.statusCode(200);

	}
}
