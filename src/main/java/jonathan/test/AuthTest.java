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
	
	
	// 1d54ac0d1793b95a2868d7bde8db584e   https://api.openweathermap.org/data/2.5/weather?q=Recife,BR&appid=1d54ac0d1793b95a2868d7bde8db584e&units=metric

}
