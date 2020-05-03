package com.infopulse.view;

import com.infopulse.model.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class TestThread extends Thread {
    private DBPool pool;
    private long workTime = 0;
    private long foundStr = 0;

    @Override
    public void run() {
        workTime = System.currentTimeMillis(); // Засекаем время
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Random rnd = new Random();// будем использовать как первичный ключ в запросе
        for (int i = 0; i < 100; i++) {
            try {
                con = pool.getConnection();// получем соединение к БД
                // отправляем запрос на парсинг и построение плана выполнения
                st = con.prepareStatement("SELECT a.*  FROM  a WHERE id =?");
                st.setObject(1, rnd.nextInt(10));
                rs = st.executeQuery();// выполняем запрос
                if (rs.next()) {
                    String tmp = (rs.getString(2));   // обрабатываем результат
                    if (tmp != null) {
                        foundStr++;
                    }
                }
            } catch (SQLException ex) {
                //ошибка при выполнении, выводим в консоль
                System.out.println("Pool " + pool + " exeption " + ex);
            } finally {
                // и в конце, аккуратно закрываем все использованные нами объекты
                try {
                    if (rs != null)
                        rs.close();
                } catch (SQLException e) {
                    //ignore
                }
                try {
                    if (st != null)
                        st.close();
                } catch (SQLException e) {
                    //ignore
                }
                try {
                    if (con != null)
                        pool.putConnection(con); // кладем соединение обратно в пул
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
        workTime = System.currentTimeMillis() - workTime; // получаем потраченное время
    }
}
