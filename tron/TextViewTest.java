package se.liu.ida.rubda680.jakbi869.tddc69.tron;

import se.liu.ida.rubda680.jakbi869.tddc69.tron.Graphics.TronFrame;
import se.liu.ida.rubda680.jakbi869.tddc69.tron.Graphics.TronTextView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA. User: RubenDas Date: 2013-09-30 Time: 16:50 To change this template use File | Settings | File
 * Templates.
 */
public final class TextViewTest {

    private TextViewTest() {}

    //This main is only used when testing the TronTextView
    public static void main(String[] args) {

        final TronTextView myPrint = new TronTextView();

        final Arena arena = new Arena(5, 10);
        final TronFrame myFrame = new TronFrame(arena);

        System.out.println(myPrint.convertToText(arena));


        final Action doOneStep = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.doText(arena);
                System.out.println(myPrint.convertToText(arena));
                arena.generateArena();
                arena.tick();
                myFrame.repaint();
            }


        };

        final Timer clockTimer = new Timer(10, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

}
