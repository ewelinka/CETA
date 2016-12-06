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

	
	/**
	 * 
	 * @param image
	 * @param count
	 * @param path
	 * @param context
	 * @param mode 0. Original img
	 * 			   1- transposed
	 * 			   2- transposed and fliped CW
	 * 			   3- double fliped
	 * 			   4- transposed fliped CCW
	 */
	public static void saveImage(Mat image, int count, String path, Context context, int mode){
    	Mat auxImage = image.clone();
    	for(int i = 0;i<5;i++){
    		String name="";
    		switch (i) {
			case 0:
				break;
			case 1:
				Core.transpose(image, auxImage);
				break;
			case 2:
				Core.transpose(image, auxImage);
				Core.flip(auxImage, auxImage, 1);
				break;
			case 3:
				Core.flip(image, auxImage, 0);
				break;
			case 4:
				Core.flip(image, auxImage, -1);
				break;
			default:
				break;
			}
	    	File file = new File(path, name+count+".jpg");
	    	String filename = file.toString();
	    	
	    	Mat bgrImage = new Mat();
	    	Imgproc.cvtColor(auxImage, bgrImage, Imgproc.COLOR_RGB2BGR);
	    	if(!Highgui.imwrite(filename,bgrImage)){
				CharSequence	text	=	"Failed to save the image!";
				int	duration	=	Toast.LENGTH_SHORT;
				Toast	toast	=	Toast.makeText(context,	text,	duration);
				toast.show();
	    	}else{
	        	MediaScannerConnection.scanFile(context, new String[] { file.getPath() }, new String[] { "image/jpg" }, null);
	    	}
    	}
    }
}
