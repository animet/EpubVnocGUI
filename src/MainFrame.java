
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Stati.Status;
import Vnoc.Misc.Random;
import Vnoc.Zip.CreateZip;
import Vnoc.Conversion.PdfToDocument;
import Vnoc.Creation.Epub.EpubPreparation;
import Vnoc.Creation.Epub.ImageCreation;
import Vnoc.Creation.Epub.TableOfContentCreation;
import Vnoc.Creation.Epub.XhtmlPageCreation;
import Vnoc.Documents.Document;
import Vnoc.Documents.Images.Size;
import Vnoc.Enums.Creation.FormatMode;
import Vnoc.Enums.Documents.Images.ImageFormat;


public class MainFrame extends MainFrameDesign implements Runnable{

	final int MIN_LENGTH_WITH_EPUB_EXTENSION = 6;
	String fileOutputName;
	String fileOutputPath;
	public MainFrame()
	{
		super();
		setActionListener();
		fileOutputName = "";
		fileOutputPath = "";
	}
	
	public void run()
	{
		
	}
	
	private void setActionListener()
	{
		mainFrame_Closed();
		btnOpenInputPath_Click();
		btnSaveOutputPath_Click();
		btnConvert_Click();
	}
	
	private void mainFrame_Closed()
	{
		this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	private void btnOpenInputPath_Click()
	{
		this.btnOpenInputPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
				//Bisher nur PDF Support
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					tfInputPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
	}
	
	private void btnSaveOutputPath_Click()
	{
		this.btnSaveOutputPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
				//Bisher nur EPUB Support
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("EPUB", "epub"));
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					fileOutputName = fileChooser.getSelectedFile().getName();
					fileOutputPath = fileChooser.getSelectedFile().getAbsolutePath().replace(fileOutputName, "");
					int fileNameLength = fileOutputName.length();
					if (fileOutputName.length() >= MIN_LENGTH_WITH_EPUB_EXTENSION 
							&& fileOutputName.substring(fileNameLength-6, fileNameLength-1).equals(".epub"))
					{
						tfOutputPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					}
					else
						tfOutputPath.setText(fileChooser.getSelectedFile().getAbsolutePath()+".epub");
					
				}
			}
		});
	}
	
	private void btnConvert_Click()
	{
		this.btnConvert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(new File(tfInputPath.getText()).exists())
				{
					Thread worker = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								convert();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					worker.start();
				}
				else
					JOptionPane.showConfirmDialog(null, "File does not exist!");
			}
		});
	}
	
	private void convert() throws IOException
	{
		String tempOutputPath = fileOutputPath+"temp_"+Random.getRandomString(8);
		Size deviceRes = new Size(768, 1024);
		////////STATUS ////////
		setProgress(1, "Epub preperation initialized");
		////////STATUS ////////
		EpubPreparation preparation = new EpubPreparation(tempOutputPath);
		preparation.prepare();
		////////STATUS ////////
		setProgress(5, "Epub preperation finished");
		////////STATUS ////////
		
		////////STATUS ////////
		setProgress(6, "Pdf text and image extraction initialized");
		////////STATUS ////////
		PdfToDocument pdf2Doc = new PdfToDocument(tfInputPath.getText());
		Document doc1 = pdf2Doc.getDocument();
		////////STATUS ////////
		setProgress(40, "Pdf text and image extraction finished");
		////////STATUS ////////
		
		System.out.println(new Date().toString());
		
		////////STATUS ////////
		setProgress(41, "Image creation initialized");
		////////STATUS ////////
		ImageCreation imageCreation = new ImageCreation(tempOutputPath, doc1, deviceRes);
		if(rbDefault.isSelected())
			imageCreation.createImages();
		else
		{
			if(rbJPEG.isSelected())
				imageCreation.createImages(ImageFormat.JPEG);
			else if (rbPNG.isSelected())
				imageCreation.createImages(ImageFormat.PNG);
			else if (rbGIF.isSelected())
				imageCreation.createImages(ImageFormat.GIF);
		}
		////////STATUS ////////
		setProgress(51, "Image creation finished");
		////////STATUS ////////
		
		////////STATUS ////////
		setProgress(52, "Table of content creation initialized");
		////////STATUS ////////
		TableOfContentCreation tocCreation = new TableOfContentCreation(doc1, tempOutputPath);
		tocCreation.create();
		////////STATUS ////////
		setProgress(63, "Table of content creation finished");
		////////STATUS ////////
		
		FormatMode txtFormat = FormatMode.DoSpacesPlaceOfLineBreaks;
		if (rbDoLineBreaks.isSelected())
			txtFormat = FormatMode.DoLineBreaks;
		else if (rbNone.isSelected())
			txtFormat = FormatMode.Default;
		else if (rbDoSpacesPlaceOfLineBreaks.isSelected())
			txtFormat = FormatMode.DoSpacesPlaceOfLineBreaks;
		
		////////STATUS ////////
		setProgress(64, "Xhtml page creation initialized");
		////////STATUS ////////
		XhtmlPageCreation pageCreation = new XhtmlPageCreation(doc1);
		//
		// "\\OEBPS" muss bei der nächsten einbindung weg!
		//
		pageCreation.createXhtmlPages(tempOutputPath+"\\OEBPS", txtFormat);
		////////STATUS ////////
		setProgress(80, "Xhtml page creation finished");
		////////STATUS ////////
		
		////////STATUS ////////
		setProgress(81, "Zip creation initialized");
		////////STATUS ////////
		CreateZip createZip = new CreateZip(tfOutputPath.getText());
		createZip.createEpubZip(tempOutputPath);
		////////STATUS ////////
		setProgress(100, "Zip creation finished");
		////////STATUS ////////
		JOptionPane.showConfirmDialog(null, "Epub successfully created!", "Info", JOptionPane.OK_OPTION);
	}
		
	private void setProgress(int progress, String progressInfo)
	{
		Status.PROGESS = progress;
		Status.PROGRESS_INFO = progressInfo;
		pbConvert.setValue(Status.PROGESS);
		lblConvertProgressInfo.setText(Status.PROGRESS_INFO);
	}
}
