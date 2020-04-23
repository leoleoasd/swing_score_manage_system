package ui;

import store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AllScoreTable extends JTable {
    Store store;

    public AllScoreTable(Store store, MainFrame fr){
        super();
        this.store = store;
        super.setModel(store.getScoresTableModel());
        super.setDragEnabled(false);
        super.setShowGrid(true);
        super.setAlignmentX(LEFT_ALIGNMENT);
    }

    public boolean getScrollableTracksViewportHeight() {
        Container parent = SwingUtilities.getUnwrappedParent(this);
        // Let the Table fill the JScrollPane.
        return getPreferredSize().height < parent.getHeight();
    }
}
