package services;

import java.util.List;

import Model.User;

public class UserSearchService implements ISearchable<User> {

	private List<User> usersList;

	public UserSearchService(List<User> usersList) {
		this.usersList = usersList;
	}

	@Override
	public User searchById(Long id) {
		return usersList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
		
		
	}

	@Override
	public User searchByName(String name) {
		return usersList.stream().filter(u -> u.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

}
