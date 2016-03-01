package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * Created by biGb on 3/1/2016.
 */
public class MinorChord extends Chord{
    public MinorChord(int n1, MidiChannel m){
        super(m);
        this.n1 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7));
        this.n2 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7) + MusicLogic.getMinorNote(2));
        this.n3 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7) + MusicLogic.getMinorNote(4));
        this.n4 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7) + MusicLogic.getMinorNote(7));
    }

    public void play(){
        midiChannel.noteOn(n1,60);
        midiChannel.noteOn(n2,60);
        midiChannel.noteOn(n3,60);
        midiChannel.noteOn(n4,60);
    }

    public void stop(){
        midiChannel.noteOff(n1);
        midiChannel.noteOff(n2);
        midiChannel.noteOff(n3);
        midiChannel.noteOff(n4);
    }
}
