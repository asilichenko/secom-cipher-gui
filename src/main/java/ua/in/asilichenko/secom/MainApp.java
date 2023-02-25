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
