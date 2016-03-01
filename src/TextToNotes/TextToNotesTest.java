package TextToNotes;

import javax.sound.midi.*;
import javax.sound.sampled.AudioPermission;

/**
 * Created by biGb on 3/1/2016.
 */
public class TextToNotesTest {
    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        MidiManager mm = new MidiManager();
        mm.forMajorUpDown(50, (i) -> mm.play(i));
        mm.forMinorUpDown(50, (i) -> mm.play(i));
    }
}
