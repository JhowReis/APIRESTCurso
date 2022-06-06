package jonathan.test;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.module.jsv.JsonSchemaValidator;



public class SchemaTest {
		
		
		@Test
		public void deveValidarSchemaJson() {
			
			given()
				.log().all()
			.when()
				.get("https://restapi.wcaquino.me/users")
			.then()
				.log().all()
				.statusCode(200)
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))
			;
		}


}
