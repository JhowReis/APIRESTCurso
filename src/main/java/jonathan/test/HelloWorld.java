package jonathan.test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;


public class HelloWorld {
	
	public static void main(String[] args) {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
		System.out.println(response.statusCode() == 200);
		
		 ValidatableResponse validacao = response.then();
		 validacao.statusCode(200);
	}
}
