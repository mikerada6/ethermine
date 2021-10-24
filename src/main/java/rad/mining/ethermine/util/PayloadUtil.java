package rad.mining.ethermine.util;

import rad.mining.ethermine.domain.Payload;
import rad.mining.ethermine.domain.PayloadError;
import rad.mining.ethermine.domain.PayloadFailure;
import rad.mining.ethermine.domain.PayloadSuccess;

public
class PayloadUtil {

	public static Payload createPayload(Object payload) {
		return PayloadSuccess.builder().success(true).payload(payload).build();
	}

	public static
	Payload createPayload(Object payload, int errorCode, String errorMessage) {
		PayloadError error = PayloadError.builder().code(errorCode).message(errorMessage).build();
		return PayloadFailure.builder().success(false).payload(payload).error(error).build();
	}

}
