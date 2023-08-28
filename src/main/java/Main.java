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
import static model.BookReport.bookReportsFromDb;
import static model.Author.authorsFromDb;
import static model.Client.clientsFromDb;
import static model.Borrow.borrowsFromDb;
import static model.Director.directorsFromDb;
import static model.Inventory.inventoriesFromDb;
import static model.Librarian.librariansFromDb;
import static model.LibrarianReport.librarianReportsFromDb;
import static model.Library.librariesFromDb;
import static model.Rack.racksFromDb;
import static model.Renovation.renovationsFromDb;
import static model.Restorer.restorersFromDb;
import static model.RestorerReport.restorerReportsFromDb;


import static model.Restorer.loggedRestorer;

public class Main {

    public static void main(String[] args) throws Exception {

        // --------------------- uwtorzenie danych testowych ----------------------------------------

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
        Set<String> testCategories = new HashSet<>();
        Set<String> testCategories2 = new HashSet<>();

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
        testCategories.add("fantastyka");
        testCategories2.add("nauka");

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
        LocalDate testBirthDate = LocalDate.of(1982, 1, 3);
        LocalDate testBirthDate2 = LocalDate.of(1933, 10, 4);
        LocalDate testBirthDate3 = LocalDate.of(1919, 5, 1);
        LocalDate testBirthDate4 = LocalDate.of(1995, 11, 21);
        LocalDate testBirthDate5 = LocalDate.of(1798, 12, 24);
        LocalDate testBirthDate6 = LocalDate.of(1884, 11, 10);
        LocalDate testBirthDate7 = LocalDate.of(1976, 12, 10);
        LocalDate testBirthDate8 = LocalDate.of(1980, 4, 27);
        LocalDate testBirthDate9 = LocalDate.of(1934, 11, 9);
        LocalDate testBirthDate10 = LocalDate.of(1948, 6, 21);
        LocalDate testBirthDate11 = LocalDate.of(2018, 11, 28);
        LocalDate testBirthDate12 = LocalDate.of(1990, 7, 3);
        LocalDate testBirthDate13 = LocalDate.of(1939, 6, 8);
        LocalDate testBirthDate14 = LocalDate.of(1957, 7, 8);
        LocalDate testBirthDate15 = LocalDate.of(1965, 1, 6);
        LocalDate testHiringDate = LocalDate.of(2020, 1, 1);
        LocalDate testHiringDate2 = LocalDate.of(2021, 1, 1);
        LocalDate testHiringDate3 = LocalDate.of(2022, 1, 1);
        LocalDate testHiringDate4 = LocalDate.of(2023, 1, 1);
        LocalDate testFrom = LocalDate.of(2023, 6, 26);
        LocalDate testFrom2 = LocalDate.of(2023, 7, 27);
        LocalDate testFrom3 = LocalDate.of(2023, 8, 28);
        LocalDate testFrom4 = LocalDate.of(2023, 9, 29);
        LocalDate testFrom5 = LocalDate.of(2023, 10, 30);
        LocalDate testTo = LocalDate.of(2023, 7, 30);
        LocalDate testTo2 = LocalDate.of(2023, 8, 30);
        LocalDate testTo3 = LocalDate.of(2023, 9, 30);
        LocalDate testTo4 = LocalDate.of(2023, 10, 30);
        LocalDate testTo5 = LocalDate.of(2023, 11, 30);
        LocalDate testTo6 = LocalDate.of(2023, 12, 1);
        LocalDate testTo7 = LocalDate.of(2023, 11, 2);
        LocalDate testTo8 = LocalDate.of(2023, 11, 3);
        LocalDate testTo9 = LocalDate.of(2023, 11, 4);
        LocalDate testTo10 = LocalDate.of(2023, 11, 5);
        LocalDate testTo11 = LocalDate.of(2023, 11, 6);
        LocalDate testTo12 = LocalDate.of(2023, 11, 7);
        LocalDate testTo13 = LocalDate.of(2023, 11, 8);
        LocalDate testTo14 = LocalDate.of(2023, 11, 9);
        LocalDate testTo15 = LocalDate.of(2023, 11, 10);
        LocalDate testRenovationDate = LocalDate.of(2023, 6,28);
        LocalDate testRenovationDate2 = LocalDate.of(2023, 7,28);

        LocalDate testInventoryDate = LocalDate.of(2023, 6,28);
        LocalDate testInventoryDate2 = LocalDate.of(2023, 7,21);
        LocalDate testInventoryDate3 = LocalDate.of(2023, 8,22);
        LocalDate testInventoryDate4 = LocalDate.of(2023, 8,23);
        LocalDate testInventoryDate5 = LocalDate.of(2023,8 ,24);

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
        Book book11 = new Book(12, "Medaliony", book6Categories, Condition.ZŁA, 62, 2011, "GREG");
        Book book12 = new Book(11, "Dziady", book5Categories, Condition.DOBRA, 282, 2021, "Wydawnictwo Siedmioróg");
        Book book13 = new Book(13, "Błękitna kropka", book3Categories, Condition.ZŁA, 560, 2011, "Zysk i S-ka");
        Book book14 = new Book(14, "Nadchodzi III wojna światowa", book7Categories, Condition.NOWA, 336, 2021, "Dom Wydawniczy Rebis");
        Book book15 = new Book(15, "Krew elfów. Wiedźmin", book8Categories, Condition.DOBRA, 340, 2014, "SUPERNOWA");
        Book book16 = new Book(16, "Polskie triumfy. 50 chwalebnych bitew naszej historii", book9Categories, Condition.DOBRA, 560, 2018, "Społeczny Instytut Wydawniczy Znak");
        Book book17 = new Book(17, "Jadłonomia po polsku", book10Categories, Condition.NOWA, 280, 2022, "Wydawnictwo Marginesy");
        Book book18 = new Book(18, "Serce Europy", book11Categories, Condition.DOBRA, 544, 2018, "Społeczny Instytut Wydawniczy Znak");
        Book book19 = new Book(19, "Thinking in Java", book12Categories, Condition.ZNISZCZONA, 1248, 2006, "Helion");
        Book book20 = new Book(20, "Powszechny Atlas Świata", book13Categories, Condition.ZNISZCZONA, 221, 2022, "Demart");
        Book testBook = new Book(21, "Władca Pierścieni Drużynia Pierścienia", testCategories, Condition.ZŁA, 448, 2009, "Wydawnictwo Amber");
        Book testBook2 = new Book(22, "Trzy kroki w szachowy świat", testCategories2, Condition.DOBRA, 88, 2020, "Wydawnictwo BIS");

        Author author1 = new Author("J.K.", "Rowling", testBirthDate, "angielska", "kobieta");
        Author author2 = new Author("Mario", "Puzo", testBirthDate2, "włoska", "mężczyzna");
        Author author3 = new Author("Steven", "Hawking", testBirthDate3, "angielska", "mężczyzna");
        Author author4 = new Author("Nassim Nicholas", "Taleb", testBirthDate3, "amerykańska", "mężczyzna");
        Author author5 = new Author("Adam", "Mickiewicz", testBirthDate5, "polska", "mężczyzna");
        Author author6 = new Author("Zofia", "Nałkowska", testBirthDate6, "polska", "kobieta");
        Author author7 = new Author("Jacek", "Bartosiak", testBirthDate7, "polska", "meżczyczna");
        Author author8 = new Author("Piotr", "Zychowicz", testBirthDate8, "polska", "mężczyzna");
        Author author9 = new Author("Carl", "Sagan", testBirthDate9, "amerykańska", "mężczyzna");
        Author author10 = new Author("Andrzej", "Sapkowski", testBirthDate10, "polska", "mężczyzna", "Wiesiek");
        Author author11 = new Author("praca zbiorowa", "praca zbiorowa", testBirthDate11, "polska", "praca zbiorowa");
        Author author12 = new Author("Marta", "Dymek", testBirthDate12, "polska", "kobieta");
        Author author13 = new Author("Norman", "Davies", testBirthDate13, "polsko-angielska", "mężczyzna");
        Author author14 = new Author("Bruce", "Eckel", testBirthDate14, "amerykańska", "mężczyzna");
        Author author15 = new Author("Hubert", "Mroczkiewicz", testBirthDate15, "polska", "mężczyzna");
        Author testAuthor = new Author("John", "Tolkien", testBirthDate, "angielska", "mężczyzna");
        Author testAuthor2 = new Author("Ryszard", "Czajkowski", testBirthDate2, "polska", "mężczyzna");
        Author testAuthor3 = new Author("Andrzej", "Nowicki", testBirthDate3, "polska", "mężczyzna");

        book1.addAuthor(author1);
        book2.addAuthor(author1);
        book3.addAuthor(author1);
        book4.addAuthor(author1);
        book5.addAuthor(author1);
        book6.addAuthor(author1);
        book7.addAuthor(author2);
        book8.addAuthor(author3);
        book9.addAuthor(author4);
        book10.addAuthor(author5);
        book11.addAuthor(author6);
        book12.addAuthor(author5);
        book13.addAuthor(author9);
        book14.addAuthor(author7);
        book14.addAuthor(author8);
        book15.addAuthor(author10);
        book16.addAuthor(author11);
        book17.addAuthor(author12);
        book18.addAuthor(author13);
        book19.addAuthor(author14);
        book20.addAuthor(author15);
        testBook.addAuthor(testAuthor);
        testBook2.addAuthor(testAuthor2);
        testBook2.addAuthor(testAuthor3);

        Client testClient1 = new Client("Jan", "Kowalski", testBirthDate, "mężczyzna", "polska", "brak", "jan.kowalski@gmail.com");
        Client testClient2 = new Client("Adam", "Nawałka", testBirthDate2, "mężczyzna", "polska", "brak", "adam.nawałka@gmail.com");
        Client testClient3 = new Client("Damian", "Jutrzenka", testBirthDate3, "mężczyzna", "polska", "klient nieuprzejmy", "damian.jutrzenka@gmail.com");
        Client testClient4 = new Client("Danuta", "Szalka", testBirthDate4, "kobieta", "polska", "brak", "danka12@gmail.com");
        Client testClient5 = new Client("Justyna", "Kowalczyk", testBirthDate2, "kobieta", "polska", "ok", "kowalczyk.justyna11@gmail.com");
        Client testClient6 = new Client("Katarzyna", "Czytaj", testBirthDate2, "kobieta", "polska", "ok", "kaska.czytaj@gmail.com");

        Borrow testBorrow = new Borrow(testFrom, testTo, testTo,true, "brak");
        Borrow testBorrow2 = new Borrow(testFrom2, testTo2, testTo2,true, "książka pilnie potrzebna po terminie");
        Borrow testBorrow3 = new Borrow(testFrom, testTo3, testTo3,true, "uważać na okładkę");
        Borrow testBorrow4 = new Borrow(testFrom4, testTo4, testTo4,true, "brak");
        Borrow testBorrow5 = new Borrow(testFrom5, testTo5, testTo5,true, "pamiętać o renowacji");
        Borrow testBorrow6 = new Borrow(testFrom5, testTo5, testTo6,true, "wypożyczenie 1");
        Borrow testBorrow7 = new Borrow(testFrom5, testTo5, testTo7,true, "wypożyczenie 2");
        Borrow testBorrow8 = new Borrow(testFrom5, testTo5, testTo8,true, "wypożyczenie 3");
        Borrow testBorrow9 = new Borrow(testFrom5, testTo5, testTo9,true, "wypożyczenie 4");
        Borrow testBorrow10 = new Borrow(testFrom5, testTo5, testTo10,true, "wypożyczenie 5");
        Borrow testBorrow11 = new Borrow(testFrom5, testTo5, testTo11,true, "wypożyczenie 6");
        Borrow testBorrow12 = new Borrow(testFrom5, testTo5, testTo12,true, "wypożyczenie 7");
        Borrow testBorrow13 = new Borrow(testFrom5, testTo5, testTo13,true, "wypożyczenie 8");
        Borrow testBorrow14 = new Borrow(testFrom5, testTo5, testTo14,true, "wypożyczenie 9");
        Borrow testBorrow15 = new Borrow(testFrom5, testTo5, testTo15,true, "wypożyczenie 10");

        testClient1.addBorrowToClient(book1, testBorrow);
        testClient2.addBorrowToClient(book2, testBorrow2);
        testClient3.addBorrowToClient(book7, testBorrow3);
        testClient4.addBorrowToClient(book17, testBorrow4);
        testClient5.addBorrowToClient(book20, testBorrow5);

        testClient1.returnBook(testBorrow, book1);
        testClient2.returnBook(testBorrow2, book2);
        testClient3.returnBook(testBorrow3, book7);
        testClient4.returnBook(testBorrow4, book17);
        testClient5.returnBook(testBorrow5, book20);

        testClient6.addBorrowToClient(book1, testBorrow6);
        testClient6.addBorrowToClient(book2, testBorrow7);
        testClient6.addBorrowToClient(book3, testBorrow8);
        testClient6.addBorrowToClient(book4, testBorrow9);
        testClient6.addBorrowToClient(book5, testBorrow10);
        testClient6.addBorrowToClient(book6, testBorrow11);

        testClient6.returnBook(testBorrow6, book1);
        testClient6.returnBook(testBorrow7, book2);
        testClient6.returnBook(testBorrow8, book3);
        testClient6.returnBook(testBorrow9, book4);
        testClient6.returnBook(testBorrow10, book5);
        testClient6.returnBook(testBorrow11, book6);

        testClient6.addBorrowToClient(book1, testBorrow12);
        testClient6.addBorrowToClient(book2, testBorrow13);
        testClient6.addBorrowToClient(book3, testBorrow14);
        testClient6.addBorrowToClient(book4, testBorrow15);

        testClient6.returnBook(testBorrow12, book1);
        testClient6.returnBook(testBorrow13, book2);
        testClient6.returnBook(testBorrow14, book3);
        testClient6.returnBook(testBorrow15, book4);


//
//        System.out.println(testBook.getState());
//        testClient1.addBorrowToClient(testBook, testBorrow);
//        System.out.println(testBook.getBorrowDetails());
//        System.out.println(testClient.getBorrowDetails());
//        System.out.println(testBook.getState());
//        testClient1.returnBook(testBorrow, testBook);
//        System.out.println(testBook.getBorrowDetails());
//        System.out.println(testBook.getState());
//
        Rack testStandardRack = new Rack(1, "A01", "fantastyka");
        Rack testMovingRack = new Rack(1, "B01", "nauka", 10000, true);
        Rack testSmartRack = new Rack(2, "A02", "historia", "Software 1.1.12", true);
        Rack testMovingSmartRack = new Rack(3, "A03", "powieść", "Software 1.1.12", true, 10000,true);
//
        testStandardRack.addBookToRack(testBook);
        testStandardRack.addBookToRack(book1);
        testStandardRack.addBookToRack(book2);
        testStandardRack.addBookToRack(book3);
        testStandardRack.addBookToRack(book4);
        testStandardRack.addBookToRack(book5);
        testStandardRack.addBookToRack(book6);
        testStandardRack.addBookToRack(book15);

        testMovingRack.addBookToRack(testBook2);
        testMovingRack.addBookToRack(book8);
        testMovingRack.addBookToRack(book9);
        testMovingRack.addBookToRack(book13);
        testMovingRack.addBookToRack(book19);
        testMovingRack.addBookToRack(book20);
        testMovingRack.addBookToRack(book17);

        testSmartRack.addBookToRack(book10);
        testSmartRack.addBookToRack(book11);
        testSmartRack.addBookToRack(book12);
        testSmartRack.addBookToRack(book14);
        testSmartRack.addBookToRack(book16);
        testSmartRack.addBookToRack(book18);

        testMovingSmartRack.addBookToRack(book7);

//        System.out.println(testBook.getRack());
//        System.out.println(testBook2.getRack());
//        System.out.println(testMovingRack.getBooks());

        Set<String> testLanguages = new HashSet<>();
        testLanguages.add("angielski");
        testLanguages.add("francuski");

        Set<String> testLanguages2 = new HashSet<>();
        testLanguages2.add("angielski");
        testLanguages2.add("niemiecki");

        Set<String> testLanguages3 = new HashSet<>();
        testLanguages3.add("angielski");
        testLanguages3.add("niemiecki");
        testLanguages3.add("hiszpański");

        //Restorer testRestorer = new Restorer(100, "Adam", "Nowak", testBirthDate, "mężczyzna", "polska", "001-321", testHiringDate, 10000, "Damięcka 12A/14 01-47testLanguages1 Warszawa", "sklejanie" );
        Restorer restorer1 = new Restorer(1, "Karol", "Szyszka", birthDate1, "mężczyzna", "polska", "001-001", hiringdate1, 7000, "Kondratowicza 17A/51, Warszawa", "sklejanie");
        Restorer restorer2 = new Restorer(2, "Karolina", "Wacławiak", birthDate2, "kobieta", "polska", "001-002", hiringdate2, 11000, "Prosta 12/1, Warszawa", "zabezpieczanie");
        Restorer restorer3 = new Restorer(3, "Robert", "Styrkosz", birthDate3, "mężczyzna", "polska", "001-003", hiringdate3, 11000, "Aleje Jerozolimskie 151, Warszawa", "brak");
        Restorer restorer4 = new Restorer(4, "Izabela", "Damięcka", birthDate4, "kobieta", "polska", "001-004", hiringdate4, 13000, "Polska 12, Warszawa", "naprawa");
        Restorer restorer5 = new Restorer(5, "Łukasz", "Łubik", birthDate5, "mężczyzna", "polska", "001-005", hiringdate5, 12000, "Polska 12, Warszawa", "sklejanie");

        Librarian testLibrarian = new Librarian ("Krzysztof", "Damięcki", testBirthDate2, "mężczyzna", "polska", "001-322", testHiringDate2, 11000, "Damięcka 12A/14 01-471 Warszawa", testLanguages);
        Librarian testLibrarian2 = new Librarian ("Witold", "Krwaczyk", testBirthDate3, "mężczyzna", "polska", "001-326", testHiringDate3, 10000, "Partyzantów 13 04-271 Warszawa", testLanguages2);
        Librarian testLibrarian3 = new Librarian ("Zygmunt", "Wałyszew", testBirthDate4, "mężczyzna", "polska", "001-329", testHiringDate4, 11000, "Szaserów 111/16 04-349 Warszawa", testLanguages2);
        Librarian testLibrarian4 = new Librarian ("Klaudia", "Durczok", testBirthDate5, "kobieta", "polska", "001-330", testHiringDate2, 13000, "Zacna 5 02-171 Legionowo", testLanguages3);
        Librarian testLibrarian5 = new Librarian ("Izabela", "Kwark", testBirthDate6, "kobieta", "polska", "001-375", testHiringDate3, 11000, "Pełczyńskiego 11A/4 01-111 Warszawa", testLanguages);

        Director testDirector = new Director("Joanna", "Kalisz", testBirthDate3, "kobieta", "polska", "001-323", testHiringDate, 16000, " Anielewicza 100/4 04-124 Warszawa", "Doktorat z ekonomii");
        Director testDirector2 = new Director("Ireneusz", "Sawicki", testBirthDate2, "mężczyzna", "polska", "001-340", testHiringDate2, 15000, "Krzywa 11A/4 01-421 Warszawa", "Doktorat z ekonomii");
        Director testDirector3 = new Director("Zenon", "Martyniuk", testBirthDate4, "mężczyzna", "polska", "001-364", testHiringDate4, 18000, "Powstańców 53/11 05-212 Warszawa", "Inżynier logistyk");
        Director testDirector4 = new Director("John", "Cole", testBirthDate2, "mężczyzna", "angielska", "005-365", testHiringDate4, 22000, "Piaseczno 141 01-543 Piaseczno", "Cambridge");
        Director testDirector5 = new Director("Krystyna", "Radziszewska", testBirthDate2, "kobieta", "polska", "001-380", testHiringDate2, 15000, "Miodowa  153 01-532 Warszawa", "Inżynier informatyk");
        Librarian testChangeRestorerToLibrarian = new Librarian(restorer1, testLanguages);
//
        Library testLibrary = new Library("Biblioteka narodowa", "Aleja Niepodległości 213", "Warszawa", "02-086");
//
        testLibrary.addRestorer(restorer1);
        testLibrary.addRestorer(restorer2);
        testLibrary.addRestorer(restorer3);
        testLibrary.addRestorer(restorer4);
        testLibrary.addRestorer(restorer5);

        testLibrary.addLibrarian(testLibrarian);
        testLibrary.addLibrarian(testLibrarian2);
        testLibrary.addLibrarian(testLibrarian3);
        testLibrary.addLibrarian(testLibrarian4);
        testLibrary.addLibrarian(testLibrarian5);
        //testLibrary.addLibrarian(testChangeRestorerToLibrarian);

        testLibrary.addDirector(testDirector);
        testLibrary.addDirector(testDirector2);
        testLibrary.addDirector(testDirector3);
        testLibrary.addDirector(testDirector4);
        testLibrary.addDirector(testDirector5);
//
//        System.out.println(testLibrary.getEmployees());
//        System.out.println(testRestorer.getLibrary());
//
//        System.out.println(testDirector.findEmployee("001-321"));



        Set<String> testMaterials = new HashSet<>();
        testMaterials.add("folia");
        testMaterials.add("klej");

        Set<String> testMaterials2 = new HashSet<>();

        testMaterials2.add("folia");

        Renovation testRenovation = new Renovation(100, testRenovationDate, testMaterials, Status.ZAKOŃCZONA, "renowacja zakończona sukcesem" );

        Inventory testInventory = new Inventory("Piętro 1", "Inwentaryzacja w trakcie przeglądu", "potrzeba zwrócenia uwagi na fantastykę", testInventoryDate);
        Inventory testInventory2 = new Inventory("Piętro 2", "Inwentaryzacja", "przegląd książek naukowych", testInventoryDate2);
        Inventory testInventory3 = new Inventory("Piętro 1", "Inwentaryzacja w trakcie przeglądu", "przegląd książek historycznych", testInventoryDate3);
        Inventory testInventory4 = new Inventory("Piętro 3", "Inwentaryzacja w trakcie przeglądu", "sprawdzenie zbioru powieści", testInventoryDate4);
        Inventory testInventory5 = new Inventory("Piętro 1", "Inwentaryzacja w trakcie przeglądu", "poukładanie fantastyki", testInventoryDate5);

        testStandardRack.addInventoryToRack(testLibrarian, testInventory);
        testMovingRack.addInventoryToRack(testLibrarian2, testInventory2);
        testSmartRack.addInventoryToRack(testLibrarian3, testInventory3);
        testMovingSmartRack.addInventoryToRack(testLibrarian4, testInventory4);
        testStandardRack.addInventoryToRack(testLibrarian5, testInventory5);


        //testBook.changeRack(testMovingSmartRack);
//        System.out.println(testStandardRack.getInventoryHistory());
//        System.out.println(testLibrarian.getDoneInventories());
//
        BookReport testBookReport = new BookReport("Przegląd książek", false, "Do zbioru warto dodać trylogię Gwiezdnych Wojen");
        RestorerReport testRestorerReport = new RestorerReport("Przegląd konserwatora", false, 10);
        LibrarianReport tesLibrarianReport = new LibrarianReport("Przegląd bibliotekarza", false, "potrzeba więcej inwentaryzacji");

        testDirector.generateReport(testBookReport);
        testLibrarian.generateReport(tesLibrarianReport);
        restorer1.generateReport(testRestorerReport);


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();

        Session sessionInitial = sessionFactory.openSession();
        sessionInitial.beginTransaction();

            // --------------------- załadowanie danych z bazy ----------------------------------------

        booksFromDb = sessionInitial.createQuery("FROM model.Book").list();
        authorsFromDb = sessionInitial.createQuery("FROM model.Author").list();
        bookReportsFromDb = sessionInitial.createQuery("FROM model.BookReport").list();
        clientsFromDb = sessionInitial.createQuery("FROM model.Client").list();
        borrowsFromDb = sessionInitial.createQuery("FROM model.Borrow").list();
        directorsFromDb = sessionInitial.createQuery("FROM model.Director").list();
        librariansFromDb = sessionInitial.createQuery("FROM model.Librarian").list();
        librarianReportsFromDb = sessionInitial.createQuery("FROM model.LibrarianReport").list();
        librariesFromDb = sessionInitial.createQuery("FROM model.Library").list();
        racksFromDb = sessionInitial.createQuery("FROM model.Rack").list();
        renovationsFromDb = sessionInitial.createQuery("FROM model.Renovation").list();
        restorersFromDb = sessionInitial.createQuery("FROM model.Restorer").list();
        restorerReportsFromDb = sessionInitial.createQuery("FROM model.RestorerReport").list();
        inventoriesFromDb = sessionInitial.createQuery("FROM model.Inventory").list();

        Query loginQuery = sessionInitial.createQuery("FROM model.Restorer r WHERE r.id = :id");
        loginQuery.setParameter("id", 1);
        loggedRestorer = (Restorer) loginQuery.uniqueResult();

            // --------------------- wyświetlenie ekstensji -----------------------------------------

            System.out.println("Książki: ");
        for (Book book : booksFromDb){
            System.out.println(book.toString());
        }

        System.out.println("Autorzy: ");
        for (Author author : authorsFromDb){
            System.out.println(author.toString());
        }

        System.out.println("Klienci: ");
        for (Client client : clientsFromDb){
            System.out.println(client.toString());
        }

        System.out.println("Wypożyczenia: ");
        for (Borrow borrow : borrowsFromDb){
            System.out.println(borrow.toString());
        }

        System.out.println("Dyrektorzy: ");
        for (Director director : directorsFromDb){
            System.out.println(director.toString());
        }

        System.out.println("Inwentaryzacje: ");
        for (Inventory inventory : inventoriesFromDb){
            System.out.println(inventory.toString());
        }

        System.out.println("Bibliotekarze: ");
        for (Librarian librarian : librariansFromDb){
            System.out.println(librarian.toString());
        }

        System.out.println("Raporty bibliotekarza: ");
        for (LibrarianReport librarianReport : librarianReportsFromDb){
            System.out.println(librarianReport.toString());
        }

        System.out.println("Biblioteki: ");
        for (Library library : librariesFromDb){
            System.out.println(library.toString());
        }

        System.out.println("Regały: ");
        for (Rack rack : racksFromDb){
            System.out.println(rack.toString());
        }

        System.out.println("Renowacje: ");
        for (Renovation renovation : renovationsFromDb){
            System.out.println(renovation.toString());
        }

        System.out.println("Renowatorzy: ");
        for (Restorer restorer : restorersFromDb) {
            System.out.println(restorer.toString());
        }

        System.out.println("Raporty renowatora: ");
        for (RestorerReport restorerReport : restorerReportsFromDb) {
            System.out.println(restorerReport.toString());
        }

            //librariesFromDb.get(0).removeDirector(directorsFromDb.get(0));
            //librariesFromDb.get(0).removeLibrarian(librariansFromDb.get(0));
            //librariesFromDb.get(0).removeRestorer(restorersFromDb.get(0));
            //directorsFromDb.get(0).addLibraryToDirector(librariesFromDb.get(0));
            //restorersFromDb.get(0).addLibraryToRestorer(librariesFromDb.get(0));
            //librariansFromDb.get(0).addLibraryToLibrarian(librariesFromDb.get(0));

            //booksFromDb.get(0).removeAuthorsFromBook(booksFromDb.get(0).getAuthors());
            //booksFromDb.get(0).addAuthor(authorsFromDb.get(4));

            //booksFromDb.get(0).changeRack(racksFromDb.get(2));
            //booksFromDb.get(0).removeRackFromBook();
            //booksFromDb.get(0).addRackToBook(racksFromDb.get(3));

//            System.out.println(librariesFromDb.get(0).findLibrarian("001-326"));
//            System.out.println(librariesFromDb.get(0).findDirector("001-380"));
//            System.out.println(librariesFromDb.get(0).findRestorer("001-003"));

            sessionInitial.getTransaction().commit();
            sessionInitial.close();

            // --------------------- uruchomienie GUI -----------------------------------------
        try {
         //   App app = new App();

          //  app.start();

//
         Session session = sessionFactory.openSession();
         session.beginTransaction();
//
////session.save(testChangeRestorerToLibrarian);
//
//
//         session.getTransaction().commit();
//         session.close();


//             --------------------- zapisanie danych testowych do bazy -----------------------------------------

            session.save(book1);
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
            session.save(testBook);
            session.save(testBook2);

            session.save(author1);
            session.save(author2);
            session.save(author3);
            session.save(author4);
            session.save(author5);
            session.save(author6);
            session.save(author7);
            session.save(author8);
            session.save(author9);
            session.save(author10);
            session.save(author11);
            session.save(author12);
            session.save(author13);
            session.save(author14);
            session.save(author15);
            session.save(testAuthor);
            session.save(testAuthor2);
            session.save(testAuthor3);

            session.save(testClient1);
            session.save(testClient2);
            session.save(testClient3);
            session.save(testClient4);
            session.save(testClient5);
            session.save(testClient6);




            session.save(testBorrow);
            session.save(testBorrow2);
            session.save(testBorrow3);
            session.save(testBorrow4);
            session.save(testBorrow5);
            session.save(testBorrow6);
            session.save(testBorrow7);
            session.save(testBorrow8);
            session.save(testBorrow9);
            session.save(testBorrow10);
            session.save(testBorrow11);
            session.save(testBorrow12);
            session.save(testBorrow13);
            session.save(testBorrow14);
            session.save(testBorrow15);

//                book2.setState(State.NIEDOSTĘPNA);
//                book5.setState(State.WYPOŻYCZONA);
//                book7.setState(State.NIEDOSTĘPNA);
//                book11.setState(State.WYPOŻYCZONA);
//                book13.setState(State.NIEDOSTĘPNA);
//                book15.setState(State.WYPOŻYCZONA);
//                book17.setState(State.NIEDOSTĘPNA);
//
            session.save(testStandardRack);
            session.save(testMovingRack);
            session.save(testSmartRack);
            session.save(testMovingSmartRack);

            session.save(restorer1);
            session.save(restorer2);
            session.save(restorer3);
            session.save(restorer4);
            session.save(restorer5);

            testBook.addRenovationToBook(restorer1,testRenovation);

            //session.save(testChangeRestorerToLibrarian);

            session.save(testLibrarian);
            session.save(testLibrarian2);
            session.save(testLibrarian3);
            session.save(testLibrarian4);
            session.save(testLibrarian5);

            session.save(testDirector);
            session.save(testDirector2);
            session.save(testDirector3);
            session.save(testDirector4);
            session.save(testDirector5);

            session.save(testRenovation);
           // session.save(testRenovation241);

            session.save(testInventory);
            session.save(testInventory2);
            session.save(testInventory3);
            session.save(testInventory4);
            session.save(testInventory5);

            session.save(testBookReport);
            session.save(tesLibrarianReport);
            session.save(testRestorerReport);

            session.save(testLibrary);


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
