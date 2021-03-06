/*
 * MAE - Multi-purpose Annotation Environment
 *
 * Copyright Keigh Rim (krim@brandeis.edu)
 * Department of Computer Science, Brandeis University
 * Original program by Amber Stubbs (astubbs@cs.brandeis.edu)
 *
 * MAE is free software: you can redistribute it and/or modify
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
 * along with this program.  If not, @see <a href="http://www.gnu.org/licenses">http://www.gnu.org/licenses</a>.
 *
 * For feedback, reporting bugs, use the project on Github
 * @see <a href="https://github.com/keighrim/mae-annotation">https://github.com/keighrim/mae-annotation</a>.
 */

package edu.brandeis.cs.nlp.mae.view;

import edu.brandeis.cs.nlp.mae.util.FontHandler;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

import static edu.brandeis.cs.nlp.mae.controller.textpanel.TextPanelController.*;

/**
 * Created by krim on 1/2/2016.
 */
public class TextPanelView extends JPanel {

    private JTabbedPane documentTabs;
    private boolean documentOpen;

    public TextPanelView() {
        super(new BorderLayout());
        documentTabs = new JTabbedPane();
        initTabs();
        add(getTabs(), BorderLayout.CENTER);

    }

    public boolean isAnyDocumentOpen() {
        return documentOpen;
    }

    public void setDocumentOpen(boolean documentOpen) {
        this.documentOpen = documentOpen;
    }

    public void selectTab(int tab) {
        getTabs().setSelectedIndex(tab);
    }

    public void initTabs() {
        getTabs().removeAll();
//        add(getTabs(), BorderLayout.CENTER);
        setDocumentOpen(false);
    }

    public void addAdjudicationTab(DocumentTabTitle title, String text, int fontSize) {
        getTabs().insertTab(title.getLabel(), null, createDocumentArea(
                FontHandler.stringToSimpleStyledDocument(text, DEFAULT_FONT_FAMILY, fontSize, Color.BLACK)), null, 0);
        getTabs().setTabComponentAt(0, title);
        selectTab(0);
    }

    public void addTextTab(DocumentTabTitle title, String text, int fontSize, boolean switchToNewTab) {
        // always open a new tab at the end, and switch to the new tab
        getTabs().addTab(title.getLabel(), null, createDocumentArea(
                FontHandler.stringToSimpleStyledDocument(text, DEFAULT_FONT_FAMILY, fontSize, Color.BLACK)));
        getTabs().setTabComponentAt(getTabs().getTabCount() - 1, title);
        if (switchToNewTab) {
            selectTab(getTabs().getTabCount() - 1);
        }
    }

    public void addTextTab(String title, String text) {
        addTextTab(title, text, DEFAULT_FONT_SIZE);
    }

    public void addTextTab(String title, String text, int fontSize) {
        getTabs().addTab(title, null, createDocumentArea(
                FontHandler.stringToSimpleStyledDocument(text, DEFAULT_FONT_FAMILY, fontSize, Color.BLACK)));
        selectTab(getTabs().getTabCount() - 1);
    }

    public JScrollPane createDocumentArea(StyledDocument document) {

        JTextPane documentArea = new JTextPane(new DefaultStyledDocument());
        JScrollPane scrollableDocument = new JScrollPane(documentArea);

        documentArea.setEditable(false);
        documentArea.setContentType("text/plain; charset=UTF-8");
        documentArea.setStyledDocument(document);

        TextLineNumberRowHeader header = new TextLineNumberRowHeader(documentArea);
        scrollableDocument.setRowHeaderView(header);
        return scrollableDocument;
    }

    public JTextPane getDocumentPane() {
        JScrollPane sp = (JScrollPane) getTabs().getSelectedComponent();
        JTextPane tp = (JTextPane) sp.getViewport().getView();
        return tp;

    }

    public DefaultStyledDocument getDocument() {
        return (DefaultStyledDocument) getDocumentPane().getDocument();
    }

    public Font getTextFont() {
        return getDocumentPane().getFont();

    }

    public void setTextFont(AttributeSet attSet) {
        getDocument().setCharacterAttributes(0, getDocument().getLength(), attSet, false);

    }

    public void setTextFont(Font font) {
        getDocumentPane().setFont(font);
    }

    public Highlighter getHighlighter() {
        return getDocumentPane().getHighlighter();
    }

    public JTabbedPane getTabs() {
        return this.documentTabs;
    }

}
