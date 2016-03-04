package TextToNotes;

import javax.sound.midi.*;
import java.util.Scanner;

/**
 * Created by biGb on 3/1/2016.
 */
public class TextToNotesTest {
    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        MidiManager mm = new MidiManager(60, 200, 50, 0);

        System.out.println("Enter your string");
        Scanner scnr = new Scanner(System.in);
        String str = scnr.nextLine();

        //These will play a major and minor scale, just as an example of the methods.
        //mm.forMajorUpDown(60, (i) -> mm.play(i));
        //mm.forMinorUpDown(60, (i) -> mm.play(i));

        //These are all the calls to the methods that play your string.
        //Uncomment each individually or together to test them
        //mm.playMajor(str);
        //Thread.sleep(1000);
        //mm.playMinor(str);
        //Thread.sleep(1000);
        //mm.playMajorChords(str);
        //Thread.sleep(1000);
        //mm.playMinorChords(str);
        //Thread.sleep(1000);
        //mm.playMajorTwoHands(str);
        //Thread.sleep(1000);

        mm.playMajorWithProgression(str);
        Thread.sleep(1000);

        //For testing the instruments in bank 1
        /*for (int i = 0; i < 128; i++){
            System.out.println(i);
            mm.switchInstrument(i);
            mm.playMajor(str);
            Thread.sleep(1000);
        }*/


        mm.close();
    }
}
