package jonathan.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class AuthTest {

	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		
		;
	}
	
	@Test
	public void deveObterClima() {
		given()
			.log().all()
			.queryParam("q", "Recife,BR")
			.queryParam("appid", "1d54ac0d1793b95a2868d7bde8db584e")
			.queryParam("units", "metric")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")	
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Recife"))
			.body("coord.lon",is(-34.8811f))
		
		;
	}
	
	@Test
	public void naoDeveAcessarSemSenha() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(401)
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica() {
		given()
		.log().all()
		.when()
		.get("https://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica2() {
		given()
		.log().all()
		.auth().basic("admin", "senha")
		.when()
		.get("https://restapi.wcaquino.me/basicauth")
		.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		;
	}
	@Test
	public void deveFazerAutenticacaoBasicaChallenge() {
		given()
		.log().all()
		.auth().preemptive().basic("admin", "senha")
		.when()
		.get("https://restapi.wcaquino.me/basicauth2")
		.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		;
	}
	
	


}
