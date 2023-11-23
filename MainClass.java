import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

public class MainClass
{
    // Scanner for user input
    private static final Scanner SCAN = new Scanner(System.in);

    // Name of the morse code file
    private static String fileName;

    // Keeps track of if a file has been loaded and the tree is built
    private static boolean fileIsLoaded = false;

    // Name of the file that will be loaded if no file is selected
    private static final String DEFAULT_FILE_NAME = "default.morse";

    // Entry point
    public static void main(String[] args)
    {
        // Creates new Morse tree
        MorseTree mt = new MorseTree();
        
        while (true)
        {
            int choice = 0;

            // Prints menu
            printMenu();
            try {
                choice = SCAN.nextInt();
                SCAN.nextLine();
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer between 1 and 4");
                SCAN.nextLine(); 
                continue;
            }

            // Decides what to do based on input
            switch (choice) {
                case 1:
                    System.out.print("Enter a file name (default: " + DEFAULT_FILE_NAME + "): ");
                    fileName = SCAN.nextLine();
                    if (fileName.equals(""))
                    {
                        fileName = DEFAULT_FILE_NAME;
                    }

                    // Build the tree based on the file and check if it succeeded
                    if (buildTree(mt, fileName))
                    {
                        System.out.println("File \"" + fileName + "\" loaded successfully!");
                    }
                    else
                    {
                        System.out.println("Error loading file \"" + fileName + "\".");
                    }
                    break;
                case 2:
                    decode(mt);
                    break;
                case 3:
                    showTree(mt);
                    break;
                case 4:
                    SCAN.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }

    /**
     * Prints menu choices
     */
    private static void printMenu()
    {
        System.out.print("""
            ----------------------
            1\tLoad a file
            2\tDecode a messsage
            3\tShow the Tree
            4\tExit
            ----------------------
        >""");
    }

    /**
     * Builds the morse tree
     * @param mt - The MorseTree to be built
     * @return - True if the tree was built succesfully
     */
    public static boolean buildTree(MorseTree mt, String fileName)
    {
        // Clear the tree so add doesn't return false
        mt.clear();
        File file = new File(fileName);
        try (Scanner scan = new Scanner(file)) {
            while(scan.hasNextLine())
            {
                String line = scan.nextLine();
                if (!mt.add(line.charAt(0), line.substring(1)))
                {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        fileIsLoaded = true;
        return true;
    }

    /**
     * Prompts a user to enter a encoded morse code message
     * @param mt
     */
    private static void decode(MorseTree mt)
    {
        // Checks if the tree has been built
        if (!fileIsLoaded)
        {
            buildTree(mt, DEFAULT_FILE_NAME);
            System.out.println("Loaded default morse code.");
        }

        System.out.print("Enter message to be decoded: ");
        String message = SCAN.nextLine();

        // If the user enters "stop", the program should stop
        if (message.equals("stop"))
        {
            return;
        }

        // Call to recursive method
        System.out.println("Decoded Message: " + decode(mt, message) + "\n");
    }

    /**
     * Turns a morse code message into a string based on the morse tree
     * @param mt - Morse Tree
     * @param message - Encoded message
     * @return - Decoded message
     */
    public static String decode(MorseTree mt, String message)
    {
        StringBuilder sb = new StringBuilder();
        Scanner scan = new Scanner(message);
        scan.useDelimiter(" ");

        // Seperates each letter and decodes it
        while (scan.hasNext())
        {
            // Try catch in the case that the user entered invalid morse code
            try {
                String line = scan.next();
                sb.append(mt.decode(line));
            } catch (InvalidMorseCodeException e) {
                System.err.println(e);
            }
            
        }
        scan.close();
        return sb.toString();
    }

    /**
     * Prints out the morse tree
     * @param mt
     */
    private static void showTree(MorseTree mt)
    {
        if (mt == null)
        {
            System.out.println("");
            return;
        }
        System.out.println(mt);
    }
}