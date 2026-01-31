import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.List;

@WebServlet("/tasks/*")
public class ListController extends HttpServlet {

  private final ObjectMapper objectMapper = new ObjectMapper();
  
  @Override //overrides the do post of httpservlet doPost is auto called whenever http post hits tasks, req is the http obj and resp the http response
  protected void doPost(HttpServletRequest req,
                        HttpServletResponse resp)
            throws IOException {
    
    String task = req.getParameter("task"); //retrives form parameters named task from the post bassically @RequestParam("task") in spring
    
    taskService.addTask(task); //calls service layer to auto save or process task
    
    resp.setStatus(HttpServletResponse.SC_CREATED);//sets http status to 201
    resp.getWriter().write("Task added") 
    
  }
  
  @Override
  protected void doDelete(HttpServletRequest req,
                        HttpServletResponse resp)
            throws IOException {
    
    String pathInfo = req.getPathInfo();
    if(pathInfo == null || pathInfo.equals("/")) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is missing");
      return;
    }
    
    try {
    
      Integer id = Integer.valueOf(pathInfo.substring(1));
      taskService.deleteTask(id);
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }catch (NumberFormatException e) {
    
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid task ID");
    }
  
  }
  
  @Override
  protected void doGet(HttpServletRequest req,
                        HttpServletResponse resp)
            throws IOException {
  
    List<Task> tasks = taskService.getAllTasks();
    
    String json = objectMapper.writeValueAsString(tasks);
    
    resp.setContentType("application/json");
    resp.setStatus(HttpServletResponse.SC_OK);
    
    resp.getWriter().write(json);
  }
  
}
