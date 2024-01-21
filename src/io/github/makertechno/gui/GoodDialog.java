package io.github.makertechno.gui;

import io.github.makertechno.references.ImgReference;

import javax.swing.*;
import java.awt.*;

public class GoodDialog extends JDialog {
    public GoodDialog(Point appearPoint, String input){
        this.setSize(438, 300);
        this.setLocation(appearPoint);
        this.setLayout(null);
        JLabel label = new JLabel();
        label.setIcon(ImgReference.GOOD);
        label.setSize(ImgReference.GOOD.getIconWidth(),ImgReference.GOOD.getIconHeight());
        label.setLocation(0,0);
        JLabel textLabel = new JLabel("恭喜"+input+"中奖！");
        textLabel.setFont(new Font("宋体", Font.BOLD, 26));
        textLabel.setSize(200, 80);
        textLabel.setBackground(null);
        textLabel.setOpaque(false);
        JPanel panel = new JPanel(new GridLayout(1,1));
        panel.add(textLabel);
        panel.setBackground(null);
        panel.setOpaque(false);
        panel.setLocation((label.getWidth()-textLabel.getWidth())/2 - 5, (label.getHeight()-textLabel.getHeight())/2);
        panel.setSize(300, 80);

        this.add(panel);
        this.add(label);
        setVisible(true);
    }
}
