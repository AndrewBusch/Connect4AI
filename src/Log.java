import java.io.*;


public class Log {
	
	String fileName;
	boolean logging = true;
	
	public Log(String on){
		if(!on.equals("0")) {
			logging = false;
		}
		fileName = "Log.txt";
	}
	
    /**
     * Write a string to log.txt
     * @param entry The String to be written to the log
     */
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

	/**
	 * Writes to log.txt without a new line at the end.
	 * @param entry The String to be written to the log without a new line
	 */
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
