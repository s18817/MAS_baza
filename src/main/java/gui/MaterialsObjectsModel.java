package gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialsObjectsModel extends AbstractListModel<String> {
    ArrayList<String> objects;

    public MaterialsObjectsModel (List<String> objs) {
        objects = new ArrayList<String>(objs);
    }

    @Override
    public String getElementAt (int index) {
        return objects.get(index);
    }

    @Override
    public int getSize () {
        return objects.size();
    }

    public void addObject (String newObj) {
        objects.add(newObj);

        // Poinformuj model o zmianie
        fireContentsChanged(this, getSize() - 1, getSize() - 1);
    }

    public void removeObject (String obj) {
        objects.remove(obj);
        // Poinformuj model o zmianie
        fireContentsChanged(this, getSize() - 1, getSize() - 1);
    }

    public List<String> getObjects () {
        return objects;
    }
}