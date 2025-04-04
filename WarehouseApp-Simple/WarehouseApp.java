import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class WarehouseApp extends JFrame implements ActionListener, FocusListener, ItemListener {
    private ArrayList<Product> products;
    private JTextField idField, nameField, quantityField, priceField;
    private JComboBox<String> productTypeComboBox;


    public WarehouseApp() {
        super("Warehouse App");
        initializeGUI();
        products = new ArrayList<>();
        enterProducts(); //defined products in the list
    } //warehouse app constructor


    private void initializeGUI() {
        // JFrame setup
        setSize(500, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Menu bar setup + options' menu
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("");

        // Set an icon for the menu
        ImageIcon menuIcon = new ImageIcon("icons/menuicon.png"); //enterthe actual path to your icon
        Image menuImage = menuIcon.getImage(); // Get the Image from the icon
        Image resizedImage = menuImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Resize the image
        ImageIcon resizedMenuIcon = new ImageIcon(resizedImage); // Create a new ImageIcon from the resized image
        optionsMenu.setIcon(resizedMenuIcon); // Set the resized icon for the menu


        JMenuItem addMenuItem = new JMenuItem("Add product");
        JMenuItem viewMenuItem = new JMenuItem("View products");
        JMenuItem searchMenuItem = new JMenuItem("Search products");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        optionsMenu.add(addMenuItem);
        optionsMenu.add(viewMenuItem);
        optionsMenu.add(searchMenuItem);
        optionsMenu.add(exitMenuItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);


        // Main panel with labels and fields
        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(173, 216, 230));  // Light Blue Color


        JLabel idLabel = new JLabel("ID:");
        mainPanel.add(idLabel);
        idField = new JTextField(5);
        mainPanel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        mainPanel.add(nameLabel);
        nameField = new JTextField(10);
        mainPanel.add(nameField);


        JLabel quantityLabel = new JLabel("Quantity:");
        mainPanel.add(quantityLabel);
        quantityField = new JTextField(10);
        mainPanel.add(quantityField);

        JLabel priceLabel = new JLabel("Price:");
        mainPanel.add(priceLabel);
        priceField = new JTextField(10);
        mainPanel.add(priceField);

        JLabel productTypeLabel = new JLabel("Product Type:");
        mainPanel.add(productTypeLabel);
        productTypeComboBox = new JComboBox<>(new String[]{"Refrigerated", "Dry Product"});
        mainPanel.add(productTypeComboBox);


        // add jButtons and set their colors

        JButton addButton = new JButton("Add product");
        JButton viewButton = new JButton("View products");
        JButton searchButton = new JButton("Search products");
        JButton editDeleteButton = new JButton("Edit/Delete product");

        addButton.setBackground(new Color(2, 31, 88));
        addButton.setOpaque(true);

        viewButton.setBackground(new Color(2, 31, 88));
        viewButton.setOpaque(true);

        searchButton.setBackground(new Color(2, 31, 88));
        searchButton.setOpaque(true);

        editDeleteButton.setBackground(new Color(2, 31, 88));
        editDeleteButton.setOpaque(true);


        // Adding elements to main panel

        mainPanel.add(addButton);
        mainPanel.add(viewButton);
        mainPanel.add(searchButton);
        mainPanel.add(editDeleteButton);


        // Adding components to the frame
        add(mainPanel, BorderLayout.CENTER);

        //to add Focuslistener to idField
        idField.addFocusListener(this);


        // Event listeners
        addMenuItem.addActionListener(this);
        viewMenuItem.addActionListener(this);
        searchMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        searchButton.addActionListener(this);
        editDeleteButton.addActionListener(e -> editDeleteProduct());


        setVisible(true);
    }

    //method with products

    private void enterProducts() {
        products.add(new RefrigeratedProduct(1, "Yogurt", 30, 1.50, 5));
        products.add(new RefrigeratedProduct(2, "Cheese", 25, 9.90, 4));
        products.add(new RefrigeratedProduct(3, "Pineapple", 10, 1.50, 5));
        products.add(new RefrigeratedProduct(4, "Grapes", 20, 1.50, 5));
        products.add(new RefrigeratedProduct(5, "Water", 50, 0.50, 5));
        products.add(new DryProduct(6, "Spaghetti", 90, 1.2, "Box"));
        products.add(new DryProduct(7, "Rice", 70, 1.5, "Box"));
        products.add(new DryProduct(8, "Olive Oil", 60, 13.90, "Pallet"));
        products.add(new DryProduct(9, "Potato", 70, 1.70, "Pallet"));
        products.add(new DryProduct(10, "Toilet Paper", 100, 3.70, "Pallet"));
        products.add(new RefrigeratedProduct(11, "Milk", 20, 1.80, 9));

    }

    // Add product method
    private void addProduct() {
        try {
            // Parse input fields (because in java you get the result as a string)
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            //to validate that quantity and price are not negative
            if (quantity < 0 || price < 0) {
                JOptionPane.showMessageDialog(this, "Quantity and Price cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get product type
            String productType = productTypeComboBox.getSelectedItem().toString();
            Product newProduct;

            if (productType.equals("Refrigerated")) {
                //check for temp of refrigerated prods (if user adds an invalid character for the tem, it will get the message to enter a valid no)
                try {
                    double storageTemp = Double.parseDouble(JOptionPane.showInputDialog("Enter the temperature:"));
                    newProduct = new RefrigeratedProduct(id, name, quantity, price, storageTemp);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid temperature. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                //packaging type for dry products + user cannot leave it empty. Must enter a pack.type
                String packagingType = JOptionPane.showInputDialog("Enter the packaging type:").trim();
                if (packagingType == null || packagingType.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Packaging type cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                newProduct = new DryProduct(id, name, quantity, price, packagingType);
            }

            //when products are added successfully
            products.add(newProduct);
            JOptionPane.showMessageDialog(this, "Product added successfully!");

            clearFields();  //clear fields after added it
            //if there is any number format error, it will show this message:
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid values for ID, Quantity, and Price.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error:  " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // View products method
    private void viewProducts() {
        //to create a new JFrame for the product list
        JFrame productListFrame = new JFrame("Products' List");
        productListFrame.setSize(400, 300);
        productListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //new JTextArea to display products
        JTextArea productTextArea = new JTextArea();
        productTextArea.setEditable(false);

        // Append all product details to the JTextArea
        String productList = "";
        for (Product product : products) {
            productList += product.toString() + "\n";
        }

        // Check if the list is empty
        if (productList.length() == 0) {
            productTextArea.setText("There is no product in the list.");
        } else {

            productTextArea.setText(productList.toString());
        }
        // Add the JTextArea to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(productTextArea);
        productListFrame.add(scrollPane);
        scrollPane.setBackground(new Color(173, 216, 230));

        // Make the product list window visible
        productListFrame.setVisible(true);
    }


    //search products method in a new window frame
    private void searchProductsWindow() {
        // new jFrame for searching
        JFrame searchFrame = new JFrame("Search Product");
        searchFrame.setSize(400, 200);
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.setLayout(new BorderLayout());

        // Panel for new jframe
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5));
        inputPanel.setBackground(new Color(173, 216, 230));

        JLabel idLabel = new JLabel("Enter Product ID: -> or");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Enter Product Name:");
        JTextField nameField = new JTextField();

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        // Adding FocusListener to the idField
        idField.addFocusListener(new FocusAdapter() {
        });
        nameField.addFocusListener(new FocusAdapter() {
        });


        // Search Button
        JButton searchButton = new JButton("Search");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Search button functionality
        searchButton.addActionListener(e -> {
            String idText = idField.getText();
            String nameText = nameField.getText();
            searchProducts(idText, nameText, resultArea);
        });

        // Add components to the search frame
        searchFrame.add(inputPanel, BorderLayout.NORTH);
        searchFrame.add(searchButton, BorderLayout.CENTER);
        searchFrame.add(scrollPane, BorderLayout.SOUTH);

        searchFrame.setVisible(true);
    }

    // Search logic to find product by ID or Name
    private void searchProducts(String idText, String nameText, JTextArea resultArea) {
        String searchResults = "";
        boolean found = false;

        for (Product product : products) {
            String productString = product.toString();

            // Check if ID or Name is contained in the product string
            if ((!idText.isEmpty() && productString.contains("ID: " + idText)) || (!nameText.isEmpty() && productString.toLowerCase().contains("Name: ".toLowerCase() + nameText.toLowerCase()))) {
                searchResults += productString + "\n";
                found = true;
            }
        }

        // If no product found, show a messagee
        if (!found) {
            searchResults += "No product found with the given ID or Name.";
        }

        // Display the search results in the result area
        resultArea.setText(searchResults);
    }


    // method for editing or deleting any product
    private void editDeleteProduct() {
        //display a message to user to enter prod.ID and exit the method if they enter an empty string or cancels it
        String idText = JOptionPane.showInputDialog("Enter Product ID:");
        if (idText == null || idText.trim().isEmpty()) return;

        //to declare a variable to save the product if edited or to delete it if match found
        Product productEdit = null;
        for (Product product : products) {
            if (String.valueOf(product.id).equals(idText)) {
                productEdit = product;
                break;
            }
        }
        //if product is found, as them to edit or del it. Display two options to choose
        if (productEdit != null) {
            Object[] options = {"Edit", "Delete"};
            int choice = JOptionPane.showOptionDialog(null, "Edit or delete a product?", "Option", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == 0) { // For editing
                JTextField nameField = new JTextField(productEdit.name);
                JTextField quantityField = new JTextField(String.valueOf(productEdit.quantity));
                JTextField priceField = new JTextField(String.valueOf(productEdit.price));
                //fields that can edit
                Object[] message = {"Name:", nameField, "Quantity:", quantityField, "Price:", priceField};

                int option = JOptionPane.showConfirmDialog(null, message, "Editing", JOptionPane.OK_CANCEL_OPTION);
                //if clicks ok, update the products
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        //get new values
                        String newName = nameField.getText().trim();
                        int newQuantity = Integer.parseInt(quantityField.getText().trim());
                        double newPrice = Double.parseDouble(priceField.getText().trim());
                        //check if name is empty; if yes, show error msg
                        if (newName.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "You must enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        //update the products with new values and display a success msg or error msg
                        productEdit.name = newName;
                        productEdit.quantity = newQuantity;
                        productEdit.price = newPrice;
                        JOptionPane.showMessageDialog(null, "The product is successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Wrong number!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (choice == 1) { //Delete product if choice is 1 and remove it from list
                products.remove(productEdit);
                JOptionPane.showMessageDialog(null, "The product is deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cannot find the product with this ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Exit application method
    private void exitApp() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // Clear input fields
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        productTypeComboBox.setSelectedIndex(0);
        idField.requestFocus();
    }

    //get the commands that are related to the action events
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add product")) {
            addProduct();
        } else if (command.equals("View products")) {
            viewProducts();
        } else if (command.equals("Search products")) {
            searchProductsWindow();
        } else if (command.equals("Edit product/Delete product")) {
            editDeleteProduct();
        } else if (command.equals("Exit")) {
            exitApp();
        }
    }


    @Override
    public void focusGained(FocusEvent e) {
        //change background color when focus- corresponding to focus listeners above
        idField.setBackground(Color.RED);

    }

    @Override
    public void focusLost(FocusEvent e) {
        //revert backgrd.color when loses focus for focus listeners
        idField.setBackground(Color.WHITE);
    }


    @Override
    public void itemStateChanged(ItemEvent e) {

    }


    // Main method
    public static void main(String[] args) {
        new WarehouseApp();
    } //end of my main method
}
