package com.zt.homework.Utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomJsonDateDeserializer extends JsonDeserializer<Timestamp> {
    @Override
    public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = jp.getText();
        try {
            return DateUtil.Date2Timestamp(format.parse(date));
        } catch (ParseException e1) {
            Long time=new Long(date);
            String d = format.format(time);
            try {
                return DateUtil.Date2Timestamp(format.parse(d));
            } catch (ParseException e2) {
                throw new RuntimeException(e2);
            }
        }
    }
}