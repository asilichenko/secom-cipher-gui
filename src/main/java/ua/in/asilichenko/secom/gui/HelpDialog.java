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

package ua.in.asilichenko.secom.gui;

import com.google.common.io.Resources;
import ua.in.asilichenko.swing.text.html.CustomHTMLEditorKit;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.event.HyperlinkEvent.EventType.ACTIVATED;

/**
 * Window with instructions on how the SECOM cipher works.
 * The text is loaded from the resource file help.html.
 *
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class HelpDialog extends JDialog {

    private static final String TITLE = "The SECOM cipher";

    private static final int WIDTH = 640;
    private static final int HEIGHT = 720;

    private static final URL CONTENT_URL = Resources.getResource("html/help.html");

    public HelpDialog(Frame owner) {
        super((Frame) null, TITLE); // owner is null so that this window can be placed under the main window

        final JPanel contentPane = createContentPane();
        setContentPane(contentPane);

        final JScrollPane scrollPane = createScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        final JEditorPane editorPane = createEditorPane();
        scrollPane.setViewportView(editorPane);

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // position to center of the screen
        setIconImage(owner.getIconImage()); // when owner is not set
    }

    private JPanel createContentPane() {
        final JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return contentPane;
    }

    private JScrollPane createScrollPane() {
        final JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    private JEditorPane createEditorPane() {
        final JEditorPane editorPane = new JEditorPane();

        editorPane.setEditable(false);
        editorPane.setMargin(new Insets(10, 20, 50, 20));

        editorPane.setEditorKit(new CustomHTMLEditorKit());
        try {
            editorPane.setPage(CONTENT_URL);
        } catch (IOException ignored) {
        }

        editorPane.addHyperlinkListener(e -> {
            try {
                if (ACTIVATED != e.getEventType()) return;
                Desktop.getDesktop().browse(new URI(e.getDescription()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return editorPane;
    }

    public void open() {
        setVisible(true);
    }
}
