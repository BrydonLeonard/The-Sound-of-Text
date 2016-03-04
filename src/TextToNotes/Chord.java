package TextToNotes;

import javax.sound.midi.MidiChannel;
import java.util.ArrayList;

/**
 * A parent class for all chords
 */
public class Chord {
    protected ArrayList<Integer> notes;

    public Chord(){
        notes = new ArrayList<Integer>();
    }

    /**
     * Plays the chord
     */
    public void play(MidiChannel midiChannel){
        System.out.println(chordName() + " " + this.getClass().getSimpleName());
        for (int i : notes){
            midiChannel.noteOn(i+12, 50);
        }
    }

    /**
     * Stops playing the chord
     */
    public void stop(MidiChannel midiChannel){
        for (int i : notes){
            midiChannel.noteOff(i+12);
        }
    }

    public String chordName(){
        if (notes.size() > 0)
            return MusicLogic.getFlatNoteName(notes.get(0));
        else return "";
    }
}
