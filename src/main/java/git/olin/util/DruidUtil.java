package git.olin.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.log4j.Logger;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Druid工具类
 */
public class DruidUtil {
    public static DruidDataSource druidDataSource = null;
    private static Properties properties = new Properties();
    // 使用log4j中的Logger对象打印日志
    private static Logger logger = Logger.getLogger(DruidUtil.class.getName());

    // 静态代码块初始化加载驱动
    static {
        // 通过类加载器来获取流
        InputStream inputStream = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            // 通过流读取配置文件中的内容到集合中
            properties.load(inputStream);
            // 通过Druid工厂加载文件注册驱动，初始化池
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Druid连接池连接数据库
     * @return Connection对象
     */
    public static Connection getConnection() {
        try {
            return druidDataSource.getConnection();
        } catch (SQLException e) {
            logger.error("SQLException in DruidUtil", e);
        }
        return null;
    }

    /**
     * 数据库相关对象的释放
     *
     * @param statement：执行静态SQL语句并返回的结果对象
     * @param connection：与特定数据库的连接对象
     */
    private static void release(Statement statement, Connection connection) {
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
