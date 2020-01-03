package main;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gson.Gson;

import io.CategoriesIO;
import io.DiscIO;
import io.OrganizationsIO;
import io.UserIO;
import io.VMIO;
import model.CategoryVM;
import model.Disc;
import model.DiscType;
import model.Organization;
import model.Resource;
import model.Role;
import model.User;
import model.VM;

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
		/*
		CategoryVM c1 = new CategoryVM("c1",4,500,2);
		CategoryVM c2 = new CategoryVM("c2",8,1000,2);
		categories.add(c1);
		categories.add(c2);
		
		Disc d1 = new Disc("d1",DiscType.HDD,1000, null);
		Disc d2 = new Disc("d2",DiscType.SSD,500, null);
		discs.add(d1);
		discs.add(d1);
		
		VM vm1 = new VM("vm1",c1,discs);
		d1.setVirtualMachine(vm1);
		d2.setVirtualMachine(vm1);
		vms.add(vm1);
		
		User u1 = new User("super@admin.com","Admin","Admin","superadmin", null, Role.SUPERADMIN);
		User u2 = new User("user1@mail.com","Marko","Markovic","user1",null, Role.USER);
		users.add(u1);
		users.add(u2);
		
		ArrayList<Resource> res1 = new ArrayList<Resource>();
		res1.add(d1);
		res1.add(vm1);		
		Organization o1 = new Organization("org1","lalala","path",users,res1);
		organizations.add(o1);
		u1.setOrganization(o1);
		u2.setOrganization(o1);
		
		// upis u fajl
		CategoriesIO.toFile(categories);
		DiscIO.toFile(discs);
		VMIO.toFile(vms);
		UserIO.toFile(users);
		OrganizationsIO.toFile(organizations);
		*/
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

		
		CategoriesIO.fromFile();
		System.out.println(categories.size());
		
		post("/rest/login", (req, res) -> {
			res.type("application/json");
			String text = req.body(); // ovde imamo {"email":"nesto","password":"nesto"}
			System.out.println(text);
			return " okyy";
		});

	}

}
