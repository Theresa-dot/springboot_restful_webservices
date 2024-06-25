package net.javaguides.springboot.mapper;

import javax.annotation.processing.Generated;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-23T11:41:49+0530",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
public class AutoUserMapperImpl implements AutoUserMapper {

    @Override
    public UserDto mapToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );

        return userDto;
    }

    @Override
    public User maptoUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setEmail( userDto.getEmail() );

        return user;
    }
}
