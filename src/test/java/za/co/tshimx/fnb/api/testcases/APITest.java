package za.co.tshimx.fnb.api.testcases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.LogStatus;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import za.co.tshimx.fnb.api.APIClient;
import za.co.tshimx.fnb.domain.UsersPage;
import za.co.tshimx.fnb.testutils.ExtentTestManager;



public class APITest extends BaseTest {
     final static Logger logger = Logger.getLogger(APITest.class);
    String serviceURL;
    String apiURL;
    String uri;
    APIClient apiClient;
    CloseableHttpResponse  closeableHttpResponse;

    @BeforeMethod
    public void setUP() throws Exception {
        logger.info("API BeforeMethod : Setting up " );
        serviceURL = env_prop.getProperty("serviceURL");
        apiURL = env_prop.getProperty("apiURL");
        uri = serviceURL + apiURL;
        logger.info("API BeforeMethod url : " + uri);
      
    }

    @Test
    public void getAPITestMethod() {
        try{
            
            apiClient = new APIClient();
            
            HashMap<String,String> headerMap = new HashMap<String,String>();
            headerMap.put("Content-Type","application/json");
//          headerMap.put("username","test@gmail.com");
//          headerMap.put("password","Wap234*");
//          headerMap.put("Auth Token","34523");
            
            closeableHttpResponse = apiClient.getCloseableHttpResponse(uri,headerMap);  
            Thread.sleep(20000);
            //1.  get status code
            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                logger.info("Status code is  " + statusCode);
                ExtentTestManager.getTest().log(LogStatus.INFO, "Status code is  " + statusCode);
             }else{
                 logger.info("Status code is  " + statusCode);
                 ExtentTestManager.getTest().log(LogStatus.FAIL, "Status code is  " + statusCode);
             }
           
            
            //2. Json to
            String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
            ExtentTestManager.getTest().log(LogStatus.INFO,"JSON OBJECT: " + responseString);
            
            JSONObject responseJson = new JSONObject(responseString);
            logger.info("String responseString"+ responseString);
            logger.info("JSONObject responseJson : "+ responseJson);
            
            
            UsersPage usersPage= jsonToJavaObject(responseString);
            writetoFile(usersPage.toString());  
            ExtentTestManager.getTest().log(LogStatus.INFO,"Java object to String: "  + jsonToJavaObject(responseString));
            
            
            if (usersPage.getPer_page()==6){
                ExtentTestManager.getTest().log(LogStatus.PASS,"per_page is as expected :  "+ usersPage.getPer_page());
            }
            if(usersPage.getTotal()==12){
                ExtentTestManager.getTest().log(LogStatus.PASS,"total is as expected :  "+ usersPage.getTotal());
            }
//            if(usersPage.getTotal()==12){
//                ExtentTestManager.getTest().log(LogStatus.PASS,"total is as expected :  "+ usersPage.getTotal());
//            }
            if(usersPage.getTotal_pages()==2){
                ExtentTestManager.getTest().log(LogStatus.PASS,"total_pages is as expected :  "+ usersPage.getTotal_pages());
            }
            if(usersPage.getPage()==2){
                ExtentTestManager.getTest().log(LogStatus.PASS,"page is as expected :  "+ usersPage.getPage());
            }
            
            
            if(usersPage.getData().get(0).getFirst_name().equalsIgnoreCase("Michael")){
                ExtentTestManager.getTest().log(LogStatus.PASS,"Data [0] FirstName is as expected :  "+ usersPage.getData().get(0).getFirst_name());
            }
            if(usersPage.getData().get(0).getEmail().equalsIgnoreCase("michael.lawson@reqres.in")){
                ExtentTestManager.getTest().log(LogStatus.PASS,"Data [0] Email is as expected :  "+ usersPage.getData().get(0).getEmail());
            }
            if(usersPage.getData().get(0).getId()==7){
                ExtentTestManager.getTest().log(LogStatus.PASS,"Data [0] id is as expected :  "+ usersPage.getData().get(0).getId());
            }
            
            if(usersPage.getData().get(0).getAvatar().equalsIgnoreCase("https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg")){
                ExtentTestManager.getTest().log(LogStatus.PASS,"Data [0] Avatar is as expected :  "+ usersPage.getData().get(0).getAvatar());
            }
            if(usersPage.getData().get(0).getAvatar().equalsIgnoreCase("Lawson")){
                ExtentTestManager.getTest().log(LogStatus.PASS,"Data [0] lastname is as expected :  "+ usersPage.getData().get(0).getLast_name());
            }
            
            //3.All Headers
            Header[] headerArray = closeableHttpResponse.getAllHeaders();
            HashMap allHeaders = new HashMap<String, String>();
            for (Header header : headerArray) {
                allHeaders.put(header.getName(), header.getValue());
            }
            logger.info("headerArray : "+ headerArray);
            
            ExtentTestManager.getTest().log(LogStatus.INFO,"HEADERS "+ allHeaders.toString());
        }catch(Exception e){
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.FAIL,e.getMessage());
           // Assert.fail(e.getMessage());
        }
        

    }
    
    public void writetoFile (String fileContent) throws IOException
    {

        FileWriter fileWriter = new FileWriter("./apiFile//api.txt");
        fileWriter.write(fileContent);
        fileWriter.close();
    }

    public UsersPage jsonToJavaObject (String input){
        UsersPage usersPage =null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            usersPage = mapper.readValue(input, UsersPage.class);
            System.out.println(usersPage);

        }catch(Exception e){
            e.printStackTrace();
        }
        return usersPage;
    }
}
