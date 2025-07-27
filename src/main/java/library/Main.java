package library;

import Model.Admin;
import Model.Book;
import Model.RegularUser;
import Model.User;
import Model.UserBook;
import jakarta.persistence.*;

import java.awt.geom.Area;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import services.BookNameComparator;
import services.BookSearchService;
import services.DataLoader;
import services.LibraryManager;
import services.UserSearchService;

public class Main {
  public static void main(String[] args) {
    Scanner inter = new Scanner(System.in);
    EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("LibraryManager");
    EntityManager em = emf.createEntityManager();
    LibraryManager libraryManager = new LibraryManager(em);
    System.out.println("Welcome to the Library Management System");
    libraryManager.loaddata();
    List<Book> books = new DataLoader().loadBookList(em);

    System.out.println("enter ur role: 1 for admin, 2 for regular user");
    int role = Integer.parseInt(inter.nextLine());
    if (role == 1) {
      System.out.println("enter your email");
      String email = inter.nextLine();
      System.out.println("enter your password");
      String password = inter.nextLine();
      if (libraryManager.IsValidAdmin(email, password)) {

        Admin admin =
            libraryManager.getAdminByEmailAndPassword(email, password);
        System.out.println("Welcome " + admin.getName());

        boolean continueadmin = true;
        boolean exit = false;
        while (continueadmin) {
          System.out.println("1. Add Book");
          System.out.println("2. Remove Book");
          System.out.println("3. View All Books");
          System.out.println("4. Search Book by Name");
          System.out.println("5. Search Book by Genre");
          System.out.println("6.serach Book by Author");
          System.out.println("7. View All Users");
          System.out.println("8. Search User by Name");
          System.out.println("9.sort books by name");
          System.out.println("10. update book");
          System.out.println("11. register user");
          System.out.println("12. Exit");
          int choice = Integer.parseInt(inter.nextLine());
          switch (choice) {

          case 1:
            System.out.println("Enter book name:");
            String bookName = inter.nextLine();
            System.out.println("Enter book author:");
            String bookAuthor = inter.nextLine();
            System.out.println("Enter book genre:");
            String bookGenre = inter.nextLine();
            System.out.println("Enter number of copies:");
            int copies = Integer.parseInt(inter.nextLine());
            admin.AddBook(new Book(bookName, bookAuthor, bookGenre, copies));

            break;

          case 2:
            System.out.println("Enter book id to remove:");
            Long bookId = Long.parseLong(inter.nextLine());
            admin.DeleteBook(bookId);

            break;
          case 3:
            System.out.println("All Books:");
            libraryManager.viewBooks();

            break;
          case 4:
            System.out.println("Enter book name to search:");
            String searchName = inter.nextLine();
            if (searchName.isEmpty()) {
              System.out.println("Invalid input. Please try again.");
              continue;
            }
            books = new DataLoader().loadBookList(em);
            BookSearchService bookSearchService = new BookSearchService(books);
            Book bookByName = bookSearchService.searchByName(searchName);
            if (bookByName != null) {
              System.out.println("Book found: " + bookByName.getTitle() +
                                 " by " + bookByName.getAuthor());
            } else {
              System.out.println("Book not found");
            }

            break;
          case 5:
            System.out.println("Enter book genre to search:");
            String searchGenre = inter.nextLine();
            if (searchGenre.isEmpty()) {
              System.out.println("Invalid input. Please try again.");
              continue;
            }
            BookSearchService bookSearchByGenre = new BookSearchService(books);
            List<Book> booksByGenre =
                bookSearchByGenre.searchByGenre(searchGenre);
            if (booksByGenre.isEmpty()) {
              System.out.println("No books found in this genre");
            } else {
              System.out.println("Books found in genre " + searchGenre + ":");
              for (Book b : booksByGenre) {
                System.out.println(b.getTitle() + " by " + b.getAuthor());
              }
            }

            break;
          case 6:
            System.out.println("Enter book author to search:");
            String searchAuthor = inter.nextLine();
            if (searchAuthor.isEmpty()) {
              System.out.println("Invalid input. Please try again.");
              continue;
            }
            BookSearchService bookSearchByAuthor = new BookSearchService(books);
            List<Book> booksByAuthor =
                bookSearchByAuthor.searchByAuthor(searchAuthor);
            if (booksByAuthor.isEmpty()) {
              System.out.println("No books found by this author");
            } else {
              System.out.println("Books found by author " + searchAuthor + ":");
              for (Book b : booksByAuthor) {
                System.out.println(b.getTitle() + " by " + b.getAuthor());
              }
            }

            break;
          case 7:
            System.out.println("All Users:");
            List<User> users = new DataLoader().loadUserList(em);
            users =
                users.stream().filter(u -> u instanceof RegularUser).toList();
            if (users.isEmpty()) {
              System.out.println("No users found");
            } else {
              for (User user : users) {
                System.out.println("User ID: " + user.getId() +
                                   ", Name: " + user.getName() +
                                   ", Email: " + user.getEmail());
              }
            }
            break;
          case 8:
            System.out.println("Enter user name to search:");
            String searchUserName = inter.nextLine();
            if (searchUserName.isEmpty()) {
              System.out.println("Invalid input. Please try again.");
              continue;
            }
            List<User> userList = new DataLoader().loadUserList(em);
            UserSearchService userSearchService =
                new UserSearchService(userList);
            User userByName = userSearchService.searchByName(searchUserName);
            if (userByName != null) {
              System.out.println("User found: " + userByName.getName() +
                                 ", Email: " + userByName.getEmail());
            } else {
              System.out.println("User not found");
            }

            break;
          case 9:
            books = new DataLoader().loadBookList(em);
            books.sort(new BookNameComparator());
            System.out.println("Books sorted by name:");
            for (Book b : books) {
              System.out.println(b.getTitle() + " by " + b.getAuthor());
            }
            break;
          case 10:
            System.out.println("Enter book id to update:");
            Long bookIdToUpdate = Long.parseLong(inter.nextLine());
            String newTitle, newAuthor, newGenre;
            int newCopies;
            System.out.println("enter title");
            newTitle = inter.nextLine();
            System.out.println("enter author");
            newAuthor = inter.nextLine();
            System.out.println("enter genre");
            newGenre = inter.nextLine();
            System.out.println("enter number of copies");
            newCopies = Integer.parseInt(inter.nextLine());
            if (newTitle.isEmpty() || newAuthor.isEmpty() ||
                newGenre.isEmpty() || newCopies <= 0) {
              System.out.println("Invalid input. Please try again.");
              continue;
            }
            admin.UpdateBook(bookIdToUpdate, newTitle, newAuthor, newGenre,
                             newCopies);

            break;

          case 11:
            System.out.println("Enter user name:");
            String userName = inter.nextLine();
            System.out.println("Enter user email:");
            String userEmail = inter.nextLine();
            System.out.println("Enter user password:");
            String userPassword = inter.nextLine();
            if (userName.isEmpty() || userEmail.isEmpty() ||
                userPassword.isEmpty()) {
              System.out.println("Invalid input. Please try again.");
              continue;
            }
            admin.RegisterUser(
                new RegularUser(userName, userEmail, userPassword));
            break;

          case 12:
            System.out.println("Exiting...");
            System.out.println("" + admin.getName() +
                               " logged out successfully");
            continueadmin = false;
            exit = true;
            break;

          default:
            System.out.println("Invalid choice");
            break;
          }
          if (!exit) {
            System.out.println("\nPress Enter to continue");
            inter.nextLine();
          }
        }

      } else {
        System.out.println("invalid email or password");
      }

    } else if (role == 2) {
    	Boolean userexit = false;
    	System.out.println("enter ur email");
    	String email = inter.nextLine();
    	System.out.println("enter ur password");
    	String password = inter.nextLine();
    	if(libraryManager.IsValidUser(email, password))
    	{
    			RegularUser user = libraryManager.getUserByEmailAndPassword(email, password);
    						System.out.println("Welcome " + user.getName());
    						boolean continueUser = true;
    						while(continueUser)
    						{
    							System.out.println("1. View All Books");
    							System.out.println("2. borrow Book");
    							System.out.println("3. Return Book");
    							System.out.println("4. Exit");
    							int choice = Integer.parseInt(inter.nextLine());
    							switch(choice)
								{
									case 1:
										System.out.println("All Books:");
										libraryManager.viewBooks();
										break;
									case 2:
										System.out.println("Enter book id to borrow:");
                         				Long bookIdToBorrow = Long.parseLong(inter.nextLine());									
                         				books = new DataLoader().loadBookList(em);
                         				BookSearchService booksearchservice = new BookSearchService(books);
                         		Book book=	booksearchservice.searchById(bookIdToBorrow);
                         		if(book!=null)
                         		{
                         			System.out.println("Book found: " + book.getTitle() + " by " + book.getAuthor());
                         			System.out.println("want to borrow this book? (yes/no)");
                         			String borrowChoice = inter.nextLine();
                         			if(borrowChoice.equalsIgnoreCase("yes"))
						 			{
						 				user.borrowBook(book.getId());
						 			}
						 			else
						 			{
						 				System.out.println("Book borrowing cancelled");
						 			}
                         		}
                         		else
						 		{
						 			System.out.println("Book not found");
						 		}
										break;
									case 3:
										System.out.println("Enter book id to return:");
										Long returnBookId = Long.parseLong(inter.nextLine());
										user.returnBook(returnBookId);
										System.out.println("Book returned successfully");
										break;
										case 4:
											System.out.println("Exiting...");
											System.out.println("" + user.getName() + " logged out successfully");
											continueUser = false;
											userexit = true;
											break;
									default:
										System.out.println("Invalid choice");
								}
    							if(!userexit) {
    							System.out.println("\nPress Enter to continue");
    							inter.nextLine();
    							}
    						}
    			
    	}
    	else {
			System.out.println("u have not been registered yet or invalid email or password");
		}

    } else {
      System.out.println("invalid role");
    }
  }
}
