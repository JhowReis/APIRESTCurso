package jonathan.test;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.http.ContentType;


public class EnvioDadoTest {
	
	@Test
	public void deveEnviarValorViaQuery() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=json")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
		
		;
		
	}
	
	@Test
	public void deveEnviarValorViaParametroQuery() {
		given()
		.log().all()
		.queryParam("format", "xml")
		.when()
		.get("https://restapi.wcaquino.me/v2/users")
		.then()
		.log().all()
		.statusCode(200)
		.contentType(ContentType.XML)
		
		;
		
	}

}
