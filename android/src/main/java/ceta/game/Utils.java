package ceta.game;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.widget.Toast;

public class Utils {

	
	private void saveImage(Mat image, int count, String path, Context context){
    	Mat bakImage = image.clone();
    	for(int i = 0;i<5;i++){
    		String name="";
    		switch (i) {
			case 0:
				name="_original_";
				break;
			case 1:
				name="_transposed_";
				Core.transpose(bakImage, image);
				break;
			case 2:
				name="_transposed_fliped_CW_";
				Core.transpose(bakImage, image);
				Core.flip(image, image, 1);
				break;
			case 3:
				name="_double_fliped_";
				Core.flip(image, image, 0);
				break;
			case 4:
				name="_transposed_fliped_CCW_";
				Core.flip(image, image, -1);
				break;
			default:
				break;
			}
	    	File file = new File(path, "screenshot-"+name+count+".png");
	    	String filename = file.toString();
	    	
	    	Mat bgrImage = new Mat();
	    	Imgproc.cvtColor(image, bgrImage, Imgproc.COLOR_RGB2BGR);
	    	if(!Highgui.imwrite(filename,bgrImage)){
				CharSequence	text	=	"Failed to save the image!";
				int	duration	=	Toast.LENGTH_SHORT;
				Toast	toast	=	Toast.makeText(context,	text,	duration);
				toast.show();
	    	}else{
	        	MediaScannerConnection.scanFile(context, new String[] { file.getPath() }, new String[] { "image/png" }, null);
	    	}
    	}
    }
}
