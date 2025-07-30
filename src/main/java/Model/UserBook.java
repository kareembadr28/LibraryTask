package Model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity

public class UserBook {
		@EmbeddedId
	private UserBookId id;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@MapsId("bookId")
	@JoinColumn(name = "book_id")
	private Book book;
	
	private LocalDate borrowDate;
	private LocalDate returnDate;

	public UserBook() {
	}
	public UserBook(User user, Book book, LocalDate borrowDate, LocalDate returnDate) {
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.id = new UserBookId(user.getId(), book.getId());
	}
	public UserBookId getId() {
		return id;
	}
	public void setId(UserBookId id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public LocalDate getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	@Override
	public String toString() {
return "UserBook [id=" + id  + ", borrowDate=" + borrowDate
				+ ", returnDate=" + returnDate + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserBook)) return false;

		UserBook userBook = (UserBook) o;

		return id.equals(userBook.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
