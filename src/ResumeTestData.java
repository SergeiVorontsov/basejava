import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
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

        Resume kislin = new Resume("Кислин Григорий");

        kislin.addContact(ContactType.EMAIL, email);
        kislin.addContact(ContactType.PHONE, tel);
        kislin.addContact(ContactType.SKYPE, skype);
        kislin.addContact(ContactType.LINKEDIN, link);
        kislin.addContact(ContactType.GITHUB, git);
        kislin.addContact(ContactType.STACKOVERFLOW, stack);
        kislin.addContact(ContactType.WEB, web);

        kislin.addSectionValue(SectionType.OBJECTIVE, objective);
        kislin.addSectionValue(SectionType.PERSONAL, personal);
        kislin.addSectionValue(SectionType.ACHIEVEMENT, achievement);
        kislin.addSectionValue(SectionType.QUALIFICATIONS, qualification);

        //  kislin.deleteSectionValue(SectionType.OBJECTIVE);
        //  kislin.deleteSectionValue(SectionType.QUALIFICATIONS);

        ArrayList<Company> experience = new ArrayList<>();
        {
            Company company = new Company();
            company.setTitle("Alcatel");
            company.setWebsite("http://www.alcatel.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Инженер по аппаратному и программному тестированию");
            period.setDescription("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
            period.setDateFrom(LocalDate.of(1997, Month.SEPTEMBER, 1));
            period.setDateTo(LocalDate.of(2005, Month.JANUARY, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Siemens AG");
            company.setWebsite("https://www.siemens.com/ru/ru/home.html");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Разработчик ПО");
            period.setDescription("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
            period.setDateFrom(LocalDate.of(2005, Month.JANUARY, 1));
            period.setDateTo(LocalDate.of(2007, Month.FEBRUARY, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Enkata");
            company.setWebsite("http://enkata.com/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Разработчик ПО");
            period.setDescription("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
            period.setDateFrom(LocalDate.of(2007, Month.MARCH, 1));
            period.setDateTo(LocalDate.of(2008, Month.JUNE, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Yota");
            company.setWebsite("https://www.yota.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Ведущий специалист");
            period.setDescription("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
            period.setDateFrom(LocalDate.of(2008, Month.JUNE, 1));
            period.setDateTo(LocalDate.of(2010, Month.DECEMBER, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Luxoft (Deutsche Bank)");
            company.setWebsite("http://www.luxoft.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Ведущий программист");
            period.setDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
            period.setDateFrom(LocalDate.of(2010, Month.DECEMBER, 1));
            period.setDateTo(LocalDate.of(2012, Month.APRIL, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("RIT Center");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Java архитектор");
            period.setDescription("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
            period.setDateFrom(LocalDate.of(2012, Month.APRIL, 1));
            period.setDateTo(LocalDate.of(2014, Month.OCTOBER, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Wrike");
            company.setWebsite("https://www.wrike.com/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Старший разработчик (backend)");
            period.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
            period.setDateFrom(LocalDate.of(2014, Month.OCTOBER, 1));
            period.setDateTo(LocalDate.of(2016, Month.JANUARY, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Java Online Projects");
            company.setWebsite("http://javaops.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Автор проекта.");
            period.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок.");
            period.setDateFrom(LocalDate.of(2013, Month.OCTOBER, 1));
            period.setDateTo(LocalDate.now());
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }

        kislin.getSections().get(SectionType.EXPERIENCE).addItem(experience);

        ArrayList<Company> education = new ArrayList<>();
        {
            Company company = new Company();
            company.setTitle("Заочная физико-техническая школа при МФТИ");
            company.setWebsite("https://mipt.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Закончил с отличием");
            period.setDateFrom(LocalDate.of(1984, Month.SEPTEMBER, 1));
            period.setDateTo(LocalDate.of(1987, Month.JUNE, 1));
            periods.add(period);
            company.setPeriods(periods);
            education.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
            company.setWebsite("http://www.ifmo.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Инженер (программист Fortran, C)");
            period.setDateFrom(LocalDate.of(1987, Month.SEPTEMBER, 1));
            period.setDateTo(LocalDate.of(1993, Month.JULY, 1));
            periods.add(period);
            Period period2 = new Period();
            period2.setTitle("Аспирантура (программист С, С++)");
            period2.setDateFrom(LocalDate.of(1993, Month.SEPTEMBER, 1));
            period2.setDateTo(LocalDate.of(1996, Month.JULY, 1));
            periods.add(period2);
            company.setPeriods(periods);
            education.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Alcatel");
            company.setWebsite("http://www.alcatel.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("6 месяцев обучения цифровым телефонным сетям (Москва)");
            period.setDateFrom(LocalDate.of(1997, Month.SEPTEMBER, 1));
            period.setDateTo(LocalDate.of(1998, Month.MARCH, 1));
            periods.add(period);
            company.setPeriods(periods);
            experience.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Siemens AG");
            company.setWebsite("http://www.siemens.ru/");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("3 месяца обучения мобильным IN сетям (Берлин)");
            period.setDateFrom(LocalDate.of(2005, Month.JANUARY, 1));
            period.setDateTo(LocalDate.of(2005, Month.APRIL, 1));
            periods.add(period);
            company.setPeriods(periods);
            education.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Luxoft");
            company.setWebsite("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.");
            period.setDateFrom(LocalDate.of(2011, Month.MARCH, 1));
            period.setDateTo(LocalDate.of(2011, Month.APRIL, 1));
            periods.add(period);
            company.setPeriods(periods);
            education.add(company);
        }
        {
            Company company = new Company();
            company.setTitle("Coursera");
            company.setWebsite("https://www.coursera.org/course/progfun");
            List<Period> periods = new ArrayList<>();
            Period period = new Period();
            period.setTitle("'Functional Programming Principles in Scala' by Martin Odersky");
            period.setDateFrom(LocalDate.of(2013, Month.MARCH, 1));
            period.setDateTo(LocalDate.of(2013, Month.MAY, 1));
            periods.add(period);
            company.setPeriods(periods);
            education.add(company);
        }
        kislin.getSections().get(SectionType.EDUCATION).addItem(education);

        //  System.out.println(kislin);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kislin.getFullName()).append("\n");
        for (Map.Entry<ContactType, Contact> contact : kislin.getContacts().entrySet()
        ) {
            stringBuilder.append(contact.getKey().getTitle()).append(": ");
            for (String s : contact.getValue().getValue()
            ) {
                stringBuilder.append(s).append(",");
            }
            stringBuilder.append("\n");
        }
        for (Map.Entry<SectionType, AbstractSection> section : kislin.getSections().entrySet()
        ) {
            if (section.getValue() instanceof TextSection) {
                stringBuilder.append(section.getKey().getTitle()).append(":\n");
                System.out.println(stringBuilder);
                TextSection textSection = (TextSection) section.getValue();
                if (textSection.getItem() instanceof String) {
                    String s = (String) textSection.getItem();
                    stringBuilder.append(s).append("\n");
                }
            }
            if (section.getValue() instanceof ListSection) {
                stringBuilder.append(section.getKey().getTitle()).append(":\n");
                ListSection listSection = (ListSection) section.getValue();
                if (listSection.getItem() instanceof ArrayList) {
                    ArrayList<String> array = (ArrayList) listSection.getItem();
                    for (String s : array) {
                        stringBuilder.append(s).append(":\n");
                    }
                }
            }
            if (section.getValue() instanceof CompanySection) {
                stringBuilder.append((section.getKey().getTitle())).append(":\n");
                CompanySection companySection = (CompanySection) section.getValue();
                if (companySection.getItem() instanceof ArrayList) {
                    ArrayList<Company> array = (ArrayList) companySection.getItem();
                    for (Company k : array) {
                        stringBuilder.append(k.getTitle()).append(" ");
                        stringBuilder.append(k.getWebsite()).append("\n");
                        for (Period p : k.getPeriods()) {
                            stringBuilder.append(p.getDateFrom()).append(" - ");
                            stringBuilder.append(p.getDateTo()).append("  ");
                            stringBuilder.append(p.getTitle()).append("\n");
                            stringBuilder.append(p.getDescription()).append("\n");
                        }
                    }
                }
            }
        }

        System.out.printf(stringBuilder.toString());
    }
}
