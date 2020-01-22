package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import main.CloudServiceProvider;
import model.CategoryVM;
import model.DateSearch;
import model.Disc;
import model.DiscType;
import model.Organization;
import model.User;
import model.VM;

public class Check {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static boolean OrganizationNameUnique(String name) {
		boolean unique = true;
		for (Organization o : CloudServiceProvider.organizations) {
			if (o.getName().equals(name))
				return false;
		}
		return unique;
	}

	public static boolean UserUnique(String email) {
		boolean unique = true;
		for (User u : CloudServiceProvider.users) {
			if (u.getEmail().equals(email))
				return false;
		}
		return unique;
	}

	public static boolean CategoryNameUnique(String name) {
		boolean unique = true;
		for (CategoryVM o : CloudServiceProvider.categories) {
			if (o.getName().equals(name))
				return false;
		}
		return unique;
	}

	public static boolean DiscNameUnique(String name) {
		boolean unique = true;
		for (Disc d : CloudServiceProvider.discs) {
			if (d.getName().equals(name))
				return false;
		}
		return unique;
	}

	public static boolean VMNameUnique(String name) {
		boolean unique = true;
		for (VM v : CloudServiceProvider.vms) {
			if (v.getName().equals(name))
				return false;
		}
		return unique;
	}

	public static double getDiscPrice(Disc d) {
		double retVal = 0;
		if (d.getType() == DiscType.HDD) {
			retVal = 0.1 * d.getCapacity();
		} else {
			retVal = 0.3 * d.getCapacity();
		}
		return retVal / 30;
	}

	public static double getVMPrice(VM v) {
		double retVal = 25 * v.getCategory().getCoreNumber() + 15 * v.getCategory().getRAM()
				+ v.getCategory().getGPUcore();

		return retVal / 30;
	}

	public static DateSearch getDate(String data) throws ParseException {
		// {"start" : "", "end" : "pass"}
		DateSearch retVal = new DateSearch();
		String[] tokens = data.split(",");
		String startDate = tokens[0].split(":")[1].trim();
		String endDate = tokens[1].split(":")[1].trim();
		endDate = endDate.substring(1, endDate.length() - 2);
		startDate = startDate.substring(1, startDate.length() - 1);
		
		retVal.setStart(sdf.parse(startDate));
		retVal.setEnd(sdf.parse(endDate));
		return retVal;
		
		
	}
}
