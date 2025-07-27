package Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserBookId {
	private Long userId;
	private Long bookId;

	public UserBookId() {
	}

	public UserBookId(Long userId, Long bookId) {
		this.userId = userId;
		this.bookId = bookId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserBookId)) return false;

		UserBookId that = (UserBookId) o;

		if (!userId.equals(that.userId)) return false;
		return bookId.equals(that.bookId);
	}

	@Override
	public int hashCode() {
		int result = userId.hashCode();
		result = 31 * result + bookId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "UserBookId [userId=" + userId + ", bookId=" + bookId + "]";
	}
	
}
