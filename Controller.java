public class Controller {

    private Hash artists;
    private Hash songs;

    public Controller(int initialCapacity) {
        this.artists = new Hash(initialCapacity);
        this.songs = new Hash(initialCapacity);
    }
    
    public void insertArtist(String artist, Node artistNode) {
        artists.insert(artist, artistNode, false);
        
    }
    
    public void insertSong(String song, Node songNode) {
        songs.insert(song, songNode, true);
    }
    
    public void removeArtist(String artist) {
        artists.remove(artist, false);
    }
    
    public void removeSong(String song) {
        songs.remove(song, true);
    }
    
    public void printArtists() {
        artists.printArtist();
        
        
    }
    
    public void printSongs() {
        songs.printSong();
    }
    
    public void printGraph()
    {
        songs.printGraph();
    }
    
    
    

    /**
     * Insert a new artist-song pair into the hash table.
     * If the hash table is half full, make it larger
     *
     * @param artist
     *            The artist's name
     * @param song
     *            The song title
     */
    /*
     * public void insert(String artist, String song) {
     * if (artist == null) {
     * throw new NullPointerException();
     * }
     * // If the size is greater than half of the capacity, double the size of
     * table
     * if (size >= capacity / 2) {
     * doubleTable();
     * }
     * 
     * String key = artist + ":" + song;
     * int index = Hash.h(key, capacity); // find the hash index from Hash class
     * int j = 1;
     * 
     * // Search for an existing duplicate before inserting
     * while (table[index][0] != null) {
     * 
     * if (table[index][0].equals(artist)) {
     * 
     * return; // Duplicate found, do not insert again
     * }
     * // Quadratic probing to resolve collisions
     * index = (index + j * j) % capacity;
     * j++;
     * }
     * 
     * 
     * 
     * System.out.println("|" + artist.trim() +
     * "| is added to the Artist database.");
     * System.out.println("|" + song.trim() +
     * "| is added to the Song database.");
     * 
     * // Insert the new artist-song pair
     * table[index][0] = artist;
     * table[index][1] = song;
     * size++;
     * }
     * 
     *//**
        * Remove an artist-song pair from the hash table.
        *
        * @param artist
        *            The artist's name
        * @param song
        *            The song title
        */
    /*
     * public void remove(String artist, String song) {
     * 
     * String key = artist + ":" + song;
     * int index = Hash.h(key, capacity);
     * 
     * int j = 1;
     * 
     * while (table[index][0] != null) {
     * if (table[index][0].equals(artist) && table[index][1].equals(song)) {
     * table[index][0] = null;
     * table[index][1] = null;
     * size--;
     * System.out.println("|" + song.trim() +
     * "| is removed from the Artist database.");
     * return;
     * }
     * 
     * 
     * index = (index + j*j) % capacity;
     * j++;
     * }
     * System.out.println("|" + song.trim() +
     * "| does not exist in the Song database.");
     * 
     * }
     * 
     * public int size() {
     * return size;
     * }
     * 
     *//**
        * When the hash table is almost half full, double the size to add more
        * storage.
        */
    /*
     * public void doubleTable() {
     * 
     * // Store the old table and capacity before resizing
     * String[][] oldTable = table;
     * int oldCapacity = capacity;
     * 
     * // Double the capacity of the table
     * capacity *= 2;
     * table = new String[capacity][2];
     * 
     * // Re-insert all existing key-value pairs into the new table
     * for (int i = 0; i < oldCapacity; i++) {
     * if (oldTable[i][0] != null) {
     * insert(oldTable[i][0], oldTable[i][1]);
     * }
     * }
     * }
     * 
     * 
     * 
     *//**
        * After getting the command to print artists from the
        * CommandProcessor, this will print all of the artists present in the
        * table
        *
        */
    /*
     * public void printArtist() {
     * int count = 0;
     * for( int i = 0; i < capacity; i++) {
     * if(table[i][0] != null) {
     * System.out.println( i + ": " + table[i][0]);
     * count++;
     * }
     * }
     * System.out.println("total artists: " + count);
     * 
     * }
     * 
     *//**
        * After getting the command to print songs from the
        * CommandProcessor, this will print all of the songs present in the
        * table
        *
        *//*
           * public void printSong() {
           * int count = 0;
           * for( int i = 0; i < capacity; i++) {
           * if (table[i][1] != null) {
           * System.out.println( i + ": " + table[i][1]);
           * count++;
           * }
           * }
           * System.out.println("total songs: " + count);
           * 
           * }
           * 
           * public void printGraph() {
           * 
           * 
           * }
           * 
           * public int getCapacity() {
           * // TODO Auto-generated method stub
           * return capacity;
           * }
           */

}
