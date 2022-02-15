package jonathan.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;



public class UserJsonTest {
	
	@Test
	public void deveVerificarPrimeiroNivel() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("João da Silva"))
			.body("name", containsString("Silva"))
			.body("age", is(30))
			.body("age", greaterThan(18))
			.body("salary", is(1234.5678F))
		
		;
		
	}

}
