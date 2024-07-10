package com.example.javaavneet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<Pizza> pizzaTable;
    @FXML
    private TableColumn<Pizza, Integer> orderIdCol;
    @FXML
    private TableColumn<Pizza, String> cnameCol;
    @FXML
    private TableColumn<Pizza, String> mobCol;
    @FXML
    private TableColumn<Pizza, String> sizeCol;
    @FXML
    private TableColumn<Pizza, String> toppingsCol;
    @FXML
    private TableColumn<Pizza, Double> totalCol;
    @FXML
    private TextField OrderId;
    @FXML
    private TextField Cname;
    @FXML
    private TextField Mob;
    @FXML
    private TextField Size;
    @FXML
    private TextField Toppings;
    @FXML
    private TextField Total;
    @FXML
    private Label welcomeText;

    private ObservableList<Pizza> list = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() {
        fetchData();
    }

    private void fetchData() {
        list.clear();

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_csd214_final";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM pizza";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderId = resultSet.getInt("OrderId");
                String cname = resultSet.getString("Cname");
                String mob = resultSet.getString("Mob");
                String size = resultSet.getString("Size");
                String toppings = resultSet.getString("Toppings");
                double total = resultSet.getDouble("Total");
                list.add(new Pizza(orderId, cname, mob, size, toppings, total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        cnameCol.setCellValueFactory(new PropertyValueFactory<>("cname"));
        mobCol.setCellValueFactory(new PropertyValueFactory<>("mob"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        toppingsCol.setCellValueFactory(new PropertyValueFactory<>("toppings"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        pizzaTable.setItems(list);
    }

    @FXML
    public void InsertData(ActionEvent actionEvent) {
        executeUpdateQuery("INSERT INTO pizza (Cname, Mob, Size, Toppings, Total) VALUES (?, ?, ?, ?, ?)");
    }

    @FXML
    public void UpdateData(ActionEvent actionEvent) {
        executeUpdateQuery("UPDATE pizza SET Cname = ?, Mob = ?, Size = ?, Toppings = ?, Total = ? WHERE OrderId = ?");
    }

    @FXML
    public void DeleteData(ActionEvent actionEvent) {
        int orderId = Integer.parseInt(OrderId.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_csd214_final";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM pizza WHERE OrderId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.execute();
            fetchData(); // Refresh table after deletion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void LoadData(ActionEvent actionEvent) {
        int orderId = Integer.parseInt(OrderId.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_csd214_final";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM pizza WHERE OrderId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String cname = resultSet.getString("Cname");
                String mob = resultSet.getString("Mob");
                String size = resultSet.getString("Size");
                String toppings = resultSet.getString("Toppings");
                double total = resultSet.getDouble("Total");

                Cname.setText(cname);
                Mob.setText(mob);
                Size.setText(size);
                Toppings.setText(toppings);
                Total.setText(String.valueOf(total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdateQuery(String query) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_csd214_final";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, Cname.getText());
            statement.setString(2, Mob.getText());
            statement.setString(3, Size.getText());
            statement.setString(4, Toppings.getText());

            // Parse Total as double
            try {
                double total = Double.parseDouble(Total.getText());
                statement.setDouble(5, total);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return; // Exit method or handle error condition
            }

            if (query.contains("UPDATE")) {
                statement.setInt(6, Integer.parseInt(OrderId.getText()));
            }

            statement.executeUpdate();
            fetchData(); // Refresh table after insertion or update

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
