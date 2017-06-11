package vm.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import orf.Entry;
import security.ExceptionLogger;

public class FastaReader {
	
	private ArrayList<Entry> list;
	
	public FastaReader(String input){
		readFile(input);
	}
	
	
	private void readFile(String inputFilePath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFilePath));

			StringBuilder sb = new StringBuilder();
			String line;
			while (!(line = br.readLine()).startsWith(">"))
				line = br.readLine();
			sb.append(line + "\n");

			while ((line = br.readLine()) != null) {
				if (!line.startsWith(">")) {
					sb.append(line);

				} else {
					digestEntry(sb.toString());
					sb.setLength(0);
					sb.trimToSize();
					sb.append(line + "\n");
				}
			}
			digestEntry(sb.toString());
			sb.setLength(0);
			sb.trimToSize();
			sb = null;
			br.close();
			br = null;
		} catch (FileNotFoundException e) {
			ExceptionLogger.writeSevereError(e);
		} catch (IOException e) {
			ExceptionLogger.writeSevereError(e);
		}
	}

	public ArrayList<Entry> getEntryList() {
		return list;
	}

	/**
	 * In case there are more than one line break within the sequence of one
	 * entry. Will reattach the separated parts
	 * 
	 * @param arr
	 * @return reattached parts
	 */
	private String buildSequence(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < arr.length; i++) {
			sb.append(arr[i].trim());
		}
		return sb.toString();
	}

	/**
	 * Does all the heavy lifting by splitting each CDS entry into its parts and
	 * creating a new CDS object
	 * 
	 */
	private void digestEntry(String line) {
		String[] splitByNewline = line.split("\n");  

		String seq;
		if (splitByNewline.length > 2)
			seq = buildSequence(splitByNewline).replaceAll("\n", "");
		else
			seq = splitByNewline[1];

		list.add(new Entry(splitByNewline[0], seq));
	}

}
