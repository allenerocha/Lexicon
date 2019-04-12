/************************
 *
 * Created and maintained
 * by Allen Rocha
 * For more information
 * please visit:
 * https://github.com/allenerocha
 *
 * *********************/
public class Node implements NodeInterface {
    private char id;
    private Node parent;
    private Node[] children = new Node[26];
    private boolean isRoot = false;

    /**
     * Leaf Node
     *
     * @param id     identifier for this Node
     * @param parent previous Node
     */
    Node(String id, Node parent) {
        this.id = id.charAt(0);
        this.parent = parent;
    }

    /**
     * Node constructor. This is called for the initialization of a root
     *
     * @param word   remaining character to be parsed as connecting Nodes
     * @param isRoot boolean value whether or not this is the root
     */
    Node(String word, boolean isRoot) {
        this.id = word.charAt(0); // Assigns this Node's representation as the first letter of the String word passed
        this.isRoot = isRoot; // Sets the boolean value if this is a root or not
        if (isRoot) {
            this.parent = null;
            addChild(word.substring(1));
        } else
            addChild(word); // Recursive call to addChild using the word parameter
    }

    /**
     * Node constructor for non-roots
     *
     * @param word   remaining character to be parsed as connecting Nodes
     * @param parent the previous Node connected to this Node
     * @param isRoot boolean value whether or not this is the root
     */
    Node(String word, Node parent, boolean isRoot) {
        this.id = word.charAt(0); // Assigns this Node's representation as the first letter of the String word passed
        this.parent = parent; // Sets the parent of this Node as the passed Node parent
        this.isRoot = isRoot; // Sets the boolean value if this is a root or not
        addChild(word.substring(1)); // Recursive call to addChild using the word parameter

    }

    /**
     * @return the ID of this Node
     */
    public char getId() {
        return this.id;
    }

    /**
     * @return the parent Node (can return null if this Node is the root)
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * @return the children as an array
     */
    public Node[] getChildren() {
        return this.children;
    }

    /**
     * @return a boolean value; true->this is the root Node, false->this is NOT the root Node
     */
    public boolean isRoot() {
        return this.isRoot;
    }

    /**
     * @param id the character ID for this Node
     */
    public void setId(char id) {
        this.id = id;
    }

    /**
     * @param parent the previous Node connected to this Node (AKA Parent)
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @param children sets the children of this Node (Should rarely be called)
     */
    public void setChildren(Node[] children) {
        this.children = children;
    }

    /**
     * @param root identifies this as the root of the tree; true->this is the root, false->this is not the root
     */
    public void setRoot(boolean root) {
        isRoot = root;
    }

    /**
     * Calls the addChild method without having to call the Node constructors
     *
     * @param word the String to be parsed as Nodes
     */
    public void insert(String word, Node parent) {
        this.parent = parent;
        addChild(word); // Recursive call to create branches
    }

    /**
     * Recursive function to add children to Node
     */
    public void addChild(String word) {
        if (word.substring(1).length() > 0) { // Loops through until the count = 0 and stops if there are no more
            char childId = word.charAt(0);
            boolean exists = false;
            for (Node child : this.children)
                if (child != null && child.id == childId)
                    exists = true;

            if (exists)
                this.children[childId - 97].insert(word.substring(1), this.children[childId - 97]);
            else
                this.children[childId - 97] = new Node(word, this, false);
        } else {
            char childId = word.charAt(0);
            if (this.children[childId - 97] != null)
                this.children[childId - 97] = new Node(word, this);
            else
                this.children[childId - 97] = new Node(word, this); // Fills in this child with a new Node
        }
    }

    /**
     * Integer value of the total amount of children this Node has
     *
     * @return Integer of size
     */
    public int size() {
        return this.children.length;
    }

    /**
     * returns an array of locations of non-null Nodes
     *
     * @return array of integers containing indexes of non-null Nodes otherwise -1
     */
    public int[] indexOfChildren() {
        int a = 0;
        for (Node child : this.children)
            if (child != null)
                a++;
        int[] arr = new int[a];
        for (int i = 0; i < arr.length; i++) {
            int j = 0;
            while (j < this.children.length) {
                if (this.children[j] != null) {
                    arr[i] = j;
                    break;
                } else
                    j++;
            }
        }
        return arr;
    }

    /**
     * Checks if the children of this Node contains the character
     *
     * @param a character to check
     * @return index number 0-25 if it exits -1 if it does not
     */
    public int contains(char a) {
        for (int i = 0; i < this.children.length; i++)
            if (this.children[i] != null) {// Skips all null children
                if (this.children[i].id == a) // Checks if the id of this child is true a
                    return i;
            }
        return -1;
    }

    public boolean hasNext() {
        return this.indexOfChildren().length > 0;
    }

    /**
     * Is there a child in this Node
     *
     * @param index location to check
     * @return if checked Node exists or not
     */
    public boolean exists(int index) {
        return this.children[index - 97] != null;
    }
}
