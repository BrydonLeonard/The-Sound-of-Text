package TextToNotes;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by biGb on 3/1/2016.
 */
public class MidiManager {
    private int defaultIntensity;
    private int defaultLengthOn;
    private int defaultLengthOff;
    private MidiChannel[] mc;
    private Synthesizer synth;
    private Integer[] majorNotes = {0,2,4,5,7,9,11,12};
    private Integer[] minorNotes = {0,2,3,5,7,8,11,12};

    /**
     * Gets the note for the midi player from its position in a major scale
     * @param n Position in major scale
     * @return Midi pitch
     */
    private int getMajorNote(int n){
        return majorNotes[n % 7] + (Math.floorDiv(n, 7) * 12);
    }

    /**
     * Gets the note for the midi player from its position in a minor scale
     * @param n Position in minor scale
     * @return Midi pitch
     */
    private int getMinorNote(int n){
        return minorNotes[n % 7] + (Math.floorDiv(n, 7) * 12);
    }

    /**
     * Initialises the synth and MidiChannels
     * @throws MidiUnavailableException
     */
    private void initSynth() throws MidiUnavailableException {
        synth = MidiSystem.getSynthesizer();
        synth.open();
        mc = synth.getChannels();
    }
    /**
     * Constructor taking parameters to set properties of the midi manager. Also opens the synth, which must be closed once it is no longer being used.
     * @param defaultIntensity Default intensity at which notes are played
     * @param defaultLengthOn Default length that notes are played for
     * @param defaultLengthOff Default time between notes
     * @throws MidiUnavailableException Thrown when MidiSystem is unavailable
     */
    public MidiManager(int defaultIntensity, int defaultLengthOn, int defaultLengthOff) throws MidiUnavailableException {
        this.defaultIntensity = defaultIntensity;
        this.defaultLengthOn = defaultLengthOn;
        this.defaultLengthOff = defaultLengthOff;
        this.initSynth();
}

    /**
     * Constructor to set default parameters to preset values. Also opens the synth, which must be closed once it is no longer being used.
     * @throws MidiUnavailableException Thrown when MidiSystem is unavailable
     */
    public MidiManager() throws MidiUnavailableException {
        this.defaultIntensity = 60;
        this.defaultLengthOn = 150;
        this.defaultLengthOff = 20;
        initSynth();
    }

    /**
     * An iterator for the notes of an ascending major scale (1 octave)
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMajorUp(int start, Interator method){
        for (int i = 0; i < 8; i++){
            method.run(start + getMajorNote(i));
        }
    }

    /**
     * An iterator for the notes of a descending major scale (1 octave)
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMajorDown(int start, Interator method){
        for (int i = 7; i >= 0; i--){
            method.run(start - (12 - getMajorNote(i)));
        }
    }

    /**
     * An iterator for the notes of an ascending and descending major scale, with a repeating top note
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMajorUpDown(int start, Interator method){
        this.forMajorUp(start, method);
        this.forMajorDown(start + 12, method);
    }

    /**
     * An iterator for the notes of an ascending harmonic minor scale
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMinorUp(int start, Interator method){
        for (int i = 0; i < 8; i++){
            method.run(start + getMinorNote(i));
        }
    }

    /**
     * An iterator for the notes of a descending harmonic minor scale
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMinorDown(int start, Interator method){
        for (int i = 7; i >= 0; i--){
            method.run(start - (12 - getMinorNote(i)));
        }
    }

    /**
     * An iterator for the notes of an ascending and descending harmonic minor scale
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMinorUpDown(int start, Interator method){
        this.forMinorUp(start, method);
        this.forMinorDown(start+12, method);
    }

    /**
     * Plays a note with the corresponding midi pitch to i for the default length
     * @param i Pitch value
     */
    public void play(int i){
        try{
            mc[0].noteOn(i, defaultIntensity);
            Thread.sleep(defaultLengthOn);
            mc[0].noteOff(i);
            Thread.sleep(defaultLengthOff);
        } catch(InterruptedException e){
            System.out.println("Something went wrong while playing a note");
        }
    }

    /**
     * Takes a string and plays it, using a major scale as a base
     * @param notes The string
     */
    public void playMajor(String notes){
        int[] n = fixStringToChar(notes);
        for (int i : n){
            this.play(getMajorNote(i)+41);
        }
    }

    /**
     * Takes a string and plays it, using a minor scale as a base
     * @param notes The string
     */
    public void playMinor(String notes){
        int[] n = fixStringToChar(notes);
        for (int i : n){
            this.play(getMinorNote(i) + 41);
        }
    }

    /**
     * Takes a string and formats it as a character array that can be used by the playMajor and playMinor methods
     * @param notes The string
     * @return The formatted character array
     */
    private int[] fixStringToChar(String notes){
         char[] c = notes.toLowerCase()
                .replaceAll("[^a-z]", "")
                 .toCharArray();
        int[] n = new int[c.length];
        for (int i = 0; i < c.length; i++)
            n[i] = ((int)c[i])-97;
        return n;
    }

    /**
     * Closes the MidiManager
     */
    public void close(){
        synth.close();
    }
}
