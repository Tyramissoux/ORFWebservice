package vm.diagrams;

import java.util.List;

import orf.ORF;

import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.zk.ui.Sessions;


public class ColumnBasicData {
	
	private CategoryModel model;
	
	@SuppressWarnings("unchecked")
	public ColumnBasicData(){
		 model = new DefaultCategoryModel();
		 List<ORF> list = (List<ORF>) Sessions.getCurrent().getAttribute("orfList");
		 int[] counter = new int[6];
		 for(ORF o : list){
			 char sense = o.getSense();
			 int frame = o.getFrameNum();
			 if(sense == '+' &&frame == 1){
				 counter[0]++;
				 continue;
			 }
			 if(sense == '+' &&frame == 2){
				 counter[1]++;
				 continue;
			 }
			 if(sense == '+' &&frame == 3){
				 counter[2]++;
				 continue;
			 }
			 if(sense == '-' &&frame == 1){
				 counter[3]++;
				 continue;
			 }
			 if(sense == '-' &&frame == 2){
				 counter[4]++;
				 continue;
			 }
			 if(sense == '-' &&frame == 3){
				 counter[5]++;
				 continue;
			 }
		 }
	        model.setValue("+", "1", counter[0]);
	        model.setValue("+", "2", counter[1]);
	        model.setValue("+", "3", counter[2]);
	        model.setValue("-", "1", counter[3]);
	        model.setValue("-", "2", counter[4]);
	        model.setValue("-", "3", counter[5]);
	}
 
    
    public CategoryModel getCategoryModel() {
        return model;
    }
}
