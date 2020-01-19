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
import model.Resource;
import model.Role;
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
				return user.getEmail();
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
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if(user.getRole() == Role.SUPERADMIN)
				return g.toJson(vms);
			else {
				ArrayList<VM> adminVMS = new ArrayList<VM>();
				for(Resource v: user.getOrganization().getResources()) {
					if(v instanceof VM) {
						adminVMS.add((VM)v);
					}
				}
				return g.toJson(adminVMS);
			}
			
		});
		
		get("/rest/getAllOrg", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if(user.getRole() == Role.SUPERADMIN)
				return g.toJson(organizations);
			else {
				ArrayList<Organization> adminOrg = new ArrayList<Organization>();
				adminOrg.add(user.getOrganization());
				return g.toJson(adminOrg);
			}
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
		
		get("rest/getAccount", (req,res) -> {
			Session ss = req.session(true);
			User user = ss.attribute("user");

			if (user == null) {
				res.status(400);
				return "";  
			} else {
				res.status(200);
				return g.toJson(user);
			}
			
		});
		
		delete("rest/deleteAcc/:id", (req,res) -> {
			res.type("application/json");
			User data = g.fromJson(req.body(), User.class);
			String email = req.params("id");
			for(User u : users)
			{
				if(u.getEmail().equals(email)) {
					users.remove(u);
					for(Organization o : organizations)
					{
						if(o.getUsers().contains(u))
							o.getUsers().remove(u);
					}
					req.session(true).invalidate();
					break;
				}
			}
			UserIO.toFile(users);
			OrganizationsIO.toFile(organizations);
			return "OK";
			
		});
		
		put("rest/editAcc/:id", (req,res) -> {
			res.type("application/json");
			User data = g.fromJson(req.body(), User.class);
			String email = req.params("id");
			for(User u : users)
			{
				if(u.getEmail().equals(email)) {
					if(!email.equals(data.getEmail()) && !utility.Check.UserUnique(data.getEmail())) {
						res.status(400);
						return "New email already has an account.";
					}
					u.setEmail(data.getEmail());
					u.setName(data.getName());
					u.setLastName(data.getLastName());
					u.setPassword(data.getPassword());
					
					break;
				}
			}
			UserIO.toFile(users);
			OrganizationsIO.toFile(organizations);
			res.status(200);
			return "OK";
			
		});
		
		get("/rest/getAllCat", (req, res) -> {
			res.type("application/json");			
			return g.toJson(categories);
		});
		
		get("/rest/getCat/:name", (req, res) -> {
			res.type("application/json");
			String name = req.params("name");
			for (CategoryVM c : categories) {
				if (c.getName().equals(name))
					return g.toJson(c);
			}
			return "";
		});
		
		delete("/rest/deleteCat/:name", (req, res) ->{
			res.type("application/json");
			CategoryVM data = g.fromJson(req.body(), CategoryVM.class);
			String name = req.params("name");
			for(CategoryVM c : categories)
			{
				if(c.getName().equals(name)) {
					// ako postoji vm zakacena, vraca se greska
					for (VM vm : vms) {
						if(vm.getCategory().equals(c)) {
							res.status(400);
							return "ERROR: At least one virtual machine has this category.";
						}
					}
					categories.remove(c);
					break;
				}
			}
			CategoriesIO.toFile(categories);
			res.status(200);
			return "OK";
		});
		
		post("/rest/addCat", (req, res) -> {
			res.type("application/json");
			CategoryVM data = g.fromJson(req.body(), CategoryVM.class);

			// proveriti da li je ime jedinstveno
			if(utility.Check.CategoryNameUnique(data.getName()))
			{
				categories.add(data);
				CategoriesIO.toFile(categories);
				res.status(200);
				return "OK";
			}			
			res.status(400);
			return "OK";
			
		});
		
		put("rest/editCat/:name", (req, res) ->{
			res.type("application/json");
			CategoryVM data = g.fromJson(req.body(), CategoryVM.class);
			String name = req.params("name");
			for(CategoryVM c : categories)
			{
				if(c.getName().equals(name)) {
					if(!name.equals(data.getName()) && !utility.Check.CategoryNameUnique(data.getName())) {
						res.status(400);
						return "New name is not unique.";
					}
					c.setName(data.getName());
					c.setCoreNumber(data.getCoreNumber());
					c.setRAM(data.getRAM());
					c.setGPUcore(data.getGPUcore());
					break;
				}
			}
			CategoriesIO.toFile(categories);
			VMIO.toFile(vms);
			res.status(200);
			return "OK";

		});
		
		get("rest/getAllUser", (req, res) ->{
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if(user.getRole() == Role.SUPERADMIN)
				return g.toJson(users);
			else {
				ArrayList<User> adminUsers = new ArrayList<User>();
				for(User u : users)
					if(u.getOrganization().getName().equals(user.getOrganization().getName()))
					{
						adminUsers.add(u);
					}
				return g.toJson(adminUsers);
			}
		});
		
		get("rest/getUser/:email", (req, res)-> {
			res.type("application/json");
			String email = req.params("email");
			for (User u : users) {
				if (u.getEmail().equals(email))
					return g.toJson(u);
			}
			return "";
		});
		
		put("rest/editUser/:email", (req, res)->{
			res.type("application/json");
			User data =  g.fromJson(req.body(), User.class);
			String email = req.params("email");
			for(User u : users) {
				if(u.getEmail().equals(email))
				{
					u.setName(data.getName());
					u.setLastName(data.getLastName());
					u.setPassword(data.getPassword());
					u.setRole(data.getRole());
					break;
				}
			}
			UserIO.toFile(users);
			return "";
		});
		
		delete("rest/deleteUser/:email", (req, res) ->{
			res.type("application/json");
			User data = g.fromJson(req.body(), User.class);
			String email = req.params("email");
			for(User u : users)
			{
				if(u.getEmail().equals(email)) {
					users.remove(u);
					for(Organization o : organizations)
					{
						if(o.getUsers().contains(u))
							o.getUsers().remove(u);
					}
					break;
				}
			}
			UserIO.toFile(users);
			OrganizationsIO.toFile(organizations);
			return "OK";
		});
		
		post("rest/addUser", (req, res) ->{
			res.type("application/json");
			User data = g.fromJson(req.body(), User.class);
			Session ss = req.session(true);
			User currentUser = ss.attribute("user");
			if(utility.Check.UserUnique(data.getEmail()))
			{
				if(currentUser.getRole() == Role.ADMIN)
				{
					data.setOrganization(currentUser.getOrganization());
				
				}
					for(Organization o :organizations)
					{
						if(o.getName().equals(data.getOrganization().getName()))
						{
							data.setOrganization(o);
							users.add(data);
							o.addUser(data);
							res.status(200);
							UserIO.toFile(users);
							OrganizationsIO.toFile(organizations);
							return "OK";
						}
					}
				
				
			}
			res.status(400);
			return "NIJE OK";
		});
		
		get("/rest/getAllDiscs", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if(user.getRole() == Role.SUPERADMIN)
				return g.toJson(discs);
			else {
				ArrayList<Disc> adminDiscs = new ArrayList<Disc>();
				for(Resource r: user.getOrganization().getResources()) {
					if(r instanceof Disc) {
						adminDiscs.add((Disc)r);
					}
				}
				return g.toJson(adminDiscs);
			}
		});
		
		get("/rest/getDisc/:name", (req, res) -> {
			res.type("application/json");
			String name = req.params("name");
			for (Disc d : discs) {
				if (d.getName().equals(name))
					return g.toJson(d);
			}
			return "";
		});
		
		put("rest/editDisc/:name", (req, res)->{
			res.type("application/json");
			Disc data =  g.fromJson(req.body(), Disc.class);
			String name = req.params("name");
			for(Disc d : discs) {
				if(d.getName().equals(name))
				{
					d.setName(data.getName());
					d.setCapacity(data.getCapacity());
					d.setType(data.getType());
					if(!d.getVirtualMachine().getName().equals(data.getVirtualMachine().getName())) {
						d.getVirtualMachine().getDiscs().remove(d);
						for(VM v: vms) {
							if(v.getName().equals(data.getVirtualMachine().getName())) {
								v.getDiscs().add(d);
								d.setVirtualMachine(v);
								break;
							}
						}	
					}
					DiscIO.toFile(discs);
					VMIO.toFile(vms);
					break;
				}
			}
			return "";
		});


	}

}
