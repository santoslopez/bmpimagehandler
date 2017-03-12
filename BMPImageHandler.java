import java.io.File;
import java.util.Scanner;
public class BMPImageHandler{

  private static String error = "No se encontro ningun parametro";
  private static String basics = "-basics";
  private static String rotate = "-rotate";
  private static String grayscale = "-grayscale";
  private static String rle = "-rle";
  private static String kernel = "-kernel";
  private static String resize = "-resize";
  private static String all = "-all";
  private static String javaBMPImageHandler = "java BMPImageHandler";
  private static String parametrosIncorrectos = "Los parametros son mas de lo esperado";
  private static String comandoInvalido = "El comando es invalido";

  /*Formato de ejecucion del proyecto*/
  public static void formatoEjecucion(){
    System.out.println("Parte I: Imagenes rojo, verde, azul y sepia. \n");
    System.out.println(javaBMPImageHandler+ " " +basics +" imagen.bmp\n");
    System.out.println("Parte II: Modificar imagen. \n");
    System.out.println("Ejecutar unicamente las rotaciones");
    System.out.println(javaBMPImageHandler+" "+rotate+ " imagen.bmp \n" );
    System.out.println("Ejecutar las imagenes thin y flat");
    System.out.println(javaBMPImageHandler+ " "+resize+ " imagen.bmp \n");
    System.out.println("Parte III: Escala de grises");
    System.out.println(javaBMPImageHandler+ " "+grayscale+ " imagen.bmp\n");
    System.out.println("Parte IV: Run Length Encoding");
    System.out.println(javaBMPImageHandler+ " "+rle + " imagen.bmp\n");
    System.out.println("Parte V: Kernel");
    System.out.println(javaBMPImageHandler+ " "+kernel + " "+kernel+".txt"+ " imagen.bmp\n");
    System.out.println("Ejecutar todas las partes del proyecto");
    System.out.println(javaBMPImageHandler+ " "+all + " imagen.bmp");
  }

  public static void main(String[] args){
    try{
      int parametros=args.length;
      if(parametros==0){
        System.out.println(error);
      }else if(parametros==1){
        if(args[0].equals("-help")){
          formatoEjecucion();
        }else{
          System.out.println(comandoInvalido);
        }
      }else if((parametros>=1)&&(parametros<=2)){
          if(args[0].equals(basics)&&(args[1].equals(args[1]))){
              BmpHandlerCore.getInstancia().filter(args[1]);
          }else if(args[0].equals(rotate)&&(args[1].equals(args[1]))){
              BmpHandlerRotator.getInstancia().rotateImage(args[1]);
          }else{
            System.out.println(comandoInvalido);
          }
      }else{
        System.out.println(parametrosIncorrectos);
      }
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }
}
