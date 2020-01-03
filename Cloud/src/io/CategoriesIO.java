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

import model.CategoryVM;

public class CategoriesIO {
static Gson gson = new Gson();
	
	public static ArrayList<CategoryVM> fromFile() throws FileNotFoundException {
		String path = "././data/categories.json";
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
	    Gson gson = new Gson();
	    CategoryVM[] json = gson.fromJson(bufferedReader, CategoryVM[].class);
	    ArrayList<CategoryVM> cat = new ArrayList<CategoryVM>();
	    Collections.addAll(cat, json);
	    return cat;
		
	}
	
	public static void toFile(ArrayList<CategoryVM> list) throws JsonIOException, IOException {	
		FileWriter fw = new FileWriter("././data/categories.json");					
		gson.toJson(list, fw);
		fw.close();
		
	}
}
