package OnlineMusic.Dao;

import OnlineMusic.entity.Music;
import OnlineMusic.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicDao {
    //  有关音乐的数据库操作

    public List<Music> findMusic(){
        // 查询全部歌单
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Music> musicList = new ArrayList();

        try {
            String sql = "select * from music";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql); // 对 sql 语句的预编译
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getDate("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserid(resultSet.getInt("userid"));
                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, resultSet);
        }
        return musicList;
    }

    public Music findMusicById(int id){
        // 根据 id 查找音乐

        Music music = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            String sql = "select * from music where id=?";
            connection = DBUtils.getConnection();
            ps = connection.prepareStatement(sql); // 对 sql 语句的预编译
            ps.setInt(1, id);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getDate("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserid(resultSet.getInt("userid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, resultSet);
        }
        return music;
    }

    public static List<Music> ifMusic(String str){
        // 根据关键字查询歌单(不是精确查找，可能查出多首歌)

        List<Music> musicList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            String sql = "select * from music where title like BINARY '%" + str + "%'";
            connection = DBUtils.getConnection();
//            ps = connection.prepareStatement("select * from music where title like title=?"); // 模糊查询
//            ps.setString(1, str);
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery(); // 执行
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getDate("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserid(resultSet.getInt("userid"));
                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, resultSet);
        }
        return musicList;
    }

    public int insert(String title, String singer, String time, String url, int userid) {
        // 上传音乐

        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        int number = 0;
        try {
            ps = connection.prepareStatement("insert into music(title, singer, time, url, userid) values(?, ?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, singer);
            ps.setString(3, time);
            ps.setString(4, url);
            ps.setInt(5, userid);
            number = ps.executeUpdate();
            return number;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(connection, ps, null);
        }
        return 0;
    }
    public static void main(String[] args) {
//        List<Music> ret = findMusic();
//        System.out.println(ret);
//        Music music = findMusicById(1);
//        Music music1 = findMusicById(2);
//        System.out.println(music); // 能找到
//        System.out.println(music1); // null
        List<Music> musicList = ifMusic("年");
        System.out.println(musicList);
    }
}
