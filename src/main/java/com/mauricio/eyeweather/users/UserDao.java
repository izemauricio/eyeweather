package com.mauricio.eyeweather.users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private String path = System.getProperty("java.io.tmpdir") + "users.db";
    private List<User> users = new ArrayList<User>();

    public void add(User user) {
	users.add(user);
    }
    public List<User> getList() {
	return users;
    }
    private void generateTxtFile() {
	System.out.println("path=" + path);
	BufferedWriter w = null;
	try {
	    w = new BufferedWriter(new FileWriter(path));
	    w.write(UUID.randomUUID().toString() + ",m@m.com,mauricio,ize,mau,123,1,1\n");
	    w.write(UUID.randomUUID().toString() + ",m@m.com,mickey,mouse,mickey40,123,1,1\n");
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		w.close();
	    } catch (Exception e) {
	    }
	}
    }
    public void load() {
	generateTxtFile();
	Scanner scanner = null;
	Scanner split = null;
	try {
	    scanner = new Scanner(new File(path));
	    while (scanner.hasNextLine()) {
		String line = scanner.nextLine();
		split = new Scanner(line);
		split.useDelimiter(",");
		while (split.hasNext()) {
		    String id = split.next();
		    String email = split.next();
		    String first = split.next();
		    String last = split.next();
		    String username = split.next();
		    String password = split.next();
		    String role = split.next();
		    String enabled = split.next();
		    User user = new User.Builder().id(id).email(email).first(first).last(last).username(username).password(password).role(Integer.parseInt(role)).enabled(Integer.parseInt(enabled)).build();
		    add(user);
		}
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} finally {
	    try {
		split.close();
		scanner.close();
	    } catch (Exception e) {
	    }
	}
    }
}
