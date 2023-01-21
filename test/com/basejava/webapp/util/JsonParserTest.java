package com.basejava.webapp.util;

import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserTest {

    @Test
    public void write() throws Exception {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}