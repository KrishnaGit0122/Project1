class DLList {
    private DoublyLinkedListNode head;
    private DoublyLinkedListNode tail;

    public DLList() {
        head = null;
        tail = null;
    }

    /**
     * Insert a string at the end of the doubly linked list.
     * 
     * @param data The string to be inserted.
     */
    public void insert(String data) {
        DoublyLinkedListNode newNode = new DoublyLinkedListNode(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    /**
     * Remove a string from the doubly linked list.
     * 
     * @param data The string to be removed.
     * @return True if the string was removed, false otherwise.
     */
    public boolean remove(String data) {
        DoublyLinkedListNode current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    }
                } else {
                    current.prev.next = current.next;
                    if (current.next != null) {
                        current.next.prev = current.prev;
                    }
                }
                if (current == tail) {
                    tail = current.prev;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Check if the list contains a specific string.
     * 
     * @param data The string to search for.
     * @return True if the string is found, false otherwise.
     */
    public boolean contains(String data) {
        DoublyLinkedListNode current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    class DoublyLinkedListNode {
        String data;
        DoublyLinkedListNode prev;
        DoublyLinkedListNode next;

        DoublyLinkedListNode(String data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

}
