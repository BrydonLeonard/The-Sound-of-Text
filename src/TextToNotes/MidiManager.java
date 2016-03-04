package TextToNotes;

import javax.sound.midi.*;

import java.util.ArrayList;
import java.util.Random;

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
    private void initSynth(int instrument) throws MidiUnavailableException {
        synth = MidiSystem.getSynthesizer();
        synth.open();
        mc = synth.getChannels();
        mc[0].programChange(0,instrument);
        mc[1].programChange(0,instrument);
    }

    /**
     * Constructor taking parameters to set properties of the midi manager. Also opens the synth, which must be closed once it is no longer being used.
     * @param defaultIntensity Default intensity at which notes are played
     * @param defaultLengthOn Default length that notes are played for
     * @param defaultLengthOff Default time between notes
     * @param instrument The instrument to select from soundbank 0
     * @throws MidiUnavailableException Thrown when MidiSystem is unavailable
     */
    public MidiManager(int defaultIntensity, int defaultLengthOn, int defaultLengthOff, int instrument) throws MidiUnavailableException {
        this.defaultIntensity = defaultIntensity;
        this.defaultLengthOn = defaultLengthOn;
        this.defaultLengthOff = defaultLengthOff;
        this.initSynth(instrument);
}

    /**
     * Constructor to set default parameters to preset values. Also opens the synth, which must be closed once it is no longer being used.
     * @throws MidiUnavailableException Thrown when MidiSystem is unavailable
     */
    public MidiManager() throws MidiUnavailableException {
        this.defaultIntensity = 60;
        this.defaultLengthOn = 150;
        this.defaultLengthOff = 20;
        initSynth(1);
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

    public void play(int... i){
        try{
            for (int j : i){
                mc[0].noteOn(j, defaultIntensity);
            }
            Thread.sleep(defaultLengthOn);
            for (int j : i){
                mc[0].noteOff(j, defaultIntensity);
            }
            Thread.sleep(defaultLengthOff);
        }catch(InterruptedException e){
            System.out.println("Something went wrong while playing some notes");
        }
    }

    /**
     * Takes a string and plays it, using a major scale as a base
     * @param notes The string
     */
    public void playMajor(String notes){
        Integer[] n = fixStringToChar(notes);
        playMajor(n);
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
        Integer[] n = fixStringToChar(notes);
        playMinor(n);
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
        Integer[] noteArr  = fixStringToChar(notes);
        for (int i = 0; i * 8 < noteArr.length; i++){
            MajorChord majChord = (MajorChord)ChordManager.BuildChord(noteArr[i * 8], false, false, 0);
            majChord.play(mc[1]);

            ArrayList<Integer> partNoteArr = new ArrayList<>();
            for (int j = 0; j < 8 && j + i * 8 < noteArr.length; j++)
                partNoteArr.add(noteArr[j + i*8]);
            playMajor(partNoteArr.toArray(new Integer[partNoteArr.size()]));

            majChord.stop(mc[1]);
        }
    }

    /**
     * Takes a string and plays it, using a major scale as a base and playing chords on every 8th note
     * @param notes The string
     * @throws InterruptedException
     */
    public void playMinorChords(String notes) {
        Integer[] noteArr  = fixStringToChar(notes);
        for (int i = 0; i * 8 < noteArr.length; i++){
            MinorChord minChord = (MinorChord)ChordManager.BuildChord(noteArr[i * 8], true, true, 0);
            minChord.play(mc[1]);

            ArrayList<Integer> partNoteArr = new ArrayList<>();
            for (int j = 0; j < 8 && j + i * 8 < noteArr.length; j++)
                partNoteArr.add(noteArr[j + i*8]);
            playMinor(partNoteArr.toArray(new Integer[partNoteArr.size()]));

            minChord.stop(mc[1]);
        }
    }

    /**
     * Splits the string in 2 and plays half on each hand
     * @param notes The string
     */
    public void playMajorTwoHands(String notes){
        String notes1 = notes.substring(0,notes.length()/2);
        String notes2 = notes.substring(notes.length()/2, notes.length());
        Integer[] readyNotes1 = fixStringToChar(notes1);
        Integer[] readyNotes2 = fixStringToChar(notes2);
        for (int i = 0; i < readyNotes1.length && i < readyNotes2.length; i++) {
            play(MusicLogic.pitchCorrect(MusicLogic.getMajorNote(readyNotes1[i])),
                    MusicLogic.pitchCorrect(MusicLogic.getMajorNote(readyNotes2[i])));
        }
    }

    public void playMajorWithProgression(String notes){
        Integer[] noteArr = fixStringToChar(notes);
        ChordProgression progression = new ChordProgression(4);
        for (int i = 0; i < noteArr.length; i++){
            if (i % Math.min(Math.floorDiv(noteArr.length,4), 8) == 0){
                progression.playNext(mc[1]);
            }
            play(MusicLogic.pitchCorrect(MusicLogic.getMajorNote(noteArr[i])));
        }
        progression.stop(mc[1]);
    }

    /*//Kinda pointless, just playing with chords
    public void playRandomChords(String notes) throws InterruptedException {
        Random rand = new Random();
        int[] noteArr  = fixStringToChar(notes);
        for (int i = 0; i< noteArr.length; i++){
            Chord chord;
            if (rand.nextBoolean())
                chord = new MajorChord(noteArr[i], mc[0]);
            else chord = new MajorChord(noteArr[i]+4, mc[0]);

            chord.play();
            Thread.sleep(defaultLengthOn);
            chord.stop();
            Thread.sleep(defaultLengthOff);
        }
    }*/

    /**
     * Takes a string and formats it as a character array that can be used by the playMajor and playMinor methods
     * @param notes The string
     * @return The formatted character array
     */
    private Integer[] fixStringToChar(String notes){
         char[] c = notes.toLowerCase()
                .replaceAll("[^a-z]", "")
                 .toCharArray();
        Integer[] n = new Integer[c.length];
        for (int i = 0; i < c.length; i++)
            n[i] = ((int)c[i])-97;
        return n;
    }

    /**
     * Changes the active instrument
     * @param newInstrument The index of the new instrument
     */
    public void switchInstrument(int newInstrument){
        mc[0].programChange(0, newInstrument);
        mc[1].programChange(0, newInstrument);
    }

    /**
     * Closes the MidiManager
     */
    public void close(){
        synth.close();
    }
}
