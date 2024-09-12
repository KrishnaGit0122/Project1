import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import student.TestCase;

public class ControllerTest extends TestCase {
    private Controller controller;

    /**
     * Set up a new controller instance for each test.
     */
    public void setUp() {
        controller = new Controller();
    }

    /**
     * Test inserting artist-song pairs into the table.
     */
    public void testInsert() {
        controller.insert("Artist1", "Song1");
        controller.insert("Artist2", "Song2");

        // Verify that size has increased
        assertEquals(2, controller.size());

        // Capture the output for printArtist and printSong to verify
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtist();
        String output = outputStream.toString();

        // Verify output for printArtist
        assertTrue(output.contains("Artist1"));
        assertTrue(output.contains("Artist2"));

        outputStream.reset();

        controller.printSong();
        String songOutput = outputStream.toString();

        // Verify output for printSong
        assertTrue(songOutput.contains("Song1"));
        assertTrue(songOutput.contains("Song2"));

        System.setOut(System.out); // Reset the standard output
    }

    /**
     * Test that inserting duplicates does not add multiple entries.
     */
    public void testInsertDuplicate() {
        controller.insert("Artist1", "Song1");
        controller.insert("Artist1", "Song1"); // Duplicate
        controller.printArtist();

        assertEquals(1, controller.size()); // Size should remain 1
    }

    /**
     * Test removing an artist-song pair and verify its removal.
     */
    public void testRemove() {
        controller.insert("Artist1", "Song1");
        controller.insert("Artist2", "Song2");

        assertEquals(2, controller.size());

        controller.remove("Artist1", "Song1");
        assertEquals(1, controller.size()); // Size should decrease

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtist();
        String output = outputStream.toString();

        // Ensure Artist1 is removed
        assertFalse(output.contains("Artist1"));
        assertTrue(output.contains("Artist2"));

        System.setOut(System.out); // Reset the standard output
    }

    /**
     * Test doubling the table when the load factor is exceeded.
     */
    public void testDoubleTable() {
        // Insert more than half the initial capacity to trigger a resize
        for (int i = 0; i < 11; i++) {
            controller.insert("Artist" + i, "Song" + i);
        }

        assertEquals(21, controller.size());
        assertEquals(40, controller.getCapacity()); // Capacity should double

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtist();
        String output = outputStream.toString();

        // Verify all artists are still present
        for (int i = 0; i < 11; i++) {
            assertTrue(output.contains("Artist" + i));
        }

        System.setOut(System.out); // Reset the standard output
    }

    /**
     * Test printing all artists.
     */
    public void testPrintArtist() {
        controller.insert("Artist1", "Song1");
        controller.insert("Artist2", "Song2");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtist();
        String output = outputStream.toString();

        assertTrue(output.contains("Artist1"));
        assertTrue(output.contains("Artist2"));

        System.setOut(System.out); // Reset the standard output
    }

    /**
     * Test printing all songs.
     */
    public void testPrintSong() {
        controller.insert("Artist1", "Song1");
        controller.insert("Artist2", "Song2");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printSong();
        String output = outputStream.toString();

        assertTrue(output.contains("Song1"));
        assertTrue(output.contains("Song2"));

        System.setOut(System.out); // Reset the standard output
    }

   
    
    /**
     * Test removing a non-existing element.
     */
    public void testRemoveNonExisting() {
        controller.insert("Artist1", "Song1");
        controller.remove("Artist2", "Song2"); // Non-existing

        // Ensure size remains unchanged
        assertEquals(1, controller.size());
    }

    /**
     * Test handling an empty table.
     */
    public void testEmptyTable() {
        // Try to remove from an empty table
        controller.remove("Artist1", "Song1");

        // Print artists and songs on an empty table
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        controller.printArtist();
        String output = outputStream.toString();
        assertTrue(output.contains("Total count of artists: 0"));

        outputStream.reset();

        controller.printSong();
        String songOutput = outputStream.toString();
        assertTrue(songOutput.contains("Total count of songs: 0"));

        System.setOut(System.out); // Reset the standard output
    }

    /**
     * Test printGraph method. 
     * Since the printGraph method is empty, we just test if it runs without error.
     */
    public void testPrintGraph() {
        controller.printGraph(); // Should not throw any errors
    }
}
