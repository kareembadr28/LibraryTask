package services;

import java.util.HashMap;
import java.util.List;

import Model.Book;
import Model.User;
import jakarta.persistence.EntityManager;

public class DataLoader {
	public static HashMap<Long, Book> loadbookMap(EntityManager em) {
		HashMap<Long, Book> books = new HashMap<>();
		try {
			List<Book> bookList = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
			for (Book book : bookList) {
				books.put(book.getId(), book);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return books;
		
		
	}
	public static HashMap<Long, User> loadUserMap(EntityManager em) {
		HashMap<Long, Model.User> users = new HashMap<>();
		try {
			List<User> userList = em.createQuery("SELECT u FROM User u", User.class).getResultList();
			for (User user : userList) {
				users.put(user.getId(), user);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return users;
		
	}
   public static List<Book> loadBookList(EntityManager em) {
	   	  try {
		 List<Book> bookList = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
		 return bookList;
	  } catch (Exception e) {
		
		 e.printStackTrace();
		 return null;
	  }
   }
   public static List<User>loadUserList(EntityManager em) 
   {
	   try {
		 List<User> UserList = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		 return UserList;
	  } catch (Exception e) {
		 
		 e.printStackTrace();
		 return null;
	  }
 }
   
}
