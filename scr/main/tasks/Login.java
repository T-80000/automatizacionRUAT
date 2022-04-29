package main.tasks;

import org.openqa.selenium.WebDriver;

import main.actions.Click;
import main.actions.Enter;
import main.ui.LoginUI;

public class Login {

		public static void as(WebDriver driver, String user, String password) {
			Enter.text(driver, LoginUI.userInput, user);
			Enter.text(driver, LoginUI.passwordInput, password);
			Click.on(driver, LoginUI.loginButton);
		}

}
 