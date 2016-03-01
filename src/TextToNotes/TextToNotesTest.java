package TextToNotes;

import javax.sound.midi.*;
import java.util.Scanner;

/**
 * Created by biGb on 3/1/2016.
 */
public class TextToNotesTest {
    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        MidiManager mm = new MidiManager(60, 150, 20);

        System.out.println("Enter your string");
        Scanner scnr = new Scanner(System.in);
        String str = scnr.nextLine();

        //These will play a major and minor scale, just as an example of the methods.
        //mm.forMajorUpDown(60, (i) -> mm.play(i));
        //mm.forMinorUpDown(60, (i) -> mm.play(i));

        //mm.playMajor(str);
        //Thread.sleep(1000);
        //mm.playMinor(str);
        //Thread.sleep(1000);
        mm.playMajorChords(str);
        //mm.playMinorChords(str);
        Thread.sleep(1000);
        mm.close();
    }
}
