package vm;

import java.io.Serializable;
import java.util.ArrayList;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

@SuppressWarnings("serial")
public class BackEndHandler extends SelectorComposer<Component> {

	private Listbox orfListBox = new Listbox();
	private Textbox inputText = new Textbox();
	private ListModelList<orf.ORF> list = new ListModelList<orf.ORF>();
	
	
	public Textbox getInputText(){
		return inputText;
	}
	
	public Listbox getOrfListBox(){
		return orfListBox;
	}
	
	public ListModelList<orf.ORF> getList(){
		return list;
	}

	/*
	 * @AfterCompose public void initSetup(@ContextParam(ContextType.VIEW)
	 * Component view) { Selectors.wireComponents(view, this, false);
	 * //Executions
	 * .getCurrent().getDesktop().getWebApp().getConfiguration().setMaxUploadSize
	 * (10 * 1024);//for larger files }
	 */

	@Command
	public void processInput() {
		String input = inputText.getValue();
		
		ArrayList<orf.ORF> li = new orf.ORFanalyzer(input,30).getORFlist();
		list = new ListModelList<orf.ORF>(li);
		orfListBox.setModel(list);
		orfListBox.setVisible(true);
		orfListBox.setMultiple(true);
	}

	/*
	 * @SuppressWarnings({"rawtypes"}) public void doAfterCompose(Component
	 * comp) throws Exception {
	 * 
	 * orfListBox.setItemRenderer(new ListitemRenderer() {
	 * 
	 * @Override public void render(Listitem item, Object arg1, int arg2) throws
	 * Exception {
	 * 
	 * item.appendChild(new Listcell(value.getCashBankVoucherNumber()));
	 * item.appendChild(new
	 * Listcell(value.getCashBankVoucherDate().toString()));
	 * item.appendChild(new Listcell(value.getCashBankType()));
	 * item.appendChild(new Listcell(value.getCashBankCurrency())); //
	 * item.appendChild(new // Listcell(value.getCashBankRate().toString()));
	 * item.appendChild(new Listcell(value.getCashBankChequeDate().toString()));
	 * item.appendChild(new Listcell(value.getCashBankChequeNo()));
	 * item.setValue(value); } }); cashBankListBox.setModel(new
	 * ListModelList<CashBank>(lst)); }
	 */
}
