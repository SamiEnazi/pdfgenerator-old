package sa.stc.app;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class InputToHtml {

	@RequestMapping(value = "/create_pdf", method = RequestMethod.GET)
	public ResponseEntity<String> createPDF() {
		System.out.println("==================================================");

		try {
			String executable = WrapperConfig.findExecutable();
			Pdf pdf = new Pdf(new WrapperConfig(executable));
			pdf.cleanAllTempFiles();
			String htmlTemplate = StreamUtils.copyToString(
					new ClassPathResource("pdf_templat.html").getInputStream(),
					Charset.defaultCharset());

			// Map<String,String> map = new HashMap<String, String>();
			// map.put("###InvoiceNum###", "عربي بحت");
			// map.put("###Date###", LocalDateTime.now().toString());
			// map.put("###Shipping###",String.valueOf(50.0));
			// map.put("###Tax###",String.valueOf(10));
			//
			//
			// htmlTemplate = util.replaceAllWithMap(htmlTemplate,map);

			pdf.addPageFromString(htmlTemplate);
			// pdf.addParam(new
			// com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--no-footer-line"),
			// new com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--header-html",
			// "file:///header.html"));
			// pdf.addParam(new
			// com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--enable-javascript"));
			// pdf.addParam(new
			// com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--javascript-delay",
			// "2000"));

			pdf.addParam(new Param("--enable-local-file-access"));

			pdf.addParam(new Param("--no-pdf-compression"));

			File file = pdf.saveAs("src\\main\\resources\\pdf.pdf");

			return new ResponseEntity<String>("[!] Done\n[!]Path:" + file.getAbsolutePath(), HttpStatus.OK);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		System.out.println("==================================================");
		return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
