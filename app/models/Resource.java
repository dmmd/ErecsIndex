package models;

import java.util.Set;
import java.util.TreeSet;

public class Resource{
	public String colId;
	public String colName;
	public Set<String> archivalObjects = new TreeSet<>();
	public Set<String> mediaObjects = new TreeSet<>();
	public Set<String> fileFormats = new TreeSet<>();
}