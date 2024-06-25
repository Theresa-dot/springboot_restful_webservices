package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistsException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //Convert UserDto into User JPA entity
        //User user= UserMapper.maptoUser(userDto);

        //User user= modelMapper.map(userDto, User.class);

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for user");
        }

        User user= AutoUserMapper.MAPPER.maptoUser(userDto);

        User savedUser = userRepository.save(user);

        //Convert User JPA entity to UserDto
        //UserDto savedUserDto=UserMapper.maptoUserDto(savedUser);
        //UserDto savedUserDto=modelMapper.map(savedUser, UserDto.class);

        UserDto savedUserDto=AutoUserMapper.MAPPER.mapToUserDto(savedUser);


        return savedUserDto;
    }

    @Override
    public UserDto getUserByID(Long userId) {
        User user= userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","id",userId)
        );
        //User savedUser = optionalUser.get();
        //UserDto savedUserDto=UserMapper.maptoUserDto(savedUser);
        //UserDto savedUserDto=modelMapper.map(savedUser, UserDto.class);

        UserDto savedUserDto=AutoUserMapper.MAPPER.mapToUserDto(user);


        return savedUserDto;

    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users= userRepository.findAll();
//        return users.stream().map(UserMapper::maptoUserDto)
//                .collect(Collectors.toList());
//        return users.stream().map((user) -> modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());
        return users.stream().map((user) -> AutoUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser=userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User","id",user.getId())
        );
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User savedUser= userRepository.save(existingUser);
        //return UserMapper.maptoUserDto(savedUser);
//        return modelMapper.map(savedUser, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser=userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","id",userId)
        );
        userRepository.deleteById(userId);
    }
}
