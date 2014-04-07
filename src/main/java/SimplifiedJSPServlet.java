import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
/**
 *
 * @author Gianluca Tessitore
 */
public class SimplifiedJSPServlet extends HttpServlet{
    
    private static final long serialVersionUID = 1L;
    
    //Support class to Modify errors in page declaretion
    
    void mergeScriptlets(
    
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            ServletContext application,
            JspWriter out,
            ServletConfig config,
            JspContext jspContext,
            Object page,
            PageContext pageContext,
            Throwable exception
            
    )throws Throwable{
    
        String test;
    
    }
    
}
