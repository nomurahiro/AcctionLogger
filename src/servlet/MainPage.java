package servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import dao.ActivitesDAO;
import model.Activites;
/**
 * Servlet implementation class MainPage
 */
@WebServlet("/")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Default constructor.
	 */
	public MainPage() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// HttpSession繧､繝ｳ繧ｿ繝輔ぉ繝ｼ繧ｹ縺ｮ繧ｪ繝悶ず繧ｧ繧ｯ繝医ｒ蜿門ｾ�
		HttpSession session = request.getSession();
		// userid繝�繝ｼ繧ｿ繧痴ession繧ｹ繧ｳ繝ｼ繝励〒菫晏ｭ�
		String userid = (String) session.getAttribute("userid");
		if (userid == null) {
			// MainView繧定｡ｨ遉ｺ
			response.sendRedirect("/ActionLogger/login");
		} else {
			// MainView繧定｡ｨ遉ｺ
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainView.jsp");
			dispatcher.forward(request, response);
		}
		ActivitesDAO activitesDAO = new ActivitesDAO();
		List<Activites> actlist = activitesDAO.Get((String)(session.getAttribute("userid")));
		request.setAttribute("actList",actlist);
		List<Activites> actltyList = activitesDAO.lGet((String)(session.getAttribute("userid")));
		request.setAttribute("actLList",actltyList);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
