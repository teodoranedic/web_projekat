package utility;

import main.CloudServiceProvider;
import model.Organization;

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
}
