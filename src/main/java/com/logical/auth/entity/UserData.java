package com.logical.auth.entity;
import com.logical.auth.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name="user")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
    private String profileImgUrl;
    private String backgroundImgUrl;
    private long totalSubscriber=0;
    private long totalUploadedVideos;
    private String description;
    private String channelUri;
    private boolean isActive;
  //  private String role;
}
