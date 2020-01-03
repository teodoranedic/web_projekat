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

import model.Organization;


public class OrganizationsIO {
static Gson gson = new Gson();
	
	public static ArrayList<Organization> fromFile() throws FileNotFoundException {
		String path = "././data/organizations.json";
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
	    Gson gson = new Gson();
	    Organization[] json = gson.fromJson(bufferedReader, Organization[].class);
	    ArrayList<Organization> org = new ArrayList<Organization>();
	    Collections.addAll(org, json);
	    return org;
		
	}
	
	public static void toFile(ArrayList<Organization> list) throws JsonIOException, IOException {	
		FileWriter fw = new FileWriter("././data/organizations.json");					
		gson.toJson(list, fw);
		fw.close();
		
	}
}
