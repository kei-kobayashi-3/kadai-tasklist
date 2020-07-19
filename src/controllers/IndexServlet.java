package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em=DBUtil.createEntityManager();

        //get open page
        int page=1;
        try{
            page=Integer.parseInt(request.getParameter("page"));
        }catch(NumberFormatException e){}

        //get task order by max import value of task and start index
        List<Task>tasks=em.createNamedQuery("getAllTasks",Task.class)
                         .setFirstResult(15*(page -1))
                         .setMaxResults(15)
                         .getResultList();

        // get all value of task
        long tasks_count=(long)em.createNamedQuery("getTasksCount",Long.class)
                .getSingleResult();


        em.close();

        request.setAttribute("tasks", tasks);
        request.setAttribute("tasks_count", tasks_count);  //number of all
        request.setAttribute("page", page);                //number of pages

        //if "flush" set sessionScope, set this requestScope (remove sessionScope)
        if(request.getSession().getAttribute("flush")!=null){
            request.setAttribute("flush",request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/tasks/index.jsp");
        rd.forward(request, response);


    }

}
