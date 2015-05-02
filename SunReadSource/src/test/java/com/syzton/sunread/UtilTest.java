package com.syzton.sunread;

import com.syzton.sunread.common.dto.ValidationErrorDTO;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

//    @Test
//    public void htmlToPdf(){
//        Document document = new Document();
//        try {
//            StyleSheet st = new StyleSheet();
//            st.loadTagStyle("body", "leading", "16,0");
//            PdfWriter.getInstance(document, new FileOutputStream("/Users/jerry/Desktop/test.pdf"));
//            document.open();
//            ArrayList p = HTMLWorker.parseToList(new FileReader("/Users/jerry/Desktop/test2.html"), st);
//            for(int k = 0; k < p.size(); ++k) {
//                document.add((Element)p.get(k));
//            }
//            document.close();
//            System.out.println("文档创建成功");
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
}
