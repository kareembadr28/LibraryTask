package Model;

import java.util.HashSet;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;
import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<UserBook>books=new ArrayList<UserBook>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public User() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<UserBook> getBorrowedBooks() {
		return books;
	}
	public void setBorrowedBooks(List<UserBook> borrowedBooks) {
		this.books = borrowedBooks;
	}
	@Override
	public int hashCode() {
		return Objects.hash(books, email, id, name, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(books, other.books) && Objects.equals(email, other.email)
				&& id == other.id && Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password +" ]";
	}
	
	

}
