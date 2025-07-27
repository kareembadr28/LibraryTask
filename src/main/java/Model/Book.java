package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int availableCopies;
    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserBook> borrowedBy=new ArrayList<UserBook>();
    public Book() {
	}
    public Book(String title, String author, String genre, int availableCopies) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.availableCopies = availableCopies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}

	public List<UserBook> getBorrowedBy() {
		return borrowedBy;
	}

	public void setBorrowedBy(List<UserBook> borrowedBy) {
		this.borrowedBy = borrowedBy;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", genre=" + genre + ", availableCopies="
				+ availableCopies;
	}
	@Override
	public int hashCode() {
		return Objects.hash(author, availableCopies, borrowedBy, genre, id, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && availableCopies == other.availableCopies
				&& Objects.equals(borrowedBy, other.borrowedBy) && Objects.equals(genre, other.genre)
				&& Objects.equals(id, other.id) && Objects.equals(title, other.title);
	}
    
}
