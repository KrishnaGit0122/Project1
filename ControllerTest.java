import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import student.TestCase;

public class ControllerTest extends TestCase {
    private Controller controller;

    /**
     * Set up a new controller instance for each test.
     */
    public void setUp() {
        controller = new Controller(5);
    }

    /**
     * Test inserting artists and songs into the table.
     */
    public void testInsert() {
        controller.insertArtist("Artist1", new Node("Artist1Node"));
        controller.insertSong("Song1", new Node("Song1Node"));

        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtists();
        String output = outputStream.toString();

        // Verify output for printArtist
        assertTrue(output.contains("Artist1"));

        outputStream.reset();

        controller.printSongs();
        String songOutput = outputStream.toString();

        // Verify output for printSong
        assertTrue(songOutput.contains("Song1"));

       
    }

    /**
     * Test that inserting duplicates does not add multiple entries.
     */
    public void testInsertDuplicate() {
        controller.insertArtist("Artist1", new Node("Artist1Node"));
        controller.insertArtist("Artist1", new Node("Artist1NodeDuplicate")); // Duplicate
        controller.insertSong("Song1", new Node("Song1Node"));
        controller.insertSong("Song1", new Node("Song1NodeDuplicate")); // Duplicate

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtists();
        String output = outputStream.toString();

        assertEquals(1, countOccurrences(output, "Artist1")); // Only one occurrence of "Artist1"

        outputStream.reset();

        controller.printSongs();
        String songOutput = outputStream.toString();

        assertEquals(1, countOccurrences(songOutput, "Song1")); // Only one occurrence of "Song1"

       
    }
    
    

    /**
     * Test removing an artist and song, and verify its removal.
     */
    public void testRemove() {
        controller.insertArtist("Artist1", new Node("Artist1Node"));
        controller.insertSong("Song1", new Node("Song1Node"));

        controller.removeArtist("Artist1");
        controller.removeSong("Song1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtists();
        String output = outputStream.toString();
        assertFalse(output.contains("Artist1")); // Ensure "Artist1" is removed

        outputStream.reset();

        controller.printSongs();
        String songOutput = outputStream.toString();
        assertFalse(songOutput.contains("Song1")); // Ensure "Song1" is removed

        
    }

    /**
     * Test doubling the table when the load factor is exceeded.
     */
    public void testDoubleTable() {
        // Insert more than half the initial capacity to trigger a resize
        for (int i = 0; i < 6; i++) {
            controller.insertArtist("Artist" + i, new Node("Node" + i));
            controller.insertSong("Song" + i, new Node("Node" + i));
        }

        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtists();
        String output = outputStream.toString();

        // Verify all artists are still present
        for (int i = 0; i < 6; i++) {
            assertTrue(output.contains("Artist" + i));
        }

        outputStream.reset();

        controller.printSongs();
        String songOutput = outputStream.toString();

        // Verify all songs are still present
        for (int i = 0; i < 6; i++) {
            assertTrue(songOutput.contains("Song" + i));
        }

        
    }

    /**
     * Test printing all artists.
     */
    public void testPrintArtist() {
        controller.insertArtist("Artist1", new Node("Artist1Node"));
        controller.insertArtist("Artist2", new Node("Artist2Node"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtists();
        String output = outputStream.toString();

        assertTrue(output.contains("Artist1"));
        assertTrue(output.contains("Artist2"));

       
    }

    /**
     * Test printing all songs.
     */
    public void testPrintSong() {
        controller.insertSong("Song1", new Node("Song1Node"));
        controller.insertSong("Song2", new Node("Song2Node"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printSongs();
        String output = outputStream.toString();

        assertTrue(output.contains("Song1"));
        assertTrue(output.contains("Song2"));

       
    }

    /**
     * Helper method to count occurrences of a string within a larger string.
     */
    private int countOccurrences(String output, String word) {
        return output.split(word, -1).length - 1;
    }
}
