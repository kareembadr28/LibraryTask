package services;

import java.util.List;

import Model.Book;

public class BookSearchService implements ISearchable<Book> {

	private List<Book> booksList;

	public BookSearchService(List<Book> booksList) {
		this.booksList = booksList;
	}

	@Override
	public Book searchById(Long id) {
		return booksList.stream().filter(b->b.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public Book searchByName(String Title) {
		return booksList.stream().filter(b->b.getTitle().equalsIgnoreCase(Title)).findFirst().orElse(null);
	}
	
	public List<Book> searchByGenre(String genre) {
		return booksList.stream().filter(b -> b.getGenre().equalsIgnoreCase(genre)).toList();
	}
	public List<Book> searchByAuthor(String author) {
		return booksList.stream().filter(b -> b.getAuthor().equalsIgnoreCase(author)).toList();
	}

}
