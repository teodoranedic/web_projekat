package main;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.CategoryVM;
import model.Disc;
import model.Organization;
import model.User;
import model.VM;

public class CloudServiceProvider {
	
	private static Gson g = new Gson();
	
	static ArrayList<User> users = new ArrayList<User>();
	static ArrayList<Organization> organizations = new ArrayList<Organization>();
	static ArrayList<VM> vms = new ArrayList<VM>();
	static ArrayList<Disc> discs = new ArrayList<Disc>();
	static ArrayList<CategoryVM> categories = new ArrayList<CategoryVM>(); 

	public static void main(String[] args) throws IOException {
		port(8080);		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		post("/rest/login", (req, res) -> {
			res.type("application/json");
			String text = req.body(); // ovde imamo {"email":"nesto","password":"nesto"}
			System.out.println(text);
			return " okyy";
		});

	}

}
