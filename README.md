# Video to ASCII

Video to ASCII is a fairly simple java program that converts video input from the webcam into ASCII characters which are displayed using JFrame

## OpenCV + Processor class

```java
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
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
