/*
 * Copyright (c) 2023 Oleksii Sylichenko.
 *
 * Licensed under the LGPL, Version 3.0 or later (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.in.asilichenko.secom.service.dataprovider.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import ua.in.asilichenko.secom.domain.CheckerBoardCode;
import ua.in.asilichenko.secom.domain.DisruptMask;
import ua.in.asilichenko.secom.domain.KeyHalves;

public interface Data2 {

    String keyPhrase = "SECURE COMMUNICATION KEY";
    String plain = "AN INTEGER VALUE REPRESENTING THE CHARACTER CODE CORRESPONDING TO A CHARACTER";
    String deciphered = plain;
    String enciphered = "2495185254228955195942451926115132958219388355343858229923863729894055352396225198525939583391328153311991826350525";

    KeyHalves KEY_PHRASE_HALVES = new KeyHalves(new int[]{9, 3, 1, 0, 8, 4, 2, 7, 5, 6}, new int[]{0, 6, 3, 2, 1, 9, 4, 8, 7, 5});
    int[] checkerBoardKey = new int[]{1, 0, 9, 2, 4, 5, 3, 6, 7, 8};
    int[] columnarKey1 = new int[]{8, 1, 0, 6, 2, 6, 7, 0, 4, 9, 9, 0, 0, 6};
    int[] columnarKey2 = new int[]{8, 1, 3, 4, 5, 4, 9, 0, 8, 6};

    /**
     * -| 1 2 3 4 5 6 7 8 9 0 <br/>
     * -+-------------------- <br/>
     * -| E S # T O # N I # A <br/>
     * -| B C D F G H J K L M <br/>
     * -| P Q R U V W X Y Z * <br/>
     * -| 1 2 3 4 5 6 7 8 9 0 <br/>
     * -+-------------------- <br/>
     * -| 1 0 9 2 4 5 3 6 7 8 <br/>
     * -+-------------------- <br/>
     * -| E S # T O # N I # A <br/>
     * 9| D F G H J K L M b C - B is under index 9 because first blank is under key 9 <br/>
     * 5| X Y Z * p Q R U V W - P is under index 5 because second blank is under key 5 <br/>
     * 7| 5 6 7 8 9 0 1 2 3 4 - 1 is under index 7 because third blank is under key 7
     */
    static BiMap<CheckerBoardCode, Character> checkerBoard() {
        final BiMap<CheckerBoardCode, Character> retval = HashBiMap.create();

        retval.put(new CheckerBoardCode(null, 1), 'E');
        retval.put(new CheckerBoardCode(null, 0), 'S');
        retval.put(new CheckerBoardCode(null, 2), 'T');
        retval.put(new CheckerBoardCode(null, 4), 'O');
        retval.put(new CheckerBoardCode(null, 3), 'N');
        retval.put(new CheckerBoardCode(null, 6), 'I');
        retval.put(new CheckerBoardCode(null, 8), 'A');

        retval.put(new CheckerBoardCode(9, 7), 'B');
        retval.put(new CheckerBoardCode(9, 8), 'C');
        retval.put(new CheckerBoardCode(9, 1), 'D');
        retval.put(new CheckerBoardCode(9, 0), 'F');
        retval.put(new CheckerBoardCode(9, 9), 'G');
        retval.put(new CheckerBoardCode(9, 2), 'H');
        retval.put(new CheckerBoardCode(9, 4), 'J');
        retval.put(new CheckerBoardCode(9, 5), 'K');
        retval.put(new CheckerBoardCode(9, 3), 'L');
        retval.put(new CheckerBoardCode(9, 6), 'M');

        retval.put(new CheckerBoardCode(5, 4), 'P');
        retval.put(new CheckerBoardCode(5, 5), 'Q');
        retval.put(new CheckerBoardCode(5, 3), 'R');
        retval.put(new CheckerBoardCode(5, 6), 'U');
        retval.put(new CheckerBoardCode(5, 7), 'V');
        retval.put(new CheckerBoardCode(5, 8), 'W');
        retval.put(new CheckerBoardCode(5, 1), 'X');
        retval.put(new CheckerBoardCode(5, 0), 'Y');
        retval.put(new CheckerBoardCode(5, 9), 'Z');
        retval.put(new CheckerBoardCode(5, 2), ' ');

        retval.put(new CheckerBoardCode(7, 3), '1');
        retval.put(new CheckerBoardCode(7, 6), '2');
        retval.put(new CheckerBoardCode(7, 7), '3');
        retval.put(new CheckerBoardCode(7, 8), '4');
        retval.put(new CheckerBoardCode(7, 1), '5');
        retval.put(new CheckerBoardCode(7, 0), '6');
        retval.put(new CheckerBoardCode(7, 9), '7');
        retval.put(new CheckerBoardCode(7, 2), '8');
        retval.put(new CheckerBoardCode(7, 4), '9');
        retval.put(new CheckerBoardCode(7, 5), '0');

        return retval;
    }

    String checkerBoardEncoded = "8352632199153525789356152531545310132639952292152989285389821535298491152984535310544391639952245285298928538982153";

    String transposition1 = "3542953556911186891282555283229193305543951531422251235928252814819569932312328128575982923163953985598404933959558";

    int[][] reverseColumnar1 = new int[][]{
            /*
             8, 1, 0, 6, 2, 6, 7, 0, 4, 9, 9, 0, 0, 6 - key
             8  1  11 4  2  5  7  12 3  9  10 13 14 6 - order */
            {8, 3, 5, 2, 6, 3, 2, 1, 9, 9, 1, 5, 3, 5},
            {2, 5, 7, 8, 9, 3, 5, 6, 1, 5, 2, 5, 3, 1},
            {5, 4, 5, 3, 1, 0, 1, 3, 2, 6, 3, 9, 9, 5},
            {2, 2, 9, 2, 1, 5, 2, 9, 8, 9, 2, 8, 5, 3},
            {8, 9, 8, 2, 1, 5, 3, 5, 2, 9, 8, 4, 9, 1},
            {1, 5, 2, 9, 8, 4, 5, 3, 5, 3, 1, 0, 5, 4},
            {4, 3, 9, 1, 6, 3, 9, 9, 5, 2, 2, 4, 5, 2},
            {8, 5, 2, 9, 8, 9, 2, 8, 5, 3, 8, 9, 8, 2},
            {1, 5, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
    };

    DisruptMask disruptMask = new DisruptMask(new int[][]{
            /*
             8, 1, 3, 4, 5, 4, 9, 0, 8, 6 - key
             7  1  2  3  5  4  9  10 8  6 - order */
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 1+9
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, // 2+8
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1}, // 3+7
            {0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, // 4+6
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, // 5+5
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1}, // 6+4
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, // 7+3
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1}, // 8+2
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, // 9+1
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 10+0
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, // 2+8
            {0, 0, 0, 1, 1, -1, -1, -1, -1, -1} // 3+2
    }, 60); // 1+2+3+4+5+6+7+8+9+10+2+3=60

    int[][] disrupt = new int[][]{
            /*
             8, 1, 3, 4, 5, 4, 9, 0, 8, 6 - key
             7  1  2  3  5  4  9  10 8  6 - order */
            {3, 2, 8, 1, 4, 8, 1, 9, 5, 6}, // 1+9
            {5, 4, 9, 9, 3, 2, 3, 1, 2, 3}, // 2+8
            {2, 9, 5, 2, 8, 1, 2, 8, 5, 7}, // 3+7
            {3, 5, 5, 6, 5, 9, 8, 2, 9, 2}, // 4+6
            {9, 1, 1, 1, 8, 3, 1, 6, 3, 9}, // 5+5
            {6, 8, 9, 1, 2, 8, 5, 3, 9, 8}, // 6+4
            {2, 5, 5, 5, 2, 8, 3, 5, 5, 9}, // 7+3
            {2, 2, 9, 1, 9, 3, 3, 0, 8, 4}, // 8+2
            {5, 5, 4, 3, 9, 5, 1, 5, 3, 0}, // 9+1
            {1, 4, 2, 2, 2, 5, 1, 2, 3, 5}, // 10+0
            {9, 2, 4, 9, 3, 3, 9, 5, 9, 5}, // 2+8
            {8, 2, 5, 5, 8, -1, -1, -1, -1, -1} // 3+2
    };
}
