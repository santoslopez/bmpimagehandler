import java.util.*;
import java.io.File;
import java.io.*;
public class BMPToGrayscale{
  private static byte[][] red;//arreglo donde guardamos el tono rojo
  private static byte[][] blue;//arreglo donde guardamos el tono azul
  private static byte[][] green;//arreglo donde guardamos el tono verde
  private static byte[] header = new byte[1078];//arreglo donde guardaremos la informacion del header, primeros 54 bytes
  private static BMPToGrayscale instancia;
  private static String guion = "-";
  private static String nameColor="";
  private static String message="";
  /*
  Hacemos uso de Singleton, donde sirve para crear una unica instancia de la clase BmpHandlerCore
  */
  public synchronized static BMPToGrayscale getInstancia(){
    if(instancia==null){
      instancia=new BMPToGrayscale();
    }
    return instancia;
  }
  /*La variable nameImage le indicamos el nombre de la imagen que queremos aplicarle el filtro*/
  public String filter(String nameImage){
    /*creamos un objeto archivo de la clase File, le pasamos el nombre del archivo a utilizar por medio
    de la variable nameImage*/
    File archivo = new File(nameImage);
    FileInputStream fileInputStream = null;
    FileOutputStream fileOutputStream = null;
    if(archivo.exists()){
      try{
          fileInputStream = new FileInputStream(archivo);
          //obtenemos la longitud de la variable nameImage o del nombre de la imagen que queremos aplicarle el filtro.
          int lengthNameImage = nameImage.length();
          //obtenemos unicamente el nombre de la imagen sin la extension ( ".bmp",".jpg",".png",etc )
          String name = nameImage.substring(0,lengthNameImage-4);
          //Creamos el archivo, con las caracteristicas que piden en el proyecto ( nombreImagen-nombreColor.bmp)
          fileOutputStream = new FileOutputStream(name+guion+"grayscale.bmp" );
          //Leemos los primeros 1078 bytes del header
          for(int position=0;position<1078;position++){
            header[position]=(byte)fileInputStream.read();
          }
          if(((BmpHandlerCore.getInstancia().getWidth())==640)&((BmpHandlerCore.getInstancia().getHeight())==480)){
            red=new byte[640][480];
            green=new byte[640][480];
            blue=new byte[640][480];
            for(int h=0;h<BmpHandlerCore.getInstancia().getHeight();h++){
              for(int w=0;w<BmpHandlerCore.getInstancia().getWidth();w++){
                red[h][w]=(byte)fileInputStream.read();
                green[h][w]=(byte)fileInputStream.read();
                blue[h][w]=(byte)fileInputStream.read();
              }
            }
            fileOutputStream.write(header);//guarda los primeros 54 bytes del header de la imagen
            for(int h=0;h<640;h++){
              for(int w=0;w<480;w++){
              if(nameColor.equals("grayscale")){
                  int promedio = (int)(((0.30*red[h][w])+(0.59*green[h][w])+(0.11*blue[h][w]))/3);
                  fileOutputStream.write(Math.min(red[h][w],promedio));
                  fileOutputStream.write(Math.min(green[h][w],promedio ));
                  fileOutputStream.write(Math.min(blue[h][w],promedio ));
                }
              }
            }
            fileInputStream.close();
            fileOutputStream.close();
            System.out.println("La imagen "+nameColor+ " fue generado correctamente");
          }else{
            System.out.println("El ancho " + BmpHandlerCore.getInstancia().getWidth() + " y alto " + BmpHandlerCore.getInstancia().getHeight() + " de la imagen son dimensiones incorrectas");
            System.out.println("Dimensiones esperadas: 640x480");
          }
      } catch(Exception exception){
        exception.printStackTrace();
      }
    }else{
      System.out.println("La imagen no se encontro en el proyecto.");
    }
    return nameImage;
  }
}
