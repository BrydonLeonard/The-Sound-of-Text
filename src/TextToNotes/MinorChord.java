package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * Class for minor chords
 */
public class MinorChord extends Chord{
    /**
     * Constructor, sets up MidiChannel and the tonic
     * @param n1 The tonic
     * @param m The MidiChannel
     */
    public MinorChord(int n1, MidiChannel m){
        super(m);
        this.n1 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7));
        this.n2 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7) + MusicLogic.getMinorNote(2));
        this.n3 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7) + MusicLogic.getMinorNote(4));
        this.n4 = MusicLogic.pitchCorrect(MusicLogic.getMinorNote(n1%7) + MusicLogic.getMinorNote(7));
    }
}
