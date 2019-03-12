import java.awt.AWTException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;

//Klasa odpowiedzialna za cykliczne sprawdzanie alarmu i aktualizację labela

public class Schedule
{

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    Alarm alarm1 = new Alarm();     //Obiekt, który będzie sprawdzał czy jest alarm czy nie

    public void alarm(int time)     //Zapętlenie sprawdzania alarmu co daną ilość czasu (time) w sekundach
    {
        final Runnable alarm = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    alarm1.check();         //Aktualizacja wartości koloru piksela
                    alarm1.checkAlert();    //Sprawdzenie czy jest alert
                } catch (AWTException | IOException | InterruptedException ex)
                {
                    Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        scheduler.scheduleAtFixedRate(alarm, time, time, SECONDS); //Definicja powtarzania aktualizacji alarmu co "time" czasu w sekundach
    };
    
    private final ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(2); 
    
    public void label(int time)             //Aktualizacja labela w sekundach (time)
    {
        final Runnable label = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println(alarm1.status);
                if(alarm1.status.equals("OK"))
                {
                    MainFrame.jLabel1.setText("OK");        //Update labela gdy nie ma alarmu
                }
                if(alarm1.status.equals("ALARM"))
                {
                    MainFrame.jLabel1.setText("ALARM");     //Update labela gdy jest alarm
                }
            }
        };

        scheduler2.scheduleAtFixedRate(label, time, time, SECONDS); //Definicja powtarzania aktualizacji labela co "time" czasu w sekundach
    };
}
