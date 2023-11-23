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

        // Checks if a file has been passed
        if (args.length == 0)
        {
            // If no file has been passed the default file is selected
            fileName = DEFAULT_FILE_NAME;
        }
        else
        {
            fileName = args[0];
        }

        // Builds the tree and checks if it was successful
        if (buildTree(mt, fileName))
        {
            System.out.println("Tree built successfully!");
        }
        else
        {
            System.out.println("Unable to build Tree from file");
            return;
        }

        String input;
        String output;
        while (true)
        {
            System.out.print("Enter message to be decoded or stop to exit: ");
            input = SCAN.nextLine();
            if (input.equalsIgnoreCase("stop"))
            {
                return;
            }
            output = decode(mt, input);
            if (output != null)
            {
                System.out.println("Decoded Message: " + decode(mt, input));
            }
        }
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
     * Turns a morse code message into a string based on the morse tree
     * @param mt - Morse Tree
     * @param message - Encoded message
     * @return - Decoded message
     */
    private static String decode(MorseTree mt, String message)
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
                return null;
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