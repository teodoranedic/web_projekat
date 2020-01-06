package main;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.CategoriesIO;
import io.DiscIO;
import io.OrganizationsIO;
import io.UserIO;
import io.VMIO;
import model.CategoryVM;
import model.Disc;
import model.Organization;
import model.User;
import model.VM;
import spark.Session;
import utility.Logging;

public class CloudServiceProvider {
	
	private static Gson g = new Gson();
	
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Organization> organizations = new ArrayList<Organization>();
	public static ArrayList<VM> vms = new ArrayList<VM>();
	public static ArrayList<Disc> discs = new ArrayList<Disc>();
	public static ArrayList<CategoryVM> categories = new ArrayList<CategoryVM>(); 

	public static void main(String[] args) throws IOException {
		port(8080);		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		// Ucitavanje podataka iz fajlova
	
		 CategoriesIO.fromFile();
		 DiscIO.fromFile();
		 try {
			VMIO.fromFile();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // dodamo odgovarajucu kategoriju i diskove, a diskovima odg virt.masinu
		 UserIO.fromFile(); 
		 OrganizationsIO.fromFile(); 

		
		
		post("/rest/login", (req, res) -> {
			res.type("application/json");
			String text = req.body(); // ovde imamo {"email":"nesto","password":"nesto"}
			JsonObject jsonObject = new JsonObject();
			User u = Logging.checkLogIn(text);
			if(u != null)
			{
				Session ss = req.session(true);
				User user = ss.attribute("user");
				if (user == null) {
					user = u;
					ss.attribute("user", user);
				}
				res.status(200);
				return jsonObject;
			}
			res.status(400);
			return jsonObject;
		});

		get("/rest/logout", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			
			if (user != null) {
				ss.attribute("user", null);
				ss.invalidate();
			}
			return true;
		});
		
		get("/rest/testlogin", (req, res) -> {
			Session ss = req.session(true);
			User user = ss.attribute("user");
			
			if (user == null) {
				res.status(400);
				return "No user logged in.";  
			} else {
				res.status(200);
				return "User " + user + " logged in.";
			}
		});
		
		get("/rest/getRole", (req, res) ->{
			Session ss = req.session(true);
			User user = ss.attribute("user");

			if (user == null) {
				res.status(400);
				return "";  
			} else {
				res.status(200);
				return user.getRole().toString();
			}
		});
		
		get("/rest/getAllVM", (req, res) -> {
			res.type("application/json");			
			return g.toJson(vms);
		});
		
		get("/rest/getAllOrg", (req, res) -> {
			res.type("application/json");
			return g.toJson(organizations);
		});
		
		get("/rest/getOrg/:name", (req, res) -> {
			res.type("application/json");
			String name = req.params("name");
			for (Organization o : organizations) {
				if (o.getName().equals(name))
					return g.toJson(o);
			}
			return "";
		});
		
		// treba proveriti
		delete("/rest/deleteOrg/:name", (req, res) ->{
			res.type("application/json");
			Organization data = g.fromJson(req.body(), Organization.class);
			String name = req.params("name");
			for(Organization o : organizations)
			{
				if(o.getName().equals(name)) {
					organizations.remove(o);
					break;
				}
			}
			OrganizationsIO.toFile(organizations);
			return "OK";
		});
		
		post("/rest/addOrg", (req, res) -> {
			res.type("application/json");
			Organization data = g.fromJson(req.body(), Organization.class);

			// proveriti da li je ime jedinstveno
			if(utility.Check.OrganizationNameUnique(data.getName()))
			{
				organizations.add(data);
				OrganizationsIO.toFile(organizations);
				res.status(200);
				return "OK";
			}			
			res.status(400);
			return "OK";
			
		});
		
		put("rest/editOrg/:name", (req, res) ->{
			res.type("application/json");
			Organization data = g.fromJson(req.body(), Organization.class);
			String name = req.params("name");
			for(Organization o : organizations)
			{
				if(o.getName().equals(name)) {
					if(!name.equals(data.getName()) && !utility.Check.OrganizationNameUnique(data.getName())) {
						res.status(400);
						return "New name is not unique.";
					}
					o.setName(data.getName());
					o.setDescription(data.getDescription());
					o.setImagePath(data.getImagePath());					
					break;
				}
			}
			//potrebno upisati i korisnike ponovo jer imaju referencu na organizaciju
			OrganizationsIO.toFile(organizations);
			UserIO.toFile(users);
			res.status(200);
			return "OK";

		});

	}

}
