package com.syzton.sunread;

import com.syzton.sunread.common.dto.FieldErrorDTO;
import com.syzton.sunread.common.dto.ValidationErrorDTO;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

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
}
