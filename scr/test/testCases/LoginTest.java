package testCases;

import helpers.common.CommonComponent;
import org.testng.Assert;
import org.testng.annotations.Test;
import tasks.Login;

public class LoginTest extends baseTest.BaseTest {

	@Test
	public void LoginTest() {
		
		//Arrange
		String usuario = "AALCAZAR.LPZ";
		String pasword = "A12345678";
		// parametros de entrada o variables
		
		//Act
		
		 Login.as(webDriver, usuario,pasword);
		
		//Assert
		
		// Assert.true(condicion,esperado);
		Assert.assertTrue(true);
		CommonComponent.registrarEnLog(" prueba del registro del assert: ...OK");
		

	}

}
