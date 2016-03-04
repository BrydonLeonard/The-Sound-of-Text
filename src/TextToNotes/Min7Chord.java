package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * Created by biGb on 3/4/2016.
 */
public class Min7Chord extends Chord {
    /**
     * Constructor, sets up the Minor 7th chord, based on the tonic, n1.
     * @param n1 The tonic
     */
    public Min7Chord(int n1){
        super();
        this.notes.add(MusicLogic.pitchCorrect(n1));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 3));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 7));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 10));
    }
}
