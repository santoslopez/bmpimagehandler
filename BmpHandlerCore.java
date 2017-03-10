import java.util.*;
import java.io.File;
import java.io.*;
public class BmpHandlerCore{
  private static byte[][] red;//arreglo donde guardamos el tono rojo
  private static byte[][] blue;//arreglo donde guardamos el tono azul
  private static byte[][] green;//arreglo donde guardamos el tono verde
  private static byte[] header = new byte[54];//arreglo donde guardaremos la informacion del header, primeros 54 bytes
  private static BmpHandlerCore instancia;
  private static String formatBMP = ".bmp";//formato de la imagen con la que se trabaja
  private static String guion = "-";
  private int width;
  private int height;
  private static String nameColor="";
  private static String message="";
  private static String mensajeEspera = "Espere un momento.";
  private static String imagenNoEncontrado = "La imagen no se encontro en el proyecto.";

  /*
  Hacemos uso de Singleton, sirve para crear una unica instancia de la clase BmpHandlerCore
  */
  public synchronized static BmpHandlerCore getInstancia(){
    if(instancia==null){
      instancia=new BmpHandlerCore();
    }
    return instancia;
  }
  /*Metodos Get que devuelven el ancho y alto de la imagen*/
  public int getWidth(){
    return width;
  }
  public int getHeight(){
    return height;
  }

  /*La variable nameImage le indicamos el nombre de la imagen que queremos aplicarle el filtro*/
  public String filter(String nameImage){
    /*creamos un objeto archivo de la clase File, le pasamos el nombre del archivo a utilizar por medio
    de la variable nameImage*/
    File archivo = new File(nameImage);
    FileInputStream fileInputStream = null;
    FileOutputStream fileOutputStream = null;
    String generateColor[]={"red","blue","green","sepia","clone"};//arreglo de colores
    String nameColor="";
    if(archivo.exists()){

      if(nameImage.endsWith(".bmp")){
        System.out.println(mensajeEspera);
        try{
          for(int pos=0;pos<=4;pos++){
            nameColor=generateColor[pos];
            fileInputStream = new FileInputStream(archivo);
            //obtenemos la longitud de la variable nameImage o del nombre de la imagen que queremos aplicarle el filtro.
            int lengthNameImage = nameImage.length();
            //obtenemos unicamente el nombre de la imagen sin la extension
            String name = nameImage.substring(0,lengthNameImage-4);
            //Creamos el archivo, con las caracteristicas que piden en el proyecto ( nombreImagen-nombreColor.bmp)
            fileOutputStream = new FileOutputStream(name+guion+nameColor+".bmp" );
            //Leemos los primeros 54 bytes del header
            for(int position=0;position<54;position++){
              header[position]=(byte)fileInputStream.read();
            }
            /*Hacemos un corrimiento de bits a la izquierda*/
            width = (((int)header[21]&0xff)<<24)|(((int)header[20]&0xff)<<16)|(((int)header[19]&0xff)<<8)|(((int)header[18]&0xff));
            height = (((int)header[25]&0xff)<<24)|(((int)header[24]&0xff)<<16)| (((int)header[23]&0xff)<<8)|(((int)header[22]&0xff));
            if( (getWidth()==640)&((getHeight())==480)){
              blue=new byte[height][width];
              green=new byte[height][width];
              red=new byte[height][width];
              for(int h=0;h<height;h++){
                for(int w=0;w<width;w++){
                  red[h][w]=(byte)fileInputStream.read();
                  green[h][w]=(byte)fileInputStream.read();
                  blue[h][w]=(byte)fileInputStream.read();
                }
              }
              fileOutputStream.write(header);//guarda los primeros 54 bytes del header de la imagen
              for(int h=0;h<getHeight();h++){
                for(int w=0;w<getWidth();w++){
                  if(nameColor.equals("blue")){
                    fileOutputStream.write(blue[h][w]);
                    fileOutputStream.write(0);
                    fileOutputStream.write(0);
                  }else if(nameColor.equals("green")){
                    fileOutputStream.write(0);
                    fileOutputStream.write(green[h][w]);
                    fileOutputStream.write(0);
                  }else if(nameColor.equals("red")){
                    fileOutputStream.write(0);
                    fileOutputStream.write(0);
                    fileOutputStream.write(red[h][w]);
                  }else if(nameColor.equals("sepia")){
                    //Esos valores con los que lo multiplique son los recomendados por Microsoft
                    int newRed = (int)((red[h][w]*0.393) +(green[h][w]*0.769)+(blue[h][w]*0.189));
                    int newGreen = (int)((red[h][w]*0.349) +(green[h][w]*0.686)+(blue[h][w]*0.168));
                    int newBlue = (int)((red[h][w]*0.272) +(green[h][w]*0.534)+(blue[h][w]*0.131));
                    fileOutputStream.write(Math.min(newRed,255));
                    fileOutputStream.write(Math.min(newGreen,255));
                    fileOutputStream.write(Math.min(newBlue,255));
                  }else{
                    fileOutputStream.write(red[h][w]);
                    fileOutputStream.write(green[h][w]);
                    fileOutputStream.write(blue[h][w]);
                  }
                }
              }
              fileInputStream.close();
              fileOutputStream.close();
              System.out.println("La imagen "+nameColor+ " fue generado correctamente");
            }else{
              System.out.println("El ancho " + width + " y alto " + height + " de la imagen son dimensiones incorrectas");
              System.out.println("Dimensiones esperadas: 640x480");
              break;
            }
          }
        } catch(Exception exception){
          exception.printStackTrace();
        }
      }else{
        System.out.println("El formato de la imagen no es .bmp");
      }
    }else{
      System.out.println(imagenNoEncontrado);
    }
    return nameImage;
  }
}
