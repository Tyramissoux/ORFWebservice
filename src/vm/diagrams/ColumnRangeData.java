package vm.diagrams;

import java.util.List;

import orf.ORF;

import org.zkoss.chart.model.DefaultXYModel;
import org.zkoss.chart.model.XYModel;
import org.zkoss.zk.ui.Sessions;


public class ColumnRangeData {
    private  XYModel model;
    private List<ORF> list;
    private String[] categories;

    
    @SuppressWarnings("unchecked")
	public ColumnRangeData(){
    	list = (List<ORF>) Sessions.getCurrent().getAttribute("orfList");
    	
    	extractValues();
    	setCategories();
    }
    
    private void extractValues(){
    	model = new DefaultXYModel();
        model.setAutoSort(false);
        for (int i = 0; i < list.size(); i++) {
			model.addValue("Sequence position", list.get(i).getStartPos(),list.get(i).getEndPos());
		}
    }
  
    private void setCategories(){
    	categories = new String[list.size()];
    	for (int i = 0; i < categories.length; i++) {
			categories[i] = "Entry "+(i+1);
		}
    }
    
    public XYModel getXYModel() {
        return model;
    }
    
    public  String[] getCategories() {
        return categories;
    }
}
