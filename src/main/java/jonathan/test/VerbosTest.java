package jonathan.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class VerbosTest {

	@Test
	public void deveSalvarUsuario() {
		given()
			.contentType("application/json")
			.log().all()
			.body("{\"name\": \"Jonathan\", \"age\": 28}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jonathan"))
			.body("age", is(28))
		
		;
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
			.contentType("application/json")
			.log().all()
			.body("{\"age\": 28}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"))
	;
		
	}
}


