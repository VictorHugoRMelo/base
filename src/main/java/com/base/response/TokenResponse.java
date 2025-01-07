package com.base.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenResponse implements Serializable {
	
	private static final long serialVersionUID = 1401377187357415743L;
	
	private String token;

}
