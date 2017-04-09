package com.xix.cleanMvpArchitecture.data.network.exception;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

/**
 * The type Stratus http exception.
 */
public class RestHttpException extends IOException {

  private final int code;
  private final String message;
  private final transient Response<?> response;
  private  String body = "Null";
  private ExceptionType mExceptionType;




  /**
   * Instantiates a new Stratus http exception.
   *
   * @param response the response
   * @param exceptionType the exception type
   */
  public RestHttpException(Response<?> response, ExceptionType exceptionType) {
    super("HTTP " + response.code() + " " + response.message());
    this.mExceptionType = exceptionType;
    this.code = response.code();
    this.message = response.message();
    this.response = response;
    try {
      this.body = response.errorBody().string();
    } catch (IOException e) {
      e.printStackTrace();

    }
  }


  //public RestHttpException(ExceptionType mExceptionType){
  //
  //}

  public RestHttpException(Response<?> response) {
    super("HTTP " + response.code() + " " + response.message());
    this.code = response.code();
    this.message = response.message();
    this.response = response;
    try {
      this.body = response.errorBody().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //initExceptionType();
  }

  //private void initExceptionType() {
  //  switch (code()){
  //    case 403:
  //  }
  //}

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
  /**
   * Sets exception type.
   *
   * @param exceptionType the exception type
   */
  public void setExceptionType(ExceptionType exceptionType) {
    this.mExceptionType = exceptionType;
  }

  /**
   * Gets exception type.
   *
   * @return the exception type
   */
  public ExceptionType getExceptionType() {
    return mExceptionType;
  }

  /** HTTP status code.  @return the int */
  public int code() {
    return code;
  }

  /** HTTP status message.  @return the string */
  @Override public  String getLocalizedMessage(){
    return message;
  }
  /**
   * The full HTTP response. This may be null if the exception was serialized.
   *
   * @return the response
   */
  public Response<?> response() {
    return response;
  }

  @Override
  public String toString() {

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("code", code);
      jsonObject.put("message", message);
      //jsonObject.put("type", mExceptionType.toString());
      jsonObject.put("body",body);
      return jsonObject.toString(4);
    } catch (JSONException e) {
      e.printStackTrace();
      return message;
    }
  }
}


