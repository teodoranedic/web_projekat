package model;

import java.util.ArrayList;

public class VM extends Resource{
	 // jedinstveno ime
	private CategoryVM category;
	private transient ArrayList<Disc> discs = new ArrayList<Disc>();
	private ArrayList<Activity> activities = new ArrayList<Activity>();	
	
	public VM() {
		super("");
	}
	
	public VM(String name, CategoryVM category, ArrayList<Disc> discs) {
		super(name);
		//this.name = name;
		this.category = category;
		this.discs = discs;
	}
	
	public VM(String name, CategoryVM category, ArrayList<Disc> discs, ArrayList<Activity> activities) {
		super(name);
		//this.name = name;
		this.category = category;
		this.discs = discs;
		this.activities = activities;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CategoryVM getCategory() {
		return category;
	}
	public void setCategory(CategoryVM category) {
		this.category = category;
	}
	public ArrayList<Disc> getDiscs() {
		return discs;
	}
	public void setDiscs(ArrayList<Disc> discs) {
		this.discs = discs;
	}
	public void addDisc(Disc disc){
		this.discs.add(disc);
	}
	public void removeDisc(String name) {
		for(Disc d : this.discs) {
			if (d.getName().equals(name))
			{
				this.discs.remove(d);
				break;
			}
		}
	}
	public ArrayList<Activity> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}
	public void addActivity(Activity activity){
		this.activities.add(activity);
	}
	
	
}
