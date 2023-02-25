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

package ua.in.asilichenko.secom.gui.about;

import com.google.common.io.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * About Dialog logic implementation.
 * Window with information about license and authors.
 *
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class AboutDialogImpl extends AboutDialog {

    private static final String TITLE = "About SECOM Cipher";
    private static final int WIDTH = 630;
    private static final int HEIGHT = 470;

    private static final URL CONTENT_URL = Resources.getResource("html/about.html");

    private static final String LICENSE_URL = "https://www.gnu.org/licenses/gpl-3.0.html";
    private static final String IDEA_OWNER_URL = "https://www.ciphermachinesandcryptology.com";
    private static final String ICON_URL = "https://www.cipherhistory.com";
    private static final String AUTHOR_URL = "https://github.com/asilichenko";

    public AboutDialogImpl(Frame owner) {
        super(owner, TITLE, true);

        aboutLblInit();
        licenseLinkLblInit();
        iconLinkLblInit();
        ideaOwnerLinkLblInit();
        authorLinkLblInit();
        closeBtnInit();

        setSize(WIDTH, HEIGHT);
        setResizable(false);
    }

    private void aboutLblInit() {
        try {
            final String text = Resources.toString(CONTENT_URL, UTF_8);
            aboutLbl.setText(text); // in html format to make the text flexible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeHyperLink(JLabel label, String url) {
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void licenseLinkLblInit() {
        makeHyperLink(licenseLinkLbl, LICENSE_URL);
    }

    private void iconLinkLblInit() {
        makeHyperLink(iconLinkLbl, ICON_URL);
    }

    private void ideaOwnerLinkLblInit() {
        makeHyperLink(ideaOwnerLinkLbl, IDEA_OWNER_URL);
    }

    private void authorLinkLblInit() {
        makeHyperLink(authorLinkLbl, AUTHOR_URL);
    }

    private void closeBtnInit() {
        closeBtn.setPreferredSize(new Dimension(90, 26));
        closeBtn.addActionListener(e -> close());
    }

    public void open() {
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(closeBtn);
        setVisible(true);
    }

    public void close() {
        setVisible(false);
    }
}
