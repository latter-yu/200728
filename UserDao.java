package OnlineMusic.Dao;

import OnlineMusic.entity.User;
import OnlineMusic.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    // 有关用户的数据库操作

    public void register(User user) {
        // 注册
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement("insert into user values(null, ?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtils.getClose(connection, ps, null);
        }
    }

    public User login(User loginUser) {
        // 登录
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from user where username=? and password=?";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql); // 对 sql 语句的预编译
            ps.setString(1, loginUser.getUsername());
            ps.setString(2, loginUser.getPassword());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, resultSet);
        }
        return user;
    }

    /*public static void main(String[] args) {
        User user = new User();
        user.setUsername("kxy");
        user.setPassword("123");
        User loginUser = login(user);
        System.out.println(loginUser);
    }*/
}
