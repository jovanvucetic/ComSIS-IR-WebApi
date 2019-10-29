package comsis.service.mapper;

import comsis.core.model.User;
import comsis.data.entity.UserDto;
import comsis.service.mapperinterface.UserMapper;
import comsis.service.mapperinterface.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IrUserMapper implements UserMapper {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User toServiceModel(UserDto dto) {
        if(dto == null) {
            return null;
        }

        User serviceModel = new User();

        serviceModel.setUsername(dto.getUsername());
        serviceModel.setPassword(dto.getPassword());
        serviceModel.setRole(userRoleMapper.toServiceModel(dto.getRole()));

        return serviceModel;
    }

    public UserDto toDto(User serviceModel) {
        if(serviceModel == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername(serviceModel.getUsername());
        userDto.setPassword(serviceModel.getPassword());
        userDto.setRole(userRoleMapper.toDto(serviceModel.getRole()));

        return userDto;
    }
}
