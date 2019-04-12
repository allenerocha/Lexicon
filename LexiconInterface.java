/************************
 *
 * Created and maintained
 * by Allen Rocha
 * For more information
 * please visit:
 * https://github.com/allenerocha
 *
 * *********************/
public interface LexiconInterface {
    /**
     * Crawls through the tree
     */
    public void TreeCrawl(String filepath);

    /**
     * @param inputFile file location of the dictionary to be organized
     */
    public void OrganizeDictionary(String inputFile);
}
