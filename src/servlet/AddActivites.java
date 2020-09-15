package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ActivitesDAO;
import model.Activites;
/**
 * Servlet implementation class AddActivites
 */
@WebServlet("/addactivites")
public class AddActivites extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddActivites() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		Activites act = new Activites();
		act.setDate(req.getParameter("date"));
		act.setStime(req.getParameter("start_time"));
		act.setEtime(req.getParameter("end_time"));
		act.setPlace(req.getParameter("place"));
		act.setReason(req.getParameter("reason"));
		act.setRemarks(req.getParameter("remarks"));
		act.setUserid((String) session.getAttribute("userid"));
		ActivitesDAO ActivitesDAO = new ActivitesDAO();
		ActivitesDAO.set(act);
		resp.sendRedirect("/ActionLogger/");
	}
}
