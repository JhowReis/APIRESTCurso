package jonathan.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.junit.Test;

import io.restassured.internal.path.xml.NodeImpl;

import org.junit.Assert;

public class UserXMLTest {
	
	@Test
	public void devoTrabalharComXML() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(200)
			.rootPath("user")
			.body("name", is("Ana Julia"))
			.body("@id", is("3"))
			
			.rootPath("user.filhos")
			.body("name.size()", is(2))

			.detachRootPath("filhos")
			.body("filhos.name[0]", is("Zezinho"))
			
			.appendRootPath("filhos")
			.body("name[1]", is("Luizinho"))
		;
	}
	
	
	@Test
	public void devoFazerPesquisasAvan�adasComXML() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.statusCode(200)
			.body("users.user.size()", is(3))
			.body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
			.body("users.user.@id", hasItems("1", "2", "3"))
			.body("users.user.find{it.age ==  25}.name", is("Maria Joaquina"))
			.body("users.user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina", "Ana Julia") )
		;
	}
	
	@Test
	public void devoFazerPesquisasAvan�adasComXMLEJAVA() {
		
		ArrayList<NodeImpl> nomes = given()
		.when()
		.get("https://restapi.wcaquino.me/usersXML")
		.then()
		.statusCode(200)
		.extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}")
		
		;
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
		Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));
	}
  
}
