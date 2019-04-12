/************************
 *
 * Created and maintained
 * by Allen Rocha
 * For more information
 * please visit:
 * https://github.com/allenerocha
 *
 * *********************/
public interface NodeInterface {
    /**
     * @return the ID of this Node
     */
    char getId();

    /**
     * @return the parent Node (can return null if this Node is the root)
     */
    Node getParent();

    /**
     * @return the children as an array
     */
    Node[] getChildren();

    /**
     * @return a boolean value; true->this is the root Node, false->this is NOT the root Node
     */
    boolean isRoot();

    /**
     * @param id the character ID for this Node
     */
    void setId(char id);

    /**
     * @param parent the previous Node connected to this Node (AKA Parent)
     */
    void setParent(Node parent);

    /**
     * @param children sets the children (rarely called)
     */
    void setChildren(Node[] children);

    /**
     * @param root identifies this as the root of the tree; true->this is the root, false->this is not the root
     */
    void setRoot(boolean root);

    /**
     * Calls the addChild method without having to call the Node constructors
     *
     * @param word the String to be parsed as Nodes
     */
    void insert(String word, Node parent);

    /**
     * Recursive function to add children to Node
     */
    void addChild(String word);

    /**
     * Integer value of the total amount of children this Node has
     *
     * @return Integer of size
     */
    int size();

    /**
     * returns an array of locations of non-null Nodes
     *
     * @return array of integers containing indexes of non-null Nodes otherwise -1
     */
    int[] indexOfChildren();

    /**
     * Checks if the children of this Node contains the character
     * @param a character to check
     * @return index number 0-25 if it exits -1 if it does not
     */
    int contains(char a);

    boolean hasNext();

    /**
     * Is there a child in this Node
     * @param index location to check
     * @return if checked Node exists or not
     * */
    boolean exists(int index);
}
