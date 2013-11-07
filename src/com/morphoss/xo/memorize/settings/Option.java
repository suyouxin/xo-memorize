package com.morphoss.xo.memorize.settings;

import android.graphics.Bitmap;

public class Option implements Comparable<Option>{
		    private String name;
		    private String data;
		    private String path;
		    private Bitmap icon;
		     
		    public Option(String n,String d,String p)
		    {
		        name = n;
		        data = d;
		        path = p;
		    }
		    public String getName()
		    {
		        return name;
		    }
		    public String getData()
		    {
		        return data;
		    }
		    public String getPath()
		    {
		        return path;
		    }
		    public Bitmap getIcon(){
		    	return icon;
		    }
		    @Override
		    public int compareTo(Option o) {
		        if(this.name != null)
		            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
		        else
		            throw new IllegalArgumentException();
		    }
		}
