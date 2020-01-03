package utility;

import main.CloudServiceProvider;
import model.User;

public class Logging {
	
	public static boolean checkLogIn(String data)
	{
		// {"email" : "mail", "password" : "pass"}
		String[] tokens = data.split(",");
		String email = tokens[0].split(":")[1].trim();
		String password = tokens[1].split(":")[1].trim();
		
		password = password.substring(1, password.length()-2);
		email = email.substring(1, email.length()-1);
		for(User u : CloudServiceProvider.users)
		{
			if (u.getEmail().equals(email) && u.getPassword().equals(password))
			{
				return true;
			}	
		}
		return false;
	}

}
