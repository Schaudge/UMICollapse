package util;

import java.util.BitSet;

import fastq.Read;

public class Utils{
    // fast Hamming distance by using pairwise equidistant encodings for each nucleotide
    public static int umiDist(BitSet a, BitSet b){
        BitSet c = (BitSet)a.clone();
        a.xor(b);
        // divide by the pairwise Hamming distance in the encoding
        return c.cardinality() / Read.ENCODING_DIST;
    }

    public static boolean charEquals(BitSet a, int idx, int b){
        for(int i = 0; i < Read.ENCODING_LENGTH; i++){
            if(a.get(idx * Read.ENCODING_LENGTH + i) != ((b & (1 << i)) != 0))
                return false;
        }

        return true;
    }

    public static BitSet charSet(BitSet a, int idx, int b){
        for(int i = 0; i < Read.ENCODING_LENGTH; i++){
            a.set(idx * Read.ENCODING_LENGTH + i, ((b & (1 << i)) != 0));
        }

        return a;
    }

    public static BitSet toBitSet(String s){
        BitSet res = new BitSet();

        for(int i = 0; i < s.length(); i++){
            charSet(res, i, Read.ENCODING_MAP.get(s.charAt(i)));
        }

        return res;
    }

    // converts quality string to byte array, using the Phred+33 format
    public static byte[] toPhred33ByteArray(String q){
        byte[] res = new byte[q.length()];

        for(int i = 0; i < q.length(); i++){
            res[i] = (byte)(q.charAt(i) - '!');
        }

        return res;
    }
}