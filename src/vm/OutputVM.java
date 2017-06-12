package vm;

import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Selectbox;

import orf.Entry;
import orf.ORF;
import orf.ORFanalyzer;

public class OutputVM {

	@Wire("#selBox")
	private Selectbox select;

	private List<ORF> orfs;
	ArrayList<Entry> allEntries;
	private int minSeqLen;
	private boolean multipleStartCodons;
	private ListModelList<String> model;
	private String header;
	private int selected;

	
	public OutputVM(){
		getSessionGlobals();
		fillSelectBox();
		//if(allEntries.size()==1)select.setVisible(false);
		entryToOrfList(0);
		if(orfs.size()==0){
			orfs.add(new ORF(0,'0'));
			Messagebox.show("No ORFs were found for '"+header+"' with a min. sequence length of "+minSeqLen,
					"Information", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	
	@Command
	@NotifyChange("header")
	public void changeModel(){
		entryToOrfList(select.getSelectedIndex());
		setHeader();
	}
	
	public String getHeader(){
		return header;
	}
	
	private void setHeader(){
		header = allEntries.get(select.getSelectedIndex()).getHeader();
		if(header.equals("")) header = "Entry "+(select.getSelectedIndex()+1);
	}

	private void fillSelectBox() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < allEntries.size(); i++) {
			String header = allEntries.get(i).getHeader();
			if (header.equals(""))
				header = "Entry " + (i + 1);
			else if (header.length() > 30)
				header = header.substring(0, 30) + "...";
			list.add(header);
		}
		model = new ListModelList<String>(list);
	}


	private void entryToOrfList(int entryNum) {
		String currentEntry = allEntries.get(entryNum).getSequence();
		orfs = new ORFanalyzer(currentEntry, minSeqLen, multipleStartCodons)
				.getORFlist();
	}

	@SuppressWarnings("unchecked")
	private void getSessionGlobals() {
		allEntries = (ArrayList<Entry>) Sessions.getCurrent().getAttribute(
				"listOfEntries");
		multipleStartCodons = (boolean) Sessions.getCurrent().getAttribute(
				"multiStart");
		minSeqLen = (int) Sessions.getCurrent().getAttribute("minSeqLength");
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
	}

	public int getSelected() {
		return selected;
	}

	public ListModelList<String> getModel() {
		return model;
	}

	public List<ORF> getOrfs() {
		return orfs;
	}

}
