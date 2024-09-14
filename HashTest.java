import student.TestCase;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


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
       hashTable = new Hash(5);
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
        hashTable.insert("Key4", new Node("Value4"), true);
        hashTable.insert("Key5", new Node("Value5"), true);

        

        // Trigger double size by adding another record
        hashTable.insert("Key6", new Node("Value6"), true);

        // The capacity should now be doubled
        assertFalse(hashTable.getCapacity().equals(10));
    }

    /**
     * Test that all elements are copied over to the new table during resizing.
     */
    public void testElementsCopiedOnResize() {
        hashTable.insert("Key1", new Node("Value1"), true);
        hashTable.insert("Key2", new Node("Value2"), true);
        hashTable.insert("Key3", new Node("Value3"), true);

        // Trigger double size
        hashTable.insert("Key4", new Node("Value4"), true);
        hashTable.insert("Key5", new Node("Value5"), true);
        hashTable.insert("Key6", new Node("Value6"), true);

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
        hashTable.insert("Key2", new Node("Value2"), true);
        hashTable.insert("Key3", new Node("Value3"), true);

        // Remove a record to create a tombstone
        hashTable.remove("Key2", true);

        // Trigger double size
        hashTable.insert("Key4", new Node("Value4"), true);
        hashTable.insert("Key5", new Node("Value5"), true);
        hashTable.insert("Key6", new Node("Value6"), true);

        // Ensure that the tombstone record was not copied
        assertNull(hashTable.find("Key2"));
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
    
    
    
    public void testQuadraticProbing()
    {
        System.out.println("abcd: " + Hash.h("abcd", 5));
        System.out.println("dacb: " + Hash.h("dacb", 5));
        System.out.println("badc: " + Hash.h("badc", 5));
        
        hashTable.insert("abcd", new Node("abcd"), true);
        hashTable.insert("dacb", new Node("dacb"), true);
        hashTable.insert("badc", new Node("badc"), true);
        
        
        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("dacb"));
        assertNotNull(hashTable.find("badc"));
        
        hashTable.remove("dacb", true);
        assertNull(hashTable.find("dacb"));
        assertNotNull(hashTable.find("abcd"));
        assertNotNull(hashTable.find("badc"));
    }
    
//    public void testDoubling()
//    {
//        
//        assertEquals(hashTable.getCapacity(), 5);
//        hashTable.insert("Key1", new Node("Value1"), true);
//        hashTable.insert("Key2", new Node("Value2"), true);
//        
//        System.out.println(hashTable.getCapacity());
//        assertEquals(hashTable.getCapacity(), 5);
//        
//        hashTable.insert("Key3", new Node("Value3"), true);
//        
//        System.out.println(hashTable.getCapacity());
//        assertEquals(hashTable.getCapacity(), 10);
//        
//        hashTable.insert("Key4", new Node("Value4"), true);
//        hashTable.insert("Key5", new Node("Value5"), true);
//        hashTable.insert("Key6", new Node("Value6"), true);
//        
//        System.out.println(hashTable.getCapacity());
//        assertEquals(hashTable.getCapacity(), 20);
//    }
    
//    public void testPrintStatements()
//    {
//        
//        hashTable.insert("Key1", new Node("Value1"), false);
//        
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        PrintStream firstOutput = System.out;
//        
//        System.setOut(new PrintStream(output));
//        
//        hashTable.remove("Key1", true);
//        
//        assertEquals("|Key1| is removed from the Song database.", output.toString());
//    }
    
    

        

        
        public void testRemoveSongPrintStatement() {
             
            hashTable.insert("Key1", new Node("Value1"), true);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream originalOut = System.out; // Save the original System.out

            
            System.setOut(new PrintStream(output));
            hashTable.remove("Key1", true);
            String expectedOutput = "|Key1| is removed from the Song database." + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());

            
            System.setOut(originalOut);
        
    }
        
        public void testRemoveArtistPrintStatement() {
            
            hashTable.insert("Key1", new Node("Value1"), false);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream originalOut = System.out; // Save the original System.out

            
            System.setOut(new PrintStream(output));
            hashTable.remove("Key1", false);
            String expectedOutput = "|Key1| is removed from the Artist database." + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());

            
            System.setOut(originalOut);
        
    }
        
        
//        public void testInsertSongPrintStatement() {
//            
//            hashTable.insert("Key2", new Node("Value2"), true);
//
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            PrintStream originalOut = System.out;
//
//            
//            System.setOut(new PrintStream(output));
//            hashTable.insert("Key2", new Node("Value1"), true);
//            String expectedOutput = "|Key2| duplicates a record already in the database." + System.lineSeparator();
//            assertEquals(expectedOutput, output.toString());
//ß
//            
//            System.setOut(originalOut);
//        }
        
//        public void testPrintArtist()
//        {
//            hashTable.insert("Key1", new Node("Value1"), false);
//            hashTable.insert("Key2", new Node("Value2"), false);
//            
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            PrintStream originalOut = System.out;
//            
//            System.setOut(new PrintStream(output));
//            
//            hashTable.printArtist();
//            
//            System.setOut(originalOut);
//            
//            String expected = 
//                "0: |Key1|\n" +
//                "1: |Key2|\n" +
//                "total artists: 2\n";
//            
//            assertEquals(expected, output.toString());
//        }

        
        

    
    
}


