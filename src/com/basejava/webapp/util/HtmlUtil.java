package com.basejava.webapp.util;

import com.basejava.webapp.model.ContactType;

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
                result = toLink(value,type.getTitle());
                break;
        }
        return result;
    }

    private static String toLink(String href, String title) {
        return "<a href='" + href + "' >" + title + "</a>";
    }
}