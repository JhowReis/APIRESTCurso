package jonathan.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
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
	@Test
	public void naoDeveFazerUploadArquivoGrande() {
		given()	
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/Resume-Jonathan-Reis.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(5000L))
			.statusCode(200)
		;
		
	}
	
	@Test
	public void deveBaixarArquivo() throws IOException {
		byte[] image = given()	
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/download")
		.then()
//			.log().all()
			.statusCode(200)
			.extract().asByteArray()
		;
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		Assert.assertThat(imagem.length(), lessThan(100000L));
	}

}


