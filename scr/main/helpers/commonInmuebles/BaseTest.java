package main.helpers.commonInmuebles;

import com.aventstack.extentreports.Status;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;

import helpers.ScreenShotHelper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import reporte.ReportManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BaseTest {
    public static XSSFSheet sheet;
    protected WebDriver webDriver;

    @BeforeSuite
    public static void setUpExcel() throws IOException {
        //configuracion excel
        File file = new File(System.getProperty("user.dir")+"\\data\\"+"datosVehiculos"+".xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        sheet = wb.getSheet("libro1");
    }

    @BeforeSuite
    public static void setUpSuite() throws Exception {
        ReportManager.init("C:\\Reports", "LoginSite");
    }

    @BeforeMethod
    public void configurar(ITestResult iTestResult){

        ReportManager.getInstance().startTest(iTestResult.getMethod().getMethodName());

        //String urlDominioAmbiente = "https://vehiculosacepta.ruat.gob.bo";
        String urlDominioAmbiente = "https://vehiculoscalidad.ruat.gob.bo";

        System.setProperty("webdriver.gecko.driver", "resource/geckodriver.exe");
        webDriver = new FirefoxDriver();
        webDriver.get(urlDominioAmbiente+"/VehiculosWeb/Administracion/Autentificacion/index.jsp");

        webDriver.manage().window().maximize();
    }

    @AfterMethod
    public void concluir(ITestResult iTestResult){
        try {
            switch (iTestResult.getStatus()){
                case ITestResult.FAILURE:
                    ReportManager.getInstance().getTest().log(Status.FAIL, "Test con falla");
                    ScreenShotHelper.takeScreenShotAndAdToHTMLReport(webDriver, Status.INFO, "Test con falla");
                    break;
                case ITestResult.SKIP:
                    ReportManager.getInstance().getTest().log(Status.SKIP, "Test omitida");
                    break;
                case ITestResult.SUCCESS:
                    ReportManager.getInstance().getTest().log(Status.PASS, "Test exitoso");
                    ScreenShotHelper.takeScreenShotAndAdToHTMLReport(webDriver, Status.INFO, "Paso exitoso");
                    break;
                default:
                    ReportManager.getInstance().getTest().log(Status.FAIL, "Test incompleto");
            }

            if(iTestResult.getStatus() != ITestResult.SUCCESS && iTestResult.getThrowable() != null){
                ReportManager.getInstance().getTest().log(Status.FAIL, iTestResult.getThrowable().getMessage());
                ScreenShotHelper.takeScreenShotAndAdToHTMLReport(webDriver, Status.FAIL, "Imagen con falla");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(webDriver != null)
              webDriver.quit();
        }
    }

    @AfterSuite
    public static void tearDownSuite(){
     ReportManager.getInstance().flush();
    }


    
}
