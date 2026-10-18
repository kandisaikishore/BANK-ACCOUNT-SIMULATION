package demo.bank;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.*;

@SpringBootApplication
public class ApiApplication {
 public static void main(String[] args){SpringApplication.run(ApiApplication.class,args);}
 @RestController @RequestMapping("/api/account") @CrossOrigin(origins="*")
 static class AccountApi {
  private double balance=10000;private double deposits=0,withdrawals=0;private final List<Map<String,Object>> txns=new ArrayList<>();
  @GetMapping Map<String,Object> overview(){return Map.of("balance",balance,"deposits",deposits,"withdrawals",withdrawals,"transactions",new ArrayList<>(txns));}
  @PostMapping("/transactions") ResponseEntity<?> transact(@RequestBody Map<String,Object> body){Object raw=body.get("amount");if(!(raw instanceof Number))return ResponseEntity.badRequest().body(Map.of("error","amount must be numeric"));double amount=((Number)raw).doubleValue();String kind=String.valueOf(body.getOrDefault("kind",""));if(!Double.isFinite(amount)||amount<=0)return ResponseEntity.badRequest().body(Map.of("error","amount must be positive"));if(!kind.equals("deposit")&&!kind.equals("withdrawal"))return ResponseEntity.badRequest().body(Map.of("error","kind must be deposit or withdrawal"));if(kind.equals("withdrawal")&&amount>balance)return ResponseEntity.badRequest().body(Map.of("error","insufficient simulated balance"));if(kind.equals("deposit")){balance+=amount;deposits+=amount;}else{balance-=amount;withdrawals+=amount;}Map<String,Object>t=new LinkedHashMap<>();t.put("id",System.currentTimeMillis());t.put("amount",amount);t.put("kind",kind);t.put("account",body.getOrDefault("account","Savings"));t.put("note",body.getOrDefault("note",""));t.put("time",java.time.Instant.now().toString());txns.add(0,t);return ResponseEntity.status(201).body(overview());}
 }
}