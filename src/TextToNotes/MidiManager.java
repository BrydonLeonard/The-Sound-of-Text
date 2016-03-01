package TextToNotes;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

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
     * Constructor taking parameters to set properties of the midi manager
     * @param defaultIntensity Default intensity at which notes are played
     * @param defaultLengthOn Default length that notes are played for
     * @param defaultLengthOff Default time between notes
     * @throws MidiUnavailableException Thrown when MidiSystem is unavailable
     */
    public MidiManager(int defaultIntensity, int defaultLengthOn, int defaultLengthOff) throws MidiUnavailableException {
        this.defaultIntensity = defaultIntensity;
        this.defaultLengthOn = defaultLengthOn;
        this.defaultLengthOff = defaultLengthOff;
        synth = MidiSystem.getSynthesizer();
        synth.open();
        mc = synth.getChannels();
    }

    /**
     * Constructor to set default parameters to preset values
     * @throws MidiUnavailableException Thrown when MidiSystem is unavailable
     */
    public MidiManager() throws MidiUnavailableException {
        this.defaultIntensity = 60;
        this.defaultLengthOn = 200;
        this.defaultLengthOff = 200;
        synth = MidiSystem.getSynthesizer();
        synth.open();
        mc = synth.getChannels();
    }

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

    public void forMajorUpDown(int start, Interator method){
        this.forMajorUp(start, method);
        this.forMajorDown(start+12, method);
    }

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

    public void forMinorUpDown(int start, Interator method){
        this.forMinorUp(start, method);
        this.forMinorDown(start+12, method);
    }

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

    public void close(){
        synth.close();
    }
}
