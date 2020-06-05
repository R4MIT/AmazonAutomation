package TestScripts;
import PageObjects.SignInPage;
import Utilities.BaseClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignInTest extends BaseClass{
    WebDriver driver = setBrowserAndUrl();

    @Test(priority = 1, dataProvider = "ramit",dataProviderClass = BaseClass.class)
    public void signInWithInValidEmail(String username) {
        System.out.println(username);
        SignInPage signIn = new SignInPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        Assert.assertEquals(signIn.getEmailLabelText(), "E-mail address or mobile phone number",
                "Email Label is not as expected.");
        Assert.assertEquals(signIn.getSignInLogo(), "Sign-In", "Sign Logo is not as expected.");
//        String nonExistingEmailAddress = generateRandomInvalidEmailAddress(10);
        signIn.inputEmailField(username);
        signIn.clickContinueButton();
        Assert.assertEquals(signIn.alertMessageText(), "We cannot find an account with that e-mail address",
                "Email entered is correct");
    }

    @Test(priority = 2, dataProvider = "invalidPassword",dataProviderClass = BaseClass.class)
    public void signInWithInValidPassword(String emailAddress, String password) {
        System.out.println(emailAddress);
        System.out.println(password);
        SignInPage signIn = new SignInPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        Assert.assertEquals(signIn.getEmailLabelText(), "E-mail address or mobile phone number",
                "Email Label is not as expected.");
//        String emailAddress = "ramitsalotra@gmail.com";
        signIn.inputEmailField(emailAddress);
        signIn.clickContinueButton();
        WebDriverWait wt = new WebDriverWait(driver,5);
        wt.until(ExpectedConditions.visibilityOf(signIn.emailEnteredPreviously()));
        Assert.assertTrue(signIn.verifyEmailEnteredIsCorrect());
        Assert.assertEquals(signIn.getConfirmEmailId(), emailAddress, "Email id is not matching");
        Assert.assertEquals(signIn.getSignInLogo(), "Sign-In", "Sign Logo is not as expected.");
        Assert.assertEquals(signIn.getPasswordLabel(), "Password", "Password Label is not as expected.");
        signIn.inputPasswordField(password);
        signIn.clickSignInButton();
        Assert.assertEquals(signIn.alertMessageText(),
                "To better protect your account, " +
                        "please re-enter your password and then enter the characters as they are shown in the image below.",
                "Password entered is correct");
    }


    private String generateRandomInvalidEmailAddress(int stringLength) {
        String generatedString = RandomStringUtils.randomAlphabetic(stringLength);
        generatedString = generatedString.concat("@mail.com.ss");
        return generatedString;
    }

}