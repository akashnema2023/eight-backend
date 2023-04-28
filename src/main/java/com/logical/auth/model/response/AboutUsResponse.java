package com.logical.auth.model.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AboutUsResponse {
    public boolean result;
    public String message;
    public String data;
}
