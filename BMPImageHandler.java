import java.util.Scanner;
public class BMPImageHandler{
  //el texto es parte del comando que se debe ejecutar
  private static String javaBMPImageHandler = "java BMPImageHandler";
  private static BMPImageHandler instance;
  private static String extension = ".bmp";//formato de la imagen a trabajar
  private static String formatIncorrect = "El formato de la imagen no es .bmp.";
  public synchronized static BMPImageHandler getInstancia(){
    if(instance==null){
      instance=new BMPImageHandler();
    }
    return instance;
  }

  public static void main(String[] args){
    Scanner scanner=new Scanner(System.in);
    String command = scanner.nextLine();
    String longitudComando = String.valueOf(command.length());
    int longitudCmd = command.length();
    String imagen = command.substring(29,Integer.parseInt(longitudComando));
    String image = command.substring(29,longitudCmd-4);//obtengo solo el nombre de la imagen, en la posicion 29 empieza el nombre del archivo a procesar
    String extension = command.substring(longitudCmd-4,longitudCmd);//obtengo el formato de la imagen
    try{
      if(command.equals(javaBMPImageHandler+" -basics "+imagen)){
        if(extension.equals(extension)){
          System.out.println("Espere un momento...");
          BmpHandlerCore.getInstancia().filter(imagen);
          BMPToGrayscale.getInstancia().filter(imagen);
          BmpHandlerRotator b = new BmpHandlerRotator();
          b.rotateImage(imagen);
        }else{
          System.out.println(formatIncorrect);
        }

      }else if(command.equals(javaBMPImageHandler+" -rotate "+imagen)){
        if(extension.equals(extension)){
          BmpHandlerRotator.getInstancia().rotateImage(imagen);
        }else{
          System.out.println(formatIncorrect);
        }
      }else{
        System.out.println("Command invalid");
      }
    }catch(Exception ex){
      ex.printStackTrace();
    }finally{
    }
  }
}
