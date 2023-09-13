package com.example.ams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager<T> {
    public final String DATABASE_URL;

    public DatabaseManager(String DATABASE_URL) {
        this.DATABASE_URL = DATABASE_URL;
    }
        public Boolean removeDataRow(T primaryKeyValue, String primaryKeyColumnName, String tableName) {
            try (Connection connection = DriverManager.getConnection(DATABASE_URL);
                 Statement statement = connection.createStatement()) {
                String deleteRowSQL = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumnName + " = '" + primaryKeyValue + "'";
                boolean rowDeleted = statement.executeUpdate(deleteRowSQL) > 0;
                if (rowDeleted) {
                    System.out.println("Row with " + primaryKeyColumnName + " = " + primaryKeyValue + " deleted successfully.");
                    return true;
                } else {
                    System.out.println("No rows were deleted. Row with " + primaryKeyColumnName + " = " + primaryKeyValue + " not found.");
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
                return false;
            }
        }
        public T getCellValue(String primaryKeyColumnName, String columnName, T primaryKeyValue, String tableName) {
            T cellValue = null;
            try (Connection connection = DriverManager.getConnection(DATABASE_URL);
                 Statement statement = connection.createStatement()) {
                String selectSQL = "SELECT " + columnName + " FROM " + tableName + " WHERE " + primaryKeyColumnName + " = '" + primaryKeyValue + "'";
                ResultSet resultSet = statement.executeQuery(selectSQL);
                if (resultSet.next()) {
                    cellValue = (T) resultSet.getObject(columnName);
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
            return cellValue;
        }
        public Boolean updateCellValue(String columnName, String primaryKeyColumn, T primaryKeyValue, T newValue, String tableName) {
            try (Connection connection = DriverManager.getConnection(DATABASE_URL);
                 Statement statement = connection.createStatement()) {
                String updateSQL = "UPDATE " + tableName + " SET " + columnName + " = '" + newValue + "'" + " WHERE " + primaryKeyColumn + " = '" + primaryKeyValue + "'";
                int rowsUpdated = statement.executeUpdate(updateSQL);
                if (rowsUpdated > 0) {
                    System.out.println("Cell value updated successfully.");
                    return true;
                } else {
                    System.out.println("No rows were updated. Row with " + primaryKeyColumn + " = " + primaryKeyValue + " not found.");
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
                return false;
            }
        }

        public int getNextPrimaryKey(String tableName, String primaryKeyColumn) {
            int nextPrimaryKey = 1; // Default value if no rows exist
            try (Connection connection = DriverManager.getConnection(DATABASE_URL);
                 Statement statement = connection.createStatement()) {
                String selectSQL = "SELECT COALESCE(MAX(CAST(" + primaryKeyColumn + " AS INT)), 0) + 1 AS next_key FROM " + tableName;
                ResultSet resultSet = statement.executeQuery(selectSQL);
                if (resultSet.next()) {
                    nextPrimaryKey = resultSet.getInt("next_key");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
            return nextPrimaryKey;
        }

        public int getRowCount(String tableName) {
            int rowCount = 0;
            try (Connection connection = DriverManager.getConnection(DATABASE_URL);
                 Statement statement = connection.createStatement()) {
                String query = "SELECT COUNT(*) as row_count FROM " + tableName;
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    rowCount = resultSet.getInt("row_count");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
            return rowCount;
        }

    public List<String> getDataFromColumn(String tableName, String columnName) {
        List<String> columnData = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {
                String sqlQuery = "SELECT " + columnName + " FROM " + tableName;
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {
                    String columnValue = resultSet.getString(columnName);
                    columnData.add(columnValue);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnData;
    }
}

