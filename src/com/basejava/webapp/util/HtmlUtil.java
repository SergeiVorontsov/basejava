package com.basejava.webapp.util;

import com.basejava.webapp.model.ContactType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HtmlUtil {
    public static String formatContactView(ContactType type, String value) {
        String result = "";
        switch (type) {
            case PHONE:
                result = type.getTitle() + ": " + value;
                break;
            case EMAIL:
                result = type.getTitle() + ": " + toLink("mailto:" + value, value);
                break;
            case SKYPE:
                result = type.getTitle() + ": " + toLink("skype:" + value, value);
                break;
            case WEB:
            case GITHUB:
            case STACKOVERFLOW:
            case LINKEDIN:
                result = toLink(value, type.getTitle());
                break;
        }
        return result;
    }

    private static String toLink(String href, String title) {
        return "<a href='" + href + "' >" + title + "</a>";
    }

    public static List<String> trimToStringArray(String text) {
        String s = text.replaceAll(" +", " ");
        String s1 = s.replaceAll("\r", "");
        String s2 = s1.replaceAll("\n+", "\n");
        String[] strings = s2.split("\n");
        List<String> list = new ArrayList<String>();
        Arrays.stream(strings).forEach(s32 -> {
            String result = s32.trim();
            list.add(result);
        });
        return list;
    }
}