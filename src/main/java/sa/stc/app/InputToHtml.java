package sa.stc.app;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.exceptions.PDFExportException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class InputToHtml {
	private static final Logger logger = LoggerFactory.getLogger(InputToHtml.class);

	@RequestMapping(value = "/create_pdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> createPDF() {
		System.out.println("==================================================");

		try {

			String template = StreamUtils.copyToString(new ClassPathResource("pdf_templat.html").getInputStream(),
					Charset.forName("utf-8"));

			Map<String, String> values = new HashMap<String, String>();

			values.put("{{CustomerName}}", "Sami");
			values.put("{{CustomerID}}", "7788990076");
			values.put("{{CRDate}}", "28-08-1434");
			values.put("{{CRCity}}", "Riyadh");
			values.put("{{BuildingNumber}}", "66");
			values.put("{{Street}}", "fwerfgwer");
			values.put("{{Neighboorhood}}", "");
			values.put("{{City}}", "-");
			values.put("{{PostalCode}}", "33332");
			values.put("{{AdditionalNo}}", "");
			values.put("{{AuthPersonName}}", "Balivadas Sowmya");
			values.put("{{AuthPersonID}}", "2-1570-3329-7");
			values.put("{{AuthPersonContact}}", "532967786");
			values.put("{{AuthPersonEmail}}", "smatta.c@stc.com.sa");
			values.put("{{AuthPersonNationality}}", "Saudi Arabia");
			values.put("{{ContractNumber}}", "3-O9A3FF5");
			values.put("{{ContractDate}}", "");
			values.put("{{ContractPeriod}}", "2");

			String productDetails[][] = {
					{ "SDWANB", "SDWAN", "", "1", "1000", "5.34%", "956.7", "675.4", "" },
					{ "SDWANB", "SDWAN", "", "1", "1000", "5.34%", "956.7", "675.4", "" },
					{ "SDWANB", "SDWAN", "", "1", "1000", "5.34%", "956.7", "675.4", "" }
			};

			values.put("{{MainProductMRC}}", util.createRows(productDetails, true));

			String installationFeesNRC[][] = {
					{ "SDWANB", "Edge Router1 OTC", "", "1", "1200", "1200" },
					{ "SDWANB", "Edge Router1 OTC", "", "1", "1200", "1200" },
					{ "SDWANB", "Edge Router1 OTC", "", "1", "1200", "1200" }
			};

			values.put("{{InstallationFees/NRC}}", util.createRows(installationFeesNRC, true));

			String VASMonthlyRecurrentCharges[][] = {
					{ "SDWANB", "Edge Router1", "", "1", "0", "0" },
					{ "SDWANB", "Edge Router1", "", "1", "0", "0" },
					{ "SDWANB", "Edge Router1", "", "1", "0", "0" }
			};

			values.put("{{VASMonthlyRecurrentCharges}}", util.createRows(VASMonthlyRecurrentCharges, true));

			String DeviceDetails[][] = {};

			values.put("{{DeviceDetails}}", util.createRows(DeviceDetails, true));

			values.put("{{EmployeeName}}", "TEST_BBLEAD14 -");
			values.put("{{IDNumber}}", "248058");
			values.put("{{Nationality}}", "Saudi Arabia");
			values.put("{{ApprovalTransactionNumber}}", "3-O9AFS0W");
			values.put("{{ApprovalTransactionDate}}", "");
			values.put("{{ApprovalTransactionTime}}", "");

			String appendixList[][] = {
					{ "JEDDAH-JEDDAH SDWAN10164", "", "SDWAN" },
					{ "JEDDAH-JEDDAH SDWAN10164", "", "SDWAN" },
					{ "JEDDAH-JEDDAH SDWAN10164", "", "SDWAN" }
			};

			values.put("{{AppendixList}}", util.createRows(appendixList, false));

			template = util.replaceAllWithMap(template, values);

			// Appendix: List of Services END

			// Map<String,String> map = new HashMap<String, String>();
			// map.put("###InvoiceNum###", "عربي بحت");
			// map.put("###Date###", LocalDateTime.now().toString());
			// map.put("###Shipping###",String.valueOf(50.0));
			// map.put("###Tax###",String.valueOf(10));
			//
			//
			// htmlTemplate = util.replaceAllWithMap(htmlTemplate,map);

			// pdf.addParam(new
			// com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--no-footer-line"),
			// new com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--header-html",
			// "file:///header.html"));
			// pdf.addParam(new
			// com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--enable-javascript"));
			// pdf.addParam(new
			// com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--javascript-delay",
			// "2000"));

			// pdf.addPageFromString(htmlTemplate);
			// pdf.addPageFromFile("src/main/resources/pdf_templat.html");
			// pdf.setTempDirectory(new File("src/main/resources/"));
			// pdf.addPageFromString(template);
			// // wkhtmltopdf --enable-local-file-access --dpi 300 --disable-smart-shrinking
			// // --margin-left 1mm --margin-right 1mm pdf_templat.html pdf.pdf
			//
			// pdf.addParam(new Param("--enable-local-file-access"));
			// pdf.addParam(new Param("--dpi"), new Param("300"));
			// pdf.addParam(new Param("--encoding"), new Param("utf-8"));
			// pdf.addParam(new Param("--disable-smart-shrinking"));
			// pdf.addParam(new Param("--margin-right"), new Param("1mm"));
			// pdf.addParam(new Param("--margin-left"), new Param("1mm"));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "bill.pdf");

			// logger.info(System.getProperty("user.dir"));
			// File file = pdf.saveAs("pdf.pdf");

			System.out.println("==================================================");
			return new ResponseEntity<>(new PDFCreator().getPDF(template), headers, HttpStatus.OK);
		} catch (InterruptedException | IOException | PDFExportException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		System.out.println("==================================================");
		return new ResponseEntity<>("Error".getBytes(StandardCharsets.UTF_8), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
