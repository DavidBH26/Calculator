package com.berlinches_hernandez_david.calculator;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.*;
import java.lang.Math;

public class Calculator {

    public static int WINDOW_WIDTH = 410;
    public static int WINDOW_HEIGHT = 600;
    public static int BUTTON_WIDTH = 80;
    public static int BUTTON_HEIGHT = 70;
    public static int MARGIN_X = 20;
    public static int MARGIN_Y = 60;

    public JFrame ventana; // Main ventana
    public JComboBox<String> comboCalcType; // Input

    public JComboBox<String> comboTheme; // Save the operator
    public JTextField inText; // For calculate with Opt != (=)
    public JButton btnC; // Connect numbers in display
    public JButton btnBack; // Save the value typed for calculation
    public JButton btnMod;
    public JButton btnDiv;
    public JButton btnMul;
    public JButton btnSub;
    public JButton btnAdd;
    public JButton btn0;
    public JButton btn1;
    public JButton btn2;
    public JButton btn3;
    public JButton btn4;
    public JButton btn5;
    public JButton btn6;
    public JButton btn7;
    public JButton btn8;
    public JButton btn9;
    public JButton btnPoint;
    public JButton btnEqual;
    public JButton btnRoot;
    public JButton btnPower;
    public JButton btnLog;
    public char opt = ' ';
    public boolean go = true;
    public boolean addWrite = true;
    public double val = 0;

    /*
        Mx Calculator: 
        X = Row
        Y = Column
    
        +-------------------+
        |   +-----------+   |   y[0]
        |   |           |   |
        |   +-----------+   |
        |                   |
        |   C  <-   %   /   |   y[1]
        |   7   8   9   *   |   y[2]
        |   4   5   6   -   |   y[3]
        |   1   2   3   +   |   y[4]
        |   .   0     =     |   y[5]
        +-------------------+
         x[0] x[1] x[2] x[3]
    
    */
    
    /*    
        +-------------------+
        |   +-----------+   |   y[0]
        |   |           |   |
        |   +-----------+   |
        |                   |
        |   0   1   1   3   |   y[1]
        |   4   5   6   7   |   y[2]
        |   8   9   10  11  |   y[3]
        |   12  13  14  15  |   y[4]
        |   16  17    18    |   y[5]
        +-------------------+
         x[0] x[1] x[2] x[3]
    
    */

    public Calculator() {
        operativa_ventana();

        comboTheme = initCombo(new String[]{"Simple", "Colored"}, 230, 30, "Theme", themeSwitchEventConsumer);

        comboCalcType = initCombo(new String[]{"Standard", "Scientific"}, 20, 30, "Calculator type", calcTypeSwitchEventConsumer);

        int[] x = {MARGIN_X, MARGIN_X + 90, 200, 290, 380};
        int[] y = {MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 180, MARGIN_Y + 260, MARGIN_Y + 340, MARGIN_Y + 420};

        inText = new JTextField("0");
        inText.setBounds(x[0], y[0], 350, 70);
        inText.setEditable(false);
        inText.setBackground(Color.WHITE);
        inText.setFont(new Font("Comic Sans MS", Font.PLAIN, 33));
        ventana.add(inText);

        btnC = initBtn("C", x[0], y[1], event -> {
            repaintFont();
            inText.setText("0");
            opt = ' ';
            val = 0;
        });

        btnBack = initBtn("<-", x[1], y[1], event -> {
            repaintFont();
            String str = inText.getText();
            StringBuilder str2 = new StringBuilder();
            for (int i = 0; i < (str.length() - 1); i++) {
                str2.append(str.charAt(i));
            }
            if (str2.toString().equals("")) {
                inText.setText("0");
            } else {
                inText.setText(str2.toString());
            }
        });

        btnMod = initBtn("%", x[2], y[1], event -> {
            repaintFont();
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = calc(val, inText.getText(), opt);
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = '%';
                    go = false;
                    addWrite = false;
                }
        });

        btnDiv = initBtn("/", x[3], y[1], event -> {
            repaintFont();
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = calc(val, inText.getText(), opt);
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = '/';
                    go = false;
                    addWrite = false;
                } else {
                    opt = '/';
                }
        });

        btn7 = initBtn("7", x[0], y[2], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("7");
                } else {
                    inText.setText(inText.getText() + "7");
                }
            } else {
                inText.setText("7");
                addWrite = true;
            }
            go = true;
        });

        btn8 = initBtn("8", x[1], y[2], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("8");
                } else {
                    inText.setText(inText.getText() + "8");
                }
            } else {
                inText.setText("8");
                addWrite = true;
            }
            go = true;
        });

        btn9 = initBtn("9", x[2], y[2], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("9");
                } else {
                    inText.setText(inText.getText() + "9");
                }
            } else {
                inText.setText("9");
                addWrite = true;
            }
            go = true;
        });

        btnMul = initBtn("*", x[3], y[2], event -> {
            repaintFont();
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = calc(val, inText.getText(), opt);
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = '*';
                    go = false;
                    addWrite = false;
                } else {
                    opt = '*';
                }
        });

        btn4 = initBtn("4", x[0], y[3], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("4");
                } else {
                    inText.setText(inText.getText() + "4");
                }
            } else {
                inText.setText("4");
                addWrite = true;
            }
            go = true;
        });

        btn5 = initBtn("5", x[1], y[3], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("5");
                } else {
                    inText.setText(inText.getText() + "5");
                }
            } else {
                inText.setText("5");
                addWrite = true;
            }
            go = true;
        });

        btn6 = initBtn("6", x[2], y[3], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("6");
                } else {
                    inText.setText(inText.getText() + "6");
                }
            } else {
                inText.setText("6");
                addWrite = true;
            }
            go = true;
        });

        btnSub = initBtn("-", x[3], y[3], event -> {
            repaintFont();
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = calc(val, inText.getText(), opt);
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }

                    opt = '-';
                    go = false;
                    addWrite = false;
                } else {
                    opt = '-';
                }
        });

        btn1 = initBtn("1", x[0], y[4], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("1");
                } else {
                    inText.setText(inText.getText() + "1");
                }
            } else {
                inText.setText("1");
                addWrite = true;
            }
            go = true;
        });

        btn2 = initBtn("2", x[1], y[4], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("2");
                } else {
                    inText.setText(inText.getText() + "2");
                }
            } else {
                inText.setText("2");
                addWrite = true;
            }
            go = true;
        });

        btn3 = initBtn("3", x[2], y[4], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("3");
                } else {
                    inText.setText(inText.getText() + "3");
                }
            } else {
                inText.setText("3");
                addWrite = true;
            }
            go = true;
        });

        btnAdd = initBtn("+", x[3], y[4], event -> {
            repaintFont();
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = calc(val, inText.getText(), opt);
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = '+';
                    go = false;
                    addWrite = false;
                } else {
                    opt = '+';
                }
        });

        btnPoint = initBtn(".", x[0], y[5], event -> {
            repaintFont();
            if (addWrite) {
                if (!inText.getText().contains(".")) {
                    inText.setText(inText.getText() + ".");
                }
            } else {
                inText.setText("0.");
                addWrite = true;
            }
            go = true;
        });

        btn0 = initBtn("0", x[1], y[5], event -> {
            repaintFont();
            if (addWrite) {
                if (Pattern.matches("[0]*", inText.getText())) {
                    inText.setText("0");
                } else {
                    inText.setText(inText.getText() + "0");
                }
            } else {
                inText.setText("0");
                addWrite = true;
            }
            go = true;
        });

        btnEqual = initBtn("=", x[2], y[5], event -> {
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = calc(val, inText.getText(), opt);
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = '=';
                    addWrite = false;
                }
        });
        btnEqual.setSize(2 * BUTTON_WIDTH + 10, BUTTON_HEIGHT);

        btnRoot = initBtn("?", x[4], y[1], event -> {
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = Math.sqrt(Double.parseDouble(inText.getText()));
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = '?';
                    addWrite = false;
                }
        });
        btnRoot.setVisible(false);

        btnPower = initBtn("pow", x[4], y[2], event -> {
            repaintFont();
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = calc(val, inText.getText(), opt);
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = '^';
                    go = false;
                    addWrite = false;
                } else {
                    opt = '^';
                }
        });
        btnPower.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        btnPower.setVisible(false);

        btnLog = initBtn("ln", x[4], y[3], event -> {
            if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()))
                if (go) {
                    val = Math.log(Double.parseDouble(inText.getText()));
                    if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(val))) {
                        inText.setText(String.valueOf((int) val));
                    } else {
                        inText.setText(String.valueOf(val));
                    }
                    opt = 'l';
                    addWrite = false;
                }
        });
        btnLog.setVisible(false);

        ventana.setLayout(null);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close button clicked? = End The process
        ventana.setVisible(true);
    }

    public void operativa_ventana() throws HeadlessException {
        setVentana(new JFrame("Calculator"));
        getVentana().setSize(getWINDOW_WIDTH(), getWINDOW_HEIGHT());
        getVentana().setLocationRelativeTo(null); // Move ventana to center
    }

    private JComboBox<String> initCombo(String[] items, int x, int y, String toolTip, Consumer consumerEvent) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBounds(x, y, 140, 25);
        combo.setToolTipText(toolTip);
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combo.addItemListener(consumerEvent::accept);
        getVentana().add(combo);

        return combo;
    }

    private JButton initBtn(String label, int x, int y, ActionListener event) {
        JButton btn = new JButton(label);
        btn.setBounds(x, y, getBUTTON_WIDTH(), getBUTTON_HEIGHT());
        btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(event);
        getVentana().add(btn);

        return btn;
    }

    public double calc(double x, String input, char opt, float cantidad) {
        getInText().setFont(getInText().getFont().deriveFont(Font.PLAIN));
        double y = Double.parseDouble(input);
        switch (opt) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '*':
                return x * y;
            case '/':
                return x / y;
            case '%':
                return x % y;
            case '^':
                return Math.pow(x, y);
            default:
                getInText().setFont(getInText().getFont().deriveFont(Font.PLAIN));
                return y;
        }
    }

    private void repaintFont() {
        getInText().setFont(getInText().getFont().deriveFont(Font.PLAIN));
    }

    public Consumer<ItemEvent> calcTypeSwitchEventConsumer = event -> {
        if (event.getStateChange() != ItemEvent.SELECTED) return;

        String selectedItem = (String) event.getItem();
        switch (selectedItem) {
            case "Standard":
                ventana.setSize(getWINDOW_WIDTH(), WINDOW_HEIGHT);
                btnRoot.setVisible(false);
                btnPower.setVisible(false);
                btnLog.setVisible(false);
                break;
            case "Scientific":
                ventana.setSize(getWINDOW_WIDTH() + 80, WINDOW_HEIGHT);
                btnRoot.setVisible(true);
                btnPower.setVisible(true);
                btnLog.setVisible(true);
                break;
        }
    };

    public Consumer<ItemEvent> themeSwitchEventConsumer = event -> {
        if (event.getStateChange() != ItemEvent.SELECTED) return;

        String selectedTheme = (String) event.getItem();
        switch (selectedTheme) {
            case "Simple":
                btnC.setBackground(null);
                btnBack.setBackground(null);
                btnMod.setBackground(null);
                btnDiv.setBackground(null);
                btnMul.setBackground(null);
                btnSub.setBackground(null);
                btnAdd.setBackground(null);
                btnRoot.setBackground(null);
                btnLog.setBackground(null);
                btnPower.setBackground(null);
                btnEqual.setBackground(null);
                btn0.setBackground(null);
                btn1.setBackground(null);
                btn2.setBackground(null);
                btn3.setBackground(null);
                btn4.setBackground(null);
                btn5.setBackground(null);
                btn6.setBackground(null);
                btn7.setBackground(null);
                btn8.setBackground(null);
                btn9.setBackground(null);
                btnPoint.setBackground(null);

                btnC.setForeground(Color.BLACK);
                btnBack.setForeground(Color.BLACK);
                btnMod.setForeground(Color.BLACK);
                btnDiv.setForeground(Color.BLACK);
                btnMul.setForeground(Color.BLACK);
                btnSub.setForeground(Color.BLACK);
                btnAdd.setForeground(Color.BLACK);
                btnEqual.setForeground(Color.BLACK);
                btnLog.setForeground(Color.BLACK);
                btnPower.setForeground(Color.BLACK);
                btnRoot.setForeground(Color.BLACK);
                break;
            case "Colored":
                btnC.setBackground(Color.RED);
                btnBack.setBackground(Color.ORANGE);
                btnMod.setBackground(Color.GREEN);
                btnDiv.setBackground(Color.PINK);
                btnMul.setBackground(Color.PINK);
                btnSub.setBackground(Color.PINK);
                btnAdd.setBackground(Color.PINK);
                btnRoot.setBackground(Color.PINK);
                btnLog.setBackground(Color.PINK);
                btnPower.setBackground(Color.PINK);
                btnEqual.setBackground(Color.BLUE);
                btn0.setBackground(Color.WHITE);
                btn1.setBackground(Color.WHITE);
                btn2.setBackground(Color.WHITE);
                btn3.setBackground(Color.WHITE);
                btn4.setBackground(Color.WHITE);
                btn5.setBackground(Color.WHITE);
                btn6.setBackground(Color.WHITE);
                btn7.setBackground(Color.WHITE);
                btn8.setBackground(Color.WHITE);
                btn9.setBackground(Color.WHITE);
                btnPoint.setBackground(Color.WHITE);

                btnC.setForeground(Color.WHITE);
                btnBack.setForeground(Color.WHITE);
                btnMod.setForeground(Color.WHITE);
                btnDiv.setForeground(Color.WHITE);
                btnMul.setForeground(Color.WHITE);
                btnSub.setForeground(Color.WHITE);
                btnAdd.setForeground(Color.WHITE);
                btnEqual.setForeground(Color.WHITE);
                btnLog.setForeground(Color.WHITE);
                btnPower.setForeground(Color.WHITE);
                btnRoot.setForeground(Color.WHITE);
                break;
        }
    };

    public static void main(String[] args) {
        new Calculator();
    }

    /**
     * @return the WINDOW_WIDTH
     */
    public static int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    /**
     * @param aWINDOW_WIDTH the WINDOW_WIDTH to set
     */
    public static void setWINDOW_WIDTH(int aWINDOW_WIDTH) {
        WINDOW_WIDTH = aWINDOW_WIDTH;
    }

    /**
     * @return the WINDOW_HEIGHT
     */
    public static int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    /**
     * @param aWINDOW_HEIGHT the WINDOW_HEIGHT to set
     */
    public static void setWINDOW_HEIGHT(int aWINDOW_HEIGHT) {
        WINDOW_HEIGHT = aWINDOW_HEIGHT;
    }

    /**
     * @return the BUTTON_WIDTH
     */
    public static int getBUTTON_WIDTH() {
        return BUTTON_WIDTH;
    }

    /**
     * @param aBUTTON_WIDTH the BUTTON_WIDTH to set
     */
    public static void setBUTTON_WIDTH(int aBUTTON_WIDTH) {
        BUTTON_WIDTH = aBUTTON_WIDTH;
    }

    /**
     * @return the BUTTON_HEIGHT
     */
    public static int getBUTTON_HEIGHT() {
        return BUTTON_HEIGHT;
    }

    /**
     * @param aBUTTON_HEIGHT the BUTTON_HEIGHT to set
     */
    public static void setBUTTON_HEIGHT(int aBUTTON_HEIGHT) {
        BUTTON_HEIGHT = aBUTTON_HEIGHT;
    }

    /**
     * @return the MARGIN_X
     */
    public static int getMARGIN_X() {
        return MARGIN_X;
    }

    /**
     * @param aMARGIN_X the MARGIN_X to set
     */
    public static void setMARGIN_X(int aMARGIN_X) {
        MARGIN_X = aMARGIN_X;
    }

    /**
     * @return the MARGIN_Y
     */
    public static int getMARGIN_Y() {
        return MARGIN_Y;
    }

    /**
     * @param aMARGIN_Y the MARGIN_Y to set
     */
    public static void setMARGIN_Y(int aMARGIN_Y) {
        MARGIN_Y = aMARGIN_Y;
    }

    /**
     * @return the ventana
     */
    public JFrame getVentana() {
        return ventana;
    }

    /**
     * @param ventana the ventana to set
     */
    public void setVentana(JFrame ventana) {
        this.ventana = ventana;
    }

    /**
     * @return the comboCalcType
     */
    public JComboBox<String> getComboCalcType() {
        return comboCalcType;
    }

    /**
     * @param comboCalcType the comboCalcType to set
     */
    public void setComboCalcType(JComboBox<String> comboCalcType) {
        this.comboCalcType = comboCalcType;
    }

    /**
     * @return the comboTheme
     */
    public JComboBox<String> getComboTheme() {
        return comboTheme;
    }

    /**
     * @param comboTheme the comboTheme to set
     */
    public void setComboTheme(JComboBox<String> comboTheme) {
        this.comboTheme = comboTheme;
    }

    /**
     * @return the inText
     */
    public JTextField getInText() {
        return inText;
    }

    /**
     * @param inText the inText to set
     */
    public void setInText(JTextField inText) {
        this.inText = inText;
    }

    /**
     * @return the btnC
     */
    public JButton getBtnC() {
        return btnC;
    }

    /**
     * @param btnC the btnC to set
     */
    public void setBtnC(JButton btnC) {
        this.btnC = btnC;
    }

    /**
     * @return the btnBack
     */
    public JButton getBtnBack() {
        return btnBack;
    }

    /**
     * @param btnBack the btnBack to set
     */
    public void setBtnBack(JButton btnBack) {
        this.btnBack = btnBack;
    }

    /**
     * @return the btnMod
     */
    public JButton getBtnMod() {
        return btnMod;
    }

    /**
     * @param btnMod the btnMod to set
     */
    public void setBtnMod(JButton btnMod) {
        this.btnMod = btnMod;
    }

    /**
     * @return the btnDiv
     */
    public JButton getBtnDiv() {
        return btnDiv;
    }

    /**
     * @param btnDiv the btnDiv to set
     */
    public void setBtnDiv(JButton btnDiv) {
        this.btnDiv = btnDiv;
    }

    /**
     * @return the btnMul
     */
    public JButton getBtnMul() {
        return btnMul;
    }

    /**
     * @param btnMul the btnMul to set
     */
    public void setBtnMul(JButton btnMul) {
        this.btnMul = btnMul;
    }

    /**
     * @return the btnSub
     */
    public JButton getBtnSub() {
        return btnSub;
    }

    /**
     * @param btnSub the btnSub to set
     */
    public void setBtnSub(JButton btnSub) {
        this.btnSub = btnSub;
    }

    /**
     * @return the btnAdd
     */
    public JButton getBtnAdd() {
        return btnAdd;
    }

    /**
     * @param btnAdd the btnAdd to set
     */
    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    /**
     * @return the btn0
     */
    public JButton getBtn0() {
        return btn0;
    }

    /**
     * @param btn0 the btn0 to set
     */
    public void setBtn0(JButton btn0) {
        this.btn0 = btn0;
    }

    /**
     * @return the btn1
     */
    public JButton getBtn1() {
        return btn1;
    }

    /**
     * @param btn1 the btn1 to set
     */
    public void setBtn1(JButton btn1) {
        this.btn1 = btn1;
    }

    /**
     * @return the btn2
     */
    public JButton getBtn2() {
        return btn2;
    }

    /**
     * @param btn2 the btn2 to set
     */
    public void setBtn2(JButton btn2) {
        this.btn2 = btn2;
    }

    /**
     * @return the btn3
     */
    public JButton getBtn3() {
        return btn3;
    }

    /**
     * @param btn3 the btn3 to set
     */
    public void setBtn3(JButton btn3) {
        this.btn3 = btn3;
    }

    /**
     * @return the btn4
     */
    public JButton getBtn4() {
        return btn4;
    }

    /**
     * @param btn4 the btn4 to set
     */
    public void setBtn4(JButton btn4) {
        this.btn4 = btn4;
    }

    /**
     * @return the btn5
     */
    public JButton getBtn5() {
        return btn5;
    }

    /**
     * @param btn5 the btn5 to set
     */
    public void setBtn5(JButton btn5) {
        this.btn5 = btn5;
    }

    /**
     * @return the btn6
     */
    public JButton getBtn6() {
        return btn6;
    }

    /**
     * @param btn6 the btn6 to set
     */
    public void setBtn6(JButton btn6) {
        this.btn6 = btn6;
    }

    /**
     * @return the btn7
     */
    public JButton getBtn7() {
        return btn7;
    }

    /**
     * @param btn7 the btn7 to set
     */
    public void setBtn7(JButton btn7) {
        this.btn7 = btn7;
    }

    /**
     * @return the btn8
     */
    public JButton getBtn8() {
        return btn8;
    }

    /**
     * @param btn8 the btn8 to set
     */
    public void setBtn8(JButton btn8) {
        this.btn8 = btn8;
    }

    /**
     * @return the btn9
     */
    public JButton getBtn9() {
        return btn9;
    }

    /**
     * @param btn9 the btn9 to set
     */
    public void setBtn9(JButton btn9) {
        this.btn9 = btn9;
    }

    /**
     * @return the btnPoint
     */
    public JButton getBtnPoint() {
        return btnPoint;
    }

    /**
     * @param btnPoint the btnPoint to set
     */
    public void setBtnPoint(JButton btnPoint) {
        this.btnPoint = btnPoint;
    }

    /**
     * @return the btnEqual
     */
    public JButton getBtnEqual() {
        return btnEqual;
    }

    /**
     * @param btnEqual the btnEqual to set
     */
    public void setBtnEqual(JButton btnEqual) {
        this.btnEqual = btnEqual;
    }

    /**
     * @return the btnRoot
     */
    public JButton getBtnRoot() {
        return btnRoot;
    }

    /**
     * @param btnRoot the btnRoot to set
     */
    public void setBtnRoot(JButton btnRoot) {
        this.btnRoot = btnRoot;
    }

    /**
     * @return the btnPower
     */
    public JButton getBtnPower() {
        return btnPower;
    }

    /**
     * @param btnPower the btnPower to set
     */
    public void setBtnPower(JButton btnPower) {
        this.btnPower = btnPower;
    }

    /**
     * @return the btnLog
     */
    public JButton getBtnLog() {
        return btnLog;
    }

    /**
     * @param btnLog the btnLog to set
     */
    public void setBtnLog(JButton btnLog) {
        this.btnLog = btnLog;
    }

    /**
     * @return the opt
     */
    public char getOpt() {
        return opt;
    }

    /**
     * @param opt the opt to set
     */
    public void setOpt(char opt) {
        this.opt = opt;
    }

    /**
     * @return the go
     */
    public boolean isGo() {
        return go;
    }

    /**
     * @param go the go to set
     */
    public void setGo(boolean go) {
        this.go = go;
    }

    /**
     * @return the addWrite
     */
    public boolean isAddWrite() {
        return addWrite;
    }

    /**
     * @param addWrite the addWrite to set
     */
    public void setAddWrite(boolean addWrite) {
        this.addWrite = addWrite;
    }

    /**
     * @return the val
     */
    public double getVal() {
        return val;
    }

    /**
     * @param val the val to set
     */
    public void setVal(double val) {
        this.val = val;
    }

    /**
     * @return the calcTypeSwitchEventConsumer
     */
    public Consumer<ItemEvent> getCalcTypeSwitchEventConsumer() {
        return calcTypeSwitchEventConsumer;
    }

    /**
     * @param calcTypeSwitchEventConsumer the calcTypeSwitchEventConsumer to set
     */
    public void setCalcTypeSwitchEventConsumer(Consumer<ItemEvent> calcTypeSwitchEventConsumer) {
        this.calcTypeSwitchEventConsumer = calcTypeSwitchEventConsumer;
    }

    /**
     * @return the themeSwitchEventConsumer
     */
    public Consumer<ItemEvent> getThemeSwitchEventConsumer() {
        return themeSwitchEventConsumer;
    }

    /**
     * @param themeSwitchEventConsumer the themeSwitchEventConsumer to set
     */
    public void setThemeSwitchEventConsumer(Consumer<ItemEvent> themeSwitchEventConsumer) {
        this.themeSwitchEventConsumer = themeSwitchEventConsumer;
    }
}