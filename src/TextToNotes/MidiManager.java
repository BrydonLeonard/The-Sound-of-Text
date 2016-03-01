package TextToNotes;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.HashMap;

/**
 * Created by biGb on 3/1/2016.
 */
public class MidiManager {
    private int defaultIntensity;
    private int defaultLengthOn;
    private int defaultLengthOff;
    private MidiChannel[] mc;
    private Synthesizer synth;
    private HashMap<Integer, Integer> majorNotes;
    private HashMap<Integer, Integer> minorNotes;

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
     * Populates a hashmap that determintes where the nth note is, relative to the 1st note.
     */
    private void setupNotes(){
        majorNotes = new HashMap<>();
        minorNotes = new HashMap<>();
        int notes = 1;
        majorNotes.put(0,0);
        minorNotes.put(0,0);
        while (notes < 100){
            Integer degree = notes % 7;
            Integer octave = Math.floorDiv(notes, 7);
            if (degree.equals(3) || degree.equals(0))
                majorNotes.put(notes, majorNotes.get(notes-1) + 1);
            else majorNotes.put(notes, majorNotes.get(notes-1) + 2);
            if (degree.equals(2) || degree.equals(5) || degree.equals(0))
                minorNotes.put(notes, minorNotes.get(notes-1) + 1);
            else if (degree.equals(5))
                minorNotes.put(notes, minorNotes.get(notes-1) + 3);
            else minorNotes.put(notes, minorNotes.get(notes-1) + 2);
            notes++;
        }
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
        this.setupNotes();
}

    /**
     * Constructor to set default parameters to preset values. Also opens the synth, which must be closed once it is no longer being used.
     * @throws MidiUnavailableException Thrown when MidiSystem is unavailable
     */
    public MidiManager() throws MidiUnavailableException {
        this.defaultIntensity = 60;
        this.defaultLengthOn = 200;
        this.defaultLengthOff = 200;
        initSynth();
        this.setupNotes();
    }

    /**
     * An iterator for the notes of an ascending major scale (1 octave)
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMajorUp(int start, Interator method){
        method.run(start);
        method.run(start+2);
        method.run(start+4);
        method.run(start+5);
        method.run(start+7);
        method.run(start+9);
        method.run(start+11);
        method.run(start+12);
    }

    /**
     * An iterator for the notes of a descending major scale (1 octave)
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMajorDown(int start, Interator method){
        method.run(start);
        method.run(start-1);
        method.run(start-3);
        method.run(start-5);
        method.run(start-7);
        method.run(start-8);
        method.run(start-10);
        method.run(start-12);
    }

    /**
     * An iterator for the notes of an ascending and descending major scale, with a repeating top note
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMajorUpDown(int start, Interator method){
        this.forMajorUp(start, method);
        this.forMajorDown(start+12, method);
    }

    /**
     * An iterator for the notes of an ascending harmonic minor scale
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMinorUp(int start, Interator method){
        method.run(start);
        method.run(start+2);
        method.run(start+3);
        method.run(start+5);
        method.run(start+7);
        method.run(start+8);
        method.run(start+11);
        method.run(start+12);
    }

    /**
     * An iterator for the notes of a descending harmonic minor scale
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMinorDown(int start, Interator method){
        method.run(start);
        method.run(start-1);
        method.run(start-4);
        method.run(start-5);
        method.run(start-7);
        method.run(start-9);
        method.run(start-10);
        method.run(start-12);
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
            this.play(majorNotes.get(i)+41);
        }
    }

    public void playMinor(String notes){
        int[] n = fixStringToChar(notes);
        for (int i : n){
            this.play(minorNotes.get(i) + 41);
        }
    }

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
