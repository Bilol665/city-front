package uz.pdp.cityfront.domain.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleDto {
    UUID id;
    String role;
    List<PermissionDto> permissions;
}
