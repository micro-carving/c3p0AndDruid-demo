package git.olin.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * C3P0工具类
 */
public class C3P0Util {
    // 创建c3p0连接，整个项目有一个连接池就可以了，设为static只实例化一次
    private static ComboPooledDataSource comboPooledDataSource;
    // 使用log4j中的Logger对象打印日志
    private static Logger logger = Logger.getLogger(C3P0Util.class.getName());

    static {
        // 使用默认的配置
        // comboPooledDataSource = new ComboPooledDataSource();
        // 使用自定义的配置
        comboPooledDataSource = new ComboPooledDataSource("mysql");
    }

    /**
     * 使用C3P0连接池连接数据库
     * @return Connection对象
     */
    public static Connection getConnection(){
        try {
            return comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            logger.error("SQLException in C3P0Util", e);
        }
        return null;
    }

    /**
     * 数据库相关对象的释放
     *
     * @param statement：执行静态SQL语句并返回的结果对象
     * @param connection：与特定数据库的连接对象
     */
    public static void release(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据库相关对象的释放，方法重载
     *
     * @param resultSet：数据集对象
     */
    public static void release(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        release(statement, connection);
    }
}
