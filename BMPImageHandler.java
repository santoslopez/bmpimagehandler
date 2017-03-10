import java.io.File;
import java.util.Scanner;
public class BMPImageHandler{

  private static String error = "No se encontro ningun parametro";
  private static String basics = "-basics";
  private static String rotate = "-rotate";
  private static String parametrosIncorrectos = "Los parametros son mas de lo esperado";
  private static String comandoInvalido = "El comando es invalido";
  public static void main(String[] args){
    try{
      int parametros=args.length;
      if(parametros==0){
        System.out.println(error);
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
