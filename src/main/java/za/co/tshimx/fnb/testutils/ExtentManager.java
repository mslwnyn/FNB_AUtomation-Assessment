/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.tshimx.fnb.testutils;

import com.relevantcodes.extentreports.ExtentReports;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tshimologo
 */
public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String pattern = "yyyyMMddHHmm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            extent = new ExtentReports(System.getProperty("user.dir") + "//reports//FNB_Automation_Assessment_Report_" + date + ".html");
            extent.loadConfig(new File(System.getProperty("user.dir") + "//ReportsConfig.xml"));
            extent.addSystemInfo("Selenium Version", "3.141.59").addSystemInfo("Environment", "QA");
        }
        return extent;
    }
}
