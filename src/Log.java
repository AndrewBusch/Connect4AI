import java.io.*;


public class Log {
	
	String fileName;
	boolean logging = true;
	
	public Log(String on){
		if(on.equals("1")) {
			logging = false;
		}
		fileName = "Log.txt";
	}
	
    public void writeLog( String entry) {
    	if(logging) { 	
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
	        }
    	}
    }

	public void writeLogNoLine(String entry) {
		if(logging) {
	        try {
				FileWriter fileWriter = new FileWriter(fileName, true);
	        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	            bufferedWriter.write(entry);
	    		bufferedWriter.close();
	        } catch(IOException ex) {
	            System.out.println(
	                "Error writing to file '"
	                + fileName + "'");
	        }
		}
	}
}
