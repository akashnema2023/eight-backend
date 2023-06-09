package com.logical.auth.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="playlist")
@Entity
public class PlayListData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playListId;
    private  long userId;
    private int videoId;
    private String playListName;
    private String playListImg;
}
