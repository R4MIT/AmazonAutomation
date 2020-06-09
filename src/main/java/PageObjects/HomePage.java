package pageObjects;

import utilities.WebElementsActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends WebElementsActions {

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div[role='main']")
    WebElement homePageLocator;

    @FindBy(css = "#nav-link-accountList .nav-icon.nav-arrow")
    WebElement accountsAndLists;

    @FindBy(css = "#nav-flyout-ya-signin .nav-action-inner")
    WebElement signIn;

    public Boolean getHomePageLocator() {
        return homePageLocator.isDisplayed();
    }

    public Boolean verifySignInButton() {
        return signIn.isDisplayed();
    }

    public void mouseHoverOnAccountsAndLists(WebDriver driver) {
        mouseHover(driver, accountsAndLists);
    }

    public void clickSignInButton(WebDriver driver) {
        this.mouseHoverOnAccountsAndLists(driver);
        waitForElementClickable(driver, signIn);
//        WebDriverWait wait = new WebDriverWait(driver, 3);
//        wait.until(ExpectedConditions.visibilityOf(signIn));
        Assert.assertTrue(verifySignInButton(), "Cannot Locate SignIn Button");
        moveOnElementAndClick(driver, signIn);
        System.out.println("SignIn Button Clicked Successfully");
    }
   /* public void waitTillElementVisible (WebElement webElement){
        WebDriverWait wt = new WebDriverWait(driver,5);
        wt.until(ExpectedConditions.visibilityOf(webElement));
    }*/

}
