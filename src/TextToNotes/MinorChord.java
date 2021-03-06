package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * Class for minor chords
 */
public class MinorChord extends Chord{
    /**
     * Constructor, sets up the Minor chord, based on the tonic, n1.
     * @param n1 The tonic
     */
    public MinorChord(int n1){
        super();
        this.notes.add(MusicLogic.pitchCorrect(n1));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 3));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 7));
        this.notes.add(MusicLogic.pitchCorrect(n1 + 12));
    }
}
