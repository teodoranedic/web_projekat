package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.User;

public class UserIO {

	static Gson gson = new Gson();
	
	public static ArrayList<User> fromFile() throws FileNotFoundException {
		String path = "././data/users.json";
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
	    Gson gson = new Gson();
	    User[] json = gson.fromJson(bufferedReader, User[].class);
	    ArrayList<User> users = new ArrayList<User>();
	    Collections.addAll(users, json);
	    return users;
		
	}
	
	public static void toFile(ArrayList<User> list) throws JsonIOException, IOException {	
		FileWriter fw = new FileWriter("././data/users.json");					
		gson.toJson(list, fw);
		fw.close();
		
	}
	
}
