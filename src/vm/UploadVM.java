package vm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import orf.Entry;

public class UploadVM {

	@Wire("#txtUpload")
	private Textbox txt;
	@Wire("#btnGo")
	private Button btn;
	
	private ListModelList<String> model;

	Pattern pattern;
	Matcher matcher;
	
	public ListModelList<String> getModel(){
		return model;
	}
	
	 public static List<String> getModelType() {return Arrays.asList(new String[]{"30", "75", "150", "300","600","900"});}
	 
	@Init
	public void init(){
		model = new ListModelList<String>(getModelType());
	}
	
	
	@SuppressWarnings("static-access")
	@Command
	public void start() {
		pattern = pattern.compile("[atgcuryswkmbdhvn.-]+",
				Pattern.CASE_INSENSITIVE); //ambiguity code included
		String text = txt.getValue();
		if (text == null || text.length() == 0) {
			Messagebox.show("No textbox input ", "Warning", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		String[] lines = text.split("\n");
		if (lines.length > 2) {
			Messagebox
					.show("Textbox input malformed - no linebreaks inside the sequence and not more than one (FASTA) sequence",
							"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Entry e = new Entry();
		for (int i = 0; i < lines.length; i++) {

			pattern.matcher(lines[i]);
			if (matcher.find()) {
				e.setSequence(lines[i].toLowerCase());
				continue;
			}
			if (lines[i].trim().startsWith(">")) {
				e.setHeader(lines[i]);
			}
		}
		if(e.getSequence().equals("")){
			Messagebox.show("No matching sequence found - try again, please", "Warning",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

	}

	private File createFolder() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		String tmpPath = getTemp();
		if (!tmpPath.endsWith(File.separatorChar + ""))
			tmpPath = tmpPath + File.separatorChar;

		String date = day + "." + month + "." + year + "-" + hour + ":" + min
				+ ":" + sec;
		Sessions.getCurrent().setAttribute("uploadDate", date);
		String filePath = tmpPath + year + "_" + month + "_" + day + "_" + hour
				+ "_" + min + "_" + sec + File.separatorChar;

		File f = new File(filePath);
		f.mkdir();

		return f;
	}

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Executions.getCurrent().getDesktop().getWebApp().getConfiguration()
				.setMaxUploadSize(10 * 1024);// for larger files
	}

	@Command
	@NotifyChange("fileuploaded")
	public void uploadFile(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
			throws IOException {

		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent == null) {
			Messagebox.show("Upload failed", "Warning", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}

		Media media = upEvent.getMedia();
		String filePath = checkUploadedFile(media);
		if (filePath == null)
			return;

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			Files.copy(writer, media.getReaderData());

			Sessions.getCurrent().setAttribute("uploadedFilePath", filePath);

			Messagebox.show("success", "Warning", Messagebox.OK,
					Messagebox.EXCLAMATION);
			// Executions.sendRedirect("customerChoice.zul");

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	private String checkUploadedFile(Media media) {
		String originalName = media.getName();
		if (!checkIfValidFile(originalName)) {
			Messagebox.show("Chosen file is not FASTA formatted", "Warning",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return null;
		}

		File saveFolder = createFolder();
		String filePath = saveFolder.getAbsolutePath() + File.separatorChar
				+ originalName;

		if (media.isBinary()) {
			Messagebox.show("Chosen file is not a text based FASTA file",
					"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
			return null;
		}
		return filePath;
	}

	private String getTemp() {
		return System.getProperty("java.io.tmpdir");
	}

	private boolean checkIfValidFile(String pathToFile) {
		return (pathToFile.endsWith("fa") || pathToFile.endsWith("fasta")
				|| pathToFile.endsWith("fna") || pathToFile.endsWith("faa") || pathToFile
					.endsWith("fas"));
	}

}
