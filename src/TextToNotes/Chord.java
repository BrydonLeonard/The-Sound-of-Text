package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * Created by biGb on 3/1/2016.
 */
public class Chord {
    protected int n1;
    protected int n2;
    protected int n3;
    protected int n4;
    MidiChannel midiChannel;

    public Chord(MidiChannel midiChannel){
        this.midiChannel = midiChannel;
    }

    public void play(){
        midiChannel.noteOn(n1, 60);
        midiChannel.noteOn(n1,60);
        midiChannel.noteOn(n2,60);
        midiChannel.noteOn(n3,60);
    }

    public void stop(){
        midiChannel.noteOff(n1);
        midiChannel.noteOff(n2);
        midiChannel.noteOff(n3);
        midiChannel.noteOff(n4);
    }
}
