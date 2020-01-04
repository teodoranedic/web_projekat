package main;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
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
		
		get("/rest/getAllVM", (req, res) -> {
			res.type("application/json");			
			return g.toJson(vms);
		});
		

	}

}
