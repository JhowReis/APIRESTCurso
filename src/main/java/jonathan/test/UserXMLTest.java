package jonathan.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.internal.path.xml.NodeImpl;

import org.junit.Assert;
import org.junit.BeforeClass;

public class UserXMLTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://restapi.wcaquino.me";
		RestAssured.port = 443;
//		RestAssured.basePath = "/v2";
		
	}
	
	
	
	
	@Test
	public void devoTrabalharComXML() {

		
		given() 
			.log().all()
		.when()
			.get("/usersXML/3")
//			.get("https://restapi.wcaquino.me/usersXML/3
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
	public void devoFazerPesquisasAvançadasComXML() {
		given()
		.when()
			.get("/usersXML")
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
	public void devoFazerPesquisasAvançadasComXMLEJAVA() {
		
		ArrayList<NodeImpl> nomes = given()
		.when()
		.get("/usersXML")
		.then()
		.statusCode(200)
		.extract().path("users.user.name.findAll{it.toString().contains('n')}")
		
		;
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
		Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));
	}
	
	@Test
	public void devoFazerPesquisasAvançadasComXPATH() {
		
		 given()
		.when()
			.get("/usersXML")
		.then()
			.statusCode(200)
			.body(hasXPath("count(/users/user)", is("3")))
			.body(hasXPath("/users/user[@id='1']"))
			.body(hasXPath("//user[@id='1']"))
			.body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia")))
			.body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos",allOf(containsString("Zezinho"), containsString("Luizinho"))))
			.body(hasXPath("/users/user/name", is("João da Silva")))
			.body(hasXPath("//name", is("João da Silva")))
			.body(hasXPath("/users/user[2]/name", is("Maria Joaquina")))
			.body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
			.body(hasXPath("count(/users/user/name[contains(., 'n')])", is("2")))
			.body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
			.body(hasXPath("//user[age > 20 and age < 30]/name", is("Maria Joaquina")))
			.body(hasXPath("//user[age > 20][age < 30]/name", is("Maria Joaquina")))
		;
	}
	
  
}
