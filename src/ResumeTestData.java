import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.SectionType;

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

Resume kislin = new Resume("Кислин Григорий");


        // Kislin.getContacts().put(ContactType.EMAIL, new Contact(ContactType.EMAIL,"gkislin@yandex.ru"));
        //  Kislin.getContacts().get(ContactType.EMAIL).setValue("gkislin@yandex.ru");
        kislin.addContact(ContactType.EMAIL, email);
        kislin.addContact(ContactType.PHONE, tel);
        kislin.addContact(ContactType.SKYPE, skype);
        kislin.addContact(ContactType.LINKEDIN, link);
        kislin.addContact(ContactType.GITHUB,git);
        kislin.addContact(ContactType.STACKOVERFLOW,stack);
        kislin.addContact(ContactType.WEB,web);

        kislin.getSections().get(SectionType.OBJECTIVE);


//kislin.deleteContact(ContactType.EMAIL, "gkislin@yandex.ru");
      System.out.println(kislin + kislin.getContacts().toString() + kislin.getSections().toString());
     //   Contact1 contact = new Contact1();
      //  contact.addValue("Hi!");
      //  System.out.println(contact);
    }
}
