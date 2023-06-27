import enums.Condition;
import enums.Status;
import gui.App;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static model.Book.booksFromDb;
import static model.Renovation.renovationsForBookFromDb;
import static model.Renovation.renovationsFromDb;
import static model.Restorer.loggedRestorer;

public class Main {

    public static void main(String[] args){


        Set<String> book1Categories = new HashSet<>();
        Set<String> book2Categories = new HashSet<>();
        Set<String> book3Categories = new HashSet<>();
        Set<String> book4Categories = new HashSet<>();
        Set<String> book5Categories = new HashSet<>();
        Set<String> book6Categories = new HashSet<>();
        Set<String> book7Categories = new HashSet<>();
        Set<String> book8Categories = new HashSet<>();
        Set<String> book9Categories = new HashSet<>();
        Set<String> book10Categories = new HashSet<>();
        Set<String> book11Categories = new HashSet<>();
        Set<String> book12Categories = new HashSet<>();
        Set<String> book13Categories = new HashSet<>();
        Set<String> book14Categories = new HashSet<>();




        // w klasie model.Book utworzenie encji jako klasa oraz atrybutow jako kolumny


//        Author author2 = new Author(1,"Steven", "Hawking", birthDate2, "British", "male", "big");
//        Author author3 = new Author(2, "Joanne", "Rowling", birthDate3, "British", "female", "potter");
//        Author author4 = new Author(3, "John", "Tolkien", birthDate4, "British", "male", "freak");
//        Author author5 = new Author(4, "Terry", "Brooks", birthDate5, "American", "male", "faceless");
//        Author author6 = new Author(5, "Henryk", "Sienkiewicz", birthDate6, "Polish", "male", "dreamer");

//        Library library = new Library(1, "model.Library", "Street", "City", "01-111");

//        Workstation workstation1 = new Workstation("A12", "Windows 12", "librarian");
//        Workstation workstation2 = new Workstation("A13", "Windows 12", "director");
//
//        // jeden do wiele - do jednej bilbioteki kilka stacji roboczych; jedna stacja robocza tylko w jednej bibliotece @OneToMany, @ManyToOne
//        workstation1.setLibrary(library);
//        workstation2.setLibrary(library);

//        // wiele do wiele - jeden autor moze napisac wiele ksiazek; jedna ksiazka moze miec wiele autorow @ManyToMany
//        author3.addBook(book1);
//        author3.addBook(book2);
//        author2.addBook(book1);
        //book1.addAuthor(author2);
        //book1.addAuthor(author3);
        //book2.addAuthor(author4);


       // Library library = new Library(1, "National library", "Pelczynskiego 110A", "Warsaw", "01-471");


        //Set<String> materials = new HashSet<>();
        //materials.add("glue");
        //.add("foil");

        //Renovation renovation1 = new Renovation(1, date1, materials, Status.FINISHED, "everything ok");
        //Renovation renovation2 = new Renovation(2, date2, materials, Status.STARTED, "not finished");

        //book1.addRenovationToBook(restorer2, renovation1);
        //book1.addRenovationToBook(restorer2, renovation2);
//        renovation1.setBook(book1);
//        renovation1.setRestorer(restorer2);
//
//        renovation2.setBook(book2);
//        renovation2.setRestorer(restorer3);



        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();


        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //renovationsFromDb = session.createQuery("FROM model.Renovation").list();

        //booksFromDb = session.createQuery("FROM model.Book JOIN FETCH model.Renovation").list();
        booksFromDb = session.createQuery("FROM model.Book").list();
        Query loginQuery = session.createQuery("FROM model.Restorer r WHERE r.id = :id");
        loginQuery.setParameter("id", 5);
        loggedRestorer = (Restorer) loginQuery.uniqueResult();
           session.getTransaction().commit();
           session.close();


        //booksFromDb = session.createQuery("FROM model.Book").list();
        //System.out.println(booksFromDb.get(0).getRenovations().);

        //System.out.println("dasdsa");
        //System.out.println(booksFromDb.get(book1));

        //Query query = session.createQuery("FROM model.Restorer WHERE model.Restorer.id = :id");
       // query.setParameter("id", 1);
       // Restorer restorerFromDb = (Restorer) query.uniqueResult();
       // System.out.println("restorer");
        //System.out.println(restorerFromDb.toString());

        //Query query = session.createQuery("FROM model.Renovation r WHERE r.restorer.id = :id");
        //Query query = session.createQuery("FROM model.Renovation r WHERE r.restorer.id = 1");
        //query.setParameter("id", restorer2.getId());
        //List<Renovation> renovations = query.list();

        //renovationsForBookFromDb = session.createQuery("From model.Renovation ").list();

//        for (Book book : booksFromDb){
//            System.out.println(book.toString());
//            System.out.println(book.getId());
//            System.out.println(book.getRenovations());
//        }
//
//        System.out.println(loggedRestorer.getRenovations());

       // for (Renovation ren : renovations){
        //    System.out.println(ren.toString());
        //    System.out.println(ren.getId());
        //}
        try {


       App app = new App();

       app.start();
//
//            book1Categories.add("fantastyka");
//
//            book2Categories.add("dramat");
//            book2Categories.add("powieść");
//
//            book3Categories.add("nauka");
//            book3Categories.add("fizyka");
//            book3Categories.add("astronomia");
//
//            book4Categories.add("poemat");
//
//            book5Categories.add("dramat");
//            book5Categories.add("historia");
//
//            book6Categories.add("historia");
//
//            book7Categories.add("historia");
//            book7Categories.add("geopolityka");
//            book7Categories.add("batalistyka");
//
//            book8Categories.add("fantastyka");
//
//            book9Categories.add("historia");
//
//            book10Categories.add("gotowanie");
//
//            book11Categories.add("historia");
//            book11Categories.add("geopolityka");
//
//            book12Categories.add("informatyka");
//
//            book13Categories.add("geografia");
//            book13Categories.add("nauka");
//
//            book14Categories.add("popularnonaukowa");
//
//
//
//            LocalDate birthDate1 = LocalDate.of(1981, 11, 11);
//            LocalDate birthDate2 = LocalDate.of(1986, 6, 1);
//            LocalDate birthDate3 = LocalDate.of(1965, 12, 1);
//            LocalDate birthDate4 = LocalDate.of(1991, 3, 13);
//            LocalDate birthDate5 = LocalDate.of(1997, 11, 10);
//
//
//            LocalDate hiringdate1 = LocalDate.of(2023, 1, 1);
//            LocalDate hiringdate2 = LocalDate.of(2023, 2, 1);
//            LocalDate hiringdate3 = LocalDate.of(2020, 12, 1);
//            LocalDate hiringdate4 = LocalDate.of(2023, 6, 13);
//            LocalDate hiringdate5 = LocalDate.of(2023, 6, 26);
//
//
//
//        Book book1 = new Book(1, "Harry Potter i Kamień Filozoficzny", book1Categories, Condition.ZŁA, 654, 2016, "Media Rodzina");
//        Book book2 = new Book(2, "Harry Potter i Komnata Tajemnic", book1Categories, Condition.DOBRA, 358, 2016, "Media Rodzina");
//        Book book3 = new Book(3, "Harry Potter i Więzień Azkabanu", book1Categories, Condition.DOBRA, 448, 2016, "Media Rodzina");
//        Book book4 = new Book(4, "Harry Potter i Czara Ognia", book1Categories, Condition.DOBRA, 768, 2016, "Media Rodzina");
//        Book book5 = new Book(5, "Harry Potter i Książę Półkrwi", book1Categories, Condition.DOBRA, 704, 2016, "Media Rodzina");
//        Book book6 = new Book(6, "Harry Potter i Insygnia Śmierci", book1Categories, Condition.DOBRA, 782, 2016, "Media Rodzina");
//        Book book7 = new Book(7, "Ojciec Chrzestny", book2Categories, Condition.DOBRA, 1088, 2019, "Albatros");
//        Book book8 = new Book(8, "Toeria wszystkiego", book3Categories, Condition.DOBRA, 144, 2018, "Editio");
//        Book book9 = new Book(9, "Czarny Łabędź", book14Categories, Condition.DOBRA, 698, 2020, "Zysk i S-ka");
//        Book book10 = new Book(10, "Pan Tadeusz", book4Categories, Condition.DOBRA, 352, 2022, "Wydawnictwo Dragon");
//        Book book11 = new Book(11, "Dziady", book5Categories, Condition.DOBRA, 282, 2021, "Wydawnictwo Siedmioróg");
//        Book book12 = new Book(12, "Medaliony", book6Categories, Condition.ZŁA, 62, 2011, "GREG");
//        Book book13 = new Book(13, "Błękitna kropka", book3Categories, Condition.ZŁA, 560, 2011, "Zysk i S-ka");
//        Book book14 = new Book(14, "Nadchodzi III wojna światowa", book7Categories, Condition.NOWA, 336, 2021, "Dom Wydawniczy Rebis");
//        Book book15 = new Book(15, "Krew elfów. Wiedźmin", book8Categories, Condition.DOBRA, 340, 2014, "SUPERNOWA");
//        Book book16 = new Book(16, "Polskie triumfy. 50 chwalebnych bitew naszej historii", book9Categories, Condition.DOBRA, 560, 2018, "Społeczny Instytut Wydawniczy Znak");
//        Book book17 = new Book(17, "Jadłonomia po polsku", book10Categories, Condition.NOWA, 280, 2022, "Wydawnictwo Marginesy");
//        Book book18 = new Book(18, "Serce Europy", book11Categories, Condition.DOBRA, 544, 2018, "Społeczny Instytut Wydawniczy Znak");
//        Book book19 = new Book(19, "Thinking in Java", book12Categories, Condition.ZNISZCZONA, 1248, 2006, "Helion");
//        Book book20 = new Book(20, "Powszechny Atlas Świata", book13Categories, Condition.ZNISZCZONA, 221, 2022, "Demart");
//
//        Restorer restorer1 = new Restorer(1, "Karol", "Szyszka", birthDate1, "mężczyzna", "polska", "001-001", hiringdate1, 7000, "Kondratowicza 17A/51, Warszawa", "sklejanie");
//        Restorer restorer2 = new Restorer(2, "Karolina", "Wacławiak", birthDate2, "kobieta", "polska", "001-002", hiringdate2, 11000, "Prosta 12/1, Warszawa", "zabezpieczanie");
//        Restorer restorer3 = new Restorer(3, "Robert", "Styrkosz", birthDate3, "mężczyzna", "polska", "001-003", hiringdate3, 11000, "Aleje Jerozolimskie 151, Warszawa", "brak");
//        Restorer restorer4 = new Restorer(4, "Izabela", "Damięcka", birthDate4, "kobieta", "polska", "001-004", hiringdate4, 13000, "Polska 12, Warszawa", "naprawa");
//        Restorer restorer5 = new Restorer(5, "Łukasz", "Łubik", birthDate5, "mężczyzna", "polska", "001-005", hiringdate5, 12000, "Polska 12, Warszawa", "sklejanie");
//
//
////
//            session.save(book1);
//            session.save(book2);
//            session.save(book3);
//            session.save(book4);
//            session.save(book5);
//            session.save(book6);
//            session.save(book7);
//            session.save(book8);
//            session.save(book9);
//            session.save(book10);
//            session.save(book11);
//            session.save(book12);
//            session.save(book13);
//            session.save(book14);
//            session.save(book15);
//            session.save(book16);
//            session.save(book17);
//            session.save(book18);
//            session.save(book19);
//            session.save(book20);
//
//            session.save(restorer1);
//            session.save(restorer2);
//            session.save(restorer3);
//            session.save(restorer4);
//            session.save(restorer5);
////          session.save(renovation1);
////          session.save(renovation2);
//            session.getTransaction().commit();
//            session.close();

        }
        catch (Exception ex) {
            ex.printStackTrace();
            StandardServiceRegistryBuilder.destroy( serviceRegistry );
        }
        finally {
            if (sessionFactory != null) {
                sessionFactory.close();
                sessionFactory = null;
            }
        }

    }
}
