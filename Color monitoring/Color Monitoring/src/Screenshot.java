import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

//Klasa odpowiedzialna za tworzenie screenshotów i wybranie monitorowanego piksela

public class Screenshot extends JFrame
{
    public static double pixelX = 0;        //Inicjalizacja zmiennej pixelX - będzie to wartość, która będzie później sprawdzana przy alertach
    public static double pixelY = 0;        //Inicjalizacja zmiennej pixelY - będzie to wartość, która będzie później sprawdzana przy alertach
    File plik = new File("psc.png");        //Utworzenie zmiennej plik, który będzie wykorzystywany do stworzenia screenshota
   
    public void createScreenshot() throws AWTException, IOException     //Stworzenie screenshota
    {
        Robot robot = new Robot();                                                          //Pozwoli na stworzenie screenshota pulpitów
        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());      //Zdefiniowanie jak duży ma być screenshot - w tym przypadku obejmie wszystkie pulpity
        BufferedImage capture = robot.createScreenCapture(screen);                          //Zrobienie screenshota wielkości "screen" poprzez robota
        ImageIO.write(capture, "png", plik);                                                //Zapisanie screenshota do pliku
    }

    public void showScreenshot() throws IOException                     //Pokazanie screenshota w nowym oknie
    {
        final ImageIcon icon = new ImageIcon("psc.png");                //Pobranie screena z pliku "psc.png"
        final JFrame frame = new JFrame();                              //Utworzenie nowego okna jframe
        final JScrollPane jsp = new JScrollPane(new JLabel(icon));      //Stworzenie okna z suwakami i screenem
        frame.getContentPane().add(jsp);                                //Dodanie okna z suwakami do nowego okna
        frame.setSize(640, 480);                                        //Zdefiniowanie wielkości okna jframe
        frame.setResizable(false);                                      //Zablokowanie możliwości zmiany wielkości okna
        frame.setVisible(true);                                         //Ustawienie okna jframe w trybie widocznym

        jsp.addMouseListener(new MouseAdapter()                         //Mouse listener - potrzebny jest do wybrania piksela do monitorowania
        {
            @Override
            public void mousePressed(MouseEvent e)                      //Odpowiada za zdefiniowanie co się stanie po kliknięciu w screena
            {
                Point save = jsp.getViewport().getViewPosition();       //Pobranie wartości położenia suwaków w oknie jframe
                pixelX = e.getX() + save.getX();                        //Zapisanie wartości położenia myszy + wartości suwaka w osi X w momencie kliknięcia
                pixelY = e.getY() + save.getY();                        //Zapisanie wartości położenia myszy + wartości suwaka w osi Y w momencie kliknięcia
                //System.out.println("Mouse X: " + pixelX + " Mouse Y: " + pixelY);
                plik.delete();                                          //Usunięcie pliku screena
                icon.getImage().flush();                                //Służy do pozbycia się screenshota z okna - dzięki temu można na nowo odpalić okno z nowym screenem bez konieczności restartu programu
                jsp.removeMouseListener(this);                          //Usunięcie mouse listenera
                frame.setVisible(false);                                //Ustawienie okna w trybie niewidocznym
                frame.dispose();                                        //Usuwa i czyści okno jframe
            }
        });
    }
}
