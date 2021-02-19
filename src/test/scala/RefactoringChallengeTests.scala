import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.chrome.ChromeDriver
import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.selenium.WebBrowser

class RefactoringChallengeTests extends FeatureSpec with GivenWhenThen with Matchers with WebBrowser {

  Feature("Challenge tests") {

    //  Test one: Check to see if you can log in with valid credentials
    Scenario("User can login with valid credentials") {
      Given("I am on the login page")
      implicit val driver: WebDriver = new ChromeDriver()
      go to ("https://automationintesting.online/#/admin")

      When("I submit valid credentials ")
      driver.findElement(By.cssSelector("footer p a:nth-child(5)")).click()
      Thread.sleep(1000)
      driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin");
      Thread.sleep(1000)
      driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password");
      Thread.sleep(1000)
      driver.findElement(By.className("float-right")).click()

      Thread.sleep(5000)

      Then("I am shown the bookings dashboard")
      val webElement = driver.findElement(By.className("navbar-collapse"))
      Console.print(webElement.getText)
      val title = webElement.getText.contains("Rooms")

      assert(title == true)
      driver.quit()
    }


    //  Test two: Check to see if rooms are saved and displayed in the UI
    Scenario("Room is saved and displayed in UI") {
      Given("I am on the login page")

      And("I login")
      implicit val driver: WebDriver = new ChromeDriver()
      go to ("https://automationintesting.online/#/admin")

      driver.findElement(By.cssSelector("footer p a:nth-child(5)")).click()
      Thread.sleep(1000)
      driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin")
      Thread.sleep(1000)
      driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password");
      Thread.sleep(1000)
      driver.findElement(By.className("float-right")).click()

      Thread.sleep(2000)

      When("I add a new booking")

      Then("The booking is saved and displayed within bookings")
      driver.findElement(By.cssSelector(".room-form > div:nth-child(1) input")).sendKeys("101")
      driver.findElement(By.cssSelector(".room-form > div:nth-child(4) input")).sendKeys("101")
      Thread.sleep(1000)
      driver.findElement(By.className("btn-outline-primary")).click()

      Thread.sleep(5000)

      assert(driver.findElements(By.className("detail")).size() != 1)
      driver.close()
    }

    //  Test three: Check to see the confirm message appears when branding is updated
    Scenario("Message appears when branding is updated") {
      Given("I am on the login page")

      And("I login")
      implicit val driver: WebDriver = new ChromeDriver()
      go to ("https://automationintesting.online/#/admin")

      driver.findElement(By.cssSelector("footer p a:nth-child(5)")).click()
      Thread.sleep(1000)
      driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin")
      Thread.sleep(1000)
      driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password");
      Thread.sleep(1000)
      driver.findElement(By.className("float-right")).click()

      go to ("https://automationintesting.online/#/admin/branding")

      Thread.sleep(5000)

      driver.findElement(By.className("form-control")).sendKeys("Test")
      driver.findElement(By.className("btn-outline-primary")).click()

      Thread.sleep(1001)

      assert(driver.findElements(By.xpath("//button[text()=\"Close\"]")).size() == 1)

      driver.close()
    }

    //  Test four: Check to see if the contact form shows a success message
    Scenario("Contact form shows success message") {
      Given("I am on the home page")
      implicit val driver: WebDriver = new ChromeDriver()
      go to ("https://automationintesting.online/")
      Thread.sleep(1000)

      And("I create and send a new message")
      driver.findElement(By.cssSelector("input[placeholder=Name]")).sendKeys("TEST123")
      driver.findElement(By.cssSelector("input[placeholder=Email]")).sendKeys("TEST123@TEST.COM")
      driver.findElement(By.cssSelector("input[placeholder=Phone]")).sendKeys("01212121311")
      driver.findElement(By.cssSelector("input[placeholder=Subject]")).sendKeys("TEsTEST")
      driver.findElement(By.cssSelector("textarea")).sendKeys("TEsTESTTEsTESTTEsTEST")
      Thread.sleep(2000)
      driver.findElement(By.xpath("//button[text()=\"Submit\"]")).click()

      Then("I am shown confirmation that my message has been sent")
      Thread.sleep(4000)
      assert(driver.findElement(By.cssSelector(".contact")).getText.contains("Thanks for getting in touch"))
      driver.close()
    }

    //  Test five: Check to see if unread messages are bolded
    Scenario("Unread messages are bold") {
      Given("I am on the messages page")
      implicit val driver: WebDriver = new ChromeDriver()
      go to ("https://automationintesting.online/#/admin/messages")

      When("I login to view my messages")
      driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin")
      Thread.sleep(1000)
      driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password")
      Thread.sleep(1000)
      driver.findElement(By.className("float-right")).click()

      Then("Unread messages are bold")
      Thread.sleep(3000)
      assert(driver.findElements(By.cssSelector(".read-false")).size() >= 1)
      driver.close()
    }
  }
}
