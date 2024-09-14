public class Node {
    private String value;
    private Node next;

    // Constructor to initialize the node with a value
    public Node(String value) {
        this.value = value;
        this.next = null;
    }

    // Getter for the value
    public String getValue() {
        return value;
    }

    // Setter for the value
    public void setValue(String value) {
        this.value = value;
    }

    // Getter for the next node (for linked lists, if needed)
    public Node getNext() {
        return next;
    }

    // Setter for the next node (for linked lists, if needed)
    public void setNext(Node next) {
        this.next = next;
    }


}
