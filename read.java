package engineering;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.util.jar.*;

public class read {
	
private static Scanner scan;
private static ArrayList <String> list;
private static boolean delete;
public static void main (String[] args) throws IOException
{
	int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete the Audio Cache after merging?", "Notice", JOptionPane.YES_NO_OPTION);
    if (reply == JOptionPane.YES_OPTION) 
    {
    	delete = true;
    }
    else 
    {
       delete = false;
    }
	
	list=new ArrayList<String>();
	//autoplaylist
	String fileName = JOptionPane.showInputDialog("Type in the file path of the audio Cache (do \\\\) ex: C:\\\\Users\\\\Tim\\\\Downloads\\\\MusicBot\\\\audio_cache"); 
	//JOptionPane.showMessageDialog(null, fileName);
	final File folder = new File(fileName);
	//C:\\Users\\Tim\\WorkSpace\\Tamu\\audio_cache
	listFilesForFolder(folder);
	
	scan= new Scanner(new File("autoplaylist.txt"));
	while (scan.hasNext())
	{
		list.add(scan.nextLine());
		//System.out.println(list.get(list.size()-1));
	}
	
		

	for(int x=0; x<list.size();x++)
	{
		String hold = list.get(x);
		if (hold.substring(0,32).equals("https://www.youtube.com/watch?v="))
		{
		}
		else if(hold.substring(hold.length()-3).equals("m4a"))
		{
		hold="https://www.youtube.com/watch?v=" + hold.substring(8,hold.length()-4);
		}
		else if(hold.substring(hold.length()-4).equals("webm"))
		{
		hold="https://www.youtube.com/watch?v=" + hold.substring(8, hold.length()-5);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Error Contact Tim: Error 1" + hold);
		}
		list.set(x,hold);  
	}
	
	removeDuplicates(list);
	

}





public static void removeDuplicates(ArrayList<String> list) throws IOException
{
	int oldSize=list.size();
	for(int x=0;x<list.size();x++)
	{
		for(int y=x+1;y<list.size();y++)
		{
			//System.out.println(list.get(x)+ " " + list.get(y));
				if((list.get(x).substring(32, 43)).equals(list.get(y).substring(32, 43)))
				{
					//System.out.println(list.get(x));
					//System.out.println(list.get(y));
					list.remove(y);
					//System.out.println("");
				}
			}	
	}
	int newSize=list.size();
	//printList(list);
	//System.out.println(list.size());
	//printList(list);
	Path file = Paths.get("newPlayList.txt");
	Files.write(file, list, Charset.forName("UTF-8"));

	JOptionPane.showMessageDialog(null, "Number of songs before " + oldSize + "\n Number of songs now " + newSize);

}

public static void listFilesForFolder(final File folder) 
{
    if(folder.isDirectory())
    {
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	            JOptionPane.showMessageDialog(null, "Error Contact Tim: Error 3");
	        } 
	        else 
	        {
	            list.add(fileEntry.getName());
	            if(delete)
	            fileEntry.delete();
	        }
	    }
    }
    else
    	JOptionPane.showMessageDialog(null, "Error Invalid path");
}

public static void printList(ArrayList<String> n)
{
	for(int x=0; x<n.size();x++)
	{
		System.out.println(n.get(x));
	}
}


}