package services;
import java.util.Comparator;

import Model.Book;

public class BookNameComparator implements Comparator<Book> {

	@Override
	public int compare(Book book1, Book book2) {
		return book1.getTitle().compareToIgnoreCase(book2.getTitle());
	}

}
