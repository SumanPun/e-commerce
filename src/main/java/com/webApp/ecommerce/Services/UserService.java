package com.webApp.ecommerce.Services;

import com.webApp.ecommerce.Config.SecurityConfig;
import com.webApp.ecommerce.Exceptions.ResourceNotFoundException;
import com.webApp.ecommerce.Model.Role;
import com.webApp.ecommerce.Model.User;
import com.webApp.ecommerce.Model.UserRole;
import com.webApp.ecommerce.Payloads.UserDto;
import com.webApp.ecommerce.Repositories.RoleRepository;
import com.webApp.ecommerce.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static Logger logger= LoggerFactory.getLogger(SecurityConfig.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public User dtoToUser(UserDto userDto) {
    	
    	return this.modelMapper.map(userDto,User.class);
    }
    public UserDto userToDto(User user) {
        return this.modelMapper.map(user,UserDto.class);
    }

    public UserDto createUser(UserDto userDto) {
if(logger.isDebugEnabled()){

    logger.debug(UserService.class+"createusers");
}
try {
    User user = this.dtoToUser(userDto);
    user.setAddedDate(LocalDateTime.now());
    user.setActive(true);
    user.setPassWord(passwordEncoder.encode(userDto.getPassWord()));
    Role role = this.roleRepository.findById(2).get();
    Set<UserRole> userRoles1 = new HashSet<>();
    UserRole userRole = new UserRole();
    userRole.setRole(role);
    userRole.setUser(user);
    userRoles1.add(userRole);
    for(UserRole ur : userRoles1) {
        this.roleRepository.save(ur.getRole());
    }
//    user.getUserRoles().addAll(userRoles);
    //this.roleRepository.save(userRole.getRole());
    user.getUserRoles().addAll(userRoles1);
    User saveUser = this.userRepository.save(user);
    return this.userToDto(saveUser);
}catch(Exception e){
logger.debug(e.getMessage()+"create user"+UserService.class);
return null;

}



}

    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","userId",userId));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassWord(userDto.getPassWord());
        user.setAddress(userDto.getAddress());
        user.setPhoneNo(userDto.getPhoneNo());
        User upDateUser = this.userRepository.save(user);
        return this.userToDto(upDateUser);
    }

    public UserDto getUserById(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        return this.userToDto(user);
    }

    public List<UserDto> getAllUser() {
        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtos = users.stream().map((user -> this.userToDto(user))).collect(Collectors.toList());
        return userDtos;
    }

    public UserDto deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        UserDto showUser = this.userToDto(user);
        this.userRepository.delete(user);
        return showUser;
    }

    public List<Object[]> getUserAndRole() {
        List<Object[]> objects = this.userRepository.getUserAndRole();
        return objects;
    }

}
