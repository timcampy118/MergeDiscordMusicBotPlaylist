import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import javax.swing.JOptionPane;

public class read {
	
private static Scanner scan;
private static ArrayList <String> list;
private static boolean delete;
private static int original;
private static String hold;

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
	//C:\\Users\\Tim\\WorkSpace\\Tamu\\audio_cache
	list=new ArrayList<String>();
	String fileName = JOptionPane.showInputDialog("Type in the file path of the audio Cache (do \\\\) ex: C:\\\\Users\\\\Tim\\\\Downloads\\\\MusicBot\\\\audio_cache"); 
	final File folder = new File(fileName);
	listFilesForFolder(folder);
	
	try
    	{
      		scan= new Scanner(new File("autoplaylist.txt"));
    	}
    catch(FileNotFoundException e)
    	{
      		JOptionPane.showMessageDialog(null, "The Playlist File is not found, Please put the program in the same folder as the playlist" + e);
		System.exit(1);
    	} 
	
	original=0;
	while (scan.hasNext())
	{
		list.add(scan.nextLine());
		original++;
	}
	
	try
	{

		for(int x=0; x<list.size();x++)
		{
			hold = list.get(x);
			if (hold.length()<32)
			{
				hold="";
			}
			else if (hold.substring(0,32).equals("https://www.youtube.com/watch?v="))
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
				hold="";
			}
			if(hold.length()>1)
				list.set(x,hold);
			else
			{
				list.remove(x);
				x--;
			}
			
		}
	}
	
	catch(IndexOutOfBoundsException r)
	{
		JOptionPane.showMessageDialog(null, "Index out of bounds please report to Tim" + hold + r);
		System.exit(1);
		
	}
	
	
	removeDuplicates(list);
	
	
}





public static void removeDuplicates(ArrayList<String> list) throws IOException
{
	int added=list.size()-original;
	for(int x=0;x<list.size();x++)
	{
		for(int y=x+1;y<list.size();y++)
		{
			if((list.get(x).substring(32, 43)).equals(list.get(y).substring(32, 43)))
			{
				list.remove(y);
				y--;
			}
		}	
	}
	Path file = Paths.get("autoplaylist.txt");
	Files.write(file, list, Charset.forName("UTF-8"));
	JOptionPane.showMessageDialog(null, "Merging Successful");
	JOptionPane.showMessageDialog(null, "Number of songs original " + original + "\n Number of songs added " + added + "\n Number of unique songs" + list.size());

}

public static void listFilesForFolder(final File folder) 
{
	System.out.println("1");
    if(folder.isDirectory())
    {

    	System.out.println("2");
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) 
	        {

	        	System.out.println("3");
	            listFilesForFolder(fileEntry);
	            JOptionPane.showMessageDialog(null, "Error, Incorrect Path");
	        } 
	        else 
	        {

	        	System.out.println("4");
	            list.add(fileEntry.getName());
	            if(delete)
	            fileEntry.delete();
	        }
	    }
    }
    else
    {
    	JOptionPane.showMessageDialog(null, "Error Invalid path");
    	System.out.println("5");
    	System.exit(1);
    }
}


}
