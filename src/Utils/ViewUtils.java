package Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class ViewUtils {
    public static Image loadImage(String imgFileName){
        try {
            File imgFile = new File("src/" + imgFileName);
            Image img = ImageIO.read(imgFile);
            return img;
        } catch (Exception e) {
            System.out.println("failed to load image " + imgFileName);
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Image> loadAllPieceImg(String[] imageFilePaths){
        HashMap<String, Image> filePath2Img = new HashMap<String, Image>();
        try{
            for(String imageName: imageFilePaths){
                Image img = ViewUtils.loadImage(imageName);
                filePath2Img.put(imageName, img);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return filePath2Img;
    }

    public static HashMap<String, Image> loadAllPieceImg(){
        String[] imageFilePaths = ChessConstants.getPieceImgPaths();
        return loadAllPieceImg(imageFilePaths);
    }
}
