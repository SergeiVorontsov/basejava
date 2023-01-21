package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Map;

import static com.basejava.webapp.util.DateUtil.of;
import static com.basejava.webapp.util.ResumeUtil.*;

public class ResumeTestData {
    public static Resume fillResume1(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(ContactType.PHONE, "777-77-77");
        resume.setContact(ContactType.EMAIL, "info@gmail.com");

        resume.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Objective1"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement1", "Achievement2", "Achievement3"));
        resume.setSection(SectionType.EXPERIENCE, new CompanySection(
                new Company("Company Name", "www.site.com",
                        new Company.Period("Position", "Description", "05.2020", "06.2020"),
                        new Company.Period("Position1", "Description1", "05.2021", "06.2021"),
                        new Company.Period("Position2", "Description2", "05.2022")
                ))
        );

        return resume;
    }

    public static Resume fillResume2(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(ContactType.PHONE, "777-77-78");
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Objective2"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Personal2"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement2.1", "Achievement2.2", "Achievement2.3"));
        resume.setSection(SectionType.EXPERIENCE, new CompanySection(
                new Company("Company Name2", "www.site2.com",
                        new Company.Period("Position1", "Description1", "04.2020", "07.2020"),
                        new Company.Period("Position2.1", "Description2.1", "04.2021", "07.2021"),
                        new Company.Period("Position3.2", "Description3.2", "04.2022", "07.2022")
                ))
        );
        return resume;
    }

    public static Resume fillResume3(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(ContactType.EMAIL, "mail12@gmail.com");
       resume.setSection(SectionType.OBJECTIVE, new TextSection("Objective21"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Personal21"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement2.11", "Achievement2.21", "Achievement2.31"));
        resume.setSection(SectionType.EXPERIENCE, new CompanySection(
                new Company("Company Name2", "www.site2.com",
                        new Company.Period("Position16", "Description16", "08.2020", "09.2020"),
                        new Company.Period("Position2.16", "Description2.16", "08.2021", "09.2021"),
                        new Company.Period("Position3.26", "Description3.26", "08.2022", "09.2022")
                ))
        );
        return resume;
    }

    public static Resume fillResume4(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(ContactType.PHONE, "777-77-80");
        resume.setContact(ContactType.EMAIL, "mail80@gmail.com");
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Objective210"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement2.110", "Achievement2.210", "Achievement2.310"));
        resume.setSection(SectionType.EXPERIENCE, new CompanySection(
                new Company("Company Name23", "www.site23.com",
                        new Company.Period("Position163", "Description163", "08.2020", "10.2020"),
                        new Company.Period("Position2.163", "Description2.163", "08.2021", "10.2021"),
                        new Company.Period("Position3.263", "Description3.263", "08.2022", "10.2022")
                ))
        );
        return resume;
    }

    public static void main(String[] args) {
        Resume kislin = createResume("123423", "Григорий Кислин");
        // System.out.println(kislin);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kislin.getFullName()).append("\n");
        for (Map.Entry<ContactType, String> contact : kislin.getContacts().entrySet()
        ) {
            stringBuilder.append(contact.getKey().getTitle()).append(": ");
            stringBuilder.append(contact.getValue()).append("\n");
        }
        for (Map.Entry<SectionType, Section> section : kislin.getSections().
                entrySet()
        ) {
            Section currentSection = section.getValue();
            if (currentSection instanceof TextSection) {
                stringBuilder.append(section.getKey().getTitle()).append(":\n");
                TextSection textSection = (TextSection) currentSection;
                stringBuilder.append(textSection.getContent()).append("\n");
            }
            if (currentSection instanceof ListSection) {
                stringBuilder.append(section.getKey().getTitle()).append(":\n");
                ListSection listSection = (ListSection) currentSection;
                if (listSection.getItems() instanceof ArrayList) {
                    ArrayList<String> array = (ArrayList<String>) listSection.getItems();
                    for (String s : array) {
                        stringBuilder.append(s).append(":\n");
                    }
                }
            }
            if (currentSection instanceof CompanySection) {
                stringBuilder.append((section.getKey().getTitle())).append(":\n");
                CompanySection companySection = (CompanySection) currentSection;
                if (companySection.getCompanies() instanceof ArrayList) {
                    ArrayList<Company> array = (ArrayList<Company>) companySection.getCompanies();
                    for (Company k : array) {
                        stringBuilder.append(k.getTitle()).append(" ");
                        stringBuilder.append(k.getWebsite()).append("\n");
                        for (Company.Period p : k.getPeriods()) {
                            stringBuilder.append(p.getStartDate()).append(" - ");
                            stringBuilder.append(p.getEndDate()).append("  ");
                            stringBuilder.append(p.getTitle()).append("\n");
                            stringBuilder.append(p.getDescription()).append("\n");
                        }
                    }
                }
            }
        }
        System.out.printf(stringBuilder.toString());

    }

    public static Resume createResume(String uuid, String fullName) {
        Resume kislin = new Resume(uuid, fullName);

        String email = "gkislin@yandex.ru";
        String tel = "+7(921) 855-0482";
        String skype = "grigory.kislin";
        String link = "https://www.linkedin.com/in/gkislin";
        String git = "https://github.com/gkislin";
        String stack = "https://stackoverflow.com/users/548473/grigory-kislin";
        String web = "https://gkislin.ru/";

        String objective = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        String personal = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";

        ArrayList<String> achievement = new ArrayList<>();
        achievement.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        ArrayList<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("Python: Django.");
        qualification.add("avaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualification.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualification.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualification.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualification.add("Родной русский, английский \"upper intermediate\"");

        addContact(kislin, ContactType.EMAIL, email);
        addContact(kislin, ContactType.PHONE, tel);
        addContact(kislin, ContactType.SKYPE, skype);
        addContact(kislin, ContactType.LINKEDIN, link);
        addContact(kislin, ContactType.GITHUB, git);
        addContact(kislin, ContactType.STACKOVERFLOW, stack);
        addContact(kislin, ContactType.WEB, web);

        addTextSection(kislin, SectionType.OBJECTIVE, objective);
        addTextSection(kislin, SectionType.PERSONAL, personal);

        addListSection(kislin, SectionType.ACHIEVEMENT, achievement);
        addListSection(kislin, SectionType.QUALIFICATIONS, qualification);

        {
            String companyName = "Alcatel";
            String website = "http://www.alcatel.ru/";
            String title = "Инженер по аппаратному и программному тестированию";
            String description = "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).";
            LocalDate startDate = of(1997, Month.SEPTEMBER);
            LocalDate endDate = of(2005, Month.JANUARY);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Siemens AG";
            String website = "https://www.siemens.com/ru/ru/home.html";
            String title = "Разработчик ПО";
            String description = "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).";
            LocalDate startDate = of(2005, Month.JANUARY);
            LocalDate endDate = of(2007, Month.FEBRUARY);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Enkata";
            String website = "http://enkata.com/";
            String title = "Разработчик ПО";
            String description = "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).";
            LocalDate startDate = of(2007, Month.MARCH);
            LocalDate endDate = of(2008, Month.JUNE);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Yota";
            String website = "https://www.yota.ru/";
            String title = "Ведущий специалист";
            String description = "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)";
            LocalDate startDate = of(2008, Month.JUNE);
            LocalDate endDate = of(2010, Month.DECEMBER);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Luxoft (Deutsche Bank)";
            String website = "http://www.luxoft.ru/";
            String title = "Ведущий программист";
            String description = "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.";
            LocalDate startDate = of(2010, Month.DECEMBER);
            LocalDate endDate = of(2012, Month.APRIL);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "RIT Center";
            String website = null;
            String title = "Java архитектор";
            String description = "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python";
            LocalDate startDate = of(2012, Month.APRIL);
            LocalDate endDate = of(2014, Month.OCTOBER);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Wrike";
            String website = "https://www.wrike.com/";
            String title = "Старший разработчик (backend)";
            String description = "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.";
            LocalDate startDate = of(2014, Month.OCTOBER);
            LocalDate endDate = of(2016, Month.JANUARY);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Java Online Projects";
            String website = "http://javaops.ru/";
            String title = "Автор проекта.";
            String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
            LocalDate startDate = of(2013, Month.OCTOBER);
            LocalDate endDate = LocalDate.now();
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Заочная физико-техническая школа при МФТИ";
            String website = "https://mipt.ru/";
            String title = "Закончил с отличием";
            String description = null;
            LocalDate startDate = of(1984, Month.SEPTEMBER);
            LocalDate endDate = of(1987, Month.JUNE);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики";
            String website = "http://www.ifmo.ru/";
            String title = "Инженер (программист Fortran, C)";
            String description = null;
            LocalDate startDate = of(1987, Month.SEPTEMBER);
            LocalDate endDate = of(1993, Month.JULY);

            String title2 = "Аспирантура (программист С, С++)";
            String description2 = null;
            LocalDate startDate2 = of(1993, Month.SEPTEMBER);
            LocalDate endDate2 = of(1996, Month.JULY);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate),
                    new Company.Period(title2, description2, startDate2, endDate2));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Alcatel";
            String website = "http://www.alcatel.ru/";
            String title = "6 месяцев обучения цифровым телефонным сетям (Москва)";
            String description = null;
            LocalDate startDate = of(1997, Month.SEPTEMBER);
            LocalDate endDate = of(1998, Month.MARCH);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Siemens AG";
            String website = "http://www.siemens.ru/";
            String title = "3 месяца обучения мобильным IN сетям (Берлин)";
            String description = null;
            LocalDate startDate = of(2005, Month.JANUARY);
            LocalDate endDate = of(2005, Month.APRIL);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Luxoft";
            String website = "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366";
            String title = "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.";
            String description = null;
            LocalDate startDate = of(2011, Month.MARCH);
            LocalDate endDate = of(2011, Month.APRIL);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        {
            String companyName = "Coursera";
            String website = "https://www.coursera.org/course/progfun";
            String title = "'Functional Programming Principles in Scala' by Martin Odersky";
            String description = null;
            LocalDate startDate = of(2013, Month.MARCH);
            LocalDate endDate = of(2013, Month.MAY);
            Company company = new Company(companyName, website,
                    new Company.Period(title, description, startDate, endDate));
            addCompany(kislin, company, SectionType.EXPERIENCE);
        }
        return kislin;
    }
}
