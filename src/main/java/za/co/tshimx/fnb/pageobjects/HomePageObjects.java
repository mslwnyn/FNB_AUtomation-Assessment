/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.tshimx.fnb.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 *
 * @author Tshimologo
 */
public class HomePageObjects {
    WebDriver driver;
    
    @FindBy(how=How.XPATH  , using="//a[@id='openaccount3']")
    WebElement openAccountLink;
    
    public HomePageObjects(WebDriver driver ){
        this.driver = driver;
    }

    

    public void clickOpenAccountLink() {
        this.openAccountLink.click();
    }
    
    
    
}
