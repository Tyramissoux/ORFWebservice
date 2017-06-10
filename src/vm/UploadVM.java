package vm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class UploadVM {

	@Wire("#txtUpload")
	Textbox txt;
	@Wire("#btnGo")
	Button btn;

	

	@Command
	public void start() {
		String text = txt.getValue();

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
			Messagebox.show("Upload failed", "Warning",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Media media = upEvent.getMedia();
		String filePath = checkUploadedFile(media);
		if(filePath == null) return;
	
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			Files.copy(writer, media.getReaderData());

			Sessions.getCurrent().setAttribute("uploadedFilePath", filePath);
			
			Messagebox.show("success", "Warning",
					Messagebox.OK, Messagebox.EXCLAMATION);
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

		if (media.isBinary())
		{
			Messagebox.show("Chosen file is not a text based FASTA file", "Warning",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return null;
		}
		return filePath;
	}

	private String getTemp() {
		return System.getProperty("java.io.tmpdir");
	}

	private boolean checkIfValidFile(String pathToFile) {
		return (pathToFile.endsWith("fa")
				|| pathToFile.endsWith("fasta") || pathToFile.endsWith("fna")
				|| pathToFile.endsWith("faa") || pathToFile.endsWith("fas"));
	}

}
