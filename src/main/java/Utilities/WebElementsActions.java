package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebElementsActions {

    public void click(WebElement webElement) {
        webElement.click();
    }
    public void waitForElementClickable(WebDriver driver,WebElement ele)
    {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.pollingEvery(Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    public void sendText(WebElement webElement, String text) {
        this.click(webElement);
        webElement.clear();
        webElement.sendKeys(text);
    }

    public void mouseHover(WebDriver driver, WebElement webElement) {
        Actions action = new Actions(driver);
        action.moveToElement(webElement).build().perform();
    }

    public void moveOnElementAndClick(WebDriver driver, WebElement webElement) {
        waitForElementClickable(driver,webElement);
        Actions action = new Actions(driver);
        action.moveToElement(webElement).click(webElement).build().perform();
    }
}
