package TextToNotes;

import javax.sound.midi.MidiChannel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by biGb on 3/4/2016.
 */
public class ChordProgression {
    private ArrayList<Chord> chords;
    private int length;
    private int current;

    /**
     * A constructor to create a chord progression from a set of chords
     * @param chords The chords in the progression, in order
     */
    public ChordProgression(Chord... chords){
        this.chords = new ArrayList<Chord>(Arrays.asList(chords));
        current = -1;
    }

    /**
     * A constructor for creating a chord progression from a list of presets
     * @param i The index of the preset to be used
     */
    public ChordProgression(int i){
        current = -1;
        chords = new ArrayList<Chord>();
        switch(i){
            case 0:{
                chords.add(ChordManager.BuildChord(0, false, false, 0));
                chords.add(ChordManager.BuildChord(5, true, false, 7));
                chords.add(ChordManager.BuildChord(1, true, false, 7));
                chords.add(ChordManager.BuildChord(4, false, false, 7));
                break;
            }
            case 1:{
                chords.add(ChordManager.BuildChord(0, false, false, 0));
                chords.add(ChordManager.BuildChord(3, false, false, 7));
                chords.add(ChordManager.BuildChord(4, false, false, 7));
                break;
            }
            case 4:{
                chords.add(ChordManager.BuildChord(0, false, false, 0));
                chords.add(ChordManager.BuildChord(4, false, false, 0));
                chords.add(ChordManager.BuildChord(5, true, false, 0));
                chords.add(ChordManager.BuildChord(3, false, false, 0));
                break;
            }
        }
        length = chords.size();
    }

    public void play(MidiChannel m){
        for (int i = 0; i < chords.size(); i++){
            chords.get(i).play(m);
        }
    }

    public void stop(MidiChannel m){
        for (int i = 0; i < chords.size(); i++){
            chords.get(i).stop(m);
        }
        current = -1;
    }

    public void playNext(MidiChannel m){
        if (current != -1)
            chords.get((current)%length).stop(m);
        chords.get((++current)%length).play(m);
    }
}
