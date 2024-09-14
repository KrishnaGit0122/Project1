import java.io.File;
import java.util.Scanner;

public class CommandProcessor {
    
    private Controller controller;
    
    public CommandProcessor(Controller controller) {
        this.controller = controller;
    }
    
    public void readLines(String filename) {
           try {  
              Scanner sc = new Scanner(new File(filename));
              Scanner scancmd;//Declare two scanners one to read the file and one to read the text pulled from the file
              while(sc.hasNextLine()) {//While we have text to read
                 String line = sc.nextLine();//Get our next line
                 scancmd = new Scanner(line);//Create a scanner from this line
                 String cmd = scancmd.next();//Get the first word (the command) on each line
                 String type;
                 switch(cmd) {
                    case "insert"://In the case of insert change our delimiter from white space to <SEP> 
                       scancmd.useDelimiter("<SEP>");
                       String artist = scancmd.next().trim();//Get the artist since it is before <SEP>
                       String song = scancmd.next().trim();//Get the song title that follows <SEP>
                       controller.insertArtist(artist, new Node(artist));
                       controller.insertSong(song, new Node(song));
                    break;
                    case "remove":
                       type = scancmd.next().trim();//Get the mode of deletion artist/song
                       String token = scancmd.nextLine().trim();
                       //Since both artist titles and song titles have spaces
                       //get the rest of the line for the song/artist name
                       switch(type) {
                          case "artist":
                             controller.removeArtist(token);
                          break;
                          case "song":
                             controller.removeSong(token);
                          break;
                          default ://Error bad token
                             System.out.println("Error bad remove type " + type);
                          break;
                       }
                    break;
                    case "print"://Print command
                       type = scancmd.next();//get the type of print command
                       switch(type) {
                          case "artist":
                             //System.out.println("PRINTING ARTISTS");
                             controller.printArtists();
                          break;
                          case "song":
                             controller.printSongs();
                          break;
                          case "graph":
                             controller.printGraph();
                          break;
                          default:
                             System.out.println("Error bad print type" + type);
                          break;
                       }
                    break;
                    default :
                       System.out.println("Unrecognized input");
                    break;
                 }
              }
           } catch (Exception e) {
              e.printStackTrace();
           }
        }

}

