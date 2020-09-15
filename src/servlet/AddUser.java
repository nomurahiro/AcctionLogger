package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ErrorViewData;
import model.InputCheckException;
import model.User;
import model.ValidationKey;

//static import
import static model.InputChecker.checkLongInput;
import static model.InputChecker.checkPhoneNumber;
import static model.InputChecker.checkMailAddress;


//繝ｦ繝ｼ繧ｶ繝ｼ霑ｽ蜉�讖溯�ｽ
//GET縺ｧ繧｢繧ｯ繧ｻ繧ｹ縺輔ｌ縺溷�ｴ蜷医��逋ｻ骭ｲ繝輔か繝ｼ繝�繧定｡ｨ遉ｺ
//POST縺ｧ繧｢繧ｯ繧ｻ繧ｹ縺輔ｌ縺溷�ｴ蜷医��逋ｻ骭ｲ繝輔か繝ｼ繝�縺九ｉ騾√ｉ繧後◆繝�繝ｼ繧ｿ繧貞�ｦ逅�
//逋ｻ骭ｲ繝輔か繝ｼ繝�縺九ｉ騾√ｉ繧後◆繝�繝ｼ繧ｿ縺ｯ縲．B菫晏ｭ伜�呵｣懊→縺励※session螟画焚縺ｫ菫晏ｭ�
@WebServlet("/adduser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddUser() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 豁｣蠖薙↑繝輔か繝ｼ繝�縺九ｉ騾√ｉ繧後◆繝�繝ｼ繧ｿ縺ｧ縺ゅｋ縺薙→繧堤｢ｺ隱阪☆繧九◆繧√�ｮ繧ｭ繝ｼ縺ｮ逕滓��
		ValidationKey validationKey = new ValidationKey();
		try {
			Random random = new Random();
			String randomStr = String.valueOf(random.nextLong());
			MessageDigest validation = MessageDigest.getInstance("MD5");
			validation.reset();
			validation.update(randomStr.getBytes("utf8"));
			String vkey = String.format("%032x", new BigInteger(1, validation.digest()));
			validationKey.setValue(vkey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 繝輔か繝ｼ繝�遒ｺ隱阪く繝ｼ繧偵そ繝�繧ｷ繝ｧ繝ｳ繧ｹ繧ｳ繝ｼ繝励↓險ｭ螳�
		HttpSession session = req.getSession();
		session.setAttribute("validationKey", validationKey);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/addUserForm.jsp");
		dispatcher.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
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
			//繧ｨ繝ｩ繝ｼ陦ｨ遉ｺ縺ｫ繝輔か繝ｯ繝ｼ繝�
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/errorView.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		User user = new User();
		try {
			user.setUserId( checkLongInput(req.getParameter("userid")) );
			user.setName(checkLongInput(req.getParameter("name")) );
			user.setAddress( checkLongInput(req.getParameter("address")) );
			user.setTel( checkPhoneNumber(req.getParameter("tel")) );
			user.setEmail( checkMailAddress(req.getParameter("email")) );

			// 繝代せ繝ｯ繝ｼ繝峨�ｮ繝上ャ繧ｷ繝･蛹�
			String rawPassword = req.getParameter("password");
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(rawPassword.getBytes("utf8"));
			String passwordHash = String.format("%064x", new BigInteger(1, digest.digest()));

			user.setPwdHash(passwordHash); // 繝上ャ繧ｷ繝･蛟､繧偵が繝悶ず繧ｧ繧ｯ繝医↓險ｭ螳�

			// user繧ｪ繝悶ず繧ｧ繧ｯ繝医ｒ繧ｻ繝�繧ｷ繝ｧ繝ｳ繧ｹ繧ｳ繝ｼ繝励↓荳�譌ｦ菫晏ｭ假ｼ�DB縺ｫ蜈･繧後ｋ縺ｮ縺ｯConfirm縺ｮ蠕鯉ｼ�
			session.setAttribute("userToAdd", user);

			// 遒ｺ隱咲判髱｢縺ｫ繝ｪ繝�繧､繝ｬ繧ｯ繝�
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/addUserConfirm.jsp");
			dispatcher.forward(req, resp);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InputCheckException e1) {
			//陦ｨ遉ｺ繝�繝ｼ繧ｿ繧堤畑諢上☆繧�
			ErrorViewData errorData = new ErrorViewData("繝輔か繝ｼ繝�縺ｫ蜈･蜉帙＆繧後◆蜀�螳ｹ縺ｫ蝠城｡後′縺ゅｊ縺ｾ縺励◆縲�",
													"蜈･蜉帷判髱｢縺ｫ謌ｻ繧�","/ActionLogger/adduser");
			req.setAttribute("errorData", errorData);
			//繧ｨ繝ｩ繝ｼ陦ｨ遉ｺ縺ｫ繝輔か繝ｯ繝ｼ繝�
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/errorView.jsp");
			dispatcher.forward(req, resp);
		}
	}

}
