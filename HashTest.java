import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import student.TestCase;

/**
 * @author <Put something here>
 * @version <Put something here>
 */
public class HashTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    private Hash hashTable;

    public void setUp() {
        hashTable = new Hash(10);
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.h("a", 10000) == 97);
        assertTrue(Hash.h("b", 10000) == 98);
        assertTrue(Hash.h("aaaa", 10000) == 1873);
        assertTrue(Hash.h("aaab", 10000) == 9089);
        assertTrue(Hash.h("baaa", 10000) == 1874);
        assertTrue(Hash.h("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.h("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.h("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.h("long Lonesome Blues", 10000) == 4667);
    }


    public void testDoubleSizeCapacity() {
        hashTable.insert("Key1", new Node("Value1"), true);
        hashTable.insert("Key2", new Node("Value2"), true);
        hashTable.insert("Key3", new Node("Value3"), true);
        hashTable.insert("Key4", new Node("Value4"), false);
        hashTable.insert("Key5", new Node("Value5"), true);

        // hashTable.insert(null, null, false);

        System.out.println("AHHHHHHHHHHH");

        assertTrue(hashTable.getCapacity().equals(10));

        // Trigger double size by adding another record
        hashTable.insert("Key6", new Node("Value6"), true);

        // The capacity should now be doubled
        assertTrue(hashTable.getCapacity().equals(20));
    }


    /**
     * Test that all elements are copied over to the new table during resizing.
     */
    public void testElementsCopiedOnResize() {
        hashTable.insert("Key1", new Node("Value1"), true);
        hashTable.insert("Key2", new Node("Value2"), false);
        hashTable.insert("Key3", new Node("Value3"), true);

        // Trigger double size
        hashTable.insert("Key4", new Node("Value4"), true);
        hashTable.insert("Key5", new Node("Value5"), true);
        hashTable.insert("Key6", new Node("Value6"), false);

        // Ensure all keys are still present
        assertNotNull(hashTable.find("Key1"));
        assertNotNull(hashTable.find("Key2"));
        assertNotNull(hashTable.find("Key3"));
        assertNotNull(hashTable.find("Key4"));
        assertNotNull(hashTable.find("Key5"));
        assertNotNull(hashTable.find("Key6"));
    }


    /**
     * Test that tombstone and null records are not copied over during resizing.
     */
    public void testTombstonesNotCopiedOnResize() {
        hashTable.insert("Key1", new Node("Value1"), true);
        hashTable.insert("Key2", new Node("Value2"), false);
        hashTable.insert("Key3", new Node("Value3"), true);

        // Remove a record to create a tombstone
        hashTable.remove("Key2", true);

        // Trigger double size
        hashTable.insert("Key4", new Node("Value4"), true);
        hashTable.insert("Key5", new Node("Value5"), true);
        hashTable.insert("Key6", new Node("Value6"), false);

        // Ensure that the tombstone record was not copied
        assertNull(hashTable.find("Key2"));
    }


    public void testQuadraticProbingCollisionResolution() {
        hashTable.insert("key1", new Node("node1"), true);
        hashTable.insert("key2", new Node("node2"), true);

        assertNotNull(hashTable.find("key1"));
        assertNotNull(hashTable.find("key2"));
    }


    public void testCapacityExpansion() {
        hashTable.insert("key1", new Node("node1"), true);
        hashTable.insert("key2", new Node("node2"), true);
        hashTable.insert("key3", new Node("node3"), true);

        assertNotNull(hashTable.find("key1"));
        assertNotNull(hashTable.find("key2"));
        assertNotNull(hashTable.find("key3"));
        assertEquals(10, hashTable.getCapacity().intValue());
    }


    /**
     * Test that quadratic probing works correctly during resizing.
     */
    public void testProbingDuringResize() {
        hashTable.insert("Key1", new Node("Value1"), true);
        hashTable.insert("Key2", new Node("Value2"), true);
        hashTable.insert("Key3", new Node("Value3"), true);
        hashTable.insert("Key4", new Node("Value4"), true);
        hashTable.insert("Key5", new Node("Value5"), true);

        // Trigger resize
        hashTable.insert("Key6", new Node("Value6"), true);

        // Ensure that all keys are still present
        assertNotNull(hashTable.find("Key1"));
        assertNotNull(hashTable.find("Key2"));
        assertNotNull(hashTable.find("Key3"));
        assertNotNull(hashTable.find("Key4"));
        assertNotNull(hashTable.find("Key5"));
        assertNotNull(hashTable.find("Key6"));

    }


    /**
     * Test that the new table is assigned after resizing.
     */
    public void testAllRecordsReassignedAfterResize() {
        hashTable.insert("Key1", new Node("Value1"), true);
        hashTable.insert("Key2", new Node("Value2"), true);
        hashTable.insert("Key3", new Node("Value3"), true);
        hashTable.insert("Key4", new Node("Value4"), true);
        hashTable.insert("Key5", new Node("Value5"), true);

        // Trigger resize
        hashTable.insert("Key6", new Node("Value6"), true);

        // Insert additional elements to confirm the new array is being used
        hashTable.insert("Key7", new Node("Value7"), true);
        assertNotNull(hashTable.find("Key7"));

        // Ensure original records are still intact
        assertNotNull(hashTable.find("Key1"));
        assertNotNull(hashTable.find("Key6"));
    }


    public void testInsertSong() {
        hashTable.insert("songKey", new Node("songNode"), true);
        assertNotNull(hashTable.find("songKey"));
    }


    public void testInsertArtist() {
        hashTable.insert("artistKey", new Node("artistNode"), false);
        assertNotNull(hashTable.find("artistKey"));
    }


    public void testInsertSongDuplicate() {
        hashTable.insert("songKey", new Node("songNode"), true);
        hashTable.insert("songKey", new Node("songNode"), true);
        assertNotNull(hashTable.find("songKey"));
    }


    public void testInsertArtistDuplicate() {
        hashTable.insert("artistKey", new Node("artistNode"), false);
        hashTable.insert("artistKey", new Node("artistNode"), false);
        assertNotNull(hashTable.find("artistKey"));
    }


    public void testRemoveSong() {
        hashTable.insert("songKey", new Node("songNode"), true);
        hashTable.remove("songKey", true);
        assertNull(hashTable.find("songKey"));
    }


    public void testRemoveArtist() {
        hashTable.insert("artistKey", new Node("artistNode"), false);
        hashTable.remove("artistKey", false);
        assertNull(hashTable.find("artistKey"));
    }


    public void testRemoveNonExistingSong() {
        hashTable.remove("nonExistingSong", true);
        assertNull(hashTable.find("nonExistingSong"));
    }


    public void testRemoveNonExistingArtist() {
        hashTable.remove("nonExistingArtist", false);
        assertNull(hashTable.find("nonExistingArtist"));
    }


    public void testInsertWithOccupiedIndex() {
        hashTable.insert("key1", new Node("node1"), true);
        hashTable.insert("key2", new Node("node2"), true);

        assertNotNull(hashTable.find("key1"));
        assertNotNull(hashTable.find("key2"));
    }


    public void testInsertWithTombstone() {
        hashTable.insert("abcd", new Node("node1"), true);
        hashTable.remove("abcd", true);

        hashTable.insert("abdc", new Node("node2"), true);

        assertNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("abdc"));
    }


    public void testDoubleSizeMaintainsTombstone() {
        // Insert and remove records
        hashTable.insert("artist1", new Node("Song 1"), false);
        hashTable.insert("artist2", new Node("Song 2"), false);
        hashTable.remove("artist1", false);

        // Trigger doubling
        hashTable.doubleSize(false);

        // Ensure TOMBSTONE behavior after resizing
        assertNull(hashTable.find("artist1")); // TOMBSTONE should be preserved
        // assertEquals(new Node("Song 2"), hashTable.find("artist2")); // Other
        // entries should be retained
    }


    public void testPrintWithTombstones() {
        hashTable.insert("artist1", new Node("Song 1"), false);
        hashTable.insert("artist2", new Node("Song 2"), false);
        hashTable.remove("artist1", false);

        // Redirect the console output to check the printed output
        java.io.ByteArrayOutputStream outContent =
            new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        hashTable.printArtist();

        String expectedOutput = "1: TOMBSTONE\n2: |artist2|\ntotal artists: 1";
        assertTrue(outContent.toString().contains("TOMBSTONE"));
    }


    public void testForceQuadraticProbing() {
        // assertEquals(hashTable.h("abcd", hashTable.getCapacity().intValue()),
        // hashTable.h("bacd", hashTable.getCapacity().intValue()));

        hashTable.insert("abcd", new Node("node1"), true);
        hashTable.insert("bacd", new Node("node2"), true);
        hashTable.insert("dabc", new Node("node3"), true);
        hashTable.insert("cabd", new Node("node4"), true);
        hashTable.insert("acbd", new Node("node4"), true);
        hashTable.insert("adbc", new Node("node4"), true);
        hashTable.insert("dcba", new Node("node4"), true);
        hashTable.insert("dbca", new Node("node4"), true);

        // assertNull(hashTable.find("key1"));
        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("dabc"));
        assertNotNull(hashTable.find("cabd"));

        assertTrue(hashTable.getCapacity().equals(20));

    }


    public void testTombstoneOnBoth() {
        hashTable.insert("abcd", new Node("node1"), true);
        // hashTable.insert("bacd", new Node("node2"), true);
        hashTable.insert("dabc", new Node("node3"), false);
        // hashTable.insert("cabd", new Node("node4"), false);

        assertNotNull(hashTable.find("abcd"));
        // assertNotNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("dabc"));
        // assertNotNull(hashTable.find("cabd"));

        hashTable.remove("abcd", true);
        hashTable.remove("dabc", false);

        assertNull(hashTable.find("abcd"));
        // assertNotNull(hashTable.find("bacd"));
        assertNull(hashTable.find("dabc"));
        // assertNotNull(hashTable.find("cabd"));

        hashTable.insert("bacd", new Node("node2"), true);
        hashTable.insert("cabd", new Node("node4"), false);
        hashTable.insert("badc", new Node("node2"), true);
        hashTable.insert("dcba", new Node("node2"), true);

        assertNotNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("cabd"));
    }

// public void testTableWithNull() {
//
// hashTable.insert("abcd", new Node("node1"), true);
// hashTable.insert("bacd", new Node("node2"), true);
// hashTable.insert("dabc", new Node("node3"), true);
// hashTable.insert("cabd", new Node("node4"), true);
// hashTable.insert("acbd", new Node("node5"), true);
// hashTable.insert("adbc", new Node("node6"), true);
// hashTable.insert("dcba", new Node("node7"), true);
// hashTable.insert("dbca", new Node("node8"), true);
//
//
// assertNotNull(hashTable.find("abcd"));
// assertNotNull(hashTable.find("bacd"));
// assertNotNull(hashTable.find("dabc"));
// assertNotNull(hashTable.find("cabd"));
// assertNotNull(hashTable.find("acbd"));
// assertNotNull(hashTable.find("adbc"));
// assertNotNull(hashTable.find("dcba"));
// assertNotNull(hashTable.find("dbca"));
//
//
// hashTable.remove("abcd", true);
// hashTable.remove("bacd", true);
// hashTable.remove("dabc", true);
// hashTable.remove("cabd", true);
// hashTable.remove("acbd", true);
// hashTable.remove("adbc", true);
// hashTable.remove("dcba", true);
// hashTable.remove("dbca", true);
//
//
// hashTable.insert("key1", new Node("node1"), true);
// hashTable.insert("key2", new Node("node2"), true);
// hashTable.insert("key3", new Node("node3"), true);
//
//
// assertNotNull(hashTable.find("key1"));
// assertNotNull(hashTable.find("key2"));
// assertNotNull(hashTable.find("key3"));
// }

// public void testNullKey()
// {
// hashTable.insert("abcd", new Node("node1"), true);
// hashTable.insert("bacd", new Node("node2"), true);
//
// hashTable.remove(null, true);
//
// assertNotNull(hashTable.find("abcd"));
// assertNotNull(hashTable.find("bacd"));
//
// }


    public void testInsertManyArtists() {
        hashTable.insert("abcd", new Node("node1"), false);
        hashTable.insert("bacd", new Node("node2"), false);
        hashTable.insert("dabc", new Node("node3"), false);
        hashTable.insert("cabd", new Node("node4"), false);
        hashTable.insert("acbd", new Node("node4"), false);

        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("dabc"));
        assertNotNull(hashTable.find("cabd"));
        assertNotNull(hashTable.find("acbd"));

    }

// public void testPrintArtist()
// {
// hashTable.insert("abcd", new Node("node1"), false);
// hashTable.insert("bacd", new Node("node2"), false);
// hashTable.insert("dabc", new Node("node3"), false);
//
// ByteArrayOutputStream output = new ByteArrayOutputStream();
// PrintStream original = System.out;
// System.setOut(new PrintStream(output));
//
// assertNotNull(hashTable.find("abcd"));
// assertNotNull(hashTable.find("bacd"));
// assertNotNull(hashTable.find("dabc"));
//
//
// String expected = "0: |abcd|\n1: |bacd|\n2: |dabc|\ntotal artists: 3\n";
//
// assertEquals(expected, output.toString());
// System.setOut(original);
// }


    public void testQuadraticProbingInRemove() {

        hashTable.insert("abcd", new Node("node1"), true);
        hashTable.insert("bacd", new Node("node2"), true);
        hashTable.insert("cabd", new Node("node3"), true);

        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("cabd"));

        hashTable.remove("bacd", true);

        assertNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("cabd"));

    }


    public void testPrintArtistWithTombstoneAndValidEntries() {

        hashTable.insert("artist1", new Node("node1"), false);
        hashTable.insert("artist2", new Node("node2"), false);
        hashTable.insert("artist3", new Node("node3"), false);

        hashTable.remove("artist2", false);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        hashTable.printArtist();

        System.setOut(originalOut);
        String outputString = output.toString();

        assertTrue(outputString.contains("TOMBSTONE"));
        assertTrue(outputString.contains("|artist1|"));
        assertTrue(outputString.contains("|artist3|"));
        assertTrue(outputString.contains("total artists: 2"));
    }


    public void testRemoveArtistAndSong() {

        hashTable.insert("artist1", new Node("node"), false); // artist
        hashTable.insert("song1", new Node("node2"), true); // song

        assertNotNull(hashTable.find("artist1"));
        assertNotNull(hashTable.find("song1"));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        hashTable.remove("song1", true);
        String expectedSongOutput =
            "|song1| is removed from the Song database.\n";
        assertTrue(output.toString().contains(expectedSongOutput));
        output.reset();

        hashTable.remove("artist1", false);
        String expectedArtistOutput =
            "|artist1| is removed from the Artist database.";
        assertTrue(output.toString().contains(expectedArtistOutput));
        assertNull(hashTable.find("artist1"));
        assertNull(hashTable.find("song1"));

        assertEquals(hashTable.getNumberOfRecords().intValue(), 0);
        System.setOut(originalOut);
    }


    public void testDoubleSizeWithCollisions() {

        hashTable.insert("artist1", new Node("artist1"), false);
        hashTable.insert("abcd", new Node("song1"), true);
        hashTable.insert("artist2", new Node("artist2"), false);

        assertEquals(10, hashTable.getCapacity().intValue());

        hashTable.insert("bacd", new Node("song2"), true);
        hashTable.insert("cabd", new Node("song3"), true);
        hashTable.insert("dbca", new Node("song4"), true);

        assertEquals(20, hashTable.getCapacity().intValue());
        assertNotNull(hashTable.find("artist1"));
        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("artist2"));
        assertNotNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("cabd"));
        assertNotNull(hashTable.find("dbca"));

        hashTable.insert("abcd", new Node("song5"), true);

        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("bacd"));
        assertNotNull(hashTable.find("cabd"));
        assertNotNull(hashTable.find("dbca"));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        hashTable.doubleSize(true);

        String expectedOutput = "Song hash table size doubled.\n";
        assertEquals(expectedOutput, output.toString());

        System.setOut(System.out);
    }

}
