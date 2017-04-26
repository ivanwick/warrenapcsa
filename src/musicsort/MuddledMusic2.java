package musicsort;

import java.io.*;
import java.util.*;

public class MuddledMusic2 {
    public static class Song{
        //Instance variables for the name and artist
        public String name;
        public String artist;
        //Constructor
        public Song(String name, String artist){
            this.name = name;
            this.artist = artist;
        }

        //Remove the "The "
        public String effectiveArtist(){
            return artist.replace("The ", "");
        }

        //String that would show the same as the input file(IE: "Hello - Adele")
        public String toString(){
            return name + " - " + artist;
        }
    }
    //Comparator for Artist. Used to sort artists alphabetically
    public static class ArtistComparator implements Comparator<Song>{
        public int compare(Song a, Song b){
            return a.effectiveArtist().compareTo(b.effectiveArtist());
        }
    }
    //Comparator for Songs. Uses to sort songs alphabetically
    public static class SongComparator implements Comparator<Song>{
        public int compare(Song a, Song b){
            return a.name.compareTo(b.name);
        }
    }
    //Used for debugging
    public static void printPairs(Song[] input){
        for(Song s : input){
            System.out.println(s.toString());
        }
    }
    //Sort with certain indexes
    public static Song[] sortBySongNameWithIndexes(Song[] arr, int a, int b){
        Song[] tempArray = new Song[(b - a) + 1];
        System.arraycopy(arr, a, tempArray, 0, ((b-a) + 1));
        Arrays.sort(tempArray, new SongComparator());
        return tempArray;
    }

    public static void main(String[] args) throws FileNotFoundException,IOException{
        //Path to text file
        String filePath = "Prob08.in.txt";
        //new bufferedreader
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        //Read first line
        String testCasesStr = br.readLine();
        //Parse to int
        int testCases = Integer.parseInt(testCasesStr);
        //loop through test cases
        for(int i = 0; i < testCases; i++){
            //Read amount of song-artist pairs
            String songArtistPairsStr = br.readLine();
            //Parse to int
            int songArtistPairs = Integer.parseInt(songArtistPairsStr);
            //Create Song objects array
            Song[] songObjectsOrig = new Song[songArtistPairs];
            //loop through song artist pairs
            for(int j = 0; j < songArtistPairs; j++){
                //Read line
                String line = br.readLine();
                //Split into song and artist
                String[] lineSplit = line.split(" - ");
                //make them seperate strings so it is easier
                String song = lineSplit[0];
                String artist = lineSplit[1];
                //Create song instance
                Song curSong = new Song(song, artist);
                //Store into array
                songObjectsOrig[j] = curSong;
            }
            //Now we have all of the data, lets sort by artists
            //But first lets copy the array into a copy
            Song[] songObjects = new Song[songArtistPairs];
            System.arraycopy(songObjectsOrig, 0, songObjects, 0, songObjectsOrig.length);
            //Now copy is made, sort copy
            Arrays.sort(songObjects, new ArtistComparator());
            //debug
            //printPairs(songObjects);
            //Now that we have sorted by artists, we much sort the names of the songs. We should only do this for songs with the same artist
            //To do this, we find the amount of artists that have multiple songs. Then make a for loop with the conditional of i < (multipleSongArtistCount)
            //Then we fill it with the songs they have.
            sortBySongNameWithIndexes(songObjects, 1, 2);
        }
    }

    public static void main2(String[] args) {
        System.out.println("HELLO");
    }
}