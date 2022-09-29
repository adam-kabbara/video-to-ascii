import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Mat;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import org.opencv.highgui.HighGui;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class classMain{
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    static JFrame frame;
    static JTextArea textArea;
    static int font_size = 6;

    public static void main(String[] args){
        // init JFrame display
        textArea = new JTextArea();
        textArea.setEditable(false);
        //textArea.setLineWrap(true); // if we wrap the text, we can see if there is text flowing off the
        Font font = new Font("Consolas", Font.PLAIN, font_size); // screen tweak variables accordingly
        textArea.setFont(font);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        frame = new JFrame("PICTURE TO ASCII");
        frame.add(textArea);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // end program after closing window

        textArea.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    font_size++;
                    Font font = new Font("Consolas", Font.PLAIN, font_size);
                    textArea.setFont(font);
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    font_size--;
                    Font font = new Font("Consolas", Font.PLAIN, font_size);
                    textArea.setFont(font);
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        // get ascii characters
        Processor myProcessor;
        Mat processedImg;
        String[] asciiArray;
        String asciiString;

        VideoCapture capture = new VideoCapture(0);
        Mat vid_frame = new Mat();
        while (true) {
            capture.read(vid_frame);
            Core.flip(vid_frame, vid_frame, 1);
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
