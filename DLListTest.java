import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DLListTest {

    private DLList list;

    /**
     * Set up an empty doubly linked list before each test.
     */
    @Before
    public void setUp() {
        list = new DLList();
    }

    /**
     * Test the insert method to ensure elements are added correctly.
     */
    @Test
    public void testInsert() {
        list.insert("First");
        list.insert("Second");
        list.insert("Third");

        // Verify that the list contains the inserted elements
        assertTrue(list.contains("First"));
        assertTrue(list.contains("Second"));
        assertTrue(list.contains("Third"));

        // Verify that non-inserted elements are not present
        assertFalse(list.contains("Fourth"));
    }

    /**
     * Test the remove method to ensure elements are removed correctly.
     */
    @Test
    public void testRemove() {
        list.insert("First");
        list.insert("Second");
        list.insert("Third");

        // Remove an element from the middle
        assertTrue(list.remove("Second"));
        assertFalse(list.contains("Second"));

        // Remove the first element
        assertTrue(list.remove("First"));
        assertFalse(list.contains("First"));

        // Remove the last element
        assertTrue(list.remove("Third"));
        assertFalse(list.contains("Third"));

        // Try to remove a non-existing element
        assertFalse(list.remove("NonExistent"));
    }

    /**
     * Test removing from an empty list to ensure no exceptions are thrown.
     */
    @Test
    public void testRemoveFromEmptyList() {
        assertFalse(list.remove("NonExistent"));
    }

    /**
     * Test the contains method to check if it correctly identifies
     * the presence of elements in the list.
     */
    @Test
    public void testContains() {
        // Initially, the list should be empty
        assertFalse(list.contains("First"));

        // Insert some elements and verify they are contained
        list.insert("First");
        assertTrue(list.contains("First"));
        
        list.insert("Second");
        assertTrue(list.contains("Second"));

        // Verify that non-inserted elements are not found
        assertFalse(list.contains("NonExistent"));
    }

    /**
     * Test removing the only element in the list.
     */
    @Test
    public void testRemoveSingleElement() {
        list.insert("Only");
        assertTrue(list.contains("Only"));

        // Remove the only element
        assertTrue(list.remove("Only"));
        assertFalse(list.contains("Only"));

        // Ensure the list is now empty
        assertFalse(list.remove("Only"));
    }
}
