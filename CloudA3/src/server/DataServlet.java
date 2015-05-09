package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class DataServlet
 */
@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    Connection conn = DBConn.getConnection();
	    ResultSet rs = null;  
	    Statement stmt = null;
	    String sql = "select sum(number) as num_pos from pos;";
	    String sql1="select sum(number) as num_neg from neg;";
	    String sql2="select token from pos order by number desc limit 5;";
	    String sql3="select token from neg order by number asc limit 5;";
	    
	    
	    String num_pos = null;
	    String num_neg = null;
	    JSONArray pos_list = null;
	    JSONArray neg_list = null;
	    try {
         stmt=  conn.createStatement();
        stmt.execute(sql);
        rs=stmt.getResultSet();
        rs.next();
        
        num_pos=rs.getString("num_pos");
        
        System.out.println(num_pos);
        
        //stmt = conn.createStatement();
        stmt.execute(sql1);
        rs=stmt.getResultSet();
        rs.next();
        num_neg=rs.getString("num_neg");
        System.out.println(num_neg);
        //JSONObject neg = new JSONObject();
        //neg.put("neg_num", num_neg);
        
        //
        neg_list= new JSONArray();
        stmt.execute(sql2);
        rs=stmt.getResultSet();
        while(rs.next()){
          neg_list.put(rs.getString("token").toString());
        }
        System.out.println(neg_list);
        
        
        pos_list = new JSONArray();
        stmt.execute(sql3);
        rs=stmt.getResultSet();
        while(rs.next()){
          pos_list.put(rs.getString("token").toString());
        }
        System.out.println(pos_list);
        
        rs.close();
        stmt.close();
        conn.close();
        
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
	    
	    
	  JSONObject ret= new JSONObject();
	  try {
      ret.put("neg_num", num_neg);
      ret.put("pos_num", num_pos);
      ret.put("neg_list", neg_list);
      ret.put("pos_list", pos_list);
      
      System.out.println(ret);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
	  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
