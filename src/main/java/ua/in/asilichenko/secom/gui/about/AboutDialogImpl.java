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
    private static final int WIDTH = 560;
    private static final int HEIGHT = 420;

    private static final URL CONTENT_URL = Resources.getResource("html/about.html");

    private static final String LICENSE_URL = "https://www.gnu.org/licenses/lgpl-3.0.en.html";
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
