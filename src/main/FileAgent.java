package main;

import java.awt.EventQueue;

import javax.swing.JFileChooser;

public class FileAgent{
	String path = null;
	String directoy;
	FileAgent(String default_repo){
		directoy = default_repo;
	}
	public String getFilePath ()throws Exception {
        EventQueue.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFileChooser fc = new JFileChooser(directoy);
                int result = fc.showDialog(null, "pick an image");
                if(result == JFileChooser.APPROVE_OPTION){
                	path = fc.getSelectedFile().getPath();
                }
            }
        }); 
        return path;
    }
   }
