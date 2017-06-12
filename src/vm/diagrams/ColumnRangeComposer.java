package vm.diagrams;
import orf.Entry;

import org.zkoss.chart.Charts;
import org.zkoss.chart.plotOptions.DataLabels;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;


@SuppressWarnings("serial")
public class ColumnRangeComposer extends SelectorComposer<Window> {

    @Wire
    Charts chart;

    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        ColumnRangeData crd = new ColumnRangeData();
        chart.setModel(crd.getXYModel());
        
        chart.getXAxis().setCategories(crd.getCategories());
        
        chart.getYAxis().setTitle("Position");

        DataLabels dataLabels = chart.getPlotOptions().getColumnRange().getDataLabels();
        dataLabels.setEnabled(true);
        Entry entry = (Entry) Sessions.getCurrent().getAttribute("entry");
        
        chart.getLegend().setEnabled(false);
        chart.setSubtitle(entry.getHeader());
    }
}
