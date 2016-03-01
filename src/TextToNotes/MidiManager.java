package TextToNotes;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import java.util.ArrayList;

/**
 * Created by biGb on 3/1/2016.
 */
public class MidiManager {
    private int defaultIntensity;
    private int defaultLengthOn;
    private int defaultLengthOff;
    private MidiChannel[] mc;
    private Synthesizer synth;

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
            method.run(start + MusicLogic.getMajorNote(i));
        }
    }

    /**
     * An iterator for the notes of a descending major scale (1 octave)
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMajorDown(int start, Interator method){
        for (int i = 7; i >= 0; i--){
            method.run(start - (12 - MusicLogic.getMajorNote(i)));
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
            method.run(start + MusicLogic.getMinorNote(i));
        }
    }

    /**
     * An iterator for the notes of a descending harmonic minor scale
     * @param start Starting note
     * @param method A method to run on each note value
     */
    public void forMinorDown(int start, Interator method){
        for (int i = 7; i >= 0; i--){
            method.run(start - (12 - MusicLogic.getMinorNote(i)));
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
        playMajor(notes);
    }

    /**
     * Plays the array of notes, which are already correct and ready to play, in a major key
     * @param n The notes
     */
    private void playMajor(Integer[] n){
        for (int i : n){
            this.play(MusicLogic.pitchCorrect(MusicLogic.getMajorNote(i)));
        }
    }

    /**
     * Takes a string and plays it, using a minor scale as a base
     * @param notes The string
     */
    public void playMinor(String notes){
        int[] n = fixStringToChar(notes);
    }

    /**
     * Plays the array of notes, which are already correct and ready to play, in a minor key
     * @param n The notes
     */
    private void playMinor(Integer[] n){
        for (int i : n){
            this.play(MusicLogic.pitchCorrect(MusicLogic.getMinorNote(i)));
        }

    }

    /**
     * Takes a string and plays it, using a major scale as a base and playing chords on every 8th note
     * @param notes The string
     * @throws InterruptedException
     */
    public void playMajorChords(String notes) {
        int[] noteArr  = fixStringToChar(notes);
        for (int i = 0; i * 8 < noteArr.length; i++){
            MajorChord majChord = new MajorChord(noteArr[i * 8], mc[1]);
            majChord.play();

            ArrayList<Integer> partNoteArr = new ArrayList<>();
            for (int j = 0; j < 8 && j + i * 8 < noteArr.length; j++)
                partNoteArr.add(noteArr[j + i*8]);
            playMajor(partNoteArr.toArray(new Integer[partNoteArr.size()]));

            majChord.stop();
        }
    }

    /**
     * Takes a string and plays it, using a major scale as a base and playing chords on every 8th note
     * @param notes The string
     * @throws InterruptedException
     */
    public void playMinorChords(String notes) {
        int[] noteArr  = fixStringToChar(notes);
        for (int i = 0; i * 8 < noteArr.length; i++){
            MinorChord minChord = new MinorChord(noteArr[i * 8], mc[1]);
            minChord.play();

            ArrayList<Integer> partNoteArr = new ArrayList<>();
            for (int j = 0; j < 8 && j + i * 8 < noteArr.length; j++)
                partNoteArr.add(noteArr[j + i*8]);
            playMinor(partNoteArr.toArray(new Integer[partNoteArr.size()]));

            minChord.stop();
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
