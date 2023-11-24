import java.util.Scanner;
import java.io.File;

public class MainClass
{
    // Name of the morse code file
    private static String fileName;

    // Name of the file that will be loaded if no file is selected
    private static final String DEFAULT_FILE_NAME = "default.morse";

    // Entry point
    public static void main(String[] args)
    {
        // Creates new Morse tree
        MorseTree mt = new MorseTree();

        // Scanner for user input
        Scanner scan = new Scanner(System.in);

        // Checks if a file has been passed
        if (args.length == 0)
        {
            // If no file has been passed the default file is selected
            fileName = DEFAULT_FILE_NAME;
        }
        else
        {
            // Sets file name equal to passed filename e.g. "other.txt" from "java MainClass other.txt"
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
            scan.close();
            return;
        }

        String input;
        String output;
        while (true)
        {
            System.out.print("Enter message to be decoded or stop to exit: ");
            input = scan.nextLine();

            // Stop the program if the user enters stop
            if (input.equalsIgnoreCase("stop"))
            {
                scan.close();
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
                scan.close();
                return null;
            }
            
        }
        scan.close();
        return sb.toString();
    }
}