import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";
    public static final String ORANGE = "\u001B[33m";

    private static Book[] books = new Book[50];
    private static int bookCount = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String libraryName, libraryAddress;

        System.out.println("========= SET UP LIBRARY =========");
        System.out.print("=> Enter Library's Name : ");
        libraryName = scanner.nextLine();
        System.out.print("=> Enter Library's Address : ");
        libraryAddress = scanner.nextLine();
        System.out.println(BLUE + " \"" + libraryName + "\""+RESET+ "Library is already created in \""+BLUE + libraryAddress +
                "\""+RESET+" address successfully on " + new Date() + "\n" + RESET);
        CellStyle numberStyle = new CellStyle(HorizontalAlign.center);
        int option;
        do {

            Table t = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND_HEADER_FOOTER_AND_COLUMNS);
            t.setColumnWidth(0, 80, 20);

            t.addCell(BLUE + libraryName + " LIBRARY,  " + libraryAddress + BLUE, numberStyle);

            t.addCell("1- Add Book");
            t.addCell("2- Show All Books");
            t.addCell("3- Show Available Books");
            t.addCell("4- Borrow Book");
            t.addCell("5- Return Book");
            t.addCell("6- Search Book");
            t.addCell("7- Remove Book");
            t.addCell("8- Exit");
            System.out.println(t.render());
            option = getUserInput(scanner, "=> Choose option (1-8): ");
            scanner.nextLine();

            switch (option) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    showAllBooks(scanner);
                    break;
                case 3:
                    showAvailableBooks(scanner);
                    break;
                case 4:
                    borrowBook(scanner);
                    break;
                case 5:
                    returnBook(scanner);
                    break;
                case 6:
                    searchName(scanner);
                    break;
                case 7:
                    removeBook();
                    break;
                case 8:
                    System.out.println("(^-^) Good Bye! (^-^)");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }

        } while (true);
    }

    private static void addBook(Scanner scanner) {

        String title, author, authorActive,publishedYear;;

        System.out.println("\t\t\t\t========= ADD BOOK INFO =========");

        System.out.print("=> Enter Book's Name : ");
        title = scanner.nextLine();
        System.out.print("=> Enter Book Author Name: ");
        author = scanner.nextLine();
        System.out.print("Enter Author Year Active: ");
        authorActive = scanner.nextLine();
        System.out.print("=> Enter Published Year : ");
        publishedYear = scanner.nextLine();

        Book addNewBook = new Book(title, author, publishedYear, authorActive, true);

        if (bookCount < books.length) {
            books[bookCount++] = addNewBook;  // Increment bookCount after adding the book
            System.out.println(BLUE+"Book is added successfully\n"+RESET);
        } else {
            System.out.println(RED+"Cannot add more books. Library is full."+RESET);
        }
    }
    private static void showAllBooks(Scanner scanner) {
        System.out.println("\t\t\t\t========= ALL BOOKS INFO =========");
        CellStyle numberStyle = new CellStyle(HorizontalAlign.center);
        Table t = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        t.addCell(PURPLE + "ID", numberStyle);
        t.addCell(ORANGE + "TITLE", numberStyle);
        t.addCell(GREEN + "AUTHOR", numberStyle);
        t.addCell("PUBLIC DATE", numberStyle);
        t.addCell("STATUS", numberStyle);

        t.setColumnWidth(0, 12, 20);
        t.setColumnWidth(1, 16, 20);
        t.setColumnWidth(2, 20, 30);
        t.setColumnWidth(3, 10, 20);
        t.setColumnWidth(4, 10, 20);

        if (bookCount == 0) {
            System.out.println(RED + "No Book Available\n" + RESET);
        } else {
           for (int i = 0; i < bookCount; i++) {
                if (books[i] != null) {
                    t.addCell(PURPLE + String.valueOf(books[i].getId()) + RESET, numberStyle);
                    t.addCell(ORANGE + books[i].getTitleBook() + RESET, numberStyle);
                    t.addCell(GREEN + books[i].getAuthor() + "( " + books[i].getAuthorActive()+" )" + RESET, numberStyle);
                    t.addCell((books[i].getPublishedYear()), numberStyle);
                    t.addCell((books[i].getStatus() ? BLUE + "Available" + RESET : RED + "Unavailable" + RESET), numberStyle);
                }
            }
        }

        System.out.println(t.render());
        System.out.print("\nPress " + BLUE + "\"ENTER\" " + RESET + "to continue...");
        String input = scanner.nextLine();
    }

    private static void showAvailableBooks(Scanner scanner) {
        System.out.print("\t\t\t\t========= AVAILABLE BOOKS INFO =========");
        CellStyle numberStyle = new CellStyle(HorizontalAlign.center);

        Table t = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        t.addCell(PURPLE + "ID", numberStyle);
        t.addCell(ORANGE + "TITLE", numberStyle);
        t.addCell(GREEN + "AUTHOR", numberStyle);
        t.addCell("PUBLIC DATE", numberStyle);
        t.addCell("STATUS", numberStyle);

        t.setColumnWidth(0, 12, 20);
        t.setColumnWidth(1, 16, 20);
        t.setColumnWidth(2, 20, 30);
        t.setColumnWidth(3, 10, 20);
        t.setColumnWidth(4, 10, 20);
        boolean availableBooksExist = false;
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getStatus()) {
                availableBooksExist = true;
                t.addCell(PURPLE + (books[i].getId()) + RESET, numberStyle);
                t.addCell(ORANGE + books[i].getTitleBook() + RESET, numberStyle);
                t.addCell(GREEN + books[i].getAuthor() + "( " + books[i].getAuthorActive()+" )" + RESET, numberStyle);
                t.addCell((books[i].getPublishedYear()), numberStyle);
                t.addCell((books[i].getStatus() ? BLUE + "Available" + RESET : RED + "Unavailable" + RESET), numberStyle);
                System.out.println();
            }
        }

        if (!availableBooksExist) {
            System.out.println(RED + "No Available Books\n" + RESET);
        }
        System.out.println(t.render());
        System.out.print("\nPress " + BLUE + "\"ENTER\" " + RESET + "to continue...");
        String input = scanner.nextLine();
    }
    /**
     * Borrow a book by setting its status to unavailable.
     * Displays relevant messages based on the borrowing result.
     *
     * @param scanner The Scanner object for user input.
     */
    private static void borrowBook(Scanner scanner) {
        int borrowId;

        System.out.println("\t\t\t\t========= BORROW BOOK INFO =========");
        borrowId= getUserInput(scanner, "=> Enter ID to Borrow: ");

        boolean bookExists = false;

        for (int i = 0; i < bookCount; i++) {
            if (books[i].getId() == borrowId) {
                bookExists = true;
                if (books[i].getStatus()) {
                    books[i].setStatus(false);
                    System.out.println(BLUE+"Book ID : " + borrowId + " is borrowed successfully...\n"+RESET);
                } else {
                    System.out.println(RED+"Book ID : " + borrowId + " is already borrowed...\n"+RESET);
                }
                break;
            }
        }

        if (!bookExists) {
            System.out.println(RED+"Book ID : " + borrowId + " not Exist...\n"+RESET);
        }
    }

    private static void returnBook(Scanner scanner) {
        int returnId;

        System.out.println("\t\t\t\t========= RETURN BOOK INFO =========");
        returnId = getUserInput(scanner, "=> Enter ID to Return: ");

        boolean bookExists = false;

        for (int i = 0; i < bookCount; i++) {
            if (books[i].getId() == returnId) {
                bookExists = true;
                if (!books[i].getStatus()) {
                    books[i].setStatus(true);
                    System.out.println(BLUE+"Book ID : " + returnId + " is returned successfully...\n"+RESET);
                } else {
                    System.out.println(RED+"Book ID : " + returnId + " is failed to return...\n"+RESET);
                }
                break;
            }
        }

        if (!bookExists) {
            System.out.println(RED+"Book ID : " + returnId + " not Exist...\n"+RESET);
        }
    }
    /**
     * Removes a book from the library based on the provided book ID.
     * If the book is found, it is removed by shifting the remaining elements in the array.
     * Displays a success message if the book is removed, or an error message if the book is not found.
     */
    private static void removeBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t\t\t\t========= REMOVE BOOK =========");
        int removeId = getUserInput(scanner, "=> Enter ID to Remove: ");

        boolean bookExists = false;

        for (int i = 0; i < bookCount; i++) {
            if (books[i] != null && books[i].getId() == removeId) {
                bookExists = true;
                // Shift the remaining elements to fill the gap
                for (int j = i; j < bookCount - 1; j++) {
                    books[j] = books[j + 1];
                }
                // Set the last element to null and decrement the book count
                books[bookCount - 1] = null;
                bookCount--;
                System.out.println(BLUE+"Book ID : " + removeId + " is removed successfully...\n"+RESET);
                break;
            }
        }
        if (!bookExists) {
            System.out.println(RED+"Book ID : " + removeId + " not Exist...\n"+RESET);
        }

        System.out.println(RED+"\nPress "+BLUE+"\"ENTER\" "+RESET +"to continue..."+RESET);
        String input = scanner.nextLine();
    }
    /**
     * Search for books based on the author's name or active year of the author.
     * Displays the matching books in a tabular format.
     * If no books are found, a corresponding message is displayed.
     *
     * @param scanner The Scanner object for user input.
     */
    private static void searchName(Scanner scanner) {

        System.out.print("=> Enter author's name to search: ");
        String searchQuery = scanner.nextLine().toLowerCase();

        if (searchQuery.isEmpty()) {
            return;
        }
        CellStyle numberStyle = new CellStyle(HorizontalAlign.center);
        Table t = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        t.addCell(PURPLE + "ID", new CellStyle(HorizontalAlign.center));
        t.addCell(ORANGE + "TITLE", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "AUTHOR", new CellStyle(HorizontalAlign.center));
        t.addCell("PUBLIC DATE", new CellStyle(HorizontalAlign.center));
        t.addCell("STATUS", new CellStyle(HorizontalAlign.center));
        t.setColumnWidth(0, 12, 20);
        t.setColumnWidth(1, 20, 20);
        t.setColumnWidth(2, 20, 20);
        t.setColumnWidth(3, 20, 20);
        t.setColumnWidth(4, 20, 20);

        boolean found = false;

        for (int i = 0; i < bookCount; i++) {
            String author = books[i].getAuthor().toLowerCase();
            String authorActive = books[i].getAuthorActive().toLowerCase();

            if (author.contains(searchQuery) || authorActive.contains(searchQuery)) {
                found = true;

                t.addCell(PURPLE + (books[i].getId()) + RESET, numberStyle);
                t.addCell(ORANGE + books[i].getTitleBook() + RESET, numberStyle);
                t.addCell(GREEN + books[i].getAuthor() + "( " + books[i].getAuthorActive() +")"+ RESET, numberStyle);
                t.addCell((books[i].getPublishedYear()), numberStyle);
                t.addCell((books[i].getStatus() ? BLUE + "Available" + RESET : RED + "Unavailable" + RESET), numberStyle);

                System.out.println();
            }
        }

        if (found) {
            System.out.println(t.render());
        } else {
            System.out.println(RED + "No books found with the specified author's name.\n" + RESET);
        }
        System.out.print("\nPress "+BLUE+"\"ENTER\" "+RESET +"to continue...");
        String input = scanner.nextLine();
    }

    /**
     * Validation user input switch case, borrow method, delete method
     */
    public static int getUserInput(Scanner scanner, String prompt) {

        String input;
        do {
            System.out.print(prompt);
            input = scanner.next();
            if (!input.matches("^[1-9]\\d*")) {
                System.out.println(RED + "Invalid input. Please review your entered." + RESET);
            }
        } while (!input.matches("^[1-9]\\d*"));

        return Integer.parseInt(input);
    }
}
