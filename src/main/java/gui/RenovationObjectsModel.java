package gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RenovationObjectsModel<Renovation> extends AbstractListModel {
    ArrayList<Renovation> objects;

    public RenovationObjectsModel (List<Renovation> objs) {
        objects = new ArrayList<Renovation>(objs);
    }

    @Override
    public Renovation getElementAt(int index) {
        return objects.get(index);
    }

    @Override
    public int getSize() {
        return objects.size();
    }

    public void addObject(Renovation newObj) {
        objects.add(newObj);

        // Poinformuj model o zmianie
        fireContentsChanged(this, getSize() - 1, getSize() - 1);
    }

    public void removeObject(Renovation obj) {
        objects.remove(obj);
        // Poinformuj model o zmianie
        fireContentsChanged(this, getSize() - 1, getSize() - 1);
    }

    public List<Renovation> getObjects() {
        return objects;
    }

}
