package com.campus.lostfound;
import jakarta.servlet.http.HttpSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;
@Controller
public class AppController {
 private final JdbcTemplate db;
 public AppController(JdbcTemplate db){this.db=db;}
 private final RowMapper<Item> itemMapper=(rs,row)->{
  Item i=new Item(); i.setId(rs.getLong("id")); i.setTitle(rs.getString("title"));
  i.setDescription(rs.getString("description")); i.setCategory(rs.getString("category"));
  i.setItemStatus(rs.getString("item_status")); i.setLocationFound(rs.getString("location_found"));
  i.setContact(rs.getString("contact")); i.setImageName(rs.getString("image_name"));
  i.setCreatedByName(rs.getString("created_by_name")); i.setCreatedAt(rs.getTimestamp("created_at")); return i;
 };
 @GetMapping("/")
 public String home(Model model,@RequestParam(required=false) String q,@RequestParam(required=false) String type){
  String search=q==null?"":q.trim(); String status=(type==null||type.isBlank())?"ALL":type.toUpperCase();
  String sql="SELECT i.*, COALESCE(u.name,'Campus user') AS created_by_name FROM items i LEFT JOIN users u ON i.created_by=u.id WHERE (?='' OR LOWER(CONCAT(i.title,' ',i.description,' ',i.category,' ',COALESCE(i.location_found,''))) LIKE LOWER(?)) AND (?='ALL' OR i.item_status=?) ORDER BY i.created_at DESC";
  List<Item> items=db.query(sql,itemMapper,search,"%"+search+"%",status,status);
  model.addAttribute("items",items); model.addAttribute("q",search); model.addAttribute("type",status);
  model.addAttribute("total",db.queryForObject("SELECT COUNT(*) FROM items",Integer.class));
  model.addAttribute("lostCount",db.queryForObject("SELECT COUNT(*) FROM items WHERE item_status='LOST'",Integer.class));
  model.addAttribute("foundCount",db.queryForObject("SELECT COUNT(*) FROM items WHERE item_status='FOUND'",Integer.class)); return "index";
 }
 @GetMapping("/login") public String login(){return "login";}
 @PostMapping("/login")
 public String doLogin(@RequestParam String email,@RequestParam String password,@RequestParam String role,HttpSession session,RedirectAttributes flash){
  List<Map<String,Object>> users=db.queryForList("SELECT id,name,email,role FROM users WHERE email=? AND password=? AND role=?",email.trim().toLowerCase(),password,role.toUpperCase());
  if(users.isEmpty()){flash.addFlashAttribute("error","Account not found. Check your details or create a student account.");return "redirect:/login";}
  Map<String,Object> u=users.get(0); session.setAttribute("userId",u.get("id")); session.setAttribute("userName",u.get("name")); session.setAttribute("userEmail",u.get("email")); session.setAttribute("userRole",u.get("role")); return "redirect:/dashboard";
 }
 @GetMapping("/register") public String register(){return "register";}
 @PostMapping("/register")
 public String doRegister(@RequestParam String name,@RequestParam String email,@RequestParam String password,RedirectAttributes flash){
  if(name==null||name.isBlank()||email==null||email.isBlank()||password.length()<6){flash.addFlashAttribute("error","Enter your name and email, and use a password with at least 6 characters.");return "redirect:/register";}
  try{db.update("INSERT INTO users(name,email,password,role) VALUES(?,?,?,'STUDENT')",name.trim(),email.trim().toLowerCase(),password);flash.addFlashAttribute("success","Student account created. You can now sign in.");return "redirect:/login";}
  catch(Exception ex){flash.addFlashAttribute("error","That email may already be registered.");return "redirect:/register";}
 }
 @GetMapping("/dashboard")
 public String dashboard(HttpSession session,Model model){
  if(session.getAttribute("userId")==null)return "redirect:/login";
  model.addAttribute("name",session.getAttribute("userName")); model.addAttribute("role",session.getAttribute("userRole"));
  model.addAttribute("items",db.query("SELECT i.*, COALESCE(u.name,'Campus user') AS created_by_name FROM items i LEFT JOIN users u ON i.created_by=u.id ORDER BY i.created_at DESC",itemMapper));
  model.addAttribute("users","ADMIN".equals(session.getAttribute("userRole"))?db.queryForList("SELECT id,name,email,role FROM users ORDER BY id DESC"):List.of()); return "dashboard";
 }
 @GetMapping("/report") public String report(HttpSession session){return session.getAttribute("userId")==null?"redirect:/login":"report";}
 @PostMapping("/report")
 public String saveReport(@RequestParam String title,@RequestParam String description,@RequestParam String category,@RequestParam String itemStatus,@RequestParam(required=false) String locationFound,@RequestParam String contact,@RequestParam(defaultValue="notebook.svg") String imageName,HttpSession session,RedirectAttributes flash){
  if(session.getAttribute("userId")==null)return "redirect:/login";
  List<String> choices=List.of("notebook.svg","bottle.svg","keys.svg","calculator.svg","umbrella.svg","idcard.svg","earbuds.svg","backpack.svg");
  String safeImage=choices.contains(imageName)?imageName:"notebook.svg"; String safeStatus=List.of("LOST","FOUND").contains(itemStatus.toUpperCase())?itemStatus.toUpperCase():"LOST";
  db.update("INSERT INTO items(title,description,category,item_status,location_found,contact,image_name,created_by) VALUES(?,?,?,?,?,?,?,?)",title.trim(),description,category,safeStatus,locationFound,contact,safeImage,session.getAttribute("userId"));
  flash.addFlashAttribute("success","Your item report has been published."); return "redirect:/dashboard";
 }
 @PostMapping("/items/{id}/status")
 public String updateStatus(@PathVariable long id,@RequestParam String status,HttpSession session,RedirectAttributes flash){
  if(!"ADMIN".equals(session.getAttribute("userRole")))return "redirect:/dashboard";
  if(List.of("LOST","FOUND","RETURNED").contains(status.toUpperCase())){db.update("UPDATE items SET item_status=? WHERE id=?",status.toUpperCase(),id);flash.addFlashAttribute("success","Item status updated.");} return "redirect:/dashboard";
 }
 @PostMapping("/logout") public String logout(HttpSession session){session.invalidate();return "redirect:/";}
}