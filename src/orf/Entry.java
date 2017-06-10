package orf;

public class Entry {
	
	private String header ="";
	private String sequence;
	
	public Entry(){
		
	}
	public Entry(String head, String seq){
		this.header = head;
		this.sequence = seq;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	
	

}
