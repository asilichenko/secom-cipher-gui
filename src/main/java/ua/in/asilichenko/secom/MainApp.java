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

import ua.in.asilichenko.secom.gui.main.FormMain;
import ua.in.asilichenko.secom.gui.main.FormMainImpl;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import static com.google.common.io.Resources.getResource;
import static javax.imageio.ImageIO.read;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Secure Communication Cipher
 *
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 11.02.2023
 */
public class MainApp {

    private static final String TITLE = "SECOM Cipher";
    private static final String ICON_PATH = "icon/icon32.png";
    private static final int WIDTH = 660;
    private static final int HEIGHT = 495;

    public static void main(String[] args) {
        final FormMain frame = new FormMainImpl();
        frame.setTitle(TITLE);
        try {
            final URL iconUrl = getResource(ICON_PATH);
            final Image icon = read(iconUrl);
            frame.setIconImage(icon);
        } catch (IOException ignored) {
        }

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null); // put in center of the screen
        frame.setVisible(true);
    }
}
