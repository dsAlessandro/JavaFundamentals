package Implementation;

public class SortedList<T extends Comparable<T>> extends List<T> {

    @Override
    public void addElement(T el) {
        //gestione della prima inserzione
        if (n == 0 || el.compareTo(head.value) < 0) {
            head = new Node(el, head);
            tail = head;
            n += 1;
            return;
        }


        Node curr = head, prev = null;

        while (curr != null && (curr.value).compareTo(el) < 0) {
            prev = curr;
            curr = curr.next;
        }  

        prev.next = new Node(el, curr);

        if (curr == null)
            tail = prev.next;

        n += 1;
    }
}
