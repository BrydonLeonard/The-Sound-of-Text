package TextToNotes;

/**
 * Created by biGb on 3/1/2016.
 */
public class MusicLogic {
    private static Integer[] majorNotes = {0,2,4,5,7,9,11,12};
    private static  Integer[] minorNotes = {0,2,3,5,7,8,11,12};
    private static String[] noteFlatNames = {"C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B"};
    private static String[] noteSharpNames = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};

    /**
     * Gets the note for the midi player from its position in a major scale
     * @param n Position in major scale
     * @return Midi pitch
     */
    public static int getMajorNote(int n){
        return majorNotes[n % 7] + (Math.floorDiv(n, 7) * 12);
    }

    /**
     * Gets the note for the midi player from its position in a minor scale
     * @param n Position in minor scale
     * @return Midi pitch
     */
    public static int getMinorNote(int n){
        return minorNotes[n % 7] + (Math.floorDiv(n, 7) * 12);
    }

    /**
     * Adjusts the pitch to the desired range
     * @param i The note to adjust
     * @return The corrected pitch
     */
    public static int pitchCorrect(int i){
        return i + 36;
    }

    /**
     * Gets the name of a note at a specific pitch, with flats
     * @param i The pitch
     * @return Note name
     */
    public static String getFlatNoteName(int i){
        return noteFlatNames[i%12];
    }

    /**
     * Gets the name of a note at a specific pitch, with sharps
     * @param i The pitch
     * @return Note name
     */
    public static String getSharpNoteName(int i){
        return noteSharpNames[i%12];
    }

    public static int affixToMajor(int i){
        if (i > 4)
            return 4;
        if (i > 2)
            return 2;
        return 0;
    }
}
