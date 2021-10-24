package rad.mining.ethermine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rad.mining.ethermine.domain.Payload;
import rad.mining.ethermine.util.PayloadUtil;

	@RestController
	@RequestMapping("/api/alive")
	@AllArgsConstructor
	@Slf4j
	public
	class AliveController {

		private final String version = "0.0.1";

		@GetMapping("")
		public ResponseEntity<Payload> alive() {
			log.info("is alive");
			return ResponseEntity.status(HttpStatus.OK).body(PayloadUtil.createPayload("alive"));
		}
		@GetMapping("/version")
		public
		ResponseEntity<Payload> version() {
			log.info("is alive");
			return ResponseEntity.status(HttpStatus.OK).body(PayloadUtil.createPayload(version));
		}
}
