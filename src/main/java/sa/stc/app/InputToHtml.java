package sa.stc.app;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class InputToHtml {
	private static final Logger logger = LoggerFactory.getLogger(InputToHtml.class);
	
	@RequestMapping(value="/create_pdf",method=RequestMethod.GET)
	public ResponseEntity<byte[]> createPDF() {
		System.out.println("==================================================");
		
		
		
		try {
			String executable = WrapperConfig.findExecutable();
			Pdf pdf = new Pdf(new WrapperConfig(executable));
			pdf.cleanAllTempFiles();
			String htmlTemplate = StreamUtils.copyToString( 
					new ClassPathResource("pdf_templat.html").getInputStream(),
					Charset.defaultCharset()
					);
			String css = StreamUtils.copyToString( 
					new ClassPathResource("styles.css").getInputStream(),
					Charset.defaultCharset()
					);
			
//			Map<String,String> map = new HashMap<String, String>();
//			map.put("###InvoiceNum###", "عربي بحت");
//			map.put("###Date###",  LocalDateTime.now().toString());
//			map.put("###Shipping###",String.valueOf(50.0));
//			map.put("###Tax###",String.valueOf(10));
//			
//			
//			htmlTemplate = util.replaceAllWithMap(htmlTemplate,map);
			
//			pdf.addParam(new com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--no-footer-line"), new com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--header-html", "file:///header.html"));
//			pdf.addParam(new com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--enable-javascript"));
//			pdf.addParam(new com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param("--javascript-delay", "2000"));
			
			pdf.addPageFromString(htmlTemplate);
			
			//wkhtmltopdf --enable-local-file-access --dpi 300 --disable-smart-shrinking --margin-left 1mm --margin-right 1mm pdf_templat.html pdf.pdf
			pdf.addParam(new Param("--enable-local-file-access"));
			pdf.addParam(new Param("--dpi"),new Param("300"));
			pdf.addParam(new Param("--disable-smart-shrinking"));
			pdf.addParam(new Param("--margin-right"),new Param("1mm"));
			pdf.addParam(new Param("--margin-left"),new Param("1mm"));
			
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", "bill.pdf"); // Replace 'file.pdf' with the desired file name

	        logger.info(System.getProperty("user.dir"));
			File file = pdf.saveAs("src\\main\\resources\\pdf.pdf");
			return new ResponseEntity<>(pdf.getPDF(),headers,HttpStatus.OK);
		} catch (InterruptedException|IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		System.out.println("==================================================");
		return new ResponseEntity<>("Error".getBytes(StandardCharsets.UTF_8),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
