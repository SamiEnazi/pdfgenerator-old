package sa.stc.app;

import java.io.File;
import java.io.IOException;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.exceptions.PDFExportException;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;

public class PDFCreator {
	String executable = WrapperConfig.findExecutable();
	Pdf pdf = new Pdf(new WrapperConfig(executable));

	public byte[] getPDF(String template)throws IOException,InterruptedException,PDFExportException {
		pdf.setTempDirectory(new File("src/main/resources/"));
		pdf.addPageFromString(template);
		pdf.addParam(new Param("--enable-local-file-access"));
		pdf.addParam(new Param("--dpi"), new Param("300"));
		pdf.addParam(new Param("--encoding"), new Param("utf-8"));
		pdf.addParam(new Param("--disable-smart-shrinking"));
		pdf.addParam(new Param("--margin-right"), new Param("1mm"));
		pdf.addParam(new Param("--margin-left"), new Param("1mm"));
		pdf.saveAs("pdf.pdf");
		return pdf.getPDF();
	}
	
}
