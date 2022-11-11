package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLHelper {

    private static QueryRunner runner  = new QueryRunner();
    private static  String url = System.getProperty("db.url");
    private static  String userName = System.getProperty("db.username");
    private static  String password = System.getProperty("db.password");
//     private static String url = "jdbc:mysql://localhost:3306/app";
//    private static String url = "jdbc:postgresql://localhost:5432/app";


    public SQLHelper(){
    }


    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, userName, password);
    }


    public static DataHelper.CreditCardData getCreditCardData() {
        var cardDataSQL =  "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, cardDataSQL,
                    new BeanHandler<>(DataHelper.CreditCardData.class));
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static DataHelper.PaymentCardData getPaymentCardData() {
        var cardDataSQL =  "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, cardDataSQL,
                    new BeanHandler<>(DataHelper.PaymentCardData.class));
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static DataHelper.TableOrderEntity getTableOrderEntity() {
        var orderEntityDataSQL =  "SELECT * FROM order_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, orderEntityDataSQL,
                    new BeanHandler<>(DataHelper.TableOrderEntity.class));
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    @SneakyThrows
    public static void cleanDatabase() {
        var conn = getConn();
        runner.execute(conn, "DELETE FROM order_entity");
        runner.execute(conn, "DELETE FROM payment_entity");
        runner.execute(conn, "DELETE FROM credit_request_entity");
    }
}
