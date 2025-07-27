package services;

public interface ISearchable<T> {
	T searchById(Long id);
	T searchByName(String name);
	

}
