package com.syzton.sunread.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by jerry on 3/12/15.
 */
public class DateSerializer extends JsonSerializer<DateTime> {

        private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        @Override
        public void serialize(DateTime value, JsonGenerator gen,
                              SerializerProvider arg2)
                throws IOException, JsonProcessingException {
            gen.writeString(formatter.print(value));
    }
}
