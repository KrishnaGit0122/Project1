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
    private Record TOMBSTONE = new Record("TOMBSTONE", new Node("TOMBSTONE"));
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

        this.allRecords = new Record[initialCapacity];
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

    public boolean insert(String key, Node value, boolean isSong) {
        // make sure that the key is actually something
//        if (key == null) {
//            return f;
//        }
        
        // check if we need to make the array size bigger
        if (this.numberOfRecords + 1 > this.capacity/2 ) {
            this.doubleSize(isSong);
            
        }
        // use sfold to calculate the new index to put the new data in
        int home = h(key, capacity);
        // variable to aid in quadratic probing
        int j = 1;
        
       
       int index = home;
        // first check if the index you are looking at is not empty and not tomb stone
        while( allRecords[index] != null && allRecords[index] != TOMBSTONE) {
            // check if the same key is present already as a duplicate
            if(allRecords[index].getKey().equals(key)) {
//                if (isSong) {
//                    System.out.println(key+ "| duplicates a record already in the database.");
//                    return;
//                }
//                
//                System.out.print("|"+ key + "<SEP>");
//                return;
                return false;
            }
            
            // if the index is already taken, use quadratic probing to find a new index
            index = (home + j * j) % capacity;
            j++;  
        }
        
        // make a new Record inside of the allRecords array
        allRecords[index] = new Record(key, value);
        // increment the number of records you have 
        numberOfRecords++;
        if (isSong) {
            System.out.println("|"+ key+ "| is added to the Song database.");
            return true;
        }
        System.out.println("|"+ key+ "| is added to the Artist database.");
        return true;
        
    }
    
    

    
    
    
    public void doubleSize( boolean isSong)
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
        
        if( isSong) {
            System.out.println("Song hash table size doubled.");
        }
        else {
            System.out.println("Artist hash table size doubled."); 
        }
            }
    
    
    public void remove(String key, boolean isSong) {
        if (key == null) {
            throw new NullPointerException();
        }
        
        int home = h(key, capacity);
        int j = 1;
        
        int index = home;
        
        
        while( allRecords[index] != null) {
            // check if the same key is present already as a duplicate
            if(allRecords[index].getKey().equals(key)) {
                allRecords[index] = TOMBSTONE;
                numberOfRecords--;
                
                if (isSong == true) {
                    System.out.println("|"+ key+ "| is removed from the Song database.");
                    return;
                }
                
                System.out.println("|"+ key+ "| is removed from the Artist database.");
                return;
            }
            
            // if the index is already taken, use quadratic probing to find a new index
            index = (home + j * j) % capacity;
            j++;  
        }
        
        if (isSong) {
            System.out.println("|"+ key+ "| does not exist in the Song database.");
            return;
        }
        
        System.out.println("|"+ key+ "| does not exist in the Artist database.");
        
        
    }
    
    public void printArtist()
    {
        int x = 0;
        for(int i = 0; i < capacity; i++) {
            if(allRecords[i] != null) {
                if (allRecords[i].getKey() == "TOMBSTONE") {
                    System.out.println(i + ": " + allRecords[i].getKey());
                    
                }
                else {
                    System.out.println(i + ": |" + allRecords[i].getKey() + "|");
                }
           
                
                
                if (allRecords[i] != TOMBSTONE) {
                    x++;
                }
            }
        }
        System.out.println("total artists: " + x);
        
    }
    
    public void printSong()
    {
        int x = 0;
        for(int i = 0; i < capacity; i++) {
            if(allRecords[i] != null) {
                System.out.println(i + ": |" + allRecords[i].getKey() + "|");
                x++;
            }
        }
        System.out.println("total songs: " + x);
        
    }
    
    public Node find(String key) {
        if (key == null) {
            throw new NullPointerException();
        }

        
        int index = h(key, capacity);
        int j = 1;

        
        while (allRecords[index] != null) {
            // Check if the key at the current index matches the key being searched
            if (allRecords[index] != TOMBSTONE && allRecords[index].getKey().equals(key)) {
                return allRecords[index].getValue(); // Return the associated value (Node)
            }
            
            // Use quadratic probing to check the next index
            index = (index + j * j) % capacity;
            j++;
        }

        // If the key is not found, return null
        return null;
    }

    
    public void printGraph()
    {
        System.out.println("There are 0 connected components");
        System.out.println("The largest connected component has 0 elements");
    }


    public Number getCapacity() {
        return capacity;
    }
    
    public Number getNumberOfRecords()
    {
        return numberOfRecords;
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