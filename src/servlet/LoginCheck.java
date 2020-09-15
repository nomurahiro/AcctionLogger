package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

@WebServlet("/logincheck")
public class LoginCheck extends HttpServlet {

	// Get繝｡繧ｽ繝�繝峨〒縺薙�ｮ繝壹�ｼ繧ｸ縺悟他縺ｰ繧後ｋ縺薙→縺ｯ縺ｪ縺�縲ゆｸ肴ｭ｣蜃ｦ逅�縺ｮ逍代＞繧ゅ≠繧九′縲√→繧翫≠縺医★繝ｭ繧ｰ繧､繝ｳ繝輔か繝ｼ繝�縺ｫ繝ｪ繝�繧､繝ｬ繧ｯ繝�
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/LoginView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�繝峨�ｻ繧ｹ繧ｿ繝�
		req.setCharacterEncoding("UTF-8");
		String passwordHash = "";
		try {
			// 繝代せ繝ｯ繝ｼ繝峨�ｮ繝上ャ繧ｷ繝･蛹�
			String rawPassword = req.getParameter("password");
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(rawPassword.getBytes("utf8"));
			passwordHash = String.format("%064x", new BigInteger(1, digest.digest()));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// DB縺九ｉ繝ｦ繝ｼ繧ｶ繝ｼ繧貞叙蠕�
		UserDAO userDAO = new UserDAO();
		User user = userDAO.get(req.getParameter("userid"));

		// DB縺九ｉ縺ｮ蜿門ｾ励′謌仙粥 AND 繝代せ繝ｯ繝ｼ繝峨ワ繝�繧ｷ繝･縺悟粋閾ｴ
		if (user != null && user.getPwdHash().equals(passwordHash)) {
			HttpSession session = req.getSession();
			session.setAttribute("userid", user.getUserId());
			resp.sendRedirect("/ActionLogger/");

		} else {
			// TODO 繝ｭ繧ｰ繧､繝ｳ繧ｨ繝ｩ繝ｼ縺ｫ繝ｪ繝�繧､繝ｬ繧ｯ繝�
			// 繧ｨ繝ｩ繝ｼ逕ｻ髱｢縺後∪縺�縺ｪ縺�縺ｮ縺ｧ繝ｭ繧ｰ繧､繝ｳ逕ｻ髱｢縺ｫ謌ｻ縺�
			resp.sendRedirect("/ActionLogger/login");
		}
	}
}
