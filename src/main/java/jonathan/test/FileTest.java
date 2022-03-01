package jonathan.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Test;

public class FileTest {
	
	@Test
	public void deveObrigarEnvioArquivo() {
		given()	
			.log().all()
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)
			.body("error", is("Arquivo não enviado"))
		;
		
	}
	@Test
	public void deveFazerUploadArquivo() {
		given()	
		.log().all()
		.multiPart("arquivo", new File("src/main/resources/testesFuncionaisCertificado.pdf"))
		.when()
		.post("https://restapi.wcaquino.me/upload")
		.then()
		.log().all()
		.statusCode(200)
		.body("name", is("testesFuncionaisCertificado.pdf"))
		;
		
	}

}
























