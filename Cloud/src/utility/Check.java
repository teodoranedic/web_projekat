package utility;

import main.CloudServiceProvider;
import model.CategoryVM;
import model.Disc;
import model.Organization;
import model.User;
import model.VM;

public class Check {
	
	public static boolean OrganizationNameUnique(String name) {
		boolean unique = true;
		for(Organization o : CloudServiceProvider.organizations)
		{
			if(o.getName().equals(name))
				return false;
		}
		return unique;
	}
	
	public static boolean UserUnique(String email) {
		boolean unique = true;
		for(User u : CloudServiceProvider.users)
		{
			if(u.getEmail().equals(email))
				return false;
		}
		return unique;
	}

	public static boolean CategoryNameUnique(String name) {
		boolean unique = true;
		for(CategoryVM o : CloudServiceProvider.categories)
		{
			if(o.getName().equals(name))
				return false;
		}
		return unique;
	}
	
	public static boolean DiscNameUnique(String name) {
		boolean unique = true;
		for(Disc d : CloudServiceProvider.discs)
		{
			if(d.getName().equals(name))
				return false;
		}
		return unique;
	}
	
	public static boolean VMNameUnique(String name) {
		boolean unique = true;
		for(VM v : CloudServiceProvider.vms)
		{
			if(v.getName().equals(name))
				return false;
		}
		return unique;
	}
}
