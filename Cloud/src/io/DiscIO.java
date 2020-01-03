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

import model.Disc;

public class DiscIO {
static Gson gson = new Gson();
	
	public static ArrayList<Disc> fromFile() throws FileNotFoundException {
		String path = "././data/discs.json";
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
	    Gson gson = new Gson();
	    Disc[] json = gson.fromJson(bufferedReader, Disc[].class);
	    ArrayList<Disc> discs = new ArrayList<Disc>();
	    Collections.addAll(discs, json);
	    return discs;
		
	}
	
	public static void toFile(ArrayList<Disc> list) throws JsonIOException, IOException {	
		FileWriter fw = new FileWriter("././data/discs.json");					
		gson.toJson(list, fw);
		fw.close();
		
	}
}
