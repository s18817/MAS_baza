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

        book1Categories.add("drama");
        book1Categories.add("historic");
        book1Categories.add("novel");
        book2Categories.add("science");
        book3Categories.add("fantasy");
        book4Categories.add("fantasy");
        book5Categories.add("science fiction");
        book6Categories.add("historic");
        book6Categories.add("novel");

        LocalDate birthDate1 = LocalDate.of(1981, 11, 11);
        LocalDate birthDate2 = LocalDate.of(1942, 1, 8);
        LocalDate birthDate3 = LocalDate.of(1965, 7, 31);
        LocalDate birthDate4 = LocalDate.of(1892, 1, 3);
        LocalDate birthDate5 = LocalDate.of(1944, 1, 8);
        LocalDate birthDate6 = LocalDate.of(1846, 5, 5);
        LocalDate birthDate7 = LocalDate.of(1964, 5, 5);
        LocalDate birthDate8 = LocalDate.of(1966, 1, 15);
        LocalDate birthDate9 = LocalDate.of(1950, 6, 11);


        // w klasie model.Book utworzenie encji jako klasa oraz atrybutow jako kolumny
        Book book1 = new Book(1, "Zbrodnia i Kara", book1Categories, Condition.BAD, 576, 2000, "Media");
        Book book2 = new Book(2, "Zbrodniacxzfsdfs i Kara", book1Categories, Condition.BAD, 576, 2000, "Media");

        Author author2 = new Author(1,"Steven", "Hawking", birthDate2, "British", "male", "big");
        Author author3 = new Author(2, "Joanne", "Rowling", birthDate3, "British", "female", "potter");
        Author author4 = new Author(3, "John", "Tolkien", birthDate4, "British", "male", "freak");
        Author author5 = new Author(4, "Terry", "Brooks", birthDate5, "American", "male", "faceless");
        Author author6 = new Author(5, "Henryk", "Sienkiewicz", birthDate6, "Polish", "male", "dreamer");

//        Library library = new Library(1, "model.Library", "Street", "City", "01-111");

//        Workstation workstation1 = new Workstation("A12", "Windows 12", "librarian");
//        Workstation workstation2 = new Workstation("A13", "Windows 12", "director");
//
//        // jeden do wiele - do jednej bilbioteki kilka stacji roboczych; jedna stacja robocza tylko w jednej bibliotece @OneToMany, @ManyToOne
//        workstation1.setLibrary(library);
//        workstation2.setLibrary(library);

        // wiele do wiele - jeden autor moze napisac wiele ksiazek; jedna ksiazka moze miec wiele autorow @ManyToMany
        author3.addBook(book1);
        author3.addBook(book2);
        author2.addBook(book1);
        //book1.addAuthor(author2);
        //book1.addAuthor(author3);
        //book2.addAuthor(author4);


        LocalDate date1 = LocalDate.of(2022, 11, 1);
        LocalDate date2 = LocalDate.of(2023, 01, 12);
       // Library library = new Library(1, "National library", "Pelczynskiego 110A", "Warsaw", "01-471");
       Restorer restorer2 = new Restorer(1, "Karol", "Szyszka", birthDate7, "male", "polish", "001-004", date1, 7000, "Polska 12, Warszawa", "cleaning");

        Set<String> materials = new HashSet<>();
        materials.add("glue");
        materials.add("foil");

        Renovation renovation1 = new Renovation(1, date1, materials, Status.FINISHED, "everything ok");
        Renovation renovation2 = new Renovation(2, date2, materials, Status.STARTED, "not finished");

        book1.addRenovationToBook(restorer2, renovation1);
        book1.addRenovationToBook(restorer2, renovation2);
        renovation1.setBook(book1);
        renovation2.setBook(book1);
        renovation1.setRestorer(restorer2);
        renovation2.setRestorer(restorer2);



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


        System.out.println("dasdsa");
        //System.out.println(booksFromDb.get(book1));

        //Query query = session.createQuery("FROM model.Restorer WHERE model.Restorer.id = :id");
       // query.setParameter("id", 1);
       // Restorer restorerFromDb = (Restorer) query.uniqueResult();
       // System.out.println("restorer");
        //System.out.println(restorerFromDb.toString());

        //Query query = session.createQuery("FROM model.Renovation r WHERE r.restorer.id = :id");
        Query query = session.createQuery("FROM model.Renovation r WHERE r.restorer.id = 1");
        //query.setParameter("id", restorer2.getId());
        List<Renovation> renovations = query.list();

        //renovationsForBookFromDb = session.createQuery("From model.Renovation ").list();

        for (Book book : booksFromDb){
            System.out.println(book.toString());
            System.out.println(book.getId());
        }

        for (Renovation ren : renovations){
            System.out.println(ren.toString());
            System.out.println(ren.getId());
        }

        Query loginQuery = session.createQuery("FROM model.Restorer r WHERE r.id = :id");
        loginQuery.setParameter("id", 1);
        loggedRestorer = (Restorer) loginQuery.uniqueResult();

        App app = new App();

        app.start();

        Book book3 = new Book(3, "Harry Potter 1", book1Categories, Condition.GOOD, 654, 2013, "Media");
        Book book4 = new Book(3, "Harry Potter 2", book1Categories, Condition.BAD, 654, 2015, "Media");
        Book book5 = new Book(4, "Harry Potter 3", book1Categories, Condition.DESTROYED, 554, 2017, "Media");
        Book book6 = new Book(5, "Harry Potter 5", book1Categories, Condition.NEW, 875, 2019, "Media");
        Book book7 = new Book(6, "Harry Potter 6", book1Categories, Condition.GOOD, 421, 2021, "Media");
        Book book8 = new Book(7, "Harry Potter 7", book1Categories, Condition.GOOD, 332, 2023, "Media");


        try {
//            session.save(book1);
//            session.save(book2);
//            session.save(book3);
//            session.save(book4);
//            session.save(book5);
//            session.save(book6);
//            session.save(book7);
//            session.save(book8);
//
//
//            session.save(author3);
//            session.save(author2);
//            session.save(restorer2);
//            session.save(renovation1);
//            session.save(renovation2);

            session.getTransaction().commit();
            session.close();
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
