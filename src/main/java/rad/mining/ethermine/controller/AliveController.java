package rad.mining.ethermine.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rad.mining.ethermine.domain.Payload;
import rad.mining.ethermine.util.PayloadUtil;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public
class AliveController {

	@Autowired BuildProperties buildProperties;

	@GetMapping("/alive")
	public
	ResponseEntity<String> alive() {
		log.info("Alive");
		return ResponseEntity.status(HttpStatus.OK).body("alive");
	}

	@GetMapping("/version")
	public
	ResponseEntity<HashMap<String, String>> version() {

		HashMap<String, String> info = new HashMap<>();
		info.put("version", buildProperties.getVersion());
		info.put("time", buildProperties.getTime().toString());

		log.info("version - {}.", info.get("version"));
		log.info("time - {}.", info.get("time"));
		return ResponseEntity.status(HttpStatus.OK).body(info);
	}
}
