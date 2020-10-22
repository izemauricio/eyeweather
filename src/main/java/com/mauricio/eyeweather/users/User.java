package com.mauricio.eyeweather.users;

public class User {
	private String id;
	private String email;
	private String first;
	private String last;
	private String username;
	private String password;
	private int role;
	private int enabled;

	private User() {
	}

	private User(Builder b) {
		this.id = b.id;
		this.email = b.email;
		this.first = b.first;
		this.last = b.last;
		this.username = b.username;
		this.password = b.password;
		this.role = b.role;
		this.enabled = b.enabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public String getFisrt() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public static class Builder {
		private String id;
		private String email;
		private String first;
		private String last;
		private String username;
		private String password;
		private int role;
		private int enabled;

		public User build() {
			return new User(this);
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder first(String first) {
			this.first = first;
			return this;
		}

		public Builder last(String last) {
			this.last = last;
			return this;
		}

		public Builder username(String name) {
			this.username = name;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder role(int role) {
			this.role = role;
			return this;
		}

		public Builder enabled(int enabled) {
			this.enabled = enabled;
			return this;
		}
	}
}
