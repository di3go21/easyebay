package com.easyebay.paypal;

//	
//	public class PayPalClient {
//		final String  clientId = "AX_U6HoRzR81mYyUbxQKn_q0BmAR85b5trji5Wv8j-t0PBcQJxnllLt8qg8EzNwqe6QzpjWhUEft9cfw";
//		final String clientSecret = "ELqdB76Qm81lty8vjoHSZ509ksrsIW1JqAMAXWuBEI7OX4kmruHi2FEVmXgKKXJ69l7tP61m3yugUggh";
//		
//		  /**
//		   *Set up the PayPal Java SDK environment with PayPal access credentials.  
//		   *This sample uses SandboxEnvironment. In production, use LiveEnvironment.
//		   */
//		  private PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
//		    clientId,
//		    clientSecret);
//
//		  /**
//		   *PayPal HTTP client instance with environment that has access
//		   *credentials context. Use to invoke PayPal APIs.
//		   */
//		  PayPalHttpClient client = new PayPalHttpClient(environment);
//
//		  /**
//		   *Method to get client object
//		   *
//		   *@return PayPalHttpClient client
//		   */
//		  public PayPalHttpClient client() {
//		    return this.client;
//		  }
//		}