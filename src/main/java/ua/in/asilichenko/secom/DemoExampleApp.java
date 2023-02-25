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

package ua.in.asilichenko.secom;

import ua.in.asilichenko.secom.domain.KeyHalves;
import ua.in.asilichenko.secom.service.DecipherService;
import ua.in.asilichenko.secom.service.EncipherService;

import static ua.in.asilichenko.secom.utils.KeyUtil.createKeyHalves;
import static ua.in.asilichenko.secom.utils.TextUtil.*;

/**
 * Simple console application that demonstrates an example of enciphering and deciphering.
 *
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class DemoExampleApp {

    public static void main(String[] args) {
        String plain = "An integer value representing the character code corresponding to a character";
        String keyPhrase = "Secure Communication Key";

        plain = preparePlainText(plain);
        if (plain.isEmpty()) {
            System.err.println("Please enter a text to process.");
            return;
        }
        keyPhrase = prepareKey(keyPhrase);
        if (keyPhrase.length() < 20) {
            System.err.println("Please enter a phrase with at least 20 letters.");
            return;
        }

        final KeyHalves keyHalves = createKeyHalves(keyPhrase);

        final EncipherService encipherService = new EncipherService();
        final String secret = encipherService.encipher(plain.toCharArray(), keyHalves);
        System.out.println("Enciphered: " + secret);
        final String expected = "2495185254228955195942451926115132958219388355343858229923863729894055352396225198525939583391328153311991826350525";
        System.out.println("Was enciphered as expected: " + expected.equalsIgnoreCase(secret));
        System.out.println(makeGroups(secret));

        final int[] digits = readDigits(secret);

        /*
        Expected:
        24951 85254 22895 51959 42451 92611 51329 58219 38835 53438
        58229 92386 37298 94055 35239 62251 98525 93958 33913 28153
        31199 18263 50525
         */

        final DecipherService decipherService = new DecipherService();
        final char[] deciphered = decipherService.decipher(digits, keyHalves);
        System.out.println();
        System.out.println("Deciphered: " + new String(deciphered));
        System.out.println("Is equal to plain: " + plain.equalsIgnoreCase(new String(deciphered)));
    }
}
