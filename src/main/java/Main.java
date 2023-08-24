import enums.Condition;
import enums.State;
import enums.Status;
import gui.App;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static model.Book.booksFromDb;
import static model.Restorer.loggedRestorer;

public class Main {

    public static void main(String[] args) throws Exception {

//        Set<String> testCategories = new HashSet<>();
//        Set<String> testCategories2 = new HashSet<>();
//        testCategories.add("fantastyka");
//        testCategories2.add("nauka");
        LocalDate testBirthDate = LocalDate.of(1982, 1, 3);
        LocalDate testBirthDate2 = LocalDate.of(1933, 10, 4);
        LocalDate testBirthDate3 = LocalDate.of(1919, 5, 1);
        LocalDate testBirthDate4 = LocalDate.of(1995, 11, 21);
//
        LocalDate testHiringDate = LocalDate.of(2020, 1, 1);
        LocalDate testHiringDate2 = LocalDate.of(2021, 1, 1);
        LocalDate testHiringDate3 = LocalDate.of(2022, 1, 1);
//        LocalDate testHiringDate4 = LocalDate.of(2023, 1, 1);
//
        LocalDate testFrom = LocalDate.of(2023, 6, 29);
        LocalDate testTo = LocalDate.of(2023, 7, 1);
//
//        LocalDate testRenovationDate = LocalDate.of(2023, 06,28);
        LocalDate testInventoryDate = LocalDate.of(2023, 06,28);
//
        Set<String> testLanguages = new HashSet<>();
        testLanguages.add("angielski");
        testLanguages.add("francuski");
//
//        Set<String> testMaterials = new HashSet<>();
//        testMaterials.add("folia");
//        testMaterials.add("klej");
//
//
//        Book testBook = new Book(21, "Władca Pierścieni Drużynia Pierścienia", testCategories, Condition.ZŁA, 448, 2009, "Wydawnictwo Amber");
//        Book testBook2 = new Book(22, "Trzy kroki w szachowy świat", testCategories2, Condition.DOBRA, 88, 2020, "Wydawnictwo BIS");
        Author testAuthor = new Author("John", "Tolkien", testBirthDate, "angielska", "mężczyzna");
        Author testAuthor2 = new Author("Ryszard", "Czajkowski", testBirthDate2, "polska", "mężczyzna");
//        Author testAuthor3 = new Author("Andrzej", "Nowicki", testBirthDate3, "polska", "mężczyzna");
//
//        testBook.addAuthor(testAuthor);
//        testBook2.addAuthor(testAuthor2);
//        testBook2.addAuthor(testAuthor3);
//
//        System.out.println(testBook2.getAuthors());
//        System.out.println(testAuthor2.getBooks());
//
        Client testClient = new Client("Jan", "Kowalski", testBirthDate4, "mężczyzna", "polska", "brak", "jan.kowalski@gmail.com");
//
        Borrow testBorrow = new Borrow(testFrom, testTo, testTo,true, "brak");
//
//        System.out.println(testBook.getState());
//        testClient.addBorrowToClient(testBook, testBorrow);
//        System.out.println(testBook.getBorrowDetails());
//        System.out.println(testClient.getBorrowDetails());
//        System.out.println(testBook.getState());
//        testClient.returnBook(testBorrow, testBook);
//        System.out.println(testBook.getBorrowDetails());
//        System.out.println(testBook.getState());
//
        Rack testStandardRack = new Rack(1, "A01", "fantastyka");
        Rack testMovingRack = new Rack(1, "A02", "fantastyka", 10000, true);
        Rack testSmartRack = new Rack(1, "A03", "fantastyka", "Software 1.1.12", true);
        Rack testMovingSmartRack = new Rack(1, "A04", "fantastyka", "Software 1.1.12", true, 10000,true);
//
//        testStandardRack.addBook(testBook);
//        testMovingRack.addBook(testBook2);
//        System.out.println(testBook.getRack());
//        System.out.println(testBook2.getRack());
//        System.out.println(testMovingRack.getBooks());
//
//
        Restorer testRestorer = new Restorer(100, "Adam", "Nowak", testBirthDate, "mężczyzna", "polska", "001-321", testHiringDate, 10000, "Damięcka 12A/14 01-47testLanguages1 Warszawa", "sklejanie" );
        Librarian testLibrarian = new Librarian ("Krzysztof", "Damięcki", testBirthDate2, "mężczyzna", "polska", "001-322", testHiringDate2, 11000, "Damięcka 12A/14 01-471 Warszawa", testLanguages);
        Director testDirector = new Director("Joanna", "Kalisz", testBirthDate3, "kobieta", "polska", "001-323", testHiringDate3, 15000, "Anielewicza 100/4", "Doktorat SGH");
//        Librarian testChangeRestorerToLibrarian = new Librarian(testRestorer, testLanguages);
//
        Library testLibrary = new Library("Biblioteka narodowa", "Aleja Niepodległości 213", "Warszawa", "02-086");
//
//        testLibrary.addEmployee(testRestorer);
//        testLibrary.addEmployee(testLibrarian);
//        testLibrary.addEmployee(testDirector);
//
//        System.out.println(testLibrary.getEmployees());
//        System.out.println(testRestorer.getLibrary());
//
//        System.out.println(testDirector.findEmployee("001-321"));
//
//        Renovation testRenovation = new Renovation(100, testRenovationDate, testMaterials, Status.ZAKOŃCZONA, "renowacja zakończona sukcesem" );
//        testBook.addRenovationToBook(testRestorer,testRenovation);
//        testBook.updateBookCondition(Condition.DOBRA);
//        System.out.println(testBook.getRenovations());
//        System.out.println(testRestorer.getRenovations());
//
//        List<Librarian> testLibrariansList = new ArrayList<>();
//        testLibrariansList.add(testLibrarian);
//        testLibrariansList.add(testChangeRestorerToLibrarian);
//
//
        Inventory testInventory = new Inventory("Piętro 1", "Inwentaryzacja w trakcie przeglądu", "potrzeba zwrócenia uwagi na fantastykę", testInventoryDate);
      LocalDate testRenovationDate = LocalDate.of(2023, 06,28);
        Set<String> testMaterials = new HashSet<>();
        testMaterials.add("folia");
        Renovation testRenovation241 = new Renovation(100, testRenovationDate, testMaterials, Status.ZAKOŃCZONA, "renowacja zakończona sukcesem" );
//        testStandardRack.addInventory(testLibrariansList, testInventory);
//        testBook.changeRack(testMovingSmartRack);
//        System.out.println(testStandardRack.getInventoryHistory());
//        System.out.println(testLibrarian.getDoneInventories());
//
        BookReport testBookReport = new BookReport("Przegląd książek", false, "Do zbioru warto dodać trylogię Gwiezdnych Wojen");
        RestorerReport testRestorerReport = new RestorerReport("Przegląd konserwatora", false, 10);
        LibrarianReport tesLibrarianReport = new LibrarianReport("Przegląd bibliotekarza", false, "potrzeba więcej inwentaryzacji");
//

//
//
//
//        System.out.println(testDirector.getGeneratedReports());
//        System.out.println(testBookReport.getReportAuthor());
//
//        testBookReport.showReport();
//        testRestorerReport.showReport();
//        tesLibrarianReport.showReport();


        final String extentFile = "C:\\Users\\lukas\\Data.ser"; // okreslenie sciezki zapisu ekstensji

//        try {
////            var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(extentFile))); // trwalosc ekstensji zapisanie
////            Author.writeExtents(out);
////            Book.writeExtents(out);
////            BookReport.writeExtents(out);
////            Client.writeExtents(out);
////            Director.writeExtents(out);
////            Inventory.writeExtents(out);
////            Librarian.writeExtents(out);
////            LibrarianReport.writeExtents(out);
////            Library.writeExtents(out);
////            Rack.writeExtents(out);
////            Renovation.writeExtents(out);
////            RestorerReport.writeExtents(out);
////
////            out.close();
//
////            var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(extentFile))); // trwalosc ekstensji wczytanie
////            Author.readExtents(in);
////            Book.readExtents(in);
////            BookReport.readExtents(in);
////            Client.readExtents(in);
////            Director.readExtents(in);
////            Inventory.readExtents(in);
////            Librarian.readExtents(in);
////            LibrarianReport.readExtents(in);
////            //Library.readExtents(in);
////            Rack.readExtents(in);
////            Renovation.readExtents(in);
////            RestorerReport.readExtents(in);
////            in.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        System.out.println("Ekstensje:");
//        Author.showExtent(); // wypisanie ekstensji przy pomocy przesloniecia metody toString()
//        Book.showExtent();
//        BookReport.showExtent();
//        Client.showExtent();
//        Director.showExtent();
//        Inventory.showExtent();
//        Librarian.showExtent();
//        LibrarianReport.showExtent();
//        Library.showExtent();
//        Rack.showExtent();
//        Renovation.showExtent();
//        RestorerReport.showExtent();


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();


        Session sessionInitial = sessionFactory.openSession();
        sessionInitial.beginTransaction();

        booksFromDb = sessionInitial.createQuery("FROM model.Book ORDER BY title").list();
        Query loginQuery = sessionInitial.createQuery("FROM model.Restorer r WHERE r.id = :id");
        loginQuery.setParameter("id", 1);
        loggedRestorer = (Restorer) loginQuery.uniqueResult();
        sessionInitial.getTransaction().commit();
        //session.close();

//        for (Book book : booksFromDb){
//            System.out.println(book.toString());
//        }

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

       //App app = new App();

       //app.start();

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

            book1Categories.add("fantastyka");

            book2Categories.add("dramat");
            book2Categories.add("powieść");

            book3Categories.add("nauka");
            book3Categories.add("fizyka");
            book3Categories.add("astronomia");

            book4Categories.add("poemat");

            book5Categories.add("dramat");
            book5Categories.add("historia");

            book6Categories.add("historia");

            book7Categories.add("historia");
            book7Categories.add("geopolityka");
            book7Categories.add("batalistyka");

            book8Categories.add("fantastyka");

            book9Categories.add("historia");

            book10Categories.add("gotowanie");

            book11Categories.add("historia");
            book11Categories.add("geopolityka");

            book12Categories.add("informatyka");

            book13Categories.add("geografia");
            book13Categories.add("nauka");

            book14Categories.add("popularnonaukowa");



            LocalDate birthDate1 = LocalDate.of(1981, 11, 11);
            LocalDate birthDate2 = LocalDate.of(1986, 6, 1);
            LocalDate birthDate3 = LocalDate.of(1965, 12, 1);
            LocalDate birthDate4 = LocalDate.of(1991, 3, 13);
            LocalDate birthDate5 = LocalDate.of(1997, 11, 10);


            LocalDate hiringdate1 = LocalDate.of(2023, 1, 1);
            LocalDate hiringdate2 = LocalDate.of(2023, 2, 1);
            LocalDate hiringdate3 = LocalDate.of(2020, 12, 1);
            LocalDate hiringdate4 = LocalDate.of(2023, 6, 13);
            LocalDate hiringdate5 = LocalDate.of(2023, 6, 26);



        Book book1 = new Book(1, "Harry Potter i Kamień Filozoficzny", book1Categories, Condition.ZŁA, 654, 2016, "Media Rodzina");
        Book book2 = new Book(2, "Harry Potter i Komnata Tajemnic", book1Categories, Condition.DOBRA, 358, 2016, "Media Rodzina");
        Book book3 = new Book(3, "Harry Potter i Więzień Azkabanu", book1Categories, Condition.DOBRA, 448, 2016, "Media Rodzina");
        Book book4 = new Book(4, "Harry Potter i Czara Ognia", book1Categories, Condition.DOBRA, 768, 2016, "Media Rodzina");
        Book book5 = new Book(5, "Harry Potter i Książę Półkrwi", book1Categories, Condition.DOBRA, 704, 2016, "Media Rodzina");
        Book book6 = new Book(6, "Harry Potter i Insygnia Śmierci", book1Categories, Condition.DOBRA, 782, 2016, "Media Rodzina");
        Book book7 = new Book(7, "Ojciec Chrzestny", book2Categories, Condition.DOBRA, 1088, 2019, "Albatros");
        Book book8 = new Book(8, "Toeria wszystkiego", book3Categories, Condition.DOBRA, 144, 2018, "Editio");
        Book book9 = new Book(9, "Czarny Łabędź", book14Categories, Condition.DOBRA, 698, 2020, "Zysk i S-ka");
        Book book10 = new Book(10, "Pan Tadeusz", book4Categories, Condition.DOBRA, 352, 2022, "Wydawnictwo Dragon");
        Book book11 = new Book(11, "Dziady", book5Categories, Condition.DOBRA, 282, 2021, "Wydawnictwo Siedmioróg");
        Book book12 = new Book(12, "Medaliony", book6Categories, Condition.ZŁA, 62, 2011, "GREG");
        Book book13 = new Book(13, "Błękitna kropka", book3Categories, Condition.ZŁA, 560, 2011, "Zysk i S-ka");
        Book book14 = new Book(14, "Nadchodzi III wojna światowa", book7Categories, Condition.NOWA, 336, 2021, "Dom Wydawniczy Rebis");
        Book book15 = new Book(15, "Krew elfów. Wiedźmin", book8Categories, Condition.DOBRA, 340, 2014, "SUPERNOWA");
        Book book16 = new Book(16, "Polskie triumfy. 50 chwalebnych bitew naszej historii", book9Categories, Condition.DOBRA, 560, 2018, "Społeczny Instytut Wydawniczy Znak");
        Book book17 = new Book(17, "Jadłonomia po polsku", book10Categories, Condition.NOWA, 280, 2022, "Wydawnictwo Marginesy");
        Book book18 = new Book(18, "Serce Europy", book11Categories, Condition.DOBRA, 544, 2018, "Społeczny Instytut Wydawniczy Znak");
        Book book19 = new Book(19, "Thinking in Java", book12Categories, Condition.ZNISZCZONA, 1248, 2006, "Helion");
        Book book20 = new Book(20, "Powszechny Atlas Świata", book13Categories, Condition.ZNISZCZONA, 221, 2022, "Demart");

        Restorer restorer1 = new Restorer(1, "Karol", "Szyszka", birthDate1, "mężczyzna", "polska", "001-001", hiringdate1, 7000, "Kondratowicza 17A/51, Warszawa", "sklejanie");
        Restorer restorer2 = new Restorer(2, "Karolina", "Wacławiak", birthDate2, "kobieta", "polska", "001-002", hiringdate2, 11000, "Prosta 12/1, Warszawa", "zabezpieczanie");
        Restorer restorer3 = new Restorer(3, "Robert", "Styrkosz", birthDate3, "mężczyzna", "polska", "001-003", hiringdate3, 11000, "Aleje Jerozolimskie 151, Warszawa", "brak");
        Restorer restorer4 = new Restorer(4, "Izabela", "Damięcka", birthDate4, "kobieta", "polska", "001-004", hiringdate4, 13000, "Polska 12, Warszawa", "naprawa");
        Restorer restorer5 = new Restorer(5, "Łukasz", "Łubik", birthDate5, "mężczyzna", "polska", "001-005", hiringdate5, 12000, "Polska 12, Warszawa", "sklejanie");





        book2.setState(State.NIEDOSTĘPNA);
        book5.setState(State.WYPOŻYCZONA);
        book7.setState(State.NIEDOSTĘPNA);
        book11.setState(State.WYPOŻYCZONA);
        book13.setState(State.NIEDOSTĘPNA);
        book15.setState(State.WYPOŻYCZONA);
        book17.setState(State.NIEDOSTĘPNA);

//
            testDirector.setLibrary(testLibrary);
            testLibrarian.setLibrary(testLibrary);
            testRestorer.setLibrary(testLibrary);

            book1.addAuthor(testAuthor);
            book1.addAuthor(testAuthor2);
            book1.addRackToBook(testSmartRack);

            session.save(testDirector);
            session.save(testLibrarian);
            session.save(testRestorer);
            session.save(testStandardRack);
            session.save(book1);
            session.save(testAuthor);
            session.save(testAuthor2);
            book1.addRenovationToBook(testRestorer, testRenovation241);
            testStandardRack.addInventoryToRack(testLibrarian,testInventory);
            book1.addBorrowToBook(testClient,testBorrow);
            testDirector.generateReport(testBookReport);
            testLibrarian.generateReport(tesLibrarianReport);
            testRestorer.generateReport(testRestorerReport);

            session.save(testInventory);
            session.save(testRenovation241);
            session.save(testBookReport);
            session.save(tesLibrarianReport);
            session.save(testRestorerReport);

            session.save(testClient);
            session.save(testBorrow);
            session.save(testLibrary);
            session.save(testMovingRack);
            session.save(testSmartRack);

            session.save(testMovingSmartRack);


            session.save(book2);
            session.save(book3);
            session.save(book4);
            session.save(book5);
            session.save(book6);
            session.save(book7);
            session.save(book8);
            session.save(book9);
            session.save(book10);
            session.save(book11);
            session.save(book12);
            session.save(book13);
            session.save(book14);
            session.save(book15);
            session.save(book16);
            session.save(book17);
            session.save(book18);
            session.save(book19);
            session.save(book20);

            session.save(restorer1);
            session.save(restorer2);
            session.save(restorer3);
            session.save(restorer4);
            session.save(restorer5);

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
