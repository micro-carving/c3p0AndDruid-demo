import git.olin.util.DruidUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static git.olin.util.DruidUtil.druidDataSource;
import static git.olin.util.DruidUtil.release;

public class DruidUtilTest {
    /**
     * 测试Druid是否能连接
     * @throws Exception
     */
    @Test
    public void testDruidUtil() throws Exception{
        Connection connection = druidDataSource.getConnection();
        System.out.println(connection);
    }

    /**
     * 查询语句
     * @throws Exception
     */
    @Test
    public void testSelect() throws Exception {
        Connection connection = DruidUtil.getConnection();
        if (connection != null) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from test.stu_info");
            ResultSet rs; //执行查询返回一个结果集(要取的数据)
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String stu_num = rs.getString("stu_num");
                String stu_name = rs.getString("stu_name");
                int stu_age = rs.getInt("stu_age");
                System.out.println(stu_num + " " + stu_name + " " + stu_age);
            }
            //关闭对象
            release(rs, preparedStatement, connection);
        }
    }
}
