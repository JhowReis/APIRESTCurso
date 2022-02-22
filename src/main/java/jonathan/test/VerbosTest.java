package jonathan.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
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
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "José");
		params.put("age", 55);
		
		given()
		.contentType("application/json")
		.log().all()
		.body(params)
		.when()
		.post("https://restapi.wcaquino.me/users")
		.then()
		.log().all()
		.statusCode(201)
		.body("id", is(notNullValue()))
		.body("name", is("José"))
		.body("age", is(55))
		
		;
	}
	
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		User user = new User("Usuário via Objeto", 35);
			
		given()
		.contentType("application/json")
		.log().all()
		.body(user)
		.when()
		.post("https://restapi.wcaquino.me/users")
		.then()
		.log().all()
		.statusCode(201)
		.body("id", is(notNullValue()))
		.body("name", is("Usuário via Objeto"))
		.body("age", is(35))
		
		;
	}
	@Test
	public void deveDeserializarObjetoAoSalvarUsuarioUsandoObjeto() {
		User user = new User("Usuário deserializado", 35);
		
		User usuarioInserido = given()
		.contentType("application/json")
		.log().all()
		.body(user)
		.when()
		.post("https://restapi.wcaquino.me/users")
		.then()
		.log().all()
		.statusCode(201)
		.extract().body().as(User.class)
		;
		System.out.println(usuarioInserido);
		
		Assert.assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertEquals("Usuário deserializado", usuarioInserido.getName());
		Assert.assertThat(usuarioInserido.getAge(), is(35));
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
	
	@Test
	public void deveAlterarUsuario() {
		given()
			.contentType("application/json")
			.log().all()
			.body("{\"name\": \"Jonathan alterado\", \"age\": 50}")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Jonathan alterado"))
			.body("age", is(50))
	
		
		;
	}
	
	@Test
	public void deveCustomizarURL() {
		given()
		.contentType("application/json")
		.log().all()
		.body("{\"name\": \"Jonathan alterado\", \"age\": 50}")
		.when()
		.put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
		.log().all()
		.statusCode(200)
		.body("name", is("Jonathan alterado"))
		.body("age", is(50))
		
		
		;
	}
	
	@Test
	public void deveCustomizarURLparte2() {
		given()
		.contentType("application/json")
		.log().all()
		.pathParam("entidade", "users")
		.pathParam("userId", 1)
		.body("{\"name\": \"Jonathan alterado\", \"age\": 50}")
		.when()
		.put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
		.log().all()
		.statusCode(200)
		.body("name", is("Jonathan alterado"))
		.body("age", is(50))
		
		
		;
	}
	
	@Test
	public void deveDeletarUsuario() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
		.log().all()
			.statusCode(204)
		
		;
	}
	
	@Test
	public void deveDeletarUsuarioInexistente() {
		given()
		.log().all()
		.when()
		.delete("https://restapi.wcaquino.me/users/1000")
		.then()
		.log().all()
		.statusCode(400)
		.body("error", is("Registro inexistente"))
		
		;
	}
}
































































