package services;

public interface IBorrowable {
	public void borrowBook(long bookId);
	public void returnBook(long bookId);

}
