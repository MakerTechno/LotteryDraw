package io.github.makertechno.gui;

import io.github.makertechno.randoms.SimpleRandoms;
import io.github.makertechno.references.ImgReference;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static io.github.makertechno.references.NameRef.students;

public class MainFrame extends JFrame {

    public static final Point[] points = {new Point(100,80),new Point(600,80),new Point(1100,80),new Point(100,600),new Point(600,600),new Point(1100,600)};
    public static int letSee = 6;
    public static final Set<GoodDialog> DIALOGS = new HashSet<>();
    /**这个Logger应该从Launcher传来*/
    public final Logger LOGGER;

    /**这tm是之前试了半天的系统临时文件夹获取方法*/
    public static final String TEMP_PATH = System.getenv("Temp");
    /**这是检查用的文件的位置*/
    private static final File FILE = new File(TEMP_PATH+File.separator+"tmp_isUsing.tmp");

    public static Timer timer = null;

    public static final JTextField[] TEXT_FIELDS = new JTextField[6];
    public static final Font DEFAULT_FONT = new Font("宋体", Font.BOLD, 16);

    private static final JButton CLOSE_BTN = new JButton("关闭所有喜报");

    public MainFrame(Logger logger){
        LOGGER = logger;

        generalSetup(this);
        extraSetup();

        setVisible(true);
    }

    private void generalSetup(@NotNull JFrame frame){
        frame.setTitle("简易抽奖器");
        frame.setAlwaysOnTop(true);
        frame.setIconImage(ImgReference.ICON.getImage());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(2,1));
    }

    private void extraSetup(){
        this.add(JTAInit(new JPanel(new GridLayout(3,2))));
        this.add(this.btnAreaInit(new JPanel(new FlowLayout())));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                CLOSE_BTN.setEnabled(!DIALOGS.isEmpty());
            }
        }, 0, 100);
    }

    private JPanel JTAInit(JPanel panel){
        for (int i = 0; i < 6; i++ ) {
            TEXT_FIELDS[i] = new JTextField();
            TEXT_FIELDS[i].setText(students[i]);
            TEXT_FIELDS[i].setFont(DEFAULT_FONT);
            TEXT_FIELDS[i].setSize(90, 30);
            TEXT_FIELDS[i].setEditable(false);
            TEXT_FIELDS[i].setHorizontalAlignment(JTextField.CENTER);
            panel.add(TEXT_FIELDS[i]);
        }
        return panel;
    }

    public void modeChooserInit(@NotNull JPanel panel){
        JLabel label = new JLabel("显示数量");
        JComboBox<Integer> comboBox = new JComboBox<>(new Integer[]{1,2,3,4,5,6});
        comboBox.setSelectedIndex(5);
        comboBox.addActionListener(event -> {
            letSee = comboBox.getSelectedIndex() + 1;
            for (int i = 0; i < 6; i++){
                if (i <= comboBox.getSelectedIndex()){
                    if (!TEXT_FIELDS[i].isVisible()) {
                        TEXT_FIELDS[i].setVisible(true);
                    }
                } else if (TEXT_FIELDS[i].isVisible()){
                    TEXT_FIELDS[i].setVisible(false);
                }
            }
        });
        panel.add(label);
        panel.add(comboBox);
    }

    @Contract("_ -> param1")
    private @NotNull JPanel btnAreaInit(@NotNull JPanel panel){
        JButton button2 = new JButton("一键生成喜报");
        button2.addActionListener((event -> {
            for (int i = 0; i < letSee; i++){
                DIALOGS.add(new GoodDialog(points[i], TEXT_FIELDS[i].getText()));
            }
        }));
        JButton button1 = new JButton("开始抽奖");
        button1.setSize(70,30);
        button1.addActionListener(event -> {
            if (event.getActionCommand().equals("开始抽奖")){
                timer = new Timer();
                timer.schedule(getRollTask(), 6, 10);
                button1.setText("结束抽奖");
                button2.setEnabled(false);
            } else {
                button1.setText("开始抽奖");
                button2.setEnabled(true);
                timer.cancel();
            }
        });
        CLOSE_BTN.addActionListener(event -> {
            if (!DIALOGS.isEmpty()) {
                for (JDialog dialog : DIALOGS){
                    if (dialog != null) dialog.dispose();
                }
                DIALOGS.clear();
            }
        });
        JCheckBox frontBox = new JCheckBox();
        frontBox.setSelected(true);
        frontBox.addActionListener(event -> {
            this.setVisible(false);
            this.dispose();
            this.setAlwaysOnTop(frontBox.isSelected());
            this.setVisible(true);
        });
        modeChooserInit(panel);
        panel.add(button1);
        panel.add(button2);
        panel.add(CLOSE_BTN);
        panel.add(new JLabel("设置保持置顶："));
        panel.add(frontBox);
        return panel;
    }

    @Contract(value = " -> new", pure = true)
    private @NotNull TimerTask getRollTask(){
        return new TimerTask() {
            @Override
            public void run() {
                int[] source = new int[6];
                for (int i = 0; i < source.length; i++) {
                    source[i] = SimpleRandoms.getUnSameX6(students.length, source, i);
                }
                for (int i = 0; i < 6; i++){
                    TEXT_FIELDS[i].setText(students[source[i]]);
                }
            }
        };
    }

    public static boolean tempExist(){
        return FILE.exists();
    }
    /**作为每刻仅允许一个窗口启动のTemp*/
    @SuppressWarnings("all")
    public static boolean createAccessTemp() throws IOException {
        return FILE.createNewFile();
    }

    /**退出时删除Temp*/
    @SuppressWarnings("all")
    public static boolean deleteAccessTemp(){
        return FILE.delete();
    }
}