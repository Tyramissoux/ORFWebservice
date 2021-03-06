package orf;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ORF {
	int frame;
	int startPos;
	int endPos;
	int aaLen;
	double gcContent;
	DecimalFormat df;

	StringBuilder nucSeq;
	StringBuilder aaSeq;
	char sense;

	public ORF(int frame, char sense) {
		nucSeq = new StringBuilder();
		aaSeq = new StringBuilder();
		this.frame = frame;
		this.sense = sense;
		df = new DecimalFormat("#.00"); 

	}
	
	public double getGcContent(){
		if(gcContent == 0) gcContent = calculateGCContent();
		return gcContent;
	}
	
	public String getGcCon(){
		return df.format((getGcContent()*100));
	}
	
	public double calculateGCContent(){
		String seq = getNucSequence();
		int counter = 0;
		for (int i = 0; i < seq.length(); i++) {
			if(seq.charAt(i) == 'c' || seq.charAt(i)=='g')counter++;
		}
		return ((double)counter/(double)seq.length());
	}

	protected void addStartPos(int pos){
		startPos = pos;
		addEndPos(pos +getSeqLength());
	}
	
	protected void addCodon(String in) {
		nucSeq.append(in);
		aaSeq.append(translateCodon(in));
	}

	private void addEndPos(int pos){
		endPos = pos;
	}
	
	
	public int getStartPos(){
		return startPos;
	}
	
	public int getEndPos(){
		return endPos;
	}
	
	public char getSense(){
		return sense;
	}
	
	public int getSeqLength(){
		return nucSeq.length();
	}
	
	public int getAaLen(){
		return aaSeq.length();
	}
	
	public int getFrameNum(){
		return frame;
	}
	
	public String getNucSequence(){
		if(nucSeq.length() == 0)return "0";
		return nucSeq.toString();
	}
	
	public String getAaSequence(){
		if(aaSeq.length() == 0)return "0";
		return aaSeq.toString();
	}
	
	/**
	 * for testing purposes
	 * @return
	 */
	protected StringBuilder getInfo(){
		StringBuilder sb = new StringBuilder();
		sb.append(startPos);
		sb.append("\t");
		sb.append(endPos);
		sb.append("\t");
		sb.append(sense);
		sb.append("\t");
		sb.append(frame);
		sb.append("\t");
		sb.append(getSeqLength());
		sb.append("\t");
		sb.append(getAaLen());
		sb.append("\t");
		sb.append(nucSeq);
		sb.append("\t");
		sb.append(aaSeq);		
		return sb;
	}
	/**
	 * simple case switch that takes a triplet and translates it into one amino
	 * acid
	 * 
	 * @param abc
	 * @return amino acid one letter code
	 */
	private char translateCodon(String in) {
		switch (in) {
		case "tct":
			return 'S';
		case "tcc":
			return 'S';
		case "tca":
			return 'S';
		case "tcg":
			return 'S';
		case "agt":
			return 'S';
		case "agc":
			return 'S';

		case "gct":
			return 'A';
		case "gcc":
			return 'A';
		case "gca":
			return 'A';
		case "gcg":
			return 'A';

		case "tta":
			return 'L';
		case "ttg":
			return 'L';
		case "ctt":
			return 'L';
		case "ctc":
			return 'L';
		case "cta":
			return 'L';
		case "ctg":
			return 'L';

		case "ggt":
			return 'G';
		case "ggc":
			return 'G';
		case "gga":
			return 'G';
		case "ggg":
			return 'G';

		case "aaa":
			return 'K';
		case "aag":
			return 'K';

		case "cgt":
			return 'R';
		case "cgc":
			return 'R';
		case "cga":
			return 'R';
		case "cgg":
			return 'R';
		case "aga":
			return 'R';
		case "agg":
			return 'R';

		case "aat":
			return 'N';
		case "aac":
			return 'N';

		case "gat":
			return 'D';
		case "gac":
			return 'D';

		case "ttt":
			return 'F';
		case "ttc":
			return 'F';

		case "tgt":
			return 'C';
		case "tgc":
			return 'C';

		case "cct":
			return 'P';
		case "ccc":
			return 'P';
		case "cca":
			return 'P';
		case "ccg":
			return 'P';

		case "caa":
			return 'Q';
		case "cag":
			return 'Q';

		case "gaa":
			return 'E';
		case "gag":
			return 'E';

		case "act":
			return 'T';
		case "acc":
			return 'T';
		case "aca":
			return 'T';
		case "acg":
			return 'T';

		case "cat":
			return 'H';
		case "cac":
			return 'H';

		case "att":
			return 'I';
		case "atc":
			return 'I';
		case "ata":
			return 'I';

		case "gtt":
			return 'V';
		case "gtc":
			return 'V';
		case "gta":
			return 'V';
		case "gtg":
			return 'V';

		case "tgg":
			return 'W';

		case "tat":
			return 'Y';
		case "tac":
			return 'Y';

		case "atg":
			return 'M';

		case "taa":
			return ' ';
		case "tga":
			return ' ';
		case "tag":
			return ' ';
		}

		return '?';
	}
	
}


