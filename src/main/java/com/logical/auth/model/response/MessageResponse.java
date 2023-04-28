package com.logical.auth.model.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {
    public boolean result;
    public String message;
    public MessageResponse( boolean result,String message) {
        this.message = message;
        this.result = result;
    }
}
