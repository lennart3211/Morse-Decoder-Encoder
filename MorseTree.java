/**
 * A binary tree storing morse code values
 */
public class MorseTree
{
    private Node root;

    /** Constructor */
    public MorseTree() 
    {
        root = new Node();
    }

    /**
     * Adds a new character and it's code to the tree
     * @param c - The character to be added
     * @param code - The morse code corresponding to the character
     * @return - True if the character was succesfully added
     */
    public boolean add(char c, String code)
    {
        if (code.length() == 0)
        {
            return false;
        }
        return add(c, code, 0, root);
    }

    /**
     * Recursive add method
     * @param c - The character to be added
     * @param code - The morse code corresponding to the character
     * @param index - The current index of the code
     * @param curr - The current node
     * @return - True if the character is succesfully added
     */
    private boolean add(char c, String code, int index, Node curr)
    {
        // Allows for characters to be added in any order
        if (curr == null)
        {
            curr = new Node();
        }

        // Checks if we are on the last part of the code
        if (index == code.length() - 1)
        {
            // Checks if the character should be added to the right or left
            if (code.charAt(index) == '-')
            {
                // Makes new node if it doesn't exist yet
                if (curr.left == null)
                {
                    curr.left = new Node(null, null, c);
                }
                
                // Otherwise it changes the data of the node
                else
                {
                    // If the node already exists, and the data isn't the default value of a char, the code already has a corresponding character
                    if (curr.left.data != 0)
                    {
                        return false;
                    }
                    curr.left.data = c;
                } 
                return true; // Character added succesfully 
            }
            // Makes new node if it doesn't exist yet
            if (curr.right == null)
            {
                curr.right = new Node(null, null, c);
            }

            // Otherwise it changes the data of the node
            else
            {
                curr.right.data = c;
            }
            return true;
        }

        // Chooses to go right or left
        if (code.charAt(index) == '-')
        {
            if (curr.left == null)
            {
                curr.left = new Node();
            }
            return add(c, code, index + 1, curr.left);
        }
      
        if (curr.right == null)
        {
            curr.right = new Node();
        }
        return add(c, code, index + 1, curr.right);
    }

    /**
     * Decodes a morse code message based on the morse tree
     * @param code - The morse code
     * @return - The character corresponding to the morse code
     * @throws InvalidMorseCodeException - If the entered morse code doesn't correspond to an element of the tree
     */
    public char decode(String code) throws InvalidMorseCodeException
    {
       return decode(code, 0, root);
    }

    /**
     * Recursive decode function
     * @param code - The morse code
     * @param index - The current position in the morse code
     * @param curr - The current node
     * @return - The character corresponding to the morse code
     * @throws InvalidMorseCodeException - If the entered morse code doesn't correspond to an element of the tree
     */
    private char decode(String code, int index, Node curr) throws InvalidMorseCodeException
    {
        // If the node is null, the entered code must be invalid
        if (curr == null)
        {
            throw new InvalidMorseCodeException("No character found equal to \"" + code + "\"");
        }

        // This means we have gone through the entire code
        if (index == code.length())
        {
            return curr.data;
        }

        // Check if we need to go left
        if (code.charAt(index) == '-')
        {
            return decode(code, ++index, curr.left);
        }

        // Check if we need to go right
        if (code.charAt(index) == '.')
        {
            return decode(code, ++index, curr.right);
        }

        // If the character is neither '-' nor '.', it is not a valid morse code character
        throw new InvalidMorseCodeException("\"" + code.charAt(index) + "\" is not a valid morse code character\nOnly use \".\" and \"-\"");
        
    }

    /**
     * @return - A string representing the tree
     */
    public String toString()
    {
        if (root == null)
        {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.data);

        String pointerRight = "└──";
        String pointerLeft = (root.right != null) ? "├──" : "└──";

        preOrder(root.left, "", pointerLeft, sb, root.right != null);
        preOrder(root.right, "", pointerRight, sb, false);

        return sb.toString();
    }

    private void preOrder(Node start, String padding, String pointer, StringBuilder sb, boolean hasRightSiblng)
    {
        if (start != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(start.data);
    
            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSiblng)
            {
                paddingBuilder.append("│  ");
            }
            else
            {
                paddingBuilder.append("   ");
            }
    
            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (start.right != null) ? "├──" : "└──";
    
            preOrder(start.left, paddingForBoth, pointerForLeft, sb, start.right != null);
            preOrder(start.right, paddingForBoth, pointerForRight, sb, false);
        }
        
    }

    /**
     * Sets the root node equal to a new node
     */
    public void clear()
    {
        root = new Node();
    }
    
    /**
     * Node class
     */
    private class Node
    {
        // Right child node
        private Node right;

        // Left child node
        private Node left;

        // Character stored in node
        private char data;

        // Default constructor
        private Node() {}

        /**
         * Overloaded constructor
         * @param right - Reference to right child node
         * @param left - Reference to left child node
         * @param data - Character to be stored
         */
        private Node(Node right, Node left, char data)
        {
            this.right = right;
            this.left = left;
            this.data = data;
        }
    }
}