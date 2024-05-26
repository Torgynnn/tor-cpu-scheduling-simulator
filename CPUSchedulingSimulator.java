import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CPUSchedulingSimulator {

    private JFrame frame;
    private JPanel mainPanel;
    private List<Process> processes;
    private int numberOfProcesses;
    private JComboBox<String> algorithmComboBox;
    private JTextField processField;
    private List<JTextField> burstTimeFields;
    private List<JTextField> arrivalTimeFields;
    private String selectedAlgorithm;
    private JLabel avgWaitingTimeLabel;
    private JLabel avgTurnaroundTimeLabel;
    private DefaultListModel<String> readyQueueModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CPUSchedulingSimulator().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Create the main frame
        frame = new JFrame("CPU Scheduling Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridBagLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.BLACK);

        JLabel welcomeLabel = new JLabel("Welcome to the simulator");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.GREEN);
        mainPanel.add(welcomeLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel chooseAlgorithmLabel = new JLabel("Choose Scheduling Algorithm:");
        chooseAlgorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseAlgorithmLabel.setMaximumSize(new Dimension(300, chooseAlgorithmLabel.getPreferredSize().height));
        chooseAlgorithmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseAlgorithmLabel.setForeground(Color.GREEN);
        mainPanel.add(chooseAlgorithmLabel);

        String[] algorithms = {"FCFS", "SJF", "Priority", "Round Robin"};
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        algorithmComboBox.setMaximumSize(new Dimension(200, algorithmComboBox.getPreferredSize().height));
        algorithmComboBox.setForeground(Color.GREEN);
        algorithmComboBox.setBackground(Color.BLACK);
        mainPanel.add(algorithmComboBox);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel processLabel = new JLabel("Enter number of processes:");
        processLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        processLabel.setMaximumSize(new Dimension(300, processLabel.getPreferredSize().height));
        processLabel.setHorizontalAlignment(SwingConstants.CENTER);
        processLabel.setForeground(Color.GREEN);
        mainPanel.add(processLabel);

        // Add text field for number of processes
        processField = new JTextField();
        processField.setMaximumSize(new Dimension(200, processField.getPreferredSize().height));
        processField.setAlignmentX(Component.CENTER_ALIGNMENT);
        processField.setForeground(Color.GREEN);
        processField.setBackground(Color.BLACK);
        processField.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Set green border
        mainPanel.add(processField);

        // Add space
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add button to proceed
        JButton proceedButton = new JButton("Proceed");
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.setForeground(Color.GREEN);
        proceedButton.setBackground(Color.BLACK);
        mainPanel.add(proceedButton);

        frame.add(mainPanel, new GridBagConstraints());
        frame.setVisible(true);

        // Add action listener to the proceed button
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                try {
                    numberOfProcesses = Integer.parseInt(processField.getText());
                    if (numberOfProcesses <= 0) {
                        throw new NumberFormatException();
                    }
                    showBurstArrivalInputPage();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number of processes.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showBurstArrivalInputPage() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.BLACK);

        JLabel instructionLabel = new JLabel("Enter Burst Time and Arrival Time for each process:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font("Serif", Font.BOLD, 20));
        instructionLabel.setForeground(Color.GREEN);
        mainPanel.add(instructionLabel);

        burstTimeFields = new ArrayList<>();
        arrivalTimeFields = new ArrayList<>();
        List<JTextField> priorityFields = new ArrayList<>();  // Store priority input fields

        for (int i = 0; i < numberOfProcesses; i++) {
            JPanel processPanel = new JPanel();
            processPanel.setLayout(new FlowLayout());
            processPanel.setBackground(Color.BLACK);

            JLabel processLabel = new JLabel("Process " + (i + 1) + ": ");
            processLabel.setForeground(Color.GREEN);
            processPanel.add(processLabel);

            JTextField burstField = new JTextField("Burst Time", 10);
            JTextField arrivalField = new JTextField("Arrival Time", 10);
            JTextField priorityField = new JTextField("Priority", 10); // Priority field

            if ("Priority".equals(selectedAlgorithm)) {
                processPanel.add(priorityField);
                priorityFields.add(priorityField);
            }

            burstField.setForeground(Color.GREEN);
            burstField.setBackground(Color.BLACK);
            burstField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            burstTimeFields.add(burstField);
            processPanel.add(burstField);

            arrivalField.setForeground(Color.GREEN);
            arrivalField.setBackground(Color.BLACK);
            arrivalField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            arrivalTimeFields.add(arrivalField);
            processPanel.add(arrivalField);

            mainPanel.add(processPanel);
        }

        JButton simulateButton = new JButton("Simulate");
        simulateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        simulateButton.setForeground(Color.GREEN);
        simulateButton.setBackground(Color.BLACK);
        mainPanel.add(simulateButton);

        frame.add(mainPanel, new GridBagConstraints());
        frame.setVisible(true);


        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processes = new ArrayList<>();
                try {
                    for (int i = 0; i < numberOfProcesses; i++) {
                        int burstTime = Integer.parseInt(burstTimeFields.get(i).getText());
                        int arrivalTime = Integer.parseInt(arrivalTimeFields.get(i).getText());
                        int priority = "Priority".equals(selectedAlgorithm) ? Integer.parseInt(priorityFields.get(i).getText()) : 0;
                        processes.add(new Process(i + 1, burstTime, arrivalTime, priority));
                    }
                    // Algorithm selection and simulation page call
                    switch (selectedAlgorithm) {
                        case "FCFS":
                            FCFS fcfs = new FCFS(processes);
                            fcfs.schedule();
                            showSimulationPage(fcfs.getProcesses());
                            break;
                        case "SJF":
                            SJF sjf = new SJF(processes);
                            sjf.schedule();
                            showSimulationPage(sjf.getProcesses());
                            break;
                        case "Priority":
                            Priority priority = new Priority(processes);
                            priority.schedule();
                            showSimulationPage(priority.getProcesses());
                            break;
                        case "Round Robin":
                            int quantum = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the quantum time:"));
                            RoundRobin rr = new RoundRobin(processes, quantum);
                            rr.schedule();
                            showSimulationPage(rr.getProcesses());
                            break;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid burst, arrival times, or quantum.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private void showSimulationPage(List<Process> processes) {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        // Determine columns based on the selected algorithm
        String[] baseColumnNames = {"Process ID", "Burst Time", "Arrival Time", "Start Time", "End Time"};
        String[] priorityColumnNames = {"Process ID", "Burst Time", "Arrival Time", "Priority", "Start Time", "End Time"};
        String[] columnNames = "Priority".equals(selectedAlgorithm) ? priorityColumnNames : baseColumnNames;

        Object[][] data = new Object[processes.size()][columnNames.length];
        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);
            List<Object> rowData = new ArrayList<>();
            rowData.add(p.id);
            rowData.add(p.burstTime);
            rowData.add(p.arrivalTime);
            if ("Priority".equals(selectedAlgorithm)) {
                rowData.add(p.priority);
            }
            rowData.add(p.startTime);
            rowData.add(p.finishTime);
            data[i] = rowData.toArray();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.GREEN);
        table.setGridColor(Color.GREEN);
        table.setShowGrid(true);
        table.setPreferredScrollableViewportSize(new Dimension(400, 500));

        // Set table header colors
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.GREEN);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 30, 0, 0));



        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        for (Process p : processes) {
            int turnaroundTime = p.finishTime - p.arrivalTime;
            int waitingTime = turnaroundTime - p.burstTime;
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
        }
        double avgWaitingTime = totalWaitingTime / processes.size();
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();

        // Display average times
        avgWaitingTimeLabel = new JLabel("Average Waiting Time: " + avgWaitingTime);
        avgWaitingTimeLabel.setForeground(Color.GREEN);
        avgWaitingTimeLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));

        avgTurnaroundTimeLabel = new JLabel("Average Turnaround Time: " + avgTurnaroundTime);
        avgTurnaroundTimeLabel.setForeground(Color.GREEN);
        avgTurnaroundTimeLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 0));


        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.BLACK);
        statsPanel.add(avgWaitingTimeLabel);
        statsPanel.add(avgTurnaroundTimeLabel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.BLACK);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        leftPanel.add(statsPanel, BorderLayout.SOUTH);

        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.setBackground(Color.BLACK);

        JLabel simulationLabel = new JLabel("Simulation in progress...");
        simulationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        simulationLabel.setFont(new Font("Serif", Font.BOLD, 20));
        simulationLabel.setForeground(Color.GREEN);
        progressPanel.add(simulationLabel);

        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.Y_AXIS));
        progressBarPanel.setBackground(Color.BLACK);
        progressBarPanel.setPreferredSize(new Dimension(300, 500)); // Adjust width and height
        progressBarPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 30)); // Add padding to move left

        readyQueueModel = new DefaultListModel<>();
        JList<String> readyQueueList = new JList<>(readyQueueModel);
        readyQueueList.setForeground(Color.GREEN);
        readyQueueList.setBackground(Color.BLACK);
        readyQueueList.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        JScrollPane readyQueueScrollPane = new JScrollPane(readyQueueList);
        readyQueueScrollPane.setPreferredSize(new Dimension(300, 100));

        for (Process process : processes) {
            JLabel processLabel = new JLabel("Process " + process.id);
            processLabel.setForeground(Color.GREEN);
            progressBarPanel.add(processLabel);

            JProgressBar progressBar = new JProgressBar();
            progressBar.setMaximum(process.burstTime);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressBar.setForeground(Color.GREEN);
            progressBar.setBackground(Color.BLACK);
            progressBar.setPreferredSize(new Dimension(250, 40)); // Adjust width and height
            progressBarPanel.add(progressBar);

            readyQueueModel.addElement("Process " + process.id);

            new Timer(1000 / process.burstTime, new ActionListener() {
                private int currentTime = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentTime < process.burstTime) {
                        currentTime++;
                        progressBar.setValue(currentTime);
                    } else {
                        ((Timer) e.getSource()).stop();
                        readyQueueModel.removeElement("Process " + process.id);
                    }
                }
            }).start();
        }

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.BLACK);
        rightPanel.add(progressBarPanel, BorderLayout.CENTER);
        rightPanel.add(readyQueueScrollPane, BorderLayout.SOUTH);

        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.WEST); // Add table and stats on the left
        frame.add(progressPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST); // Add progress bars and ready queue on the right
        frame.setVisible(true);
    }
}


