import java.io.*;


public class Log {
	
	String fileName;
	
	public Log(){
		fileName = "Log.txt";
	}
	
    public void writeLog( String entry) {

        // The name of the file to open.
    	
        try {
			FileWriter fileWriter = new FileWriter(fileName, true);
        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(entry);
            bufferedWriter.newLine();
    		bufferedWriter.close();
        } catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
    
/*    public void closeLog(){
    	try {
    		bufferedWriter.close();
    	} catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }*/
    
}
