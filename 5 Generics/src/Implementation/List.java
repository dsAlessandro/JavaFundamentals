package Implementation;

import java.util.Iterator;

public class List<T> implements Iterable<T> {
    // Definizione del singolo nodo
    class Node {
        T value;
        Node next;

        public Node(T val, Node next) {
            this.value = val;
            this.next = next;
        }
    }
    


    // Attributi fondamentali della SortedList
    Node head, tail;
    int n;


    // Costruttore
    public List () {
        head = tail = null;
        n = 0;
    }
 


    // Definizione del metodo per ottenere l'iteratore della lista

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node curr = head;

            @Override
            public boolean hasNext() {
                if (curr == null) {
                    return false;
                }

                return true;
            }

            @Override
            public T next() {
                T tmp = curr.value;

                curr = curr.next;

                return tmp;
            }
        };
    }



    // Metodi fondamentali della lista

    public void addElement(T el) {
        if (n == 0) {
            head = new Node(el, null);
            tail = head;
        }

        else {
            tail.next = new Node(el, null);
            tail = tail.next;
        }

        n += 1;
    }

    public void deleteElement(T el) {
        Node curr = head;
        Node prev = null;
    
        //verifico la cancellazione in testa
        if ((curr.value).equals(el)) {
            head = curr.next;
            n -= 1;
            System.out.println("Cancellazione riuscita!");
            return;
        }



        while (curr != null) {
            if ((curr.value).equals(el)) {
                break;
            }

            prev = curr;
            curr = curr.next;
        }

        if (curr == null) {
            System.out.println("Elemento non presente in lista");
            return;
        }

        //verifico la cancellazone in coda
        if (curr == tail) {
            tail = prev;
        }

        prev.next = curr.next;
        n -= 1;
        System.out.println("Cancellazione riuscita!");
    }
}
