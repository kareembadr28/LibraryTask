package services;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import Model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class LibraryManager {
    private EntityManager em;
    Map<Long, Book> books=new HashMap<>();
    Map<Long, User> users=new HashMap<>();
    Set<String> genres=new HashSet<>();
    List<User>usersList=new ArrayList<>();
    List<Book>booksList=new ArrayList<>();

    public List<User> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}
	public List<Book> getBooksList() {
		return booksList;
	}
	public void setBooksList(List<Book> booksList) {
		this.booksList = booksList;
	}
	public LibraryManager(EntityManager em) {
        this.em = em;
        
        
    }
    public LibraryManager() {
		this.em = Persistence.createEntityManagerFactory("LibraryManager").createEntityManager();
		
	}
    
    public void loaddata()
    {
    	this.books = DataLoader.loadbookMap(em);
        this.users = DataLoader.loadUserMap(em);
        this.usersList = DataLoader.loadUserList(em);
        this.booksList = DataLoader.loadBookList(em);
        this.genres=booksList.stream()
				.map(b->b.getGenre())
				.collect(Collectors.toSet());
    }

    public void borrowBook(Long bookId, long userId) {
        em.getTransaction().begin();
        try {
            User user = em.find(User.class, userId);
            Book book = em.find(Book.class, bookId);

            if (user == null || book == null)
                throw new IllegalArgumentException("user or book not found");
            if (book.getAvailableCopies() == 0)
                throw new IllegalStateException("there is no book  " );
            if (findUserBook(userId, bookId) != null)
                throw new IllegalStateException("u have already borrowed this book before");
            Long count=em.createQuery(
				"SELECT COUNT(ub) FROM UserBook ub WHERE ub.user.id = :userId AND ub.returnDate IS NULL", Long.class)
				.setParameter("userId", userId)
				.getSingleResult();
            if (count >= 3)
				throw new IllegalStateException("u have already borrowed 3 books, u can't borrow more");

            UserBook userBook = new UserBook(user, book, LocalDate.now(), null);
            em.persist(userBook);

            user.getBorrowedBooks().add(userBook);
            book.getBorrowedBy().add(userBook);
            book.setAvailableCopies(book.getAvailableCopies() - 1);

            em.getTransaction().commit();
            
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void returnBook(Long bookId, long userId) {
        em.getTransaction().begin();
        try {
            User user = em.find(User.class, userId);
            Book book = em.find(Book.class, bookId);
            if (user == null || book == null)
                throw new IllegalArgumentException("user or book not found");

            UserBook userBook = findUserBook(userId, bookId);
            if (userBook != null) {
                userBook.setReturnDate(LocalDate.now());
                book.setAvailableCopies(book.getAvailableCopies() + 1);
            } else {
                System.out.println("u have not borrowed this book");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    private UserBook findUserBook(Long userId, Long bookId) {
    	List<UserBook> result = em.createQuery(
    	        "SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId AND ub.returnDate IS NULL", UserBook.class)
    	        .setParameter("userId", userId)
    	        .setParameter("bookId", bookId)
    	        .getResultList();

    	    return result.isEmpty() ? null : result.get(0);
    }
	public void viewBooks() {
		List<Book>books=DataLoader.loadBookList(em);
		for(Book book : books)
		{
			System.out.println("book title : "+ book.getTitle());
			System.out.println("book author : "+ book.getAuthor());
			System.out.println("book genre : "+ book.getGenre());
			System.out.println("Available copies : "+ book.getAvailableCopies());
			System.out.println("*******************************");
		}
		
	}
	public void AddBook(Book book) {
	em.getTransaction().begin();
		try {
			List<Book> existingBooks = em.createQuery(
		            "select b from Book b where b.title = :title and b.author = :author", Book.class)
		            .setParameter("title", book.getTitle())
		            .setParameter("author", book.getAuthor())
		            .getResultList();

		        if (!existingBooks.isEmpty()) {
		            throw new IllegalArgumentException("This book already exists!");
		        }
		        
			
			em.persist(book);
			em.flush();
			books.put(book.getId(), book);
			booksList.add(book);
			genres.add(book.getGenre());
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}
 
		
	}
	public void RemoveBook(long bookId) {
	
		em.getTransaction().begin();
		try {
			Book book = em.find(Book.class, bookId);
			if(book==null)
			throw new IllegalArgumentException("book not found!!!");
			boolean isBorrowed = em.createQuery(
				"SELECT COUNT(ub) > 0 FROM UserBook ub WHERE ub.book.id = :bookId ", Boolean.class)
				.setParameter("bookId", bookId)
				.getSingleResult();
			if (isBorrowed) {
				throw new IllegalStateException("This book is currently borrowed and cannot be removed.");
			}
			
			books.remove(bookId);
			booksList.remove(book);
			
			em.remove(book);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}
	}
	public void RegisterUser(User user) {
		
		em.getTransaction().begin();
		try {

         List<User>existingUsers=em.createQuery("select u from User u where u.email= :Email",User.class)
        		 .setParameter("Email", user.getEmail())
        		 .getResultList();
         if(!existingUsers.isEmpty())
        	 throw new IllegalArgumentException("this user is already exist");
			
			em.persist(user);
			em.flush();
			usersList.add(user);
			users.put(user.getId(), user);

			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}
	}
	public void UpdateBook(Long bookId, String newTitle, String newAuthor, String newGenre, int newAvailableCopies) {
	    em.getTransaction().begin();
	    try {
	        Book book = em.find(Book.class, bookId);
	        if (book == null) {
	            throw new IllegalArgumentException("Book not found!");
	        }

	        book.setTitle(newTitle);
	        book.setAuthor(newAuthor);
	        book.setGenre(newGenre);
	        book.setAvailableCopies(newAvailableCopies);

	        em.merge(book);
	        genres.add(newGenre);
	        
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    }
	}
	
	public boolean IsValidAdmin(String email, String password) {
		em.getTransaction().begin();
		try {
			List<Admin> admins = em.createQuery("SELECT a FROM Admin a WHERE a.email = :email AND a.password = :password", Admin.class)
					.setParameter("email", email)
					.setParameter("password", password)
					.getResultList();
			em.getTransaction().commit();
			return admins.size()==1;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public boolean IsValidUser(String email, String password) {
		em.getTransaction().begin();
		try {
			List<User> users = em.createQuery("SELECT u FROM RegularUser u WHERE u.email = :email AND u.password = :password", User.class)
					.setParameter("email", email)
					.setParameter("password", password)
					.getResultList();
			em.getTransaction().commit();
			return users.size()==1;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	
	public RegularUser getUserByEmailAndPassword(String email, String password) {
		em.getTransaction().begin();
		try {
			List<RegularUser> users = em.createQuery("SELECT u FROM RegularUser u WHERE u.email = :email AND u.password = :password", RegularUser.class)
					.setParameter("email", email)
					.setParameter("password", password)
					.getResultList();
			em.getTransaction().commit();
			return users.isEmpty() ? null : users.get(0);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		}
	}
	
	public Admin getAdminByEmailAndPassword(String email, String password) {
		em.getTransaction().begin();
		try {
			List<Admin> admins = em.createQuery("SELECT a FROM Admin a WHERE a.email = :email AND a.password = :password", Admin.class)
					.setParameter("email", email)
					.setParameter("password", password)
					.getResultList();
			em.getTransaction().commit();
			return admins.isEmpty() ? null : admins.get(0);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		}
	}
	
	public List<UserBook> getunreturneBooks() {
		em.getTransaction().begin();
		try {
			List<UserBook> userBooks = em.createQuery(
				"select ub from UserBook ub where ub.returnDate is Null", UserBook.class)
				.getResultList();
			em.getTransaction().commit();
			return userBooks;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public List<Book>getborrowedBooks(Long userId) {
		em.getTransaction().begin();
		try {
			List<UserBook> userBooks = em.createQuery(
				"SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.returnDate IS NULL", UserBook.class)
				.setParameter("userId", userId)
				.getResultList();
			em.getTransaction().commit();
			return userBooks.stream()
				.map(UserBook::getBook)
				.collect(Collectors.toList());
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
    

}
