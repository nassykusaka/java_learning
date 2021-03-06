package learning.page_object;

import learning.page_object.buisness_object.User;
import learning.page_object.pages.*;
import learning.page_object.utils.WebDriverSingleton;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class YandexMailTest {
    private static final String[] EMAIL_DATA = {"test@gmail.com", "Selenium webdriver",
            "The primary new feature in Selenium 2.0 is the integration of the WebDriver API. " +
            "WebDriver is designed to provide a simpler, more concise programming interface in addition to addressing " +
            "some limitations in the Selenium-RC API. \nSelenium-WebDriver was developed to better support dynamic " +
            "web pages where elements of a page may change without the page itself being reloaded. \nWebDriver’s " +
            "goal is to supply a well-designed object-oriented API that provides improved support for modern " +
            "advanced web-app testing problems."};

    private String authorization(User user){
        new HomePage().open();

        LoginPage logForm = new LoginPage();
        logForm.fillLoginCredentials(user);
        return logForm.returnTitle();
    }
    private String incorrectAuthorization(User user){
        new HomePage().open();

        LoginPage logForm = new LoginPage();
        logForm.fillIncorrectLoginCredentials(user);
        return logForm.returnTitle();
    }

    @Test(enabled = true, description="check logging in with incorrect credentials")
    public void loginFailTest(){
        String winTitle = incorrectAuthorization(new User()); //incorrect pass
        Assert.assertEquals(winTitle, "Авторизация");
    }

    @Test(dependsOnMethods = {"loginFailTest"})
    public void loginTest(){
        String winTitle = authorization(new User());
        Assert.assertEquals(winTitle, "Yandex.Mail");
    }

    @Test(enabled = true, dependsOnMethods = {"loginTest"})
    public void createDraftTest(){
        Menu mailBoxMenu = new Menu();
        learning.page_object.pages.NewLetter newLetterForm = mailBoxMenu.openNewMailForm();
        newLetterForm.fillNewLetterForm(EMAIL_DATA);
        mailBoxMenu.openDraftsFolder();
        newLetterForm.saveDraft();

        Assert.assertEquals(new LettersContainer().getLastMessageSubject(), EMAIL_DATA[1]);
        Assert.assertEquals(new LettersContainer().getLastMessageAddress(), EMAIL_DATA[0]);
    }

    @Test(enabled=true, dependsOnMethods = {"createDraftTest"})
    public void sendDraftTest(){
        LettersContainer drafts = new LettersContainer();
        learning.page_object.pages.NewLetter currentLetter = drafts.openLastMessage();
        learning.page_object.pages.SendResultPage result = currentLetter.sendLetter();
        Assert.assertEquals(result.getStatus(), "Message sent successfully.");
    }

    @Test(enabled = true, dependsOnMethods = {"sendDraftTest"})
    public void checkDraftTest(){
        LettersContainer lettersList = new Menu().openDraftsFolder();
        try{
            lettersList.getIsEmptyListLocator();
        }catch (NoSuchElementException e){
            Assert.assertTrue(false);
        };
    }

    @Test(enabled = true, dependsOnMethods = {"sendDraftTest"})
    public void checkSentTest(){
        LettersContainer mailList = new Menu().openSentFolder();
        String title = mailList.getLastMessageSubject();
        String email = mailList.getLastMessageAddress();

        Assert.assertEquals(title, EMAIL_DATA[1]);
        Assert.assertEquals(email, EMAIL_DATA[0]);
    }

    @Test(enabled = true, description = "clean sent folder", dependsOnMethods = {"checkSentTest"})
    public void cleanSentFolder(){
        LettersContainer mailList = new LettersContainer();
        mailList.chooseAllMessage();
        mailList.pushDelete();
        try{
            mailList.getIsEmptyListLocator();
        }catch (NoSuchElementException e){
            Assert.assertTrue(false);
        };
    }

    @Test(enabled = true, dependsOnMethods = {"cleanSentFolder"})
    public void logoutTest(){
        Menu mailBoxMenu = new Menu();
        mailBoxMenu.openAccountMenu();
        mailBoxMenu.logoutSubmit();
        Assert.assertEquals(mailBoxMenu.returnTitle(), "Яндекс");
    }

    @AfterClass(description = "close browser")
    public void closure(){
        WebDriverSingleton.kill();
    }
}
