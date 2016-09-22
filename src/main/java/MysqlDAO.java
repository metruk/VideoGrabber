import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class MysqlDAO {
	
	private static Connection connection;
	private static final String URL = "jdbc:mysql://metruk.mysql.ukraine.com.ua:3306/metruk_livehd1";
	//private static final String URL = "jdbc:mysql://hostx.mysql.ukraine.com.ua:3306/hostx_footlivehd";
	private static final String USERNAME = "metruk_livehd1";
	private static final String PASSWORD = "wsuhu9ld";
	
	MysqlDAO(){
		do{
			try {
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			} catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException ex) {
				System.out.println("Link failure");
				ex.printStackTrace();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch (SQLException e) {
				System.out.println("sql");
			}
		}while(connection==null);
	}
	

	public Connection getConnection() {
		return connection;
	}
	
void insertTranslationQuery(String content,String postTitle, String postName) throws ParseException, InterruptedException {
	 String insertPlayerPage = "INSERT INTO wp_posts (post_author, post_date, post_date_gmt, post_content," +
			 " post_excerpt,post_title, post_status, comment_status, ping_status,post_name,to_ping,pinged," +
			 "post_modified, post_modified_gmt,post_content_filtered, guid,post_type)" +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			String currentDate = DateFormator.currentDateDAO();
			String currentGmtDate = DateFormator.currentDateGrinvichTime();
			
			PreparedStatement preparedStatement = getConnection().prepareStatement(insertPlayerPage);
			preparedStatement.setInt(1, 2);
			preparedStatement.setString(2, currentDate);
			preparedStatement.setString(3, currentGmtDate);
			preparedStatement.setString(4, content);
			preparedStatement.setString(5, "footlivehd.com");
			preparedStatement.setString(6, postTitle);
			preparedStatement.setString(7, "publish");
			preparedStatement.setString(8, "open");
			preparedStatement.setString(9, "open");
			preparedStatement.setString(10, postName);
			preparedStatement.setString(11, "");
			preparedStatement.setString(12, "");
			preparedStatement.setString(13, currentDate);
			preparedStatement.setString(14, currentGmtDate);
			preparedStatement.setString(15, "");
			preparedStatement.setString(16, "");
			preparedStatement.setString(17, "post");
			
			preparedStatement.execute();
			System.out.println("added");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	void insertIntoWpPostMeta(long id, String metaKey, String metaValue)throws ParseException {
		String THUMBNAIL_POST = "INSERT into wp_postmeta(post_id,meta_key,meta_value) VALUES (?,?,?);";
		try {
			PreparedStatement preparedStatement = null;
			preparedStatement = getConnection().prepareStatement(THUMBNAIL_POST);
			preparedStatement.setFloat(1, id);
			preparedStatement.setString(2, metaKey);
			preparedStatement.setString(3, metaValue);
			preparedStatement.execute();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
	int postedId(String newsTitle) throws SQLException {
		PreparedStatement preparedStatement = null;
		int min=0;
		int postId = 0;
		String SELECT_POSTED_POST_ID = "SELECT id FROM wp_posts WHERE post_title= ? AND post_status='publish'";
		preparedStatement = getConnection().prepareStatement(SELECT_POSTED_POST_ID);
		preparedStatement.setString(1, newsTitle);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
				postId = rs.getInt(1);
				min=postId;
		}
		preparedStatement.execute();
		System.out.println("Get id");
			return min;
	}
	
	void insertIntoPostRelationships(int objectId,int term)throws ParseException {
	String TERM_POST_PLAYER = "INSERT into wp_term_relationships(object_id,term_taxonomy_id) " +
				"VALUES (?,?);";
		try {
			PreparedStatement preparedStatement = null;
			preparedStatement = getConnection().prepareStatement(TERM_POST_PLAYER);
			preparedStatement.setInt(1, objectId);
			preparedStatement.setInt(2, term);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	boolean selectTitle(String newsTitle) throws SQLException {
		String SELECT_POSTED_POST_ID = "SELECT post_title FROM wp_posts WHERE post_title= ? AND post_status='publish'";
		boolean flag;
		String header = null;
		
		PreparedStatement preparedStatement = null;
		preparedStatement = getConnection().prepareStatement(SELECT_POSTED_POST_ID);
		preparedStatement.setString(1, newsTitle);
		
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
				header = rs.getString(1);
		}
		
		preparedStatement.execute();
		
		if(header==null){
			flag=true;
		}else{
			flag=false;
		}
	return flag;
	}
}
