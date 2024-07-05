package com.ishan.servlet;

import com.ishan.dao.TaskDAO;
import com.ishan.model.Task;
import com.ishan.model.TaskScheduler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/TaskSchedulerServlet")
public class TaskSchedulerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    public void init() {
        taskDAO = new TaskDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        TaskScheduler taskScheduler = (TaskScheduler) session.getAttribute("taskScheduler");

        if (taskScheduler == null) {
            taskScheduler = new TaskScheduler();
            session.setAttribute("taskScheduler", taskScheduler);
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String taskName = request.getParameter("taskName");
            int taskPriority = Integer.parseInt(request.getParameter("taskPriority"));
            Task task = new Task(0, taskName, taskPriority);
            taskScheduler.addTask(task);
            try {
                taskDAO.insertTask(task);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if ("next".equals(action)) {
            Task nextTask = taskScheduler.getNextTask();
            if (nextTask != null) {
                request.setAttribute("nextTask", nextTask.toString());
            } else {
                request.setAttribute("nextTask", "No tasks available");
            }
        }

        List<Task> tasks = taskDAO.selectAllTasks();
        tasks.forEach(taskScheduler::addTask);

        request.setAttribute("taskQueue", taskScheduler.getTaskQueue());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
