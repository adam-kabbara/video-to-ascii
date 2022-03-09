import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Mat;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import org.opencv.highgui.HighGui;
import java.awt.Font;
import java.awt.Color;


public class classMain extends JFrame{
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    static JFrame frame;
    static JTextArea textArea;

    public static void main(String[] args) {

        // init JFrame display
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true); // if we wrap the text, we can see if there is text flowing off the
        Font font = new Font("Consolas", Font.PLAIN, 6); // screen tweak variables accordingly
        textArea.setFont(font);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        frame = new JFrame("PICTURE TO ASCII");
        frame.add(textArea);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // end program after closing window

        // get ascii characters
        Processor myProcessor;
        Mat processedImg;
        String[] asciiArray;
        String asciiString;

        VideoCapture capture = new VideoCapture(0);
        Mat vid_frame = new Mat();
        while (true) {
            capture.read(vid_frame);
            myProcessor = new Processor(vid_frame, 0.2, 0.2);
            processedImg = myProcessor.processImg();
            asciiArray = myProcessor.mapToDensityChar(processedImg);
            asciiString = refactorAsciiArray(asciiArray);
            textArea.setText(asciiString);
            HighGui.imshow("test", processedImg);
            HighGui.waitKey(1);
        }



    }

    public static String refactorAsciiArray(String[] asciiArray){
        String asciiString = "";
        for (String str : asciiArray) {
            if (str != null) {
                for (char ch : str.toCharArray()) {
                    asciiString = asciiString.concat(ch + "  ");
                }
                asciiString = asciiString.concat("\n");
            }
        }
        return asciiString;
    }
}
