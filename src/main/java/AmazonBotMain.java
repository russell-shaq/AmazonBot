import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class AmazonBotMain {


    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        driver.get("https://primenow.amazon.com/home");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().maximize();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a search phrase");
        String searchPhrase = "REESE'S Snack Size";
        searchPhrase = scanner.nextLine();
        System.out.println("Enter a key phrase, which required item's name contains.");
        String keyPhrase = "REESE'S";
        keyPhrase = scanner.nextLine();
        System.out.println("Enter waiting time in seconds.");
        String waitingTimeInSeconds = scanner.nextLine();
        System.out.println("Enter ok, after you logged in Amazon.");
        if (scanner.nextLine().equals("ok")) {
            System.out.println("Bot is working.");
        }
        while (true) {
            WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"search-string\"]"));
            searchBar.sendKeys(searchPhrase);
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement searchButton = driver.findElement(By.cssSelector("#houdini-nav-search-submit-button"));
            searchButton.click();
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement searchBar2 = driver.findElement(By.xpath("//*[@id=\"search-string\"]"));
            searchBar2.clear();
            ArrayList<WebElement> itemElements = (ArrayList<WebElement>) driver.findElements(By.className("grid-item"));
            WebElement requiredItem = null;
            for (WebElement item : itemElements) {

                if (item.getText().contains(keyPhrase)) {

                    requiredItem = item;
                    WebElement itemImage = requiredItem.findElement(By.tagName("img"));
                    itemImage.click();
                    break;
                }
            }
            if (requiredItem == null) {
                try {
                    sleep(Integer.valueOf(waitingTimeInSeconds)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

                if (requiredItem != null) {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Select quantity = new Select(driver.findElement(By.cssSelector("#primenowQuantity")));
                    ArrayList<WebElement> options = (ArrayList<WebElement>) quantity.getOptions();
                    ArrayList<Integer> optionsValues = new ArrayList<>();
                    for (WebElement option: options)
                    {
                       optionsValues.add(Integer.valueOf(option.getText()));
                    }
                    Collections.sort(optionsValues);
                    Integer biggestOption  = optionsValues.get(optionsValues.size()-1);
                    quantity.selectByValue(biggestOption.toString());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WebElement addToCartButton = driver.findElement(By.cssSelector("#add-to-cart-button"));
                    addToCartButton.sendKeys(Keys.ENTER);
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WebElement cartElement = driver.findElement(By.cssSelector("#navCart"));
                    cartElement.click();
                    WebElement proceedToCheckoutButton = driver.findElement(By.cssSelector("#a-autoid-1-announce"));
                    proceedToCheckoutButton.sendKeys(Keys.ENTER);
                    WebElement deliveryTime = driver.findElement(By.cssSelector("" +
                            "#delivery-slot-form > div.a-row.a-expander-container.a-expander-extend-container.checkout-radio-group > div:nth-child(5) > div > div.a-column.a-span9 > div > span > div > label > i"));
                    deliveryTime.click();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WebElement placeYourOrderButton = driver.findElement(By.xpath("//*[contains(text(), 'Place your order')]"));
                    placeYourOrderButton.sendKeys(Keys.ENTER);
                    System.out.println("Item "+requiredItem.getText()+ " in quantity of "+biggestOption + " has been purchased.");
                    System.out.println("Bot will finish work soon.");
                    try {
                        sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    driver.quit();
                    System.exit(0);
                }
            }
        }
    }

