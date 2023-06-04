package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class ObjectPlus implements Serializable {

    private static Map<Class, List<ObjectPlus>> allExtents = new Hashtable<>(); // glowna ekstensja

    public ObjectPlus() { // konstruktor
        List extent = null;
        Class theClass = this.getClass();

        if (allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        }
        else {
            extent = new ArrayList();
            allExtents.put(theClass, extent);
        }

        extent.add(this); // dodanie nowego obiektu do ekstensji
    }

    // ----------- Serializacja -----------

    public static void writeExtents (ObjectOutputStream stream) throws IOException { // eksport do pliku
        stream.writeObject(allExtents);
    }

    public static void readExtents (ObjectInputStream stream) throws IOException, ClassNotFoundException { // import z pliku
        allExtents = (Hashtable) stream.readObject(); // rzutowanie typu
    }

    // get ekstensja

    public static <T> Iterable<T> getExtent (Class<T> type) throws ClassNotFoundException {
        if (allExtents.containsKey(type)) {
            return (Iterable<T>) allExtents.get(type);
        }
        else {
            throw new ClassNotFoundException(String.format("%s. Stored extents: %s", type.toString(), allExtents.keySet()));
        }
    }

    // show ekstensja


    public static void showExtent (Class theClass) throws Exception {
        List extent = null;

        if (allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        }
        else {
            throw new Exception("Unkown class " + theClass);
        }

        //System.out.println("Extent of the class: " + theClass.getSimpleName());

        for (Object obj : extent) {
            System.out.println(obj);
        }
    }

}
