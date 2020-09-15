package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.ErrorViewData;
import model.User;
import model.ValidationKey;

//繝ｦ繝ｼ繧ｶ繝ｼ縺檎匳骭ｲ縺吶ｋ繝ｦ繝ｼ繧ｶ繝ｼ諠�蝣ｱ繧堤｢ｺ隱阪＠縺溷ｾ後�＾K繧偵け繝ｪ繝�繧ｯ縺励◆縺ｨ縺阪�ｮ蜃ｦ逅�
@WebServlet("/adduserconfirm")
public class AddUserConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddUserConfirm() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		// 繝輔か繝ｼ繝�縺九ｉ騾√ｉ繧後◆遒ｺ隱阪く繝ｼ縺御ｿ晏ｭ倥＠縺溘ｂ縺ｮ縺ｨ荳�閾ｴ縺吶ｋ縺狗｢ｺ隱�
		ValidationKey validationKey = (ValidationKey) session.getAttribute("validationKey");
		if (!req.getParameter("vKey").equals(validationKey.getValue())) {
			// 荳�閾ｴ縺励↑縺九▲縺溘�ｮ縺ｧ縲√そ繝�繧ｷ繝ｧ繝ｳ繧ｹ繧ｳ繝ｼ繝励↓菫晏ｭ倥＠縺溘く繝ｼ繧堤�ｴ譽�縺励�√お繝ｩ繝ｼ繝壹�ｼ繧ｸ縺ｫ
			session.removeAttribute("validationKey");
			
			//陦ｨ遉ｺ繝�繝ｼ繧ｿ繧堤畑諢上☆繧�
			ErrorViewData errorData = new ErrorViewData("蝠城｡後′逋ｺ逕溘＠縺ｾ縺励◆縲�",
													"繝医ャ繝励↓謌ｻ繧�","/ActionLogger/");
			req.setAttribute("errorData", errorData);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/errorView.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		// status縺慶onfirmed縺ｮ蝣ｴ蜷�
		// 譛ｬ譚･縺ｯ豁｣蠖薙↑逋ｻ骭ｲ遒ｺ隱阪〒縺ゅｋ縺薙→繧偵メ繧ｧ繝�繧ｯ縺吶ｋ縺ｹ縺阪〒縺ゅｋ縺後�√→繧翫≠縺医★Omit
		if (req.getParameter("status").equals("confirmed")) {
			// 繧ｻ繝�繧ｷ繝ｧ繝ｳ繧ｹ繧ｳ繝ｼ繝励↓菫晏ｭ倥＠縺ｦ縺�縺溘�．B逋ｻ骭ｲ蜑阪�ｮ繝ｦ繝ｼ繧ｶ繝ｼ諠�蝣ｱ繧貞叙蠕�
			User user = (User) session.getAttribute("userToAdd");
			UserDAO userDAO = new UserDAO();
			userDAO.save(user); // DB縺ｫ菫晏ｭ�
			// TODO 荳ｻ繧ｭ繝ｼ縺ｮ驥崎､�縺ｧ菫晏ｭ倥〒縺阪↑縺九▲縺溷�ｴ蜷医�ｮ蜃ｦ逅�繧定ｿｽ蜉�

		}
		// DB縺ｸ縺ｮ菫晏ｭ倥′謌仙粥縺励◆繧ゅ�ｮ縺ｨ縺励※縲√Ο繧ｰ繧､繝ｳ繝壹�ｼ繧ｸ縺ｫ驕ｷ遘ｻ
		resp.sendRedirect("/ActionLogger/login");
	}
}
