package tasks;

import helpers.common.CommonComponent;
import helpers.commonInmuebles.ComponentCommonInmuebles;
import org.openqa.selenium.WebDriver;

import main.actions.Click;
import main.actions.Enter;
import main.ui.LoginUI;

public class Login extends ComponentCommonInmuebles
{


	  public static void as(WebDriver driver, String user, String password) {
		  long tiempoEjecucionInicial = 0;
		  tiempoEjecucionInicial = System.currentTimeMillis();
          new ComponentCommonInmuebles();
          Enter.text(driver, LoginUI.userInput, user);
		  Enter.text(driver, LoginUI.passwordInput, password);
		  Click.on(driver, LoginUI.loginButton);
		  CommonComponent.registrarEnLog(" Generar Login: ...OK");
		  CommonComponent.registrarEnLog("TIEMPO EJECUCION TOTAL: ".concat(CommonComponent.formatearTiempoEjecucion(System.currentTimeMillis() - tiempoEjecucionInicial)));
		}

}
 