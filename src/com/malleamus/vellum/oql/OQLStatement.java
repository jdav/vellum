package com.malleamus.vellum.oql;

import com.malleamus.vellum.VellumException;

public abstract class OQLStatement {

	public static OQLStatement create(String oqlString) throws VellumException {
		if (oqlString.startsWith("REGISTER")) {
			return new RegistrationStatement(oqlString);
		} else if (oqlString.startsWith("WITHDRAW")) {
			return new WithdrawalStatement(oqlString);
		} else if (oqlString.startsWith("CHECKOUT")) {
			return new CheckoutStatement(oqlString);
		} else if (oqlString.startsWith("CHECKIN")) {
			return new CheckinStatement(oqlString);
		} else if (oqlString.startsWith("GET")) {
			return new GetStatement(oqlString);
		} else if (oqlString.startsWith("FIND")) {
			return new FindStatement(oqlString);
		} else {
			throw new VellumException("Cannot parse OQL statement: "
					+ oqlString);
		}
	}
}
