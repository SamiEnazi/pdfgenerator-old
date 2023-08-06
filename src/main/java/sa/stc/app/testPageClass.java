package sa.stc.app;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ui")
public class testPageClass {
	private static final Logger logger = LoggerFactory.getLogger(testPageClass.class);
	@GetMapping("testPage")
	public ResponseEntity<String> testPage(){
		String template = "";
		try {
			template = StreamUtils.copyToString(new ClassPathResource("templates/download_button.html").getInputStream(),Charset.forName("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		
		
		return new ResponseEntity<String>(template,HttpStatus.OK);
	}
}
