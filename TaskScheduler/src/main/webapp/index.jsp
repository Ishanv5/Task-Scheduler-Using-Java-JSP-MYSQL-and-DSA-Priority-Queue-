<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.PriorityQueue" %>
<%@ page import="com.ishan.model.Task" %>
<%@ page import="com.ishan.servlet.TaskSchedulerServlet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task Scheduler</title>
</head>
<body>
    <h1>Task Scheduler</h1>
    <form action="TaskSchedulerServlet" method="post">
        <label for="taskName">Task Name:</label>
        <input type="text" id="taskName" name="taskName" required><br>
        <label for="taskPriority">Task Priority:</label>
        <input type="number" id="taskPriority" name="taskPriority" required><br>
        <button type="submit" name="action" value="add">Add Task</button>
        <button type="submit" name="action" value="next">Get Next Task</button>
    </form>
    <h2>Current Tasks:</h2>
    <ul>
        <%
            PriorityQueue<Task> taskQueue = (PriorityQueue<Task>) request.getAttribute("taskQueue");
            if (taskQueue != null && !taskQueue.isEmpty()) {
                for (Task task : taskQueue) {
                    out.println("<li>" + task.toString() + "</li>");
                }
            } else {
                out.println("<li>No tasks available</li>");
            }
        %>
    </ul>
    <%
        String nextTask = (String) request.getAttribute("nextTask");
        if (nextTask != null) {
            out.println("<h3>Next Task: " + nextTask + "</h3>");
        }
    %>
</body>
</html>
    