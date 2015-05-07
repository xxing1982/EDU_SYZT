package com.syzton.sunread;

import com.syzton.sunread.common.dto.ValidationErrorDTO;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;

/**
 */
public class UtilTest {


    @Test
    public void getLongDate() {
        DateTime now = DateTime.now();
        now.getMillis();
        System.out.print(now.getMillis());
    }

    @Test
    @Ignore
    public void htmlToPdf() throws Exception {
        generationPdfDzOrder(null);
    }

    public InputStream generationPdfDzOrder(Map<String ,Object> params) throws Exception{
        String tmpFileName = UUID.randomUUID().toString(); //生成随机文件名
        File dir = new File("/Users/jerry");
        if(!dir.exists())
            dir.mkdirs();
        String pdfFileName = "/Users/jerry/" + tmpFileName + ".pdf" ;
        File pdfFile = new  File(pdfFileName); //pdf文件
        String command = getCommand("http://www.sina.com" , pdfFileName);
        Runtime.getRuntime().exec(command);
        TimeUnit.SECONDS.sleep(3);
        return new FileInputStream(pdfFile);
    }

    public String getCommand(String htmlName , String pdfName){
        return  "wkhtmltopdf " + htmlName + " " + pdfName;
    }

}
