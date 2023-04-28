package com.logical.auth.model.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlayListRequest {
    private long userId;
    @NotNull(message = "playListName should not be null")
    String playListName;
}
