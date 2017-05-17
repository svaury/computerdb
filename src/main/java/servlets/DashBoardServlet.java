package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ComputerService;

/**
 * Servlet implementation class DashBoardServlet.
 */
@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerService computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashBoardServlet() {
		super();
		computerService = new ComputerService();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request .
	 * @param response .
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String search = request.getParameter("search");
		String page = request.getParameter("page");
		String itemNumber = request.getParameter("item_number");

		request.setAttribute("search", search);
		request.setAttribute("computerList", computerService.getComputerFromTo(page, itemNumber, search));
		request.setAttribute("currentPage", computerService.getPage().getCurrentPage());
		request.setAttribute("startPage", computerService.getPage().startPage());
		request.setAttribute("endPage", computerService.getPage().endPage());
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}

}
