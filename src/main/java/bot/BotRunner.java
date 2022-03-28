package bot;

import rlbot.manager.BotManager;
import util.PortReader;
import util.rocket_league.Constants;
import util.rocket_league.keyboard_command_listener.HICommandListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

public class BotRunner {

    private static final Integer DEFAULT_PORT = 17357;

    public static void main(String[] args) {
        BotManager botManager = new BotManager();
        Integer port = PortReader.readPortFromArgs(args).orElseGet(() -> {
            System.out.println("Could not read port from arguments. Using default port...");
            return DEFAULT_PORT;
        });

        botManager.setRefreshRate((int) Constants.BOT_REFRESH_RATE);

        PythonInterface pythonInterface = new PythonInterface(port, botManager);
        new Thread(pythonInterface::start).start();

        final JFrame jFrame = new JFrame("Java Bot Runner");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.addKeyListener(HICommandListener.instance);
        jFrame.addMouseListener(HICommandListener.instance);

        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        final BorderLayout borderLayout = new BorderLayout();
        panel.setLayout(borderLayout);
        final JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        dataPanel.add(new JLabel("Listening on port " + port), BorderLayout.CENTER);
        final JLabel botsRunning = new JLabel("Loading bots...");
        dataPanel.add(botsRunning, BorderLayout.CENTER);
        panel.add(dataPanel, BorderLayout.CENTER);
        jFrame.add(panel);

        final URL url = BotRunner.class.getClassLoader().getResource("icon.png");
        final Image image = Toolkit.getDefaultToolkit().createImage(url);
        panel.add(new JLabel(new ImageIcon(image)), BorderLayout.WEST);
        jFrame.setIconImage(image);

        jFrame.pack();
        jFrame.setVisible(true);

        final ActionListener myListener = e -> {
            Set<Integer> runningBotIndices = botManager.getRunningBotIndices();

            String botsStr;
            if (runningBotIndices.isEmpty()) {
                botsStr = "None";
            }
            else {
                botsStr = runningBotIndices.stream()
                        .sorted()
                        .map(i -> "#" + i)
                        .collect(Collectors.joining(", "));
            }
            if(runningBotIndices.isEmpty()) {
                botsRunning.setText("No bots running.");
            }
            else if(runningBotIndices.size() == 1) {
                botsRunning.setText("Bot running: " + botsStr);
            }
            else {
                botsRunning.setText("Bots running: " + botsStr);
            }
        };
        new Timer(1000, myListener).start();
    }
}
