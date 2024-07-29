package solRetailIHM.ScenarioMainClass;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import solRetailIHM.PageObjectModel.PersonnalInfoPage;
import solRetailIHM.ProjSpecFunctions.CheckGeneralInfo.Basket.PaymentMethodBasketPage;
import solRetailIHM.ProjSpecFunctions.CheckGeneralInfo.HOME.CheckAccount;
import solRetailIHM.ProjSpecFunctions.CheckPage.CheckBasketPage;
import solRetailIHM.ProjSpecFunctions.CheckPage.CheckConfigPage;
import solRetailIHM.ProjSpecFunctions.CheckPage.CheckHomePage;
import solRetailIHM.ProjSpecFunctions.CheckPage.CheckTrimPage;
import solRetailIHM.ProjSpecFunctions.*;
import solRetailIHM.ProjSpecFunctions.ChooseCar.ChooseCarCashNonEc41;
import solRetailIHM.ProjSpecFunctions.LoginApplications.LoginApplicationRetail;
import solRetailIHM.Utilities.BaseClass;
import solRetailIHM.Utilities.UniversalMethods;
import solRetailIHM.PageObjectModel.DealerStepBeforeBasketPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static solRetailIHM.PageObjectModel.HomePage.getHomePageVahicleName;
import static solRetailIHM.ProjSpecFunctions.DealerStepBeforeBasket.getDealerPageVehicalename;

@Listeners(solRetailIHM.Runner.ListenerTest.class)
public class ScenarioMain_FR extends UniversalMethods {

    final String URL;
    final String Brand;
    final String Country;
    final String Digital1;
    final String Digital1Flag;
    final String ScenarioName;
    final String PaymentMode;
    final String PaymentType;
    final String HomePageChecks;
    final String GuestUser;
    final String UserRegister;
    final String VehicleChoice;
    final String TrimPageCheck;
    final String BasketPageCheck;
    final String ConfigPageCheck;
    final String PersoV;
    final String PostalCode;
    final String City;
    final String NIF;
    final String Retailer;
    final String EmailId;
    final String Password;
    final String Name;
    final String Phone;
    final String Plate;
    final String Address;
    final String Fwidget;
    final String MopValidation;
    final String PXCheckoutPage;
    final String PXConfigPage;
    final String PXBO;
    final String Catalan;
    final String CarNumber;
    final String CarDate;
    final String CVC;
    final String PromoCode;

    final String DealerPage;
    final String Options;
    public static WebDriver driver;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest logger;
    public static Object[] carPrices;
    public static String registerEmail;
    public static String registerPassword;
    public static String resultDirectory;

    public ScenarioMain_FR(String Brand, String Country, String Digital1, String Digital1Flag, String URL,
                           String ScenarioName, String PaymentMode, String PaymentType, String HomePageChecks, String GuestUser,
                           String UserRegister, String VehicleChoice, String TrimPageCheck, String BasketPageCheck,
                           String ConfigPageCheck, String PersoV, String PostalCode, String City, String NIF, String Retailer,
                           String EmailId, String Password, String Name, String Phone, String Plate, String Address, String Fwidget,
                           String MopValidation, String PXCheckoutPage, String PXConfigPage, String PXBO, String Catalan,
                           String CarNumber, String CarDate, String CVC, String PromoCode, String DealerPage, String Options) {
        super();
        this.Brand = Brand;
        this.Country = Country;
        this.Digital1 = Digital1;
        this.Digital1Flag = Digital1Flag;
        this.URL = URL;
        this.ScenarioName = ScenarioName;
        this.PaymentMode = PaymentMode;
        this.PaymentType = PaymentType;
        this.HomePageChecks = HomePageChecks;
        this.GuestUser = GuestUser;
        this.UserRegister = UserRegister;
        this.VehicleChoice = VehicleChoice;
        this.TrimPageCheck = TrimPageCheck;
        this.BasketPageCheck = BasketPageCheck;
        this.ConfigPageCheck = ConfigPageCheck;
        this.PersoV = PersoV;
        this.PostalCode = PostalCode;
        this.City = City;
        this.NIF = NIF;
        this.EmailId = EmailId;
        this.Password = Password;
        this.Name = Name;
        this.Phone = Phone;
        this.Plate = Plate;
        this.Address = Address;
        this.Fwidget = Fwidget;
        this.MopValidation = MopValidation;
        this.Retailer = Retailer;
        this.PXCheckoutPage = PXCheckoutPage;
        this.PXConfigPage = PXConfigPage;
        this.PXBO = PXBO;
        this.Catalan = Catalan;
        this.CarNumber = CarNumber;
        this.CarDate = CarDate;
        this.CVC = CVC;
        this.PromoCode = PromoCode;
        this.DealerPage = DealerPage;
        this.Options=Options;
    }

    @BeforeTest
    public void SetUp() throws Exception {
        BaseClass bc = new BaseClass();
        bc.projectSetup();

        String dateName = new SimpleDateFormat("EEE_dd_MMM_yyyy_HH_mm_ss").format(new Date());
        System.out.println(dateName);
        resultDirectory = Paths
                .get(System.getProperty("user.dir"), "test-output", "SolRetailIHM", "Results_" + dateName + "")
                .toString();
        System.out.println(resultDirectory);

        //File file = new File(resultDirectory);
        // true if the directory was created, false otherwise
		/*if (file.mkdirs()) {
			System.out.println("Directory is created!");
		} else {
			System.out.println("Failed to create directory!");
		}*/

        htmlReporter = new ExtentHtmlReporter(resultDirectory + "/NRTCampaignSolRetailIHM_" + dateName + ".html");
        // Create an object of Extent Reports
        //extent = new ExtentReports();
        //extent.attachReporter(htmlReporter);
        htmlReporter.config().setLevel();
        htmlReporter.config().setAutoCreateRelativePathMedia(true);
        htmlReporter.config().enableTimeline(true);
        htmlReporter.config().setReportName(ScenarioName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Automated Test SOL");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("NRT QA Auto Sol Retail IHM");
        htmlReporter.config().setCSS(".r-img { width: 100%; }");

        Process hostName = Runtime.getRuntime().exec("hostname");
        hostName.waitFor();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(hostName.getInputStream()));
        String UserHostName = stdInput.readLine();
        extent = new ExtentReports();
        extent.setSystemInfo("User Host Name: ", UserHostName);
        extent.setSystemInfo("UserName: ", System.getProperty("user.name"));
        extent.setSystemInfo("User Home: ", System.getProperty("user.home"));
        extent.setSystemInfo("User Language: ", System.getProperty("user.language"));
        extent.setSystemInfo("User Country: ", System.getProperty("user.country"));
        extent.setSystemInfo("Time Zone: ", System.getProperty("user.timezone"));
        extent.setSystemInfo("Operating System: ", System.getProperty("os.name"));
        extent.setSystemInfo("Operating System Version: ", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version: ", System.getProperty("java.specification.version"));
        extent.setSystemInfo("Java Exact Version: ", System.getProperty("java.version"));
        extent.setSystemInfo("Java Version Release Date: ", System.getProperty("java.version.date"));
        extent.setSystemInfo("CPU: ", System.getProperty("sun.cpu.isalist"));
        extent.setSystemInfo("Environment", "Automated Testing");
        extent.setSystemInfo("Launching URL", "URL");
        extent.attachReporter(htmlReporter);

    }

    @Test(priority = 1, description = "Scenario E2E", enabled = true)
    public void Scenario() throws Exception {
        try {
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-notifications");
            options.addArguments("disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--no-proxy-server");
            options.addArguments("start-maximized");
            options.addArguments("--no-sandbox");
            options.addArguments("chrome.switches",
                    "--disable-extensions --disable-extensions-file-access-check --disable-extensions-http-throttling");
            // options.addArguments("--incognito");
            options.addArguments("force-device-scale-factor=0.75");
            options.addArguments("high-dpi-support=0.75");
            // options.addArguments("--incognito");
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 1);
            prefs.put("profile.content_settings.exceptions.clipboard", 1);
            options.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(options);
            //driver= SelfHealingDriver.create(driver1);
            driver.manage().window().maximize();
            /*driver.manage().window().setSize(new Dimension(1920, 1080));*/
            driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
            extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
            logger = extent.createTest(ScenarioName);
            logger.assignDevice("Chrome: 115.0.5790.171");
            logger.assignCategory("Regression Testing");

            System.out.println(AnalysisStrategy.SUITE);
            System.out.println("ScenarioName : " + ScenarioName);

            // Login to Sol RETAIL IHM
            LoginApplicationRetail.login(resultDirectory, driver, extent, logger, Digital1, Digital1Flag, URL, Brand, Country);

            // Check Home Page

            if (HomePageChecks.equals("yes") && driver!=null) {
                CheckHomePage.HomePageDetails(resultDirectory, driver, extent, logger, Country, EmailId, Name, Phone, Address, Password, Brand,PaymentMode);
                //CheckAccount.checkForgotPassword(resultDirectory, driver, extent, logger, URL, Country, Brand, EmailId,PaymentMode);
                UserRegistration.checkExistingUserEmail(resultDirectory, driver, logger, URL, Brand, Country, EmailId);
            }

            // Check User registration Page
            if (UserRegister.equalsIgnoreCase("yes") && driver!=null) {
                UserRegistration.registration(resultDirectory, driver, extent, logger, Brand, Country);
                YopMailConfirmationMsg.validateEmail(resultDirectory, driver, extent, logger, Brand, Country);
                registerEmail = readFromProperties("RegisterEmail");
                registerPassword = readFromProperties("RegisterPassword");
                UserRegistration.login(resultDirectory, driver, extent, logger, Brand, Country, registerEmail,
                        registerPassword,PaymentMode);
                driver.quit();
            }

            // workflow cash non e-C41
            if ((PaymentMode.equals("Cash")) && (VehicleChoice.equals("non-ec41")) && driver!=null) {

                // Choose vehicle
                carPrices = ChooseCarCashNonEc41.chooseCarModel(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);

                // Check TrimPage
                if (TrimPageCheck.equalsIgnoreCase("yes") && driver!=null) {
                    CheckTrimPage.TrimPageCheck(resultDirectory, driver, extent, logger, Brand, Country, PostalCode, EmailId, Name, Phone, Address,PaymentMode);
                   //(comment) ChooseCarCashNonEc41.chooseCarModel(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);
                }

                ChooseCarCashNonEc41.chooseCarType(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PaymentMode);
                //if (!Brand.equalsIgnoreCase("OV")||ScenarioName.contains("Retail")) {
                  if(isElementPresent(driver,ChooseCarCashNonEc41.error)){
                    if (isElementPresent(driver, ChooseCarCashNonEc41.homeButton)) {
                        clickUsingJS(driver, ChooseCarCashNonEc41.homeButton);
                        carPrices = ChooseCarCashNonEc41.chooseCarModel(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);
                        ChooseCarCashNonEc41.chooseCarType(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PaymentMode);
                    }
                }
                // Check ConfigPage
                if (ConfigPageCheck.equalsIgnoreCase("yes") && driver!=null) {
                    CheckConfigPage.ConfigPageDetails(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PostalCode, EmailId, Password, Name, Phone, Address, PaymentMode);
                }

                // choose car options
                if (Options.equals("yes") && driver!=null) {
                      ChooseCarOptions.CarOptions(resultDirectory, driver, extent, logger, Brand, Country,PaymentMode);
                }
                // New dealer-step page Before Basket page

                DealerStepBeforeBasket.goToDealerStep(resultDirectory, driver, extent, logger, Country, PostalCode, DealerPage,Brand,PaymentMode);
                if (DealerPage.equalsIgnoreCase("yes") && driver!=null) {
                    DealerStepBeforeBasket.verifyTheDealerChoice(resultDirectory, driver, extent, logger, Country, PostalCode, EmailId, Name, Phone, Brand);
                } else {
                    DealerStepBeforeBasket.DealerPagePASS(resultDirectory, driver, extent, logger, Country, PostalCode, EmailId, Name, Phone, Brand);
                }

                // Go to Basket
                if(!ScenarioName.contains("PX_Retail")||(!Brand.equalsIgnoreCase("DS")&&!Brand.equalsIgnoreCase("AP")&&!Brand.equalsIgnoreCase("OV")&&!Brand.equalsIgnoreCase("AC"))){
                    if(!(ScenarioName.contains("Complete")&&(Brand.equalsIgnoreCase("DS")||Brand.equalsIgnoreCase("OV")||Brand.equalsIgnoreCase("AC")))){
                        //if(!(Brand.equalsIgnoreCase("AC")&&Country.equalsIgnoreCase("FR")&&ScenarioName.contains("E2E")))
                        GoToBasket.goToBasket(resultDirectory, driver, extent, logger, Country, PaymentMode, VehicleChoice, DealerPage, Brand,ScenarioName);
                    }
                }
                PaymentMethodBasketPage.selectPaymentOption(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);
                if (!ScenarioName.contains("PX_Retail") && driver!=null) {
                    if (BasketPageCheck.equalsIgnoreCase("yes")) {
                        CheckBasketPage.basketPageCheck(resultDirectory, driver, extent, logger, Brand, Country,
                                PaymentMode, VehicleChoice, PostalCode, EmailId, Password, Name, Phone, Address, Plate, PromoCode,PersoV);
                    }

                    // Validate Basket
                    ValidateBasket.navigateToDealerPage(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, PostalCode, DealerPage);
                    waitForPageToLoad(driver,15);
                    if(BasketPageCheck.equalsIgnoreCase("yes") && !(Brand.equalsIgnoreCase("OV")) && driver!=null ) {
                        ValidateBasket.checkPrices(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, PostalCode, "Identification");
                    }

                    // Login
                    if (UserRegister.equalsIgnoreCase("yes") && driver!=null) {
                        registerEmail = readFromProperties("RegisterEmail");
                        registerPassword = readFromProperties("RegisterPassword");
                        if (!Brand.equals("OV")) {
                            LoginUser.loginAccount(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, registerEmail, registerPassword);
                        }
                        if (Brand.equals("OV")) {
                            CheckPersonnalInfoCash.checkAndModifyPersonalDetails(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PostalCode, City, NIF, registerEmail, Name, Phone, Address,PersoV,PaymentMode);
                        } else {
                            CheckPersonnalInfoCash.registerUserPersonalDetails(resultDirectory, driver, extent, logger, Country, VehicleChoice, PostalCode, City, NIF, registerEmail, Phone, Address, Brand, PersoV);
                        }
                    } else if (GuestUser.equalsIgnoreCase("yes") && driver!=null) {
                        String email=IdentificationPageCash.fillEmailCash(resultDirectory, driver, extent, logger, Country, VehicleChoice, PostalCode, City, NIF, EmailId, Name, Phone, Address, Brand, PaymentMode);
                        IdentificationPageCash.registerUserPersonalDetails(resultDirectory, driver, extent, logger, Country, VehicleChoice, PostalCode, City, NIF, email, Phone, Address, Brand, Name);
                    } else {
                        if (!Brand.equals("OV")) {
                            CheckPersonnalInfoCash.PersonalInfoPgDetails(resultDirectory,driver,extent,logger,Brand, Country, PaymentMode);
                            LoginUser.loginAccount(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, EmailId, Password);
                        }
                        CheckPersonnalInfoCash.checkAndModifyPersonalDetails(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PostalCode, City, NIF, EmailId, Name, Phone, Address,PersoV,PaymentMode);
                    }

                    if (!Brand.equals("OV")) {
                        if (BasketPageCheck.equalsIgnoreCase("yes") && driver!=null) {
                            ValidateBasket.checkPrices(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, PostalCode, "Reprise");
                            ReprisePXcheckout.repriseFullPartExchangeStep(resultDirectory, driver, extent, logger, Country);
                           // ReprisePXcheckout.repriseDoneAndSelectNoTradeInOffer(resultDirectory, driver, extent, logger, Country,PersoV, Brand, PaymentMode);
                            ValidateBasket.checkPrices(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, PostalCode, "OrderSummary");

                        } else {
                                ReprisePXcheckout.selectNoRepriseOptionAndSubmit(resultDirectory, driver, extent, logger, Country, PersoV, PaymentMode, EmailId, PostalCode, City,Phone, Brand, Name);
                        }
                    } else {
                        ValidateBasket.checkPrices(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, PostalCode, "Identification");

                    }

                    // Validate Order
                    ValidateOrderCash.orderValidation(resultDirectory, driver, extent, logger, Brand, Country, Catalan, PaymentMode,Name,PostalCode,EmailId,City,Phone);

                    // PaymentCash
                    PaymentCash.Payment(resultDirectory, driver, extent, logger, Brand, Country, MopValidation, CarNumber, CarDate, CVC, PaymentMode,EmailId,PostalCode,City,Phone,Name);
                }


            }

                // workflow finance non e-c41
                if ((PaymentMode.equals("Finance")) && (VehicleChoice.equals("non-ec41")) && driver!=null) {

                    // Choose vehicle
                    carPrices = ChooseCarCashNonEc41.chooseCarModel(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);

                    // Check TrimPage
                    if (TrimPageCheck.equalsIgnoreCase("yes") && driver!=null) {

                        CheckTrimPage.TrimPageCheck(resultDirectory, driver, extent, logger, Brand, Country, PostalCode, EmailId, Name, Phone, Address,PaymentMode);
                        //ChooseCarCashNonEc41.chooseCarModel(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);
                    }

                    ChooseCarCashNonEc41.chooseCarType(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PaymentMode);
                    if(isElementPresent(driver,ChooseCarCashNonEc41.error)){
                        if (isElementPresent(driver, ChooseCarCashNonEc41.homeButton)) {
                            clickElement(driver, ChooseCarCashNonEc41.homeButton);
                            carPrices = ChooseCarCashNonEc41.chooseCarModel(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);
                            ChooseCarCashNonEc41.chooseCarType(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PaymentMode);
                        }
                    }
                    // Check ConfigPage
                    if (ConfigPageCheck.equalsIgnoreCase("yes") && driver!=null) {
                        CheckConfigPage.ConfigPageDetails(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PostalCode, EmailId, Password, Name, Phone, Address, PaymentMode);
                    }

                    // choose car options
                    if (Options.equals("yes") && driver!=null) {
                        ChooseCarOptions.CarOptions(resultDirectory, driver, extent, logger, Brand, Country,PaymentMode);
                    }

                    DealerStepBeforeBasket.goToDealerStep(resultDirectory, driver, extent, logger, Country, PostalCode, DealerPage,Brand,PaymentMode);
                    if (DealerPage.equalsIgnoreCase("yes") && driver!=null) {
                        DealerStepBeforeBasket.verifyTheDealerChoice(resultDirectory, driver, extent, logger, Country, PostalCode, EmailId, Name, Phone, Brand);
                    } else {
                        DealerStepBeforeBasket.DealerPagePASS(resultDirectory, driver, extent, logger, Country, PostalCode, EmailId, Name, Phone, Brand);
                    }

                    // check basket page
                    GoToBasket.goToBasket(resultDirectory, driver, extent, logger, Country, PaymentMode, VehicleChoice, DealerPage, Brand, ScenarioName);
                    //DealerStepBeforeBasket.verifyTheDealerChoice(resultDirectory, driver, extent, logger, Country, PostalCode,EmailId, Name, Phone,Brand);
                    PaymentMethodBasketPage.selectPaymentOption(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode);
                    if (!ScenarioName.contains("PX_Retail") && driver!=null) {
                        if (BasketPageCheck.equalsIgnoreCase("yes") && driver!=null) {
                            CheckBasketPage.basketPageCheck(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, PostalCode, EmailId, Password, Name, Phone, Address, Plate, PromoCode,PersoV);
                        }

                        // Validate Basket
                        ValidateBasket.navigateToDealerPage(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, PostalCode, DealerPage);

				/*// Choose Dealer
				ChooseDealerCash.searchAndSelectRetailer(resultDirectory, driver, extent, logger, Brand, Country,
						VehicleChoice, Retailer, PaymentMode,DealerPage);*/

                        // Login
                        if (UserRegister.equalsIgnoreCase("yes") && driver!=null) {
                            registerEmail = readFromProperties("RegisterEmail");
                            registerPassword = readFromProperties("RegisterPassword");
                            if (!Brand.equals("OV")) {
                                LoginUser.loginAccount(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, registerEmail, registerPassword);
                            }
                            if (Brand.equals("OV")) {
                                CheckPersonnalInfoCash.checkAndModifyPersonalDetails(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PostalCode, City, NIF, registerEmail, Name, Phone, Address,PersoV, PaymentMode);
                            } else {
                                CheckPersonnalInfoCash.registerUserPersonalDetails(resultDirectory, driver, extent, logger, Country, VehicleChoice, PostalCode, City, NIF, registerEmail, Phone, Address, Brand, PersoV);
                            }
                        } else if (GuestUser.equalsIgnoreCase("yes") && driver!=null) {
                            IdentificationPageCash.fillEmailCash(resultDirectory, driver, extent, logger, Country, VehicleChoice, PostalCode, City, NIF, EmailId, Name, Phone, Address, Brand, PaymentMode);
                            IdentificationPageCash.registerUserPersonalDetails(resultDirectory, driver, extent, logger, Country, VehicleChoice, PostalCode, City, NIF, EmailId, Phone, Address, Brand, Name);
                        } else {
                            if (!Brand.equals("OV")) {
                                //CheckPersonnalInfoCash.validateRetailerAddress(resultDirectory,driver,extent,logger,Brand, Country);
                                LoginUser.loginAccount(resultDirectory, driver, extent, logger, Brand, Country, PaymentMode, VehicleChoice, EmailId, Password);
                            }
                            CheckPersonnalInfoCash.checkAndModifyPersonalDetails(resultDirectory, driver, extent, logger, Brand, Country, VehicleChoice, PostalCode, City, NIF, EmailId, Name, Phone, Address,PersoV,PaymentMode);
                        }

                        if (!Brand.equals("OV")) {
                            if (BasketPageCheck.equalsIgnoreCase("yes") && driver!=null) {
                                ReprisePXcheckout.repriseFullPartExchangeStep(resultDirectory, driver, extent, logger, Country);
                                ReprisePXcheckout.repriseDoneAndGoToNextStep(resultDirectory, driver, extent, logger, Country, PaymentMode);
                            } else {
                                ReprisePXcheckout.selectNoRepriseOptionAndSubmit(resultDirectory, driver, extent, logger, Country,PersoV, PaymentMode, EmailId, PostalCode, City, Phone, Brand, Name);
                            }
                        }

                        // MOP check
                        ValidateOrderFinance.orderValidation(resultDirectory, driver, extent, logger, Brand, Country, MopValidation, PaymentMode, ScenarioName, EmailId, PostalCode, City, Phone, Name);

                    }
                    }

                if (driver != null) {
                    driver.quit();
                }
        } catch (Exception e) {
            //logger.log(Status.FAIL,"Test Failed");
            e.printStackTrace();
            failWithScreenshot("test Failed", resultDirectory, driver, extent, logger);
        } finally {
            if (driver != null) {
                driver.quit();
                extent.flush();
            }
        }
    }


	/*@AfterTest
	public static void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
		extent.flush();
	}*/

}