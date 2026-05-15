package dashboard;

import auth.LoginPage;
import view.GUIStyle;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RestaurantDashboard extends JFrame {

    private DefaultListModel<String> menuModel;
    private DefaultListModel<String> customerModel;

    private JTextArea notificationArea;
    private JTextArea orderReceiptArea;
    private JTextArea summaryArea;
    private JTextArea salesHistoryArea;

    private JComboBox<String> orderComboBox;

    private ArrayList<String> menuItems;
    private ArrayList<String> customers;
    private ArrayList<String> dailySalesHistory;

    private ArrayList<String> graphItemNames;
    private ArrayList<Integer> graphSales;

    private double totalDailyRevenue = 0.0;

    private GraphPanel graphPanel;

    public RestaurantDashboard() {

        menuItems = new ArrayList<>();
        customers = new ArrayList<>();
        dailySalesHistory = new ArrayList<>();

        graphItemNames = new ArrayList<>();
        graphSales = new ArrayList<>();

        addDefaultItem("Burger - £8.99", 45);
        addDefaultItem("Pizza - £12.50", 35);
        addDefaultItem("Coffee - £3.20", 60);
        addDefaultItem("Cake - £4.50", 25);

        setTitle("Smart Restaurant Ordering System Dashboard");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        GUIStyle.styleFrame(this);

        JLabel title = new JLabel("Smart Restaurant Sales Dashboard", JLabel.CENTER);
        GUIStyle.styleTitle(title);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        GUIStyle.styleTabbedPane(tabbedPane);

        tabbedPane.add("Sales Graph", createGraphPanel());
        tabbedPane.add("Buy Food", createBuyFoodPanel());
        tabbedPane.add("Online Order", createOnlineOrderPanel());
        tabbedPane.add("Add / Delete Items", createAddItemPanel());
        tabbedPane.add("Register Customer", createCustomerPanel());
        tabbedPane.add("Notifications", createNotificationPanel());
        tabbedPane.add("Daily Sales History", createSalesHistoryPanel());

        add(tabbedPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        GUIStyle.styleButton(logoutButton);
        logoutButton.addActionListener(e -> logout());
        add(logoutButton, BorderLayout.SOUTH);
    }

    private void addDefaultItem(String item, int sales) {
        menuItems.add(item);
        graphItemNames.add(extractItemName(item));
        graphSales.add(sales);
    }

    private JPanel createGraphPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        GUIStyle.stylePanel(panel);

        graphPanel = new GraphPanel();
        panel.add(graphPanel, BorderLayout.CENTER);

        summaryArea = new JTextArea();
        GUIStyle.styleTextArea(summaryArea);
        summaryArea.setEditable(false);
        updateSummary();

        panel.add(summaryArea, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBuyFoodPanel() {

        JPanel panel = new JPanel();
        GUIStyle.stylePanel(panel);
        panel.setLayout(null);

        JLabel title = new JLabel("Buy Food / Place Order");
        GUIStyle.styleTitle(title);
        title.setBounds(390, 20, 400, 30);
        panel.add(title);

        JLabel itemLabel = new JLabel("Select Item:");
        GUIStyle.styleLabel(itemLabel);
        itemLabel.setBounds(270, 90, 120, 25);
        panel.add(itemLabel);

        orderComboBox = new JComboBox<>();
        orderComboBox.setFont(GUIStyle.normalFont());
        refreshOrderItems();
        orderComboBox.setBounds(420, 90, 300, 25);
        panel.add(orderComboBox);

        JLabel quantityLabel = new JLabel("Quantity:");
        GUIStyle.styleLabel(quantityLabel);
        quantityLabel.setBounds(270, 130, 120, 25);
        panel.add(quantityLabel);

        JTextField quantityField = new JTextField();
        GUIStyle.styleTextField(quantityField);
        quantityField.setBounds(420, 130, 300, 25);
        panel.add(quantityField);

        JButton buyButton = new JButton("Buy Now");
        GUIStyle.styleSuccessButton(buyButton);
        buyButton.setBounds(420, 180, 130, 35);
        panel.add(buyButton);

        JButton printButton = new JButton("Print Receipt");
        GUIStyle.styleButton(printButton);
        printButton.setBounds(570, 180, 150, 35);
        panel.add(printButton);

        orderReceiptArea = new JTextArea();
        GUIStyle.styleTextArea(orderReceiptArea);
        orderReceiptArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(orderReceiptArea);
        scrollPane.setBounds(250, 240, 680, 330);
        panel.add(scrollPane);

        buyButton.addActionListener(e -> {
            String selectedItem = (String) orderComboBox.getSelectedItem();
            String quantityText = quantityField.getText().trim();

            if (selectedItem == null || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select item and enter quantity.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                    return;
                }

                double price = extractPrice(selectedItem);
                double total = price * quantity;

                updateSalesData(selectedItem, quantity);
                addToSalesHistory(selectedItem, quantity, price, total, "Counter Order");

                String receipt =
                        "===== COUNTER ORDER RECEIPT =====\n" +
                                "Order Type: Counter Order\n" +
                                "Item: " + selectedItem + "\n" +
                                "Quantity: " + quantity + "\n" +
                                "Unit Price: £" + String.format("%.2f", price) + "\n" +
                                "Total Bill: £" + String.format("%.2f", total) + "\n" +
                                "Status: Order Placed Successfully\n" +
                                "Time: " + getCurrentTime() + "\n\n";

                orderReceiptArea.append(receipt);
                showNotification("Counter order placed: " + selectedItem + " x " + quantity);
                quantityField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid quantity.");
            }
        });

        printButton.addActionListener(e -> printReceipt(orderReceiptArea));

        return panel;
    }

    private JPanel createOnlineOrderPanel() {

        JPanel panel = new JPanel();
        GUIStyle.stylePanel(panel);
        panel.setLayout(null);

        JLabel title = new JLabel("Online Customer Order");
        GUIStyle.styleTitle(title);
        title.setBounds(420, 20, 400, 30);
        panel.add(title);

        JLabel nameLabel = new JLabel("Customer Name:");
        GUIStyle.styleLabel(nameLabel);
        nameLabel.setBounds(250, 80, 150, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        GUIStyle.styleTextField(nameField);
        nameField.setBounds(430, 80, 300, 25);
        panel.add(nameField);

        JLabel addressLabel = new JLabel("Delivery Address:");
        GUIStyle.styleLabel(addressLabel);
        addressLabel.setBounds(250, 120, 150, 25);
        panel.add(addressLabel);

        JTextField addressField = new JTextField();
        GUIStyle.styleTextField(addressField);
        addressField.setBounds(430, 120, 300, 25);
        panel.add(addressField);

        JLabel phoneLabel = new JLabel("Phone:");
        GUIStyle.styleLabel(phoneLabel);
        phoneLabel.setBounds(250, 160, 150, 25);
        panel.add(phoneLabel);

        JTextField phoneField = new JTextField();
        GUIStyle.styleTextField(phoneField);
        phoneField.setBounds(430, 160, 300, 25);
        panel.add(phoneField);

        JLabel itemLabel = new JLabel("Select Item:");
        GUIStyle.styleLabel(itemLabel);
        itemLabel.setBounds(250, 200, 150, 25);
        panel.add(itemLabel);

        JComboBox<String> onlineItemBox = new JComboBox<>();
        onlineItemBox.setFont(GUIStyle.normalFont());

        for (String item : menuItems) {
            onlineItemBox.addItem(item);
        }

        onlineItemBox.setBounds(430, 200, 300, 25);
        panel.add(onlineItemBox);

        JLabel quantityLabel = new JLabel("Quantity:");
        GUIStyle.styleLabel(quantityLabel);
        quantityLabel.setBounds(250, 240, 150, 25);
        panel.add(quantityLabel);

        JTextField quantityField = new JTextField();
        GUIStyle.styleTextField(quantityField);
        quantityField.setBounds(430, 240, 300, 25);
        panel.add(quantityField);

        JButton orderButton = new JButton("Place Online Order");
        GUIStyle.styleSuccessButton(orderButton);
        orderButton.setBounds(430, 290, 180, 35);
        panel.add(orderButton);

        JButton printOnlineButton = new JButton("Print Online Receipt");
        GUIStyle.styleButton(printOnlineButton);
        printOnlineButton.setBounds(630, 290, 190, 35);
        panel.add(printOnlineButton);

        JTextArea onlineReceiptArea = new JTextArea();
        GUIStyle.styleTextArea(onlineReceiptArea);
        onlineReceiptArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(onlineReceiptArea);
        scrollPane.setBounds(220, 350, 760, 260);
        panel.add(scrollPane);

        orderButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            String item = (String) onlineItemBox.getSelectedItem();
            String quantityText = quantityField.getText().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || item == null || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete all online order details.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                    return;
                }

                double price = extractPrice(item);
                double total = price * quantity;

                updateSalesData(item, quantity);
                addToSalesHistory(item, quantity, price, total, "Online Delivery");

                String receipt =
                        "===== ONLINE ORDER RECEIPT =====\n" +
                                "Order Type: Online Delivery\n" +
                                "Customer: " + name + "\n" +
                                "Phone: " + phone + "\n" +
                                "Address: " + address + "\n" +
                                "Item: " + item + "\n" +
                                "Quantity: " + quantity + "\n" +
                                "Unit Price: £" + String.format("%.2f", price) + "\n" +
                                "Total Bill: £" + String.format("%.2f", total) + "\n" +
                                "Status: Order Confirmed\n" +
                                "Time: " + getCurrentTime() + "\n\n";

                onlineReceiptArea.append(receipt);

                if (orderReceiptArea != null) {
                    orderReceiptArea.append(receipt);
                }

                showNotification("Online order placed by " + name);

                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                quantityField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid quantity.");
            }
        });

        printOnlineButton.addActionListener(e -> printReceipt(onlineReceiptArea));

        return panel;
    }

    private JPanel createAddItemPanel() {

        JPanel panel = new JPanel();
        GUIStyle.stylePanel(panel);
        panel.setLayout(null);

        JLabel title = new JLabel("Add / Delete Menu Item");
        GUIStyle.styleTitle(title);
        title.setBounds(410, 20, 400, 30);
        panel.add(title);

        JLabel nameLabel = new JLabel("Item Name:");
        GUIStyle.styleLabel(nameLabel);
        nameLabel.setBounds(260, 90, 120, 25);
        panel.add(nameLabel);

        JTextField itemNameField = new JTextField();
        GUIStyle.styleTextField(itemNameField);
        itemNameField.setBounds(400, 90, 260, 25);
        panel.add(itemNameField);

        JLabel priceLabel = new JLabel("Price:");
        GUIStyle.styleLabel(priceLabel);
        priceLabel.setBounds(260, 130, 120, 25);
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        GUIStyle.styleTextField(priceField);
        priceField.setBounds(400, 130, 260, 25);
        panel.add(priceField);

        JButton addButton = new JButton("Add Item");
        GUIStyle.styleSuccessButton(addButton);
        addButton.setBounds(400, 180, 130, 35);
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete Item");
        GUIStyle.styleButton(deleteButton);
        deleteButton.setBounds(550, 180, 140, 35);
        panel.add(deleteButton);

        menuModel = new DefaultListModel<>();

        for (String item : menuItems) {
            menuModel.addElement(item);
        }

        JList<String> menuList = new JList<>(menuModel);
        menuList.setFont(GUIStyle.normalFont());
        menuList.setBackground(GUIStyle.CARD_BG);
        menuList.setForeground(GUIStyle.TEXT);

        JScrollPane scrollPane = new JScrollPane(menuList);
        scrollPane.setBounds(250, 240, 660, 300);
        panel.add(scrollPane);

        addButton.addActionListener(e -> {
            String itemName = itemNameField.getText().trim();
            String price = priceField.getText().trim();

            if (itemName.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter item name and price.");
                return;
            }

            try {
                double priceValue = Double.parseDouble(price);

                if (priceValue <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                    return;
                }

                String item = itemName + " - £" + String.format("%.2f", priceValue);

                menuItems.add(item);
                menuModel.addElement(item);
                graphItemNames.add(itemName);
                graphSales.add(0);

                refreshOrderItems();
                updateSummary();
                updateSalesHistoryArea();

                if (graphPanel != null) {
                    graphPanel.repaint();
                }

                showNotification("New menu item added to menu and graph: " + item);

                itemNameField.setText("");
                priceField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedIndex = menuList.getSelectedIndex();

            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select an item to delete.");
                return;
            }

            String selectedItem = menuModel.getElementAt(selectedIndex);
            String itemName = extractItemName(selectedItem);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete " + itemName + "?",
                    "Delete Item",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                menuItems.remove(selectedItem);
                menuModel.remove(selectedIndex);

                int graphIndex = graphItemNames.indexOf(itemName);

                if (graphIndex >= 0) {
                    graphItemNames.remove(graphIndex);
                    graphSales.remove(graphIndex);
                }

                refreshOrderItems();
                updateSummary();
                updateSalesHistoryArea();

                if (graphPanel != null) {
                    graphPanel.repaint();
                }

                showNotification("Menu item deleted: " + itemName);
            }
        });

        return panel;
    }

    private JPanel createCustomerPanel() {

        JPanel panel = new JPanel();
        GUIStyle.stylePanel(panel);
        panel.setLayout(null);

        JLabel title = new JLabel("Register New Customer");
        GUIStyle.styleTitle(title);
        title.setBounds(410, 20, 400, 30);
        panel.add(title);

        JLabel nameLabel = new JLabel("Customer Name:");
        GUIStyle.styleLabel(nameLabel);
        nameLabel.setBounds(260, 90, 140, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        GUIStyle.styleTextField(nameField);
        nameField.setBounds(430, 90, 270, 25);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        GUIStyle.styleLabel(emailLabel);
        emailLabel.setBounds(260, 130, 140, 25);
        panel.add(emailLabel);

        JTextField emailField = new JTextField();
        GUIStyle.styleTextField(emailField);
        emailField.setBounds(430, 130, 270, 25);
        panel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        GUIStyle.styleLabel(phoneLabel);
        phoneLabel.setBounds(260, 170, 140, 25);
        panel.add(phoneLabel);

        JTextField phoneField = new JTextField();
        GUIStyle.styleTextField(phoneField);
        phoneField.setBounds(430, 170, 270, 25);
        panel.add(phoneField);

        JButton registerButton = new JButton("Register Customer");
        GUIStyle.styleSuccessButton(registerButton);
        registerButton.setBounds(430, 220, 180, 35);
        panel.add(registerButton);

        customerModel = new DefaultListModel<>();

        JList<String> customerList = new JList<>(customerModel);
        customerList.setFont(GUIStyle.normalFont());
        customerList.setBackground(GUIStyle.CARD_BG);
        customerList.setForeground(GUIStyle.TEXT);

        JScrollPane scrollPane = new JScrollPane(customerList);
        scrollPane.setBounds(250, 280, 660, 260);
        panel.add(scrollPane);

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all customer details.");
            } else {
                String customer = name + " | " + email + " | " + phone;

                customers.add(customer);
                customerModel.addElement(customer);

                showNotification("New customer registered: " + name);

                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
            }
        });

        return panel;
    }

    private JPanel createNotificationPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        GUIStyle.stylePanel(panel);

        JLabel title = new JLabel("System Notifications", JLabel.CENTER);
        GUIStyle.styleTitle(title);
        panel.add(title, BorderLayout.NORTH);

        notificationArea = new JTextArea();
        GUIStyle.styleTextArea(notificationArea);
        notificationArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(notificationArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton clearButton = new JButton("Clear Notifications");
        GUIStyle.styleButton(clearButton);
        clearButton.addActionListener(e -> notificationArea.setText(""));

        panel.add(clearButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSalesHistoryPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        GUIStyle.stylePanel(panel);

        JLabel title = new JLabel("Whole Day Sales History", JLabel.CENTER);
        GUIStyle.styleTitle(title);
        panel.add(title, BorderLayout.NORTH);

        salesHistoryArea = new JTextArea();
        GUIStyle.styleTextArea(salesHistoryArea);
        salesHistoryArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(salesHistoryArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Sales History");
        GUIStyle.styleButton(refreshButton);
        refreshButton.addActionListener(e -> updateSalesHistoryArea());

        panel.add(refreshButton, BorderLayout.SOUTH);

        updateSalesHistoryArea();

        return panel;
    }

    private void updateSalesData(String selectedItem, int quantity) {

        String itemName = extractItemName(selectedItem);
        int index = graphItemNames.indexOf(itemName);

        if (index >= 0) {
            graphSales.set(index, graphSales.get(index) + quantity);
        } else {
            graphItemNames.add(itemName);
            graphSales.add(quantity);
        }

        if (graphPanel != null) {
            graphPanel.repaint();
        }

        updateSummary();
        updateSalesHistoryArea();
    }

    private void addToSalesHistory(String item, int quantity, double price, double total, String orderType) {

        totalDailyRevenue += total;

        String record =
                getCurrentTime() +
                        " | Type: " + orderType +
                        " | Item: " + item +
                        " | Qty: " + quantity +
                        " | Unit Price: £" + String.format("%.2f", price) +
                        " | Total: £" + String.format("%.2f", total);

        dailySalesHistory.add(record);

        updateSalesHistoryArea();
        updateSummary();
    }

    private void updateSalesHistoryArea() {

        if (salesHistoryArea != null) {
            StringBuilder builder = new StringBuilder();

            builder.append("===== WHOLE DAY SALES HISTORY =====\n\n");

            if (dailySalesHistory.isEmpty()) {
                builder.append("No sales recorded yet.\n");
            } else {
                for (String record : dailySalesHistory) {
                    builder.append(record).append("\n");
                }
            }

            builder.append("\n----------------------------------\n");
            builder.append("Total Daily Revenue: £")
                    .append(String.format("%.2f", totalDailyRevenue))
                    .append("\n");

            builder.append("Total Items Sold: ")
                    .append(getTotalItemsSold())
                    .append("\n");

            salesHistoryArea.setText(builder.toString());
        }
    }

    private void updateSummary() {

        if (summaryArea != null) {
            StringBuilder builder = new StringBuilder();

            builder.append("Dashboard Summary\n\n");

            for (int i = 0; i < graphItemNames.size(); i++) {
                builder.append(graphItemNames.get(i))
                        .append(" Sales: ")
                        .append(graphSales.get(i))
                        .append("\n");
            }

            builder.append("\nTotal Daily Revenue: £")
                    .append(String.format("%.2f", totalDailyRevenue))
                    .append("\n\n");

            builder.append("Graph updates automatically when food is purchased.");

            summaryArea.setText(builder.toString());
        }
    }

    private int getTotalItemsSold() {

        int total = 0;

        for (int sale : graphSales) {
            total += sale;
        }

        return total;
    }

    private void refreshOrderItems() {

        if (orderComboBox != null) {
            orderComboBox.removeAllItems();

            for (String item : menuItems) {
                orderComboBox.addItem(item);
            }
        }
    }

    private String extractItemName(String item) {

        if (item.contains(" - £")) {
            return item.substring(0, item.indexOf(" - £"));
        }

        return item;
    }

    private double extractPrice(String item) {

        String priceText = item.substring(item.indexOf("£") + 1);
        return Double.parseDouble(priceText);
    }

    private String getCurrentTime() {

        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    private void printReceipt(JTextArea receiptArea) {

        try {
            if (receiptArea == null || receiptArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No receipt available to print.");
                return;
            }

            boolean printed = receiptArea.print();

            if (printed) {
                showNotification("Receipt printed successfully.");
            } else {
                showNotification("Receipt printing cancelled.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Printing failed: " + e.getMessage());
        }
    }

    private void showNotification(String message) {

        if (notificationArea != null) {
            notificationArea.append("NOTIFICATION: " + message + "\n");
        }

        JOptionPane.showMessageDialog(this, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();

            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        }
    }

    class GraphPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            setBackground(GUIStyle.PANEL_BG);

            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.setColor(Color.WHITE);

            int x = 90;
            int bottom = 360;
            int barWidth = 60;

            g2.drawLine(60, 60, 60, bottom);
            g2.drawLine(60, bottom, 1050, bottom);

            int maxSales = 1;

            for (int sale : graphSales) {
                if (sale > maxSales) {
                    maxSales = sale;
                }
            }

            double scale = 260.0 / maxSales;

            for (int i = 0; i < graphItemNames.size(); i++) {

                int sale = graphSales.get(i);
                int barHeight = (int) (sale * scale);

                if (barHeight == 0) {
                    barHeight = 5;
                }

                g2.setColor(GUIStyle.PRIMARY);

                g2.fillRoundRect(
                        x,
                        bottom - barHeight,
                        barWidth,
                        barHeight,
                        15,
                        15
                );

                g2.setColor(Color.WHITE);

                g2.drawString(
                        String.valueOf(sale),
                        x + 20,
                        bottom - barHeight - 10
                );

                String itemName = graphItemNames.get(i);

                if (itemName.length() > 8) {
                    itemName = itemName.substring(0, 8);
                }

                g2.drawString(itemName, x, bottom + 25);

                x += 95;
            }

            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.setColor(GUIStyle.PRIMARY);
            g2.drawString("Dynamic Restaurant Sales Analytics", 360, 35);
        }
    }
}