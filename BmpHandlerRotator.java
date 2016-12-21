import java.io.*;
import java.util.*;
public class BmpHandlerRotator{
  private static BmpHandlerRotator instancia;
  public synchronized static BmpHandlerRotator getInstancia(){
    if(instancia==null){
      instancia=new BmpHandlerRotator();
    }
    return instancia;
  }

  public String rotateImage(String nameImage){
    File file = new File(nameImage);
    FileInputStream fileInputStream = null;
    FileOutputStream fileOutputStream = null;
    if(file.exists()){
      try{
        fileInputStream = new FileInputStream(file);
        fileOutputStream = new FileOutputStream("HRotation"+nameImage);
        int lengthImage = (int)(file.length()-54);
        byte header[]=new byte[54];
        for(int i=0;i<54;i++){
          header[i]=(byte)fileInputStream.read();
        }
        byte arrayBytes[]=new byte[lengthImage];
        for(int pos=0;pos<arrayBytes.length;pos++){
          arrayBytes[(arrayBytes.length-1)-pos]=(byte)fileInputStream.read();
        }
        fileOutputStream.write(header);
        fileOutputStream.write(arrayBytes);
        fileOutputStream.write(0);
        fileOutputStream.write(0);
        System.out.println("La imagen se ha rotado 180 grados");
        fileInputStream.close();
        fileOutputStream.close();
      }catch(Exception ex){
        ex.printStackTrace();
      }
    }else{
      System.out.println("La imagen no se encontro");
    }
    return nameImage;
  }
}
