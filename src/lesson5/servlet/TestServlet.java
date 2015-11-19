package lesson5.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int counter = 0;
	private Connection connection;
	private Statement statement;
	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	private Vector<String> sessions = new Vector<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Servlet initialization...");
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
			statement = connection.createStatement();
			statement.executeUpdate("create table if not exists addressbook (id integer primary key autoincrement, "
					+ "name varchar(20), surname varchar(20), address varchar(30))");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		PrintWriter p = response.getWriter();
		System.out.println("User in build" + sessions.get(0).toString());
		if (sessions.contains(request.getSession().getId())){
		
			
		p.println("<html>");
		p.println("<header>");
		p.println("</header>");
		p.println("<body>");
		p.println("<a> servlet </a>");
		p.println("<a> request =" + (counter++)+"</a>");
		p.println("<a> IP " + request.getRemoteAddr() + "</a>");
		p.println("<hr/>");
		p.println("<menu>");
		p.println("<li>");
		p.print("<a href = \""+request.getContextPath()+"/adduser.jsp\">"+"добавить"+"</a>");
		p.println("</li>");
		p.println("</menu>");
		

		p.print("<table border =1>");	
		ArrayList<Object[]> list= getFromDB();
		
		for (Object[] o:list){
			
			p.print("<tr>");
			p.println("<form action = \"Delete\" method = \"POST\">");
			
				for(int i=1;i<o.length ;i++)
				{
					p.print("<td>" + o[i].toString() +"</td>");
				}
				p.println("<td>" +"<input type= \"hidden\" name= \"index\" value=\""+o[0]+"\" >"+"</td>");
				p.println("<td>" +"<input type= \"submit\" value=\"удалить\" >"+"</td>");
	
			p.println("</form>");	
			p.print("</tr>");
			}

		p.print("</table>");
		p.println("</body>");
		p.println("</html>");
		p.flush();
		}
		else {response.sendError(401);}
		
	}
		

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if ((request.getParameter("log")!=null)&&(request.getParameter("second")!=null)&&(request.getParameter("log").equals("serg")))
		{
			sessions.add(request.getSession().getId());
			System.out.println("User " + sessions.get(0).toString());
		}
		
		if (sessions.contains(request.getSession().getId())){
		
			if(request.getParameter("name")!=null)
			{
					String s1 = request.getParameter("name");
					String s2 = request.getParameter("surname");
					String s3 = request.getParameter("city");
					
					try {
						if(request.getParameter("add-user")!=null)
						{		
						
						statement.executeUpdate("insert into addressbook(name, surname, address) values('"+s1+"' , '"+s2+"', '"+s3+"' ) ");
						request.getRequestDispatcher("/message.jsp").forward(request, response); 
					
						} else if(request.getParameter("some-where")!=null)
						{
							request.getRequestDispatcher("/SomeWhere.jsp").forward(request, response); 
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			else
			{doGet(request, response);}
		} else
		{
			response.sendError(401);
		}
		
	}
	
	public ArrayList<Object[]> getFromDB()
	{
		ArrayList<Object[]> list = new ArrayList<>();
		
		try {
			rs = statement.executeQuery("select * from addressbook");
			rsmd = rs.getMetaData();
			
			while(rs.next()){
				Object[] o = new Object[rsmd.getColumnCount()];
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{					
					o[i-1]=rs.getString(i);
				}
				list.add(o);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}

}
