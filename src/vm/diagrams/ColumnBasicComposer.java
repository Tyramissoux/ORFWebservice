package vm.diagrams;

import orf.Entry;

import org.zkoss.chart.Charts;
import org.zkoss.chart.Tooltip;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;


@SuppressWarnings("serial")
public class ColumnBasicComposer extends SelectorComposer<Window> {

    @Wire
    Charts chart;
    
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
    
        chart.setModel(new ColumnBasicData().getCategoryModel());
        
        chart.getXAxis().setCrosshair(true);
        
        chart.getYAxis().setMin(0);
        chart.getYAxis().getTitle().setText("Number of sequences");
        chart.getXAxis().getTitle().setText("Frame number and sense");
        
        Tooltip tooltip = chart.getTooltip();
        tooltip.setHeaderFormat("<span style=\"font-size:10px\">{point.key}</span><table>");
        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>"
            + "<td style=\"padding:0\"><b>{point.y:.1f} mm</b></td></tr>");
        tooltip.setFooterFormat("</table>");
        tooltip.setShared(true);
        tooltip.setUseHTML(true);
        
        chart.getPlotOptions().getColumn().setPointPadding(0.2);
        chart.getPlotOptions().getColumn().setBorderWidth(0);
        Entry entry = (Entry) Sessions.getCurrent().getAttribute("entry");
        chart.setSubtitle(entry.getHeader());
    }
}
