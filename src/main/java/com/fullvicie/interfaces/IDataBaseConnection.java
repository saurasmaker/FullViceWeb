package com.fullvicie.interfaces;

import com.fullvicie.enums.ErrorType;

public interface IDataBaseConnection {
	public ErrorType connect();
	public ErrorType disconnect();
}
