package util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrapperUtil {
	private static final String NO_PICTURE_URL = "https://portal.cfm.org.br/wp-content/themes/portalcfm/assets/images/no-picture.svg";
	private static final String IMAGE_XPATH = "//*[@id=\"content\"]/div[1]/section[2]/div/div/div/div[1]/div/div/div/div[1]/div/img";

	private String driverUri;

	public ScrapperUtil(String driverUri) {
		this.driverUri = driverUri;
	}

	public String scrape(String doctorName) {
		// dados mockados para testes
		switch (doctorName) {
			case "Drauzio Varella":
				return "https://portal.cfm.org.br/wp-content/themes/portalcfm/assets/php/foto_medico.php?crm=13449&uf=SP&hash=797711dfb59ec8589d6da4690e6c2bbf";
			case "Paulo Muzy":
				return "https://portal.cfm.org.br/wp-content/themes/portalcfm/assets/php/foto_medico.php?crm=115573&uf=SP&hash=07304aa93b512cde3cd64dd5d8a8779a";
			default:
				break;
		}

    try {
			RemoteWebDriver driver = initializeWebDriver();
			return scrapeImage(driver, doctorName);
    } catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
    }
	}

	private RemoteWebDriver initializeWebDriver() throws MalformedURLException, URISyntaxException {
		EdgeOptions options = new EdgeOptions();
		options.setCapability("browserName", "MicrosoftEdge");
		options.setCapability("platformName", "Windows 10");

		return new RemoteWebDriver(new URI(this.driverUri).toURL(), options);
	}

	private String scrapeImage(RemoteWebDriver driver, String doctorName) {
    System.out.println("Scrapper function");

    driver.get("https://portal.cfm.org.br/busca-medicos/");
    WebDriverWait wait = new WebDriverWait(driver, 20);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"buscaForm\"]/div/div[1]/div[1]/div/input")));
    System.out.println("Page loaded");

    WebElement searchInput = driver.findElement(By.xpath("//*[@id=\"buscaForm\"]/div/div[1]/div[1]/div/input"));
    searchInput.sendKeys(doctorName);

    WebElement sendButton = driver.findElement(By.xpath("//*[@id=\"buscaForm\"]/div/div[4]/div[2]/button"));
    JavascriptExecutor executor = (JavascriptExecutor)driver;
    executor.executeScript("arguments[0].click();", sendButton);
    System.out.println("Search button clicked");

    String imageUrl;
    do {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(IMAGE_XPATH)));

        WebElement imageElement = driver.findElement(By.xpath(IMAGE_XPATH));

        imageUrl = imageElement.getAttribute("src");
        System.out.println("Image");
    } while (imageUrl.equals(NO_PICTURE_URL));

    return imageUrl;
	}
}
