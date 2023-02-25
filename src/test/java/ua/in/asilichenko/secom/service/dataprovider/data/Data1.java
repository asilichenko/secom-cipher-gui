/*
 * Copyright (c) 2023 Oleksii Sylichenko.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ua.in.asilichenko.secom.service.dataprovider.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import ua.in.asilichenko.secom.domain.CheckerBoardCode;
import ua.in.asilichenko.secom.domain.DisruptMask;
import ua.in.asilichenko.secom.domain.KeyHalves;

public interface Data1 {

    String keyPhrase = "MAKE NEW FRIENDS BUT KEEP THE OLD";
    String plain = "RV TOMORROW AT 1400PM TO COMPLETE TRANSACTION USE DEADDROP AS USUAL";
    String deciphered = plain + "O"; // O at the ending because of trailing 0
    String enciphered = "777193862200032042396003829683146080607178016736060606463536069686740369681890014021906662606660863160549";

    KeyHalves KEY_PHRASE_HALVES = new KeyHalves(new int[]{7, 1, 6, 2, 8, 3, 0, 4, 9, 5}, new int[]{3, 7, 2, 8, 1, 0, 9, 6, 4, 5});
    int[] checkerBoardKey = new int[]{8, 1, 3, 9, 0, 6, 5, 4, 2, 7};
    int[] columnarKey1 = new int[]{8, 4, 8, 9, 8, 2, 4, 5, 8, 9, 8, 2};
    int[] columnarKey2 = new int[]{0, 9, 7, 9, 2, 8, 5, 5, 8, 7, 8};

    /**
     * -| 1 2 3 4 5 6 7 8 9 0 - index <br/>
     * --+-------------------- - default <br/>
     * -| E S # T O # N I # A - the most frequent letters <br/>
     * -| B C D F G H J K L M <br/>
     * -| P Q R U V W X Y Z * <br/>
     * -| 1 2 3 4 5 6 7 8 9 0 <br/>
     * --+-------------------- <br/>
     * -| 8 1 3 9 0 6 5 4 2 7 - key <br/>
     * -+-------------------- - result <br/>
     * -| E S # T O # N I # A - as is <br/>
     * 3| L M b C D F G H J K - first blank is under key's 3 - B starts under index's 3 <br/>
     * 6| W X Y Z * p Q R U V - second blank is under key's 6 - P starts under index's 6 <br/>
     * 2| 0 1 2 3 4 5 6 7 8 9 - third blank is under key's 2 - 1 starts under index's 2 <br/>
     */
    static BiMap<CheckerBoardCode, Character> checkerBoard() {
        final BiMap<CheckerBoardCode, Character> retval = HashBiMap.create();

        retval.put(new CheckerBoardCode(null, 8), 'E');
        retval.put(new CheckerBoardCode(null, 1), 'S');
        retval.put(new CheckerBoardCode(null, 9), 'T');
        retval.put(new CheckerBoardCode(null, 0), 'O');
        retval.put(new CheckerBoardCode(null, 5), 'N');
        retval.put(new CheckerBoardCode(null, 4), 'I');
        retval.put(new CheckerBoardCode(null, 7), 'A');
        //
        retval.put(new CheckerBoardCode(3, 8), 'L'); // 1
        retval.put(new CheckerBoardCode(3, 1), 'M'); // 2
        retval.put(new CheckerBoardCode(3, 3), 'B'); // # B start from 3
        retval.put(new CheckerBoardCode(3, 9), 'C'); // 4
        retval.put(new CheckerBoardCode(3, 0), 'D'); // 5
        retval.put(new CheckerBoardCode(3, 6), 'F'); // 6
        retval.put(new CheckerBoardCode(3, 5), 'G'); // 7
        retval.put(new CheckerBoardCode(3, 4), 'H'); // 8
        retval.put(new CheckerBoardCode(3, 2), 'J'); // 9
        retval.put(new CheckerBoardCode(3, 7), 'K'); // 10
        //
        retval.put(new CheckerBoardCode(6, 8), 'W'); // 1
        retval.put(new CheckerBoardCode(6, 1), 'X'); // 2
        retval.put(new CheckerBoardCode(6, 3), 'Y'); // 3
        retval.put(new CheckerBoardCode(6, 9), 'Z'); // 4
        retval.put(new CheckerBoardCode(6, 0), ' '); // 5
        retval.put(new CheckerBoardCode(6, 6), 'P'); // # P starts from 6
        retval.put(new CheckerBoardCode(6, 5), 'Q'); // 7
        retval.put(new CheckerBoardCode(6, 4), 'R'); // 8
        retval.put(new CheckerBoardCode(6, 2), 'U'); // 9
        retval.put(new CheckerBoardCode(6, 7), 'V'); // 10
        //
        retval.put(new CheckerBoardCode(2, 8), '0'); // 1
        retval.put(new CheckerBoardCode(2, 1), '1'); // # 1 starts from 2
        retval.put(new CheckerBoardCode(2, 3), '2'); // 3
        retval.put(new CheckerBoardCode(2, 9), '3'); // 4
        retval.put(new CheckerBoardCode(2, 0), '4'); // 5
        retval.put(new CheckerBoardCode(2, 6), '5'); // 6
        retval.put(new CheckerBoardCode(2, 5), '6'); // 7
        retval.put(new CheckerBoardCode(2, 4), '7'); // 8
        retval.put(new CheckerBoardCode(2, 2), '8'); // 9
        retval.put(new CheckerBoardCode(2, 7), '9'); // 10

        return retval;
    }

    String checkerBoardEncoded = "64676090310646406860796021202828663160906039031663889860964751739940560621860308730306406660716062162738";

    String transposition1 = "088089367601676304610311629623640630089008086426652066429878416626990623760957700631400670008360619636631";

    int[][] reverseColumnar1 = new int[][]{
            /*
             8, 4, 8, 9, 8, 2, 4, 5, 8, 9, 8, 2 - key
             6  3  7  11 8  1  4  5  9  12 10 2 - order */
            {6, 4, 6, 7, 6, 0, 9, 0, 3, 1, 0, 6},
            {4, 6, 4, 0, 6, 8, 6, 0, 7, 9, 6, 0},
            {2, 1, 2, 0, 2, 8, 2, 8, 6, 6, 3, 1},
            {6, 0, 9, 0, 6, 0, 3, 9, 0, 3, 1, 6},
            {6, 3, 8, 8, 9, 8, 6, 0, 9, 6, 4, 7},
            {5, 1, 7, 3, 9, 9, 4, 0, 5, 6, 0, 6},
            {2, 1, 8, 6, 0, 3, 0, 8, 7, 3, 0, 3},
            {0, 6, 4, 0, 6, 6, 6, 0, 7, 1, 6, 0},
            {6, 2, 1, 6, 2, 7, 3, 8, 0, -1, -1, -1}
    };

    DisruptMask disruptMask = new DisruptMask(new int[][]{
            /*
             0, 9, 7, 9, 2, 8, 5, 5, 8, 7, 8 - key
             11 9  4  10 1  6  2  3  7  5  8 - order */
            {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1}, // 4+7
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, // 5+6
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, // 6+5
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1}, // 7+4
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, // 8+3
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}, // 9+2
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, // 10+1
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 11+0
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, // 6+5
            {0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1} // 6
    }, 72); // 4+5+6+7+8+9+10+11+6+6=72

    int[][] disrupt = new int[][]{
            /*
             0, 9, 7, 9, 2, 8, 5, 5, 8, 7, 8 - key
             11 9  4  10 1  6  2  3  7  5  8 - order */
            {0, 8, 8, 0, 7, 6, 0, 9, 5, 7, 7}, // 4+7
            {8, 9, 3, 6, 7, 0, 0, 6, 3, 1, 4}, // 5+6
            {6, 0, 1, 6, 7, 6, 0, 0, 6, 7, 0}, // 6+5
            {3, 0, 4, 6, 1, 0, 3, 0, 0, 8, 3}, // 7+4
            {1, 1, 6, 2, 9, 6, 2, 3, 6, 0, 6}, // 8+3
            {6, 4, 0, 6, 3, 0, 0, 8, 9, 1, 9}, // 9+2
            {0, 0, 8, 0, 8, 6, 4, 2, 6, 6, 6}, // 10+1
            {5, 2, 0, 6, 6, 4, 2, 9, 8, 7, 8}, // 11+0
            {4, 1, 6, 6, 2, 6, 3, 6, 6, 3, 1}, // 6+5
            {9, 9, 0, 6, 2, 3, -1, -1, -1, -1, -1} //
    };
}
