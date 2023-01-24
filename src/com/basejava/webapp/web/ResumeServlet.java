package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (value != null && value.trim().length() != 0) {
                        r.setSection(type, new TextSection(value.trim()));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> list = HtmlUtil.trimToStringArray(value);
                    if (value.trim().length() != 0) {
                        r.setSection(type, new ListSection(list));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    break;
            }
        }

        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        if (action.equals("clear")) {
            storage.clear();
            response.sendRedirect("resume");
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);

                break;
            case "create":
                r = new Resume("Новое резюме");
                r.getSections().put(SectionType.PERSONAL, new TextSection());
                r.getSections().put(SectionType.OBJECTIVE,new TextSection());
                r.getSections().put(SectionType.ACHIEVEMENT, new ListSection(""));
                r.getSections().put(SectionType.QUALIFICATIONS, new ListSection(""));
                r.getSections().put(SectionType.EXPERIENCE, new CompanySection(new Company("","",new Company.Period("","","",""))));
                r.getSections().put(SectionType.EDUCATION,new CompanySection(new Company("","",new Company.Period("","","",""))));
                storage.save(r);
                break;
            default:
                throw new IllegalStateException("Action " + action + "is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}