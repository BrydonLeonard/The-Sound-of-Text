package TextToNotes;

import javax.sound.midi.MidiChannel;

/**
 * Created by biGb on 3/4/2016.
 */
public class ChordManager {
    public static Chord BuildChord(int tonic, boolean minor, boolean minorProgression, int chordType){
        if (minorProgression)
            tonic = MusicLogic.getMinorNote(tonic%7);
        else tonic = MusicLogic.getMajorNote(tonic%7);
        if (minor){
            if (chordType == 7)
                return new Min7Chord(tonic);
            else return new MinorChord(tonic);
        }else{
            if (chordType == 7)
                return new Maj7Chord(tonic);
            else return new MajorChord(tonic);
        }
    }

}
