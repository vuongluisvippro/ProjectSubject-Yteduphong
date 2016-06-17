package controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Vacxin;
import model.bo.VacxinBO;
import utils.MessageBudle;

/**
 * Servlet implementation class doVacxinThemMoi
 */
@SuppressWarnings("all")
public class doVacxinThemMoi extends HttpServlet {
	
	private static final MessageBudle messageBudle = new MessageBudle();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public doVacxinThemMoi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean contx = true;
		if(request.getParameter("them")!=null){
			if(new MessageBudle().validateName(request.getParameter("tenVacxin"))){
				// "Tên vắc xin không chứa ký tự đặc biệt!"
				contx = false;
			}	
			if(new MessageBudle().validateSoMui(request.getParameter("soMui"))){
				// "Số mũi tim là số dương 
				contx = false;
			}
			if(new MessageBudle().validateGiaVacxin(request.getParameter("giaVacxin"))){
				// "Giá vắc xin không hợp lệ"
				contx = false;
			}
			if(contx){
				String maVacxin = "VX"+new Random().nextInt(1000);
				String tenVacxin = new String(request.getParameter("tenVacxin").toString().getBytes("ISO-8859-1"),"UTF-8");
				int soMui = Integer.parseInt(request.getParameter("soMui"));
				String moTa = new String(request.getParameter("moTa").toString().getBytes("ISO-8859-1"),"UTF-8");
				int giaVacxin = Integer.parseInt(request.getParameter("giaVacxin"));
				String tenHang = new String(request.getParameter("tenHang").toString().getBytes("ISO-8859-1"),"UTF-8");
				// thêm vào cơ sỡ dữ liệu
				Vacxin vacxin = new Vacxin(maVacxin,tenVacxin,soMui,moTa,giaVacxin,tenHang);
				if(new VacxinBO().addVacxin(vacxin) > 0){
					response.sendRedirect(request.getContextPath()+"/admin/danh-sach-vacxin?msg=add1");
				}else{
					response.sendRedirect(request.getContextPath()+"/admin/danh-sach-vacxin?msg=add0");
				}
			}else{
				request.setAttribute("messageBudle",messageBudle);
				RequestDispatcher rd = request.getRequestDispatcher("/admin/vacxin-them-moi.jsp");
				rd.forward(request, response);
			}
		}else{
			response.sendRedirect(request.getContextPath()+"/admin/vacxin-them-moi");
		}
	}
}
