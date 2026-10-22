public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> current;

    public LinkedList(){
        head = current = null;
    }

    public boolean empty(){
        return head == null;
    }

    public boolean last(){
        return current.getNext() == null;
    }

    public boolean full(){
        return false;
    }

    public void findFirst(){
        current = head;
    }

    public void findNext(){
        current = current.getNext();
    }

    public T retrieve(){
        return current.getData();
    }

    public void update(T val){
        current.setData(val);
    }

    public void insert(T val){
        Node<T> tmp;
        if (empty()){
            current = head = new Node<T>(val);      // if list is empty, insert at head
        }
        else {
            tmp = current.getNext();        // current's next is saved
            current.setNext(new Node<T>(val));      // new node is added where current next is
            current = current.getNext();        // current set on the new node
            current.setNext(tmp);       // new node's next points to tmp again
        }
    }

    public void remove(){
        if (current == head)
            head = head.getNext();      // head is to be removed
        else {
            Node<T> tmp = head;
            while (tmp.getNext() != current){
                tmp = tmp.getNext();        // finding previous to current
            }
            tmp.setNext(current.getNext());     // jumping the current so that it's removed
        }
        if(current.getNext() == null)
            current = head;
        else
            current = current.getNext();
    }
}
