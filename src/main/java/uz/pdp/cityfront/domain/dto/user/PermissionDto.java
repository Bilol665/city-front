package uz.pdp.cityfront.domain.dto.user;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PermissionDto  {
    private UUID id;
    private String permission;
}

