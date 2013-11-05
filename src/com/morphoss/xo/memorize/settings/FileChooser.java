package com.morphoss.xo.memorize.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.morphoss.xo.memorize.R;

public class FileChooser extends ListActivity {
	
		    public static String soundPath;
		    private File currentDir;
		    private FileArrayAdapter adapter;
		    private String pathDirectory;
		    
			@Override
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        Bundle extras = getIntent().getExtras();
		        pathDirectory = extras.getString("pathDirectory");
		        currentDir = new File(pathDirectory);
		        fill(currentDir);
		    }
		    private void fill(File f)
		    {
		        File[]dirs = f.listFiles();
		         this.setTitle("Current Dir: "+f.getName());
		         List<Option>dir = new ArrayList<Option>();
		         List<Option>fls = new ArrayList<Option>();
		         try{
		             for(File ff: dirs)
		             {
		                if(ff.isDirectory())
		                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
		                else
		                {
		                    fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
		                }
		             }
		         }catch(Exception e)
		         {
		              
		         }
		         Collections.sort(dir);
		         Collections.sort(fls);
		         dir.addAll(fls);
		         adapter = new FileArrayAdapter(FileChooser.this,R.layout.file_view,dir);
		         this.setListAdapter(adapter);
		    }
		    @Override
		    protected void onListItemClick(ListView l, View v, int position, long id) {
		        // TODO Auto-generated method stub
		        super.onListItemClick(l, v, position, id);
		        Option o = adapter.getItem(position);
		        if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
		                currentDir = new File(o.getPath());
		                fill(currentDir);
		        }
		        else
		        {
		            onFileClick(o);
		        }
		    }
		    private void onFileClick(Option o)
		    {
		    	soundPath = o.getPath();
		    	Intent data = new Intent();
		    	  data.putExtra("returnKey1", soundPath);
		    	  // Activity finished ok, return the data
		    	  setResult(RESULT_OK, data);
		    	  super.finish();
		    }
		}