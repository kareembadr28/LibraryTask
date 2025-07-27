package Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import services.*;

@Entity
@DiscriminatorValue("ADMIN")

public class Admin extends User {

	public Admin() {
		super();
	}

	public Admin(String name, String email, String password) {
		super(name, email, password);
	}
    public void AddBook(Book book)
    {
    	new LibraryManager().AddBook(book);
    }
    
    public void DeleteBook(long BookId)
    {
    	new LibraryManager().RemoveBook(BookId);
    }
    
    public void RegisterUser(User user)
    {
    	new LibraryManager().RegisterUser(user);
    }
    
    public void UpdateBook(Long bookId, String newTitle, String newAuthor, String newGenre, int newAvailableCopies)
    {
    	new LibraryManager().UpdateBook(bookId, newTitle, newAuthor, newGenre, newAvailableCopies);
    }
    
    @Override
	public String toString() {
		return "Admin{" +
				"id=" + getId() +
				", name='" + getName() + '\'' +
				", email='" + getEmail() + '\'' +
				'}';
	}

}
