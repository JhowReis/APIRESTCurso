package jonathan.test;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class HelloWorldTest {
	
	@Test
	public void testHelloWorld() {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		
		 ValidatableResponse validacao = response.then();
		 validacao.statusCode(200);
	}
}
