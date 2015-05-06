package com.syzton.sunread.controller.common;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by jerry on 5/3/15.
 */

@Controller
public class HtmlToPDF {
    @RequestMapping(value = "/html2pdf",method = RequestMethod.GET)
    protected void processRequest(@RequestParam("url") String url,HttpServletRequest request, HttpServletResponse response)
            throws  Exception {
        try {
            IOUtils.copy(generationPdfDzOrder(url), response.getOutputStream());
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e){
            System.out.print(e.toString());
        }finally {
            response.getOutputStream().close();
        }
    }

    public InputStream generationPdfDzOrder(String url) throws Exception{
        String tmpFileName = UUID.randomUUID().toString(); //生成随机文件名
        String pdfFileName = "/tmp/" + tmpFileName + ".pdf" ;
        File pdfFile = new  File(pdfFileName); //pdf文件
        String command = getCommand(url , pdfFileName);
        Runtime.getRuntime().exec(command);
        TimeUnit.SECONDS.sleep(5);
        InputStream inputStream = new FileInputStream(pdfFile);
        pdfFile.delete();
        return inputStream;
    }

    public String getCommand(String htmlName , String pdfName){
        return  "wkhtmltopdf  --javascript-delay 1000 " + htmlName + " " + pdfName;
    }

}
