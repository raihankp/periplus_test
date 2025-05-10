package com.periplus.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By loginButton = By.id("button-login");
    private By errorMessage = By.xpath("//*[@class='warning']");

    public void setEmailField(String email) {
        set(emailField, email);
    }

    public void setPasswordField(String password) {
        set(passwordField, password);
    }

    public AccountPage login(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
        click(loginButton);
        return new AccountPage(driver);
    }

    public String getErrorMessage() {
        return find(errorMessage).getText();
    }
}
