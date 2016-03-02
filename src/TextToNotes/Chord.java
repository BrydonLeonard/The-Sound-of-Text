package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * A parent class for all chords
 */
public class Chord {
    protected int n1;
    protected int n2;
    protected int n3;
    protected int n4;
    MidiChannel midiChannel;

    /**
     * Constructor, initialises the MidiChannel
     * @param midiChannel The MidiChannel
     */
    public Chord(MidiChannel midiChannel){
        this.midiChannel = midiChannel;
    }

    /**
     * Plays the chord
     */
    public void play(){
        midiChannel.noteOn(n1, 60);
        midiChannel.noteOn(n1,60);
        midiChannel.noteOn(n2,60);
        midiChannel.noteOn(n3,60);
    }

    /**
     * Stops playing the chord
     */
    public void stop(){
        midiChannel.noteOff(n1);
        midiChannel.noteOff(n2);
        midiChannel.noteOff(n3);
        midiChannel.noteOff(n4);
    }
}
