package com.product.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfConfig {

	
	@RequestMapping(value = "/downLoadPdf",method = RequestMethod.GET)
	public void downLoadPdf(String path ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("application/pdf");
		OutputStream os =  response.getOutputStream();
		File file = new File(path);
		byte[] b = new byte[1024];
		int len;
		InputStream is = new FileInputStream(file);
		while((len = is.read(b)) != -1){
			os.write(b);
		}
		os.flush();
		os.close();
	}
	
}
