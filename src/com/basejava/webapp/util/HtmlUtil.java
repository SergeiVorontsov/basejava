package com.basejava.webapp.util;

import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.model.TextSection;

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

    public static String formatSectionView(SectionType type, Section section) {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                String value = ((TextSection) section).getContent();
  /*              String s = JsonParser.read(value, Section.class);
                return JsonParser.read(value, Section.class);
                return JsonParser.read(value, Section.class);*/
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
    /*            List<String> list = new ArrayList<>(Arrays.asList(value.split("\n")));
                resume.setSection(type, new ListSection(list));*/
                break;
            case EXPERIENCE:
            case EDUCATION:
                break;
        }
        return null;
    }
}