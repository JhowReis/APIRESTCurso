package jonathan.test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class HelloWorldTest {

	@Test
	public void testHelloWorld() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		Assert.assertTrue("O STATUS CODE DEVE SER 200",response.statusCode() == 200);
		
		//Amelhor solução é:::
		Assert.assertEquals(200, response.statusCode());

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}

	@Test
	public void outrasFormarRestAssured() {
		Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		//forma rapida
		get("https://restapi.wcaquino.me/ola").then().statusCode(200);
		
		//Modo fluente
		
		given()//pré condições
		.when()//Ações
			.get("https://restapi.wcaquino.me/ola")
		.then()//Assertivas
			.statusCode(200);

	}
	
	@Test
	public void deveConhecerOsMatchersHamcrest() {
		assertThat("Maria", Matchers.is("Maria"));
		assertThat(125, Matchers.is(125));
		assertThat(125, Matchers.isA(Integer.class));
		assertThat(125d, Matchers.isA(Double.class));
		assertThat(125, Matchers.greaterThan(122));
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9); //listas
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, containsInAnyOrder(1,5,3,7,9));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1,3,9));
		
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));
		
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));//ou
		
		assertThat("Maria", allOf(startsWith("Ma"), endsWith("ia"), containsString("ria")));// e
		
	}
	
	@Test
	public void devoValidarBody() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
}











































