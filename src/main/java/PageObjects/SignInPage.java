package pageObjects;

import utilities.WebElementsActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage extends WebElementsActions {
    public SignInPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#ap_email")
    WebElement emailField;

    @FindBy(css = "#continue")
    WebElement continueButton;

    @FindBy(css = "#createAccountSubmit")
    WebElement createAccountButton;

    @FindBy(css = "label[class='a-form-label']")
    WebElement emailLabelText;

    @FindBy(css = ".a-spacing-small")
    WebElement signInLogo;

    @FindBy(css = "label[for='ap_password']")
    WebElement passwordLabelText;

    @FindBy(css = "#ap_password")
    WebElement passwordField;

    @FindBy(css = "span[class='a-list-item']")
    WebElement alert;

    @FindBy(css = "#signInSubmit")
    WebElement signInButton;

    @FindBy(css = ".a-spacing-base>span")
    WebElement emailConfirmation;

    public void inputEmailField(String email) {
        sendText(emailField, email);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    public void clickCreateAccountButton() {
        createAccountButton.click();
    }

    public String getEmailLabelText() {
        return emailLabelText.getText();
    }

    public String getSignInLogo() {
        return signInLogo.getText();
    }

    public String getPasswordLabel() {
        return passwordLabelText.getText();
    }

    public void inputPasswordField(String password) {
        sendText(passwordField, password);
    }

    public String alertMessageText() {
        return alert.getText();
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public String getConfirmEmailId() {
        return emailConfirmation.getText();
    }
    public Boolean verifyEmailEnteredIsCorrect(){
        return emailConfirmation.isDisplayed();
    }
    public WebElement emailEnteredPreviously(){
        return emailConfirmation;
    }
}
