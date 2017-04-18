package com.xix.cleanMvpArchitecture.data.network.exception;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xix on 25.3.16..
 */
public class RequestException extends IOException {




  private final ExceptionType type = ExceptionType.BAD_REQUEST;
  private  String message = "Invalid  request";
  public RequestException(String detailMessage) {
    super(detailMessage);
    this.message= detailMessage;
  }

  public RequestException() {
    super("Invalid  request ");
  }

  public RequestException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  public RequestException(Throwable throwable) {
    super(throwable);
  }


  public ExceptionType getType() {
    return type;
  }


  @Override public  String getLocalizedMessage(){
    return message;
  }
  @Override public String toString() {

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("message",message);
      jsonObject.put("type", type.toString());
      return jsonObject.toString(4);
    } catch (JSONException e) {
      e.printStackTrace();
      return "Invalid Stratus request ";
    }
  }


}
