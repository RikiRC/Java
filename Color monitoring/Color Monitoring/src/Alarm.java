import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

//Klasa odpowiedzialna za sprawdzanie koloru i tego czy istnieje alert

public class Alarm
{

    public String status = "OK";                    //Początkowa wartość statusu alarmu

    //int colorRed = 0;
    //int colorGreen = 0;
    //int colorBlue = 0;
    
    Color color1 = new Color(0,0,0);                //color1 - odpowiada za aktualną wartość RGB piksela
    Color colorAlert = new Color(255,255,255);      //colorAlert - odpowiada za definicję koloru, który NIE jest alarmem - innymi słowy wszystkie odchyły od tego koloru uruchomią alarm

    public void check() throws AWTException, IOException, InterruptedException
    {
        Robot robot1 = new Robot();                 //Obiekt, który pozwoli na pobranie koloru piksela

        Color color = robot1.getPixelColor((int) (Screenshot.pixelX), (int) (Screenshot.pixelY));   //Pobranie koloru danego piksela z pulpitów
        
        color1 = color;                             //Aktualizacja zmiennej color1

        //colorRed = color.getRed();
        //colorGreen = color.getGreen();
        //colorBlue = color.getBlue();

        //System.out.println("Red: " + colorRed);
        //System.out.println("Green: " + colorGreen);
        //System.out.println("Blue: " + colorBlue);
        //System.out.println("-----------------");
    }

    public void checkAlert() throws IOException
    {
        if (!color1.equals(colorAlert))     //Sprawdzenie czy obecny kolor jest różny od koloru bez alertu - jeżeli tak to odpalony zostanie alarm
        {
            start();                        //Rozpoczęcie alarmu
        } 
        else
        {
            status = "OK";                  //Ustawienie statusu OK gdy nie ma alarmu
        }
    }

    public void start() throws IOException      //Odpowiada za sygnał dźwiękowy i zmianę statusu w trakcie alarmu
    {
        status = "ALARM";
        InputStream inputStream = getClass().getResourceAsStream("1111.wav");
        AudioStream audioStream = new AudioStream(inputStream);
        AudioPlayer.player.start(audioStream);
    }
}
