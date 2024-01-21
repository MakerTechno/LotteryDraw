package io.github.makertechno;

import io.github.makertechno.gui.MainFrame;
import io.github.makertechno.impl.CoverPane;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher {
    public static final Logger LOGGER = Logger.getLogger(Launcher.class.getName());

    /**Try to stop others twice.
     * If not it means the app is still running.
     * (GUI will check the exist file every 200 seconds，when it's not exist GUI will create one.)
     */
    public static void main(String[] args) {
        if (startGUI())if (startGUI())System.out.println("检测到当前系统有本程序运行，请先关闭程序再重新运行");
    }


    /**If tag file exist, try to delete it, Maybe it just an unexpected close before. Else, start GUI.*/
    private static boolean startGUI(){
        if (MainFrame.tempExist()){
            MainFrame.deleteAccessTemp();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Launcher was thrown an sleeping exception on Thread.", e);
            }
            return true;
        } else {
            SwingUtilities.invokeLater(() -> new CoverPane(()-> new MainFrame(LOGGER)));
            return false;
        }
    }
}
