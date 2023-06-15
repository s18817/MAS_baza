package gui;

import model.Book;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BookObjectsModel<Book> extends AbstractListModel {
    ArrayList<Book> objects;

    public BookObjectsModel (List<Book> objs) {
        objects = new ArrayList<Book>(objs);
    }

    @Override
    public Book getElementAt(int index) {
        return objects.get(index);
    }

    @Override
    public int getSize() {
        return objects.size();
    }

    public void addObject(Book newObj) {
        objects.add(newObj);

        // Poinformuj model o zmianie
        fireContentsChanged(this, getSize() - 1, getSize() - 1);
    }

    public void removeObject(Book obj) {
        objects.remove(obj);
        // Poinformuj model o zmianie
        fireContentsChanged(this, getSize() - 1, getSize() - 1);
    }

    public List<Book> getObjects() {
        return objects;
    }

}
