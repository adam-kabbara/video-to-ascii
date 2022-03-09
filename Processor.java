import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;


public class Processor{
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    private String density = "Ñ@#W$9876543210?!abc;:+=-,._         ";
    public Mat img; public double fx; public double fy;

    public Processor(Object imgObj, double width_div, double height_div){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        if (imgObj instanceof Mat){ //if passed a Mat img, set img to param
            img = (Mat) imgObj;
        }else if (imgObj instanceof String){ // if passed string, treat it as dir to img & read img
            Imgcodecs imageCodecs = new Imgcodecs();
            img = imageCodecs.imread((String) imgObj);
        }
        fx = width_div;
        fy = height_div;
    }

    public Mat processImg(){

        //resize image
        Mat small = new Mat();
        Imgproc.resize(img, small, new Size(0, 0), fx, fy);

        // convert image to grayscale
        Mat grayscale = new Mat();
        Imgproc.cvtColor(small, grayscale, Imgproc.COLOR_RGB2GRAY);

        return grayscale;
    }

    public String[] mapToDensityChar(Mat img){
        double pixelValue;
        int index;
        char asciiChar;
        String row = "";
        String[] asciiChars = new String[img.height()];

        for (int i=0; i<img.height()-1; i++){ // -1 since java starts counting at 0 and not 1
            for (int j=0; j<img.width()-1; j++){
                pixelValue = img.get(i, j)[0];
                index = Long.valueOf(Math.round(Math.abs(pixelValue/255*(density.length()-1) - (density.length() - 1)))).intValue();
                asciiChar = density.charAt(index);
                row = row + asciiChar;
            }
            asciiChars[i] = row;
            row = "";
        }

        return asciiChars;
    }
}
