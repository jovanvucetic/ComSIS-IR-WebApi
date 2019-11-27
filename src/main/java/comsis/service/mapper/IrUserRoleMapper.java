package comsis.service.mapper;

import comsis.core.model.UserRole;
import comsis.data.entity.UserRoleDto;
import comsis.core.mapperinterface.UserRoleMapper;
import org.springframework.stereotype.Component;

@Component
public class IrUserRoleMapper implements UserRoleMapper {
    @Override
    public UserRoleDto toDto(UserRole userRole) {
        switch (userRole) {
            case USER:
                return UserRoleDto.USER;
            case ADMIN:
                return UserRoleDto.ADMIN;
            default:
                throw new IllegalArgumentException("Passed user role does not exist");
        }
    }

    @Override
    public UserRole toServiceModel(UserRoleDto userRoleDto) {
        switch (userRoleDto) {
            case USER:
                return UserRole.USER;
            case ADMIN:
                return UserRole.ADMIN;
            default:
                throw new IllegalArgumentException("Passed user role dto does not exist");
        }
    }
}
