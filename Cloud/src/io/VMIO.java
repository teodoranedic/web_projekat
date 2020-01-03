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

import model.VM;

public class VMIO {
static Gson gson = new Gson();
	
	public static ArrayList<VM> fromFile() throws FileNotFoundException {
		String path = "././data/vm.json";
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
	    Gson gson = new Gson();
	    VM[] json = gson.fromJson(bufferedReader, VM[].class);
	    ArrayList<VM> vms = new ArrayList<VM>();
	    Collections.addAll(vms, json);
	    return vms;
		
	}
	
	public static void toFile(ArrayList<VM> list) throws JsonIOException, IOException {	
		FileWriter fw = new FileWriter("././data/vm.json");					
		gson.toJson(list, fw);
		fw.close();
		
	}
}
