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

        mm.playMajor(str);
        Thread.sleep(1000);
        mm.playMinor(str);
        Thread.sleep(1000);
        mm.close();
    }
}
