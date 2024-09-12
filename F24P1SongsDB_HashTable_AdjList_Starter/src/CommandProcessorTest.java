/**
 * 
 */

/**
 * @author nitinankareddy
 *
 */
public class CommandProcessorTest extends student.TestCase {
    private CommandProcessor cp;
    private Controller controller;
    
    public void setUp() {
        controller = new Controller();
        cp = new CommandProcessor(controller);
    }
    
    
    public void testReadLines() {
        cp.readLines("P1_sampleInput.txt");
        
    }
    

}
