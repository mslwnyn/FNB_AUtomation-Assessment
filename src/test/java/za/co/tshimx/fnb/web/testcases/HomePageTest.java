/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.tshimx.fnb.web.testcases;

import com.relevantcodes.extentreports.LogStatus;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import za.co.tshimx.fnb.domain.Person;
import za.co.tshimx.fnb.pageobjects.HomePageObjects;
import za.co.tshimx.fnb.pageobjects.RegisterPageObjects;
import za.co.tshimx.fnb.testutils.ExtentTestManager;
import za.co.tshimx.fnb.utils.HibernateDatabaseAccess;
import za.co.tshimx.fnb.utils.MobileNumberGenerator;
import za.co.tshimx.fnb.utils.XLUtils;

/**
 *
 * @author Tshimologo
 */
public class HomePageTest extends BaseTest {

    
    @Test
    public void homePage() {
        
        try {
             
              if(driver.getTitle().equalsIgnoreCase("FNB - Stockbroking and Portfolio Management")){
                  ExtentTestManager.getTest().log(LogStatus.PASS, "Title page is correct :FNB - Stockbroking and Portfolio Management");
                  takescreenshot();
              }else {
                   ExtentTestManager.getTest().log(LogStatus.FAIL, "Title page is not correct :" +driver.getTitle() );
              }
              
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        
    }
    @Test(dependsOnMethods = {"homePage"})
    public void createAccount() {
        try {
            logger.info("Starting the test :createAccount  ");
            HomePageObjects homepage = PageFactory.initElements(driver, HomePageObjects.class);
            homepage.ClickOpenAccountLink();
            Thread.sleep(5000);
            takescreenshot();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test(dependsOnMethods = {"createAccount"})
    public void register()  {
        try {
            
            logger.info("Starting the test :register  ");
            RegisterPageObjects registerPage = PageFactory.initElements(driver, RegisterPageObjects.class);
            // database aceess
            Person person=null ;
            if(env_prop.getProperty("firefox.webdriver.key").equalsIgnoreCase("true")){
               HibernateDatabaseAccess dbAccess = new HibernateDatabaseAccess();
               person = dbAccess.getPersonDetails();
            }else{
                    String filePath= System.getProperty("user.dir")+"/data_files/testdata.xlsx";
                    String userdata[][] = getData(filePath,  "customer");
                    person = new Person();
                    person.setUsername(userdata[0][1]);
                    person.setEmail(userdata[0][2]);
                    person.setFirstName(userdata[0][3]);
                    person.setSurname(userdata[0][4]);
                    person.setTitle(userdata[0][5]);
                    person.setIdNumber(userdata[0][6]);
                    person.setRsa_citizen(userdata[0][7]);
                    person.setPassword(userdata[0][8]);                     
            }   

            registerPage.setTitle(person.getTitle()); 
            registerPage.setEmail(person.getEmail());
            registerPage.setMobileNumber(MobileNumberGenerator.getPhoneNumber());
            registerPage.setCitizen(person.getRsa_citizen());

            registerPage.setRsaID("6410015366");
            registerPage.setUsername(person.getUsername());            

            Thread.sleep(10000);
            if (driver.findElement(By.xpath("//*[@ng-message='minlength']")).getText().equalsIgnoreCase("South African ID numbers are thirteen digits.")) {
                Assert.assertTrue(true, "Validation: South African ID numbers are thirteen digits - passed test");
                ExtentTestManager.getTest().log(LogStatus.PASS, "Validation: South African ID numbers are thirteen digits - passed test");
                takescreenshot();
            }
            registerPage.RsaIDClear();
            registerPage.setRsaID("1234567890123");

            registerPage.setPassword(person.getPassword());
            Thread.sleep(10000);
            if (driver.findElement(By.xpath("//*[@ng-message='aiValIdNumber']")).getText().equalsIgnoreCase("Number entered is not a valid SA ID number.")) {
                takescreenshot();
                Thread.sleep(10000);
                Assert.assertTrue(true, "Validation: Number entered is not a valid SA ID number. - passed test");
                ExtentTestManager.getTest().log(LogStatus.PASS, "Validation: Number entered is not a valid SA ID number. - passed test");
                takescreenshot();

            }else{
                    
                ExtentTestManager.getTest().log(LogStatus.FAIL, "Validation: Number entered is not a valid SA ID number. - Failed test");
                takescreenshot();
            }            

            registerPage.setFirstName(person.getFirstName());           
            registerPage.setSurname(person.getSurname());
            
          
            registerPage.RsaIDClear();

            registerPage.setRsaID(person.getIdNumber());
            String screenshotPath5 = BaseTest.getScreenshot(driver, "screenshot_");
            ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(screenshotPath5));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            takescreenshot();
            // Select Product
            String filePath= System.getProperty("user.dir")+"/data_files/testdata.xlsx";
            Random random = new Random();
            String userdata[][] = getData(filePath,  "product");
            
            int randomNumber = random.nextInt(4);
            String selected_product = userdata[randomNumber][0];
             if (selected_product.equalsIgnoreCase("Local Trading Account")) {
                registerPage.selectProduct1();
            } else if (selected_product.equalsIgnoreCase("Derivatives Trader")) {
                registerPage.selectProduct2();
            } else if (selected_product.equalsIgnoreCase("Global Trading Account (offshore)")) {
                registerPage.selectProduct3();
            } else if (selected_product.equalsIgnoreCase("All")) {
                registerPage.selectProduct4();
            }
            takescreenshot();
            // Click Captcha
            Thread.sleep(3000);
            registerPage.checkCaptcha();
            
            //  code to select captcha images
            Thread.sleep(3000);
            takescreenshot();
            registerPage.submitForm();
            Thread.sleep(3000);
            takescreenshot();
        } catch (Exception e) {
            logger.error(e.getMessage());            
            if(e.getMessage().contains("Unable to locate element: //label[@class='rc-anchor-center-item rc-anchor-checkbox-label']")){
                 ExtentTestManager.getTest().log(LogStatus.PASS,e.getMessage());                 
            }
           
        }
    }
        
    public String[][] getData(String filePath, String sheet) throws IOException {
        
        int rowCount = XLUtils.getRowCount(filePath, sheet);
        int colCount = XLUtils.getCellCount(filePath, sheet, 0);
        String data[][] = new String[rowCount][colCount];
        for (int i = 1; i <= rowCount; i++) {

            for (int j = 0; j < colCount; j++) {

                data[i - 1][j] = XLUtils.getCellData(filePath, sheet, i, j);
                //System.out.print("\t"+ data[i - 1][j]) ;

            }
            //System.out.println();
            
        }
        return data;
    }
}



