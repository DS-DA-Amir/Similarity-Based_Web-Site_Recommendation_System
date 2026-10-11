import java.util.*;
import java.io.*;

class HT implements java.io.Serializable {
    static final class Node {
        Object key;
        Node next;
        int count;
        // Object value;
        Node(Object k, Node n) {
            key = k;
            next = n;
            count = 1;
        }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    boolean contains(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                return true;
            }
        }
        return false;
    }
    //tf
    int getCount(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                return e.count;
            }
        }
        return 0;
    }
    void add(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                e.count++;
                return;
            }
        }
        table[i] = new Node(key, table[i]);
        ++size;
        if ((float)size/table.length >= 0.75f) {
            resizeV2();
        }
    }
    void resize() {
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Node[] newTable = new Node[newCapacity];
        for (int i = 0; i < oldCapacity; ++i) {
            for (Node e = oldTable[i]; e != null; e = e.next) {
                int h = e.key.hashCode();
                int j = h & (newTable.length - 1);
                newTable[j] = new Node(e.key, newTable[j]);
            }
        }
        table = newTable;
    }
    void resizeV2() { // avoids unnecessary creation
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Node[] newTable = new Node[newCapacity];
        for (int i = 0; i < oldCapacity; ++i) {
            Node e = oldTable[i];
            while (e != null) {
                Node next = e.next;
                int h = e.key.hashCode();
                int j = h & (newTable.length - 1);
                e.next = newTable[j];
                newTable[j] = e;
                e = next;
            }
        }
        table = newTable;
    }
    void remove(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        Node e = table[i], p = null;
        while (e != null) {
            if (key.equals(e.key)) {
                if (p == null) {
                    table[i] = e.next;
                }
                else {
                    p.next = e.next;
                }
                --size;
                break;
            }
            p = e;
            e = e.next;
        }
    }
    void printAll() {
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                System.out.println(e.key);
            }
        }
    }
    public String[] keys() {
        String[] result = new String[size];
        int k = 0;
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                result[k] = (String) e.key;
                k++;
            }
        }
        return result;
    }
    private void writeObject(ObjectOutputStream s) throws Exception {
        s.defaultWriteObject();
        s.writeInt(size);
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                s.writeObject(e.key);
            }
        }
    }
    private void readObject(ObjectInputStream s) throws Exception {
        s.defaultReadObject();
        int n = s.readInt();
        for (int i = 0; i < n; ++i) {
            add(s.readObject());
        }
    }
}
