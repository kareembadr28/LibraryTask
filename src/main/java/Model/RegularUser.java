package Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import services.IBorrowable;
import services.LibraryManager;

@Entity
@DiscriminatorValue("REGULAR")

public class RegularUser extends User implements IBorrowable {

	public RegularUser() {
		super();
	}

	public RegularUser(String name, String email, String password) {
		super(name, email, password);
	}

	@Override
	public String toString() {
		return "RegularUser{" +
				"id=" + getId() +
				", name='" + getName() + '\'' +
				", email='" + getEmail() + '\'' +
				'}';
	}

	@Override
	public void borrowBook(long bookId) {
		new LibraryManager().borrowBook(bookId, getId());
		
	}

	@Override
	public void returnBook(long bookId) {
		
		new LibraryManager().returnBook(bookId, getId());
	}
	
	public void ViewBookCatalog()
	{
		new LibraryManager().viewBooks();
	}

}
