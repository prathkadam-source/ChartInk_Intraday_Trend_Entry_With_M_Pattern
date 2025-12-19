package qa.helperClass;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.base.BaseTest;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class SeleniumHelper extends BaseTest {

    //    public SeleniumHelper(WebDriver driver) {
//        this.driver = driver;
//    }
    public SeleniumHelper() {
        PageFactory.initElements(driver, this);
    }

    // ✅ 1. Check if element is visible
    public boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            System.out.println("Element not visible: " + e.getMessage());
            return false;
        }
    }

    // ✅ 2. Safe Click
    public void safeClick(By locator) {
        try {
            if (isElementVisible(locator)) {
                driver.findElement(locator).click();
            } else {
                System.out.println("Element not clickable: " + locator.toString());
            }
        } catch (Exception e) {
            System.out.println("Error in click: " + e.getMessage());
        }
    }

    public void forceClickByJavaScript(By locator) {

        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);

        } catch (Exception e) {
            System.out.println("Error in click: " + e.getMessage());
        }
    }

    public void makeElementvisibleByJavaScript(By locator) {

        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", element);

        } catch (Exception e) {
            System.out.println("Error in click: " + e.getMessage());
        }
    }

    public void triggerFrontendStateByJavaScript(By locator) {

        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    """
                    const input = arguments[0];
                    const file = new File(['dummy'], 'runTime_Stocks_for_watchlist.txt', { type: 'text/plain' });
                
                    const dataTransfer = new DataTransfer();
                    dataTransfer.items.add(file);
                
                    input.files = dataTransfer.files;
                
                    input.dispatchEvent(new Event('change', { bubbles: true }));
                    """,
                    element
            );

        } catch (Exception e) {
            System.out.println("Error in click: " + e.getMessage());
        }
    }

    // ✅ 3. Safe SendKeys
    public void sendKeysSafe(By locator, String text) {
        try {
            if (isElementVisible(locator)) {
                WebElement element = driver.findElement(locator);
                element.clear();
                element.sendKeys(text);
            } else {
                System.out.println("Element not visible to send keys: " + locator.toString());
            }
        } catch (Exception e) {
            System.out.println("Error sending keys: " + e.getMessage());
        }
    }

    // ✅ 4. Get Text Safely
    public String getTextSafe(By locator) {
        try {
            if (isElementVisible(locator)) {
                return driver.findElement(locator).getText();
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("Error getting text: " + e.getMessage());
            return "";
        }
    }

    // ✅ 5. Wait for element to be visible (Explicit Wait)
    public boolean waitForElementVisible(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element: " + locator.toString());
            return false;
        }
    }

    // ✅ 6. Wait for element to be clickable
    public boolean waitForElementClickable(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element to be clickable: " + locator.toString());
            return false;
        }
    }

    // ✅ 7. Scroll to element using JavaScript
    public void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }

    // ✅ 8. Check if element exists (without exception)
    public boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 9. Get Attribute Value
    public String getAttribute(By locator, String attribute) {
        try {
            return driver.findElement(locator).getAttribute(attribute);
        } catch (Exception e) {
            System.out.println("Error getting attribute: " + e.getMessage());
            return "";
        }
    }

    // ✅ 10. JavaScript click (force click)
    public void jsClick(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println("JavaScript click failed: " + e.getMessage());
        }
    }

    public boolean safeFindElement(By locator, int timeoutInSeconds) {
        try {
            if (timeoutInSeconds <= 0) {
                // Temporarily set implicit wait to 0
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                WebElement element = driver.findElement(locator);
                return true;
            } else {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                return true;
            }
        } catch (NoSuchElementException | TimeoutException e) {
            return false; // Element not found
        } finally {
            // Reset implicit wait to default (optional: set your framework default)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }
}