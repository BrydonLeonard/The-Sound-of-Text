package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * Created by biGb on 3/1/2016.
 */
public class MajorChord extends Chord{
    /**
     * Constructor, sets up the Major chord, based on the tonic, n1.
     * @param n1 The tonic
     */
    public MajorChord(int n1){
        super();
        this.notes.add(MusicLogic.pitchCorrect(n1));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 4));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 7));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 12));
    }
}
