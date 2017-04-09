package com.xix.cleanMvpArchitecture.data.network.exception;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xix on 23.3.16..
 */
public class UserNotAuthorizedException extends Exception {
  /**
   * Instantiates a new User not authorized exception.
   */
  public UserNotAuthorizedException() {
    super("User is not authorized");
  }

  /**
   * Instantiates a new User not authorized exception.
   *
   * @param detailMessage the detail message
   */
  public UserNotAuthorizedException(String detailMessage) {
    super(detailMessage);
  }

  /**
   * Instantiates a new User not authorized exception.
   *
   * @param detailMessage the detail message
   * @param throwable the throwable
   */
  public UserNotAuthorizedException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  /**
   * Instantiates a new User not authorized exception.
   *
   * @param throwable the throwable
   */
  public UserNotAuthorizedException(Throwable throwable) {
    super(throwable);
  }

  @Override public  String getLocalizedMessage(){
    return "User is not authorized";
  }
  @Override public String toString() {
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("message", "User is not authorized");
      jsonObject.put("type", ExceptionType.NOT_AUTHORIZED.toString());
      return jsonObject.toString(4);
    } catch (JSONException e) {
      e.printStackTrace();
      return "User is not authorized";
    }
  }
}
