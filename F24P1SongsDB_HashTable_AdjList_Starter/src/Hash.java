/**
 * Hash table class
 *
 * @author Nitin Ankareddy and Krishna Patel
 * @version Sep 12 2024
 */
public class Hash {

    // private DLList[] table; // Array of doubly linked lists to handle
    // collisions
    // private int size; // Number of elements in the table
    private Record[] allRecords;
    private int numberOfRecords;
    private Record TOMBSTONE = new Record(null, null);
    private int capacity;

    /**
     * Constructor to initialize the hash table with a specific length.
     * 
     * @param length
     *            The length of the hash table.
     */
    public Hash(int initialCapacity) {
        // just make an array with the size also inputted
        // instantiate Record[] allrecords

        Record[] allRecords = new Record[initialCapacity];
        this.capacity = initialCapacity;
        this.numberOfRecords = 0;
    }


    /**
     * Compute the hash function
     * 
     * @param s
     *            The string that we are hashing
     * @param length
     *            Length of the hash table (needed because this method is
     *            static)
     * @return
     *         The hash function value (the home slot in the table for this key)
     */
    public static int h(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % length);
    }


    /**
     * Insert a string into the hash table.
     * 
     * @param s
     *            The string to be inserted.
     */
    // first if artist is there already

    public void insert(String key, Node value) {
        // make sure that the key is actually something
        if (key == null) {
            throw new NullPointerException();
        }
        
        // check if we need to make the array size bigger
        if (numberOfRecords >= capacity/2) {
            this.doubleSize();
        }
        // use sfold to calculate the new index to put the new data in
        int index = h(key, capacity);
        // variable to aid in quadratic probing
        int j = 1;
        
        // first check if the index you are looking at is not empty and not tomb stone
        while( allRecords[index] != null && allRecords[index] != TOMBSTONE) {
            // check if the same key is present already as a duplicate
            if(allRecords[index].getKey().equals(key)) {
                System.out.println("Duplicate");
                return;
            }
            
            // if the index is already taken, use quadratic probing to find a new index
            index = (index + j * j) % capacity;
            j++;  
        }
        
        // make a new Record inside of the allRecords array
        allRecords[index] = new Record(key, value);
        // increment the number of records you have 
        numberOfRecords++;
        System.out.println("Artist/Song was inserted");
        
    }
    
    
    
    
    public void doubleSize()
    {
        // initially double the capacity and make new table that size
        int doubledCap = capacity * 2;
        Record[] moreRecords = new Record[doubledCap];
        
        // loop through
        for( int i = 0; i < capacity; i++) 
        {
            // if the spot in the old table is taken
            if(allRecords[i]!= null && allRecords[i] != TOMBSTONE)
            {
                // get a new hash index for the doubled table
                int newIndex = h(allRecords[i].getKey(), doubledCap);
                int j = 1;
                
                // use quadratic probing if needed
                while( moreRecords[newIndex] != null) {
                    newIndex = (newIndex + j * j) % doubledCap;
                    j++;
                }
                
                // place the date into the new table
                moreRecords[newIndex] = allRecords[i];
            }
        }
        
        // finish transferring the new table and capacity to the overall class
        
        allRecords = moreRecords;
        capacity = doubledCap;
    }
    
    
    public void remove(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        
        int index = h(key, capacity);
        int j = 1;
        
        
        while( allRecords[index] != null) {
            // check if the same key is present already as a duplicate
            if(allRecords[index].getKey().equals(key)) {
                allRecords[index] = TOMBSTONE;
                numberOfRecords--;
                System.out.println("deleted");
                return;
            }
            
            // if the index is already taken, use quadratic probing to find a new index
            index = (index + j * j) % capacity;
            j++;  
        }
    }
    
    public void printArtist()
    {
        for( int i = 0; i < capacity; i++) {
            if(allRecords[i] != null) {
                System.out.println(i + ":" + allRecords[i].getKey());
            }
        }
        
    }
    
    public void printSong()
    {
        for( int i = 0; i < capacity; i++) {
            if(allRecords[i] != null) {
                System.out.println(i + ":" + allRecords[i].getKey());
            }
        }
        
    }
    
    public void find()
    {
        
    }
    
    public void printGraph()
    {
        
    }


    

    /**
     * Remove a string from the hash table.
     * 
     * @param s
     *            The string to be removed.
     * @return True if the string was found and removed, false otherwise.
     */
    /*
     * public boolean remove(String s) {
     * int index = h(s, table.length); // Compute the hash value to find the
     * index
     * if (table[index].remove(s)) { // If the string is found and removed
     * size--;
     * return true;
     * }
     * return false; // Return false if the string wasn't found
     * }
     * 
     *//**
        * Check if a string is in the hash table.
        * 
        * @param s
        *            The string to check for.
        * @return True if the string is in the hash table, false otherwise.
        */
    /*
     * public boolean contains(String s) {
     * int index = h(s, table.length); // Compute the hash value to find the
     * index
     * return table[index].contains(s); // Check if the string is in the doubly
     * linked list at that index
     * }
     * 
     *//**
        * Get the current number of elements in the hash table.
        * 
        * @return The size of the hash table.
        */
    /*
     * public int size() {
     * return size;
     * }
     * 
     * 
     *//**
        * Find and return a string from the hash table.
        * 
        * @param s
        *            The string to find.
        * @return The string if found, otherwise null.
        *//*
           * public String find(String s) {
           * int index = h(s, table.length); // Compute the hash value to find
           * the index
           * if (table[index].contains(s)) { // If the string is found
           * return s; // Return the string
           * }
           * return null; // Return null if the string wasn't found
           * }
           */
}
