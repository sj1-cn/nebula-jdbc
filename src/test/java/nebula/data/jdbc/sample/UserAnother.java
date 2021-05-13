package nebula.data.jdbc.sample;

import nebula.data.jdbc.WithIdKey;

public class UserAnother implements WithIdKey {
	private long id;
	private String name;
	private String description;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserAnother(long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
