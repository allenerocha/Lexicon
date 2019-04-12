/************************
 *
 * Created and maintained
 * by Allen Rocha
 * For more information
 * please visit:
 * https://github.com/allenerocha
 *
 * *********************/

import java.io.*;
import java.util.*;

public class Lexicon implements LexiconInterface {
    private String filepath;

    @Override
    public void TreeCrawl(String filepath) {
        this.filepath = filepath;
        Node[] roots = new Node[26]; // ArrayList to hold the roots (max 26)
        Scanner scanner = new Scanner(System.in); // Scanner for the user to input a sentence

        try { // Parses through the text file
            BufferedReader buffer = new BufferedReader(new FileReader(this.filepath));
            String line = buffer.readLine(); // Adds current line to the String line

            while (line != null) { // Checks if the line does not exist
                String[] filteredLine = wordFilter(line); // Array of Strings that holds the words that were put through the filter
                for (String fl : filteredLine) {
                    try {
                        boolean exists = false;
                        int index = 0;
                        for (int i = 0; i < roots.length; i++)
                            if (roots[i] != null && roots[i].getId() == fl.charAt(0)) {
                                index = i;
                                exists = true;
                                break;
                            }
                        if (exists && fl.length() > 1)
                            roots[index].insert(fl.substring(1), roots[index]); // Inserts the new Nodes in the position after the root and assigns the root as its parent
                        else if (!exists && fl.length() > 1)
                            roots[fl.charAt(0) - 97] = new Node(fl, true); // Creates a new root with branches
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println(line);
                    }
                }
                line = buffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Enter a sentence:"); // Prompts users to enter a sentence
        scanner = new Scanner(System.in); // Resets the scanner function for a new user input
        String sentence = scanner.nextLine(); // String to contain the sentence that the user inputted
        String[] filteredWords = wordFilter(sentence); // Splits the sentence string at each space and converts the resulting String array to an

        for (String fw : filteredWords) { // This goes through the words in the entered sentence
            /*CHECK EACH OF THE INDEXES*/
            int pos = 0;
            if (roots[fw.charAt(0) - 97] != null) { // Starts the spell check at the root
                if (fw.length() > 1) // Checks if the string is more than 1 character
                    dictionarySearch(fw.substring(1), roots[fw.charAt(0) - 97], fw, pos + 1);// Passes the word except the root
                else if (roots[fw.charAt(0) - 97] == null) // Checks if the character is a root
                    System.out.println("The word: " + fw + " has been misspelled");
            }
        }

    }

    @Override
    public void OrganizeDictionary(String inputFile) {
        try {
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String inputLine;
            List<String> lineList = new ArrayList<>();
            while ((inputLine = bufferedReader.readLine()) != null) {
                lineList.add(inputLine);
            }
            fileReader.close();

            Collections.sort(lineList);

            FileWriter fileWriter = new FileWriter(inputFile);
            PrintWriter out = new PrintWriter(fileWriter);
            for (String outputLine : lineList) {
                out.println(outputLine);
            }
            out.flush();
            out.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Comparison
     *
     * @param word string of the passed word
     * @param node current children Nodes
     */
    private void dictionarySearch(String word, Node node, String l, int pos) {
        if (word.length() > 1)
            if (node.exists(word.charAt(0)))
                dictionarySearch(word.substring(1), node.getChildren()[word.charAt(0) - 97], l, pos + 1);
            else
                suggestions(node, l, pos);
        else {
            if (!node.exists(word.charAt(0)))
                suggestions(node, l, pos);
        }
    }

    /**
     * @param node current Node
     * @param l    word
     * @param pos  current postision
     */
    private void suggestions(Node node, String l, int pos) {
        System.out.printf("The word %s has been misspelled!\nSuggestions: ", l);
        String mis = l;
        StringBuilder sugWords = new StringBuilder(); // Container for suggested word
        l = l.substring(0, pos); //

        while (node.hasNext()) {
            node = node.getChildren()[node.indexOfChildren()[0]]; // Sets the Node to the child at the index of the passed character
            sugWords.append(node.getId()); // Adds the character to
        }
        System.out.println(l + sugWords.toString()); // Displays the suggested word
        System.out.printf("\nWould you like to add [%s] to the dictionary?\t[Y/N]\n", mis);
        Scanner input = new Scanner(System.in);
        char in = input.nextLine().charAt(0);
        switch (in) {
            case 'y':
            case 'Y':
                addToDictionary(mis);
                break;
            default:
                break;
        }


        sugWords = new StringBuilder(); // // Resets the suggested word container
    }

    /**
     * @param unfilteredLine filters out the special characters and punctuation
     * @return returned filters
     */
    private String[] wordFilter(String unfilteredLine) {
        String temp = unfilteredLine.trim();
        temp = temp.replaceAll("\n", "");
        temp = temp.replaceAll("\t", "");
        temp = temp.replaceAll("\\.", "");
        temp = temp.replaceAll(",", "");
        temp = temp.replaceAll("\'", "");
        temp = temp.replaceAll("\"", "");
        temp = temp.replaceAll("\\?", "");
        temp = temp.replaceAll("!", "");
        return temp.toLowerCase().split(" ");
    }

    /**
     * Adds the word to the dictionary
     *
     * @param word to be added to the dictionary
     */
    private void addToDictionary(String word) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filepath, true));
            bufferedWriter.newLine(); // Makes a new line
            bufferedWriter.write(word); // Writes the word to the file on the new line
            bufferedWriter.flush(); // flushes the characters from a write buffer to the character or byte stream as an intended destination
            OrganizeDictionary(this.filepath); // Call to reorganize the dictionary
            System.out.printf("The word [%s], has been added!\n", word); // Output informing the user the word has been added
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // Closes the file
            if (bufferedWriter != null) try {
                bufferedWriter.close();
            } catch (IOException ignored) {

            }
        }
    }
}
