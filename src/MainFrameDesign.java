
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class MainFrameDesign {

	protected final int FRAME_WIDTH = 600;
	protected final int FRAME_HEIGHT = 430;
	protected final int TEXT_FIELD_HEIGHT = 50;
	protected final int BUTTON_HEIGHT = 50;
	protected final int PROGRESS_BAR_HEIGHT = 50;
	protected JFrame mainFrame;
	protected JPanel mainPanel;
	
	protected GroupLayout groupLayout;
	
	protected JLabel lblInputPath;
	protected JTextField tfInputPath;
	protected JButton btnOpenInputPath;
	
	protected JLabel lblOutputPath;
	protected JTextField tfOutputPath;
	protected JButton btnSaveOutputPath;
	
	protected JPanel pAdditionalOptions;
	protected JTextField tfDeviceResolution;
	protected JLabel lblDeviceResolution;
	
	protected JPanel pOutputImageFormatBorder;
	protected ButtonGroup bgImageFormat;
	protected JRadioButton rbJPEG;
	protected JRadioButton rbPNG;
	protected JRadioButton rbGIF;
	protected JRadioButton rbDefault;
	
	protected JPanel pTextFormatBorder;
	protected ButtonGroup bgTextFormat;
	protected JRadioButton rbDoSpacesPlaceOfLineBreaks;
	protected JRadioButton rbDoLineBreaks;
	protected JRadioButton rbNone;
	
	protected JButton btnConvert;
	protected JProgressBar pbConvert;
	protected JLabel lblConvertProgressInfo;
	
	public MainFrameDesign()
	{
		initGuiComponents();
	}
	
	public void show()
	{
		mainFrame.setVisible(true);
	}
	
	protected void initGuiComponents()
	{
		mainFrame = new JFrame();
		mainFrame.setTitle("EpubVnocGUI");
		mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		mainFrame.setResizable(false);
		
		lblInputPath = new JLabel("Input file:");
		tfInputPath = new JTextField();
		tfInputPath.setSize(100, TEXT_FIELD_HEIGHT);
		btnOpenInputPath = new JButton();
		btnOpenInputPath.setText("...");
		btnOpenInputPath.setSize(50, BUTTON_HEIGHT);
		
		lblOutputPath = new JLabel("Output file:");
		tfOutputPath = new JTextField();
		tfOutputPath.setSize(100, TEXT_FIELD_HEIGHT);
		btnSaveOutputPath = new JButton();
		btnSaveOutputPath.setText("...");
		btnSaveOutputPath.setSize(50, BUTTON_HEIGHT);
		
		pAdditionalOptions = new JPanel();
		pAdditionalOptions.setBorder(BorderFactory.createTitledBorder("Additional options:"));
		lblDeviceResolution = new JLabel("Device resolution:");
		tfDeviceResolution = new JTextField("768x1024");
		tfDeviceResolution.setSize(100, TEXT_FIELD_HEIGHT);
		//pAdditionalOptions.add(lblDeviceResolution);
		//pAdditionalOptions.add(tfDeviceResolution);
		setUpAdditionalOptionsLayout();
		
		pOutputImageFormatBorder = new JPanel();
		pOutputImageFormatBorder.setBorder(BorderFactory.createTitledBorder("Output image format:"));
		bgImageFormat = new ButtonGroup();
		rbJPEG = new JRadioButton("JPEG", true);
		rbPNG = new JRadioButton("PNG");
		rbGIF = new JRadioButton("GIF");
		rbDefault = new JRadioButton("Default");
		bgImageFormat.add(rbJPEG);
		bgImageFormat.add(rbPNG);
		bgImageFormat.add(rbGIF);
		bgImageFormat.add(rbDefault);
		pOutputImageFormatBorder.add(rbJPEG);
		pOutputImageFormatBorder.add(rbPNG);
		pOutputImageFormatBorder.add(rbGIF);
		pOutputImageFormatBorder.add(rbDefault);
		
		pTextFormatBorder = new JPanel();
		pTextFormatBorder.setBorder(BorderFactory.createTitledBorder("Text format:"));
		bgTextFormat = new ButtonGroup();
		rbDoSpacesPlaceOfLineBreaks = new JRadioButton("Do spaces place of linebreaks", true);
		rbDoLineBreaks = new JRadioButton("Do linebreaks");
		rbNone = new JRadioButton("None");
		bgTextFormat.add(rbDoSpacesPlaceOfLineBreaks);
		bgTextFormat.add(rbDoLineBreaks);
		bgTextFormat.add(rbNone);
		setUpTextFormatLayout();
		
		btnConvert = new JButton();
		btnConvert.setText("Convert");
		btnConvert.setSize(50, BUTTON_HEIGHT);
		pbConvert = new JProgressBar(0, 100);
		lblConvertProgressInfo = new JLabel("Progress info:");
		
		//Layout
		setUpMainGroupLayout();
		mainFrame.add(mainPanel);
	}
	
	protected void setUpMainGroupLayout()
	{
		mainPanel = new JPanel();
		groupLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(groupLayout);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		//Horizontal
		groupLayout.setHorizontalGroup(
			groupLayout.createSequentialGroup()
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(lblInputPath)
				.addComponent(tfInputPath)
				.addComponent(lblOutputPath)
				.addComponent(tfOutputPath)
				.addComponent(pOutputImageFormatBorder)
				.addComponent(pAdditionalOptions)
				.addComponent(btnConvert)
				.addComponent(pbConvert)
				.addComponent(lblConvertProgressInfo))
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(btnOpenInputPath)
				.addComponent(btnSaveOutputPath)
				.addComponent(pTextFormatBorder))
		);
		//Vertical
		groupLayout.setVerticalGroup(
			groupLayout.createSequentialGroup()
			.addComponent(lblInputPath)
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(tfInputPath)
					.addComponent(btnOpenInputPath))
			.addComponent(lblOutputPath)
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(tfOutputPath)
					.addComponent(btnSaveOutputPath))
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(pOutputImageFormatBorder)
					.addComponent(pTextFormatBorder))
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(pAdditionalOptions))
			.addComponent(btnConvert)
			.addComponent(pbConvert)
			.addComponent(lblConvertProgressInfo)
		);
	}
	
	protected void setUpAdditionalOptionsLayout()
	{
		GroupLayout layout = new GroupLayout(pAdditionalOptions);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		pAdditionalOptions.setLayout(layout);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(lblDeviceResolution)
				.addComponent(tfDeviceResolution)));
		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addComponent(lblDeviceResolution)
			.addComponent(tfDeviceResolution));
	}
	
	protected void setUpTextFormatLayout()
	{
		GroupLayout layout = new GroupLayout(pTextFormatBorder);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		pTextFormatBorder.setLayout(layout);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(rbDoSpacesPlaceOfLineBreaks)
				.addComponent(rbDoLineBreaks)
				.addComponent(rbNone)));
		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addComponent(rbDoSpacesPlaceOfLineBreaks)
			.addComponent(rbDoLineBreaks)
			.addComponent(rbNone));
	}
	
}
