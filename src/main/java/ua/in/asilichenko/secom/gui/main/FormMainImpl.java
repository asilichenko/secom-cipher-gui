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

package ua.in.asilichenko.secom.gui.main;

import ua.in.asilichenko.secom.domain.KeyHalves;
import ua.in.asilichenko.secom.gui.HelpDialog;
import ua.in.asilichenko.secom.gui.about.AboutDialogImpl;
import ua.in.asilichenko.secom.service.DecipherService;
import ua.in.asilichenko.secom.service.EncipherService;
import ua.in.asilichenko.secom.validation.CipherTextValidator;
import ua.in.asilichenko.secom.validation.KeyPhraseValidator;
import ua.in.asilichenko.secom.validation.PlainTextValidator;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static ua.in.asilichenko.secom.utils.KeyUtil.createKeyHalves;
import static ua.in.asilichenko.secom.utils.TextUtil.*;

/**
 * Main Form logic implementation
 *
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class FormMainImpl extends FormMain {

    /**
     * The maximum number of characters that allowed to be enciphered.
     */
    private static final int TEXT_LEN_LIMIT = 100_000;
    /**
     * Capacity of the textArea. Must be >2x than TEXT_LEN_LIMIT
     */
    private static final int TEXT_AREA_LIMIT = 3 * TEXT_LEN_LIMIT;

    /**
     * The key phrase must consist of A-Z letters and spaces only.
     */
    private static final String KEY_PHRASE_FILTER = "[^A-Z ]";
    /**
     * The content of the textArea must consist of A-Z letters, spaces and line breaks only.
     */
    private static final String TEXT_FILTER = "[^A-Za-z 0-9\n]";

    /**
     * Format of the information about number of characters in the textArea at the moment.
     */
    private static final String STATUS_FORMAT = "%,d Chars"; // f.e.: 100 000 Chars

    private static final int BUTTONS_LONG_WIDTH = 90;
    private static final int BUTTONS_SHORT_WIDTH = 76;
    private static final int BUTTONS_HEIGHT = 26;

    private static final String ENCIPHER_ERROR_TITLE = "Encipher error";
    private static final String DECIPHER_ERROR_TITLE = "Decipher error";

    private final EncipherService encipherService;
    private final DecipherService decipherService;

    private final CipherTextValidator cipherTextValidator;
    private final PlainTextValidator plainTextValidator;
    private final KeyPhraseValidator keyPhraseValidator;

    public FormMainImpl() {
        encipherService = new EncipherService();
        decipherService = new DecipherService();

        cipherTextValidator = new CipherTextValidator(this);
        plainTextValidator = new PlainTextValidator(this, TEXT_LEN_LIMIT);
        keyPhraseValidator = new KeyPhraseValidator(this);

        keyPhraseTfInit();
        textAreaInit();

        encipherBtnInit();
        decipherBtnInit();

        clearBtnInit();
        pasteBtnInit();
        copyBtnInit();

        helpBtnInit();
        aboutBtnInit();
    }

    private void keyPhraseTfInit() {
        String text = keyPhraseTF.getText();
        keyPhraseTF.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (keyPhraseValidator.isLengthValid(getLength())) {
                    str = str.toUpperCase().replaceAll(KEY_PHRASE_FILTER, "");
                    if (keyPhraseValidator.isLengthValid(getLength() + str.length())) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });
        keyPhraseTF.setText(text);
    }

    private void textAreaInit() {
        final PlainDocument document = new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (1 == str.length() && str.matches(TEXT_FILTER)) return;
                str = str.toUpperCase().replaceAll(TEXT_FILTER, " ");
                if (TEXT_AREA_LIMIT > getLength() + str.length()) super.insertString(offs, str, a);
            }
        };

        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateStatus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateStatus();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateStatus();
            }
        });

        String text = textArea.getText();
        textArea.setDocument(document);
        textArea.setText(text);
    }

    private void encipherBtnInit() {
        encipherBtn.setPreferredSize(new Dimension(BUTTONS_LONG_WIDTH, BUTTONS_HEIGHT));
        encipherBtn.addActionListener(e -> {
            final String keyPhrase = getKeyPhrase();
            if (keyPhraseValidator.isValidationFail(keyPhrase)) {
                keyPhraseTF.requestFocus();
                return;
            }

            final String plainText = getPainText();
            if (plainTextValidator.isValidationFail(plainText)) {
                textArea.requestFocus();
                return;
            }

            if (plainTextValidator.isNotLengthValid(plainText.length())) {
                textArea.requestFocus();
                return;
            }

            try {
                final String encipher = encipherService.encipher(plainText.toCharArray(), createKeyHalves(keyPhrase));
                final String cipherText = makeGroups(encipher);
                textArea.setText(cipherText);
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorMessage(ENCIPHER_ERROR_TITLE, ex.getMessage());
            }
        });
    }

    private void decipherBtnInit() {
        decipherBtn.setPreferredSize(new Dimension(BUTTONS_LONG_WIDTH, BUTTONS_HEIGHT));
        decipherBtn.addActionListener(e -> {
            final String keyPhrase = getKeyPhrase();
            if (keyPhraseValidator.isValidationFail(keyPhrase)) {
                keyPhraseTF.requestFocus();
                return;
            }

            final String cipherText = textArea.getText();
            if (cipherTextValidator.isValidationFail(cipherText)) {
                textArea.requestFocus();
                return;
            }

            final KeyHalves keyHalves = createKeyHalves(keyPhrase);
            final int[] digits = readCipherText(cipherText);
            try {
                final char[] decipher = decipherService.decipher(digits, keyHalves);
                textArea.setText(new String(decipher));
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorMessage(DECIPHER_ERROR_TITLE, ex.getMessage());
            }
        });
    }

    private void clearBtnInit() {
        clearBtn.setPreferredSize(new Dimension(BUTTONS_SHORT_WIDTH, BUTTONS_HEIGHT));
        clearBtn.addActionListener(e -> {
            textArea.setText("");
            textArea.requestFocus();
        });
    }

    private void pasteBtnInit() {
        pasteBtn.setPreferredSize(new Dimension(BUTTONS_SHORT_WIDTH, BUTTONS_HEIGHT));
        pasteBtn.addActionListener(e -> {
            String result = null;
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            final Transferable contents = clipboard.getContents(null);
            boolean hasStringText = (null != contents) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            if (hasStringText) {
                try {
                    result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                }
            }
            textArea.setText(result);
            textArea.requestFocus();
        });
    }

    private void copyBtnInit() {
        copyBtn.setPreferredSize(new Dimension(BUTTONS_SHORT_WIDTH, BUTTONS_HEIGHT));
        copyBtn.addActionListener(e -> {
            textArea.requestFocus();
            textArea.selectAll();
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(textArea.getSelectedText()), null);
        });
    }

    private void helpBtnInit() {
        helpBtn.setPreferredSize(new Dimension(BUTTONS_SHORT_WIDTH, BUTTONS_HEIGHT));
        helpBtn.addActionListener(new ActionListener() {
            private HelpDialog helpDialog = null;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (null == helpDialog) helpDialog = new HelpDialog(FormMainImpl.this);
                helpDialog.open();
            }
        });
    }

    private void aboutBtnInit() {
        aboutBtn.setPreferredSize(new Dimension(BUTTONS_SHORT_WIDTH, BUTTONS_HEIGHT));
        aboutBtn.addActionListener(new ActionListener() {
            private AboutDialogImpl aboutDialog = null;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (null == aboutDialog) aboutDialog = new AboutDialogImpl(FormMainImpl.this);
                aboutDialog.open();
            }
        });
    }

    private void showErrorMessage(String title, String msg) {
        showMessageDialog(this, msg, title, ERROR_MESSAGE);
    }

    private String getKeyPhrase() {
        return prepareKey(keyPhraseTF.getText());
    }

    private String getPainText() {
        return preparePlainText(textArea.getText());
    }

    private int[] readCipherText(String cipherText) {
        cipherText = prepareCipherText(cipherText);
        return readDigits(cipherText);
    }

    private void updateStatus() {
        statusLbl.setText(String.format(STATUS_FORMAT, textArea.getText().length()));
    }
}
