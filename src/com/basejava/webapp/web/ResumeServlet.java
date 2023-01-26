package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.DateUtil;
import com.basejava.webapp.util.HtmlUtil;
import com.basejava.webapp.util.ResumeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                    r.getSections().remove(type);
                    saveWithoutNull(request, r, type);
                    break;
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException {
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
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                addEmptySection(r);
                break;
            case "create":
                r = ResumeUtil.EMPTY();
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

    private void addEmptySection(Resume r) {
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    if (r.getSections().get(type) == null) {
                        r.getSections().put(type, new TextSection(""));
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    if (r.getSections().get(type) == null) {
                        r.getSections().put(type, new ListSection(new ArrayList<>()));
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    if (r.getSections().get(type) == null) {
                        r.getSections().put(type, new CompanySection(new Company("", "", new Company.Period("", "", "", ""))));
                    } else {
                        ((CompanySection) r.getSections().get(type)).getCompanies().add(0, new Company("", "", new Company.Period("", "", "", "")));
                    }
                    for (Company company : ((CompanySection) r.getSections().get(type)).getCompanies()
                    ) {
                        if (company.getPeriods().isEmpty()) {
                            company.setPeriods(new Company.Period("", "", "", ""));
                        }
                    }
            }
        }
    }

    private void saveWithoutNull(HttpServletRequest request, Resume r, SectionType type) {
        r.getSections().put(type, new CompanySection(new ArrayList<>()));
        int i = 0;
        boolean paramExist = true;
        while (paramExist) {
            String name = request.getParameter(type.name() + i + "name");
            String website = request.getParameter(type.name() + i + "website");
            String startDate = request.getParameter(type.name() + i + "startDate");
            String endDate = request.getParameter(type.name() + i + "endDate");
            String title = request.getParameter(type.name() + i + "title");
            String description = request.getParameter(type.name() + i + "description");
            if (name != null && !name.equals("")) {
                Company company = null;
                List<Company> searchCompanyList = ((CompanySection) r.getSections().get(type)).getCompanies().stream().filter(Objects::nonNull)
                        .peek(c -> {
                            if (c.getTitle() == null) {
                                c.setTitle("dummy");
                            }
                        })
                        .filter(comp -> comp.getTitle().equals(name)).collect(Collectors.toList());
                boolean b = (title != null && !title.equals("")) || (description != null && !description.equals(""));
                if (!searchCompanyList.isEmpty()) {
                    if (b) {
                        Company existCompany = searchCompanyList.get(0);
                        if (website != null && !website.equals("")) {
                            existCompany.setWebsite(website);
                        }
                        existCompany.getPeriods().add(0, new Company.Period(title, description, startDate, endDate));
                    }
                } else if (b) {
                    Company.Period period = createPeriod(startDate, endDate, title, description);
                    company = new Company(name, null, period);
                    if (website != null && !website.equals("")) {
                        company.setWebsite(website);
                    }
                    company.setPeriods(period);
                }
                if (company != null) {
                    ((CompanySection) r.getSections().get(type)).getCompanies().add(company);
                }
            }
            i++;
            paramExist = (request.getParameter(type.name() + i + "name") != null);
        }
        if (((CompanySection) r.getSections().get(type)).getCompanies().isEmpty()) {
            r.getSections().remove(type);
        }

    }

    private Company.Period createPeriod(String startDate, String endDate, String title, String description) {
        Company.Period period = new Company.Period();
        period.setTitle(title);
        period.setDescription(description);
        if (startDate != null) {
            period.setStartDate(DateUtil.of(startDate));
        }
        if (endDate != null) {
            period.setEndDate(Objects.equals(DateUtil.of(endDate), LocalDate.now()) ? DateUtil.NOW() : DateUtil.of(endDate));
        }
        return period;
    }
}