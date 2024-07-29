package solRetailIHM.Runner;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import solRetailIHM.ScenarioMainClass.ScenarioMain_ES;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Listeners(solRetailIHM.Runner.ListenerTest.class)

public class Runner_ES {

	@Test(description = "Handling Data before starting of the execution")
	@DataProvider
	public static Object[][] dataMethod() {
		Object[][] TCParam = null;
		try {

			// String ExcelPath =
			// "\\src\\test\\java\\solRetailIHM\\Datafiles\\Runner_Spain.xlsx";
			String ExcelPath = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "solRetailIHM",
					"Datafiles", "Runner_ES.xlsx").toString();
			FileInputStream FIP = new FileInputStream(ExcelPath);
			XSSFWorkbook wb = new XSSFWorkbook(FIP);
			XSSFSheet sh1 = wb.getSheet("Exec");
			XSSFSheet sh2 = wb.getSheet("TC table");
			DataFormatter formatter = new DataFormatter();

			int row1 = sh1.getLastRowNum();
			int col1 = sh1.getRow(0).getLastCellNum();
			int ExecIndex = 0;
			int TCIndex = 0;
			List<String> TCName = new ArrayList<String>();
			for (int k = 0; k < col1; k++) {
				String celldata = sh1.getRow(0).getCell(k).getStringCellValue();
				if (celldata.equalsIgnoreCase("Exec")) {
					ExecIndex = k;
				}
				if (celldata.equalsIgnoreCase("TC")) {
					TCIndex = k;
				}
			}
			// System.out.println(row1);
			for (int j = 1; j <= row1; j++) {
				String celldata = sh1.getRow(j).getCell(ExecIndex).getStringCellValue();
				String cellvalue;
				// System.out.println(j);
				if (celldata.equals("x")) {
					cellvalue = sh1.getRow(j).getCell(TCIndex).getStringCellValue();
					TCName.add(cellvalue);
				}
			}
			System.out.println(TCName);
			Assert.assertNotNull(TCName, "Test Scenario Name could not be identified");

			int row2 = sh2.getLastRowNum();
			int col2 = sh2.getRow(0).getLastCellNum();
			int TCIndex2 = 0;
			for (int k = 0; k < col2; k++) {
				String celldata = sh2.getRow(0).getCell(k).getStringCellValue();
				if (celldata.equalsIgnoreCase("TC")) {
					TCIndex2 = k;
				}
			}

			int x = 0;
			System.out.println(row2);
			TCParam = new String[TCName.size()][col2 - 1];
			for (int j = 1; j <= row2; j++) {
				String celldata = sh2.getRow(j).getCell(TCIndex2).getStringCellValue();
				System.out.println(celldata);
				System.out.println(j);
				if (TCName.contains(celldata)) {
					for (int l = 1; l < col2; l++) {
						String cellvalue = formatter.formatCellValue(sh2.getRow(j).getCell(l));
						TCParam[x][l - 1] = cellvalue;

					}
					x = x + 1;

				}

			}
		     
		     }catch(Exception e) {
		    	 e.printStackTrace();
		     }
			return TCParam;
    }
	
	
	    @Factory(dataProvider="dataMethod")	
		public Object[] createInstances(String Brand,
				                        String Country,
				                        String Digital1,
				                        String Digital1Flag,
				                        String URL,
				                        String ScenarioName,				                        
				                        String PaymentMode,
				                        String PaymentType,
				                        String HomePageChecks,
				                        String GuestUser,
				                        String UserRegister,
				                        String VehicleChoice,
				                        String TrimPageCheck,
				                        String BasketPageCheck,
				                        String ConfigPageCheck,
				                        String PersoV,
				                        String PostalCode,
				                        String City,
				                        String NIF,
				                        String Retailer,
				                        String EmailId,
				                        String Password,
				                        String Name,				                        
				                        String Phone,
				                        String Plate,
				                        String Address,
				                        String Fwidget,
				                        String MopValidation,
				                        String PXCheckoutPage,
				                        String PXConfigPage,
				                        String PXBO,
				                        String Catalan,
				                        String CarNumber,
		                                String CarDate,
		                                String CVC,
		                                String PromoCode,
										String DealerPage,
										String CarOptions) throws IOException {
		  return new Object[] {new ScenarioMain_ES(Brand,
				                                Country,
				                                Digital1,
				                                Digital1Flag,
				                                URL, 
				                                ScenarioName, 				                                
				                                PaymentMode,
				                                PaymentType,
				                                HomePageChecks,
				                                GuestUser,
				                                UserRegister,
				                                VehicleChoice,
				                                TrimPageCheck,
				                                BasketPageCheck,
				                                ConfigPageCheck,
				                                PersoV,
				                                PostalCode,
				                                City,
				                                NIF,
				                                Retailer,
				                                EmailId,
				                                Password,
				                                Name,
				                                Phone,
				                                Plate,
				                                Address,
				                                Fwidget,
				                                MopValidation,
				                                PXCheckoutPage,
				                                PXConfigPage,
						                        PXBO,
						                        Catalan, 
						                        CarNumber,
				                                CarDate,
				                                CVC,
				                                PromoCode,
				  								DealerPage,
				  								CarOptions)};
	}

}
