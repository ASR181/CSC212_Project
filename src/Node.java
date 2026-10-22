public class Node<T> {

    public T data;
    public Node<T> next;

    public Node(){
        data = null;
        next = null;
    }

    public Node(T val){
        data = val;
        next = null;
    }

    public T getData(){
        return data;
    }

    public Node getNext(){
        return next;
    }

    public void setData(T val){
        data = val;
    }

    public void setNext(Node next){
        this.next = next;
    }

}
