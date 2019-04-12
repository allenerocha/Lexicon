/************************
 *
 * Created and maintained
 * by Allen Rocha
 * For more information
 * please visit:
 * https://github.com/allenerocha
 *
 * *********************/
public class Main {
    public static void main(String[] args) {
        // MENU
        System.out.println("::MENU::\n1. Type\t2. Organize dictionary\n");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int input = scanner.nextInt();
        switch (input) {
            case 1: {
                System.out.println("Enter file path."); // Prompts for the file path of the text file
                String filepath = scanner.nextLine(); // Adds the file path to the string file path
                Lexicon lexicon = new Lexicon();
                lexicon.TreeCrawl(filepath);
            }
            break;
            case 2: {
                System.out.println("Enter file path."); // Prompts for the file path of the text file
                String filepath = scanner.nextLine(); // Adds the file path to the string file path
                Lexicon lexicon = new Lexicon();
                lexicon.OrganizeDictionary(filepath);
            }
            break;
            default:
                break;
        }
    }
}
