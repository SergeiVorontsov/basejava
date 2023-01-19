package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        List<Resume> resumes = storage.getAllSorted();
        StringBuilder builder = new StringBuilder();
        builder.append("<head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\"></head>");
        builder.append("<div class=\"container\" style=\"margin-top: 30px\">\n" +
                "<div class=\"row\">\n" +
                "<div class=\"col-md-12\">\n" +
                "<div class=\"card\">\n" +
                "<div class=\"card-header\">Таблица резюме\n" +
                "<div class=\"card-body\">\n" +
                "<table class=\"table table-striped\">\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>uuid</th>\n" +
                "<th>Имя</th>\n" +
                "</tr>\n" +
                "</thead>" +
                "<tbody>");

        for (Resume resume : resumes) {
            String uuid = resume.getUuid();
            String fullName = resume.getFullName();
            builder.append("<tr>\n" +
                            " <td>\n").append(uuid).append("</td>\n")
                    .append(" <td>\n").append(fullName).append("</td>\n")
                    .append(" </tr>\n");
        }

        builder.append(" </tbody>\n" +
                "</table>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n");
        response.getWriter().write(builder.toString());
    }
}